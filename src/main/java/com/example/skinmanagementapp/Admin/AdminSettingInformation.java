package com.example.skinmanagementapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.skinmanagementapp.MainActivity;
import com.example.skinmanagementapp.R;
import com.example.skinmanagementapp.article.ArticleMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class AdminSettingInformation extends AppCompatActivity {

    ImageView imageView_Admin;
    Button admin_update, admin_delete,user_article;
    EditText admin_edit_register_lastName, admin_edit_register_firstName,
            admin_edit_register_email, admin_edit_register_password,
            admin_edit_register_conform_password;
    Uri imageUri;

    FirebaseDatabase mData;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    String UserID;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_setting_information);

        imageView_Admin = findViewById(R.id.imageView_Admin);
        admin_update = findViewById(R.id.admin_update);
        admin_delete = findViewById(R.id.admin_delete);
        user_article = findViewById(R.id.user_article);
        admin_edit_register_lastName = findViewById(R.id.admin_edit_register_lastName);
        admin_edit_register_firstName = findViewById(R.id.admin_edit_register_firstName);
        admin_edit_register_email = findViewById(R.id.admin_edit_register_email);
        admin_edit_register_password = findViewById(R.id.admin_edit_register_password);
        admin_edit_register_conform_password = findViewById(R.id.admin_edit_register_conform_password);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();
        UserID = user.getUid();

        Delete();
        displayInformation();
        jumpArticle();


        storageReference = storage.getReferenceFromUrl("gs://skin-management.appspot.com/").child("Admins").child(UserID);
        Glide.with(this)
                .load(storageReference)
                .into(imageView_Admin);



    }

    private void jumpArticle() {

        user_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent = intent.setClass(AdminSettingInformation.this, ArticleMainActivity.class);
                intent.putExtra("isAdmin",true);
                startActivity(intent);

            }
        });
    }

    private void displayInformation() {
        reference = FirebaseDatabase.getInstance().getReference().child("Admin").child(UserID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String firstName = Objects.requireNonNull(dataSnapshot.child("first_Name").getValue()).toString();
                String LastName = Objects.requireNonNull(dataSnapshot.child("last_Name").getValue()).toString();
                String password = Objects.requireNonNull(dataSnapshot.child("password").getValue()).toString();
                String conform = Objects.requireNonNull(dataSnapshot.child("conform").getValue()).toString();
                String email = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();



                admin_edit_register_firstName.setText(firstName);
                admin_edit_register_lastName.setText(LastName);
                admin_edit_register_password.setText(password);
                admin_edit_register_conform_password.setText(conform);
                admin_edit_register_email.setText(email);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void Delete() {
        admin_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue(null);
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            private static final String TAG = "delete";

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User account deleted.");
                                }
                            }
                        });
                storageReference.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            private static final String TAG = "delete";

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User account deleted.");
                                }
                            }
                        });

                Intent intent = new Intent(AdminSettingInformation.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void UpdateInformation(View view) {

        if (isLastNameChanged()) {
            Toast.makeText(AdminSettingInformation.this, "Information Update", Toast.LENGTH_SHORT).show();

        }
        if (isFirstNameChanged()) {
            Toast.makeText(AdminSettingInformation.this, "Information Update", Toast.LENGTH_SHORT).show();

        }


    }

    private boolean isFirstNameChanged() {

        if(! admin_edit_register_firstName.equals(admin_edit_register_firstName.getText().toString())){
            reference.child(UserID).child("first_name").setValue(admin_edit_register_firstName.getEditableText().toString());
            return true;
        }else {
            return false;
        }

    }

    private boolean isLastNameChanged() {

        if(! admin_edit_register_lastName.equals(admin_edit_register_lastName.getText().toString())){
            reference.child(UserID).child("last_name").setValue(admin_edit_register_lastName.getEditableText().toString());
            return true;
        }else {
            return false;
        }
    }

}
