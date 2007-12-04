package org.sharewi.opt.model.solution;

import org.appfuse.model.BaseObject;
import org.sharewi.opt.model.event.Event;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.LinkedHashSet;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 29, 2007
 * Time: 3:17:16 AM
 * Note: Solution class represents a single instance of a solution for a particular event
 */

@Entity
public class Solution extends BaseObject implements Serializable {
    private static final long serialVersionUID = 6902067413627588824L;

    private Long id;

    /* event this solutions belongs to */
    private Event event = null;

    /* routes contained in this solution */
    private Set<Route> routes = null;

    /* a note about this solution */
    private String note;


    //Constructor

    public Solution() {
        event = new Event();
        routes = new LinkedHashSet<Route>();
    }


    //Getters

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @ManyToOne(targetEntity = Event.class)
    public Event getEvent() {
        return event;
    }

    @OneToMany(targetEntity = Route.class, cascade = CascadeType.ALL)
    @org.hibernate.annotations.Cascade( value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN )
    @JoinTable(name = "Solution_Route")
    public Set<Route> getRoutes() {
        return routes;
    }

    @Column
    public String getNote() {
        return note;
    }


    //Setters

    public void setId(Long id) {
        this.id = id;
    }
    public void setEvent(Event event) {
        this.event = event;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }
    public void setNote(String note) {
        this.note = note;
    }


    //Convenience

    public void addRoute(Route route) {
        route.setSolution(this);
        this.routes.add(route);
    }
    public void removeRoute(Route route) {
        this.routes.remove(route);
    }


    //Utility

    public boolean equals(Object o) {
        if (this == o) return true;
        if ( !(o instanceof Solution)) return false;

        final Solution other = (Solution) o;
        return new EqualsBuilder()
                .append(this.routes, other.routes)
                .isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder(17,37)
                .append(this.routes)
                .toHashCode();
    }
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id",this.id)
                .append("under event id", this.event.getId())
                .append("with route id", this.routes)
                .append("note", this.note)
                .toString();
    }
}