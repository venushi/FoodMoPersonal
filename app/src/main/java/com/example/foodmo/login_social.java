package com.example.foodmo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class login_social extends Activity {

    EditText mEmailEt,mPasswordEt;
    TextView notHaveAccountTv;
    Button mLoginBtn;

    private FirebaseAuth mAuth;

    ProgressDialog pd;


    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_social);

        mAuth = FirebaseAuth.getInstance();

        mEmailEt=findViewById(R.id.emailET);
        mPasswordEt=findViewById(R.id.passwordET);
        notHaveAccountTv= findViewById(R.id.nothave_accountTv);
        mLoginBtn=findViewById(R.id.loginBtn);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email =mEmailEt.getText().toString();
                String passw = mPasswordEt.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);

                }

                else{
                    loginUser(email,passw);
                }

            }
        });

        notHaveAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login_social.this,RegisterSocial.class));
            }
        });

        pd=new ProgressDialog(this);
        pd.setMessage("Logging In...");







    }

    private void loginUser(String email, String passw) {

        pd.show();

        mAuth.signInWithEmailAndPassword(email,passw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            pd.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            if(task.getResult().getAdditionalUserInfo().isNewUser()){

                                String email=user.getEmail();
                                String uid=user.getUid();
                                HashMap<Object,String> hashMap=new HashMap<>();

                                hashMap.put("email",email);
                                hashMap.put("uid",uid);

                                hashMap.put("name","");

                                hashMap.put("phone","");

                                hashMap.put("image","");
                                hashMap.put("cover","");


                                FirebaseDatabase databse = FirebaseDatabase.getInstance();
                                DatabaseReference reference = databse.getReference("Social_Users");

                                reference.child(uid).setValue(hashMap);

                            }




                            startActivity(new Intent(login_social.this, DashboardActivity.class));

                        } else{

                            pd.dismiss();
                            Toast.makeText(login_social.this,"Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(login_social.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }

}
