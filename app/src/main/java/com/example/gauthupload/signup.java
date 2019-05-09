

package com.example.gauthupload;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class signup extends AppCompatActivity {
    private EditText name, email_id, passwordcheck;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    private static final String TAG = "";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.newback);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        TextView btnSignUp = (TextView) findViewById(R.id.login_page);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, MainActivity.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        email_id = (EditText) findViewById(R.id.input_email);
        passwordcheck = (EditText) findViewById(R.id.input_password);
        Button ahsignup = (Button) findViewById(R.id.btn_signup);
        ahsignup.setOnClickListener(new View.OnClickListener() {
            private boolean isNetworkavailable()
            {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkinfo=connectivityManager.getActiveNetworkInfo();
                return activeNetworkinfo != null && activeNetworkinfo.isConnected();
            }

            @Override
            public void onClick(final View v) {
                final String email = email_id.getText().toString();
                String password = passwordcheck.getText().toString();
                if(isNetworkavailable()){
                if (TextUtils.isEmpty(email)) {
                    email_id.setError("Enter the Email");
                    email_id.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordcheck.setError("Enter the Password");
                    passwordcheck.requestFocus();
                    return;
                }
                progressDialog = new ProgressDialog(signup.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle("Just a Moment");
                progressDialog.setProgressStyle(0);
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    progressDialog.cancel();
                                    // Sign in success, update UI with the signed-in user's information
                                    //Toast.makeText(signup.this,"New account created with "+email,Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    mAuth.signOut();

                                    Intent intent = new Intent(signup.this, signup.class);
                                    startActivity(intent);
                                    sendverificationcode(user);

                                    //finish();
                                } else {
                                    String message = task.getException().getMessage();
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    progressDialog.cancel();
                                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                                    Toast toast = Toast.makeText(signup.this,"ERROR "+message,Toast.LENGTH_SHORT);
                                    toast.show();

                                }
                            }
                        });
            }
            else
                {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    Toast.makeText(signup.this,"No network",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendverificationcode(final FirebaseUser user) {
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast toast = Toast.makeText(signup.this,"Verification mail sent. Kindly check your inbox and then Login.",Toast.LENGTH_SHORT);

                toast.show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toast = Toast.makeText(signup.this,"Verification mail not sent.Check your connectivity.",Toast.LENGTH_SHORT);

                toast.show();

            }
        });
    }


}