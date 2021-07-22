package com.example.skinmanagementapp.article;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.skinmanagementapp.R;

public class ArticleDetails extends AppCompatActivity {

    TextView mEtTitle;
    TextView mEtContent;
    TextView mEtAddress;
    private ImageView iv_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ArticleInformation event = (ArticleInformation) getIntent().getSerializableExtra("data");
        mEtTitle = findViewById(R.id.et_title);
        mEtAddress = findViewById(R.id.et_address);
        mEtContent = findViewById(R.id.et_content);
        iv_img = findViewById(R.id.iv_img);
        mEtTitle.setText(event.getTitle());
        mEtContent.setText(event.getContent());
        mEtAddress.setText("Tips:"+event.getAddress());
        if (!TextUtils.isEmpty(event.getImg())){
            Glide.with(this).load(event.getImg()).into(iv_img);
        }
    }
}
