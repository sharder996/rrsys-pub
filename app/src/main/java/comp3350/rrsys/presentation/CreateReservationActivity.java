package comp3350.rrsys.presentation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.lang.String;

import comp3350.rrsys.R;
import comp3350.rrsys.business.AccessTables;
import comp3350.rrsys.business.AccessReservations;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;

public class CreateReservationActivity extends Activity
{
    private ArrayList<Reservation> reservationList;
    private AccessReservations accessReservations;
    private AccessTables accessTables;
    private ArrayAdapter<Reservation> reservationArrayAdapter;
    private int reservationSelected = -1;

    private Reservation selected;
    private int setYear, setMonth, setDay, setStartHourOfDay, setStartMinute, setEndHourOfDay, setEndMinute;
    private int numberOfPeople = Reservation.MIN_PEOPLE;

    private Calendar calendar;
    private String amPm;
    private boolean dateEdited = false;
    private boolean timeInEdited = false;
    private boolean timeOutEdited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);
        calendar = Calendar.getInstance();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        reservationList = new ArrayList<>();
        accessReservations = new AccessReservations();
        accessTables = new AccessTables();
        reservationArrayAdapter = new ArrayAdapter<Reservation>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, reservationList)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View view = super.getView(position, convertView, parent);

                TextView text1 = view.findViewById(android.R.id.text1);

                text1.setText(reservationList.get(position).getStartTime() + " - " + reservationList.get(position).getEndTime().getTime()
                        + " " + accessTables.getRandom(reservationList.get(position).getTID()));

                return view;
            }
        };

        final ListView listView = findViewById(R.id.availabilityList);
        listView.setAdapter(reservationArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                Button confirmButton = findViewById(R.id.buttonMakeReservation);

                if(position == reservationSelected)
                {
                    listView.setItemChecked(position, false);
                    confirmButton.setEnabled(false);
                    reservationSelected = -1;
                }
                else
                {
                    listView.setItemChecked(position, true);
                    confirmButton.setEnabled(true);
                    reservationSelected = position;
                    selectTimeAtPosition(position);
                }
            }
        });

        final Button buttonCheckAvailability = findViewById(R.id.buttonCheckAvailability);
        final EditText editTextDate = findViewById(R.id.editTextDate);
        editTextDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateReservationActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day)
                    {
                        editTextDate.setText((month + 1) + "/" + day + "/" + year);
                        setDay = day;
                        setMonth = month;
                        setYear = year;
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        editTextDate.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable)
            {
                dateEdited = editTextDate.getText().toString().length() > 0;
                buttonCheckAvailability.setEnabled(dateEdited && timeInEdited && timeOutEdited);
            }
        });

        final EditText editTextTime = findViewById(R.id.editTextTime);
        editTextTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateReservationActivity.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes)
                    {
                        if(hourOfDay >= 12)
                            amPm = " PM";
                        else
                            amPm = " AM";

                        editTextTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);

                        setStartHourOfDay = hourOfDay;
                        setStartMinute = minutes;
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

                timePickerDialog.show();
            }
        });

        editTextTime.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable)
            {
                timeInEdited = editTextTime.getText().toString().length() > 0;
                buttonCheckAvailability.setEnabled(dateEdited && timeInEdited && timeOutEdited);
            }
        });

        final EditText editTextLengthOfStay = findViewById(R.id.editLengthOfStay);
        editTextLengthOfStay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateReservationActivity.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes)
                    {
                        if(hourOfDay >= 12)
                            amPm = " PM";
                        else
                            amPm = " AM";

                        editTextLengthOfStay.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);

                        setEndHourOfDay = hourOfDay;
                        setEndMinute = minutes;
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

                timePickerDialog.show();
            }
        });

        editTextLengthOfStay.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable)
            {
                timeOutEdited = editTextLengthOfStay.getText().toString().length() > 0;
                buttonCheckAvailability.setEnabled(dateEdited && timeInEdited && timeOutEdited);
            }
        });

        final NumberPicker numberPicker = findViewById(R.id.editNumberOfPeople);
        numberPicker.setMinValue(Reservation.MIN_PEOPLE);
        numberPicker.setMaxValue(Reservation.MAX_PEOPLE);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker numberPicker, int prev, int next)
            {
                numberOfPeople = next;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_create_reservation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void buttonConfirmOnClick(View v)
    {
        Intent createReservationIntent = new Intent(CreateReservationActivity.this, CreateConfirmReservationActivity.class);
        createReservationIntent.putExtra("tableID", selected.getTID() + "");
        createReservationIntent.putExtra("numPeople", selected.getNumPeople() + "");
        createReservationIntent.putExtra("year", selected.getStartTime().getYear() + "");
        createReservationIntent.putExtra("month", selected.getStartTime().getMonth() + "");
        createReservationIntent.putExtra("day", selected.getStartTime().getDate() + "");
        createReservationIntent.putExtra("startHour", selected.getStartTime().getHour() + "");
        createReservationIntent.putExtra("startMinute", selected.getStartTime().getMinutes() + "");
        createReservationIntent.putExtra("endHour", selected.getEndTime().getHour() + "");
        createReservationIntent.putExtra("endMinute", selected.getEndTime().getMinutes() + "");
        CreateReservationActivity.this.startActivity(createReservationIntent);
    }

    public void selectTimeAtPosition(int position)
    {
        selected = reservationArrayAdapter.getItem(position);
    }

    public void buttonCheckAvailabilityOnClick(View v)
    {
        DateTime startTime = null;
        DateTime endTime = null;

        try
        {
            startTime = new DateTime(new GregorianCalendar(setYear, setMonth, setDay, setStartHourOfDay, setStartMinute));
            endTime = new DateTime(new GregorianCalendar(setYear, setMonth, setDay, setEndHourOfDay, setEndMinute));
        }
        catch(Exception e)
        {
            Messages.fatalError(this, "Error processing date. Please enter a valid date.");
        }

        if(setYear < calendar.get(Calendar.YEAR) || (setYear == calendar.get(Calendar.YEAR) && setMonth < calendar.get(Calendar.MONTH)) ||
                (setYear == calendar.get(Calendar.YEAR) && setMonth == calendar.get(Calendar.MONTH) && setDay < calendar.get(Calendar.DAY_OF_MONTH)))
            Messages.warning(this, "Error: Please enter a date that is not in the past.");
        else
        {
            if(startTime != null && endTime != null)
            {
                if(startTime.getHour() >= Table.START_TIME && (endTime.getHour() < Table.END_TIME || (endTime.getHour() == Table.END_TIME && endTime.getMinutes() == 0))
                        && startTime.getPeriod(endTime) >= Reservation.MIN_TIME && startTime.getPeriod(endTime) <= Reservation.MAX_TIME)
                {
                    reservationList.clear();
                    ArrayList<Reservation> suggestions = accessReservations.suggestReservations(startTime, endTime, numberOfPeople);
                    for(int i = 0; i < suggestions.size() && i < 5; i++)
                        reservationList.add(suggestions.get(i));

                    reservationArrayAdapter.notifyDataSetChanged();
                    ListView listView = findViewById(R.id.availabilityList);
                    listView.setSelection(0);

                    if(suggestions.isEmpty())
                        Messages.warning(this, "Error: No openings found.");
                }
                else if(startTime.getPeriod(endTime) < Reservation.MIN_TIME || startTime.getPeriod(endTime) > Reservation.MAX_TIME)
                    Messages.warning(this, "Error: Reservation must be between " + Reservation.MIN_TIME + " minutes and " + Reservation.MAX_TIME + " minutes.");
                else
                    Messages.warning(this, "Error: Our restaurant is open from 7:00 AM to 23:00 PM.");
            }
            else
                Messages.warning(this, "Error processing date. Please enter a valid date.");
        }
    }
}
