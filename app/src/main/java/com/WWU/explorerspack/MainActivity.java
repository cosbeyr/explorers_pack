package com.WWU.explorerspack;

import android.os.Bundle;

import com.WWU.explorerspack.ui.guide.GuideListFragment;
import com.WWU.explorerspack.ui.guide.ChapterData.ChapterContent;
import com.WWU.explorerspack.ui.guide.L2.ChapterPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements GuideListFragment.OnListFragmentInteractionListener{
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
       navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       // this.navController = navController;
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
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
