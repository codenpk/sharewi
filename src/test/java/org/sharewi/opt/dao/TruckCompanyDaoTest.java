package org.sharewi.opt.dao;

import org.appfuse.dao.BaseDaoTestCase;
import org.joda.time.DateTimeZone;
import org.sharewi.opt.model.event.Fee;
import org.sharewi.opt.model.event.TruckCompany;
import org.springframework.dao.DataAccessException;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Aug 29, 2007
 * Time: 4:13:24 AM
 */
public class TruckCompanyDaoTest extends BaseDaoTestCase {

    private TruckCompanyDao truckCompanyDao = null;


    //Setters

    public void setTruckCompanyDao(TruckCompanyDao truckCompanyDao) {
        this.truckCompanyDao = truckCompanyDao;
    }


    //Test Methods

    public void testGetLocationInvalid() throws Exception {
        try {
            truckCompanyDao.get(0L);
            fail("'badlocationname' found in database, failing test...");
        } catch (DataAccessException d) {
            assertTrue(d != null);
        }
    }

    public void testSaveOrUpdateAndRemoveTruckCompany() throws Exception {

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
        log.debug(truckCompany);

        //Save
        truckCompany = truckCompanyDao.saveTruckCompany(truckCompany);

        //Check persistance
        assertNotNull(truckCompany.getId());
        log.warn("new truckCompany id: " + truckCompany.getId());

        //getAllTruckCompanies
        assertFalse(truckCompanyDao.getAllTruckCompanies().size() < 1);


        truckCompany = truckCompanyDao.get(truckCompany.getId());
        assertEquals(truckCompany.getAddress().getCity(),"Testtown");

        //Update
        truckCompany.getFees().removeAll(truckCompany.getFees());

        assertNotNull(truckCompany.getId());

        truckCompany = truckCompanyDao.saveTruckCompany(truckCompany);
        assertEquals(truckCompany.getFees().size(), 0);

        //Remove
        assertNotNull(truckCompany.getId());
        truckCompanyDao.remove(truckCompany.getId());
        flush();

        //Check existance
        try {
            truckCompanyDao.get(truckCompany.getId());
            fail("getTruckCompany didn't throw DataAccessException");
        } catch (DataAccessException d) {
            assertNotNull(d);
        }

    }
}