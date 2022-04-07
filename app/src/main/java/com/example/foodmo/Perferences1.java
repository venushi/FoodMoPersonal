package com.example.foodmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Perferences1 extends AppCompatActivity {

    private Button done;
    RadioButton c1, c2, c3, c4, c5,c6;
    FirebaseDatabase database;
    //DatabaseReference reference3;

    Member member;
    int i = 0;

    FirebaseUser mUser;
    FirebaseAuth mAuth;

    List<Object> reference = new ArrayList<>();
    HashMap<String, Object> preference = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perferences1);


        //reference3=database.getInstance().getReference().child("Users");

        member = new Member() {
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
        done = findViewById(R.id.done);
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        c4 = findViewById(R.id.c4);
        c5 = findViewById(R.id.c5);
        c6 = findViewById(R.id.c6);



        String d1 = "Happy";
        String d2 = "Sad";
        String d3 = "Angry";
        String d4 = "Anxious";
        String d5 = "Stressed";
        String d6 = "Can't Sleep";



        mUser = FirebaseAuth.getInstance().getCurrentUser();

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
        ImageView myImageView = (ImageView)findViewById(R.id.recFood);


        done.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        ////////////////

                                        ///////////////
                                        if (c1.isChecked()) {
                                            reference.add(d1);

                                        } else {
                                            //
                                        }
                                        if (c2.isChecked()) {
                                            reference.add(d2);

                                        } else {
                                            //
                                        }
                                        if (c3.isChecked()) {
                                            reference.add(d3);

                                        } else {
                                            //
                                        }
                                        if (c4.isChecked()) {
                                            reference.add(d4);

                                        } else {
                                            //
                                        }
                                        if (c5.isChecked()) {
                                            reference.add(d5);

                                        } else {
                                            //
                                        }
                                        if (c6.isChecked()) {
                                            reference.add(d6);

                                        } else {
                                            //

                                        }
                                        preference.put("preferences", reference);
                                        FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).updateChildren(preference)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(Perferences1.this, "Successful", Toast.LENGTH_SHORT).show();
                                                        //Intent intent=new Intent(UserPreferences.this,DashboardActivity.class);
                                                        //startActivity(intent);
                                                    }
                                                });
                                        Log.d("User", mUser.getUid());
                                        Toast.makeText(Perferences1.this, "Successful !", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Perferences1.this, perferences2.class);
                                        startActivity(intent);
                                    }
                                }

        );
    }
}