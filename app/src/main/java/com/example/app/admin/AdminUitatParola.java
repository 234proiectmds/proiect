package com.example.app.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.admin.AdminLoginEmail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AdminUitatParola extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnReset, btnInapoi;
    private FirebaseAuth Fauth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_uitat_parola);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnInapoi = (Button) findViewById(R.id.btnBack);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Fauth = FirebaseAuth.getInstance();

        btnInapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminUitatParola.this, AdminLoginEmail.class));
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Introdu emailul cu care te-ai logat", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                Fauth.sendPasswordResetEmail(email)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AdminUitatParola.this, "Am trimis link-ul de resetare a parolei", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AdminUitatParola.this, "Eroare la trimiterea emailului!", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}