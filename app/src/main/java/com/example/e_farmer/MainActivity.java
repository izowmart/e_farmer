package com.example.e_farmer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.example.e_farmer.models.User;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Toast;

import com.example.e_farmer.fragments.MyAnimalTreatment;
import com.example.e_farmer.fragments.MyAnimals;
import com.example.e_farmer.fragments.MyDashboard;
import com.example.e_farmer.fragments.MyFarmMachinery;
import com.example.e_farmer.fragments.MyFarmManagement;
import com.example.e_farmer.fragments.MyFarmTasks;
import com.example.e_farmer.fragments.MyLandAndCropMngt;
import com.example.e_farmer.fragments.MyWorkersTimesheet;

import io.objectbox.Box;
import io.objectbox.query.Query;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainActivity {

    private Toolbar toolbar;

    private long backPressedTime = 0;
    private Box<User> userBox;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        check for authentication. if not signed in,send to login activity
        if (!Settings.isLoggedIn()) {
            Intent sendToLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(sendToLogin);
            finish();

            Toast.makeText(this, "Login required", Toast.LENGTH_SHORT).show();

        }
        toolbar = findViewById(R.id.toolbar_main);
//        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        init();
    }

    //    This method inflates the selector fragment fast to our container so as to allow other transactions to take place there.vry dope!
    private void init() {
        MyDashboard fragment = new MyDashboard();
        doFragmentTransaction(fragment, getString(R.string.dashboard), false);
        toolbar.setTitle(getString(R.string.dashboard));
    }

    public void doFragmentTransaction(Fragment fragment, String tag, Boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        The third parameter is set so that we can be able to setTag in our fragment.
        transaction.replace(R.id.container, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        long t = System.currentTimeMillis();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();       // bye
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dashboard) {
            doFragmentTransaction(new MyDashboard(), getString(R.string.dashboard), false);
        } else if (id == R.id.animals) {
            doFragmentTransaction(new MyAnimals(), getString(R.string.animals), false);

        } else if (id == R.id.animal_treatment) {
            doFragmentTransaction(new MyAnimalTreatment(), getString(R.string.animal_treatment), false);

        } else if (id == R.id.land_management) {
            doFragmentTransaction(new MyLandAndCropMngt(), getString(R.string.crop_management), false);

        } else if (id == R.id.farm_tasks) {
            doFragmentTransaction(new MyFarmTasks(), getString(R.string.farm_tasks), false);

        } else if (id == R.id.farm_management) {
            doFragmentTransaction(new MyFarmManagement(), getString(R.string.farm_management), false);

        } else if (id == R.id.farm_machinery) {
            doFragmentTransaction(new MyFarmMachinery(), getString(R.string.farm_machinery), false);

        } else if (id == R.id.workers_timesheet) {
            doFragmentTransaction(new MyWorkersTimesheet(), getString(R.string.workers_time_sheet), false);

        } else if (id == R.id.profile) {
            Intent intent = new Intent(this.getApplicationContext(), ProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
//            TODO CALL THE SESSION OFF USING SHARED PREFFERNCE

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setToolbarTitle(String fragmentTag) {
        toolbar.setTitle(fragmentTag);
    }

    @Override
    public void inflateFragment(String fragmentTag) {

        if (fragmentTag.equals(getString(R.string.animals))) {
            doFragmentTransaction(new MyAnimals(), fragmentTag, false);
        } else if (fragmentTag.equals(getString(R.string.farm_tasks))) {
            doFragmentTransaction(new MyFarmTasks(), fragmentTag, false);
        } else if (fragmentTag.equals(getString(R.string.animal_treatment))) {
            doFragmentTransaction(new MyAnimalTreatment(), fragmentTag, false);
        } else if (fragmentTag.equals(getString(R.string.crop_management))) {
            doFragmentTransaction(new MyLandAndCropMngt(), fragmentTag, false);
        } else if (fragmentTag.equals(getString(R.string.workers_time_sheet))) {
            doFragmentTransaction(new MyWorkersTimesheet(), fragmentTag, false);
        } else if (fragmentTag.equals(getString(R.string.farm_management))) {
            doFragmentTransaction(new MyFarmManagement(), fragmentTag, false);
        } else if (fragmentTag.equals(getString(R.string.farm_machinery))) {
            doFragmentTransaction(new MyFarmMachinery(), fragmentTag, false);
        }

    }
}
