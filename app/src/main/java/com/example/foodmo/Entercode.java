package com.example.foodmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Entercode extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBarec;
    private EditText editTexte;

    PhoneAuthProvider.ForceResendingToken token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entercode);

        mAuth = FirebaseAuth.getInstance();

        progressBarec = findViewById(R.id.progressBarec);
        editTexte = findViewById(R.id.editTextTextPersonNameec1);

        String phonenumber = getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phonenumber);

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String code = editTexte.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    editTexte.setError("Enter code...");
                    editTexte.requestFocus();
                    return;
                }
                verifyCode(code);
                FirebaseAuth.getInstance().signOut();
                Intent intentSUGGESTIONS = new Intent(Entercode.this, SignIn.class);
                intentSUGGESTIONS.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intentSUGGESTIONS);
            }
        });

    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Entercode.this, "Authentication is successful", Toast.LENGTH_SHORT).show();

                            //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            //startActivity(new Intent(VerifyPhoneActivity.this, MainActivity.class));
                            //Intent intent = new Intent(VerifyPhoneActivity.this,MainActivity.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            //startActivity(intent);

                        } else {
                            Toast.makeText(Entercode.this, "Authentication is not successful" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        progressBarec.setVisibility(View.VISIBLE);

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        //verifying the code
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            token = forceResendingToken;
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);
            Toast.makeText(Entercode.this, "OTP Expired..", Toast.LENGTH_SHORT).show();
        }

        //this method called when the verification is completed
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editTexte.setText(code);
                verifyCode(code);
            }


        }

        //if the verification fails
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Entercode.this, "Cannot create the account" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


    public void gotosigninec(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intentGotoSU = new Intent(Entercode.this, SignIn.class);
        startActivity(intentGotoSU);
    }


}