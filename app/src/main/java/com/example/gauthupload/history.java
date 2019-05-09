package com.example.gauthupload;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class history extends AppCompatActivity {
    private static final String TAG = "History";
    FirebaseFirestore db;
    DocumentReference documentReference;
    RecyclerView rvlist;
    DataAdapter mDataAdapter;
    List<GetData> userlist;
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(history.this, homepage.class));
        finish();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_history);

        userlist = new ArrayList<>();

        mDataAdapter = new DataAdapter(this, userlist);

        db = FirebaseFirestore.getInstance();
        documentReference=db.collection("Summary").document();


        rvlist = findViewById(R.id.rvuserList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvlist.setHasFixedSize(true);
        rvlist.setLayoutManager(layoutManager);
        rvlist.setAdapter(mDataAdapter);



        recieveData();



    }

    private void recieveData() {

        final CollectionReference citiesRef = db.collection("Summary");
        db.collection("Summary").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange dc : documentSnapshots.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        String user;
                        user = FirebaseAuth.getInstance().getUid();

                        String sum;
                        String id;
                        id = dc.getDocument().getString("User_id");
                        if (user.equals(id)) {
                            sum = dc.getDocument().getString("Summary");
                            Log.d(TAG, "onEvent: " + sum);

                            GetData getData = dc.getDocument().toObject(GetData.class);

                            userlist.add(getData);
                            Log.d(TAG, "onEvent: " + userlist);
                            mDataAdapter.notifyDataSetChanged();

                        }

                    }
                }


            }


        });


    }

}
