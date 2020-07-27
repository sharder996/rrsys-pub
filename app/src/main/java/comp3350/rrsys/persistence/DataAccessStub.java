package comp3350.rrsys.persistence;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;

public class DataAccessStub implements DataAccess
{
    private String dbName;
    private String dbType = "stub";

    //private int customerID; // the customer ID of current customer (logged in)
    private ArrayList<Customer> customers;
    private ArrayList<Table> tables;
    private ArrayList<Reservation> reservations;
    private ArrayList<Item> menu;
    private ArrayList<Order> orders;

    public DataAccessStub(String dbName)
    {
        this.dbName = dbName;
    }

    public DataAccessStub()
    {
        this(Main.dbName);
    }

    public void open(String dbName)
    {
        customers = new ArrayList<Customer>();
        tables = new ArrayList<Table>();
        reservations = new ArrayList<Reservation>();
        menu = new ArrayList<Item>();
        orders = new ArrayList<Order>();

        generateFakeData();
    }

    public void close()
    {
        System.out.println("Closed " + dbType + " database " + dbName);
    }

    // return the index of a date time
    public int getIndex(DateTime time)
    {
        return (time.getHour()-Table.getStartTime())*4 + (time.getMinutes()+7)/15;
    }

    // return the date time corresponding to an index
    public DateTime getDateTime(DateTime time, int index)
    {
        DateTime result = null;
        try
        {
            result = new DateTime(Calendar.getInstance());
            result.setYear(time.getYear());
            result.setMonth(time.getMonth());
            result.setDate(time.getDate());
            result.setHour(Table.getStartTime() + index / 4);
            result.setMinutes(index % 4 * 15);
        }
        catch (IllegalArgumentException pe)
        {
            System.out.println(pe);
        }
        return result;
    }

