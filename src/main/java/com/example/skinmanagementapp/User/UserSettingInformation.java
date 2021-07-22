package com.example.skinmanagementapp.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.skinmanagementapp.MainActivity;
import com.example.skinmanagementapp.R;
import com.example.skinmanagementapp.article.ArticleMainActivity;
import com.example.skinmanagementapp.test.test_main;
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

public class UserSettingInformation extends AppCompatActivity {

    ImageView imageView_user;
    Button user_update,user_delete,user_test,user_back;
    EditText user_edit_register_lastName,
            user_edit_register_firstName,user_edit_register_email,
            user_edit_register_password,user_edit_register_age,user_edit_register_line1,
            user_edit_register_line2,user_edit_register_line3;


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
        setContentView(R.layout.activity_user_setting_information);


        imageView_user=findViewById(R.id.imageView_user);
        user_update=findViewById(R.id.user_update);
        user_delete=findViewById(R.id.user_delete);
        user_back = findViewById(R.id.user_back);
        user_edit_register_lastName=findViewById(R.id.user_edit_register_lastName);
        user_edit_register_firstName=findViewById(R.id.user_edit_register_firstName);
        user_edit_register_email=findViewById(R.id.user_edit_register_email);
        user_edit_register_password=findViewById(R.id.user_edit_register_password);
        user_edit_register_age = findViewById(R.id.user_edit_register_age);
        user_edit_register_line1 = findViewById(R.id.user_edit_register_line1);
        user_edit_register_line2 = findViewById(R.id.user_edit_register_line2);
        user_edit_register_line3 = findViewById(R.id.user_edit_register_line3);


        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();
        UserID = user.getUid();


        Delete();
        ChangeInformation();
        back();

        storageReference = storage.getReferenceFromUrl("gs://skin-management.appspot.com/").child("Users").child(UserID);
        Glide.with(this)
                .load(storageReference)
                .into(imageView_user);






    }

    private void back() {
        user_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent = intent.setClass(UserSettingInformation.this, UserHomeActivity.class);
                startActivity(intent);

            }
        });
    }



    private void ChangeInformation() {

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(UserID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String firstName = Objects.requireNonNull(dataSnapshot.child("first_Name").getValue()).toString();
                String LastName = Objects.requireNonNull(dataSnapshot.child("last_Name").getValue()).toString();
                String password = Objects.requireNonNull(dataSnapshot.child("password").getValue()).toString();
                String age = Objects.requireNonNull(dataSnapshot.child("age").getValue()).toString();
                String email = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                String address_1 = Objects.requireNonNull(dataSnapshot.child("address_1").getValue()).toString();
                String address_2 = Objects.requireNonNull(dataSnapshot.child("address_2").getValue()).toString();
                String address_3 = Objects.requireNonNull(dataSnapshot.child("address_3").getValue()).toString();




                user_edit_register_firstName.setText(firstName);
                user_edit_register_lastName.setText(LastName);
                user_edit_register_password.setText(password);
                user_edit_register_age.setText(age);
                user_edit_register_email.setText(email);
                user_edit_register_line1.setText(address_1);
                user_edit_register_line2.setText(address_2);
                user_edit_register_line3.setText(address_3);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    //用户删除个人信息及资料
    private void Delete() {
        user_delete.setOnClickListener(new View.OnClickListener() { //OnClickListener为按钮设置间听事件，及按钮实现的功能
            @Override
            public void onClick(View v) {
                reference.removeValue(null); //reference删除用户，重要，remove与firebase连接
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

                Intent intent = new Intent(UserSettingInformation.this, MainActivity.class);
                startActivity(intent);
            }
        });




    }

    public void UpdateInformation(View view) {  //更新现有用户的现有信息

        if (isLastNameChanged()) {
            Toast.makeText(UserSettingInformation.this, "Information Update", Toast.LENGTH_SHORT).show();

        }
        if (isFirstNameChanged()) {
            Toast.makeText(UserSettingInformation.this, "Information Update", Toast.LENGTH_SHORT).show();

        }

        if (isAgeChanged()) {
            Toast.makeText(UserSettingInformation.this, "Information Update", Toast.LENGTH_SHORT).show();

        }

        if (isAddress_1Changed()) {
            Toast.makeText(UserSettingInformation.this, "Information Update", Toast.LENGTH_SHORT).show();

        }
        if (isAddress_2Changed()) {
            Toast.makeText(UserSettingInformation.this, "Information Update", Toast.LENGTH_SHORT).show();

        }
        if (isAddress_3Changed()) {
            Toast.makeText(UserSettingInformation.this, "Information Update", Toast.LENGTH_SHORT).show();

        }

        }


    private boolean isAddress_3Changed() {
        if(! user_edit_register_line3.equals(user_edit_register_line3.getText().toString())){
            reference.child(UserID).child("address_3").setValue(user_edit_register_line3.getEditableText().toString());
            return true;
        }else {
            return false;
        }
    }

    private boolean isAddress_2Changed() {
        if(! user_edit_register_line2.equals(user_edit_register_line2.getText().toString())){
            reference.child(UserID).child("address_2").setValue(user_edit_register_line2.getEditableText().toString());
            return true;
        }else {
            return false;
        }
    }

    private boolean isAddress_1Changed() {
        if(! user_edit_register_line1.equals(user_edit_register_line1.getText().toString())){
            reference.child(UserID).child("address_1").setValue(user_edit_register_line1.getEditableText().toString());
            return true;
        }else {
            return false;
        }
    }

    private boolean isAgeChanged() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        if(! user_edit_register_age.equals(user_edit_register_age.getText().toString())){
            reference.child(UserID).child("age").setValue(user_edit_register_age.getEditableText().toString());
            return true;
        }else {
            return false;
        }
    }

    private boolean isFirstNameChanged() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        if(! user_edit_register_firstName.equals(user_edit_register_firstName.getText().toString())){
            reference.child(UserID).child("last_Name").setValue(user_edit_register_firstName.getEditableText().toString());
            return true;
        }else {
            return false;
        }

    }

    private boolean isLastNameChanged() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        if(! user_edit_register_lastName.equals(user_edit_register_lastName.getText().toString())){
            reference.child(UserID).child("first_Name").setValue(user_edit_register_lastName.getEditableText().toString());
            return true;
        }else {
            return false;
        }
    }
}