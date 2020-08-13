package comp3350.rrsys.objects;

/* Class: Customer
 *
 * Customer stub database object. Holds unique ID (enforced by Database object), customer name (first
 * and last) and phone number;
 */

public class Customer
{
    private int cID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    // enforces format: 9999999999, 1-999-999-9999 and 999-999-9999
    private final static String regExPhoneNumber = "^(1\\-)?[0-9]{3}\\-?[0-9]{3}\\-?[0-9]{4}$";

    public Customer(String fName, String lName, String pNum) throws IllegalArgumentException
    {
        if(!fName.isEmpty() && !containsDigitInString(fName) && fName.split("\\s+").length == 1)
            firstName = titleCaseConversion(fName);
        else
            throw new IllegalArgumentException("Invalid name.");

        if(!lName.isEmpty() && !containsDigitInString(lName) && lName.split("\\s+").length == 1)
            lastName = titleCaseConversion(lName);
        else
            throw new IllegalArgumentException("Invalid name.");

        if(!pNum.isEmpty() && pNum.matches(regExPhoneNumber))
            phoneNumber = pNum.replaceAll("\\D", "");
        else
            throw new IllegalArgumentException("Invalid phone number format.");
    }

    private boolean containsDigitInString(String input)
    {
        boolean containsDigit = false;
        if(input != null && !input.isEmpty())
        {
            for(char c : input.toCharArray())
            {
                if(containsDigit = Character.isDigit(c))
                    break;
            }
        }
        return containsDigit;
    }

    private String titleCaseConversion(String input)
    {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getCID() { return cID; }

    public void setFirstName(String newFirstName) throws IllegalArgumentException
    {
        if(!newFirstName.isEmpty() && !containsDigitInString(newFirstName) && newFirstName.split("\\s+").length == 1)
            firstName = titleCaseConversion(newFirstName);
        else
            throw new IllegalArgumentException("Invalid name.");
    }

    public void setLastName(String newLastName) throws IllegalArgumentException
    {
        if(!newLastName.isEmpty() && !containsDigitInString(newLastName) && newLastName.split("\\s+").length == 1)
            lastName = titleCaseConversion(newLastName);
        else
            throw new IllegalArgumentException("Invalid name.");
    }

    public void setPhoneNumber(String newPhNum) throws IllegalArgumentException
    {
        if(!newPhNum.isEmpty() && newPhNum.matches(regExPhoneNumber))
            phoneNumber = newPhNum.replaceAll("\\D", "");
        else
            throw new IllegalArgumentException("Invalid phone number format.");
    }

    public void setCID(int id) throws IllegalArgumentException
    {
        if(id >= 0)
            cID = id;
        else
            throw new IllegalArgumentException("Invalid cID.");
    }
}
