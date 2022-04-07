package com.example.foodmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity implements View.OnClickListener{

    EditText editTextTextPersonNamesi2,editTextTextPersonNamesi3;
    Button signin,already;
    TextView forgetpw;
    ProgressBar progressBarSignin;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //set onclick listener for signin button
        signin = (Button) findViewById(R.id.signin);
        signin.setOnClickListener(this);

        //set onclick listener for signup button
        already = (Button) findViewById(R.id.already);
        already.setOnClickListener(this);

        editTextTextPersonNamesi2 = (EditText) findViewById(R.id.editTextTextPersonNamesi2);
        editTextTextPersonNamesi3 = (EditText) findViewById(R.id.editTextTextPersonNamesi3);

        progressBarSignin = (ProgressBar) findViewById(R.id.progressBarSignin);

        //firebase authentication instance
        mAuth = FirebaseAuth.getInstance();

        //set onclick listener for forgotpassword button
        forgetpw = findViewById(R.id.forgetpw);
        forgetpw.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.already:
                //go to sign up page
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.signin:
//                Log.d("USERLOGIN", "----------------------H----------------------------");
//                Log.d("USERLOGIN", "----------------------I----------------------------");
//                Log.d("USERLOGIN", "----------------------J----------------------------");
                userLogin();
                break;

            case R.id.forgetpw:
                //go to forget password page
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    //get & display current user's profile
    @Override
    protected void onStart() {
        super.onStart();

        //signin authentication and redirect to landing page
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, Perferences1.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }

    //get user credentials & convert it back to string

    private void userLogin() {
        //get user email and password
        String email = editTextTextPersonNamesi2.getText().toString().trim();
        String password = editTextTextPersonNamesi3.getText().toString().trim();

        //check email is entered
        if (email.isEmpty()) {
            editTextTextPersonNamesi2.setError("Email is required");
            editTextTextPersonNamesi2.requestFocus();
            return;
        }
        //check whether email is valid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextTextPersonNamesi2.setError("Enter a valid email!");
            editTextTextPersonNamesi2.requestFocus();
            return;
        }
        //check whether password is entered
        if (password.isEmpty()) {
            editTextTextPersonNamesi3.setError("Password is Required");
            editTextTextPersonNamesi3.requestFocus();
            return;
        }
        //check passoword length is more than 8 characters
        if (password.length() < 8) {
            editTextTextPersonNamesi3.setError("Minimum Password length should be 8 characters!");
            editTextTextPersonNamesi3.requestFocus();
            return;
        }

        Log.d("USERLOGIN", "----------------------A----------------------------");
        Log.d("USERLOGIN", "----------------------B----------------------------");
        Log.d("USERLOGIN", "----------------------C----------------------------");
        progressBarSignin.setVisibility(View.GONE);
        //signin with email and password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

//                    Log.d("USERLOGIN", "----------------------E----------------------------");
//                    Log.d("USERLOGIN", "----------------------F----------------------------");
//                    Log.d("USERLOGIN", "----------------------G----------------------------");
                    //check whether user details are correct and authenticate with firebase userdata and redirect user to landing page
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    startActivity(new Intent(SignIn.this, Perferences1.class));

                     /*if (user.isEmailVerified()) {
                        // redirect to user profile
                        startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                    }
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your email to verify your account",Toast.LENGTH_LONG)
                                .show();
                    }*/

                } else {
                    Toast.makeText(SignIn.this, "LogIn Failed! Please check your username or password again", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });


    }


    public void gotosup(View view) {
        Intent intentsup = new Intent(SignIn.this, MainActivity.class);

        startActivity(intentsup);
    }
}