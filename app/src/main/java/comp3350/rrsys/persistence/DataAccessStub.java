package comp3350.rrsys.persistence;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;

public class DataAccessStub implements DataAccess
{
    private String dbName;
    private static String dbType = "stub";

    private ArrayList<Reservation> reservations;
    private ArrayList<Table> tables;
    private ArrayList<Customer> customers;
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
        customers = new ArrayList<>();
        tables = new ArrayList<>();
        reservations = new ArrayList<>();
        menu = new ArrayList<>();
        orders = new ArrayList<>();

        generateFakeData();
    }

    public void close() { System.out.println("Closed " + dbType + " database " + dbName); }

    // Reservation Functions:
    public String getReservationSequential(ArrayList<Reservation> reservationResult)
    {
        reservationResult.addAll(reservations);
        return null;
    }

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

    // insert a reservation
    public String insertReservation(Reservation r)
    {
        if(r == null || r.getEndTime() == null || r.getStartTime() == null || r.getNumPeople() < 0 || r.getTID() < 0 || r.getCID() < 0)
            return "fail";

        if(r.getEndTime().getDate() != r.getStartTime().getDate() || r.getEndTime().getHour() - r.getStartTime().getHour() > 3)
            return "fail";

        r.setRID(getNextReservationID());
        reservations.add(r);

        return "success";
    }

    // update a reservation with rID to curr
    public String updateReservation(int rID, Reservation curr)
    {
        if(curr.getNumPeople() < 0 || curr.getTID() < 0 )
            return "fail";

        for(int i = 0; i < reservations.size(); i++)
        {
            if (reservations.get(i).equals(rID))
            {
                Reservation prev = reservations.get(i);
                curr.setRID(prev.getRID());
                curr.setRID(prev.getRID());
                reservations.set(i, curr);
            }
        }
        return "success";
    }

    // delete a reservation by reservation ID
    public String deleteReservation(int rID)
    {
        boolean found = false;
        for(int i = 0; i < reservations.size(); i++)
        {
            if(reservations.get(i).equals(rID))
            {
                reservations.remove(i);
                found = true;
                break;
            }
        }
        if(!found)
            return "fail";

        return "success";
    }

    // get next reservationID for creating a new reservation
    public int getNextReservationID()
    {
        int maxResID = 0;
        if(orders != null)
        {
            for (Order order : orders)
            {
                if (order.getReservationID() >= maxResID)
                {
                    maxResID = order.getReservationID() + 1;
                }
            }
        }
        return maxResID;
    }

    // Table Functions:
    public String getTableSequential(ArrayList<Table> tableResult)
    {
        tableResult.addAll(tables);
        return null;
    }

    // get a table by tableID
    public Table getTable(int tableID)
    {
        Table result = null;
        for(int i = 0; i < tables.size(); i++)
        {
            if(tables.get(i).getTID() == tableID)
            {
                result = tables.get(i);
                break;
            }
        }
        return result;
    }

    // Customer Functions:
    public String getCustomerSequential(List<Customer> customerResult)
    {
        customerResult.addAll(customers);
        return null;
    }

    // insert a customer
    public String insertCustomer(Customer customer)
    {
        customers.add(customer);
        return null;
    }

    // only for testing purpose
    public String deleteCustomer(Customer customer)
    {
        customers.remove(customer);
        return null;
    }

    // Menu Functions:
    public ArrayList<String> getMenuTypes()
    {
        ArrayList<String> types = new ArrayList<>();

        types.add("Salads");
        types.add("Sandwiches");
        types.add("Burgers");
        types.add("Mains");
        types.add("Desserts");
        types.add("Drinks");

        return types;
    }

    public ArrayList<Item> getMenu() { return menu; }

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

    // Order Functions:
    public Order getOrder(int reservationID)
    {
        if(reservationID < 0)
            throw new IllegalArgumentException("Invalid reservationID");

        Order orderResult = null;
        for(int i = 0; i < orders.size(); i++)
        {
            if(orders.get(i).getReservationID() == reservationID)
            {
                orderResult = orders.get(i);
                break;
            }
        }
        return orderResult;
    }

    // insert single item into order
    public String insertItemIntoOrder(int resID, Item item)
    {
        String result = null;

        if(resID < 0)
            throw new IllegalArgumentException("Invalid reservationID");

        //first check if order with resID exists, if not make new order
        Order orderResult = null;
        for(int i = 0; i < orders.size(); i++)
        {
            if(orders.get(i).getReservationID() == resID)
            {
                orderResult = orders.get(i);
                break;
            }
        }
        if(orderResult == null)
        {
            orderResult = new Order(resID);
            orderResult.addItem(item);
            insertOrder(orderResult);
        }
        else
        {
            int index = orders.indexOf(orderResult);
            orderResult.addItem(item);
            orders.set(index, orderResult);
        }
        return result;
    }

    // insert an order into order lists
    public String insertOrder(Order newOrder)
    {
        if(newOrder == null || newOrder.getReservationID() < 0)
            return "fail";

        orders.add(newOrder);
        return "success";
    }

    // remove an order
    public String removeOrder(int reservationID)
    {
        if(reservationID < 0)
            throw new IllegalArgumentException("Invalid reservationID");

        for(int i = 0; i < orders.size(); i++)
        {
            if(orders.get(i).getReservationID() == reservationID)
            {
                orders.remove(i);
                break;
            }
        }
        return null;
    }

    // get the price of an order with reservationID
    public double getPrice(int resID)
    {
        double totalPrice = 0.0;
        if(resID > 0 && orders.contains(getOrder(resID)))
        {
            totalPrice = getOrder(resID).getTotalPrice();
        }
        return totalPrice;
    }

    // private method to generate fake data
    private void generateFakeData()
    {
        // generate tables in the restaurant.
        // assume there are 30 tables
        // Table ID will be 1 to 30.
        // 5 tables each for 2, 4, 6, 8, 10, 12 people, totally 30 tables
        int size = 2;
        for(int i = 1; i <= 30; i++)
        {
            tables.add(new Table(i, size));
            if(i % 5 == 0)
                size += 2;
        }

        // generate items in menu
        Item salad, sandwich, burger, main, dessert, drink;
        salad = new Item(1, "SPECIAL SALAD", "Salads", "romaine lettuce, arugula, red cabbage, carrot, red onion & toasted sunflower seeds.", 9.95);
        menu.add(salad);
        salad = new Item(2, "SPINACH SALAD", "Salads", "tender spinach leaves, toasted almonds, orange slices & tangy chutney dressing.", 10.95);
        menu.add(salad);
        salad = new Item(3, "KALE SALAD", "Salads", "kale, tomato, goat cheese, red onion, dried cranberries, pepitas, avocado & balsamic vinaigrette.", 10.95);
        menu.add(salad);
        salad = new Item(4, "CAESAR SALAD", "Salads", "romaine lettuce, creamy garlic dressing, croutons & grated parmesan cheese.", 10.95);
        menu.add(salad);
        salad = new Item(5, "ARUGULA SALAD", "Salads", "arugula, green peas, sugar snap peas, radish, feta cheese & agave basil vinaigrette.", 11.95);
        menu.add(salad);
        salad = new Item(6, "AVOCADO SALAD", "Salads", "avocado, brussels sprouts, radish, alfalfa sprouts, chickpeas, dried cranberries & pepitas.", 12.95);
        menu.add(salad);

        sandwich = new Item(7, "VEGETARIAN", "Sandwiches", "vegetable patty, hummus, cream cheese, alfalfa sprouts, tomato & cucumber.", 12.95);
        menu.add(sandwich);
        sandwich = new Item(8, "SAUSAGE", "Sandwiches", "whole sausage, peppers, onions, sauerkraut, chili, lettuce, tomato& BBQ sauce.", 13.95);
        menu.add(sandwich);
        sandwich = new Item(9, "BACON", "Sandwiches", "bacon, lettuce, creamy havarti cheese, ripe tomato & mayonnaise on toasted multi-grain.", 13.95);
        menu.add(sandwich);
        sandwich = new Item(10, "TOASTED TUNA", "Sandwiches", "beef, green onion, mushroom, cucumber, lettuce, ripe tomato & mayonnaise.", 13.95);
        menu.add(sandwich);
        sandwich = new Item(11, "ROAST CHICKEN", "Sandwiches", "oven roasted chicken, Stella’s cranberry sauce, lettuce & mayonnaise on multigrain.", 14.95);
        menu.add(sandwich);
        sandwich = new Item(12, "BEEF", "Sandwiches", "toasted tuna, celery, green onion, sourdough, dill pickle, lettuce tomato & mayonnaise.", 14.95);
        menu.add(sandwich);
        sandwich = new Item(13, "SMOKED SALMON", "Sandwiches", "smoked salmon, cream cheese, capers, lettuce & a squeeze of lemon on a flaky croissant.", 15.95);
        menu.add(sandwich);
        sandwich = new Item(14, "CLUB", "Sandwiches", "oven roasted chicken, crisp bacon, cheddar cheese, lettuce, tomato & mayonnaise.", 15.95);
        menu.add(sandwich);

        burger = new Item(15, "GARDON", "Burgers", "chickpea patty, alfalfa sprouts, tomato, mayonnaise, crispy onions, peach chutney & cilantro sauce.", 13.95);
        menu.add(burger);
        burger = new Item(16, "GARBANZO", "Burgers", "garbanzo patty, onion, aged cheddar, basil mayo, lettuce, pickle, tomato & mayonnaise.", 13.95);
        menu.add(burger);
        burger = new Item(17, "QUINOA", "Burgers", "quinoa, mayonnaise, caramelized onion, arugula, tomato, dill pickle & caesar dressing.", 13.95);
        menu.add(burger);
        burger = new Item(18, "CHICKEN", "Burgers", "marinated chicken breast, tomato, lettuce, mayonnaise, crispy onions & a dollop of cilantro sauce.", 14.95);
        menu.add(burger);
        burger = new Item(19, "BEEF", "Burgers", "beef patty, mayonnaise, caramelized onion, arugula, tomato, pickle & tomato relish.", 14.95);
        menu.add(burger);
        burger = new Item(20, "SALMON FILLET", "Burgers", "salmon fillet grilled with lemon & fresh parsley with lettuce, tomato & crispy onions.", 15.95);
        menu.add(burger);
        burger = new Item(21, "MUSHROOM", "Burgers", "beef patty with all of the fixings, garlic mushrooms, havarti cheese & mayonnaise.", 15.95);
        menu.add(burger);
        burger = new Item(22, "GUACAMOLE & BACON", "Burgers", "beef patty with all of the fixings, two strips of bacon & scoop of guacamole.", 16.95);
        menu.add(burger);

        main = new Item(23, "RATATOUILLE", "Mains", "oven roasted zucchini, eggplant, tomato, peppers, onion, garlic & fresh herbs over steamed quinoa.", 14.95);
        menu.add(main);
        main = new Item(24, "PAD THAI", "Mains", "rice noodles, mushrooms, red peppers, cabbage, snap peas & bean sprouts in sweet soy tamarind sauce.", 14.95);
        menu.add(main);
        main = new Item(25, "CREAM LINGUINE", "Mains", "spinach, mushrooms, eggplant, tomato, red pepper & linguine in pesto cream sauce.", 15.95);
        menu.add(main);
        main = new Item(26, "DRAGON BOWL", "Mains", "spicy chili garlic tamarind sauce, eggplant, mushrooms & cabbage over quinoa and brown basmati rice.", 14.95);
        menu.add(main);
        main = new Item(27, "VEGGIE LASAGNA", "Mains", "mushrooms, zucchini, spinach, asiago, mozzarella, cottage cheese & Stella's marinara sauce.", 15.95);
        menu.add(main);
        main = new Item(28, "EGGPLANT PARMESAN", "Mains", "parmesan crusted eggplant slices with linguine, roasted garlic marinara & sourdough garlic toast.", 15.95);
        menu.add(main);
        main = new Item(29, "FISH TACOS", "Mains", "three corn tortillas, crispy cod fillets, avocado, black beans, cilantro sauce & spicy curtido.", 15.95);
        menu.add(main);
        main = new Item(30, "JAMBALAYA", "Mains", "chorizo sausage, shrimp, chicken, onion, tomato, garlic, peppers, celery, rice & special sause.", 16.95);
        menu.add(main);

        dessert = new Item(31, "CHOCOLATE CAKE", "Desserts", "double-stacked dark chocolate cake frosted with chocolate icing.", 8.00);
        menu.add(dessert);
        dessert = new Item(32, "CHOCOLATE TORTE", "Desserts", "velvety, dark chocolate flourless cake topped with raspberry sauce & whipped cream.", 8.00);
        menu.add(dessert);
        dessert = new Item(33, "CARROT CAKE", "Desserts", "two layers of delicious coconut carrot cake with vanilla cream cheese icing.", 8.00);
        menu.add(dessert);
        dessert = new Item(34, "CHEESECAKE", "Desserts", "half chocolate, half vanilla cheesecake with a chocolate cookie crust.", 8.00);
        menu.add(dessert);
        dessert = new Item(35, "APPLE CROSTADA", "Desserts", "fresh Chudleigh Farms heirloom apples & drizzled with caramel.", 8.00);
        menu.add(dessert);
        dessert = new Item(36, "PECAN BLONDIE", "Desserts", "nuts, topped with ice cream, glazed pecans & maple-flavored cream cheese sauce", 9.00);
        menu.add(dessert);

        drink = new Item(37, "PINK LEMONADE", "Drinks", "non-alcoholic lemon juice & a splash of grenadine.", 7.00);
        menu.add(drink);
        drink = new Item(38, "FRUIT PUNCH", "Drinks", "cranberry, orange, and pineapple juice, sour mix and a splash of grenadine.", 7.00);
        menu.add(drink);
        drink = new Item(39, "SHIRLEY TEMPLE","Drinks", "orange juice & ginger ale with a splash of grenadine.", 7.00);
        menu.add(drink);
        drink = new Item(40, "BLUE HAWAIIAN","Drinks", "malibu rum, blue curacao, pineapple juice with a slice of lemon.", 8.00);
        menu.add(drink);
        drink = new Item(41, "CAESAR", "Drinks", "vodka & a mixture of seasoning served in true sarnia style  with a dill pickle", 8.00);
        menu.add(drink);
        drink = new Item(42, "MOJITO", "Drinks", "malibu rum, suger water, sprite and served with fresh mint leaves and slice of lime.", 8.00);
        menu.add(drink);
        drink = new Item(43, "LONG ISLAND ICE TEA", "Drinks", "vodka, rum, gin, tequila, triple sec, coke, sour mix & a slice of lemon.", 9.00);
        menu.add(drink);
        drink = new Item(44, "MARGARITA", "Drinks", "tequila, lime juice with salted rimmed glass & a slice of lemon.", 9.00);
        menu.add(drink);

        // generate customer information
        Customer customer = new Customer("Gary", "Chalmers", "2049990123");
        customer.setCID(1);
        customers.add(customer);
        customer = new Customer("Selma", "Bouvier", "3065559999");
        customer.setCID(2);
        customers.add(customer);
        customer = new Customer("Arnie", "Pye", "4192045678");
        customer.setCID(3);
        customers.add(customer);
        customer = new Customer("Mary", "Bailey", "1057770123");
        customer.setCID(4);
        customers.add(customer);

        // generate reservation information
        DateTime startTime = new DateTime(new GregorianCalendar(2020, 7, 27, 18, 0));
        DateTime endTime = new DateTime(new GregorianCalendar(2020, 7, 27, 20, 0));
        Reservation reservation = new Reservation(1,2,2, startTime, endTime);
        reservation.setRID(1);
        reservations.add(reservation);

        startTime = new DateTime(new GregorianCalendar(2020, 7, 28, 15, 0));
        endTime = new DateTime(new GregorianCalendar(2020, 7, 28, 16, 0));
        reservation = new Reservation(2,6,3, startTime, endTime);
        reservation.setRID(2);
        reservations.add(reservation);

        startTime = new DateTime(new GregorianCalendar(2020, 7, 29, 17, 0));
        endTime = new DateTime(new GregorianCalendar(2020, 7, 29, 19, 0));
        reservation = new Reservation(3,18, 8, startTime, endTime);
        reservation.setRID(3);
        reservations.add(reservation);

        startTime = new DateTime(new GregorianCalendar(2020, 7, 30, 14, 0));
        endTime = new DateTime(new GregorianCalendar(2020, 7, 30, 15, 0));
        reservation = new Reservation(4,25, 10, startTime, endTime);
        reservation.setRID(4);
        reservations.add(reservation);

        // generate order information
        Order order = new Order(1);
        order.addItem(menu.get(12), 1, "");
        order.addItem(menu.get(4), 1, "");
        orders.add(order);

        order = new Order(2);
        order.addItem(menu.get(29), 1, "Extra hot sauce");
        order.addItem(menu.get(34), 2, "");
        order.addItem(menu.get(41), 4, "Spicy");
        orders.add(order);

        order = new Order(3);
        order.addItem(menu.get(5), 1, "");
        orders.add(order);

        order = new Order(4);
        order.addItem(menu.get(19), 2, "No cheese");
        order.addItem(menu.get(14), 1, "");
        order.addItem(menu.get(43), 3, "");
        orders.add(order);
    }
}
