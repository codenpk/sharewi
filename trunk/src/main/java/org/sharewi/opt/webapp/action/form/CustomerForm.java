package org.sharewi.opt.webapp.action.form;

import org.appfuse.webapp.action.BasePage;
import org.joda.time.LocalTime;
import org.sharewi.opt.model.location.Customer;
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
public class CustomerForm extends BasePage implements Serializable {
    private static final long serialVersionUID = 9154566658272508593L;

    private LocationManager locationManager = null;     //spring (c-arg)
    private Customer customer = null;                   //object (subject)

    // Constructor (Spring-injected)
    public CustomerForm(LocationManager locationManager) {
        this.locationManager = locationManager;

        String id = getParameter("id");
        if (id != null && customer == null) {
            log.debug("Fetching customer with id: [" + id + "]");

            try {
                customer = (Customer) locationManager.get(Long.valueOf(id));
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
            customer = new Customer();
        }

        log.debug("Created CustomerForm object");
    }


    // Getters
    public Customer getCustomer() {
        return customer;
    }

    // Setters
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    // Action Methods
    public String save() {
        /*Set hours appropriately*/
        customer.getOperationHours().setTimeOpens(new LocalTime(hrOpens, minOpens));
        customer.getOperationHours().setTimeCloses(new LocalTime(hrCloses, minCloses));

        /*Custom Application-Level Validation*/
        if (!isValid()) {
            return "error";
        }

        /*Check whether customer is new*/
        boolean isNew = (customer.getId() == null);

        /*Check whether geocode changed*/
        boolean geoChanged = true;
        if (!isNew) {
            Geocode oldGeocode = locationManager.get(customer.getId()).getGeocode();
            Geocode newGeocode = customer.getGeocode();
            geoChanged = (!newGeocode.equals(oldGeocode));
        }

        /*Save or update location*/
        Integer originalVersion = customer.getVersion();

        try {
            customer = locationManager.saveLocation(customer);
        } catch (LocationExistsException e) {
            log.warn(e.getMessage());
            addError("errors.existing.location");

            customer.setVersion(originalVersion);
            return null;
        }

        /*Add appropriate message*/
        String msg = (isNew) ? "customer.added" : "customer.updated";
        addMessage(msg);

        if (geoChanged) {
            try {
                getResponse().sendRedirect("/pathFetch.html?type=customer&id=" + customer.getId());
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
        locationManager.remove(customer.getId());

        addMessage("customer.deleted");

        return "list";
    }


    // Validation Methods
    public Boolean isValid() {
        // Hours validation
        if (!customer.getOperationHours().isValid()) {
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
        Set<Integer> setOfDays = customer.getOperationHours().getDaysOfWeek();
        if (setOfDays != null) {
            daysOfWeek = new Integer[setOfDays.size()];
            setOfDays.toArray(daysOfWeek);
        }
        return daysOfWeek;
    }
    public void setDaysOfWeek(Integer[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;

        customer.getOperationHours().getDaysOfWeek().clear();

        if (daysOfWeek != null) {
            for (Integer distDay : daysOfWeek) {
                customer.getOperationHours().addDayOfWeek(distDay);
                log.debug(distDay);
            }
        }
    }

    private Integer hrOpens;
    private Integer minOpens;
    private Integer hrCloses;
    private Integer minCloses;

    public Integer getHrOpens() {
        if (customer.getOperationHours().getTimeOpens() == null) return null;
        return customer.getOperationHours().getTimeOpens().getHourOfDay();
    }
    public Integer getMinOpens() {
        if (customer.getOperationHours().getTimeOpens() == null) return null;
        return customer.getOperationHours().getTimeOpens().getMinuteOfHour();
    }
    public Integer getHrCloses() {
        if (customer.getOperationHours().getTimeCloses() == null) return null;
        return customer.getOperationHours().getTimeCloses().getHourOfDay();
    }
    public Integer getMinCloses() {
        if (customer.getOperationHours().getTimeCloses() == null) return null;
        return customer.getOperationHours().getTimeCloses().getMinuteOfHour();
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