package com.example.foodmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText emailEditTextfp;
    private Button resetPasswordButtonfp;
    private ProgressBar progressBarfp;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditTextfp = (EditText) findViewById(R.id.editTextTextPersonNamefp);
        resetPasswordButtonfp = (Button) findViewById(R.id.resetpw);
        progressBarfp = (ProgressBar) findViewById(R.id.progressBarfp);

        //get instance of firebase user authentication
        auth = FirebaseAuth.getInstance();

        //onclick listener for resetPasswordButtonfp to run resetPassword() method
        resetPasswordButtonfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    //resetting password
    private void resetPassword() {
        String email = emailEditTextfp.getText().toString().trim();

        //check email is entered or not
        if (email.isEmpty()) {
            emailEditTextfp.setError("Email is Required");
            emailEditTextfp.requestFocus();
            return;
        }

        //check if a valid email is entered
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditTextfp.setError("Please provide valid email!");
            emailEditTextfp.requestFocus();
            return;
        }


        progressBarfp.setVisibility(View.VISIBLE);
        //after user is authenticated send reset password mail to user email via otp
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //if task is successful show the toast
                    Toast.makeText(ForgotPassword.this, "Check your email to reset your password & Log in again to continue!", Toast.LENGTH_LONG)
                            .show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //after user change password via otp redirect user to signin page to signin with new password
                            startActivity(new Intent(ForgotPassword.this, SignIn.class));
                        }
                    }, 8000);
                } else {
                    Toast.makeText(ForgotPassword.this, "Something went wrong! Try again", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }





}