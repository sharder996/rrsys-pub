package comp3350.rrsys.business;

import java.util.ArrayList;

import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;

public class GenerateReservation
{
    public static ArrayList<Reservation> SuggestReservations(DateTime start, DateTime end, int numberOfPeople)
    {
        ArrayList<Reservation> result = new ArrayList<>();
        result.add(new Reservation(0, 0, numberOfPeople, start, end));

        return result;
    }
}
