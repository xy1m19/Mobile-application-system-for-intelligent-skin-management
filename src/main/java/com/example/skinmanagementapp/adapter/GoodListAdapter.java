package com.example.skinmanagementapp.adapter;

import android.content.Context;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.skinmanagementapp.R;
import com.example.skinmanagementapp.module.GoodItem;

import java.util.List;

public class GoodListAdapter extends BaseQuickAdapter<GoodItem, BaseViewHolder> {
    private Context context;
    public GoodListAdapter(Context context , int layoutResId, @Nullable List<GoodItem> data) {
        super(layoutResId, data);
        this.context =context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GoodItem item) {
        helper.setText(R.id.tv_name, item.getName());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.error_img)
                .error(R.drawable.error_img);
        Glide.with(context).load(item.getLogo()).apply(options).into((ImageView) helper.getView(R.id.iv_logo));
        helper.setText(R.id.tv_price, "$" + item.getPrice());

    }


}
