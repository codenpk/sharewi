package org.sharewi.opt.model.location;

import org.appfuse.model.BaseObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Embeddable;
import javax.persistence.Column;
import java.io.Serializable;

@Embeddable
public class Geocode extends BaseObject implements Serializable {
    private static final long serialVersionUID = -8895081310185247669L;

    private Double latitude;
    private Double longitude;


    /* Getters */

    @Column(nullable = false)
    public Double getLatitude() {
        return latitude;
    }

    @Column(nullable = false)
    public Double getLongitude() {
        return longitude;
    }


    /* Setters */

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    /* Common */

    public boolean equals(Object o) {
        if (this == o) return true;
        if ( !(o instanceof Geocode) ) return false;

        final Geocode other = (Geocode) o;
        return new EqualsBuilder()
                .append(this.latitude, other.latitude)
                .append(this.longitude, other.longitude)
                .isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder(11,29)
                .append(this.latitude)
                .append(this.longitude)
                .toHashCode();
    }
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("lat",  this.latitude)
                .append("long", this.longitude)
                .toString();
    }
}