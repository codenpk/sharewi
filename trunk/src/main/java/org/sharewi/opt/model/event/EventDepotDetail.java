package org.sharewi.opt.model.event;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.appfuse.model.BaseObject;
import org.hibernate.annotations.Parent;
import org.sharewi.opt.model.location.Depot;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;


/**
 * Class Creation info:
 * User: pkatsev
 * Date: Aug 28, 2007
 * Time: 5:57:17 PM
 */

@Embeddable
public class EventDepotDetail extends BaseObject implements Serializable {
    private static final long serialVersionUID = 3018638036811046680L;

    //Foreign keys
    private Event event;                    //parent
    private Depot depot;                    //child

    //Additional properties



    //Constructors

    public EventDepotDetail() {}
    public EventDepotDetail(Event event, Depot depot) {
        this.event = event;
        this.depot = depot;
    }

    //Getters

    @Parent
    public Event getEvent() {
        return event;
    }

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    public Depot getDepot() {
        return depot;
    }



    //Setters

    public void setEvent(Event event) {
        this.event = event;
    }
    public void setDepot(Depot depot) {
        this.depot = depot;
    }


    //Utility
    public boolean equals(Object o) {
        if (this == o) return true;
        if ( !(o instanceof EventDepotDetail) ) return false;

        final EventDepotDetail other = (EventDepotDetail) o;
        return new EqualsBuilder()
                .append(this.event, other.event)
                .append(this.depot,  other.depot)
                .isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder(17,37)
                .append(this.event)
                .append(this.depot)
                .toHashCode();
    }
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append(this.event.getId())
                .append(this.depot.getId())
                .toString();
    }
}