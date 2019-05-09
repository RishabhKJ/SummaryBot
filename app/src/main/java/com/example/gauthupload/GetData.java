package com.example.gauthupload;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class GetData {

  private  String Summary,User_id;
   private @ServerTimestamp
   Date time;

    public GetData(String Summary, String User_id, Date time) {
        this.Summary = Summary;
        this.User_id = User_id;
        this.time = time;
    }

    public GetData() {
    }

    public String getsummary() {
        return Summary;
    }

    public void setsummary(String Summary) {
        this.Summary = Summary;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setuser_id(String User_id) {
        this.User_id = User_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
