package org.sharewi.opt.model.location;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: pkatsev
 * Date: Apr 16, 2007
 * Time: 12:06:09 AM
 */

@Entity
public class Depot extends Location implements Serializable {
    private static final long serialVersionUID = 7954197466945403126L;

    private String businessName;                        //required
    private String operationsSite;                      //required


    //Getters

    @Column(length = 30, nullable = false)
    public String getBusinessName() {
        return businessName;
    }

    @Column(length = 30, nullable = false)
    public String getOperationsSite() {
        return operationsSite;
    }


    //Setters

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    public void setOperationsSite(String operationsSite) {
        this.operationsSite = operationsSite;
    }


    //Common

    public boolean equals(Object o) {
        if (this == o) return true;
        if ( !(o instanceof Depot) ) return false;

        final Depot other = (Depot) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(this.businessName, other.businessName)
                .append(this.operationsSite, other.operationsSite)
                .isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder(17,37)
                .appendSuper(super.hashCode())
                .append(businessName)
                .append(operationsSite)
                .toHashCode();
    }
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .appendSuper(super.toString())
                .append("businessName", this.businessName)
                .append("operationsSite", this.operationsSite)
                .toString();
    }
}