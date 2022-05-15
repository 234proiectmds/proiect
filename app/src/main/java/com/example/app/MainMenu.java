package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity {
    Button signinemail, signinphone, signup;
    ImageView bgimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        final Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);

        bgimage = findViewById(R.id.back);
        bgimage.setAnimation(zoomin);
        bgimage.setAnimation(zoomout);

        zoomout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bgimage.startAnimation(zoomout);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        signinemail=(Button)findViewById(R.id.signInwithEmail);
        signinemail.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent signEmail = new Intent(MainMenu.this, ChooseOne.class);
                 signEmail.putExtra("Home", "Email");
                 startActivity(signEmail);
                 finish();
             }
         });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}