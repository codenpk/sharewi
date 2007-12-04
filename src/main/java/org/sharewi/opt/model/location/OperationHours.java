package org.sharewi.opt.model.location;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.appfuse.model.BaseObject;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.CollectionOfElements;
import org.joda.time.LocalTime;
import org.joda.time.Chronology;
import org.joda.time.chrono.ISOChronology;

import java.io.Serializable;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: pkatsev
 * Date: Apr 16, 2007
 * Time: 12:09:23 AM
 */

@Embeddable
public class OperationHours extends BaseObject implements Serializable {
    private static final long serialVersionUID = -822414765609726176L;

    private LocalTime timeOpens = null;             //required
    private LocalTime timeCloses = null;            //required

    private Set<Integer> daysOfWeek = null;         //required


    //Constructor
    public OperationHours() {
        this.daysOfWeek = new HashSet<Integer>();
    }


    //Getters

    @Column(nullable = false)
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeExact")
    public LocalTime getTimeOpens() {
        return timeOpens;
    }

    @Column(nullable = false)
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeExact")
    public LocalTime getTimeCloses() {
        return timeCloses;
    }

    @CollectionOfElements( targetElement = Integer.class, fetch = FetchType.EAGER)
    @JoinTable( name = "Location_DayOfWeek" )
    @Column( name = "dayOfWeek", nullable = false )
    public Set<Integer> getDaysOfWeek() {
        return daysOfWeek;
    }


    //Setters

    public void setTimeOpens(LocalTime timeOpens) {
        this.timeOpens = timeOpens;
    }
    public void setTimeCloses(LocalTime timeCloses) {
        this.timeCloses = timeCloses;
    }
    public void setDaysOfWeek(Set<Integer> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }


    //Convenience

    @Transient
    public void addDayOfWeek(Integer day) {
        getDaysOfWeek().add(day);
    }

    @Transient
    public String getDaysOfWeekAsText() {
        TreeSet<Integer> sortedSet = new TreeSet<Integer>(daysOfWeek);

        StringBuffer sb = new StringBuffer();
        if (!sortedSet.isEmpty()) {
            Chronology c = ISOChronology.getInstance();
            for (Iterator<Integer> iterator = sortedSet.iterator(); iterator.hasNext();) {
                Integer day =  iterator.next();
                sb.append(c.dayOfWeek().getAsShortText(day, Locale.getDefault()));
                if (iterator.hasNext()) sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Transient
    public Boolean isValid() {
        return timeOpens.isBefore(timeCloses);
    }

    //Common

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperationHours)) return false;

        final OperationHours operationHours = (OperationHours) o;
        return new EqualsBuilder()
                .append(this.timeOpens, operationHours.timeOpens)
                .append(this.timeCloses, operationHours.timeCloses)
                .append(this.daysOfWeek, operationHours.daysOfWeek)
                .isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder(17,37)
                .append(this.timeOpens)
                .append(this.timeCloses)
                .append(this.daysOfWeek)
                .toHashCode();
    }
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append(this.timeOpens)
                .append(this.timeCloses)
                .append(this.daysOfWeek)
                .toString();
    }
}