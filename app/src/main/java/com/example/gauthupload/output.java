package com.example.gauthupload;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class output extends AppCompatActivity {

    TextView optxt;
    TextToSpeech mTTS;
    SeekBar mSeekBarPitch;
    SeekBar mSeekBarSpeed;
    String TAG ="Hello";
    private ImageView mButtonSpeak;
    int result;
    String text;
    Button start,back,back1;
    ImageView save,share,stop;
    private static final int STORAGE_CODE = 1000 ;
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(output.this, history.class));
        finish();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_output);
        optxt = findViewById(R.id.opbox);
        optxt.setMovementMethod(new ScrollingMovementMethod());
        mButtonSpeak = findViewById(R.id.button_speak);


        share = findViewById(R.id.share);
        getIncomingIntent();

        save = findViewById(R.id.conpdf);
        stop = findViewById(R.id.button_stop);
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = mTTS.setLanguage(Locale.UK);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {

                        mButtonSpeak.setEnabled(true);
                        stop.setEnabled(true);

                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                speak();

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M)
                {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[] permssions =  {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permssions, STORAGE_CODE);
                    }
                    else
                    {
                        savePdf();
                    }
                }
                else
                {
                    savePdf();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTTS != null) {

                    mTTS.stop();
                }

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String sharemy = optxt.getText().toString();
                if(!TextUtils.isEmpty(sharemy)) {
                    Intent msharingintent = new Intent(Intent.ACTION_SEND);
                    msharingintent.setType("text/plain");
                    msharingintent.putExtra(Intent.EXTRA_SUBJECT, "My summary");
                    msharingintent.putExtra(Intent.EXTRA_TEXT, sharemy);

                    startActivity(Intent.createChooser(msharingintent, "Share Summary via"));
                }
                else {
                    Toast.makeText(output.this,"Summary not found",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void speak() {
        String text = optxt.getText().toString();



        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        int dividerLimit = 3900;
        if(text.length() >= dividerLimit) {
            int textLength = text.length();
            ArrayList<String> texts = new ArrayList<String>();
            int count = textLength / dividerLimit + ((textLength % dividerLimit == 0) ? 0 : 1);
            int start = 0;
            int end = text.indexOf(" ", dividerLimit);
            for(int i = 1; i<=count; i++) {
                texts.add(text.substring(start, end));
                start = end;
                if((start + dividerLimit) < textLength) {
                    end = text.indexOf(" ", start + dividerLimit);
                } else {
                    end = textLength;
                }
            }
            try {
                for (int i = 0; i < texts.size(); i++) {
                    mTTS.speak(texts.get(i), TextToSpeech.QUEUE_ADD, null);
                }
            }
            catch (Exception e)
            {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        } else {
            try {
                mTTS.speak(text, TextToSpeech.QUEUE_ADD, null);
            }
            catch (Exception e)
            {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchhistory() {
        Intent intent = new Intent(this, history.class);
        startActivity(intent);
    }

    private void launchmain() {
        Intent intent = new Intent(this, homepage.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTTS != null) {
            Log.d(TAG, "onDestroy: ");
            mTTS.stop();
            mTTS.shutdown();

        }


    }
    @Override
    protected  void onStop(){
        super.onStop();
        if (mTTS != null) {
            Log.d(TAG, "onstopped: ");
            mTTS.stop();
            mTTS.shutdown();

        }


    }
    @Override
    protected  void onPause(){
        super.onPause();
        if (mTTS != null) {
            Log.d(TAG, "onpaused: ");
            mTTS.stop();
            mTTS = null;

        }


    }
    @Override
    protected  void onResume(){
        super.onResume();
        if (mTTS == null) {
            Log.d(TAG, "Resumed: ");
            mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {

                        int result = mTTS.setLanguage(Locale.UK);

                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "Language not supported");
                        } else {

                            mButtonSpeak.setEnabled(true);
                            stop.setEnabled(true);

                        }
                    } else {
                        Log.e("TTS", "Initialization failed");
                    }
                }

            });


        }


    }
    private void getIncomingIntent(){


        if(getIntent().hasExtra("yoursum")){

            String sum = getIntent().getStringExtra("yoursum");
            settx(sum);
        }
    }
    private void settx(String sum){


        optxt.setText(sum);

    }

    private void savePdf() {
        Document mdoc = new Document();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String fileName="Summary"+System.currentTimeMillis()+"-"+currentFirebaseUser.getUid();
        String mFilepath = Environment.getExternalStorageDirectory() + "/" + fileName + ".pdf";

        try
        {
            PdfWriter.getInstance(mdoc,new FileOutputStream(mFilepath));
            mdoc.open();
            String mText = optxt.getText().toString();
            mdoc.add(new Paragraph(mText));
            mdoc.close();
            Toast.makeText(this,"Your File is Downloaded",Toast.LENGTH_SHORT).show();




        }
        catch (Exception e){
            new File(mFilepath).getAbsoluteFile().delete();
            Toast.makeText(this,"Summary Not Found",Toast.LENGTH_SHORT).show();
        }



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savePdf();
                }
                else
                {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
