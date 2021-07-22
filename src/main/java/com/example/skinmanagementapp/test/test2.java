package com.example.skinmanagementapp.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skinmanagementapp.R;
import com.example.skinmanagementapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class test2 extends AppCompatActivity {


    Button test_submit,test_back;
    ScrollView vertical_scroll_view;
    RadioGroup select1_1,select1_2,select1_3,select1_4,select1_5;
    RadioButton select1_1_1,select1_1_2,select1_1_3,select1_1_4,
            select1_2_1,select1_2_2,select1_2_3,select1_2_4,
            select1_3_1,select1_3_2,select1_3_3,select1_3_4,
            select1_4_1,select1_4_2,select1_4_3,select1_4_4,
            select1_5_1,select1_5_2,select1_5_3,select1_5_4;

    int num1,num2,num3,num4,num5,sum;

    private DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String UserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);

        test_submit = findViewById(R.id.test2_submit);
        test_back = findViewById(R.id.test2_back);
        vertical_scroll_view = findViewById(R.id.vertical_scroll_view);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        UserID = user.getUid();





        select1_1 = findViewById(R.id.select1_1);
        select1_2 = findViewById(R.id.select1_2);
        select1_3 = findViewById(R.id.select1_3);
        select1_4 = findViewById(R.id.select1_4);
        select1_5 = findViewById(R.id.select1_5);


        select1_1_1 = findViewById(R.id.select1_1_1);
        select1_1_2 = findViewById(R.id.select1_1_2);
        select1_1_3 = findViewById(R.id.select1_1_3);
        select1_1_4 = findViewById(R.id.select1_1_4);
        select1_2_1 = findViewById(R.id.select1_2_1);
        select1_2_2 = findViewById(R.id.select1_2_2);
        select1_2_3 = findViewById(R.id.select1_2_3);
        select1_2_4 = findViewById(R.id.select1_2_4);
        select1_3_1 = findViewById(R.id.select1_3_1);
        select1_3_2 = findViewById(R.id.select1_3_2);
        select1_3_3 = findViewById(R.id.select1_3_3);
        select1_3_4 = findViewById(R.id.select1_3_4);
        select1_4_1 = findViewById(R.id.select1_4_1);
        select1_4_2 = findViewById(R.id.select1_4_2);
        select1_4_3 = findViewById(R.id.select1_4_3);
        select1_4_4 = findViewById(R.id.select1_4_4);
        select1_5_1 = findViewById(R.id.select1_5_1);
        select1_5_2 = findViewById(R.id.select1_5_2);
        select1_5_3 = findViewById(R.id.select1_5_3);
        select1_5_4 = findViewById(R.id.select1_5_4);


        select1_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radBtn = findViewById(checkedId);
                radBtn.getText().toString();
                Log.i("RadioButton", radBtn.getText().toString());
            }
        });
        select1_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radBtn = findViewById(checkedId);
                radBtn.getText().toString();
                Log.i("RadioButton", radBtn.getText().toString());
            }
        });
        select1_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radBtn = findViewById(checkedId);
                radBtn.getText().toString();
                Log.i("RadioButton", radBtn.getText().toString());
            }
        });
        select1_4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radBtn = findViewById(checkedId);
                radBtn.getText().toString();
                Log.i("RadioButton", radBtn.getText().toString());
            }
        });

        select1_5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radBtn = findViewById(checkedId);
                radBtn.getText().toString();
                Log.i("RadioButton", radBtn.getText().toString());
            }
        });

        test_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int optionID1 = select1_1.getCheckedRadioButtonId();
                int optionID2 = select1_2.getCheckedRadioButtonId();
                int optionID3 = select1_3.getCheckedRadioButtonId();
                int optionID4 = select1_4.getCheckedRadioButtonId();
                int optionID5 = select1_5.getCheckedRadioButtonId();

                resultDialog();

                /*if (optionID1 == R.id.select1_1_1 || optionID1==R.id.select1_1_2 & optionID2==R.id.select1_2_1 || optionID2==R.id.select1_2_2 &
                        optionID3==R.id.select1_3_1 || optionID3==R.id.select1_3_2 & optionID4==R.id.select1_4_1 || optionID3==R.id.select1_4_2 & optionID5==R.id.select1_5_1 || optionID3==R.id.select1_5_2) {
                    result1.setText("you are a oil");
                } else if (optionID1 == R.id.select1_1_3 || optionID1==R.id.select1_1_4 & optionID2==R.id.select1_2_3 || optionID2==R.id.select1_2_4 &
                        optionID3==R.id.select1_3_1 || optionID3==R.id.select1_3_2 & optionID4==R.id.select1_4_1 || optionID3==R.id.select1_4_2 & optionID5==R.id.select1_5_1 || optionID3==R.id.select1_5_2) {
                    result1.setText("you are a oil");
                }else if (optionID1 == R.id.select1_1_3 || optionID1==R.id.select1_1_4 & optionID2==R.id.select1_2_3 || optionID2==R.id.select1_2_4 &
                        optionID3==R.id.select1_3_3 || optionID3==R.id.select1_3_4 & optionID4==R.id.select1_4_1 || optionID3==R.id.select1_4_2 & optionID5==R.id.select1_5_1 || optionID3==R.id.select1_5_2){
                    result1.setText("dry");
                }else if (optionID1 == R.id.select1_1_3 || optionID1==R.id.select1_1_4 & optionID2==R.id.select1_2_3 || optionID2==R.id.select1_2_4 &
                        optionID3==R.id.select1_3_3 || optionID3==R.id.select1_3_4 & optionID4==R.id.select1_4_3 || optionID3==R.id.select1_4_4 & optionID5==R.id.select1_5_1 || optionID3==R.id.select1_5_2){
                    result1.setText("dry");
                }else if (optionID1==-1||optionID2==-1||optionID3==-1||optionID4==-1 ){
                    Toast.makeText(test.this, "请作出选择", Toast.LENGTH_SHORT).show();
                }else{
                    result1.setText("分数：0分");
                }*/



                if (optionID1 == R.id.select1_1_1 || optionID1==R.id.select1_1_2 ) {
                    num1 = 20;
                }else if(optionID1 == R.id.select1_1_3 || optionID1==R.id.select1_1_4){
                    num1 = 0;
                }else{}

                if (optionID2 == R.id.select1_2_1 || optionID1==R.id.select1_2_2 ) {
                    num2 = 20;
                }else if(optionID2 == R.id.select1_2_3 || optionID1==R.id.select1_2_4 ){
                    num2 = 0;
                }else{}

                if (optionID3 == R.id.select1_3_1 || optionID1==R.id.select1_3_2 ) {
                    num3 = 20;
                }else if(optionID3 == R.id.select1_3_3 || optionID1==R.id.select1_3_4){
                    num3 = 0;

                }else{}

                if (optionID4 == R.id.select1_4_1 || optionID1==R.id.select1_4_2) {
                    num4 = 20;
                }else if(optionID4 == R.id.select1_4_3 || optionID1==R.id.select1_4_4){
                    num4 = 0;

                }
                if (optionID5 == R.id.select1_5_1 || optionID1==R.id.select1_5_2 ) {
                    num5 = 20;
                }else if(optionID5 == R.id.select1_5_3 || optionID1==R.id.select1_5_4 ){
                    num5 = 0;

                }else{}

            }

        });

    }

    private void resultDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("RESULT:");


        LinearLayout linearLayout = new LinearLayout(this);

        final TextView result = new TextView(this);
        result.setHint("Result");
        result.setTextSize(20);


        linearLayout.addView(result);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                databaseReference.child("Users").child(UserID).child("type").setValue(skinResult);


                finish();


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });

        builder.create().show();

        sum = num1 + num2 + num3 +num4 + num5;
        if (sum > 50) {
            skinResult="Tolerance";
            result.setText("Your skin is Tolerance");
            linearLayout.setPadding(10,10,10,10);


        }else if(sum < 50){
            skinResult="Sensitivity";
            result.setText("Your skin is Tolerance");
            linearLayout.setPadding(10,10,10,10);

        }else {}


    }
    String skinResult="";
}
