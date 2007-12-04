package org.sharewi.opt.webapp.action.list;

import org.appfuse.webapp.action.BasePage;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.sharewi.opt.model.event.Event;
import org.sharewi.opt.service.EventManager;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: pkatsev
 * Date: Nov 19, 2007
 * Time: 6:21:52 PM
 */
public class EventList extends BasePage implements Serializable {
    private static final long serialVersionUID = -5052044634455755018L;

    private EventManager eventManager = null;           //spring c-arg
    private Date forDate = null;


    //Constructor
    public EventList(EventManager eventManager) {
        this.eventManager = eventManager;

        String dateParam = getParameter("date");
        if (dateParam != null) {
            try {
                forDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateParam);
            }
            catch (ParseException e) {
                log.debug(e.getMessage());
            }
        }

        setSortColumn("id");
        setAscending(true);
    }


    //Getters
    public List getEvents() {
        List<Event> events;

        if (forDate != null) {                                 //todo timezone handling centralized
            events = eventManager.findByLocalDate(new LocalDate(forDate), DateTimeZone.getDefault());
            return sort(events);
        }

        events = eventManager.getAllEvents();
        return sort(events);
    }

    public Date getForDate() {
        return forDate;
    }


    //Setters
    public void setForDate(Date forDate) {
        this.forDate = forDate;
    }


    //UI
    public String getForDateAsString(){
        return new SimpleDateFormat("MMMM d, yyyy").format(forDate);
    }
}