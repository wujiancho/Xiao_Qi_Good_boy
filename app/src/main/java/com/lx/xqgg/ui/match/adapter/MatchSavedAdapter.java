package com.lx.xqgg.ui.match.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.ui.match.bean.MatchSavedBean;
import com.lx.xqgg.ui.product.ProductDetailActivity;

import java.util.List;

import androidx.annotation.Nullable;

public class MatchSavedAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {


    public MatchSavedAdapter(@Nullable List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.item_match_one);
        addItemType(1, R.layout.item_match_result);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (item.getItemType()) {
            case 0:
                View view = helper.getView(R.id.view);
                final MatchSavedBean oneBean = (MatchSavedBean) item;
                if (oneBean.isExpanded()) {
                    view.setRotation(0);
                } else {
                    view.setRotation(270);
                }
                helper.setText(R.id.tv_company_name, (oneBean.getCompany() + "(" + oneBean.getLink_man() + ")"));
                helper.setText(R.id.tv_time, oneBean.getCreatetime() + "");
                helper.setText(R.id.tv_num, oneBean.getProduct().size() + "条");
                helper.setOnClickListener(R.id.layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (oneBean.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case 1:
                final MatchSavedBean.ProductBean productBean = (MatchSavedBean.ProductBean) item;
                String[] titles = productBean.getTitle().split("-");
                helper.setText(R.id.tv_bank_name, titles[0].trim() + "");
                if (titles.length > 1) {
                    helper.setText(R.id.textView2, titles[1].trim() + "");
                } else {
                    helper.setText(R.id.textView2, "");
                }
//                helper.setText(R.id.tv_bank_name, productBean.getTitle() + "");
                helper.setText(R.id.tv_ed, productBean.getQuota() + "");
                helper.setText(R.id.tv_rlv, productBean.getRate() + "");
                Glide.with(mContext)
                        .load(Config.IMGURL + productBean.getImage())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into((ImageView) helper.getView(R.id.image_view));
                helper.setOnClickListener(R.id.layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ProductDetailActivity.class);
                        intent.putExtra("data", productBean.getId());
                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }
}
