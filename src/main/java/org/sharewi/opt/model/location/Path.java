package org.sharewi.opt.model.location;

import org.appfuse.model.BaseObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Path extends BaseObject implements Serializable {
    private static final long serialVersionUID = 453749533078395061L;

    private Long id;
    private Location source;
    private Location destination;
    private Double dist;
    private Double time;


    //Gettters

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name="src_id", nullable = false)
    public Location getSource() {
        return source;
    }

    @ManyToOne
    @JoinColumn(name = "dst_id", nullable = false)
    public Location getDestination() {
        return destination;
    }

    @Column(nullable = false)
    public Double getDist() {
        return dist;
    }

    @Column(nullable = false)
    public Double getTime() {
        return time;
    }


    //Setters

    public void setId(Long id) {
        this.id = id;
    }
    public void setSource(Location source) {
        this.source = source;
    }
    public void setDestination(Location destination) {
        this.destination = destination;
    }
    public void setDist(Double dist) {
        this.dist = dist;
    }
    public void setTime(Double time) {
        this.time = time;
    }


    //Common

    public boolean equals(Object o) {
        if (this == o) return true;
        if ( !(o instanceof Path) ) return false;

        final Path other = (Path) o;

        return new EqualsBuilder()
                .append(this.source, other.source)
                .append(this.destination, other.destination)
                .append(this.time, other.time)
                .append(this.dist, other.dist)
                .isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder(17,37)
                .append(this.source)
                .append(this.destination)
                .append(this.time)
                .append(this.dist)
                .toHashCode();
    }
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("source: ", this.source.getId())
                .append("destination: ", this.destination.toString())
                .append("dist: ", this.dist.toString())
                .append("time: ", this.time.toString())
                .toString();
    }
}