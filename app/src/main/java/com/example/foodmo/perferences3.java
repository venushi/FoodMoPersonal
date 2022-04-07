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

public class perferences3 extends AppCompatActivity {

    private Button done3;
    RadioButton c13, c23, c33, c43, c53, c63;
    FirebaseDatabase database;
    //DatabaseReference reference3;

    Member member3;
    int i = 0;

    FirebaseUser mUser3;
    FirebaseAuth mAuth;

    List<Object> reference3 = new ArrayList<>();
    HashMap<String, Object> preference3 = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perferences3);


        //reference3=database.getInstance().getReference().child("Users");

        member3 = new Member() {
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
        done3 = findViewById(R.id.done3);
        c13 = findViewById(R.id.c13);
        c23 = findViewById(R.id.c23);
        c33 = findViewById(R.id.c33);
        c43 = findViewById(R.id.c43);
        c53 = findViewById(R.id.c53);
        c63 = findViewById(R.id.c63);




        String d13 = "Breakfast";
        String d23 = "Mid - morning Snack";
        String d33 = "Lunch";
        String d43 = "Mid - afternoon Snack";
        String d53 = "Dinner";
        String d63 = "Desserts";




        mUser3 = FirebaseAuth.getInstance().getCurrentUser();

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


        done3.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {

                                         ////////////////

                                         ///////////////
                                         if (c13.isChecked()) {
                                             reference3.add(d13);
                                         } else {
                                             //
                                         }
                                         if (c23.isChecked()) {
                                             reference3.add(d23);
                                         } else {
                                             //
                                         }
                                         if (c33.isChecked()) {
                                             reference3.add(d33);
                                         } else {
                                             //
                                         }
                                         if (c43.isChecked()) {
                                             reference3.add(d43);
                                         } else {
                                             //
                                         }
                                         if (c53.isChecked()) {
                                             reference3.add(d53);
                                         } else {
                                             //
                                         }
                                         if (c63.isChecked()) {
                                             reference3.add(d63);
                                         } else {
                                             //

                                         }
                                         preference3.put("preferencesMeal", reference3);
                                         FirebaseDatabase.getInstance().getReference().child("Users").child(mUser3.getUid()).updateChildren(preference3)
                                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<Void> task) {
                                                         Toast.makeText(perferences3.this, "Successful", Toast.LENGTH_SHORT).show();
                                                         //Intent intent=new Intent(UserPreferences.this,DashboardActivity.class);
                                                         //startActivity(intent);
                                                     }
                                                 });
                                         Log.d("User", mUser3.getUid());
                                         Toast.makeText(perferences3.this, "Successful !", Toast.LENGTH_SHORT).show();
                                         Intent intent = new Intent(perferences3.this, LandingPage.class);
                                         startActivity(intent);
                                     }
                                 }

        );
    }
}