package com.example.skinmanagementapp.article;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.skinmanagementapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ArticleMainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwRefresh;
    private List<ArticleInformation> list = new ArrayList<>();
    private ArticleAdapter mAdapter;
    private DatabaseReference reff;
    private ProgressDialog mProgressDialog;
    private TextView tvAdd;
    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);
        mProgressDialog =  new ProgressDialog(this);
        isAdmin = getIntent().getBooleanExtra("isAdmin",false);
        initView();
        init();
    }

    private void initView() {
        tvAdd = findViewById(R.id.tv_add);
        if (isAdmin){
            tvAdd.setVisibility(View.VISIBLE);
        }else{
            tvAdd.setVisibility(View.GONE);
        }
        mRecyclerView = findViewById(R.id.recyclerView);
        mSwRefresh = findViewById(R.id.sw_refresh);
        findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvAdd .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArticleMainActivity.this, AddArticleActivity.class));
            }
        });

        mSwRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ArticleAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ArticleMainActivity.this, ArticleDetails.class);
                intent.putExtra("data", list.get(position));
                startActivity(intent);
            }
        });
    }




    private void init() {
        reff = FirebaseDatabase.getInstance().getReference().child("Article");
        mProgressDialog.show();
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mProgressDialog.dismiss();
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ArticleInformation event = snapshot.getValue(ArticleInformation.class);
                    list.add(event);

                }
                mAdapter.notifyDataSetChanged();
                mSwRefresh.setRefreshing(false);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mProgressDialog.dismiss();
            }
        });
    }
    }
