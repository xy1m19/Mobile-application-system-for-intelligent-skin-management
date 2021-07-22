package com.example.skinmanagementapp.article;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.skinmanagementapp.R;

import java.util.List;

public class ArticleAdapter extends BaseQuickAdapter<ArticleInformation, BaseViewHolder> {

    public ArticleAdapter(List<ArticleInformation> articles) {
        super(R.layout.event_item,articles);
    }


    @Override
    protected void convert(BaseViewHolder helper, ArticleInformation item) {
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_content,item.getContent());
        helper.setText(R.id.mtvTime,item.getTime());
    }

}
