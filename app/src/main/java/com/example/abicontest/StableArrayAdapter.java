package com.example.abicontest;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class StableArrayAdapter extends ArrayAdapter<String> {

    final String TAG = getClass().getCanonicalName();
    HashMap<String, Integer> idMap = new HashMap<>();
    ListView listView;
    Activity activity;
    final List<String> listData;


    public StableArrayAdapter(Activity activity, int resource, List<String> listData) {
        super(activity, resource, listData);

        this.activity = activity;
        this.listData = listData;
        listView = (ListView) activity.findViewById(R.id.results);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(multiChoiceModeListener());

        for (int i = 0; i < this.listData.size(); i++) {
            idMap.put(listData.get(i), i);
            Log.d(TAG, "listData, resultText(" + i + "): " + listData.get(i));
        }
    }

    static class ViewHolder {
        public TextView resultText;
    }

    public List<String> getListData() {
        return listData;
    }

    @Override
    public long getItemId(int position) {
        String resultText = getItem(position);
        return idMap.get(resultText);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
//        Log.d(TAG, "getViewA");

        if (rowView == null) {
//            Log.d(TAG, "getViewB");
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.result_row, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.resultText = (TextView) rowView.findViewById(R.id.resultText);

            viewHolder.resultText.setText(listData.get(position));

            rowView.setTag(viewHolder);
        } else {
//            Log.d(TAG, "getViewC");
            ViewHolder viewHolder = (ViewHolder) rowView.getTag();

            viewHolder.resultText.setText(listData.get(position));
        }


        return rowView;
    }


    private AbsListView.MultiChoiceModeListener multiChoiceModeListener() {

        return new AbsListView
                .MultiChoiceModeListener() {


            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

                getSelectedItems();
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

                MenuInflater menuInflater = actionMode.getMenuInflater();
                menuInflater.inflate(R.menu.menu_result_cab, menu);
                Log.d(TAG, "onCreateActionMode");

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {




                return invalidateMenuItems(menu);
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_edit_selected:
                        actionMode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        };


    }

    private boolean invalidateMenuItems(Menu menu) {
        MenuItem item;
        boolean status = false;

        return false;
    }

    private List<String> getSelectedItems(){

        List<String> selectedItems = new ArrayList<>();
        SparseBooleanArray selKeysArray = listView.getCheckedItemPositions();
        for (int i = 0; i < selKeysArray.size(); i++) {
            int key = selKeysArray.keyAt(i);
            if (selKeysArray.get(key)) {

                selectedItems.add(listData.get(key));
            }
        }
        return selectedItems;
    }
}