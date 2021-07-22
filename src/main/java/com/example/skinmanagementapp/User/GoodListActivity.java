package com.example.skinmanagementapp.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.skinmanagementapp.R;
import com.example.skinmanagementapp.adapter.GoodListAdapter;
import com.example.skinmanagementapp.module.GoodItem;
import com.example.skinmanagementapp.test.test_main;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodListActivity extends AppCompatActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvGood)
    RecyclerView rvGood;
    private List<GoodItem> goodList = new ArrayList<>();
    private GoodListAdapter goodAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodlist);
        ButterKnife.bind(this);
        onSetTitle("List of Good");
        rvGood.setLayoutManager(new LinearLayoutManager(this));
        goodAdapter = new GoodListAdapter(this, R.layout.item_good, goodList);
        rvGood.setAdapter(goodAdapter);
        getGoods();
        goodAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(GoodListActivity.this,GoodDetailActivity.class);
                intent.putExtra("data",goodList.get(position));

                startActivity(intent);
            }
        });
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

    private void getGoods() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Good");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                goodList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
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
