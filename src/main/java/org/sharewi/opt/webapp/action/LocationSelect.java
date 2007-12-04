package org.sharewi.opt.webapp.action;

import org.appfuse.webapp.action.BasePage;
import org.sharewi.opt.service.LocationManager;
import org.sharewi.opt.model.location.Location;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 28, 2007
 * Time: 1:07:30 AM
 */
public class LocationSelect extends BasePage implements Serializable {
    private LocationManager locationManager;

    private String idToAdd;
    private String idToRemove;

    private Set<Location> addedLocations;

    private List<String> testRepeat;
    private String testString;

    private static final long serialVersionUID = 3575661996128627321L;

    //Constructor
    public LocationSelect() {
        setSortColumn("hostSite");
        addedLocations = new HashSet<Location>();
    }

    //Setters
    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }
    public void setTestString(String testString) {
        this.testString = testString;
    }
    public void setIdToAdd(String idToAdd) {
        this.idToAdd = idToAdd;
    }
    public void setIdToRemove(String idToRemove) {
        this.idToRemove = idToRemove;
    }

    //Getters
    public String getTestString() {
        return testString;
    }
    public List getLocations() {
        return sort(locationManager.getAllLocations());
    }
    public Set<Location> getAddedLocations() {
        return addedLocations;
    }
    public List<String> getTestRepeat() {
        if (this.testRepeat == null) {
            this.testRepeat = new ArrayList<String>();
            this.testRepeat.add("masha");
            this.testRepeat.add("lena");
            this.testRepeat.add("katya");
            this.testRepeat.add("jane");
            this.testRepeat.add("katya");
        }
        return testRepeat;
    }
    public String getIdToAdd() {
        return idToAdd;
    }
    public String getIdToRemove() {
        return idToRemove;
    }

    //Methods
    public void testMethod() {
        this.testString = "test method ran successfully";
    }

    public void addLocation() {
        try {
            addedLocations.add(locationManager.get(new Long(idToAdd)));

        } catch (Exception e) {
            System.out.println("Couldn't add location: " + e.getMessage());
        }
    }
    public void removeLocation() {
        try {
        addedLocations.remove(locationManager.get(new Long(idToRemove)));
        } catch (Exception e) {
            System.out.println("Couldn't remove location: " + e.getMessage());
        }
    }

}
