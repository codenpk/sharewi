package org.sharewi.opt.util;

import org.sharewi.opt.model.location.Geocode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 10, 2007
 * Time: 3:30:58 PM
 */
public class PathDwrWrapper implements Serializable {
    private static final long serialVersionUID = -7269588423989373568L;

    private Long id;

    private Long srcId;
    private Long dstId;
    private Geocode srcGeocode;
    private Geocode dstGeocode;
    private Double dist;
    private Double time;

    //Constructors

    public PathDwrWrapper() {}

    public PathDwrWrapper(Long id, Long srcId, Long dstId,
                          Geocode srcGeocode, Geocode dstGeocode,
                          Double dist, Double time) {
        this.id = id;
        this.srcId = srcId;
        this.dstId = dstId;
        this.srcGeocode = srcGeocode;
        this.dstGeocode = dstGeocode;
        this.dist = dist;
        this.time = time;
    }


    //Getters

    public Long getId() {
        return id;
    }

    public Long getSrcId() {
        return srcId;
    }

    public Long getDstId() {
        return dstId;
    }

    public Geocode getSrcGeocode() {
        return srcGeocode;
    }

    public Geocode getDstGeocode() {
        return dstGeocode;
    }

    public Double getDist() {
        return dist;
    }

    public Double getTime() {
        return time;
    }


    //Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setSrcId(Long srcId) {
        this.srcId = srcId;
    }

    public void setDstId(Long dstId) {
        this.dstId = dstId;
    }

    public void setSrcGeocode(Geocode srcGeocode) {
        this.srcGeocode = srcGeocode;
    }

    public void setDstGeocode(Geocode dstGeocode) {
        this.dstGeocode = dstGeocode;
    }

    public void setDist(Double dist) {
        this.dist = dist;
    }

    public void setTime(Double time) {
        this.time = time;
    }


    //Common

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", this.id)
                .append("srcId", this.srcId)
                .append("dstId", this.dstId)
                .append("srcGeocode", this.srcGeocode)
                .append("dstGeocode", this.dstGeocode)
                .append("dist", this.dist)
                .append("time", this.time)
                .toString();
    }
}
