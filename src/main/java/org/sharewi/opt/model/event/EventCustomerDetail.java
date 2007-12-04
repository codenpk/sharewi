package org.sharewi.opt.model.event;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.appfuse.model.BaseObject;
import org.hibernate.annotations.Parent;
import org.sharewi.opt.model.location.Customer;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jul 25, 2007
 * Time: 10:09:47 PM
 */

@Embeddable
public class EventCustomerDetail extends BaseObject implements Serializable {
    private static final long serialVersionUID = 5499391976751120395L;

    //Foreign keys
    private Event event;            //parent
    private Customer customer;          //child

    //Additional properties
    private Integer demand;             //customer's demand for this instance
    private Boolean pickup;             //overrides pickup.bool for this instance



    //Constructors

    public EventCustomerDetail() {}
    public EventCustomerDetail(Event event, Customer customer, Integer demand, Boolean pickup) {
        this.event = event;
        this.customer = customer;
        this.demand = demand;
        this.pickup = pickup;
    }


    //Getters

    @Parent
    public Event getEvent() {
        return event;
    }

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    public Customer getCustomer() {
        return customer;
    }

    @Column
    public Integer getDemand() {
        return demand;
    }

    @Column
    public Boolean getPickup() {
        return pickup;
    }


    //Setters

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDemand(Integer demand) {
        this.demand = demand;
    }

    public void setPickup(Boolean pickup) {
        this.pickup = pickup;
    }


    //Utility

    public boolean equals(Object o) {
        if (this == o) return true;
        if ( !(o instanceof EventCustomerDetail)) return false;

        final EventCustomerDetail other = (EventCustomerDetail) o;
        return new EqualsBuilder()
                .append(this.event, other.event)
                .append(this.customer, other.customer)
                .isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder(17,37)
                .append(this.event)
                .append(this.customer)
                .toHashCode();
    }
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("event",    this.event.getId())
                .append("customer", this.customer.getId())
                .append("demand",   this.demand)
                .append("pickup",   this.pickup)
                .toString();
    }
}