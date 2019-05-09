package com.example.gauthupload;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.mbms.FileInfo;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.errorprone.annotations.RequiredModifiers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import static android.support.constraint.Constraints.TAG;

public class voice extends Activity implements
        RecognitionListener {


    private static final int STORAGE_CODE = 1000;
    private static final int STORAGE_CODE2 = 2000;
    private TextView returnedText;
    Uri pdf;
    FirebaseStorage storage;
    FirebaseFirestore db;
    FirebaseDatabase database;
    String ans;
    private ToggleButton toggleButton;
    ProgressDialog progressDialog;
    Button mback;
    private ImageView mButtonSpeak, stop;
    TextToSpeech mTTS;
    SeekBar mSeekBarPitch;
    SeekBar mSeekBarSpeed;
    ImageView share, download;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognition";
    String speechString = "";
    boolean spechStarted = false;
    Button submit, clear;
    TextView result;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(voice.this, homepage.class));
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voice);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AudioManager audio = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
        returnedText = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
        download = findViewById(R.id.downloadvoice);
        share = findViewById(R.id.sharesumvoice);
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        result = findViewById(R.id.fetch);
        db = FirebaseFirestore.getInstance();
        mButtonSpeak = findViewById(R.id.button_speakc);
        stop = findViewById(R.id.button_stopc);
        submit = findViewById(R.id.submitvoice);
        clear = findViewById(R.id.clearvoice);
        progressBar.setVisibility(View.INVISIBLE);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra("android.speech.extra.DICTATION_MODE", true);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,
                true);
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

        mSeekBarPitch = findViewById(R.id.seek_bar_pitchc);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speedc);
        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                speak();

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
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permssions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permssions, STORAGE_CODE2);
                    } else {
                        if(!TextUtils.isEmpty(ans)) {
                            savepdf1();
                        }
                        else
                        {
                            Toast.makeText(voice.this,"Summary Not Found",Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if(!TextUtils.isEmpty(ans)) {
                        savepdf1();
                    }
                    else
                    {
                        Toast.makeText(voice.this,"Summary Not Found",Toast.LENGTH_SHORT).show();
                    }
                }

            }


        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!TextUtils.isEmpty(ans)) {

                    String sharemy = result.getText().toString();
                    Intent msharingintent = new Intent(Intent.ACTION_SEND);
                    msharingintent.setType("text/plain");
                    msharingintent.putExtra(Intent.EXTRA_SUBJECT, "My summary");
                    msharingintent.putExtra(Intent.EXTRA_TEXT, sharemy);

                    startActivity(Intent.createChooser(msharingintent, "Share Summary via"));
                }
                else {
                    Toast.makeText(voice.this,"Summary not found",Toast.LENGTH_SHORT).show();
                }
            }
        });



        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        if (isChecked) {
                            speech.setRecognitionListener(voice.this);
                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.setIndeterminate(true);
                            speech.startListening(recognizerIntent);
                        } else {
                            progressBar.setIndeterminate(false);
                            progressBar.setVisibility(View.INVISIBLE);
                            speech.stopListening();
                            speech.destroy();


                        }
                    } else {
                        String[] permssions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permssions, STORAGE_CODE);

                    }
                } else {

                    if (isChecked) {
                        speech.setRecognitionListener(voice.this);
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setIndeterminate(true);
                        speech.startListening(recognizerIntent);
                    } else {
                        progressBar.setIndeterminate(false);
                        progressBar.setVisibility(View.INVISIBLE);
                        speech.stopListening();
                        speech.destroy();


                    }


                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            private boolean isNetworkavailable()
            {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkinfo=connectivityManager.getActiveNetworkInfo();
                return activeNetworkinfo != null && activeNetworkinfo.isConnected();
            }

            @Override
            public void onClick(View v) {
                if(isNetworkavailable()) {
                    savePdf();
                    speech.destroy();

                    speechString = "";
                    returnedText.setText(speechString);
                    toggleButton.setChecked(false);
                }
                else
                {
                    Toast.makeText(voice.this,"No Network", Toast.LENGTH_SHORT).show();
                }

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speech.destroy();

                speechString = "";
                returnedText.setText(speechString);
                toggleButton.setChecked(false);

            }
        });

    }

    private void savepdf1() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Converting");
        // Toast.makeText(Profile.this,"Uploading",Toast.LENGTH_LONG).show();
        progressDialog.setProgressStyle(0);
        progressDialog.show();
        Document mdoc = new Document();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String fileName = "VoiceSummary" + System.currentTimeMillis() + "-" + currentFirebaseUser.getUid();
        String mFilepath = Environment.getExternalStorageDirectory() + "/" + fileName + ".pdf";


        try {

            PdfWriter.getInstance(mdoc, new FileOutputStream(mFilepath));
            mdoc.open();
            String mText = result.getText().toString();
            mdoc.add(new Paragraph(mText));
            mdoc.close();
            progressDialog.cancel();
            Toast.makeText(this, "Your File is Downloaded", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            progressDialog.cancel();
            Toast.makeText(this, "Summary Not Found", Toast.LENGTH_SHORT).show();
            new File(mFilepath).getAbsoluteFile().delete();
        }


    }

    private void speak() {
        String text = result.getText().toString();


        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        int dividerLimit = 3900;
        if (text.length() >= dividerLimit) {
            int textLength = text.length();
            ArrayList<String> texts = new ArrayList<String>();
            int count = textLength / dividerLimit + ((textLength % dividerLimit == 0) ? 0 : 1);
            int start = 0;
            int end = text.indexOf(" ", dividerLimit);
            for (int i = 1; i <= count; i++) {
                texts.add(text.substring(start, end));
                start = end;
                if ((start + dividerLimit) < textLength) {
                    end = text.indexOf(" ", start + dividerLimit);
                } else {
                    end = textLength;
                }
            }
            try {
                for (int i = 0; i < texts.size(); i++) {
                    mTTS.speak(texts.get(i), TextToSpeech.QUEUE_ADD, null);
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        } else {
            try {
                mTTS.speak(text, TextToSpeech.QUEUE_ADD ,null);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void savePdf() {
        String nouse = "";
        result.setText(nouse);
        ans ="";


        Document mdoc = new Document();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final long fileNameold = System.currentTimeMillis();
        final String fileName = fileNameold + "-" + currentFirebaseUser.getUid();
        String mFilepath = Environment.getExternalStorageDirectory() + "/" + fileName + ".pdf";
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();


        try {
            PdfWriter.getInstance(mdoc, new FileOutputStream(mFilepath));
            mdoc.open();
            String mText = returnedText.getText().toString();
            mdoc.add(new Paragraph(mText));
            mdoc.close();
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("Uploading....");
            // Toast.makeText(Profile.this,"Uploading",Toast.LENGTH_LONG).show();
            progressDialog.setProgressStyle(0);
            progressDialog.show();


            final StorageReference mstorage;
            final DatabaseReference reference = database.getReference();
            mstorage = storage.getReference();

            pdf = Uri.fromFile(new File(mFilepath));
            final StorageReference filepath = mstorage.child("Uploads By : " + currentFirebaseUser.getUid() + ".pdf");
            filepath.putFile(pdf).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadurl) {
                            progressDialog.cancel();
                            progressDialog.cancel();
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressDialog.setTitle("Just a Moment");
                            progressDialog.setProgressStyle(0);
                            progressDialog.show();
                            String finalname = fileName + refreshedToken + "pdf";
                            final String downloadUrl =
                                    downloadurl.toString();
//                            Toast.makeText(voice.this, downloadUrl, Toast.LENGTH_SHORT).show();
                            reference.child(finalname).setValue(downloadUrl);
                            getsum(fileNameold);

                        }
                    });

                }


            });


        } catch (Exception e) {
            Toast.makeText(this, "Input is Empty", Toast.LENGTH_SHORT).show();
        }


    }

    private void getsum(long fileNameold) {

        String token = Long.toString(fileNameold);
        Runnable r = new Runnable() {
            @Override
            public void run(){
                if(result.getText().length()==0)
                {
                    progressDialog.cancel();
                    String dup = "Our server is overloaded/not responding, kindly wait for 2 minutes you might see your output here otherwise upload again";
                    result.setTextColor(Color.parseColor("#ff0000"));
                    result.setText(dup);
                }
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 10000);
        db.collection("Summary")
                .whereEqualTo("Token", token)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot documentSnapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        String user;
                        user = FirebaseAuth.getInstance().getUid();
                        String id;
                        for (DocumentSnapshot ds : documentSnapshots) {

                            String summary;
                            id = ds.getString("User_id");
                            if (user.equals(id)) {
                                summary = ds.getString("Summary");
                                if(summary.equals("ERROR-PRO"))
                                {
                                    summary = "Sorry unable to process";
                                    Toast.makeText(voice.this,"Sorry unable to process",Toast.LENGTH_SHORT).show();
                                    result.setTextColor(Color.parseColor("#ff0000"));
                                    result.setText(summary);
                                    ans =summary;
                                    progressDialog.cancel();
                                }
                                else if (summary.equals("ERROR-NULL"))
                                {
                                    summary = "Sorry TOO little data";
                                    Toast.makeText(voice.this,"Sorry TOO little data",Toast.LENGTH_SHORT).show();
                                    result.setTextColor(Color.parseColor("#ff0000"));
                                    result.setText(summary);
                                    ans =summary;
                                    progressDialog.cancel();
                                }
                                else if (summary.equals("ERROR-LANG"))
                                {
                                    summary = "Sorry Language Error ";
                                    Toast.makeText(voice.this,"Sorry Language Error",Toast.LENGTH_SHORT).show();
                                    result.setTextColor(Color.parseColor("#ff0000"));
                                    result.setText(summary);
                                    ans =summary;
                                    progressDialog.cancel();
                                }
                                else if (summary.equals("ERROR-TOOLONG"))
                                {
                                    summary = "Sorry TOO long data";
                                    Toast.makeText(voice.this,"Sorry TOO long data",Toast.LENGTH_SHORT).show();
                                    result.setTextColor(Color.parseColor("#ff0000"));
                                    result.setText(summary);
                                    ans =summary;
                                    progressDialog.cancel();
                                }
                                else
                                {
                                    result.setTextColor(Color.parseColor("#000000"));
                                    result.setText(summary);
                                    ans =summary;
                                    progressDialog.cancel();
                                }
                            }
                        }
                    }
                });

    }


    private void homeAct() {
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
    protected void onStop() {
        super.onStop();
        if (mTTS != null) {
            Log.d(TAG, "onstopped: ");
            mTTS.stop();
            mTTS.shutdown();

        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech == null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
        super.onPause();
        if (mTTS != null) {
            Log.d(TAG, "onpaused: ");
            mTTS.stop();
            mTTS = null;

        }

    }

    @Override
    protected void onResume() {
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

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        spechStarted = true;
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {

        spechStarted = false;
        Log.i(LOG_TAG, "onEndOfSpeech");
        speech.startListening(recognizerIntent);

    }

    @Override
    public void onError(int errorCode) {
        Log.d(LOG_TAG, "FAILED ");
        if (!spechStarted)
            speech.startListening(recognizerIntent);

    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");

        ArrayList<String> matches = arg0
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        returnedText.setText(speechString + matches.get(0));


    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        speechString = speechString + ". " + matches.get(0);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    speech.setRecognitionListener(voice.this);
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    speech.startListening(recognizerIntent);


                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
        switch (requestCode) {
            case STORAGE_CODE2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    savepdf1();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }
}
