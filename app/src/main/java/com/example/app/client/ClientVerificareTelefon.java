package com.example.app.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.MainMenu;
import com.example.app.MesajAlerta;
import com.example.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ClientVerificareTelefon extends AppCompatActivity {
    String codVerificare;
    FirebaseAuth FAuth;
    Button verify, resend;
    TextView txt;
    EditText enterCode;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_verificare_telefon);

        phoneNumber = getIntent().getStringExtra("phonenumber").trim();
        enterCode = (EditText) findViewById(R.id.codee);
        txt = (TextView) findViewById(R.id.text);
        resend = (Button) findViewById(R.id.Resendotpp);
        verify = (Button) findViewById(R.id.Verifyy);
        FAuth = FirebaseAuth.getInstance();
        resend.setVisibility(View.INVISIBLE);
        txt.setVisibility(View.INVISIBLE);

        sendVerificationCode(phoneNumber);

        verify.setOnClickListener(v -> {
            String code = enterCode.getText().toString().trim();
            resend.setVisibility(View.INVISIBLE);
            if (code.length() == 0) {
                enterCode.setError("Enter code");
                enterCode.requestFocus();
                return;
            }
            verifyCode(code);
        });

        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                txt.setVisibility(View.VISIBLE);
                txt.setText("Retrimit codul in " + millisUntilFinished / 1000 + " secunde");
            }

            @Override
            public void onFinish() {
                resend.setVisibility(View.VISIBLE);
                txt.setVisibility(View.INVISIBLE);
            }
        }.start();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend.setVisibility(View.INVISIBLE);
                resendOtp(phoneNumber);
            }
        });
    }

    private void resendOtp(String phoneNumber) {
        sendVerificationCode(phoneNumber);
    }

    private void sendVerificationCode(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mcallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                enterCode.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(ClientVerificareTelefon.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codVerificare = s;
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codVerificare, code);
        linkCredential(credential);
    }

    private void linkCredential(PhoneAuthCredential credential) {
        FAuth.getCurrentUser().linkWithCredential(credential).
                addOnCompleteListener(ClientVerificareTelefon.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(ClientVerificareTelefon.this, MainMenu.class);
                            startActivity(intent);
                            finish();
                        } else {
                            MesajAlerta.ShowAlert(ClientVerificareTelefon.this, "Error", task.getException().getMessage());
                        }
                    }
                });
    }
}