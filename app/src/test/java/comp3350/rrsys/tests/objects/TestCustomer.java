package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

import comp3350.rrsys.objects.Customer;
/* TODO:
 *      - Add more test cases for invalid data
 */
public class TestCustomer extends TestCase {
    public TestCustomer(String arg0) { super(arg0); }

    public void testCustomer()
    {
        Customer customer0;

        System.out.println("\nStarting TestCustomer");

        customer0 = new Customer("Ivan", "Barbashev", "204-555-9999");
        assertNotNull(customer0);
        assertTrue("Ivan".equals(customer0.getFirstName()));
        assertTrue("Barbashev".equals(customer0.getLastName()));
        assertTrue("Ivan Barbashev".equals((customer0.getFullName())));
        assertFalse("204-555-9999".equals(Integer.toString(customer0.getPhoneNumber())));
        assertTrue(2045559999 == customer0.getPhoneNumber());

        customer0.setFirstName("Annie");
        customer0.setLastName("Apple");
        customer0.setPhoneNumber("204-555-1111");
        assertNotNull(customer0);
        assertTrue("Annie".equals(customer0.getFirstName()));
        assertTrue("Apple".equals(customer0.getLastName()));
        assertTrue("Annie Apple".equals((customer0.getFullName())));
        assertTrue(2045551111 == customer0.getPhoneNumber());
    }
}
