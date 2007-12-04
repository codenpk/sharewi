package org.sharewi.opt.model.location;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.appfuse.model.BaseObject;
import org.hibernate.annotations.Type;
import org.joda.time.DateTimeZone;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Locale;

@Embeddable
public class Address extends BaseObject implements Serializable {
    private static final long serialVersionUID = 3896756182196759687L;

    private String street;
    private String city;                              //required
    private String province;                          //required
    private String country;                           //required
    private String postalCode;                        //required

    private DateTimeZone timeZone;                    //required


    //Constructor
    public Address() {
        //todo make this an option of sorts
        this.country = Locale.US.getCountry();
    }


    //Getters

    @Column(length = 150)
    public String getStreet() {
        return street;
    }

    @Column(length = 50)
    public String getCity() {
        return city;
    }

    @Column(nullable = false, length = 100)
    public String getProvince() {
        return province;
    }

    @Column(nullable = false, length = 100)
    public String getCountry() {
        return country;
    }

    @Column(nullable = false, length = 15)
    public String getPostalCode() {
        return postalCode;
    }

    @Column(nullable = false)
    @Type(type = "org.sharewi.opt.util.hibernate.joda.PersistentTZ")
    public DateTimeZone getTimeZone() {
        return timeZone;
    }


    //Setters

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setTimeZone(DateTimeZone timeZone) {
        this.timeZone = timeZone;
    }


    //Common

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        final Address other = (Address) o;
        return new EqualsBuilder()
                .append(this.street, other.street)
                .append(this.city, other.city)
                .append(this.province, other.province)
                .append(this.postalCode, other.postalCode)
                .append(this.country, other.country)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.street)
                .append(this.city)
                .append(this.province)
                .append(this.postalCode)
                .append(this.country)
                .toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("country", this.country)
                .append("street", this.street)
                .append("province", this.province)
                .append("postalCode", this.postalCode)
                .append("city", this.city)
                .append("timeZone", this.timeZone)
                .toString();
    }
}