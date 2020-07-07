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
        assertEquals(2045559999, customer0.getPhoneNumber());

        customer0.setFirstName("Annie");
        customer0.setLastName("Apple");
        customer0.setPhoneNumber("204-555-1111");
        assertNotNull(customer0);
        assertTrue("Annie".equals(customer0.getFirstName()));
        assertTrue("Apple".equals(customer0.getLastName()));
        assertTrue("Annie Apple".equals((customer0.getFullName())));
        assertEquals(2045551111, customer0.getPhoneNumber());

        Customer customer1 = new Customer("Judy", "Test", "204-414-0198");
        assertNotNull(customer1);
        assertFalse(customer0.equals(customer1));

        Customer customer2 = new Customer("Judy", "Test", "204-414-0198");
        assertNotNull(customer2);
        assertFalse(customer1.equals(customer2));

        Customer customer3 = null;
        try {
            customer3 = new Customer("", "Test", "123-456-7890");
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        assertNull(customer3);

        try {
            customer3 = new Customer("Test", "", "123-456-7890");
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        assertNull(customer3);

        try {
            customer3 = new Customer("Test", "Testing", "");
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        assertNull(customer3);

        try {
            customer3 = new Customer("Test", "Testing", "aaa-456-7890");
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        assertNull(customer3);

        try {
            customer3 = new Customer("Test3", "Testing", "123-456-7890");
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        assertNull(customer3);

        try {
            customer3 = new Customer("Test", "Testing6", "123-456-7890");
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        assertNull(customer3);

        try {
            customer3 = new Customer("Test", "Testing", "456-7890");
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        assertNull(customer3);
    }
}
