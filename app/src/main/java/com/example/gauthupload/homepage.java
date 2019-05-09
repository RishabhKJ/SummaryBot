package com.example.gauthupload;

import android.Manifest;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.auth.FirebaseAuth;

public class homepage extends AppCompatActivity {
    private static final int STORAGE_CODE = 1000;
    Button copy,file,sout,mvoice,history;
    FirebaseAuth mauth;
    private Boolean exit1 = false;
    @Override
    public void onBackPressed() {
        if (exit1) {
            moveTaskToBack(true);

        } else {

            Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
            exit1 = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit1=false;
                }
            }, 2000);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_homepage);
        copy = findViewById(R.id.copy);
        file = findViewById(R.id.file);
        mvoice=findViewById(R.id.voice);
        mauth=FirebaseAuth.getInstance();
        sout = findViewById(R.id.signout);
        history = findViewById(R.id.historya);
        sout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();

            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                copyAct();
            }
        });
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[] permssions =  {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permssions, STORAGE_CODE);
                    }
                    else
                    {finish();
                        fileAct();
                    }
                }
                else
                {
                    finish();
                    fileAct();
                }

            }
        });
        mvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                voiceAct();
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            private boolean isNetworkavailable()
            {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkinfo=connectivityManager.getActiveNetworkInfo();
                return activeNetworkinfo != null && activeNetworkinfo.isConnected();
            }
            @Override
            public void onClick(View v) {
                if(isNetworkavailable()){
                finish();
                history();
            }
            else
                {
                    Toast.makeText(homepage.this,"No network",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void signout() {

        mauth.signOut();
        finish();
        launchacitvity();
    }

    private void history() {
        Intent intent = new Intent(this, history.class);
        startActivity(intent);
    }

    private void voiceAct() {
        Intent intent = new Intent(this, voice.class);
        startActivity(intent);
    }

    private void launchacitvity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void copyAct(){
        Intent intent = new Intent(this, copy.class);
        startActivity(intent);
    }
    private void fileAct(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    finish();
                    fileAct();

                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }


            }}}
    @Override
    protected void onStart() {
        super.onStart();
        if(mauth.getCurrentUser()==null)
        {

            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
