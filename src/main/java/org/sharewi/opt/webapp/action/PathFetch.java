package org.sharewi.opt.webapp.action;

import org.appfuse.webapp.action.BasePage;
import org.sharewi.opt.service.LocationManager;
import org.sharewi.opt.model.location.Location;

import java.io.Serializable;
import java.io.IOException;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 10, 2007
 * Time: 8:55:30 PM
 */
public class PathFetch extends BasePage implements Serializable {
    private static final long serialVersionUID = 6865561609025110019L;

    private LocationManager locationManager = null;     //spring(c-arg)

    private String id;                                  //url param
    private String type;                                //url param

    private Location location = null;               //object



    //Constructor (Spring-injected)

    public PathFetch(LocationManager locationManager) {
        this.locationManager = locationManager;

        this.id = getParameter("id");
        this.type = getParameter("type");

        if (id != null) {
            try {
                location = locationManager.get(Long.valueOf(id));
            } catch(Exception e) {
                try { getResponse().sendRedirect("/404.jsp"); }
                catch (IOException e1) { e1.printStackTrace(); }
            }
        }
    }


    //Getters

    public String getId() {
        return id;
    }
    public String getType() {
        return type;
    }

    public String getMenu() {
        if (type != null) {
            if ("customer".equals(type))    { return "CustomerMenu"; }
            if ("depot".equals(type))       { return "DepotMenu"; }
        }
        return null;
    }
    public Location getLocation() {
        return location;
    }


    //Setters

    public void setId(String id) {
        this.id = id;
    }
    public void setType(String type) {
        this.type = type;
    }


    //Navigation

    public String done() {
        if (type != null) {
            if ("customer".equals(type))    { return "customers"; }
            if ("depot".equals(type))       { return "depots"; }
        }
        return "mainMenu";
    }
}
