package com.example.skinmanagementapp.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.example.skinmanagementapp.R;
import com.example.skinmanagementapp.WebViewActivity;
import com.example.skinmanagementapp.module.GoodItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodDetailActivity extends AppCompatActivity {
    GoodItem goodItem;
    @BindView(R.id.banner)
    ImageView banner;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.wv_detail)
    TextView wvDetail;
    @BindView(R.id.bt_buy)
    Button btBuy;
    @BindView(R.id.tv_type_name)
    TextView tv_type_name;
    @BindView(R.id.tv_age_name)
    TextView tv_age_name;
    @BindView(R.id.tv_pigment_name)
     TextView  tv_pigment_name;
    @BindView(R.id.tv_skin_name)
     TextView  tv_skin_name;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_good);
        ButterKnife.bind(this);
        goodItem = (GoodItem) getIntent().getSerializableExtra("data");
        wvDetail.setText(goodItem.des);

        Glide.with(this).load(goodItem.getLogo()).into(banner);
        tvPrice.setText("$"+goodItem.getPrice());
        tvName.setText(goodItem.getName());
        tv_type_name.setText(goodItem.getType());
        tv_age_name.setText(goodItem.getAge());
        tv_pigment_name.setText(goodItem.getPigment());
        tv_skin_name.setText(goodItem.getSkin());
        btBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoodDetailActivity.this, WebViewActivity.class);
                intent.putExtra("data",goodItem);

                startActivity(intent);
            }
        });
        onSetTitle(goodItem.getName());
    }
    public void onSetTitle(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar == null) {
            return;
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView mTitle = toolbar.findViewById(R.id.tv_title);
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }
}
