package com.example.skinmanagementapp.test;

import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skinmanagementapp.R;
import com.example.skinmanagementapp.User.UserHomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class test_main extends AppCompatActivity {

    TextView tv_test1,tv_test2,tv_test3,tv_test4;
    Button btn_test1,btn_test2,btn_test3,btn_test4,test_back;

    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String UserID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);

        tv_test1 = findViewById(R.id.tv_test1);
        tv_test2 = findViewById(R.id.tv_test2);
        tv_test3 = findViewById(R.id.tv_test3);
        tv_test4 = findViewById(R.id.tv_test4);
        btn_test1 = findViewById(R.id.btn_test1);
        btn_test2 = findViewById(R.id.btn_test2);
        btn_test3 = findViewById(R.id.btn_test3);
        btn_test4 = findViewById(R.id.btn_test4);
        test_back = findViewById(R.id.test_back);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        UserID = user.getUid();



        test_jump();
        upload_result();
        back();




    }

    private void back() {
        test_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent = intent.setClass(test_main.this, UserHomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void upload_result() {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(UserID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Object result1 = dataSnapshot.child("result1").getValue();

                if(result1!= null) {
                    tv_test1.setText(result1.toString());
                }

                Object result2 = dataSnapshot.child("result2").getValue();
                if(result2 != null) {
                    tv_test2.setText(result2.toString());
                }

                Object result3 = dataSnapshot.child("result3").getValue();

                if(result3!= null) {
                    tv_test3.setText(result3.toString());
                }
                Object result4 = dataSnapshot.child("result4").getValue();

                if(result4!= null) {
                    tv_test4.setText(result4.toString());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void test_jump() {
        btn_test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent = intent.setClass(test_main.this,test.class);
                startActivity(intent);
            }
        });

        btn_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2 = intent2.setClass(test_main.this,test2.class);
                startActivity(intent2);
            }
        });

        btn_test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent();
                intent3 = intent3.setClass(test_main.this,test3.class);
                startActivity(intent3);
            }
        });

        btn_test4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent();
                intent4 = intent4.setClass(test_main.this,test4.class);
                startActivity(intent4);
            }
        });




    }
}
