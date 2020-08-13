package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import comp3350.rrsys.R;
import comp3350.rrsys.business.AccessCustomers;
import comp3350.rrsys.business.AccessReservations;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.Reservation;

public class CreateConfirmReservationActivity extends Activity
{
    private Reservation reservation;
    private String lastName, firstName, phoneNumber;
    private boolean firstNameEdited, lastNameEdited, phoneNumberEdited;
    private AccessReservations accessReservations;
    private AccessCustomers accessCustomers;
    private int tableID, numPeople, year, month, day, startHour, startMinute, endHour, endMinute;
    private ArrayList<Customer> customers;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_confirm_reservation);
        tableID = Integer.parseInt(getIntent().getStringExtra("tableID"));
        numPeople = Integer.parseInt(getIntent().getStringExtra("numPeople"));
        year = Integer.parseInt(getIntent().getStringExtra("year"));
        month = Integer.parseInt(getIntent().getStringExtra("month"));
        day = Integer.parseInt(getIntent().getStringExtra("day"));
        startHour = Integer.parseInt(getIntent().getStringExtra("startHour"));
        startMinute = Integer.parseInt(getIntent().getStringExtra("startMinute"));
        endHour = Integer.parseInt(getIntent().getStringExtra("endHour"));
        endMinute = Integer.parseInt(getIntent().getStringExtra("endMinute"));
        DateTime start = new DateTime(new GregorianCalendar(year, month, day, startHour, startMinute));
        DateTime end = new DateTime(new GregorianCalendar(year, month, day, endHour, endMinute));
        reservation = new Reservation(tableID, numPeople, start, end);

        accessReservations = new AccessReservations();
        accessCustomers = new AccessCustomers();
        customers = new ArrayList<>();

        final TextView editDateInfo = findViewById(R.id.textDateInfo);
        editDateInfo.setText((reservation.getStartTime().getMonth()+1) + "/" + reservation.getStartTime().getDate() + "/" + reservation.getStartTime().getYear());

        final TextView editStartTimeInfo = findViewById(R.id.textStartTimeInfo);
        String startTime;

        if(reservation.getStartTime().getMinutes() < 10)
            startTime = String.format(reservation.getStartTime().getHour() + ":0%d", reservation.getStartTime().getMinutes());
        else
            startTime = String.format(reservation.getStartTime().getHour() + ":%d", reservation.getStartTime().getMinutes());

        editStartTimeInfo.setText(startTime);

        final TextView editEndTimeInfo = findViewById(R.id.textEndTimeInfo);
        String endTime;

        if(reservation.getEndTime().getMinutes() < 10)
            endTime = String.format(reservation.getEndTime().getHour() + ":0%d", reservation.getEndTime().getMinutes());
        else
            endTime = String.format(reservation.getEndTime().getHour() + ":%d", reservation.getEndTime().getMinutes());

        editEndTimeInfo.setText(endTime);

        final TextView editNumberOfPeople = findViewById(R.id.textNumberOfPeopleInfo);
        editNumberOfPeople.setText("" + reservation.getNumPeople());

        final Button buttonConfirm = findViewById(R.id.buttonConfirm);
        final EditText editFirstName = findViewById(R.id.editFirstName);
        editFirstName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable)
            {
                firstNameEdited = editFirstName.getText().toString().length() > 0;
                buttonConfirm.setEnabled(firstNameEdited && lastNameEdited && phoneNumberEdited);
                firstName = editFirstName.getText().toString();
            }
        });

        final EditText editLastName = findViewById(R.id.editLastName);
        editLastName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable)
            {
                lastNameEdited = editLastName.getText().toString().length() > 0;
                buttonConfirm.setEnabled(firstNameEdited && lastNameEdited && phoneNumberEdited);
                lastName = editLastName.getText().toString();
            }
        });

        final EditText editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editPhoneNumber.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable)
            {
                phoneNumberEdited = editPhoneNumber.getText().toString().length() > 0;
                buttonConfirm.setEnabled(firstNameEdited && lastNameEdited && phoneNumberEdited);
                phoneNumber = editPhoneNumber.getText().toString();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_create_confirm_reservation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void buttonBackOnClick(View v)
    {
        Intent backPageIntent = new Intent(CreateConfirmReservationActivity.this, CreateReservationActivity.class);
        CreateConfirmReservationActivity.this.startActivity(backPageIntent);
    }

    public void buttonConfirmOnClick(View v)
    {
        Customer customer = null;

        try
        {
            customer = new Customer(firstName, lastName, phoneNumber);
            accessCustomers.getCustomers(customers);
            customer.setCID(customers.size()+1);
        }
        catch(Exception e)
        {
            Messages.warning(this, "Error: " + e.getMessage());
        }

        if(customer != null)
        {
            accessCustomers.insertCustomer(customer);
            reservation.setCustomerID(customer.getCID());
            reservation.setRID(accessReservations.getNextReservationID());
            accessReservations.insertReservation(reservation);

            Intent confirmReservationIntent = new Intent(CreateConfirmReservationActivity.this, ReceiptReservationActivity.class);
            confirmReservationIntent.putExtra("reservationID", reservation.getRID() + "");
            confirmReservationIntent.putExtra("numPeople", numPeople+"");
            confirmReservationIntent.putExtra("year", year + "");
            confirmReservationIntent.putExtra("month", month + "");
            confirmReservationIntent.putExtra("day", day + "");
            confirmReservationIntent.putExtra("startHour", startHour + "");
            confirmReservationIntent.putExtra("startMinute", startMinute + "");
            confirmReservationIntent.putExtra("endHour", endHour + "");
            confirmReservationIntent.putExtra("endMinute", endMinute + "");
            CreateConfirmReservationActivity.this.startActivity(confirmReservationIntent);
        }
    }
}