    // ordered insert a suggested reservation into a temp array
    // ordered by how close to the startTime
    public void orderedInsert(ArrayList<Reservation> results, Reservation r, DateTime t)
    {
        int pos = 0;
        int max = results.size();
        while(pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) < Math.abs(r.getStartTime().getPeriod(t)))
            pos++;
        while(pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) == Math.abs(r.getStartTime().getPeriod(t)) && getTableRandom(results.get(pos).getTID()).getCapacity() < getTableRandom(r.getTID()).getCapacity())
            pos++;
        while(pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) == Math.abs(r.getStartTime().getPeriod(t)) &&
                getTableRandom(results.get(pos).getTID()).getCapacity() == getTableRandom(r.getTID()).getCapacity() && results.get(pos).getTID() < r.getTID())
            pos++;
        results.add(pos, r);
    }

    // insert a reservation
    public String insertReservation(Reservation r)
    {
        if(r == null || r.getEndTime() == null || r.getStartTime() == null || r.getNumPeople() < 0 || r.getTID() < 0){
            return "fail";
        }
        r.setRID();
        reservations.add(r);
        return "success";
    }

    // get a reservation
    // get a reservation by reservationID
    public Reservation getReservation(int reservationID)
    {
        Reservation result = null;
        for(int i = 0; i < reservations.size(); i++)
        {
            if(reservations.get(i).equals(reservationID))
            {
                result = reservations.get(i);
                break;
            }
        }
        return result;
    }

    // delete a reservation by reservation ID
    public String deleteReservation(int rID)
    {
        boolean found = false;
        for(int i = 0; i < reservations.size(); i++)
        {
            if (reservations.get(i).equals(rID))
            {
                DateTime start = reservations.get(i).getStartTime();
                DateTime end = reservations.get(i).getEndTime();
                reservations.remove(i);
                found = true;
                break;
            }
        }
        if(!found) {
            return "fail";
        }
        return "success";
    }

    // update a reservation with rID to curr
    public String updateReservation(int rID, Reservation curr)
    {
        if(curr.getNumPeople() < 0 || curr.getTID() < 0 ){
            return "fail";
        }
        for(int i = 0; i < reservations.size(); i++)
        {
            if (reservations.get(i).equals(rID))
            {
                Reservation prev = reservations.get(i);
                DateTime prevStart = prev.getStartTime();
                DateTime prevEnd = prev.getEndTime();
                DateTime currStart = curr.getStartTime();
                DateTime currEnd = curr.getEndTime();
                curr.setRID(prev.getRID());
                curr.setRID(prev.getRID());
                reservations.set(i, curr);
            }
        }
        return "success";
    }

    public String getReservationSequential(List<Reservation> reservationResult)
    {
        reservationResult.addAll(reservations);
        return null;
    }

    public String getTableSequential(ArrayList<Table> tableResult)
    {
        tableResult.addAll(tables);
        return null;
    }

    public Table getTableRandom(int tableID)
    {
        Table result = null;
        for(int i = 0; i < tables.size(); i++)
        {
            if(tables.get(i).equals(tableID))
            {
                result = tables.get(i);
                break;
            }
        }
        return result;
    }

    public String getCustomerSequential(List<Customer> customerResult)
    {
        customerResult.addAll(customers);
        return null;
    }

    //Adds a customer to list by object
    public String insertCustomer(Customer customer)
    {
        customers.add(customer);
        return null;
    }

    //Adds a customer by raw data type values
    public String insertCustomer(String firstName, String lastName, String phoneNumber)
    {
        try
        {
            customers.add(new Customer(firstName, lastName, phoneNumber));
            return null;
        }
        catch (IllegalArgumentException e)
        {
            return e.getMessage();
        }
    }

    //adds a new table object by raw data types
    public String addTable(int tableID, int size)
    {
        for (int i = 0; i < tables.size(); i++)
        {
            if(tableID == tables.get(i).getTID())
            {
                return "Error: Table with ID: " + tableID + " already exists.";
            }
        }
        tables.add(new Table(tableID, size));
        return null;
    }

    public void generateFakeData()
    {
        //generate tables in the restaurant.
        //assume there are 30 tables
        // Table ID will be 1 to 30.
        // 5 tables each for 2, 4, 6, 8, 10, 12 people, totally 30 tables
        int size = 2;
        for(int i = 1; i <= 30; i++)
        {
            addTable(i, size);
            if(i % 5 == 0)
                size += 2;
        }

        // generate items in menu
        //Item(String name, String type, String detail, double price)
        Item salad, sandwich, burger, main, dessert, drink;
        salad = new Item(1, "SPECIAL SALAD", "Salads", "romaine lettuce, arugula, red cabbage, carrot, red onion & toasted sunflower seeds.", 9.95);
        insertItem(salad);
        salad = new Item(2, "SPINACH SALAD", "Salads", "tender spinach leaves, toasted almonds, orange slices & tangy chutney dressing.", 10.95);
        insertItem(salad);
        salad = new Item(3, "KALE SALAD", "Salads", "kale, tomato, goat cheese, red onion, dried cranberries, pepitas, avocado & balsamic vinaigrette.", 10.95);
        insertItem(salad);
        salad = new Item(4, "CAESAR SALAD", "Salads", "romaine lettuce, creamy garlic dressing, croutons & grated parmesan cheese.", 10.95);
        insertItem(salad);
        salad = new Item(5, "ARUGULA SALAD", "Salads", "arugula, green peas, sugar snap peas, radish, feta cheese & agave basil vinaigrette.", 11.95);
        insertItem(salad);
        salad = new Item(6, "AVOCADO SALAD", "Salads", "avocado, brussels sprouts, radish, alfalfa sprouts, chickpeas, dried cranberries & pepitas.", 12.95);
        insertItem(salad);

        sandwich = new Item(7, "VEGETARIAN", "Sandwiches", "vegetable patty, hummus, cream cheese, alfalfa sprouts, tomato & cucumber.", 12.95);
        insertItem(sandwich);
        sandwich = new Item(8, "SAUSAGE", "Sandwiches", "whole sausage, peppers, onions, sauerkraut, chili, lettuce, tomato& BBQ sauce.", 13.95);
        insertItem(sandwich);
        sandwich = new Item(9, "BACON", "Sandwiches", "bacon, lettuce, creamy havarti cheese, ripe tomato & mayonnaise on toasted multi-grain.", 13.95);
        insertItem(sandwich);
        sandwich = new Item(10, "TOASTED TUNA", "Sandwiches", "beef, green onion, mushroom, cucumber, lettuce, ripe tomato & mayonnaise.", 13.95);
        insertItem(sandwich);
        sandwich = new Item(11, "ROAST CHICKEN", "Sandwiches", "oven roasted chicken, Stella’s cranberry sauce, lettuce & mayonnaise on multigrain.", 14.95);
        insertItem(sandwich);
        sandwich = new Item(12, "BEEF", "Sandwiches", "toasted tuna, celery, green onion, sourdough, dill pickle, lettuce tomato & mayonnaise.", 14.95);
        insertItem(sandwich);
        sandwich = new Item(13, "SMOKED SALMON", "Sandwiches", "smoked salmon, cream cheese, capers, lettuce & a squeeze of lemon on a flaky croissant.", 15.95);
        insertItem(sandwich);
        sandwich = new Item(14, "CLUB", "Sandwiches", "oven roasted chicken, crisp bacon, cheddar cheese, lettuce, tomato & mayonnaise.", 15.95);
        insertItem(sandwich);

        burger = new Item(15, "GARDON", "Burgers", "chickpea patty, alfalfa sprouts, tomato, mayonnaise, crispy onions, peach chutney & cilantro sauce.", 13.95);
        insertItem(burger);
        burger = new Item(16, "GARBANZO", "Burgers", "garbanzo patty, onion, aged cheddar, basil mayo, lettuce, pickle, tomato & mayonnaise.", 13.95);
        insertItem(burger);
        burger = new Item(17, "QUINOA", "Burgers", "quinoa, mayonnaise, caramelized onion, arugula, tomato, dill pickle & caesar dressing.", 13.95);
        insertItem(burger);
        burger = new Item(18, "CHICKEN", "Burgers", "marinated chicken breast, tomato, lettuce, mayonnaise, crispy onions & a dollop of cilantro sauce.", 14.95);
        insertItem(burger);
        burger = new Item(19, "BEEF", "Burgers", "beef patty, mayonnaise, caramelized onion, arugula, tomato, pickle & tomato relish.", 14.95);
        insertItem(burger);
        burger = new Item(20, "SALMON FILLET", "Burgers", "salmon fillet grilled with lemon & fresh parsley with lettuce, tomato & crispy onions.", 15.95);
        insertItem(burger);
        burger = new Item(21, "MUSHROOM", "Burgers", "beef patty with all of the fixings, garlic mushrooms, havarti cheese & mayonnaise.", 15.95);
        insertItem(burger);
        burger = new Item(22, "GUACAMOLE & BACON", "Burgers", "beef patty with all of the fixings, two strips of bacon & scoop of guacamole.", 16.95);
        insertItem(burger);

        main = new Item(23, "RATATOUILLE", "Mains", "oven roasted zucchini, eggplant, tomato, peppers, onion, garlic & fresh herbs over steamed quinoa.", 14.95);
        insertItem(main);
        main = new Item(24, "PAD THAI", "Mains", "rice noodles, mushrooms, red peppers, cabbage, snap peas & bean sprouts in sweet soy tamarind sauce.", 14.95);
        insertItem(main);
        main = new Item(25, "CREAM LINGUINE", "Mains", "spinach, mushrooms, eggplant, tomato, red pepper & linguine in pesto cream sauce.", 15.95);
        insertItem(main);
        main = new Item(26, "DRAGON BOWL", "Mains", "spicy chili garlic tamarind sauce, eggplant, mushrooms & cabbage over quinoa and brown basmati rice.", 14.95);
        insertItem(main);
        main = new Item(27, "VEGGIE LASAGNA", "Mains", "mushrooms, zucchini, spinach, asiago, mozzarella, cottage cheese & Stella's marinara sauce.", 15.95);
        insertItem(main);
        main = new Item(28, "EGGPLANT PARMESAN", "Mains", "parmesan crusted eggplant slices with linguine, roasted garlic marinara & sourdough garlic toast.", 15.95);
        insertItem(main);
        main = new Item(29, "FISH TACOS", "Mains", "three corn tortillas, crispy cod fillets, avocado, black beans, cilantro sauce & spicy curtido.", 15.95);
        insertItem(main);
        main = new Item(30, "JAMBALAYA", "Mains", "chorizo sausage, shrimp, chicken, onion, tomato, garlic, peppers, celery, rice & special sause.", 16.95);
        insertItem(main);

        dessert = new Item(31, "CHOCOLATE CAKE", "Desserts", "double-stacked dark chocolate cake frosted with chocolate icing.", 8.00);
        insertItem(dessert);
        dessert = new Item(32, "CHOCOLATE TORTE", "Desserts", "velvety, dark chocolate flourless cake topped with raspberry sauce & whipped cream.", 8.00);
        insertItem(dessert);
        dessert = new Item(33, "CARROT CAKE", "Desserts", "two layers of delicious coconut carrot cake with vanilla cream cheese icing.", 8.00);
        insertItem(dessert);
        dessert = new Item(34, "CHEESECAKE", "Desserts", "half chocolate, half vanilla cheesecake with a chocolate cookie crust.", 8.00);
        insertItem(dessert);
        dessert = new Item(35, "APPLE CROSTADA", "Desserts", "fresh Chudleigh Farms heirloom apples & drizzled with caramel.", 8.00);
        insertItem(dessert);
        dessert = new Item(36, "PECAN BLONDIE", "Desserts", "nuts, topped with ice cream, glazed pecans & maple-flavored cream cheese sauce", 9.00);
        insertItem(dessert);

        drink = new Item(37, "PINK LEMONADE", "Drinks", "non-alcoholic lemon juice & a splash of grenadine.", 7.00);
        insertItem(drink);
        drink = new Item(38, "FRUIT PUNCH", "Drinks", "cranberry, orange, and pineapple juice, sour mix and a splash of grenadine.", 7.00);
        insertItem(drink);
        drink = new Item(39, "SHIRLEY TEMPLE","Drinks", "orange juice & ginger ale with a splash of grenadine.", 7.00);
        insertItem(drink);
        drink = new Item(40, "BLUE HAWAIIAN","Drinks", "malibu rum, blue curacao, pineapple juice with a slice of lemon.", 8.00);
        insertItem(drink);
        drink = new Item(41, "CAESAR", "Drinks", "vodka & a mixture of seasoning served in true sarnia style  with a dill pickle", 8.00);
        insertItem(drink);
        drink = new Item(42, "MOJITO", "Drinks", "malibu rum, suger water, sprite and served with fresh mint leaves and slice of lime.", 8.00);
        insertItem(drink);
        drink = new Item(43, "LONG ISLAND ICE TEA", "Drinks", "vodka, rum, gin, tequila, triple sec, coke, sour mix & a slice of lemon.", 9.00);
        insertItem(drink);
        drink = new Item(44, "MARGARITA", "Drinks", "tequila, lime juice with salted rimmed glass & a slice of lemon.", 9.00);
        insertItem(drink);

        //generate customer informations
        //assume there are 100 customers.
        Random rand = new Random();
        String name = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String randomfirstName = "";
        String randomlastName = "";

        int length = 4;

        for(int k = 1000; k <1100; k++)
        { // k is last four digits in phone number. (100 customers)
            ///////////////////////////////////////////////////////////////////////////
            // generate random names
            char[] first = new char[length];
            char[] last = new char[length + 2];

            for (int i = 0; i < length; i++)
                first[i] = name.charAt(rand.nextInt(name.length()));

            for (int i = 0; i < length; i++)
                last[i] = name.charAt(rand.nextInt(name.length()));

            for (int i = 0; i < first.length; i++)
                randomfirstName += first[i];

            for (int i = 0; i < last.length; i++)
                    randomlastName += last[i];

            ///////////////////////////////////////////////////////////////////////////
            // generate random phone numbers
            int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
            int num2 = rand.nextInt(743);
            int num3 = rand.nextInt(10000);

            DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
            DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros

            String phoneNumber = df3.format(num1) + "-" + df3.format(num2) + "-" + df4.format(num3);
            insertCustomer(randomfirstName, randomlastName, phoneNumber);
        }
    }

    public String insertItem(Item newItem)
    {
        menu.add(newItem);
        return null;
    }

    public ArrayList<Item> getMenuByType(String type)
    {
        ArrayList<Item> items = new ArrayList<>();
        for(int i = 0; i < menu.size(); i++)
        {
            if(menu.get(i).getType().equals(type))
                items.add(menu.get(i));
        }
        return items;
    }

    public ArrayList<String> getMenuTypes(){
        ArrayList<String> types = new ArrayList<>();

        types.add("Salads");
        types.add("Sandwiches");
        types.add("Burgers");
        types.add("Mains");
        types.add("Desserts");
        types.add("Drinks");

        return types;
    }

    public ArrayList<Item> getMenu()
    {
        return menu;
    }
    public boolean[] getAvailable(int TID, DateTime time) {
        boolean[] available = new boolean[Table.getNumIncrement()];
        for(int i = 0; i < available.length; i++)
            available[i] = true;
        for(int i = 0; i < reservations.size(); i++) {
            if(reservations.get(i).getTID() == TID && reservations.get(i).getStartTime().getYear() == time.getYear() && reservations.get(i).getStartTime().getMonth() == time.getMonth() && reservations.get(i).getStartTime().getDate() == time.getDate()) {
                int startIndex = getIndex(reservations.get(i).getStartTime());
                int endIndex = getIndex(reservations.get(i).getEndTime());
                for(int j = startIndex; j < endIndex; j++)
                    available[j] = false;
            }
        }
        return available;
    }
}
