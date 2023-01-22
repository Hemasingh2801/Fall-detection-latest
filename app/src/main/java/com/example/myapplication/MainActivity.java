package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
       // auth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iHome = new Intent(MainActivity.this, signup.class);
                startActivity(iHome);
                finish();
            }
        }, 4000);


    }

    /*@Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser==null){
            Intent signUp = new Intent(MainActivity.this, signup.class);
            startActivity(signUp);
            finish();
        }else{
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            finish();
        }
    }*/
}