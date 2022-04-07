package com.example.foodmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextView textView2,textView3,textView4,textView5;
    EditText editTextusername, editTextemail, editTextpw, editTextTextrepw;
    ProgressBar pgsignup;
    Button signup,signinMain;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
        textView5=findViewById(R.id.textView5);

        editTextusername =findViewById(R.id.editTextTextPersonName2);
        editTextemail =findViewById(R.id.editTextTextPersonName3);
        editTextpw =findViewById(R.id.editTextTextPersonName4);
        editTextTextrepw =findViewById(R.id.editTextTextPersonName5);

        pgsignup=findViewById(R.id.pgsignup);

        mAuth = FirebaseAuth.getInstance();

        signup = (Button) findViewById(R.id.signup);
        signinMain = (Button) findViewById(R.id.signinmain);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling registerUser function
                registerUser();
            }

        });
    }

    private void registerUser() {
        String email = editTextemail.getText().toString().trim();
        String password = editTextpw.getText().toString().trim();
        String fullName = editTextusername.getText().toString().trim();
        String reEnter = editTextTextrepw.getText().toString().trim();



        if(fullName.isEmpty()){
            editTextusername.setError("Full Name is Required");
            editTextusername.requestFocus();
            return;
        }


        if(email.isEmpty()){
            editTextusername.setError("Email is Required");
            editTextusername.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextusername.setError("Email is Invalid");
            editTextusername.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextpw.setError("Password is Required");
            editTextpw.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextpw.setError("Minimum Password length should be 6 characters!");
            editTextpw.requestFocus();
            return;
        }
        if (reEnter.isEmpty()) {
            editTextTextrepw.setError("Re_ Enter Password");
            editTextTextrepw.requestFocus();
            return;
        }
        // Notifying user if password entered and re entered password does not match
        if (!password.equals(reEnter)) {
            editTextTextrepw.setError("Password does not match");
            editTextTextrepw.requestFocus();
            return;
        }

        pgsignup.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user= new User(fullName,email);

                            //startActivity(new Intent(RegisterUser.this, EnterPhoneActivity.class));

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Intent intentveri = new Intent(MainActivity.this, EnterPhonenumber.class);
                                        startActivity(intentveri);
                                        Toast.makeText(MainActivity.this,"User has been registered successfully!",Toast.LENGTH_LONG)
                                                .show();

                                        //redirect to login layout
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this,"Registration Unsuccessful. Try Again!",Toast.LENGTH_LONG)
                                                .show();
                                    }
                                    pgsignup.setVisibility(View.GONE);
                                }
                            });

                        }
                    }
                });

    }

    public void gotosignin1(View view) {
        Intent intentsig = new Intent(MainActivity.this, SignIn.class);
        startActivity(intentsig);
    }
}