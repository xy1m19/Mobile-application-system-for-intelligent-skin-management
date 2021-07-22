package com.example.skinmanagementapp.User;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.skinmanagementapp.MyGlideAppApply;
import com.example.skinmanagementapp.R;
import com.example.skinmanagementapp.WebViewActivity;
import com.example.skinmanagementapp.module.GoodItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class banner_1 extends AppCompatActivity {

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
    TextView tv_pigment_name;
    @BindView(R.id.tv_skin_name)
    TextView tv_skin_name;

    FirebaseDatabase mData;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    String UserID;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_good);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();
        UserID = user.getUid();


        upload();


        /*reference = mData.getReference().child("Good").child("-MFZbolOrEK10AiKkqgX").child("logo");
        Glide.with(this)
                .load(reference)
                .into(banner);*/
        /*Bitmap bitmap = getHttpBitmap("https://www.origins.com/media/export/cms/products/1000x1000/origins_sku_0T7F01_1000x1000_0.jpg");
        banner.setImageBitmap(bitmap); //设置Bitmap*/

    }

    /*public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            assert myFileUrl != null;
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;


    }*/





    private void upload() {

        reference = FirebaseDatabase.getInstance().getReference().child("Good").child("-MFZbolOrEK10AiKkqgX");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String price = Objects.requireNonNull(dataSnapshot.child("price").getValue()).toString();
                String name = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                String skin = Objects.requireNonNull(dataSnapshot.child("skin").getValue()).toString();
                String age = Objects.requireNonNull(dataSnapshot.child("age").getValue()).toString();
                String pigment = Objects.requireNonNull(dataSnapshot.child("pigment").getValue()).toString();
                String type = Objects.requireNonNull(dataSnapshot.child("type").getValue()).toString();
                String des = Objects.requireNonNull(dataSnapshot.child("des").getValue()).toString();




                tvPrice.setText(price);
                tvName.setText(name);
                tv_type_name.setText(type);
                tv_skin_name.setText(skin);
                tv_age_name.setText(age);
                tv_pigment_name.setText(pigment);
                wvDetail.setText(des);
                btBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(banner_1.this, binner_1_web.class);
                        intent.putExtra("data",goodItem);

                        startActivity(intent);
                    }
                });




            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //onSetTitle(goodItem.getName());
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
