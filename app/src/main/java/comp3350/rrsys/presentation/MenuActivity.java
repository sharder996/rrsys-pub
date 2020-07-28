package comp3350.rrsys.presentation;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ListView;

        import java.util.ArrayList;

        import comp3350.rrsys.R;
        import comp3350.rrsys.business.AccessMenu;
        import comp3350.rrsys.objects.Item;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.MotionEvent;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import java.util.TreeSet;

public class MenuActivity extends Activity
{
    ArrayList<Item> menuItems;
    AccessMenu accessMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        accessMenu = new AccessMenu();
        menuItems = new ArrayList<>();
        final ListView listMenu = (ListView) findViewById(R.id.menuList);
        Adapter adapter = new Adapter();

        ArrayList<String> types = accessMenu.getMenuTypes();

        for(int i = 0; i < types.size(); i++) {
            adapter.addSeparatorItem(types.get(i));
            ArrayList<Item> items = accessMenu.getMenuByType(types.get(i));
            for (int j = 0; j < items.size(); j++) {
                adapter.addItem(items.get(j).getName()+ "\n" +items.get(j).getPrice() + ", " + items.get(j).getDetail());
            }
        }
        listMenu.setAdapter(adapter);
    }

    public void buttonBackOnClick(View v)
    {
        Intent backPageIntent = new Intent(MenuActivity.this, HomeActivity.class);
        MenuActivity.this.startActivity(backPageIntent);
    }

    private class Adapter extends BaseAdapter {

        private static final int TYPE_ITEM = 0;
        private static final int TYPE_SEPARATOR = 1;

        private ArrayList<String> data = new ArrayList<String>();
        private LayoutInflater inflater;

        private TreeSet<Integer> separatorsSet = new TreeSet<Integer>();

        public Adapter() {
            inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addItem(final String item) {
            data.add(item);
            notifyDataSetChanged();
        }

        public void addSeparatorItem(final String item) {
            data.add(item);
            separatorsSet.add(data.size() - 1);
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            return separatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
        }


        public int getCount() {
            return data.size();
        }


        public String getItem(int position) {
            return data.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            int type = getItemViewType(position);
            if (convertView == null) {
                holder = new ViewHolder();
                switch (type) {
                    case TYPE_ITEM:
                        convertView = inflater.inflate(R.layout.menu_item, null);
                        holder.textView = (TextView)convertView.findViewById(R.id.text);
                        break;
                    case TYPE_SEPARATOR:
                        convertView = inflater.inflate(R.layout.menu_header, null);
                        holder.textView = (TextView)convertView.findViewById(R.id.textSeparator);
                        break;
                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.textView.setText(data.get(position));
            return convertView;
        }

    }

    public static class ViewHolder {
        public TextView textView;
    }
}

//public class MainActivity extends ListActivity implements OnTouchListener{
//
//    private MyAdapter mAdapter;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mAdapter = new MyAdapter();
//        for (int i = 1; i < 50; i++) {
//            mAdapter.addItem("Menu Item List " + i);
//            if (i % 4 == 0) {
//                mAdapter.addSeparatorItem("Menu " + i);
//            }
//        }
//        setListAdapter(mAdapter);
//    }
    //Adapter Class

//}