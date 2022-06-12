package com.example.app.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.app.MesajAlerta;
import com.example.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ClientInregistrare extends AppCompatActivity {

    TextInputLayout FirstName, LastName, Email, Password, ConfirmPassword, Mobile, Street, StreetNumber, DetailsAddress, ZipCode;
    Spinner countySpin, citySpin;
    Button signUp, emaill, phone;

    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String firstName, lastName, email, password, confirmPassword, mobile, county, city, street, streetNumber, detailsAddress, zipCode, role="Client";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_inregistrare);

        FirstName = (TextInputLayout) findViewById(R.id.prenume);
        LastName = (TextInputLayout) findViewById(R.id.nume);
        Email = (TextInputLayout) findViewById(R.id.email);
        Password = (TextInputLayout) findViewById(R.id.parola);
        ConfirmPassword = (TextInputLayout) findViewById(R.id.vparola);
        Mobile = (TextInputLayout) findViewById(R.id.nrtelefon);
        countySpin = (Spinner) findViewById(R.id.judet);
        citySpin = (Spinner) findViewById(R.id.oras);
        Street = (TextInputLayout) findViewById(R.id.strada);
        StreetNumber = (TextInputLayout) findViewById(R.id.nradresa);
        DetailsAddress = (TextInputLayout) findViewById(R.id.detalii);
        ZipCode = (TextInputLayout) findViewById(R.id.codpostal);

        signUp = (Button) findViewById(R.id.signIn);
        emaill = (Button) findViewById(R.id.loginmail);
        phone = (Button) findViewById(R.id.logintel);

        countySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                county = value.toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                city = value.toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        databaseReference = firebaseDatabase.getInstance().getReference("Client");
        FAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = FirstName.getEditText().getText().toString().trim();
                lastName = LastName.getEditText().getText().toString().trim();
                email = Email.getEditText().getText().toString().trim();
                password = Password.getEditText().getText().toString().trim();
                confirmPassword = ConfirmPassword.getEditText().getText().toString().trim();
                mobile = Mobile.getEditText().getText().toString().trim();
                street = Street.getEditText().getText().toString().trim();
                streetNumber = StreetNumber.getEditText().getText().toString().trim();
                detailsAddress = DetailsAddress.getEditText().getText().toString().trim();
                zipCode = ZipCode.getEditText().getText().toString().trim();

                if(isValid()){
                    final ProgressDialog mDialog = new ProgressDialog(ClientInregistrare.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Inregistrare in progres. Va rugam asteptati .....");
                    mDialog.show();

                    FAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(userId);
                                final Map<String, String> userMap = new HashMap<>();
                                userMap.put("Role",role);
                                databaseReference.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Map<String,String> userMap1 = new HashMap<>();
                                        userMap1.put("Nume",lastName);
                                        userMap1.put("Prenume",firstName);
                                        userMap1.put("Email",email);
                                        userMap1.put("Parola",password);
                                        userMap1.put("Confirmare Parola",confirmPassword);
                                        userMap1.put("Mobil",mobile);
                                        userMap1.put("Strada",street);
                                        userMap1.put("Nr strada",streetNumber);
                                        userMap1.put("Detalii adresa",detailsAddress);
                                        userMap1.put("Cod postal",zipCode);

                                        firebaseDatabase.getInstance().getReference("Client")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(userMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mDialog.dismiss();
                                                FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(ClientInregistrare.this);
                                                            builder.setMessage("V-ati inregistrat cu succes! Acum va rugam verificati emailul dvs.");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                    dialog.dismiss();
                                                                    String phonenumber = "+40"+ mobile;
                                                                    Intent b = new Intent(ClientInregistrare.this, ClientVerificareTelefon.class);
                                                                    b.putExtra("phonenumber",phonenumber);
                                                                    startActivity(b);
                                                                }
                                                            });
                                                            AlertDialog Alert = builder.create();
                                                            Alert.show();

                                                        }else
                                                        {
                                                            mDialog.dismiss();
                                                            MesajAlerta.ShowAlert(ClientInregistrare.this,"Error",task.getException().getMessage());
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                            else
                            {
                                mDialog.dismiss();
                                MesajAlerta.ShowAlert(ClientInregistrare.this,"Error",task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });
        emaill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientInregistrare.this, ClientLoginEmail.class));
                finish();
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientInregistrare.this, ClientLoginTelefon.class));
                finish();
            }
        });
    }

    String emailPattern = "[a-zA-Z0-9._]+@[a-z]+\\.+[a-z]+";
    String lastNamePattern = "([A-Z][a-z]*)([\\s\\\'-][A-Z][a-z]*)*";
    String firstNamePattern = "([A-Z][a-z]*)([\\s\\\'-][A-Z][a-z]*)*";

    public boolean isValid(){
        Email.setErrorEnabled(false);
        Email.setError("");
        FirstName.setErrorEnabled(false);
        FirstName.setError("");
        LastName.setErrorEnabled(false);
        LastName.setError("");
        Password.setErrorEnabled(false);
        Password.setError("");
        ConfirmPassword.setErrorEnabled(false);
        ConfirmPassword.setError("");
        Mobile.setErrorEnabled(false);
        Mobile.setError("");
        Street.setErrorEnabled(false);
        Street.setError("");
        StreetNumber.setErrorEnabled(false);
        StreetNumber.setError("");
        DetailsAddress.setErrorEnabled(false);
        DetailsAddress.setError("");
        ZipCode.setErrorEnabled(false);
        ZipCode.setError("");

        boolean isValid = false,
                isValideLastName = false,
                isValideFirstName = false,
                isValideEmail = false,
                isValidePassword = false,
                isValideConfirmPassword = false,
                isValideMobile = false,
                isValideStreet = false,
                isValideStreetNumber = false,
                isValideZipCode = false;

//                check first name
        if(TextUtils.isEmpty(firstName)){
            FirstName.setErrorEnabled(true);
            FirstName.setError("Introduceti prenumele dvs.!  Acesta nu poate lipsi.");
        }else{
            if(firstName.matches(firstNamePattern)){
                isValideFirstName = true;
            }else{
                FirstName.setErrorEnabled(true);
                FirstName.setError("Introduceti un prenume valid! Prenumele nu poate contine cifre sau alte caractere in afara de '-' si incepe cu litera mare!");
            }
        }
//                check last name
        if(TextUtils.isEmpty(lastName)){
            LastName.setErrorEnabled(true);
            LastName.setError("Introduceti numele dvs. de familie!  Acesta nu poate lipsi.");
        }else{
            if(lastName.matches(lastNamePattern)){
                isValideLastName = true;
            }else{
                LastName.setErrorEnabled(true);
                LastName.setError("Introduceti un nume valid! Numele nu poate contine cifre sau alte caractere si incepe cu litera mare!");
            }
        }
//               check email
        if(TextUtils.isEmpty(email)){
            Email.setErrorEnabled(true);
            Email.setError("Introduceti email-ul dvs! Acesta nu poate lipsi.");
        }else {
            if (email.matches(emailPattern)) {
                isValideEmail = true;
            } else {
                Email.setErrorEnabled(true);
                Email.setError("Introduceti un email valid!");
            }
        }
//              check password
        if(TextUtils.isEmpty(password)){
            Password.setErrorEnabled(true);
            Password.setError("Creati o parola. Aceasta nu poate lipsi.");
        }else{
            if(password.length()<8){
                Password.setErrorEnabled(true);
                Password.setError("Parola dvs. este slaba. Creati o parola care are cel putin 8 caractere.");
            }
            isValidePassword = true;
        }
//              check confirm password
        if(TextUtils.isEmpty(confirmPassword)){
            ConfirmPassword.setErrorEnabled(true);
            ConfirmPassword.setError("Confirmati parola introdusa mai sus!");
        }else{
            if(!password.equals(confirmPassword)){
                ConfirmPassword.setErrorEnabled(true);
                ConfirmPassword.setError("Parolele nu se potrivesc!");
            }else{
                isValideConfirmPassword = true;
            }

        }
//              check mobile number
        if(TextUtils.isEmpty(mobile)){
            Mobile.setErrorEnabled(true);
            Mobile.setError("Nr. dvs. de telefon nu poate lipsi!");
        }else{
            if(mobile.length()<10){
                Mobile.setErrorEnabled(true);
                Mobile.setError("Numar de telefon invalid!");
            }else{
                isValideMobile = true;
            }
        }
//              check street
        if(TextUtils.isEmpty(street)){
            Street.setErrorEnabled(true);
            Street.setError("Nu ati introdus strada!");
        }else{
            isValideStreet = true;
        }

//              check street number
        if(TextUtils.isEmpty(streetNumber)){
            StreetNumber.setErrorEnabled(true);
            StreetNumber.setError("Nu ati introdus numarul strazii!");
        }else{
            isValideStreetNumber = true;
        }
//              check zip code
        if(TextUtils.isEmpty(zipCode)){
            ZipCode.setErrorEnabled(true);
            ZipCode.setError("Nu ati introdus codul postal!");
        }else{
            if(zipCode.length()!=6){
                ZipCode.setErrorEnabled(true);
                ZipCode.setError("Codul postal este format din 6  cifre! Nu ati introdus unul valid.");
            }else{
                isValideZipCode = true;
            }
        }
        isValid = (isValideLastName && isValideFirstName && isValideEmail && isValidePassword && isValideConfirmPassword && isValideMobile && isValideStreet && isValideStreetNumber && isValideZipCode)? true : false;

        return isValid;
    }
}