package org.sharewi.opt.service;

import org.appfuse.service.GenericManager;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.DateTimeZone;
import org.sharewi.opt.model.event.Event;
import org.sharewi.opt.service.exceptions.EventExistsException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: pkatsev
 * Date: Nov 16, 2007
 * Time: 4:39:49 AM
 */
public interface EventManager extends GenericManager<Event, Long> {

    public Event saveEvent(Event event) throws EventExistsException;

    public List<Event> getAllEvents();

    public List<Event> findByDate(DateTime dateTime);

    public List<Event> findByDateRange(DateTime start, DateTime end);

    public List<Event> findByLocalDate(LocalDate localDate, DateTimeZone timeZone);

}