package org.sharewi.opt.webapp.calendar;

import org.joda.time.chrono.ISOChronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;
import org.sharewi.opt.model.event.Event;
import org.sharewi.opt.service.EventManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: pkatsev
 * Date: Nov 4, 2007
 * Time: 9:26:07 PM
 */
public class EventCalendarDataModel implements CalendarDataModel, Serializable {
    private static final long serialVersionUID = -4285992020998429888L;

    protected final Log log = LogFactory.getLog(getClass());

    private EventManager eventManager = null;


    //Constructor
    public EventCalendarDataModel(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    //Implemented Methods
    public CalendarDataModelItem[] getData(Date[] dateArray) {
        if (dateArray == null) return null;

        EventCalendarDataModelItem[] items = new EventCalendarDataModelItem[dateArray.length];
        for (int i = 0; i < dateArray.length; i++) {
            EventCalendarDataModelItem item = new EventCalendarDataModelItem();
            item.setDay(ISOChronology.getInstance().dayOfMonth().get(dateArray[i].getTime()));
            item.setEnabled(true);
            items[i] = item;
        }

        List<Event> events = getEvents(dateArray);
        log.debug("Events found: " + events.size());

        for (Event event : events) {
            int i = event.getDateTimeStart().withZone(DateTimeZone.getDefault()).getDayOfMonth() - 1;
            items[i].setStyleClass("rich-calendar-eventful");
        }

        return items;
    }

    public Object getToolTip(Date date) {
        return null;
    }


    //Convenience
    private List<Event> getEvents(Date[] dateArray) {
        DateTime lo = new DateTime(dateArray[0]);
        DateTime hi = new DateTime(dateArray[dateArray.length-1]).plusDays(1);
        log.debug("Querying for event: " + lo + " through " + hi);
        return eventManager.findByDateRange(lo, hi);
    }


//        Additional DataLoad
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("name", "her vam");
//        item.setData(data);
}