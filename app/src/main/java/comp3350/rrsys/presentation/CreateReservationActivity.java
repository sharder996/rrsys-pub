package comp3350.rrsys.presentation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

import comp3350.rrsys.R;
import comp3350.rrsys.objects.Reservation;

public class CreateReservationActivity extends Activity
{
    private ArrayList<Reservation> reservationList;
    private ArrayAdapter<Reservation> reservationArrayAdapter;
    private int reservationSelected = -1;
    private Reservation selected = null;

    private Calendar calendar;
    private String amPm;
    private boolean dateEdited = false;
    private boolean timeInEdited = false;
    private boolean timeOutEdited = false;

    private final int MAX_PEOPLE = 12;
    private final int MIN_PEOPLE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);
        calendar = Calendar.getInstance();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        reservationList = new ArrayList<>();
        reservationArrayAdapter = new ArrayAdapter<Reservation>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, reservationList)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View view = super.getView(position, convertView, parent);

                TextView text1 = view.findViewById(android.R.id.text1);

                text1.setText(reservationList.get(position).getStartTime() + " - " + reservationList.get(position).getEndTime());

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
                Button confirmButton = findViewById(R.id.buttonConfirm);

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
                        editTextDate.setText(day + "/" + (month + 1) + "/" + year);
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
                dateEdited = true;
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
                            amPm = "PM";
                        else
                            amPm = "AM";

                        editTextTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
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
                timeInEdited = true;
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
                            amPm = "PM";
                        else
                            amPm = "AM";

                        editTextLengthOfStay.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
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
                timeOutEdited = true;
                buttonCheckAvailability.setEnabled(dateEdited && timeInEdited && timeOutEdited);
            }
        });

        NumberPicker numberPicker = findViewById(R.id.editNumberOfPeople);
        numberPicker.setMinValue(MIN_PEOPLE);
        numberPicker.setMaxValue(MAX_PEOPLE);
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
        Intent confirmReservationIntent = new Intent(CreateReservationActivity.this, CreateConfirmReservationActivity.class);
        confirmReservationIntent.putExtra("reservation", selected);
        CreateReservationActivity.this.startActivity(confirmReservationIntent);
    }

    public void selectTimeAtPosition(int position)
    {
        selected = reservationArrayAdapter.getItem(position);


    }

    public void buttonCheckAvailabilityOnClick(View v)
    {

    }
}
