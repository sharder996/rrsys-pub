package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import comp3350.rrsys.R;
import comp3350.rrsys.business.AccessMenu;
import comp3350.rrsys.business.AccessOrders;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;

public class CreateOrderActivity extends Activity
{
    private AccessMenu accessMenu;
    private Map<String, List<Item>> parentListItems;
    private ExpandableListView menuItemsListView;

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        accessMenu = new AccessMenu();
        order = new Order(Integer.parseInt(getIntent().getStringExtra("reservationID")));

        List<String> parentList = accessMenu.getMenuTypes();
        parentListItems = new LinkedHashMap<>();

        for (String parent : parentList)
        {
            ArrayList<Item> items = accessMenu.getMenuByType(parent);
            parentListItems.put(parent, items);
        }

        menuItemsListView = findViewById(R.id.menuList);
        final ExpandableListAdapter expandableListAdapter = new ListAdapter(this, parentList, parentListItems);
        menuItemsListView.setAdapter(expandableListAdapter);

        menuItemsListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l)
            {
                final Item selected = (Item)expandableListAdapter.getChild(i, i1);

                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_order_window, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                popupWindow.showAtLocation(menuItemsListView, Gravity.CENTER, 0, 0);

                popupView.setOnTouchListener(new View.OnTouchListener()
                {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent)
                    {
                        popupWindow.dismiss();
                        return true;
                    }
                });

                TextView textView = popupView.findViewById(R.id.textAddItemToOrder);
                textView.setText(R.string.add_item_to_order);

                final Button confirmButton = popupView.findViewById(R.id.buttonPopupConfirm);
                confirmButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        order.addItem(selected);
                        popupWindow.dismiss();
                    }
                });

                final NumberPicker numberPicker = popupView.findViewById(R.id.editQuantity);
                numberPicker.setMinValue(Item.MIN_QUANTITY);
                numberPicker.setMaxValue(Item.MAX_QUANTITY);
                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
                {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1)
                    {
                        selected.setQuantity(i1);
                    }
                });

                return true;
            }
        });
    }

    public void buttonViewOrderOnClick(View v)
    {
        final LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_view_order, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(menuItemsListView, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                popupWindow.dismiss();
                return true;
            }
        });

        final ArrayList<Item> itemList = order.getOrder();
        final ArrayAdapter<Item> orderArrayAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, itemList)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                text1.setText(itemList.get(position).toString());

                return view;
            }
        };

        final ListView listView = popupView.findViewById(R.id.orderList);
        listView.setAdapter(orderArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                final Item selected = orderArrayAdapter.getItem(i);

                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_remove_item, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                popupWindow.showAtLocation(menuItemsListView, Gravity.CENTER, 0, 0);

                popupView.setOnTouchListener(new View.OnTouchListener()
                {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent)
                    {
                        popupWindow.dismiss();
                        return true;
                    }
                });

                final Button confirmButton = popupView.findViewById(R.id.buttonPopupConfirm);
                confirmButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        order.deleteItem(selected);
                        orderArrayAdapter.notifyDataSetChanged();
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    public void buttonCancelOnClick(View v)
    {
        Intent intent = getIntent();
        String activity = intent.getStringExtra("activity");

        Intent backPageIntent;
        if (activity.equals("GetChoiceUpdateReservationActivity"))
        {
            backPageIntent = new Intent(CreateOrderActivity.this, GetChoiceUpdateReservationActivity.class);
            backPageIntent.putExtra("Date", getIntent().getStringExtra("Date"));
            backPageIntent.putExtra("TimeStart", getIntent().getStringExtra("TimeStart"));
            backPageIntent.putExtra("TimeEnd", getIntent().getStringExtra("TimeEnd"));
            backPageIntent.putExtra("Code", getIntent().getStringExtra("Code"));
        }
        else
            backPageIntent = new Intent(CreateOrderActivity.this, ReceiptReservationActivity.class);

        backPageIntent.putExtra("reservationID", getIntent().getStringExtra("reservationID"));
        backPageIntent.putExtra("numPeople", getIntent().getStringExtra("numPeople"));
        backPageIntent.putExtra("year", getIntent().getStringExtra("year"));
        backPageIntent.putExtra("month", getIntent().getStringExtra("month"));
        backPageIntent.putExtra("day", getIntent().getStringExtra("day"));
        backPageIntent.putExtra("startHour", getIntent().getStringExtra("startHour"));
        backPageIntent.putExtra("startMinute", getIntent().getStringExtra("startMinute"));
        backPageIntent.putExtra("endHour", getIntent().getStringExtra("endHour"));
        backPageIntent.putExtra("endMinute", getIntent().getStringExtra("endMinute"));

        CreateOrderActivity.this.startActivity(backPageIntent);
    }

    public void buttonConfirmOrderOnClick(View v)
    {
        final LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_order_note_confirm, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(menuItemsListView, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                popupWindow.dismiss();
                return true;
            }
        });

        final EditText editTextInstructions = popupView.findViewById((R.id.editTextInstructions));
        editTextInstructions.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable)
            {
                order.setNote(editTextInstructions.getText().toString());
            }
        });

        final Button confirmButton = popupView.findViewById(R.id.buttonPopupConfirm);
        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AccessOrders accessOrders = new AccessOrders();
                accessOrders.insertOrder(order);

                Intent homeIntent = new Intent(CreateOrderActivity.this, HomeActivity.class);
                CreateOrderActivity.this.startActivity(homeIntent);
            }
        });
    }
}
