package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import comp3350.rrsys.R;
import java.util.List;
import java.util.Map;

public class ListAdapter extends BaseExpandableListAdapter
{
    private Activity context;
    private Map<String, List<String>> parentListItems;
    private List<String> items;

    public ListAdapter(Activity context, List<String> items, Map<String, List<String>> parentListItems)
    {
        this.context = context;
        this.parentListItems = parentListItems;
        this.items = items;
    }

    public Object getChild(int groupPosition, int childPosition) { return parentListItems.get(items.get(groupPosition)).get(childPosition); }

    public long getChildId(int groupPosition, int childPosition) { return childPosition; }

    public boolean hasStableIds() { return true; }

    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    public int getChildrenCount(int groupPosition) { return parentListItems.get(items.get(groupPosition)).size(); }

    public Object getGroup(int groupPosition) { return items.get(groupPosition); }

    public int getGroupCount() { return items.size(); }

    public long getGroupId(int groupPosition) { return groupPosition; }

    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent)
    {
        String parentName = (String)getGroup(groupPosition);

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_parent_item, null);
        }

        TextView item = view.findViewById(R.id.textView1);
        item.setText(parentName);
        return view;
    }

    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View view, ViewGroup parent)
    {
        final String childName = (String)getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if(view == null)
        {
            view = inflater.inflate(R.layout.list_child_item, null);
        }

        TextView textView = view.findViewById(R.id.textView1);
        textView.setText(childName);
        return view;
    }
}
