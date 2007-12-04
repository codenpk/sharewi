package org.sharewi.opt.model.location;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.appfuse.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: pkatsev
 * Date: Apr 16, 2007
 * Time: 12:06:09 AM
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"street", "city", "province", "postalCode", "country"})})
public abstract class Location extends BaseObject implements Serializable {
    private static final long serialVersionUID = 7527161250563135378L;

    private Long id;                                    //signature field
    private Integer version;                            //signature field

    private Address address = null;                      //required 
    private Geocode geocode = null;                      //not required

    private OperationHours operationHours = null;        //required

    private String phone;                                //not required
    private String email;                                //not required
    private String note;

    //Constructor

    public Location() {
        this.address = new Address();
        this.geocode = new Geocode();
        this.operationHours = new OperationHours();
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

    @Embedded
    public Address getAddress() {
        return address;
    }

    @Embedded
    public Geocode getGeocode() {
        return geocode;
    }

    @Embedded
    public OperationHours getOperationHours() {
        return operationHours;
    }

    @Column(length = 15)
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

    //Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setGeocode(Geocode geocode) {
        this.geocode = geocode;
    }

    public void setOperationHours(OperationHours operationHours) {
        this.operationHours = operationHours;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNote(String note) {
        this.note = note;
    }

    //Utility

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        final Location other = (Location) o;
        return new EqualsBuilder()
                .append(this.address, other.address)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.address).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", this.id)
                .append("geocode", this.geocode)
                .append("address", this.address)
                .append("hours", this.operationHours)
                .toString();
    }
}