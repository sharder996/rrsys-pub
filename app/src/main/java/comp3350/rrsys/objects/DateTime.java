package comp3350.rrsys.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
    accepts "yyyy-mm-ddTHH:mm" this format
 */
public class DateTime implements Parcelable
{
    Calendar timeSlot; //Calendar library
    //
    //Calendar cal = new GregorianCalendar(2013,7,28,13,24);
    //DateTime reservation = new DateTime(cal);
    public DateTime(Calendar timeInfo) throws  IllegalArgumentException
    {

        int CurrYear = Calendar.getInstance().get(Calendar.YEAR);


        //check valid year
        if(!(timeInfo.get(Calendar.YEAR) >= CurrYear &&  timeInfo.get(Calendar.YEAR) <= CurrYear+1))
        {
            throw new IllegalArgumentException("Invalid Year.");
        }

        timeSlot = timeInfo;
    }

    private DateTime(Parcel in)
    {
        timeSlot = new GregorianCalendar(in.readInt(), in.readInt(), in.readInt(), in.readInt(), in.readInt());
    }

    public void setYear(int year){timeSlot.set(Calendar.YEAR,year);}
    public void setMonth(int month){timeSlot.set(Calendar.MONTH,month);}
    public void setDate(int date){timeSlot.set(Calendar.DATE,date);}
    public void setHour(int hour){ timeSlot.set(Calendar.HOUR_OF_DAY,hour); }
    public void setMinutes(int minutes){timeSlot.set(Calendar.MINUTE,minutes);}

    public int getYear(){ return timeSlot.get(Calendar.YEAR); }
    public int getMonth(){ return timeSlot.get(Calendar.MONTH); }
    public int getDate(){ return timeSlot.get(Calendar.DATE); }
    public int getHour(){ return timeSlot.get(Calendar.HOUR_OF_DAY); }
    public int getMinutes(){ return timeSlot.get(Calendar.MINUTE); }

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm");

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
