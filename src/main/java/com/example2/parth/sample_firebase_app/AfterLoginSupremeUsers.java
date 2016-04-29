package com.example2.parth.sample_firebase_app;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class AfterLoginSupremeUsers extends AppCompatActivity{


    private Intent intent;
    private Firebase reference1,reference2;
    private Bundle bundle;
    private String username,db_username,db_name,db_role,finalValue,newUsername,newRole;
    private TextView ST1;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_supreme_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ST1 = (TextView)findViewById(R.id.ST1_After_Login_Supreme_Users);
        bundle = getIntent().getExtras();
        if(bundle==null){
            return;
        }
        else{
            username = bundle.getString("username");
            Map<String, Object> fetcher = new HashMap<String, Object>();
            fetcher.put("fetcher", "");
            reference1 = new Firebase("https://amber-inferno-6557.firebaseio.com/Smart_Tagging/supreme_names");
            reference1.updateChildren(fetcher);
            reference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot resultSnapshot : dataSnapshot.getChildren()) {
                        db_username = resultSnapshot.getKey();
                        if (username.equals(db_username)){
                            db_name = (resultSnapshot.getValue()).toString();
                            Toast.makeText(AfterLoginSupremeUsers.this,db_name,Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AfterLoginSupremeUsers.this,"Anonymous",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    //We have nothing to do here.
                }
            });
            reference2 = new Firebase("https://amber-inferno-6557.firebaseio.com/Smart_Tagging/supreme_roles");
            reference1.updateChildren(fetcher);
            reference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot resultSnapshot : dataSnapshot.getChildren()){
                        db_username = resultSnapshot.getKey();
                        if(username.equals(db_username)){
                            db_role = (resultSnapshot.getValue()).toString();
                            Toast.makeText(AfterLoginSupremeUsers.this,db_role,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(AfterLoginSupremeUsers.this,"Anonymous",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    //We have nothing to do here.
                }
            });
            finalValue = "Hello " + db_name + " (" + db_role + ")";
            ST1.setText(finalValue);
        }
    }


    public void onClickSignUpPatientAfterLoginSupremeUsers(View view){
        intent = new Intent(AfterLoginSupremeUsers.this,SignUpPatientActivity.class);
        startActivity(intent);
    }


    public void onClickLoginPatientAfterLoginSupremeUsers(View view){
        intent = new Intent(AfterLoginSupremeUsers.this,LoginPatient.class);
        startActivity(intent);
    }


    public void onClickReadTagAfterLoginSupremeUsers(View view){
        intent = new Intent(AfterLoginSupremeUsers.this,ReadTagActivity.class);
        startActivity(intent);
    }


    public void onClickLogoutAfterLoginSupremeUsers(View view){
        intent = new Intent(AfterLoginSupremeUsers.this,MainActivity.class);
        startActivity(intent);
    }


    public void onClickSystemExitAfterLoginSupremeUsers(View view){
        finish();
        System.exit(0);
    }


}
