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
import java.util.HashMap;
import java.util.Map;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 29, 2007
 * Time: 3:29:53 AM
 */

@Embeddable
public class Fee extends BaseObject implements Serializable {
    private static final long serialVersionUID = -3556535324886154866L;

    //Static access
    public static final Integer PER_STOP = 1;
    public static final Integer PER_MILE = 2;
    public static final Integer PER_HOUR = 3;
    public static final Integer FLAT_FEE = 4;
    public static final Map<Integer, String> feeTypeNames;
    static {
        feeTypeNames = new HashMap<Integer, String>();
        feeTypeNames.put(PER_STOP, "Per Stop");
        feeTypeNames.put(PER_MILE, "Per Mile");
        feeTypeNames.put(PER_HOUR, "Per Hour");
        feeTypeNames.put(FLAT_FEE, "Flat");
    }


    private String name;
    private Double amount;
    private Integer type;
    private String description;

    private Boolean isEditable = false;


    //Constructors

    public Fee() {}
    public Fee(String name, Double amount, Integer type, String description) {
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.description = description;
    }


    //Getters

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @Column(nullable = false)
    public Double getAmount() {
        return amount;
    }

    @Column(nullable = false)
    public Integer getType() {
        return type;
    }

    @Column(nullable = true)
    public String getDescription() {
        return description;
    }


    @Transient
    public String getTypeString() {
      return feeTypeNames.get(type);
    }

    @Transient
    public Boolean getIsEditable() {
        return isEditable;
    }


    //Setters

    public void setName(String name) {
        this.name = name;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public void setType(Integer type) {
        this.type = type;
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
        if ( !(o instanceof Fee) ) return false;

        final Fee other = (Fee) o;
        return new EqualsBuilder()
                .append(this.name, other.name)
                .append(this.amount, other.amount)
                .append(this.type, other.type)
                .isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder(17,37)
                .append(this.name)
                .append(this.amount)
                .append(this.type)
                .toHashCode();
    }
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", this.name)
                .append("amount", this.amount)
                .append("type", this.type)
                .append("desc", this.description)
                .toString();
    }
}