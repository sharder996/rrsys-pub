package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

import comp3350.rrsys.objects.Customer;

import static org.junit.Assert.assertNotEquals;

public class TestCustomer extends TestCase
{
    public TestCustomer(String arg0) { super(arg0); }

    public void testCustomerCreation()
    {
        System.out.println("\nStarting testCustomerCreation");

        Customer customer = new Customer("Ivan", "Barbashev", "204-555-9999");

        assertNotNull(customer);
        assertEquals("Ivan", customer.getFirstName());
        assertEquals("Barbashev", customer.getLastName());
        assertEquals("Ivan Barbashev", customer.getFullName());
        assertNotEquals("204-555-9999", Integer.toString(customer.getPhoneNumber()));
        assertEquals(2045559999, customer.getPhoneNumber());

        customer.setFirstName("Annie");
        customer.setLastName("Apple");
        customer.setPhoneNumber("204-555-1111");

        assertNotNull(customer);
        assertEquals("Annie", customer.getFirstName());
        assertEquals("Apple", customer.getLastName());
        assertEquals("Annie Apple", customer.getFullName());
        assertEquals(2045551111, customer.getPhoneNumber());

        System.out.println("\nEnding testCustomerCreation");
    }

    public void testCustomerNotEquals()
    {
        System.out.println("\nStarting testCustomerNotEquals");

        Customer customer0 = new Customer("Ivan", "Barbashev", "204-555-9999");
        Customer customer1 = new Customer("Judy", "Test", "204-414-0198");
        Customer customer2 = new Customer("Judy", "Test", "204-414-0198");

        assertNotNull(customer1);
        assertFalse(customer0.equals(customer1));

        assertNotNull(customer2);
        assertFalse(customer1.equals(customer2));

        System.out.println("\nEnding testCustomerNotEquals");
    }

    public void testCustomerEmptyParameters()
    {
        System.out.println("\nStarting testCustomerEmptyParameters");

        Customer customer = null;
        try
        {
            customer = new Customer("", "Test", "123-456-7890");
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(customer);
        }

        try
        {
            customer = new Customer("Test", "", "123-456-7890");
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(customer);
        }

        try
        {
            customer = new Customer("Test", "Testing", "");
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(customer);
        }

        System.out.println("\nEnding testCustomerEmptyParameters");
    }

    public void testCustomerAlphanumericPhoneNumber()
    {
        System.out.println("\nStarting testCustomerAlphanumericPhoneNumber");

        Customer customer = null;
        try
        {
            customer = new Customer("Test", "Testing", "aaa-456-7890");
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(customer);
        }

        System.out.println("\nEnding testCustomerAlphanumericPhoneNumber");
    }

    public void testCustomerAlphanumericName()
    {
        System.out.println("\nStarting testCustomerAlphanumericName");

        Customer customer = null;
        try
        {
            customer = new Customer("Test3", "Testing", "123-456-7890");
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(customer);
        }

        try
        {
            customer = new Customer("Test", "Testing6", "123-456-7890");
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(customer);
        }

        System.out.println("\nEnding testCustomerAlphanumericName");
    }

    public void testCustomerInvalidPhoneNumberLength()
    {
        System.out.println("\nStarting testCustomerInvalidPhoneNumberLength");

        Customer customer = null;
        try
        {
            customer = new Customer("Test", "Testing", "456-7890");
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(customer);
        }
        System.out.println("\nEnding testCustomerInvalidPhoneNumberLength");
    }
}
