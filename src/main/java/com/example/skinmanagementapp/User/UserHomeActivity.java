package com.example.skinmanagementapp.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.skinmanagementapp.R;
import com.example.skinmanagementapp.adapter.GoodListAdapter;
import com.example.skinmanagementapp.adapter.GoodMallAdapter;
import com.example.skinmanagementapp.article.ArticleMainActivity;
import com.example.skinmanagementapp.module.Category;
import com.example.skinmanagementapp.module.GoodItem;
import com.example.skinmanagementapp.test.test_main;
import com.example.skinmanagementapp.utils.GlideImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserHomeActivity extends AppCompatActivity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.layout_search)
    LinearLayout layoutSearch;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.rv_goodlist)
    RecyclerView rvGoodslist;
    private GoodMallAdapter categoryAdapter;
    private List<GoodItem> goodList = new ArrayList<>();
    private GoodListAdapter goodAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        ButterKnife.bind(this);
        initView();
    }
    public void initView() {

        findViewById(R.id.layout_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHomeActivity.this,GoodSearchActivity.class));
            }
        });

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        final List<String> url = new ArrayList<>();
        url.add("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSm7FL8uB8aEp7urYNChzTGtCazOkMWqrrmcA&usqp=CAU");
        url.add("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQfN5t-hJnK_BqLosl5oamQY_W7ZavUHPGEmg&usqp=CAU");
        url.add("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcThj9qnIZBowGzAOHMjZ-5abqdgt9Q8WM-n2A&usqp=CAU");
        banner.setImages(url);
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position==0){
                    startActivity(new Intent(UserHomeActivity.this, banner_1.class));
                }else if (position==1){
                    startActivity(new Intent(UserHomeActivity.this, GoodListActivity.class));
                }else if (position==2){
                    startActivity(new Intent(UserHomeActivity.this, GoodListActivity.class));
                }
            }
        });








        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvCategory.setLayoutManager(linearLayoutManager);
        ArrayList<Category> secCategories = new ArrayList<>();
        secCategories.add(new Category("Skin Test",R.drawable.icon_my_like));
        secCategories.add(new Category("Reading Article",R.drawable.icon_my_order));
        secCategories.add(new Category("List of Good",R.drawable.icon_my_experience));
        secCategories.add(new Category("Setting",R.drawable.icon_my_like_author));
        categoryAdapter = new GoodMallAdapter(this, R.layout.item_category, secCategories);
        rvCategory.setAdapter(categoryAdapter);


        rvGoodslist.setLayoutManager(new LinearLayoutManager(this));
        goodAdapter = new GoodListAdapter(this, R.layout.item_good, goodList);
        rvGoodslist.setAdapter(goodAdapter);
        categoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position==0){
                    startActivity(new Intent(UserHomeActivity.this, test_main.class));
                }else if (position==1){
                    startActivity(new Intent(UserHomeActivity.this, ArticleMainActivity.class));
                }else if (position==2){
                    startActivity(new Intent(UserHomeActivity.this, GoodListActivity.class));
                }else if (position==3){
                    startActivity(new Intent(UserHomeActivity.this, UserSettingInformation.class));
                }
            }
        });

        goodAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(UserHomeActivity.this,GoodDetailActivity.class);
                intent.putExtra("data",goodList.get(position));

                startActivity(intent);
            }
        });
        initUserInfo();



    }

    private void initUserInfo() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object skin = dataSnapshot.child("skin").getValue();
                Object type = dataSnapshot.child("type").getValue();
                Object pigment = dataSnapshot.child("pigment").getValue();
                Object age = dataSnapshot.child("age").getValue();

                if (skin!=null||type!=null||pigment!=null||age!=null){
                    getGoods(skin,type,pigment,age);
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getGoods(Object skin, Object type,Object pigment,Object age) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Good");
        Query da=null;
        if (skin!=null){
            da = databaseReference.orderByChild("skin").equalTo(skin.toString());
        }
        if (type!=null){
            da = databaseReference.orderByChild("type").equalTo(type.toString());
        }
        if (pigment!=null){
            da = databaseReference.orderByChild("pigment").equalTo(pigment.toString());
        }
        if (age!=null){
            da = databaseReference.orderByChild("age").equalTo(age.toString());
        }
        da.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                goodList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    GoodItem goodItem = snapshot.getValue(GoodItem.class);
                    goodList.add(goodItem);
                }
                goodAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
