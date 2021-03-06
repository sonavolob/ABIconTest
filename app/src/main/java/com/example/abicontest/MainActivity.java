package com.example.abicontest;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;

import java.util.Arrays;
import java.util.List;

import io.fabric.sdk.android.Fabric;
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
    private StableArrayAdapter stableArrayAdapter;
    private Activity activity;
    private ListView listView;

    private List<String> listDataElements, listDataAnimals;

    public ListActive getListActive() {
        return listActive;
    }

    public void setListActive(ListActive listActive) {
        this.listActive = listActive;
    }

    private ListActive listActive;
    public enum ListActive {

        ELEMENTS,
        ANIMALS

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        activity = this;


        listDataElements = Arrays.asList("Hydrogen", "Helium", "Lithium", "Beryllium",
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

        listDataAnimals = Arrays.asList("alligator", "alpaca", "ant", "antelope",
                "ape", "armadillo", "baboon", "badger", "bat", "bear", "beaver", "bee",
                "beetle", "buffalo", "butterfly", "camel", "carabao", "caribou", "cat", "cattle",
                "cheetah", "chimpanzee", "chinchilla", "cicada", "clam", "cockroach", "cod",
                "coyote", "crab", "cricket", "crow", "deer", "dinosaur", "dog", "dolphin",
                "duck",  "eel", "elephant", "elk", "ferret", "fish", "fly", "fox", "frog",
                "gerbil",  "giraffe", "gnat", "gnu", "goat", "goldfish", "gorilla",
                "grasshopper",  "guinea pig", "hamster", "hare", "hedgehog", "herring",
                "hippopotamus", "hornet", "horse", "hound", "hyena", "impala", "insect",
                "jackal",  "jellyfish", "kangaroo", "koala", "leopard", "lion", "lizard",
                "llama",  "locust", "louse", "mallard", "mammoth", "manatee", "marten", "mink",
                "minnow", "mole", "monkey", "moose", "mosquito", "mouse", "mule", "muskrat",
                "otter", "ox", "oyster", "panda", "pig", "platypus", "porcupine", "prairie dog",
                "pug", "rabbit", "raccoon", "reindeer", "rhinoceros", "salmon", "sardine",
                "scorpion", "seal", "serval", "shark", "sheep", "skunk", "snail", "snake",
                "spider", "squirrel", "termite", "tiger", "trout", "turtle", "walrus", "wasp",
                "weasel", "whale", "wolf", "wombat", "woodchuck", "worm", "yak", "yellow jacket",
                "zebra");

        listView = (ListView) this.findViewById(R.id.results);
        stableArrayAdapter = new StableArrayAdapter(this,
                getSupportFragmentManager(), R.id.results, listDataElements, listView);
        listView.setAdapter(stableArrayAdapter);
        setListActive(ListActive.ELEMENTS);


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

                switchActiveList();

                if (getListActive() == ListActive.ELEMENTS) {
                    Log.d(TAG, "onClick: ELEMENTS");

                    ListView listView = (ListView) activity.findViewById(R.id.results);
                    stableArrayAdapter = new StableArrayAdapter(activity,
                            getSupportFragmentManager(), R.id.results, listDataElements, listView);
                    listView.setAdapter(stableArrayAdapter);

                    floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R
                            .drawable.ic_fab_e));

                } else if (getListActive() == ListActive.ANIMALS) {
                    Log.d(TAG, "onClick: ANIMALS");

                    ListView listView = (ListView) activity.findViewById(R.id.results);
                    stableArrayAdapter = new StableArrayAdapter(activity,
                            getSupportFragmentManager(), R.id.results, listDataAnimals, listView);
                    listView.setAdapter(stableArrayAdapter);

                    floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R
                            .drawable.ic_fab_a));
                }

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

                searchView.setActivated(true);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        MenuInflater inflater = getMenuInflater();

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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



    private void switchActiveList() {
        if (getListActive() == ListActive.ELEMENTS) {
            setListActive(ListActive.ANIMALS);
        } else {
            setListActive(ListActive.ELEMENTS);
        }

    }

}
