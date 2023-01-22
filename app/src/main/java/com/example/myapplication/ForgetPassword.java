package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.concurrent.TimeUnit;


public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        final EditText inputMobile = findViewById(R.id.inputMobile);
        Button buttonGetOTP = findViewById(R.id.buttonGetOTP);

        final ProgressBar progressBar = findViewById(R.id.progressBar);

        buttonGetOTP.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                if (inputMobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(ForgetPassword.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(android.view.View.VISIBLE);
                buttonGetOTP.setVisibility(android.view.View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + inputMobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        ForgetPassword.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(android.view.View.GONE);
                                buttonGetOTP.setVisibility(android.view.View.VISIBLE);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(android.view.View.GONE);
                                buttonGetOTP.setVisibility(android.view.View.VISIBLE);
                                Toast.makeText(ForgetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(android.view.View.GONE);
                                buttonGetOTP.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getApplicationContext(),VerifyOTP.class);
                                intent.putExtra("phone",inputMobile.getText().toString());
                                intent.putExtra("verificationID",verificationID);
                                startActivity(intent);
                            }
                        }
                );


            }
        });


    }
}