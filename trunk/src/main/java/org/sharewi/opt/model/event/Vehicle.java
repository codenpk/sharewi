package org.sharewi.opt.model.event;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.appfuse.model.BaseObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: pkatsev
 * Date: Nov 25, 2007
 * Time: 11:34:34 PM
 */
@Embeddable
public class Vehicle extends BaseObject implements Serializable {
    private static final long serialVersionUID = 6244543910569416392L;

    private Integer capacity;
    private String description;

    private Boolean isEditable = false;


    //Constructor
    public Vehicle() {
    }


    //Getters
    @Column(nullable = false)
    public Integer getCapacity() {
        return capacity;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    @Transient
    public Boolean getIsEditable() {
        return isEditable;
    }


    //Setters
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsEditable(Boolean editable) {
        isEditable = editable;
    }


    //Common
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;

        Vehicle vehicle = (Vehicle) o;

        return new EqualsBuilder()
                .append(this.capacity, vehicle.capacity)
                .append(this.capacity, vehicle.description)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.capacity)
                .append(this.description)
                .toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(this.capacity)
                .append(this.description)
                .toString();
    }
}
