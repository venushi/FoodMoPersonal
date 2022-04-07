package com.example.foodmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class LandingPage extends AppCompatActivity {
    ImageView recFood;
    FirebaseUser mUser;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        ImageView myImageView = (ImageView)findViewById(R.id.recFood);
        button=findViewById(R.id.button);


//        recFood=findViewById(R.id.recFood);

        mUser = FirebaseAuth.getInstance().getCurrentUser();






        DatabaseReference zonesRef;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("Users");
                DatabaseReference zone1Ref = zonesRef.child(mUser.getUid());
                DatabaseReference zone1NameRef = zone1Ref.child("perferences");
                DatabaseReference zone2Ref = zone1NameRef.child("0");

                zone1NameRef.addValueEventListener(new ValueEventListener() {

                    private static final String TAG = "HI";

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.i(TAG, dataSnapshot.getValue(String.class));

                        if((dataSnapshot.hasChild("Happy"))){

                            myImageView.setImageResource(R.drawable.happy);
                        }
                        if((dataSnapshot.hasChild("Sad"))){

                            myImageView.setImageResource(R.drawable.sad);
                        }
                        if((dataSnapshot.hasChild("Angry"))){

                            myImageView.setImageResource(R.drawable.angry);
                        }
                        if((dataSnapshot.hasChild("Anxious"))){

                            myImageView.setImageResource(R.drawable.anxious);
                        }
                        if((dataSnapshot.hasChild("Stressed"))){

                            myImageView.setImageResource(R.drawable.stressed);
                        }
                        if((dataSnapshot.hasChild("Sleep"))){

                            myImageView.setImageResource(R.drawable.sleep);
                        }




                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "onCancelled", databaseError.toException());
                    }
                });




            }
        });




    }

    public void gotosocial(View view) {

        Intent intentskip = new Intent(LandingPage.this, SocialmediaReg.class);

        startActivity(intentskip);
    }
}