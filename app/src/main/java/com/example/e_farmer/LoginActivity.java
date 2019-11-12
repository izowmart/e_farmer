package com.example.e_farmer;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.e_farmer.models.User;
import com.example.e_farmer.viewmodels.UserViewModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private static final String EMAIL = "email";
    public static final int RC_SIGN_IN = 111;
    public static final String PROFILE_IMAGE = "public_profile";
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private String userId;
    private User user;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

//        google
        signInButton = findViewById(R.id.mGoogleLogin);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        //        facebook
        loginButton = findViewById(R.id.mFacebookLogin);
        loginButton.setReadPermissions(Arrays.asList(EMAIL, PROFILE_IMAGE));

        //viewmodel
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                userId = loginResult.getAccessToken().getUserId();
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        displayUserInfo(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,link");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Settings.isLoggedIn()) {
            goToMainActivity();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    //    google's
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            assert account != null;

            String name = account.getDisplayName();
            String email = account.getEmail();
            String user_id = account.getId();
            String imageUrl = String.valueOf(account.getPhotoUrl());

            /*
             * Save to Room ORM
             * Set the Logged in status to true
             * Navigate user to Main Activity
             */
            user = userViewModel.getCurrentUser(user_id);
            //check for user existence in database
            if (user == null){
                //Then this an new user.Add him to database
                user = new User(user_id,name, email, imageUrl);
                userViewModel.insert(user);

                Settings.setUserId(user_id);
                Settings.setLoggedInSharedPref(true);
                Settings.setIsFacebook(false);

                goToMainActivity();
            }else{
                Settings.setUserId(user_id);
                Settings.setLoggedInSharedPref(true);
                Settings.setIsFacebook(false);

                goToMainActivity();
            }

            Log.d(TAG, "handleSignInResult: inserting a user to DB using google");

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void goToMainActivity() {
        Intent login = new Intent(LoginActivity.this, MainActivity.class);
//        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void displayUserInfo(JSONObject object) {
        try {
            String name = object.getString("name");
            String email = object.getString("email");
            String image_url = "http://graph.facebook.com/" + userId + "/picture?type=large";
            /*
             * Save to Room ORM
             * Set the Logged in status to true
             * Navigate user to Main Activity
             */

            user = userViewModel.getCurrentUser(userId);
            //check for user existence in database
            if (user == null){
                //Then this an new user.Add him to database
                user = new User(userId,name, email, image_url);
                userViewModel.insert(user);

                Settings.setUserId(userId);
                Settings.setLoggedInSharedPref(true);
                Settings.setIsFacebook(true);

                goToMainActivity();
            }else{
                Settings.setUserId(userId);
                Settings.setLoggedInSharedPref(true);
                Settings.setIsFacebook(true);

                goToMainActivity();
            }

            Log.d(TAG, "displayUserInfo: user count inserting user via facebook to Db:");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
