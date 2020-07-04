package comp3350.rrsys.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
    accepts "yyyy-mm-ddTHH:mm" this format
 */
public class DateTime {

  Calendar timeSlot; //Calendar library
    //
    //Calendar cal = new GregorianCalendar(2013,7,28,13,24);
    //DateTime reservation = new DateTime(cal);
    public DateTime(Calendar timeInfo) throws java.text.ParseException {
        int CurrYear = Calendar.getInstance().get(Calendar.YEAR);
        //check valid year
        if(!(timeInfo.get(timeInfo.YEAR) >= CurrYear &&  timeInfo.get(timeInfo.YEAR) <= CurrYear+1)){
            throw new ParseException("Invalid Year.\n", timeInfo.get(timeInfo.YEAR));
        }

        if(!(timeInfo.get(timeInfo.MONTH) >= 1 && timeInfo.get(timeInfo.MONTH) <= 12)){
            throw new ParseException("Invalid Month.\n", timeInfo.get(timeInfo.MONTH));
        }

        if(!(timeInfo.get(timeInfo.DAY_OF_MONTH) >= 1 ||  timeInfo.get(timeInfo.DAY_OF_MONTH) <= timeInfo.getActualMaximum(timeInfo.DAY_OF_MONTH))){
            throw new ParseException("Invalid Date.\n",timeInfo.get(timeInfo.DAY_OF_MONTH));
        }

        if(!(timeInfo.get(timeInfo.HOUR) >=0 && timeInfo.get(timeInfo.HOUR) <= 24)){
            throw new ParseException("Invalid Hour.\n",timeInfo.get(timeInfo.HOUR));
        }

        if(!(timeInfo.get(timeInfo.MINUTE) >=0 && timeInfo.get(timeInfo.MINUTE) <= 60)){
            throw new ParseException("Invalid Minutes.\n", timeInfo.get(timeInfo.MINUTE));
        }
    timeSlot = timeInfo;
    }

    public void setYear(int year){timeSlot.set(timeSlot.YEAR,year);}
    public void setMonth(int month){timeSlot.set(timeSlot.MONTH,month);}
    public void setDate(int date){timeSlot.set(timeSlot.DATE,date);}
    public void setHour(int hour){timeSlot.set(timeSlot.HOUR,hour);}
    public void setMintues(int minutes){timeSlot.set(timeSlot.MINUTE,minutes);}

    public int getYear(){ return timeSlot.get(timeSlot.YEAR); }
    public int getMonth(){ return timeSlot.get(timeSlot.MONTH); }
    public int getDate(){ return timeSlot.get(timeSlot.DATE); }
    public int getHour(){ return timeSlot.get(timeSlot.HOUR); }
    public int getMinutes(){ return timeSlot.get(timeSlot.MINUTE); }

    // return how long between two date time in minutes
    public int getPeriod(DateTime other) {
        return (other.getHour()-this.getHour())*60 + other.getMinutes()-this.getMinutes();
    }

    public String print(){
        String s = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm");

        s += sdf.format(timeSlot.getTime());
        return s;
    }
}
