package org.sharewi.opt.dao;

import org.appfuse.dao.BaseDaoTestCase;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.sharewi.opt.model.location.Customer;
import org.sharewi.opt.model.location.Depot;
import org.springframework.dao.DataAccessException;

import java.util.*;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 9, 2007
 * Time: 9:02:26 PM
 */
public class LocationDaoTest extends BaseDaoTestCase {
    private LocationDao locationDao;


    //Setters

    public void setLocationDao(LocationDao locationDao) {
        this.locationDao = locationDao;
    }


    //Test Methods

    public void testGetLocationInvalid() throws Exception {
        try {
            locationDao.get(0L);
            fail("'badlocationname' found in database, failing test...");
        } catch (DataAccessException d) {
            assertTrue(d != null);
        }
    }

    public void testSaveOrUpdateAndRemoveCustomer() throws Exception {

        Customer customer = new Customer();
        customer.setHostSite("test host");
        customer.setDistSite("test dist");
        customer.setPhone("8472191048");

        customer.getAddress().setCity("Denver");
        customer.getAddress().setProvince("CO");
        customer.getAddress().setPostalCode("80210");
        customer.getAddress().setTimeZone(DateTimeZone.forID("US/Central"));

        customer.getGeocode().setLatitude(6.00);
        customer.getGeocode().setLongitude(9.04);

        customer.getOperationHours().setTimeOpens(new LocalTime(8,30));
        customer.getOperationHours().setTimeCloses(new LocalTime(15,30));
        customer.getOperationHours().addDayOfWeek(DateTimeConstants.MONDAY);
        customer.getOperationHours().addDayOfWeek(DateTimeConstants.TUESDAY);

        assertNotNull(customer);

        //Save
        customer = locationDao.saveLocation(customer);

        //Check persistance
        assertNotNull(customer.getId());
        log.warn("new customer id: " + customer.getId());

        customer = (Customer) locationDao.get(customer.getId());
        assertEquals(customer.getAddress().getCity(),"Denver");

        //Update
        customer.getAddress().setCity("Chicago");
        customer = locationDao.saveLocation(customer);

        assertNotNull(customer.getId());
        customer = (Customer) locationDao.get(customer.getId());
        assertEquals(customer.getAddress().getCity(),"Chicago");

        //Remove
        assertNotNull(customer.getId());
        locationDao.remove(customer.getId());
        flush();

        //Check existance
        try {
            locationDao.get(customer.getId());
            fail("getLocation didn't throw DataAccessException");
        } catch (DataAccessException d) {
            assertNotNull(d);
        }
    }

    public void testSaveOrUpdateAndRemoveDepot() throws Exception {

        Depot depot = new Depot();
        depot.setBusinessName("dummy");
        depot.setOperationsSite("test depot");
        depot.setPhone("8474143182");

        depot.getAddress().setCity("Chicago");
        depot.getAddress().setProvince("IL");
        depot.getAddress().setPostalCode("60089");
        depot.getAddress().setTimeZone(DateTimeZone.forID("US/Central"));

        depot.getGeocode().setLatitude(333.00);
        depot.getGeocode().setLongitude(444.00);

        depot.getOperationHours().setTimeOpens(new LocalTime(8,30));
        depot.getOperationHours().setTimeCloses(new LocalTime(15,30));
        depot.getOperationHours().addDayOfWeek(DateTimeConstants.MONDAY);
        depot.getOperationHours().addDayOfWeek(DateTimeConstants.TUESDAY);

        assertNotNull(depot);

        //Save
        depot = locationDao.saveLocation(depot);

        //Check persistance
        assertNotNull(depot.getId());
        log.warn("new depot id: " + depot.getId());

        depot = (Depot) locationDao.get(depot.getId());         //fetch from hib
        assertEquals(depot.getAddress().getCity(),"Chicago");

        //Remove
        assertNotNull(depot.getId());
        locationDao.remove(depot.getId());
        flush();

        //Check existance
        try {
            locationDao.get(depot.getId());
            fail("getLocation didn't throw DataAccessException");
        } catch (DataAccessException d) {
            assertNotNull(d);
        }
    }

    public void testFindLocationCRIT() throws Exception {
        Set<String> states = new HashSet<String>(Arrays.asList("IL","WI"));
        Set<String> cities = null;
        Set<String> postalCodes = null;

        List<Customer> results = locationDao.find(Customer.class, states, cities, postalCodes);
        assertNotNull(results);
        log.debug("Matches found: " + results.size());
    }
}