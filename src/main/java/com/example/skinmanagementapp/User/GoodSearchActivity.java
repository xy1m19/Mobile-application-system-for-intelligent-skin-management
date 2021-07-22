package com.example.skinmanagementapp.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.skinmanagementapp.R;
import com.example.skinmanagementapp.adapter.GoodListAdapter;
import com.example.skinmanagementapp.module.GoodItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodSearchActivity extends AppCompatActivity {
    @BindView(R.id.rvGood)
    RecyclerView rvGood;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    private List<GoodItem> goodList = new ArrayList<>();
    private GoodListAdapter goodAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsearch);
        ButterKnife.bind(this);
        findViewById(R.id.nav_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rvGood.setLayoutManager(new LinearLayoutManager(this));
        goodAdapter = new GoodListAdapter(this, R.layout.item_good, goodList);
        rvGood.setAdapter(goodAdapter);
        goodAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(GoodSearchActivity.this, GoodDetailActivity.class);
                intent.putExtra("data", goodList.get(position));

                startActivity(intent);
            }
        });
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH) {

                   String key = edtSearch.getText().toString();
                   getGoods( key);
                }
                return false;
            }
        });
    }


    private void getGoods(String key) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Good");
        databaseReference.orderByChild("name").startAt(key).addValueEventListener(new ValueEventListener() {
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
