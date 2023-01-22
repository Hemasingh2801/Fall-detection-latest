package com.example.myapplication;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    private TextInputLayout regUsername, regPhone, regEmail, regPassword;
    private TextInputEditText UsernameEdit, PhoneEdit, EmailEdit,PasswordEdit;
    Button callSignIn, regBtn;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
   private String PasswordPattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}";
   private String EmailPattern="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        //hooks to all xml elements to sign up
        regUsername = findViewById(R.id.Username);
        regPhone = findViewById(R.id.phoneNo);
        regEmail = findViewById(R.id.Email);
        regPassword = findViewById(R.id.Password);
        UsernameEdit=findViewById(R.id.usernameedit);
        PhoneEdit=findViewById(R.id.phoneedit);
        EmailEdit=findViewById(R.id.emailedit);
        PasswordEdit=findViewById(R.id.passwordedit);
        regBtn = findViewById(R.id.chip2);

        callSignIn = findViewById(R.id.signInbtn);

        //signup button
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        });

        UsernameEdit.addTextChangedListener(new TextWatcher() {
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
        PasswordEdit.addTextChangedListener(new TextWatcher() {
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
        EmailEdit.addTextChangedListener(new TextWatcher() {
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
        PhoneEdit.addTextChangedListener(new TextWatcher() {
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
        callSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });

    }
        private void checkEmailAndPassword () {
            if (EmailEdit.getText().toString().matches(EmailPattern)) {
                if (PasswordEdit.getText().toString().matches(PasswordPattern)) {

                    auth.createUserWithEmailAndPassword(EmailEdit.getText().toString(), PasswordEdit.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                       final Map<Object,String > userdata= new HashMap<>();
                                        userdata.put("username",UsernameEdit.getText().toString());
                                        userdata.put("Phone",PhoneEdit.getText().toString());
                                        userdata.put("Email",EmailEdit.getText().toString());
                                        userdata.put("Password",PasswordEdit.getText().toString());

                                        firebaseFirestore.collection("USERS")
                                                .add(userdata)
                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(signup.this, "Successfully Sign Up ", Toast.LENGTH_SHORT).show();
                                                            loginuser();
                                                        }else {
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(signup.this, error, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(signup.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    regPassword.setError("Enter a Strong Password");
                }
            } else {
                regEmail.setError("Enter Valid Email");
            }
        }

        private void checkInputs () {
            if (!TextUtils.isEmpty(UsernameEdit.getText())) {
                if (!TextUtils.isEmpty(PhoneEdit.getText())) {
                    if (!TextUtils.isEmpty(EmailEdit.getText())) {
                        if (!TextUtils.isEmpty(PasswordEdit.getText())) {
                            regBtn.setEnabled(true);
                            regBtn.setTextColor(Color.rgb(255, 255, 255));
                        } else {
                            regBtn.setEnabled(false);
                            regBtn.setTextColor(Color.argb(50, 255, 255, 255));
                        }

                    } else {
                        regBtn.setEnabled(false);
                        regBtn.setTextColor(Color.argb(50, 255, 255, 255));
                    }
                } else {
                    regBtn.setEnabled(false);
                    regBtn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                regBtn.setEnabled(false);
                regBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
    }
    private void loginuser(){
        Intent intent = new Intent(getApplicationContext(), login.class);
        startActivity(intent);
    }

}