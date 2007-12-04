package org.sharewi.opt.dao;

import org.appfuse.dao.GenericDao;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.sharewi.opt.model.event.Event;

import java.util.List;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jul 27, 2007
 * Time: 3:42:08 PM
 */
public interface EventDao extends GenericDao<Event, Long> {

    public Event saveEvent(Event event);

    public List<Event> getAllEvents();

    public List<Event> findByDate(DateTime dateTime);

    public List<Event> findByDateRange(DateTime start, DateTime end);
    
    public List<Event> findByLocalDate(LocalDate localDate, DateTimeZone timeZone);

}