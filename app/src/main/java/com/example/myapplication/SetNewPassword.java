package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {
TextInputLayout newpassword, confirmpassword;
    DatabaseReference reference;
Button SaveChangeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_new_password);
        //reference = FirebaseDatabase.getInstance().getReference("users");

        newpassword = findViewById(R.id.new_password);
        confirmpassword = findViewById(R.id.confirm_password);
        SaveChangeBtn= findViewById(R.id.set_new_password_btn);


        SaveChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validatePassword() | !validateConfirmPassword()) {
                    return;
                }
                if (newpassword.getEditText().getText().toString().equals(confirmpassword.getEditText().getText().toString())) {
                    Toast.makeText(SetNewPassword.this, "Password matched", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SetNewPassword.this, "Password Not matched", Toast.LENGTH_SHORT).show();
                }
                String Userphone = getIntent().getStringExtra("phone");
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(Userphone).child("password").setValue(newpassword);
            }

        });


        }


    private boolean validateConfirmPassword() {
        String val = newpassword.getEditText().getText().toString().trim();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$";

        if (val.isEmpty()) {
            newpassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)){
            newpassword.setError("Password is too weak");
            return false;
        } else {
            newpassword.setError(null);
            newpassword.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePassword() {
        String val = newpassword.getEditText().getText().toString().trim();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$";

        if (val.isEmpty()) {
            confirmpassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)){
            confirmpassword.setError("Password is too weak");
            return false;
        } else {
            confirmpassword.setError(null);
            confirmpassword.setErrorEnabled(false);
            return true;
        }
    }
   /* private boolean isPassword() {
        String Userphone = getIntent().getStringExtra("phone");
        if (newpassword.equals(confirmpassword.getEditText().getText().toString())){
            reference.child(Userphone).child("password").setValue(confirmpassword.getEditText().getText().toString());
            return true;
        } else {
            return false;
        }
    }*/

}
