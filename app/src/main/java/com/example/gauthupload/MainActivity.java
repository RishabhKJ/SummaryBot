package com.example.gauthupload;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 5000;
    private static final String TAG = "";
    private EditText inputEmail, inputPassword;
    private FirebaseAuth mAuth;
    ImageView button;
    private final static int RC_SIGN_IN = 123;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener mAuthListner;
    ProgressDialog progressDialog,progressDialog1;
    private Boolean exit = false;


    @Override
    public void onBackPressed() {
        if (exit) {
            moveTaskToBack(true);

        } else {

            Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit=false;
                }
            }, 2000);
        }
    }




    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawableResource(R.drawable.newback);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        //check the current user
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, homepage.class));
            finish();
        }
        setContentView(R.layout.activity_main);


        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        Button ahlogin = (Button) findViewById(R.id.ah_login);

        TextView btnSignIn = (TextView) findViewById(R.id.sign_in_button);
        TextView needhelp = findViewById(R.id.needhelp);
        button = findViewById(R.id.sign_in_google);
        button.setOnClickListener(new View.OnClickListener() {
            private boolean isNetworkavailable()
            {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkinfo=connectivityManager.getActiveNetworkInfo();
                return activeNetworkinfo != null && activeNetworkinfo.isConnected();
            }

            @Override
            public void onClick(View v) {
                if (isNetworkavailable()){
                signIn();}
                else{
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    Toast.makeText(MainActivity.this,"No Network",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, signup.class));
            }
        });
        needhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,recovery.class));
            }
        });
        mAuth = FirebaseAuth.getInstance();
        // Checking the email id and password is Empty
        ahlogin.setOnClickListener(new View.OnClickListener() {
            private boolean isNetworkavailable()
            {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkinfo=connectivityManager.getActiveNetworkInfo();
                return activeNetworkinfo != null && activeNetworkinfo.isConnected();
            }
            @Override
            public void onClick(final View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                if (isNetworkavailable()) {
                    if (TextUtils.isEmpty(email)) {

                        inputEmail.setError("Enter the Email");
                        inputEmail.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        inputPassword.setError("Enter the Password");
                        inputPassword.requestFocus();
                        //                   Toast toast = Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT);
//                    View view = toast.getView();
//                    TextView  view1=view.findViewById(android.R.id.message);
//                    view1.setTextColor(Color.WHITE);
//                    view.setBackgroundColor(Color.RED);
//                    toast.show();
                        return;
                    }
                    progressDialog1 = new ProgressDialog(MainActivity.this);
                    progressDialog1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog1.setTitle("Just a Moment");
                    progressDialog1.setProgressStyle(0);
                    progressDialog1.show();

                    //authenticate user
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        // there was an error
                                        progressDialog1.cancel();
                                        if (mAuth.getCurrentUser().isEmailVerified()) {
                                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                                            Toast toast = Toast.makeText(MainActivity.this, "Sign in Success with " + email, Toast.LENGTH_SHORT);

                                            toast.show();
                                            Intent intent = new Intent(MainActivity.this, homepage.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            mAuth.signOut();
                                            progressDialog1.cancel();
                                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                                            Toast.makeText(MainActivity.this, "Your email " + email + " is not verified.", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        String message = task.getException().getMessage();
                                        progressDialog1.cancel();
                                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                                        Toast toast = Toast.makeText(MainActivity.this, "ERROR " + message, Toast.LENGTH_LONG);

                                        toast.show();
                                    }
                                }
                            });
                }
                else
                {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    Toast.makeText(MainActivity.this,"No Network",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, homepage.class));
                }
            }
        };
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1002394355883-miunmindmum35fir0rv3ottcg2geopk2.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    private void signIn() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                        startActivityForResult(signInIntent, RC_SIGN_IN);

                    }
                });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Just a Moment");
        progressDialog.setProgressStyle(0);
        progressDialog.show();

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);



            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                Toast toast = Toast.makeText(MainActivity.this,"Sign in Failed",Toast.LENGTH_SHORT);

                toast.show();
                progressDialog.cancel();
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        final String personEmail = account.getEmail();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            progressDialog.cancel();
                            // Sign in success, update UI with the signed-in user's information

                            Toast toast = Toast.makeText(MainActivity.this,"Sign in Success with "+personEmail,Toast.LENGTH_SHORT);

                            toast.show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {

                            // If sign in fails, display a message to the user.
                            String message = task.getException().getMessage();
                            Toast toast = Toast.makeText(MainActivity.this, "ERROR "+message, Toast.LENGTH_SHORT);

                            toast.show();
                            progressDialog.cancel();
                            //updateUI(null);
                        }
                        // ...
                    }
                });
    }
}