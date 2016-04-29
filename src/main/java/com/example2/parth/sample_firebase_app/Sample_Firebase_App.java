package com.example2.parth.sample_firebase_app;


import android.app.Application;
import com.firebase.client.Firebase;


public class Sample_Firebase_App extends Application{


    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }


}
