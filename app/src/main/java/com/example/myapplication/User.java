package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class User extends AppCompatActivity {
private TextView callsettings;
private TextView calleditUser;
private TextView calladdCaretaker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        callsettings=findViewById(R.id.Settingstext);
        calleditUser=findViewById(R.id.EditProfileText);
        calladdCaretaker=findViewById(R.id.addcaretext);

        //call EditProfile
        calleditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, EditProfile.class);
                startActivity(intent);
            }
        });
        //calling add caretaker number
        calladdCaretaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, addCaretaker.class);
                startActivity(intent);
            }
        });

        //callings Settings
        callsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, settings.class);
                startActivity(intent);
            }
        });


    }
    public void backtoUserprofile(View view){
        Intent intent=new Intent(this,positioncap.class);
        this.startActivity(intent);
    }

}