package com.example.app.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

public class AdminCreeazaTort extends AppCompatActivity {
    ImageButton imageButton;
    Button creazaTort;
    Spinner torturiSpinner;
    TextInputLayout desc, cant, prt;
    String descriere, cantitate, pret, torturi, adminId, randomUID;
    Uri mcropimageuri, imageuri;
    FirebaseStorage storage;
    StorageReference storageReference, ref;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, data;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_creeaza_tort);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        torturiSpinner = (Spinner) findViewById(R.id.torturi);
        desc = (TextInputLayout) findViewById(R.id.descriere);
        cant = (TextInputLayout) findViewById(R.id.cantitate);
        prt = (TextInputLayout) findViewById(R.id.pret);
        creazaTort = (Button) findViewById(R.id.creaza);
        fAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("DetaliiTorturi");

        try {
            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            data = firebaseDatabase.getInstance().getReference("Admin").child(userid);

            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Admin admin = snapshot.getValue(Admin.class);
                    imageButton = (ImageButton) findViewById(R.id.image_upload);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onSelectImageClick(v);
                        }
                    });
                    creazaTort.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            torturi = torturiSpinner.getSelectedItem().toString().trim();
                            descriere = desc.getEditText().getText().toString().trim();
                            cantitate = cant.getEditText().getText().toString().trim();
                            pret = prt.getEditText().getText().toString().trim();

                            if (isValid()) {
                                uploadImage();
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            Log.e("Eroare: ", e.getMessage());
        }
    }

    private void uploadImage() {

        if (imageuri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(AdminCreeazaTort.this);
            progressDialog.setTitle("Se incarca...");
            progressDialog.show();
            randomUID = UUID.randomUUID().toString();
            ref = storageReference.child(randomUID);
            adminId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DetaliiTorturi info = new DetaliiTorturi(torturi, cantitate, pret,
                                    descriere, String.valueOf(uri), randomUID, adminId);
                            firebaseDatabase.getInstance().getReference("DetaliiTorturi")
                                    .child(FirebaseAuth.getInstance()
                                            .getCurrentUser().getUid()).child(randomUID)
                                    .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(AdminCreeazaTort.this,
                                            "Tort creat cu succes!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AdminCreeazaTort.this, e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() /
                            snapshot.getTotalByteCount());
                    progressDialog.setMessage("Incarcat " + (int) progress + ("%"));
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });
        }
    }

    private boolean isValid() {
        desc.setErrorEnabled(false);
        desc.setError("");
        cant.setErrorEnabled(false);
        cant.setError("");
        prt.setErrorEnabled(false);
        prt.setError("");

        boolean isValidDescription = false, isValidPrice = false, isValidQuantity = false,
                isValid = false;

        if (TextUtils.isEmpty(descriere)) {
            desc.setErrorEnabled(true);
            desc.setError("Va rugam scrieti descrierea produsului!");
        } else {
            desc.setError(null);
            isValidDescription = true;
        }

        if (TextUtils.isEmpty(cantitate)) {
            cant.setErrorEnabled(true);
            cant.setError("Va rugam setati cantitatea produsului!");
        } else {
            cant.setError(null);
            isValidQuantity = true;
        }

        if (TextUtils.isEmpty(pret)) {
            prt.setErrorEnabled(true);
            prt.setError("Va rugam setati pretul unitar al produsului!");
        } else {
            prt.setError(null);
            isValidPrice = true;
        }
        return isValidDescription && isValidQuantity && isValidPrice;
    }

    private void startCropImageActivity(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private void onSelectImageClick(View v) {
        CropImage.startPickImageActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mcropimageuri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCropImageActivity(mcropimageuri);
        } else {
            Toast.makeText(this, "Operatie anulata! A fost refuzat accesul.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageuri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
                mcropimageuri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);
            } else {
                startCropImageActivity(imageuri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ((ImageButton) findViewById(R.id.image_upload)).setImageURI((result.getUri()));
                Toast.makeText(this, "Decuparea a fost realizata cu succes!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Decupare esuata: " + result.getError(), Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}