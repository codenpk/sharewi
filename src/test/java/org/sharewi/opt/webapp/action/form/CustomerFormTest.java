package org.sharewi.opt.webapp.action.form;

import org.appfuse.webapp.action.BasePageTestCase;
import org.sharewi.opt.service.LocationManager;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Sep 15, 2007
 * Time: 2:52:47 AM
 */
public class CustomerFormTest extends BasePageTestCase {
    private CustomerForm customerForm = null;
    private LocationManager locationManager = null;

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    @Override
    public void onSetUp() throws Exception {
        super.onSetUp();
        customerForm = new CustomerForm(locationManager);
        assertNotNull(customerForm);
    }

    @Override
    protected void onTearDown() throws Exception {
        super.onTearDown();
        customerForm = null;
    }

    public void testDummy() throws Exception {
        assertEquals(2+2,4);
    }

//    public void testIsSerializable() throws IOException {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(out);
//        oos.writeObject(customerForm);
//        oos.close();
//        assertTrue(out.toByteArray().length > 0);
//    }

//    public void testAdd() throws Exception {
//        assertEquals(customerForm.edit(), "edit");
//        assertFalse(customerForm.hasErrors());
//    }
//
//    public void testEdit() throws Exception {
//        assertEquals(customerForm.edit(), "edit");
//        assertFalse(customerForm.hasErrors());
//    }

//    public void testSave() throws Exception {
//        Customer customer = locationManager.getAllCustomers().get(1);
//        customer.setHostSite("tested by CustomerFormTest");
//        customer.getDaysOfWeek().clear();
//
//        customerForm.setCustomer(customer);
//        assertEquals(customerForm.save(), "list");
//        assertNotNull(customerForm.getCustomer());
//        assertFalse(customerForm.hasErrors());
//    }
//
//    public void testRemove() throws Exception {
//        Customer customer2delete = new Customer();
//        customer2delete.setId(1L);
//
//        customerForm.setCustomer(customer2delete);
//        assertEquals(customerForm.delete(), "list");
//        assertFalse(customerForm.hasErrors());
//    }

}
