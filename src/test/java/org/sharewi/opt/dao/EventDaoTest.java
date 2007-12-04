package org.sharewi.opt.dao;

import org.appfuse.dao.BaseDaoTestCase;
import org.appfuse.dao.UserDao;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.sharewi.opt.model.event.Event;
import org.sharewi.opt.model.event.Fee;
import org.sharewi.opt.model.event.TruckCompany;
import org.sharewi.opt.model.location.Customer;
import org.sharewi.opt.model.location.Depot;
import org.sharewi.opt.model.location.Path;
import org.sharewi.opt.model.solution.Route;
import org.sharewi.opt.model.solution.Solution;
import org.springframework.dao.DataAccessException;


/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jul 27, 2007
 * Time: 3:38:46 PM
 */
public class EventDaoTest extends BaseDaoTestCase {
    private EventDao eventDao = null;
    private LocationDao locationDao = null;
    private PathDao pathDao = null;
    private GenericDaoHibernate<TruckCompany,Long> truckCompanyDao = null;
    private UserDao userDao = null;


    // Setters

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public void setLocationDao(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    public void setPathDao(PathDao pathDao) {
        this.pathDao = pathDao;
    }

    public void setTruckCompanyDao(GenericDaoHibernate<TruckCompany, Long> truckCompanyDao) {
        this.truckCompanyDao = truckCompanyDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    // Test Methods

    public void testGetEventsInvalid() throws Exception {
        try {
            eventDao.get(0L);
            fail("'badProjectId' found in database, failing test...");
        } catch (DataAccessException d) {
            assertTrue(d != null);
        }
    }

    public void testAddAndRemoveEvent() throws Exception {

        /*---------------------*/
        /* make a truck company*/
        /*---------------------*/
        TruckCompany truckCompany = new TruckCompany();
        truckCompany.setName("dummy truck company");
        truckCompany.setContact("john doe");
        truckCompany.setPhone("1234567890");
        truckCompany.setEmail("dummy@email.jjj");
        truckCompany.setNote("noted");
        truckCompany.getAddress().setStreet("123 Testing St");
        truckCompany.getAddress().setCity("Testtown");
        truckCompany.getAddress().setProvince("IL");
        truckCompany.getAddress().setPostalCode("12345");
        truckCompany.getAddress().setCountry("US");
        truckCompany.getAddress().setTimeZone(DateTimeZone.forID("US/Central"));
        truckCompany.addFee(new Fee("Test flat fee", 60.00, Fee.FLAT_FEE, "Testing flat fee"));
        truckCompany.addFee(new Fee("Test per mile", 3.00, Fee.PER_MILE, "Testing per mile fee"));
        truckCompany.addFee(new Fee("Test per hour", 10.00, Fee.PER_HOUR, "Testing per hour fee"));
        truckCompany.addFee(new Fee("Test per stop", 25.00, Fee.PER_STOP, "Testing per stop fee"));

        assertNotNull(truckCompany);
        truckCompany = truckCompanyDao.save(truckCompany);
        flush();
        assertNotNull(truckCompany.getId());


        /*--------------*/
        /* make a route */
        /*--------------*/
        Route route = new Route();
        assertFalse(pathDao.getAll().size() < 1);
        for (Path path : pathDao.getAll()) { route.addPath(path); }
        route.setTruckCompany(truckCompany);

        assertNotNull(route);


        /*--------------------*/
        /* make another route */
        /*--------------------*/
        Route route1 = new Route();
        assertFalse(pathDao.getAll().size() < 1);
        route1.addPath(pathDao.getAll().get(1));
        route1.setTruckCompany(truckCompany);

        assertNotNull(route1);


        /*-----------------*/
        /* make a solution */
        /*-----------------*/
        Solution solution = new Solution();
        solution.addRoute(route);
        solution.addRoute(route1);
        solution.setNote("Noted solution");

        assertNotNull(solution);


        /*----------------*/
        /* make a event */
        /*----------------*/
        Event event = new Event();

        event.setName("dummy event");
        event.setNote("Noted event");

        event.setDateTimeStart(new DateTime(2007,11,13,8,45,30,0, DateTimeZone.forID("US/Central")));

        event.addCustomer((Customer)locationDao.get(1L), 4, false);
        event.addCustomer((Customer)locationDao.get(2L), 10, true);

        event.addDepot((Depot) locationDao.get(3L));

        event.addSolution(solution);

        event.addTruckCompany(truckCompany);

        event.setAuthor(userDao.getAll().get(0));

        assertNotNull(event);



        /*-------------------*/
        /* play with event */
        /*-------------------*/

        // save
        event = eventDao.saveEvent(event);

        // check persistance
        assertNotNull(event.getId());
        log.warn("new event id: " + event.getId());

        for (Solution solution1 : event.getSolutions()) {
            log.debug(solution1);
            for (Route route2 : solution1.getRoutes()) {
                log.debug(route2);
            }
        }

        // remove
        assertNotNull(event.getId());
        eventDao.remove(event.getId());
        flush();

        // check existance
        try {
            eventDao.get(event.getId());
            fail("getEvent didn't throw DataAccessException");
        } catch (DataAccessException d) {
            assertNotNull(d);
        }

    }

    public void testFindByDateRange() throws Exception {
        for (Event event : eventDao.getAllEvents()){
            log.debug("Found event: " + event.getDateTimeStart());
        }
        DateTime start = new DateTime(2007,8,1,0,0,0,0, DateTimeZone.forOffsetHours(-6));
        DateTime end = new DateTime(2007,12,1,0,0,0,0, DateTimeZone.forOffsetHours(-6));

        log.debug("Now between: " + start + " and " + end);
        for (Event event : eventDao.findByDateRange(start, end)) {
            log.debug("Found event: " + event.getDateTimeStart());
        }
        LocalDate localDate = new LocalDate(2007,11,13);
        DateTimeZone tz = DateTimeZone.forOffsetHours(-6);
        log.debug("Now for date: " + localDate.toDateMidnight(tz));
        for (Event event : eventDao.findByLocalDate(localDate, tz)) {
            log.debug("Found event: " + event.getDateTimeStart());
        }
    }
}