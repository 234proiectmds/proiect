package com.example.app.client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.MesajAlerta;
import com.example.app.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ClientLoginEmail extends AppCompatActivity {

    TextInputLayout email, password;
    Button signin, siginWithPhone;
    TextView Forgotpassword, signup;
    FirebaseAuth Fauth;
    String emailid, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login_email);

        try {
            email = (TextInputLayout) findViewById(R.id.email);
            password = (TextInputLayout) findViewById(R.id.parola);
            signin = (Button) findViewById(R.id.logIn);
            signup = (TextView) findViewById(R.id.signIn);
            Forgotpassword = (TextView) findViewById(R.id.forgotpass);
            siginWithPhone = (Button) findViewById(R.id.logintel);

            Fauth = FirebaseAuth.getInstance();

            signin.setOnClickListener(v -> {
                emailid = email.getEditText().getText().toString().trim();
                pwd = password.getEditText().getText().toString().trim();

                if (isValid()) {
                    final ProgressDialog mDialog = new ProgressDialog(ClientLoginEmail.this);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setCancelable(false);
                    mDialog.setMessage("Intri in cont acum. Te rog asteapta...");
                    mDialog.show();

                    Fauth.signInWithEmailAndPassword(emailid, pwd).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            mDialog.dismiss();
                            if (Fauth.getCurrentUser().isEmailVerified()) {
                                mDialog.dismiss();
                                Toast.makeText(ClientLoginEmail.this, "Bine ai venit in cont!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ClientLoginEmail.this, ClientTablouNavigare.class);
                                startActivity(intent);
                                finish();
                            } else {
                                MesajAlerta.ShowAlert(ClientLoginEmail.this, "Email neverificat", "Emailul nu este verificat");
                            }
                        } else {
                            mDialog.dismiss();
                            MesajAlerta.ShowAlert(ClientLoginEmail.this, "Error", task.getException().getMessage());
                        }
                    });
                }
            });
            signup.setOnClickListener(v -> {
                startActivity(new Intent(ClientLoginEmail.this, ClientInregistrare.class));
                finish();
            });
            Forgotpassword.setOnClickListener(v -> {
                startActivity(new Intent(ClientLoginEmail.this, ClientUitatParola.class));
                finish();
            });
            siginWithPhone.setOnClickListener(v -> {
                startActivity(new Intent(ClientLoginEmail.this, ClientLoginTelefon.class));
                finish();
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    String emailPattern = "[a-zA-Z0-9._]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        email.setErrorEnabled(false);
        email.setError("");
        password.setErrorEnabled(false);
        password.setError("");

        boolean isvalidemail = false, isvalidpass = false;
        if (TextUtils.isEmpty(emailid)) {
            email.setErrorEnabled(true);
            email.setError("Introduceti email-ul dvs! Acesta nu poate lipsi.");

        } else {
            if (emailid.matches(emailPattern)) {
                isvalidemail = true;
            } else {
                email.setErrorEnabled(true);
                email.setError("Ati introdus un email invalid!");
            }
        }

        if (TextUtils.isEmpty(pwd)) {
            password.setErrorEnabled(true);
            password.setError("Creati o parola. Aceasta nu poate lipsi.");
        } else {

            isvalidpass = true;
        }
        return isvalidemail && isvalidpass;
    }
}