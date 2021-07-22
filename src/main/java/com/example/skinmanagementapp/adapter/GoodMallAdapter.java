package com.example.skinmanagementapp.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.skinmanagementapp.R;
import com.example.skinmanagementapp.module.Category;
import com.luck.picture.lib.tools.ScreenUtils;

import java.util.List;

public class GoodMallAdapter extends BaseQuickAdapter<Category, BaseViewHolder> {
    private Context context;
    public GoodMallAdapter(Context context , int layoutResId, @Nullable List<Category> data) {
        super(layoutResId, data);
        this.context =context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Category item) {
        ViewGroup.LayoutParams layoutParams = helper.getView(R.id.ll_rootview).getLayoutParams();
        layoutParams.width = (ScreenUtils.getScreenWidth(context) - ScreenUtils.dip2px(context,12)) / 4;
        helper.getView(R.id.ll_rootview).setLayoutParams(layoutParams);
        helper.setText(R.id.tv_name, item.name);
        helper.setImageResource(R.id.iv_logo,item.logo);

    }
}
