package org.sharewi.opt.dao.hibernate;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.DateTimeZone;
import org.joda.time.DateMidnight;
import org.sharewi.opt.dao.EventDao;
import org.sharewi.opt.model.event.Event;

import java.util.List;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jul 27, 2007
 * Time: 3:47:14 PM
 */
public class EventDaoHibernate extends GenericDaoHibernate<Event, Long> implements EventDao {


    //Constructor
    public EventDaoHibernate() { super(Event.class); }


    //Override Methods
    @Override
    public Event save(Event event) {
        return this.saveEvent(event);
    }

    public Event saveEvent(Event event) {
        log.debug("event's id: " + event.getId());

        getHibernateTemplate().saveOrUpdate(event);
        getHibernateTemplate().flush();

        return event;
    }


    //Queries
    @SuppressWarnings("unchecked")
    public List<Event> getAllEvents() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Event.class);
        criteria.addOrder(Order.asc("dateTimeStart.millis"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    public List<Event> findByDateRange(DateTime start, DateTime end) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Event.class);
        criteria.add(Restrictions.ge("dateTimeStart.millis", start.getMillis()));
        criteria.add(Restrictions.lt("dateTimeStart.millis", end.getMillis()));
        criteria.addOrder(Order.asc("dateTimeStart.millis"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getHibernateTemplate().findByCriteria(criteria);
    }

    public List<Event> findByLocalDate(LocalDate localDate, DateTimeZone timeZone) {
        DateMidnight lo = localDate.toDateMidnight(timeZone);
        DateMidnight hi = lo.plusDays(1);
        return findByDateRange(lo.toDateTime(), hi.toDateTime());
    }

    @SuppressWarnings("unchecked")
    public List<Event> findByDate(DateTime dateTime) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Event.class);
        criteria.add(Restrictions.eq("dateTimeStart.millis", dateTime.getMillis()));
        criteria.addOrder(Order.asc("dateTimeStart.millis"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getHibernateTemplate().findByCriteria(criteria);
    }
}