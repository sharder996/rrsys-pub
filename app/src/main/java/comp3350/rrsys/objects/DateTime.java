package comp3350.rrsys.objects;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/* Class: DateTime
 *
 * Custom Calendar object that provides custom behavior so that valid times can be restricted
 */

public class DateTime
{
    private Calendar timeSlot;
    public static final int MAX_DAYS_DIFFERENCE = 30;

    // Calendar cal = new GregorianCalendar(2013,7,28,13,24);
    // DateTime reservation = new DateTime(cal);
    public DateTime(Calendar timeInfo) throws IllegalArgumentException
    {
        Calendar currDate = Calendar.getInstance();

        double differenceDays = (timeInfo.getTimeInMillis() - currDate.getTimeInMillis()) / 1000.0 / 3600.0 / 24.0;
        if(differenceDays > MAX_DAYS_DIFFERENCE || differenceDays < 0)
            throw new IllegalArgumentException("Invalid date.");

        timeSlot = timeInfo;
    }

    public int getYear(){ return timeSlot.get(Calendar.YEAR); }
    public int getMonth(){ return timeSlot.get(Calendar.MONTH); }
    public int getDate(){ return timeSlot.get(Calendar.DATE); }
    public int getHour(){ return timeSlot.get(Calendar.HOUR_OF_DAY); }
    public int getMinutes(){ return timeSlot.get(Calendar.MINUTE); }
    public Calendar getCalendar(){ return timeSlot; }

    // return how long between two date time in minutes
    public boolean equals(DateTime compare)
    {
        return compare.getYear() == timeSlot.get(Calendar.YEAR) && compare.getMonth() == timeSlot.get(Calendar.MONTH) && compare.getDate() == timeSlot.get(Calendar.DATE) &&
                compare.getHour() == timeSlot.get(Calendar.HOUR_OF_DAY) && compare.getMinutes() == timeSlot.get(Calendar.MINUTE);
    }

    public int getPeriod(DateTime other)
    {
        return (other.getHour()-this.getHour())*60 + other.getMinutes()-this.getMinutes();
    }

    @Override
    public String toString()
    {
        String s = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        s += sdf.format(timeSlot.getTime());
        return s;
    }
}
