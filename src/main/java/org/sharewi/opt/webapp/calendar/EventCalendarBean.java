package org.sharewi.opt.webapp.calendar;

import org.appfuse.webapp.action.BasePage;
import org.joda.time.DateTimeZone;
import org.richfaces.component.UICalendar;
import org.richfaces.component.html.HtmlCalendar;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: pkatsev
 * Date: Nov 4, 2007
 * Time: 9:34:57 PM
 */
public class EventCalendarBean extends BasePage implements Serializable {
    private static final long serialVersionUID = -6642320897672049454L;

    private Date selectedDate;

    private HtmlCalendar calendar = null;               //jsf


    //Constructor
    public EventCalendarBean() {
        this.calendar = new HtmlCalendar();
        this.calendar.setPopup(false);
        this.calendar.setMode(UICalendar.AJAX_MODE);
        this.calendar.setToolTipMode("none");

        this.calendar.setLocale(Locale.US);  //todo timezone centralization (user?)
        this.calendar.setTimeZone(DateTimeZone.getDefault().toTimeZone());
        this.calendar.setDatePattern("MMM d, yyyy");
    }


    //Getters
    public HtmlCalendar getCalendar() {
        return calendar;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }


    //Setters
    public void setCalendar(HtmlCalendar calendar) {
        this.calendar = calendar;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }


    //Action Methods
    public void listEvents() {
        StringBuffer urlBuilder = new StringBuffer("/lists/events.html");

        if (selectedDate != null) {
            urlBuilder.append("?date=");
            urlBuilder.append(new SimpleDateFormat("yyyy-MM-dd").format(selectedDate));  //todo timezone handling
        }

        try {
            getFacesContext().getExternalContext().redirect(urlBuilder.toString());
        }
        catch (IOException e) {
            log.debug(e.getMessage());
        }
    }
}