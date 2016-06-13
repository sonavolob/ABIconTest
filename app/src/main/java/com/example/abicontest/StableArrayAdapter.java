package com.example.abicontest;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class StableArrayAdapter extends ArrayAdapter<String> {

    final String TAG = getClass().getCanonicalName();
    HashMap<String, Integer> idMap = new HashMap<>();
    ListView listView;
    Activity activity;
    MainActivity mainActivity;
    FragmentManager fragmentManager;
    final List<String> listData;


    public StableArrayAdapter(Activity activity, FragmentManager fragmentManager, int resource,
                              List<String> listData, ListView listView) {
        super(activity, resource, listData);

        this.activity = activity;
        this.mainActivity = (MainActivity) activity;
        this.listData = listData;
        this.fragmentManager = fragmentManager;
        this.listView = listView;
/*        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(multiChoiceModeListener());*/



        if (this.mainActivity.getListActive() ==  MainActivity.ListActive.ELEMENTS) {
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            listView.setOnItemLongClickListener(onItemLongClickListener());

        } else if (this.mainActivity.getListActive() ==  MainActivity.ListActive.ANIMALS) {
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            listView.setOnItemClickListener(onItemClickListener());
        }

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

    private AdapterView.OnItemLongClickListener onItemLongClickListener() {
        return new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selElem = listData.get(listView.getPositionForView(view));
                Log.d(TAG, "c7d48bbf2, onItemLongClick, view: " + view + ", adapterView: "
                        + adapterView + ", i: " + i + ", l: " + l + ", selectedData: '" +
                        selElem + "'");

                android.support.v4.app.DialogFragment newFragment = TouchDialog.newInstance
                        ("onItemLongClick",  selElem);
                newFragment.show(fragmentManager, "touchDialogOnItemLongClick");

//                showModalLongTouchDialog(items);

                return true;
            }
        };
    }

    private AdapterView.OnItemClickListener onItemClickListener() {

        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selElem = listData.get(listView.getPositionForView(view));
                Log.d(TAG, "c7d48bbf2, onItemClickListener, view: " + view + ", adapterView: "
                        + adapterView + ", i: " + i + ", l: " + l + ", selectedData: '" +
                        listData.get(listView.getPositionForView(view)) + "'");

                android.support.v4.app.DialogFragment newFragment = TouchDialog.newInstance
                        ("onItemClickListener", selElem);
                newFragment.show(fragmentManager, "touchDialogOnItemClickListener");
//                showModalLongTouchDialog(items);

            }
        };
    }
}