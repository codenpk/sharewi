package org.sharewi.opt.service;

import org.appfuse.service.BaseManagerTestCase;
import org.appfuse.service.UserManager;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Sep 15, 2007
 * Time: 2:15:46 AM
 */
public class LocationManagerTest extends BaseManagerTestCase {

    private LocationManager locationManager = null;

    private UserManager userManager = null;

    //Setters

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    //Tests

    public void testDummy() throws Exception {
        assertEquals(2+2,4);
    }    

//    public void testIsSerializable() throws IOException {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(out);
//        oos.writeObject(userManager);
//        oos.close();
//        assertTrue(out.toByteArray().length > 0);
//    }
}
