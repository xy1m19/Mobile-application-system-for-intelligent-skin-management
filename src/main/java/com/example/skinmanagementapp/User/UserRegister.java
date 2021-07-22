package com.example.skinmanagementapp.User;

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

public class UserRegister extends AppCompatActivity {

    ImageView imageView_user;
    Button user_register_button_submit;
    EditText user_edit_register_lastName,
            user_edit_register_firstName,user_edit_register_email,
            user_edit_register_password,user_edit_register_age,user_edit_register_line1,
            user_edit_register_line2,user_edit_register_line3;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        imageView_user=findViewById(R.id.imageView_user);
        user_register_button_submit=findViewById(R.id.user_register_button_submit);
        user_edit_register_lastName=findViewById(R.id.user_edit_register_lastName);
        user_edit_register_firstName=findViewById(R.id.user_edit_register_firstName);
        user_edit_register_email=findViewById(R.id.user_edit_register_email);
        user_edit_register_password=findViewById(R.id.user_edit_register_password);
        user_edit_register_age = findViewById(R.id.user_edit_register_age);
        user_edit_register_line1 = findViewById(R.id.user_edit_register_line1);
        user_edit_register_line2 = findViewById(R.id.user_edit_register_line2);
        user_edit_register_line3 = findViewById(R.id.user_edit_register_line3);


        user_register_button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
                backup();
            }
            @SuppressLint("ShowToast")
            private void backup() {
                Intent intent=new Intent(UserRegister.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(UserRegister.this,"Well done Please Sign in!",Toast.LENGTH_SHORT);
            }
        });
        imageView_user.setOnClickListener(new View.OnClickListener() {
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

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user_edit_register_email.getText().toString(), user_edit_register_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final String uid=
                            Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                    final StorageReference storageReference =
                            FirebaseStorage.getInstance().getReference().child("Users").child(uid);
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
                                        moduleInformation.Last_Name = user_edit_register_lastName.getText().toString();
                                        moduleInformation.First_Name = user_edit_register_firstName.getText().toString();
                                        moduleInformation.Email = user_edit_register_email.getText().toString();
                                        moduleInformation.password = user_edit_register_password.getText().toString();
                                        moduleInformation.Age = user_edit_register_age.getText().toString();
                                        moduleInformation.Address_1 = user_edit_register_line1.getText().toString();
                                        moduleInformation.Address_2 = user_edit_register_line2.getText().toString();
                                        moduleInformation.Address_3 = user_edit_register_line3.getText().toString();
                                        moduleInformation.uid=uid;

                                        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).setValue(ModuleInformation);
                                    }
                                });
                            } else {
                                Toast.makeText(UserRegister.this, "there is an error please fill all the boxes", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{

                    Toast.makeText(UserRegister.this,"there is an error please fill all the boxes",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            imageView_user.setImageURI(data.getData());
            imageUri = data.getData();
        }


    }
}