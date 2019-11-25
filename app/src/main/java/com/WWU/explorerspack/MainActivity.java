package com.WWU.explorerspack;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.WWU.explorerspack.ui.guide.L2.SubChapterData.SubChapterContent;
import com.WWU.explorerspack.ui.logs.HikeFragment;
import com.WWU.explorerspack.ui.logs.MyHikeRecyclerViewAdapter;
import com.WWU.explorerspack.ui.logs.hike_item.HikeList;
import com.WWU.explorerspack.ui.guide.L3.SubChapterFragment;
import com.WWU.explorerspack.utilities.StorageUtilities;
import com.WWU.explorerspack.ui.guide.GuideListFragment;
import com.WWU.explorerspack.ui.guide.ChapterData.ChapterContent;
import com.WWU.explorerspack.ui.guide.L2.ChapterPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jayway.jsonpath.Configuration;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GuideListFragment.OnListFragmentInteractionListener,HikeFragment.OnListFragmentInteractionListener, ChapterPageFragment.OnSubListFragmentInteractionListener{
    private NavController navController;
    private JSONObject guideJSON;
    public String current_title = "Created";
    public MyHikeRecyclerViewAdapter logAdaptor;
    private final int MAX_SEARCH_RESULT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        boolean isFilePresent = StorageUtilities.isFilePresent(this, StorageUtilities.jsonStorageName);
        if (!isFilePresent) {
            // TODO remove include example from final version
            boolean isFileCreated = StorageUtilities.create(this, StorageUtilities.jsonStorageName, StorageUtilities.template(true).toString());
            if (isFileCreated) {
                //proceed with storing the first show ui
            } else {
                Log.e("main", "can't create " + StorageUtilities.jsonStorageName);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_navigation_hike_to_hike_creation);
            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_settings)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.navigation_notifications)
                    fab.show();
                else
                    fab.hide();

            }
        });
        // this.navController = navController;
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        boolean homePageTips = true;
        try{
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            homePageTips = sharedPreferences.getBoolean("home_page_tips", true);
        } catch (Exception e){
            e.printStackTrace();
        }
        if(homePageTips){
            navController.navigate(R.id.help);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Toast.makeText(MainActivity.this, "navigating up from " + current_title, Toast.LENGTH_SHORT).show();
        return navController.navigateUp();//this might not be necessary.
    }


    @Override
    public void onListFragmentInteraction(ChapterContent.ChapterItem chapterItem) {
        //do stuff
        //System.out.println("You pressed " + chapterItem.content);
        ChapterPageFragment newFragment = new ChapterPageFragment();
        Bundle args = new Bundle();
        args.putString("id", chapterItem.id);//pass the id of the chapter item to the new fragment.
        navController.navigate(R.id.action_navigation_home_to_chapter_page, args);
//        newFragment.setArguments(args);
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        transaction.replace(R.id.nav_host_fragment, newFragment);
//        transaction.addToBackStack(null);
//
//        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(HikeList.HikeItem item) {
        Bundle args = new Bundle();
        args.putString("id", item.id);
        navController.navigate(R.id.action_navigation_hike_to_sub_hike_page, args);
    }

    public void setLogAdaptor(MyHikeRecyclerViewAdapter adaptor){
        logAdaptor = adaptor;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
        current_title = title;
    }

    // See above
    private class SearchViewExpandListener implements MenuItemCompat.OnActionExpandListener {

        private Context context;

        public SearchViewExpandListener (Context c) {
            context = c;
        }

        @Override
        public boolean onMenuItemActionExpand(MenuItem item) {
            ((AppCompatActivity) context).getSupportActionBar().setDisplayShowHomeEnabled(true);
            return false;
        }

        @Override
        public boolean onMenuItemActionCollapse(MenuItem item) {
            ((AppCompatActivity) context).getSupportActionBar().setDisplayShowHomeEnabled(false);
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        final String guideRaw = StorageUtilities.loadJSONFromAsset(this);
        final String storageRaw = StorageUtilities.read(this, StorageUtilities.jsonStorageName);
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(guideRaw);
        searchView.setQueryHint("Search this page...");

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do whatever you need
                if(current_title.equals("Guide")){
                    searchView.setQueryHint("Search a topic...");
                } else if (current_title.equals("Logs")) {
                    searchView.setQueryHint("Search a hike name...");
                } else if (current_title.contains(" > ")){
                    searchView.setQueryHint("Search a text...");
                } else {
                    searchView.setQueryHint("Search a section...");
                }
                return true; // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do whatever you need
                if(current_title.equals("Logs")){
                    Toast.makeText(MainActivity.this, "returned item", Toast.LENGTH_SHORT).show();
                    logAdaptor.returnItems();
                }
                return true; // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                if (current_title.equals("Guide")){
                    if (!s.equals("")){
                        // searching in topic
                        JSONObject parsedGuide;
                        // hideItems max 5 results
                        String[] results = new String[MAX_SEARCH_RESULT];
                        int counter = 0;
                        try{
                            parsedGuide = new JSONObject(guideRaw);
                            Iterator<String> keys = parsedGuide.keys();
                            while(keys.hasNext() && counter < MAX_SEARCH_RESULT) {
                                String key = keys.next();
                                if(key.toLowerCase().startsWith(s.toLowerCase())){
                                    results[counter] = key;
                                    counter++;
                                }
                            }
                            Toast.makeText(MainActivity.this, "Found topic: " + results[0], Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else if (current_title.contains(" > ")) {
                    if (!s.equals("")){
                        String[] subsection = current_title.split(" > ");
                        JSONObject parsedGuide;
                        int position;
                        try{
                            parsedGuide = new JSONObject(guideRaw);
                            JSONObject section = parsedGuide.getJSONObject(subsection[0]);
                            String subsectionData = section.getString(subsection[1]);
                            position = subsectionData.indexOf(s);
                            Toast.makeText(MainActivity.this, "Found details at position " + position, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else if(current_title.equals("Logs")) {
                    if (!s.equals("")){
                        logAdaptor.search(s);
                    }
                } else {
                    if(!s.equals("")){
                        // searching in section
                        JSONObject parsedGuide;
                        // hideItems max 5 results
                        String[] results = new String[5];
                        int counter = 0;
                        try{
                            parsedGuide = new JSONObject(guideRaw);
                            JSONObject section = parsedGuide.getJSONObject(current_title);
                            Iterator<String> keys = section.keys();
                            while(keys.hasNext() && counter < 5) {
                                String key = keys.next();
                                if(key.toLowerCase().startsWith(s.toLowerCase())){
                                    results[counter] = key;
                                    counter++;
                                }
                            }
                            Toast.makeText(MainActivity.this, "Found section: " + results[0], Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(current_title.equals("Logs")){
                    logAdaptor.search(s);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.info:
                navController.navigate(R.id.help);
                Log.i("INFO", "info icon was pressed");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onSubListFragmentInteraction(SubChapterContent.SubChapterItem item) {
        //chapter name,id of sub chapter -- name
        SubChapterFragment fragment = new SubChapterFragment();
        Bundle args = new Bundle();
        args.putString("chapter", item.chapter);
        args.putString("subChapter",item.subChapter);
        navController.navigate(R.id.action_navigation_chapter_to_sub_chapter_page, args);
    }
}


