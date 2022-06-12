package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.app.admin.AdminInregistrare;
import com.example.app.admin.AdminLoginEmail;
import com.example.app.admin.AdminLoginTelefon;
import com.example.app.client.ClientInregistrare;
import com.example.app.client.ClientLoginEmail;
import com.example.app.client.ClientLoginTelefon;

public class OptiuniPrimaPagina extends AppCompatActivity {
    Button admin, client;
    Intent intent;
    String type;
    ConstraintLayout bgimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optiuni_prima_pagina);

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

        admin = (Button) findViewById(R.id.admin);
        client = (Button) findViewById(R.id.client);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("Email")) {
                    Intent loginemail = new Intent(OptiuniPrimaPagina.this, AdminLoginEmail.class);
                    startActivity(loginemail);
                    finish();
                }
                if (type.equals("Phone")) {
                    Intent loginphone = new Intent(OptiuniPrimaPagina.this, AdminLoginTelefon.class);
                    startActivity(loginphone);
                    finish();
                }
                if (type.equals("SignUp")) {
                    Intent Register = new Intent(OptiuniPrimaPagina.this, AdminInregistrare.class);
                    startActivity(Register);
                    finish();
                }
            }
        });

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("Email")) {
                    Intent loginemailclient = new Intent(OptiuniPrimaPagina.this, ClientLoginEmail.class);
                    startActivity(loginemailclient);
                    finish();
                }
                if (type.equals("Phone")) {
                    Intent loginphoneclient = new Intent(OptiuniPrimaPagina.this, ClientLoginTelefon.class);
                    startActivity(loginphoneclient);
                    finish();
                }
                if (type.equals("SignUp")) {
                    Intent Registerclient = new Intent(OptiuniPrimaPagina.this, ClientInregistrare.class);
                    startActivity(Registerclient);
                    finish();
                }
            }
        });
    }
}