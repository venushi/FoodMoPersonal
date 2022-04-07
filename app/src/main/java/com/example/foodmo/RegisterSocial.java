package com.example.foodmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterSocial extends AppCompatActivity {

    EditText mEmailEt, mPasswordEt;
    Button mRegisterBtn;
    ProgressDialog progressDialog;
    TextView mHaveAccountTv;

    private FirebaseAuth sAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_social);

        ActionBar actionBar=getSupportActionBar();
//        actionBar.setTitle("Create Account");

//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);

        mEmailEt = findViewById(R.id.emailET);
        mPasswordEt = findViewById(R.id.passwordET);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mHaveAccountTv=findViewById(R.id.have_accountTv);

        sAuth=FirebaseAuth.getInstance();


        progressDialog  = new ProgressDialog(this);
        progressDialog.setMessage("Registering User....");


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmailEt.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);

                }

                else if (password.length()<6){
                    mPasswordEt.setError("Password length at least 6 characters");
                    mPasswordEt.setFocusable(true);


                }

                else{
                    registerUser(email,password);
                }

            }
        });


        mHaveAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterSocial.this, login_social.class));
            }
        });

    }

    private void registerUser(String email,String password){

        progressDialog.show();

        sAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            progressDialog.dismiss();
                            FirebaseUser user = sAuth.getCurrentUser();

                            String email=user.getEmail();
                            String uid=user.getUid();
                            HashMap<Object,String>hashMap=new HashMap<>();

                            hashMap.put("email",email);
                            hashMap.put("uid",uid);

                            hashMap.put("name","");

                            hashMap.put("phone","");

                            hashMap.put("image","");
                            hashMap.put("cover","");


                            FirebaseDatabase databse = FirebaseDatabase.getInstance();
                            DatabaseReference reference = databse.getReference("Social_Users");

                            reference.child(uid).setValue(hashMap);



                            Toast.makeText(RegisterSocial.this, "Registered....\n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterSocial.this, DashboardActivity.class));
                            finish();
                        }

                        else{

                            progressDialog.dismiss();
                            Toast.makeText(RegisterSocial.this,"Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(RegisterSocial.this,""+e.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();  //go previous  activity
        return super.onSupportNavigateUp();
    }
}