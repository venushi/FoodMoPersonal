package com.example.foodmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class perferences2 extends AppCompatActivity {

    private Button done2;
    RadioButton c12, c22, c32, c42, c52, c62;
    FirebaseDatabase database;
    //DatabaseReference reference3;

    Member member2;
    int i = 0;

    FirebaseUser mUser2;
    FirebaseAuth mAuth;

    List<Object> reference2 = new ArrayList<>();
    HashMap<String, Object> preference2 = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perferences2);


        //reference3=database.getInstance().getReference().child("Users");

        member2 = new Member() {
            @NonNull
            @Override
            public Class<?> getDeclaringClass() {
                return null;
            }

            @NonNull
            @Override
            public String getName() {
                return null;
            }

            @Override
            public int getModifiers() {
                return 0;
            }

            @Override
            public boolean isSynthetic() {
                return false;
            }
        };
        done2 = findViewById(R.id.done2);
        c12 = findViewById(R.id.c12);
        c22 = findViewById(R.id.c22);
        c32 = findViewById(R.id.c32);
        c42 = findViewById(R.id.c42);
        c52 = findViewById(R.id.c52);
        c62 = findViewById(R.id.c62);



        String d12 = "Sweet";
        String d22 = "Spicy";
        String d32 = "Sour";
        String d42 = "Bitter";
        String d52 = "Salty";
        String d62 = "Savoury";



        mUser2 = FirebaseAuth.getInstance().getCurrentUser();

        /*reference3.addValueEventListener(new ValueEventListener(){


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    i=(int)snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


        done2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        ////////////////

                                        ///////////////
                                        if (c12.isChecked()) {
                                            reference2.add(d12);
                                        } else {
                                            //
                                        }
                                        if (c22.isChecked()) {
                                            reference2.add(d22);
                                        } else {
                                            //
                                        }
                                        if (c32.isChecked()) {
                                            reference2.add(d32);
                                        } else {
                                            //
                                        }
                                        if (c42.isChecked()) {
                                            reference2.add(d42);
                                        } else {
                                            //
                                        }
                                        if (c52.isChecked()) {
                                            reference2.add(d52);
                                        } else {
                                            //
                                        }
                                        if (c62.isChecked()) {
                                            reference2.add(d62);
                                        } else {
                                            //

                                        }
                                        preference2.put("preferencesTaste", reference2);
                                        FirebaseDatabase.getInstance().getReference().child("Users").child(mUser2.getUid()).updateChildren(preference2)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(perferences2.this, "Successful", Toast.LENGTH_SHORT).show();
                                                        //Intent intent=new Intent(UserPreferences.this,DashboardActivity.class);
                                                        //startActivity(intent);
                                                    }
                                                });
                                        Log.d("User", mUser2.getUid());
                                        Toast.makeText(perferences2.this, "Successful !", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(perferences2.this, perferences3.class);
                                        startActivity(intent);
                                    }
                                }

        );
    }
}