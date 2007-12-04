package org.sharewi.opt.service.impl;

import org.appfuse.service.impl.GenericManagerImpl;
import org.sharewi.opt.dao.EventDao;
import org.sharewi.opt.model.event.Event;
import org.sharewi.opt.service.EventManager;
import org.sharewi.opt.service.exceptions.EventExistsException;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.DateTimeZone;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: pkatsev
 * Date: Nov 16, 2007
 * Time: 4:31:19 PM
 */
public class EventManagerImpl extends GenericManagerImpl<Event, Long> implements EventManager {

    private EventDao eventDao;

    public EventManagerImpl(EventDao eventDao) {
        super(eventDao);
        this.eventDao = eventDao;
    }

    public Event saveEvent(Event event) throws EventExistsException {
        return eventDao.saveEvent(event);
    }

    public List<Event> getAllEvents() {
        return eventDao.getAllEvents();
    }

    public List<Event> findByDate(DateTime dateTime) {
        return eventDao.findByDate(dateTime);
    }

    public List<Event> findByDateRange(DateTime start, DateTime end) {
        return eventDao.findByDateRange(start, end);
    }

    public List<Event> findByLocalDate(LocalDate localDate, DateTimeZone timeZone) {
        return eventDao.findByLocalDate(localDate, timeZone);
    }

}