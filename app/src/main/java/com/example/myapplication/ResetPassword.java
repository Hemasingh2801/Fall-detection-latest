package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    //Variables
    private EditText registeredemail;
    private Button resetpasswordBtn;
    private TextView goback;
    private FirebaseAuth firebaseAuth;
    private ViewGroup emailIconContainer;
    private ImageView emailIcon;
    private TextView emailIconText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reset_password);
        registeredemail = findViewById(R.id.editTextTextEmailAddress);
        resetpasswordBtn = findViewById(R.id.buttonGetOTP);
        goback = findViewById(R.id.forgot_pass_go_back);
        firebaseAuth=FirebaseAuth.getInstance();
        emailIconContainer= findViewById(R.id.forgot_password_email_icon_container);
        emailIcon= findViewById(R.id.forgot_password_email_icon);
        emailIconText= findViewById(R.id.forgot_password_email_icon_text);
        progressBar= findViewById(R.id.forgot_password_progressbar);

        registeredemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        resetpasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIconText.setVisibility(View.GONE);

                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIcon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.sendPasswordResetEmail(registeredemail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    TransitionManager.beginDelayedTransition(emailIconContainer);
                                    emailIconText.setVisibility(View.VISIBLE);
                                    emailIcon.setVisibility(View.VISIBLE);
                                    emailIconText.setTextColor(getResources().getColor(R.color.SuccessGreen));
                                    Toast.makeText(ResetPassword.this, "Email sent Successfully for Inbox", Toast.LENGTH_SHORT).show();
                                }else{
                                    String error = task.getException().getMessage();


                                    emailIconText.setText(error);
                                    emailIconText.setTextColor(getResources().getColor(R.color.Red));
                                    TransitionManager.beginDelayedTransition(emailIconContainer);
                                    emailIcon.setVisibility(View.VISIBLE);
                                    //Toast.makeText(ResetPassword.this, error, Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                                resetpasswordBtn.setEnabled(true);
                                resetpasswordBtn.setTextColor(Color.argb(50,255,255,255));
                            }
                        });
            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(getApplicationContext(), login.class);
                startActivity(loginIntent);
            }
        });
    }

    private void checkInputs() {
        if(!TextUtils.isEmpty(registeredemail.getText())) {
            resetpasswordBtn.setEnabled(true);
            resetpasswordBtn.setTextColor(Color.rgb(255,255,255));

        }
        else{
            resetpasswordBtn.setEnabled(false);
            resetpasswordBtn.setTextColor(Color.argb(50,255,255,255));
        }
    }
}