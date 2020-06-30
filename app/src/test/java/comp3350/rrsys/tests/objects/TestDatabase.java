package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

import comp3350.rrsys.objects.Database;

/* TODO:
 *  - Object: Customer
 *      - test add valid all fields to database
 *      - test add invalid to database
 *      - test add duplicate to database
 *      - test change name
 *      - test change phone number
 *      - test change number of people
 *  - Object: Reservation (RID)
 *      - test add new reservation all fields
 *      - test add new reservation missing fields
 *      - test add new reservation too many people
 *      - test add new reservation negative people
 *  - Object: Menu
 *      - tbd
 *  - Object: (Relationship) Customer has-a reservation
 *  - Object: (Relationship) Reservation has-a menu
 *  Later:
 *      - test remove exsisting customer from database
 *      - test remove non-existing customer from database
 *      - test remove existing RID from database
 *      - test remove non-existing RID from database
*/
public class TestDatabase extends TestCase {
    public TestDatabase(String arg0) { super(arg0); }

    public void testDatabase(){
        Database database;

        System.out.println("\nStarting testDatabase");

        database = new Database();

    }

}
