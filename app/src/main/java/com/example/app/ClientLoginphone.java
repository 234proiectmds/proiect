package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ClientLoginphone extends AppCompatActivity {

    TextInputLayout num;
    Button sendotp, signinemail;
    TextView signup;
    FirebaseAuth Fauth;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_loginphone);

        num = (TextInputLayout) findViewById(R.id.nrtelefon);
        sendotp = (Button)findViewById(R.id.logintel);
        signinemail=(Button)findViewById(R.id.loginmail);
        signup = (TextView)findViewById(R.id.textView3);

        Fauth = FirebaseAuth.getInstance();

        sendotp.setOnClickListener(v -> {
            number =  num.getEditText().getText().toString().trim();
            String phoneNumber = "+40" + number;
            Intent b = new Intent(ClientLoginphone.this,ClientSendOtp.class);
            b.putExtra("phonenumber",phoneNumber);
            startActivity(b);
            finish();
        });

        signup.setOnClickListener(v -> {
            startActivity(new Intent(ClientLoginphone.this,ClientRegistration.class));
            finish();
        });
        signinemail.setOnClickListener(v -> {
            startActivity(new Intent(ClientLoginphone.this,ClientRegistration.class));
            finish();
        });
    }
}