package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.Button;

public class ChooseOne extends AppCompatActivity {
    Button admin, client;
    Intent intent;
    String type;
    ConstraintLayout bgimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_one);

        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.imagine6), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.imagine2), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.imagine1), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.imagine4), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.imagine5), 3000);

        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(850);
        animationDrawable.setExitFadeDuration(1600);
        bgimage = findViewById(R.id.back3);
        bgimage.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        intent = getIntent();
        type = intent.getStringExtra("Home").toString().trim();

//        admin = (Button) findViewById(R.id.admin);

    }
}