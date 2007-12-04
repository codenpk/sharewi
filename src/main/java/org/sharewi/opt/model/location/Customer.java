package org.sharewi.opt.model.location;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Aug 5, 2007
 * Time: 6:32:39 PM
 */

@Entity
public class Customer extends Location implements Serializable {
    private static final long serialVersionUID = -3874971330632681586L;

    private String hostSite;                                    //required
    private String distSite;                                    //required

    //TODO: should be implemented as optional properties?
    private Integer number;                                     //not required
    private Integer region;                                     //not required

    private Boolean pickup;                                     //required

    //Constructor

    public Customer() {
        pickup = false;
    }


    //Getters

    @Column(length = 30, nullable = false)
    public String getHostSite() {
        return hostSite;
    }

    @Column(length = 30, nullable = false)
    public String getDistSite() {
        return distSite;
    }

    @Column
    public Integer getNumber() {
        return number;
    }

    @Column
    public Integer getRegion() {
        return region;
    }

    @Column(nullable = false)
    public Boolean getPickup() {
        return pickup;
    }


    //Setters

    public void setHostSite(String hostSite) {
        this.hostSite = hostSite;
    }
    public void setDistSite(String distSite) {
        this.distSite = distSite;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public void setRegion(Integer region) {
        this.region = region;
    }
    public void setPickup(Boolean pickup) {
        this.pickup = pickup;
    }


    //Common

    public boolean equals(Object o) {
        if (this == o) return true;
        if ( !(o instanceof Customer)) return false;

        final Customer other = (Customer) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(this.distSite, other.distSite)
                .append(this.hostSite, other.hostSite)
                .isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder(17,37)
                .appendSuper(super.hashCode())
                .append(this.distSite)
                .append(this.hostSite)
                .toHashCode();
    }
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .appendSuper(super.toString())
                .append("hostSite", this.hostSite)
                .append("distSite", this.distSite)
                .append("number", this.number)
                .append("region", this.region)
                .append("pickup", this.pickup)
                .toString();
    }
}