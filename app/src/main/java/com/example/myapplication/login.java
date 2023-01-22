package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
public class login extends AppCompatActivity {
    private Button callSignUp, login_btn;
    private  TextInputLayout login_email, login_password;
    private TextInputEditText logEmailEdit,logPasswordEdit;
    private String PasswordPattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}";
    private String EmailPattern="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        login_email= findViewById(R.id.logUsername);
        login_password= findViewById(R.id.logPassword);
        logEmailEdit=findViewById(R.id.LogEmailEdit);
        logPasswordEdit=findViewById(R.id.LogPasswordEdit);
        callSignUp= findViewById(R.id.signUpbtn);
        login_btn= findViewById(R.id.Signin);

        logEmailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             CheckInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    logPasswordEdit.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            CheckInputs();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            checkEmailAndPassword();
            }
        });






        //redirect from login to sign up
        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });
    }



    private void CheckInputs() {
        if(!TextUtils.isEmpty(logEmailEdit.getText())){
           if(!TextUtils.isEmpty(logPasswordEdit.getText())){
               login_btn.setEnabled(true);
               login_btn.setTextColor(Color.rgb(255,255,255));
           }else{
               login_btn.setEnabled(false);
               login_btn.setTextColor(Color.argb(50,255,255,255));
           }
        }else{
            login_btn.setEnabled(false);
            login_btn.setTextColor(Color.argb(50,255,255,255));
        }
    }
    private void checkEmailAndPassword() {
        if(logEmailEdit.getText().toString().matches(EmailPattern)){
            if(logPasswordEdit.getText().toString().matches(PasswordPattern)){
              auth.signInWithEmailAndPassword(logEmailEdit.getText().toString(), logPasswordEdit.getText().toString())
                      .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                              if(task.isSuccessful()){
                                  Toast.makeText(login.this, "Successfully Login ", Toast.LENGTH_SHORT).show();
                                  callhome();
                              }else{
                                  String error=task.getException().getMessage();
                                  Toast.makeText(login.this,error, Toast.LENGTH_SHORT).show();
                              }
                          }
                      });
            }else {
                login_email.setError("Incorrect Password");
            }
        }else {
            login_password.setError("Incorrect Email");

        }
    }
    // call home
 private void callhome(){
     Intent mainIntent = new Intent(getApplicationContext(), positioncap.class);
     startActivity(mainIntent);
 }

    //forget Password call
    public  void callforgetPassword(View view){
        startActivity(new Intent(getApplicationContext(), ResetPassword.class));
    }
}
