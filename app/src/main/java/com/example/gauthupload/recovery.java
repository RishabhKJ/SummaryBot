package com.example.gauthupload;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class recovery extends AppCompatActivity {

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(recovery.this, MainActivity.class));
        finish();

    }
    EditText mail;
    Button send;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.newback);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        setContentView(R.layout.activity_recovery);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        mail = findViewById(R.id.email1);
        send = findViewById(R.id.reset1);

        send.setOnClickListener(new View.OnClickListener() {
            private boolean isNetworkavailable()
            {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkinfo=connectivityManager.getActiveNetworkInfo();
                return activeNetworkinfo != null && activeNetworkinfo.isConnected();
            }
            @Override
            public void onClick(final View v) {
                String usermail = mail.getText().toString();
                if (isNetworkavailable()) {
                    if (TextUtils.isEmpty(usermail)) {
                        mail.setError("Enter the Email");
                        mail.requestFocus();
                    } else {
                        progressDialog = new ProgressDialog(recovery.this);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.setTitle("Just a Moment");
                        progressDialog.setProgressStyle(0);
                        progressDialog.show();
                        mAuth.sendPasswordResetEmail(usermail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.cancel();
                                    Toast toast = Toast.makeText(recovery.this, "Reset mail is sent check your email inbox ", Toast.LENGTH_SHORT);
                                    toast.show();
                                    startActivity(new Intent(recovery.this, MainActivity.class));
                                } else {
                                    progressDialog.cancel();
                                    String message = task.getException().getMessage();
                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                                    Toast toast = Toast.makeText(recovery.this, "ERROR " + message, Toast.LENGTH_SHORT);
                                    toast.show();

                                }

                            }
                        });
                    }
                }
                else
                {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    Toast.makeText(recovery.this,"No network",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
