package org.sharewi.opt.model.event;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.appfuse.model.BaseObject;
import org.appfuse.model.User;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.sharewi.opt.model.location.Customer;
import org.sharewi.opt.model.location.Depot;
import org.sharewi.opt.model.solution.Solution;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 29, 2007
 * Time: 3:04:27 AM
 */

@Entity
public class Event extends BaseObject implements Serializable {
    private static final long serialVersionUID = -585180288293303741L;

    //TODO add fields: an Enum to select which type of problem this (OVRP, CVPR, ...) and performing algorithm name

    private Long id;                                        //signature
    private Integer version;                                //signature
    private DateTime createdOn = null;                      //signature
    private User author = null;                             //required

    private String name;                                    //required
    private String note;

    private DateTime dateTimeStart = null;

    private Set<EventCustomerDetail> customers = null;      //collection
    private Set<EventDepotDetail> depots = null;            //collection
    private Set<TruckCompany> truckCompanies = null;        //collection

    private Set<Solution> solutions = null;                 //collection


    //Constructor

    public Event() {
        createdOn = new DateTime(DateTimeZone.UTC);
        author = new User();

        customers = new LinkedHashSet<EventCustomerDetail>();
        depots = new LinkedHashSet<EventDepotDetail>();
        truckCompanies = new LinkedHashSet<TruckCompany>();

        solutions = new LinkedHashSet<Solution>();
    }


    //Getters

    /* signature */

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    @Column(nullable = false, updatable = false)
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    public DateTime getCreatedOn() {
        return createdOn;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(nullable = false)
    public User getAuthor() {
        return author;
    }

    /* details */

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @Type(type = "org.sharewi.opt.util.hibernate.joda.PersistentDateTimeExactTZ")
    @Columns(columns = {
    @Column(name = "dateTime", nullable = false, unique = true),
    @Column(name = "timeZone", nullable = false) })
    public DateTime getDateTimeStart() {
        return dateTimeStart;
    }


    @Column
    public String getNote() {
        return note;
    }

    /* collections */

    @CollectionOfElements(targetElement = EventCustomerDetail.class, fetch = FetchType.EAGER)
    @JoinTable(name = "Event_Customer")
    public Set<EventCustomerDetail> getCustomers() {
        return customers;
    }

    @CollectionOfElements(targetElement = EventDepotDetail.class, fetch = FetchType.EAGER)
    @JoinTable(name = "Event_Depot")
    public Set<EventDepotDetail> getDepots() {
        return depots;
    }

    @ManyToMany(targetEntity = TruckCompany.class, fetch = FetchType.EAGER)
    @JoinTable(name = "Event_TruckCompany",inverseJoinColumns = { @JoinColumn(name = "truckCompany_id", nullable = false) } )
    public Set<TruckCompany> getTruckCompanies() {
        return truckCompanies;
    }

    @OneToMany(targetEntity = Solution.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Cascade( value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN )
    @JoinTable(name = "Event_Solution")
    public Set<Solution> getSolutions() {
        return solutions;
    }


    //Setters

    public void setId(Long id) {
        this.id = id;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }
    public void setAuthor(User author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setNote(String note) {
        this.note = note;
    }

    public void setDateTimeStart(DateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public void setCustomers(Set<EventCustomerDetail> customers) {
        this.customers = customers;
    }
    public void setDepots(Set<EventDepotDetail> depots) {
        this.depots = depots;
    }
    public void setTruckCompanies(Set<TruckCompany> truckCompanies) {
        this.truckCompanies = truckCompanies;
    }

    public void setSolutions(Set<Solution> solutions) {
        this.solutions = solutions;
    }


    //Convenience

    public void addTruckCompany(TruckCompany truckCompany) {
        this.truckCompanies.add(truckCompany);
    }
    public void removeTruckCompany(TruckCompany truckCompany) {
        this.truckCompanies.remove(truckCompany);
    }

    public void addCustomer(Customer customer, Integer demand, Boolean pickup ) {
        customers.add(new EventCustomerDetail(this, customer, demand, pickup));
    }
    public void removeCustomer(Customer customer) {
        if (customers != null) {
            Iterator<EventCustomerDetail> iterator = customers.iterator();
            if (iterator.hasNext()) {
                do {
                    EventCustomerDetail eventCustomerDetail = iterator.next();
                    if (eventCustomerDetail.getCustomer().equals(customer)) {
                        this.customers.remove(eventCustomerDetail);
                    }
                } while (iterator.hasNext());
            }
        }
    }

    public void addDepot(Depot depot) {
        this.depots.add(new EventDepotDetail(this, depot));
    }
    public void removeDepot(Depot depot) {
        if (depots != null) {
            Iterator<EventDepotDetail> iterator = depots.iterator();
            if (iterator.hasNext()) {
                do {
                    EventDepotDetail eventDepotDetail = iterator.next();
                    if (eventDepotDetail.getDepot().equals(depot)) {
                        depots.remove(eventDepotDetail);
                    }
                } while (iterator.hasNext());
            }
        }
    }

    public void addSolution(Solution solution) {
        solution.setEvent(this);
        this.solutions.add(solution);
    }
    public void removeSolution(Solution solution) {
        this.solutions.remove(solution);
    }


    //Transients
    @Transient
    public String getStartDateAsString() {
        DateTimeFormatter fmt = DateTimeFormat.mediumDate();
        return fmt.print(dateTimeStart);
    }
    @Transient
    public String getStartTimeAsString() {
        DateTimeFormatter fmt = DateTimeFormat.shortTime();
        return fmt.print(dateTimeStart);
    }
    @Transient
    public String getStartDateDayOfWeek() {
        return dateTimeStart.dayOfWeek().getAsShortText();
    }
    @Transient
    public Integer getCustomerCount() {
        return customers.size();
    }
    @Transient
    public Integer getDepotCount() {
        return depots.size();
    }
    @Transient
    public Integer getTruckCompanyCount() {
        return truckCompanies.size();
    }

    //Utility

    public boolean equals(Object o) {
        if (this == o) return true;
        if ( !(o instanceof Event)) return false;

        final Event other = (Event) o;
        return new EqualsBuilder()
                .append(this.name, other.name)
                .append(this.createdOn, other.name)
                .append(this.author, other.author)
                .isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder(17,37)
                .append(this.name)
                .append(this.createdOn)
                .append(this.author)
                .toHashCode();
    }
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id",        this.id)
                .append("name",      this.name)
                .append("createdOn", this.createdOn)
                .append("for days",  this.author)
                .append("solutions", this.solutions)
                .toString();
    }
}