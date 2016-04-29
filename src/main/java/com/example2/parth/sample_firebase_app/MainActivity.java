package com.example2.parth.sample_firebase_app;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity{


    private Firebase reference;
    private EditText ET1,ET2;
    private String username,password;
    private static String db_key,db_password;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        //Firebase.setAndroidContext(this);
        ET1 = (EditText)findViewById(R.id.ET1);
        ET2 = (EditText)findViewById(R.id.ET2);
    }


    @Override
    protected void onResume(){
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onClickLoginAdmin(View view){
        username = (ET1.getText()).toString();
        password = (ET2.getText()).toString();
        authenticateUser(username, password);
    }

    private void authenticateUser(final String user_name, final String pass_word){
        Map<String,Object> fetcher = new HashMap<String,Object>();
        fetcher.put("fetcher","");
        final String key="pwd";
        reference = new Firebase("https://myapp3037.firebaseio.com/users/"+user_name);
        reference.updateChildren(fetcher);

        reference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                for(DataSnapshot resultSnapshot : dataSnapshot.getChildren()){
                    db_key = resultSnapshot.getKey();
                    db_password = (resultSnapshot.getValue()).toString();
                    if(key.equals(db_key) && pass_word.equals(db_password)){
                        Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        intent = new Intent(MainActivity.this,AfterLoginSupremeUsers.class);
                        intent.putExtra("username",user_name);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError){
            }
        });
    }


    public void onClickGoToForgotUsername(View view){
        intent = new Intent();
        startActivity(intent);
    }


    public void onClickGoToForgotPassword(View view){
        intent = new Intent();
        startActivity(intent);
    }


    public void onClickSystemExit(View view){
        finish();
        System.exit(0);
    }


}
