package com.example.skinmanagementapp.article;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.skinmanagementapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddArticleActivity extends AppCompatActivity {

    EditText mEtTitle;
    EditText mEtContent;
    TextView mBtAdd;
    private DatabaseReference databaseReference;
    private EditText mEtAddress;

    public String img;
    private ImageView ivimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        initView();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
    private void getImage() {

        if (ActivityCompat.checkSelfPermission(AddArticleActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddArticleActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            return;
        }
        if (ActivityCompat.checkSelfPermission(AddArticleActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddArticleActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            return;
        }


        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case 10:
                    final Uri uri = data.getData();
                    upload(uri);
                    break;

                default:
                    break;
            }
        }

    }
    private void upload(Uri selectedUri) {
        StorageReference mStoreReference = FirebaseStorage.getInstance().getReference();
        final StorageReference riversRef = mStoreReference.child(System.currentTimeMillis()+".jpg");
        UploadTask uploadTask = riversRef.putFile(selectedUri);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    img= downloadUri.toString();
                    Glide.with(AddArticleActivity.this).load(img).into(ivimg);
                } else {

                }
            }
        });

    }
    private void initView() {
        findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mEtTitle = findViewById(R.id.et_title);
        mEtAddress = findViewById(R.id.et_address);
        ivimg = findViewById(R.id.iv_img);
        mEtContent = findViewById(R.id.et_content);
        mBtAdd = findViewById(R.id.bt_add);
        ivimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });
        mBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mEtTitle.getText().toString();
                String content = mEtContent.getText().toString();
                String address = mEtAddress.getText().toString();
                if (TextUtils.isEmpty(title)){
                    return;
                }
                if (TextUtils.isEmpty(address)){
                    return;
                }
                if (TextUtils.isEmpty(content)){
                    return;
                }
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("title", title);
                hashMap.put("content", content);
                hashMap.put("address", address);
                hashMap.put("time", getTime());
                hashMap.put("img", img);
                databaseReference.child("Article").push().setValue(hashMap);
                finish();
            }
        });
    }

    private String getTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return  df.format(new Date());
    }

}
