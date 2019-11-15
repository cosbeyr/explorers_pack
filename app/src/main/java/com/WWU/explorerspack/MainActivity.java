package com.WWU.explorerspack;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import com.WWU.explorerspack.ui.guide.GuideListFragment;
import com.WWU.explorerspack.ui.guide.ChapterData.ChapterContent;
import com.WWU.explorerspack.ui.guide.L2.ChapterPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Dictionary;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GuideListFragment.OnListFragmentInteractionListener{
    private NavController navController;


    private boolean create(Context context, String fileName, String jsonString){
        String FILENAME = "storage.json";
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }

    }

    public boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean isFilePresent = isFilePresent(this, "storage.json");
        if(!isFilePresent) {
            JSONObject template = new JSONObject();
            JSONObject settings = new JSONObject();
            JSONObject hikeLogs = new JSONObject();
            JSONObject hike = new JSONObject();
            JSONObject photo = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            try {
                photo.put("JPEG_20191114_152929_8217285707265593563.jpg", "/storage/emulated/0/JPEG_20191114_152929_8217285707265593563.jpg");
                jsonArray.put(photo);
            }  catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                settings.put("saveToGallery", true);
                settings.put("homePageTips", true);
                settings.put("darkMode", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                hike.put("notes", "example notes...");
                hike.put("map","map path on disk for processing");
                hike.put("photos", jsonArray);
                hikeLogs.put("example hike", hike);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                template.put("settings", settings);
                template.put("hikeLogs", hikeLogs);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            boolean isFileCreated = create(this, "storage.json", template.toString());
            if(isFileCreated) {
                //proceed with storing the first todo  or show ui
            } else {
                Log.e("main","can't create storage.json");
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
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        return navController.navigateUp();//this might not be necessary.
    }

    @Override
    public void onListFragmentInteraction(ChapterContent.ChapterItem chapterItem){
        //do stuff
        //System.out.println("You pressed " + chapterItem.content);
        ChapterPageFragment newFragment = new ChapterPageFragment();
        Bundle args = new Bundle();
        args.putString("id", chapterItem.id);//pass the id of the chapter item to the new fragment.
        navController.navigate(R.id.action_navigation_home_to_chapter_page,args);
//        newFragment.setArguments(args);
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        transaction.replace(R.id.nav_host_fragment, newFragment);
//        transaction.addToBackStack(null);
//
//        transaction.commit();
    }

}
