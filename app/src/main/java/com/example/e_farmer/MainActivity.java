package com.example.e_farmer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_farmer.models.User;
import com.example.e_farmer.viewmodels.UserViewModel;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_farmer.fragments.MyAnimalTreatment;
import com.example.e_farmer.fragments.MyAnimals;
import com.example.e_farmer.fragments.MyDashboard;
import com.example.e_farmer.fragments.MyFarmMachinery;
import com.example.e_farmer.fragments.MyFarmTasks;
import com.example.e_farmer.fragments.MyLandAndCropMngt;
import com.example.e_farmer.fragments.FinanceManagement;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainActivity {
    private static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private GoogleSignInClient mGoogleSignInClient;
    private long backPressedTime = 0;
    private User user;

    private TextView mUsername, mEmail;
    private de.hdodenhof.circleimageview.CircleImageView navProfile;
    private DrawerLayout drawer;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

        String user_id = Settings.getUserId();

        // check for authentication. if not signed in,send to login activity
        if (!Settings.isLoggedIn()) {
            Toast.makeText(this, "Login required", Toast.LENGTH_SHORT).show();
            goToLoginActivity();



        }
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        user = userViewModel.getCurrentUser(user_id);

        Log.d(TAG, "onCreate: " + mEmail + mUsername + navProfile);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        toolbar = findViewById(R.id.toolbar_main);
//        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

//        very important when referencing ids
        View headerView = navigationView.getHeaderView(0);

        mUsername = headerView.findViewById(R.id.nav_bar_username);
        mEmail = headerView.findViewById(R.id.nav_bar_email);
        navProfile = headerView.findViewById(R.id.nav_bar_profile);

        mUsername.setText(user.getName());
        mEmail.setText(user.getEmail());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.avatar);

        Glide.with(getApplicationContext())
                .applyDefaultRequestOptions(options)
                .load(user.getImageUrl())
                .into(navProfile);

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

        } else if (id == R.id.farm_machinery) {
            doFragmentTransaction(new MyFarmMachinery(), getString(R.string.farm_machinery), false);

        } else if (id == R.id.finance_management) {
            doFragmentTransaction(new FinanceManagement(), getString(R.string.finance_management), false);

        } else if (id == R.id.profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

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
                    Toast.makeText(MainActivity.this, "Logging out", Toast.LENGTH_LONG).show();
                    LoginManager.getInstance().logOut();
//                    Clear shared pref file
                    Settings.setLoggedInSharedPref(false);
                    goToLoginActivity();

                }
            }).executeAsync();
        } else {
            mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(MainActivity.this, "Logging out", Toast.LENGTH_LONG).show();
                    //Clear Shared Pref File
                    Settings.setLoggedInSharedPref(false);
                    goToLoginActivity();
                }
            });
        }
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
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
        } else if (fragmentTag.equals(getString(R.string.finance_management))) {
            doFragmentTransaction(new FinanceManagement(), fragmentTag, false);
        } else if (fragmentTag.equals(getString(R.string.farm_machinery))) {
            doFragmentTransaction(new MyFarmMachinery(), fragmentTag, false);
        }

    }
}
