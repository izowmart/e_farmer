package com.example.e_farmer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.example.e_farmer.models.User;
import com.example.e_farmer.models.User_;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.google.android.material.snackbar.Snackbar;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainActivity {

    private Toolbar toolbar;
    private GoogleSignInClient mGoogleSignInClient;
    private long backPressedTime = 0;
    private Box<User> userBox;
    private BoxStore farmerApp;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        objectBox initialization
        farmerApp = FarmerApp.getBoxStore();
        userBox = farmerApp.boxFor(User.class);
        user = userBox.query().build().findFirst();

        // check for authentication. if not signed in,send to login activity
        if (!Settings.isLoggedIn()) {
            Intent sendToLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(sendToLogin);
            finish();

            Toast.makeText(this, "Login required", Toast.LENGTH_SHORT).show();

        }

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        if (Settings.isFacebook()) {
            new GraphRequest(AccessToken.getCurrentAccessToken(), "me/permissions/", null, HttpMethod.DELETE, new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {

                    LoginManager.getInstance().logOut();
//                    Clear shared pref file
                    Settings.setLoggedInSharedPref(false);
//                    clear local DB
                    userBox.removeAll();
                    Toast.makeText(MainActivity.this, "Logging out", Toast.LENGTH_LONG).show();
//                    Redirect user to login page
                    Intent intent = new Intent(MainActivity.this.getApplicationContext(), LoginActivity.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();

                }
            }).executeAsync();
        } else {
            mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    //Clear Shared Pref File
                    Settings.setLoggedInSharedPref(false);
                    //Clear Local DB
                    userBox.removeAll();
                    Toast.makeText(MainActivity.this, "Logging out", Toast.LENGTH_LONG).show();
                    //Redirect User to Login Page
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
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
