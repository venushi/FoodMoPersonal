package com.example.foodmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splashScreen extends AppCompatActivity {


    private static int SPLASH_SCREEN = 5000;

    Animation topAnim,bottomAnim;
    ImageView imageSS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);





        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);

        imageSS = findViewById(R.id.imageiewss);

        imageSS.setAnimation(topAnim);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentStart = new Intent(splashScreen.this, SignIn.class);
                startActivity(intentStart);
                finish();

            }
        }, SPLASH_SCREEN);


    }





}
