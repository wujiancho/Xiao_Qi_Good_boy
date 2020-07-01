package com.lx.xqgg.face_ui.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.face_ui.home.bean.TopListBean;

import java.util.List;

import androidx.annotation.Nullable;

public class TopListAdapter extends BaseQuickAdapter<TopListBean, BaseViewHolder> {
    public TopListAdapter(@Nullable List<TopListBean> data) {
        super(R.layout.item_top_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopListBean item) {
        helper.setText(R.id.tv_title,item.getName());
        Glide.with(mContext)
                .load(item.getImg())
                .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                .into((ImageView) helper.getView(R.id.imageView));
    }
}
