package com.example.skinmanagementapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;

import com.example.skinmanagementapp.MainActivity;
import com.example.skinmanagementapp.R;
import com.example.skinmanagementapp.module.moduleInformation;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.Objects;

public class Admin_Register extends AppCompatActivity {

    ImageView imageView_Admin;
    Button admin_register_button_submit;
    EditText admin_edit_register_lastName,admin_edit_register_firstName,
            admin_edit_register_email,admin_edit_register_password,
            admin_edit_register_conform_password;
    Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__register);



        imageView_Admin=findViewById(R.id.imageView_Admin);
        admin_register_button_submit=findViewById(R.id.admin_register_button_submit);
        admin_edit_register_lastName=findViewById(R.id.admin_edit_register_lastName);
        admin_edit_register_firstName=findViewById(R.id.admin_edit_register_firstName);
        admin_edit_register_email=findViewById(R.id.admin_edit_register_email);
        admin_edit_register_password=findViewById(R.id.admin_edit_register_password);
        admin_edit_register_conform_password = findViewById(R.id.admin_edit_register_conform_password);


        admin_register_button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
                backup();
            }
            @SuppressLint("ShowToast")
            private void backup() {
                Intent intent=new Intent(Admin_Register.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(Admin_Register.this,"Well done Please Sign in!",Toast.LENGTH_SHORT);
            }
        });
        imageView_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }
    private void upload(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,10);
    }
    private void signup(){

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(admin_edit_register_email.getText().toString(), admin_edit_register_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final String uid=
                            Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                    final StorageReference storageReference =
                            FirebaseStorage.getInstance().getReference().child("Admin").child(uid);
                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull
                                                       Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {

                                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        String imageUri = task.toString();
                                        moduleInformation ModuleInformation = new moduleInformation();
                                        moduleInformation.Last_Name = admin_edit_register_lastName.getText().toString();
                                        moduleInformation.First_Name = admin_edit_register_firstName.getText().toString();
                                        moduleInformation.Email = admin_edit_register_email.getText().toString();
                                        moduleInformation.password = admin_edit_register_password.getText().toString();
                                        moduleInformation.Conform = admin_edit_register_conform_password.getText().toString();
                                        moduleInformation.uid=uid;

                                        FirebaseDatabase.getInstance().getReference().child("Admin").child(uid).setValue(ModuleInformation);
                                    }
                                });
                            } else {
                                Toast.makeText(Admin_Register.this, "there is an error please fill all the boxes", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{

                    Toast.makeText(Admin_Register.this,"there is an error please fill all the boxes",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            imageView_Admin.setImageURI(data.getData());
            imageUri = data.getData();
        }
    }



    }
