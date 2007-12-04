package org.sharewi.opt.webapp.action.form;

import org.appfuse.webapp.action.BasePage;
import org.joda.time.LocalTime;
import org.sharewi.opt.model.location.Depot;
import org.sharewi.opt.model.location.Geocode;
import org.sharewi.opt.service.LocationManager;
import org.sharewi.opt.service.exceptions.LocationExistsException;

import javax.faces.application.FacesMessage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: pkatsev
 * Date: Apr 23, 2007
 * Time: 12:53:34 AM
 */

public class DepotForm extends BasePage implements Serializable {
    private static final long serialVersionUID = -6172011436756225832L;

    private Depot depot = null;                       //object
    private LocationManager locationManager = null;   //spring (c-arg)

    // Constructor (Spring-injected)
    public DepotForm(LocationManager locationManager) {
        this.locationManager = locationManager;

        String id = getParameter("id");
        if (id != null && depot == null) {
            log.debug("Fetching depot with id: [" + id + "]");

            try {
                depot = (Depot) locationManager.get(Long.valueOf(id));
            }
            catch (Exception e) {
                log.warn(e);
                try {
                    getResponse().sendRedirect("/404.jsp");
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            depot = new Depot();
        }

        log.debug("Created DepotForm object");
    }

    // Getters
    public Depot getDepot() {
        return depot;
    }

    // Setters
    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    // Action Methods

    public String save() {
        /*Set hours appropriately*/
        depot.getOperationHours().setTimeOpens(new LocalTime(hrOpens, minOpens));
        depot.getOperationHours().setTimeCloses(new LocalTime(hrCloses, minCloses));

        /*Custom Application-Level Validation*/
        if (!isValid()) {
            return "error";
        }

        /*Check whether depot is new*/
        boolean isNew = (depot.getId() == null);

        /*Check whether geocode changed*/
        boolean geoChanged = true;
        if (!isNew) {
            Geocode oldGeocode = locationManager.get(depot.getId()).getGeocode();
            Geocode newGeocode = depot.getGeocode();
            geoChanged = (!newGeocode.equals(oldGeocode));
        }

        /*Save or update location*/
        Integer originalVersion = depot.getVersion();
        try {
            depot = locationManager.saveLocation(depot);
        } catch (LocationExistsException e) {
            log.warn(e.getMessage());
            addError("errors.existing.location");

            depot.setVersion(originalVersion);
            return null;
        }

        /*Add appropriate message*/
        String msg = (isNew) ? "depot.added" : "depot.updated";
        addMessage(msg);

        if (geoChanged) {
            try {
                getResponse().sendRedirect("/pathFetch.html?type=depot&id=" + depot.getId());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }

    public String delete() {
        locationManager.remove(depot.getId());

        addMessage("depot.deleted");

        return "list";
    }

    // Validation Methods
    public Boolean isValid() {
        // Hours validation
        if (!depot.getOperationHours().isValid()) {
            FacesMessage msgTime = new FacesMessage();
            msgTime.setSeverity(FacesMessage.SEVERITY_ERROR);
            msgTime.setSummary("Erroneous Operation Hours");
            msgTime.setDetail("Opening Time is later than Closing Time");
            getFacesContext().addMessage("locationForm:distHoursErrors", msgTime);
            return false;
        }

        return true;
    }


    // UI Stuff
    /* workaround for plain ol' HTML input tags that don't seem to set properties on the managed bean */
    private Integer[] daysOfWeek;

    public Integer[] getDaysOfWeek() {
        Set<Integer> setOfDays = depot.getOperationHours().getDaysOfWeek();
        if (setOfDays != null) {
            daysOfWeek = new Integer[setOfDays.size()];
            setOfDays.toArray(daysOfWeek);
        }
        return daysOfWeek;
    }

    public void setDaysOfWeek(Integer[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;

        depot.getOperationHours().getDaysOfWeek().clear();

        if (daysOfWeek != null) {
            for (Integer distDay : daysOfWeek) {
                depot.getOperationHours().addDayOfWeek(distDay);
                log.debug(distDay);
            }
        }
    }

    private Integer hrOpens;
    private Integer minOpens;
    private Integer hrCloses;
    private Integer minCloses;

    public Integer getHrOpens() {
        if (depot.getOperationHours().getTimeOpens() == null) return null;
        return depot.getOperationHours().getTimeOpens().getHourOfDay();
    }

    public Integer getMinOpens() {
        if (depot.getOperationHours().getTimeOpens() == null) return null;
        return depot.getOperationHours().getTimeOpens().getMinuteOfHour();
    }

    public Integer getHrCloses() {
        if (depot.getOperationHours().getTimeCloses() == null) return null;
        return depot.getOperationHours().getTimeCloses().getHourOfDay();
    }

    public Integer getMinCloses() {
        if (depot.getOperationHours().getTimeCloses() == null) return null;
        return depot.getOperationHours().getTimeCloses().getMinuteOfHour();
    }

    public void setHrOpens(Integer hrOpens) {
        this.hrOpens = hrOpens;
    }

    public void setMinOpens(Integer minOpens) {
        this.minOpens = minOpens;
    }

    public void setHrCloses(Integer hrCloses) {
        this.hrCloses = hrCloses;
    }

    public void setMinCloses(Integer minCloses) {
        this.minCloses = minCloses;
    }
}