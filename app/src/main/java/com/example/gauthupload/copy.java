package com.example.gauthupload;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.mbms.FileInfo;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class copy extends AppCompatActivity {

    private static final int STORAGE_CODE = 1000 ;
    private static final int STORAGE_CODE1 = 1001 ;
    private static final String TAG = "copy";
    EditText mtextEt;
    ProgressDialog progressDialog;
    Button mSaveBtn,mback;
    ImageView mwhatsapp,mmessages,mgmail,stop,share;
    FirebaseStorage storage;
    FirebaseDatabase database;
    TextView result;
    FirebaseFirestore db;
    Uri pdf;
    ImageView downloadsum;
    String ans;
    TextToSpeech mTTS;
    SeekBar mSeekBarPitch;
    SeekBar mSeekBarSpeed;
    private ImageView mButtonSpeak;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(copy.this, homepage.class));
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        db = FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_copy);
        mwhatsapp = findViewById(R.id.whatsapp);
        mmessages = findViewById(R.id.messages);
        mgmail = findViewById(R.id.Gmail);
        share=findViewById(R.id.sharesumchat);
        mtextEt = findViewById(R.id.textEt);
        downloadsum=findViewById(R.id.downloadfile1);
        storage= FirebaseStorage.getInstance();
        database= FirebaseDatabase.getInstance();
        mSaveBtn=findViewById(R.id.saveBtn);
        mButtonSpeak = findViewById(R.id.button_speakb);
        stop = findViewById(R.id.button_stopb);

        result = findViewById(R.id.fetch);
        result.setMovementMethod(new ScrollingMovementMethod());
        mgmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException anfe) {
                    Toast.makeText(copy.this,"No email app found on your device.",Toast.LENGTH_SHORT).show();

                }
            }
        });

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

        mSeekBarPitch = findViewById(R.id.seek_bar_pitchb);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speedb);

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
                    Toast.makeText(copy.this,"Summary not found",Toast.LENGTH_SHORT).show();
                }

            }
        });
        mmessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException anfe) {
                    Toast.makeText(copy.this,"No messaging app found on your device.",Toast.LENGTH_SHORT).show();

                }
            }
        });
        mwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent Launch = getPackageManager().getLaunchIntentForPackage("com.whatsapp");


                    startActivity(Launch);
                } catch (Exception e) {
                    Toast.makeText(copy.this,"Whatsapp not found on your device.",Toast.LENGTH_SHORT).show();

                }
            }
        });
        downloadsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[] permssions =  {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permssions, STORAGE_CODE1);
                    }
                    else
                    {
                        if(!TextUtils.isEmpty(ans)) {
                            savepdf1();
                        }
                        else
                        {
                            Toast.makeText(copy.this,"Summary Not Found",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    if(!TextUtils.isEmpty(ans)) {
                        savepdf1();
                    }
                    else
                    {
                        Toast.makeText(copy.this,"Summary Not Found",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            private boolean isNetworkavailable()
            {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkinfo=connectivityManager.getActiveNetworkInfo();
                return activeNetworkinfo != null && activeNetworkinfo.isConnected();
            }
            @Override
            public void onClick(View v) {
                if (isNetworkavailable()) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            String[] permssions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permssions, STORAGE_CODE);
                        } else {
                            savePdf();
                        }
                    } else {
                        savePdf();
                    }
                }
                else
                {
                    Toast.makeText(copy.this,"No Network", Toast.LENGTH_SHORT).show();
                }
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
        final String fileName="ChatSummary"+System.currentTimeMillis()+"-"+currentFirebaseUser.getUid();
        String mFilepath = Environment.getExternalStorageDirectory() + "/" + fileName + ".pdf";


        try
        {

            PdfWriter.getInstance(mdoc,new FileOutputStream(mFilepath));
            mdoc.open();
            String mText = result.getText().toString();
            mdoc.add(new Paragraph(mText));
            mdoc.close();
            progressDialog.cancel();
            Toast.makeText(this,"Your File is Downloaded",Toast.LENGTH_SHORT).show();




        }
        catch (Exception e){
            new File(mFilepath).getAbsoluteFile().delete();
            progressDialog.cancel();
            Toast.makeText(this,"Summary Not Found",Toast.LENGTH_SHORT).show();

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
    @Override
    protected void onDestroy() {
        if (mTTS != null) {

            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
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






    private void launchmain() {
        Intent intent = new Intent(this, homepage.class);
        startActivity(intent);
    }

    private void savePdf() {
        String nouse = "";
        result.setText(nouse);
        ans="";
        Document mdoc = new Document();
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final long fileNameold=System.currentTimeMillis();
        final String fileName = fileNameold+"-"+currentFirebaseUser.getUid();
        String mFilepath = Environment.getExternalStorageDirectory() + "/" + fileName + ".pdf";

        try
        {
            PdfWriter.getInstance(mdoc,new FileOutputStream(mFilepath));
            mdoc.open();
            String mText = mtextEt.getText().toString();
            if(mText.contains("\n"))
            {
                char chrs[] = mText.toCharArray();
                int i = 0;
                while (i < chrs.length) {

                    if (chrs[i] == '\n') {
                        chrs[i] = '|';
                    }
                    i++;
                }
                mText=String.copyValueOf(chrs);

               


            }
            mdoc.add(new Paragraph(mText));
            mdoc.close();
            progressDialog =new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("Uploading....");
            // Toast.makeText(Profile.this,"Uploading",Toast.LENGTH_LONG).show();
            progressDialog.setProgressStyle(0);
            progressDialog.show();


            final StorageReference mstorage;
            final DatabaseReference reference = database.getReference();
            mstorage=storage.getReference();
            pdf=Uri.fromFile(new File(mFilepath));
            final StorageReference filepath = mstorage.child("Uploads By : "+currentFirebaseUser.getUid()+".pdf");
            filepath.putFile(pdf).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadurl) {
                            progressDialog.cancel();
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressDialog.setTitle("Just a Moment");
                            progressDialog.setProgressStyle(0);
                            progressDialog.show();
                            String finalname = fileName+refreshedToken + "pdf";

                            final String downloadUrl =
                                    downloadurl.toString();
                            reference.child(finalname).setValue(downloadUrl);
                                    getsum(fileNameold);



                        }
                    });

                }








            });





        }
        catch (Exception e){
            Toast.makeText(this,"Input is Empty",Toast.LENGTH_SHORT).show();
        }



    }

    private void getsum(long fileNameold) {

         final String token = Long.toString(fileNameold);
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
                    for (DocumentSnapshot ds: documentSnapshots){
                        String summary;
                        id = ds.getString("User_id");
                        if (user.equals(id)) {
                        summary = ds.getString("Summary");
                        if(summary.equals("ERROR-PRO"))
                        {
                            summary = "Sorry unable to process";
                            Toast.makeText(copy.this,"Sorry unable to process",Toast.LENGTH_SHORT).show();
                            result.setTextColor(Color.parseColor("#ff0000"));
                            result.setText(summary);
                            ans =summary;
                            progressDialog.cancel();

                        }
                        else if (summary.equals("ERROR-NULL"))
                        {
                            summary = "Sorry TOO little data";
                            Toast.makeText(copy.this,"Sorry TOO little data",Toast.LENGTH_SHORT).show();
                            result.setTextColor(Color.parseColor("#ff0000"));
                            result.setText(summary);
                            ans =summary;
                            progressDialog.cancel();
                        }
                        else if (summary.equals("ERROR-LANG"))
                        {
                            summary = "Sorry Language Error ";
                            Toast.makeText(copy.this,"Sorry Language Error",Toast.LENGTH_SHORT).show();
                            result.setTextColor(Color.parseColor("#ff0000"));
                            result.setText(summary);
                            ans =summary;
                            progressDialog.cancel();
                        }
                        else if (summary.equals("ERROR-TOOLONG"))
                        {
                            summary = "Sorry TOO long data";
                            Toast.makeText(copy.this,"Sorry TOO long data",Toast.LENGTH_SHORT).show();
                            result.setTextColor(Color.parseColor("#ff0000"));
                            result.setText(summary);
                            ans =summary;
                            progressDialog.cancel();
                        }
                        else
                        {
                            result.setTextColor(Color.parseColor("#000000"));
                            result.setText(summary);
                            ans = summary;
                            progressDialog.cancel();
                        }

                    }}
                    }
                });

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
            case STORAGE_CODE1:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savepdf1();
                }
                else
                {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
