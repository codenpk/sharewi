package org.sharewi.opt.model.event;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.appfuse.model.BaseObject;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.IndexColumn;
import org.sharewi.opt.model.location.Address;
import org.sharewi.opt.model.location.Geocode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 29, 2007
 * Time: 3:05:06 AM
 */
@Entity
public class TruckCompany extends BaseObject implements Serializable {
    private static final long serialVersionUID = -288981710969006546L;

    private Long id;
    private Integer version;

    private String name;                            //required
    private String email;
    private String phone;
    private String note;

    private Address address = null;
    private Geocode geocode = null;                 //not required
    private String contact;

    private List<Fee> fees = null;
    private List<Vehicle> vehicles = null;

    //TODO implement props to store geo cutoffs

    //Constructor

    public TruckCompany() {
        address = new Address();

        geocode = new Geocode();
        geocode.setLatitude(.0);
        geocode.setLongitude(.0);

        fees = new ArrayList<Fee>();
        vehicles = new ArrayList<Vehicle>();
    }

    //Getters

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    @Embedded
    public Address getAddress() {
        return address;
    }

    @Embedded
    public Geocode getGeocode() {
        return geocode;
    }

    @Column
    public String getContact() {
        return contact;
    }

    @Column
    public String getPhone() {
        return phone;
    }

    @Column
    public String getEmail() {
        return email;
    }

    @Column
    public String getNote() {
        return note;
    }

    @CollectionOfElements(targetElement = Fee.class, fetch = FetchType.EAGER)
    @JoinTable(name = "TruckCompany_Fee")
    @IndexColumn(name = "pos")
    public List<Fee> getFees() {
        return fees;
    }

    @CollectionOfElements(targetElement = Vehicle.class, fetch = FetchType.EAGER)
    @JoinTable(name = "TruckCompany_Vehicle")
    @IndexColumn(name = "pos")
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    //Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setGeocode(Geocode geocode) {
        this.geocode = geocode;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    //Convenience

    public void addFee(Fee fee) {
        fees.add(fee);
    }

    public void removeFee(Fee fee) {
        fees.remove(fee);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    //Common

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TruckCompany)) return false;

        final TruckCompany other = (TruckCompany) o;
        return new EqualsBuilder()
                .append(this.name, other.name)
                .append(this.address, other.address)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.name)
                .append(this.address)
                .toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", this.id)
                .append("version", this.version)
                .append("name", this.name)
                .append("address", this.address)
                .append("contact", this.contact)
                .append("phone", this.phone)
                .toString();
    }
}