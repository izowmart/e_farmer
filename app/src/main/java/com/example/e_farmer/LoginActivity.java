package com.example.e_farmer;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.e_farmer.models.User;
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

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private static final String EMAIL = "email";
    public static final int RC_SIGN_IN = 111;
    public static final String PROFILE_IMAGE = "public_profile";
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String first_name, last_name, email, id;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private String userId;

    private Box<User> userBox;
    private BoxStore farmerApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//      objectBox
        farmerApp = FarmerApp.getBoxStore();
        userBox = farmerApp.boxFor(User.class);

//        google
        signInButton = findViewById(R.id.mGoogleLogin);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        //        facebook
        loginButton = findViewById(R.id.mFacebookLogin);
        loginButton.setReadPermissions(Arrays.asList(EMAIL, PROFILE_IMAGE));

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
                parameters.putString("fields", "id,name,link");
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
            String imageUrl = String.valueOf(account.getPhotoUrl());

            /*
             * Save to ObjectBox ORM
             * Set the Logged in status to true
             * Navigate user to Main Activity
             */
            User user = new User();
            user.setName(name);
            user.setImageUrl(imageUrl);
            userBox.put(user);

            Log.d(TAG, "handleSignInResult: "  + userBox.count());

            Settings.setLoggedInSharedPref(true);
            Settings.setIsFacebook(false);

            Intent login = new Intent(LoginActivity.this, MainActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(login);
            finish();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void displayUserInfo(JSONObject object) {
        try {
            String name = object.getString("name");
            String image_url = "http://graph.facebook.com/" + userId + "/picture?type=large";

            /*
             * Save to ObjectBox ORM
             * Set the Logged in status to true
             * Navigate user to Main Activity
             */
            User user = new User();
            user.setName(name);
            user.setImageUrl(image_url);
            userBox.put(user);

            Log.d(TAG, "displayUserInfo: user count" + userBox.count());

            Settings.setLoggedInSharedPref(true);
            Settings.setIsFacebook(true);

            Intent login = new Intent(LoginActivity.this, MainActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(login);
            finish();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
