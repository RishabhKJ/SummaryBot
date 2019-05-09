package com.example.gauthupload;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Context mContext;

    public List<GetData> getDataList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DataAdapter(Context mContext, List<GetData> getData){
        this.mContext = mContext;
        this.getDataList = getData;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        TextView tvuser,tvtime,day,month;
        RelativeLayout parentLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            mView= itemView;
            parentLayout = itemView.findViewById(R.id.parentLayout);
            tvuser = mView.findViewById(R.id.tvuser);
            tvtime = mView.findViewById(R.id.tvtime);
            day = mView.findViewById(R.id.day);
            month = mView.findViewById(R.id.month);


        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.download_data,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        CollectionReference citiesRef = db.collection("Summary");
        Date date =  getDataList.get(i).getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat1 = new SimpleDateFormat("d");
        DateFormat dateFormat2 = new SimpleDateFormat("  MMM");
        String k = dateFormat1.format(date);
        String n = dateFormat.format(date);
        String g = dateFormat2.format(date);
        Log.d(TAG,"Hello "+n+" "+k+""+g );
        String myoutput = getDataList.get(i).getsummary();
        if (myoutput.length()>4) {
            String newstring =   "...."+myoutput.substring(0, 4);
            viewHolder.tvuser.setText(newstring);
        }
        else {
            String newstring = "......";
            viewHolder.tvuser.setText(newstring);

        }

        viewHolder.tvtime.setText(n);
        viewHolder.day.setText(k);
        viewHolder.month.setText(g);
        citiesRef.orderBy("date").limit(3);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: "+getDataList.get(i).getsummary());
                Intent intent = new Intent(mContext,output.class);
                intent.putExtra("yoursum", getDataList.get(i).getsummary());
                mContext.startActivity(intent);

            }
        });


    }



    @Override
    public int getItemCount() {
        return getDataList.size();
    }
}
