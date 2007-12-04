package org.sharewi.opt.model.location;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Sep 15, 2007
 * Time: 2:04:35 AM
 */
public class CustomerTest extends TestCase {

    public void testIsSerializable() throws IOException {
        Customer customer = new Customer();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(customer);
        oos.close();
        assertTrue(out.toByteArray().length > 0);
    }

}
