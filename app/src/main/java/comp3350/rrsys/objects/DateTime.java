package comp3350.rrsys.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
    accepts "yyyy-mm-ddTHH:mm" this format
 */

public class DateTime implements Parcelable
{
    private Calendar timeSlot;
    private static final int MAX_DAYS_DIFFERENCE = 30;

    // Calendar cal = new GregorianCalendar(2013,7,28,13,24);
    // DateTime reservation = new DateTime(cal);
    public DateTime(Calendar timeInfo) throws IllegalArgumentException
    {
        Calendar currDate = Calendar.getInstance();

        int differenceDays = (int)((timeInfo.getTimeInMillis() - currDate.getTimeInMillis()) / 1000 / 3600 / 24);
        if(differenceDays > MAX_DAYS_DIFFERENCE || differenceDays < 0)
            throw new IllegalArgumentException("Invalid date.");

        timeSlot = timeInfo;
    }

    private DateTime(Parcel in)
    {
        timeSlot = new GregorianCalendar(in.readInt(), in.readInt(), in.readInt(), in.readInt(), in.readInt());
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

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.getYear());
        dest.writeInt(this.getMonth());
        dest.writeInt(this.getDate());
        dest.writeInt(this.getHour());
        dest.writeInt(this.getMinutes());
    }

    public static final Parcelable.Creator<DateTime> CREATOR = new Parcelable.Creator<DateTime>()
    {
        @Override
        public DateTime createFromParcel(Parcel source)
        {
            return new DateTime(source);
        }

        @Override
        public DateTime[] newArray(int size)
        {
            return new DateTime[size];
        }
    };
}
