package com.example.abicontest;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.Arrays;
import java.util.List;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "MainActivity";

    static final String STATE_SEARCH_VIEW_QUERY = "actualSearchView";
    static final String STATE_SEARCH_VIEW_ISICONIFIED = "actualSearchViewIconifiedState";
    static final String STATE_SEARCH_VIEW_ISFOCUSED = "actualSearchViewIsFocusableState";

    private SearchView searchView;
    private Bundle transferredInstanceState = null;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<String> listData = Arrays.asList("Hydrogen", "Helium", "Lithium", "Beryllium",
                "Boron", "Carbon",
                "Nitrogen", "Oxygen", "Fluorine", "Neon", "Sodium", "Magnesium", "Aluminium",
                "Silicon", "Phosphorus", "Sulfur", "Chlorine", "Argon", "Potassium", "Calcium",
                "Scandium", "Titanium", "Vanadium", "Chromium", "Manganese", "Iron", "Cobalt",
                "Nickel", "Copper", "Zinc", "Gallium", "Germanium", "Arsenic", "Selenium",
                "Bromine", "Krypton", "Rubidium", "Strontium", "Yttrium", "Zirconium", "Niobium",
                "Molybdenum", "Technetium", "Ruthenium", "Rhodium", "Palladium", "Silver",
                "Cadmium", "Indium", "Tin", "Antimony", "Tellurium", "Iodine", "Xenon",
                "Caesium", "Barium", "Lanthanum", "Cerium", "Praseodymium", "Neodymium",
                "Promethium", "Samarium", "Europium", "Gadolinium", "Terbium", "Dysprosium",
                "Holmium", "Erbium", "Thulium", "Ytterbium", "Lutetium", "Hafnium", "Tantalum",
                "Tungsten", "Rhenium", "Osmium", "Iridium", "Platinum", "Gold", "Mercury",
                "Thallium", "Lead", "Bismuth", "Polonium", "Astatine", "Radon", "Francium",
                "Radium", "Actinium", "Thorium", "Protactinium", "Uranium", "Neptunium",
                "Plutonium", "Americium", "Curium", "Berkelium", "Californium", "Einsteinium",
                "Fermium", "Mendelevium", "Nobelium", "Lawrencium", "Rutherfordium", "Dubnium",
                "Seaborgium", "Bohrium", "Hassium", "Meitnerium", "Darmstadtium", "Roentgenium",
                "Copernicium", "Ununtrium", "Flerovium", "Ununpentium", "Livermorium",
                "Ununseptium", "Ununoctium");

        StableArrayAdapter stableArrayAdapter = new StableArrayAdapter(this, R.id.results, listData);
        ListView listView = (ListView) this.findViewById(R.id.results);
        listView.setAdapter(stableArrayAdapter);


        if (savedInstanceState != null) {
            transferredInstanceState = savedInstanceState;
        }

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*  list.add("New Item");

                adapter.notifyDataSetChanged();*/
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R
                        .mipmap.ic_gps));
                Log.d(TAG, "_--FAB pressed");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);

        //  get display Metrics and calculate width
        DisplayMetrics metrics = new DisplayMetrics();
        Log.d(TAG, "_--displayDimension: " + getResources().getDisplayMetrics());
        int displayWidthPixels = getResources().getDisplayMetrics().widthPixels;
        Log.d(TAG, "_--widthPixels: " + getResources().getDisplayMetrics().widthPixels);

        //  create searchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        searchView.setMaxWidth((int) (displayWidthPixels * 0.6));




//        menu.findItem(R.id.action_filter).setIcon(R.drawable.ic_filter_list_24dp);


                /*
         * Info - this preserve state of search result and searchView after screen rotation.
          * I thought it might be reset from onRestoreInstanceState, but onCreateOptionsMenu
          * runs after onRestoreInstanceState. Each screen rotate goes through these basic parts
          * of apps live cycle: onSaveInstanceState, onStop, onCreate, onRestoreInstanceState,
            * onResume, onCreateOptionsMenu. Haven't found way how not to throw out all instances
            * created on onCreate from the previous screen orientation. Except not suggested:
            * http://developer.android.com/guide/topics/resources/runtime-changes.html
            * onConfigurationChanged.
            * Other sources to this topic:
            * http://stackoverflow.com/questions/22498344/is-there-a-better-way-to-restore-searchview-state
            * http://stackoverflow.com/questions/456211/activity-restart-on-rotation-android
            * http://stackoverflow.com/questions/7618703/activity-lifecycle-oncreate-called-on-every-re-orientation
            * http://developer.android.com/training/basics/activity-lifecycle/recreating.html#RestoreState
            * http://stackoverflow.com/questions/3014089/maintain-save-restore-scroll-position-when-returning-to-a-listview/5694441#5694441
            *
         */

        //  TODO - transferredInstanceState.getCharSequence(STATE_SEARCH_VIEW_QUERY) sometimes
        //  crash with java.lang.NullPointerException - unknown reason
        try {
            if (transferredInstanceState != null) {

                searchView.setQuery(transferredInstanceState
                        .getCharSequence(STATE_SEARCH_VIEW_QUERY), false);

                searchView.setIconified(
                        transferredInstanceState.getBoolean(STATE_SEARCH_VIEW_ISICONIFIED));
                Log.d(TAG, "searchView.getMaxWidth(): " + searchView.getMaxWidth());
                if (transferredInstanceState.getBoolean(STATE_SEARCH_VIEW_ISFOCUSED)) {
                    searchView.requestFocus();
                }
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "NullPointerException - problem on loading SavedInstanceState.");
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if (searchView != null) {
            savedInstanceState.putCharSequence(STATE_SEARCH_VIEW_QUERY, searchView.getQuery());

            savedInstanceState.putBoolean(STATE_SEARCH_VIEW_ISICONIFIED, searchView.isIconified());
            savedInstanceState.putBoolean(STATE_SEARCH_VIEW_ISFOCUSED, checkFocusRec(searchView));
        }
    }

    /**
     * Info - checks whether view is focused or not, isFocused pointed to searchView was not
     * working so this solution comes from
     * http://stackoverflow.com/questions/24082745/searchview-isfocused-always-returns-false
     * @param view View
     * @return boolean
     */
    private boolean checkFocusRec(View view) {
        if (view.isFocused())
            return true;

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (checkFocusRec(viewGroup.getChildAt(i)))
                    return true;
            }
        }
        return false;
    }



    /**
     * Function - this is wrapper to change font in this activity using calligraphy
     * @param newBase Context
     */
    @Override
    protected void attachBaseContext(Context newBase) {

        //  Defining default calligraphy font
        //  formally it was in onCreate, but when app is killed due to low memory, and recreated
        //  later font is not loaded in attachBaseContext, because onCreate run after
        //  AttachBaseContext
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/GoodDog.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        Log.d(TAG, "attachBaseContext, newBase: " + newBase);
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
