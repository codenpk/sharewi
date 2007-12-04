package org.sharewi.opt.model.solution;

import org.sharewi.opt.model.location.Path;
import org.sharewi.opt.model.event.TruckCompany;
import org.appfuse.model.BaseObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.io.Serializable;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 29, 2007
 * Time: 3:16:35 AM
 */

@Entity
public class Route extends BaseObject implements Serializable {
    private static final long serialVersionUID = 9173815517290323294L;

    private Long id;
    private Solution solution = null;               //required

    private Set<Path> paths = null;
    private TruckCompany truckCompany = null;


    //Constructor

    public Route() {
        this.solution = new Solution();
        this.paths = new LinkedHashSet<Path>();
        this.truckCompany = new TruckCompany();
    }


    //Getters

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @ManyToOne(targetEntity = Solution.class)
    public Solution getSolution() {
        return solution;
    }

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public TruckCompany getTruckCompany() {
        return truckCompany;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Route_Path", inverseJoinColumns = { @JoinColumn(name = "path_id", nullable = false) } )
    public Set<Path> getPaths() {
        return paths;
    }


    //Setters

    public void setId(Long id) {
        this.id = id;
    }
    public void setSolution(Solution solution) {
        this.solution = solution;
    }
    public void setPaths(Set<Path> paths) {
        this.paths = paths;
    }
    public void setTruckCompany(TruckCompany truckCompany) {
        this.truckCompany = truckCompany;
    }


    //Convenience

    public void addPath(Path path) {
        this.paths.add(path);
    }
    public void removePath(Path path) {
        this.paths.remove(path);
    }


    //Utility

    public boolean equals(Object o) {
        if (this == o) return true;
        if ( !(o instanceof Route) ) return false;

        final Route other = (Route) o;
        return new EqualsBuilder().append(this.paths, other.paths).isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder(17,37).append(this.paths).toHashCode();
    }
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", this.id)
                .append("under solution id", this.solution.getId())
                .append("paths", this.paths)
                .append("served by truckCompany id", this.truckCompany.getId())
                .toString();
    }
}

