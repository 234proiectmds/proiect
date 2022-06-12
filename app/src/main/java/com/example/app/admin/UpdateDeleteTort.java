package com.example.app.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class UpdateDeleteTort extends AppCompatActivity {
    TextInputLayout desc, cant, pretTort;
    TextView cakeName;
    ImageButton imageButton;
    Uri mCropimageuri, imageuri;
    Button updateCake, deleteCake;
    String descriere, cantitate, pret, torturi, adminId, randomUID, dburi, ID;
    StorageReference ref;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, data;
    FirebaseAuth FAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_produs);

        desc = (TextInputLayout) findViewById(R.id.descriere);
        cant = (TextInputLayout) findViewById((R.id.cantitate));
        pretTort = (TextInputLayout) findViewById(R.id.pret);
        cakeName = (TextView) findViewById(R.id.torturi);
        imageButton = (ImageButton) findViewById(R.id.imageupload);
        updateCake = (Button) findViewById(R.id.Updatedish);
        deleteCake = (Button) findViewById(R.id.Deletedish);
        ID = getIntent().getStringExtra("updatedeletecake");

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        data = firebaseDatabase.getInstance().getReference("Admin").child(userId);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin admin = snapshot.getValue(Admin.class);

                updateCake.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        descriere = desc.getEditText().getText().toString().trim();
                        cantitate = cant.getEditText().getText().toString().trim();
                        pret = pretTort.getEditText().getText().toString().trim();

                        if (isValid()) {
                            if (imageuri != null)
                                uploadImage();
                            else
                                updateDesc(dburi);
                        }
                    }
                });

                deleteCake.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDeleteTort.this);
                        builder.setMessage("Sunteti sigur ca doriti sa stergeti produsul?");
                        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseDatabase.getInstance().getReference("DetaliiTorturi").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID).removeValue();
                                AlertDialog.Builder cake = new AlertDialog.Builder(UpdateDeleteTort.this);
                                cake.setMessage("Tortul a fost sters!");
                                cake.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(UpdateDeleteTort.this, AdminTablouNavigatie.class));
                                    }
                                });
                                AlertDialog alertDialog = cake.create();
                                alertDialog.show();
                            }
                        });
                        builder.setNegativeButton("NU", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });

                String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                progressDialog = new ProgressDialog(UpdateDeleteTort.this);
                databaseReference = FirebaseDatabase.getInstance().getReference("DetaliiTorturi").child(idUser).child(ID);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UpdateTortModel updateCakeModel = snapshot.getValue(UpdateTortModel.class);
                        desc.getEditText().setText(updateCakeModel.getDescriere());
                        cant.getEditText().setText(updateCakeModel.getCantitate());
                        pretTort.getEditText().setText(updateCakeModel.getPret());
                        cakeName.setText("Nume tort: " + updateCakeModel.getTorturi());
                        torturi = updateCakeModel.getTorturi();
                        Glide.with(UpdateDeleteTort.this).load(updateCakeModel.getImageURL()).into(imageButton);
                        dburi = updateCakeModel.getImageURL();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                FAuth = FirebaseAuth.getInstance();
                databaseReference = firebaseDatabase.getInstance().getReference("DetaliiTorturi");
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSelectImageClick(v);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void updateDesc(String buri) {
        adminId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DetaliiTorturi info = new DetaliiTorturi(torturi, cantitate, pret, descriere, buri, ID, adminId);
        firebaseDatabase.getInstance().getReference("DetaliiTorturi").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID)
                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(UpdateDeleteTort.this, "Actualizare reusita!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage() {
        if (imageuri != null) {
            progressDialog.setTitle("Se incarca....");
            progressDialog.show();
            randomUID = UUID.randomUUID().toString();
            ref = storageReference.child(randomUID);
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            updateDesc(String.valueOf(uri));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateDeleteTort.this, "Eroare: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Incarcat " + (int) progress + "%");
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
        pretTort.setErrorEnabled(false);
        pretTort.setError("");

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
            pretTort.setErrorEnabled(true);
            pretTort.setError("Va rugam setati pretul unitar al produsului!");
        } else {
            pretTort.setError(null);
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
        if (mCropimageuri != null && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCropImageActivity(mCropimageuri);
        } else {
            Toast.makeText(this, "Operatie anulata! A fost refuzat accesul.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageuri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
                mCropimageuri = imageuri;
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
                Toast.makeText(this, "Decuparea a fost realizata cu succes!" + result.getSampleSize(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Decupare esuata: " + result.getError(), Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}