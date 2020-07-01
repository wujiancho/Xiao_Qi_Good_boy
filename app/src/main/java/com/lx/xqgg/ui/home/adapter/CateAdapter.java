package com.lx.xqgg.ui.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.ui.product.bean.CateBean;

import java.util.List;

import androidx.annotation.Nullable;

public class CateAdapter extends BaseQuickAdapter<CateBean, BaseViewHolder> {
    public CateAdapter(@Nullable List<CateBean> data) {
        super(R.layout.item_home_top, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CateBean item) {
        helper.setText(R.id.tv_name, item.getName()+"");
        if (item.getName().equals("更多")) {
            Glide.with(mContext)
                    .load(R.drawable.ic_more)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                    .into((ImageView) helper.getView(R.id.iv_icon));
        } else {
            Glide.with(mContext)
                    .load(Config.IMGURL + item.getImage())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                    .into((ImageView) helper.getView(R.id.iv_icon));
        }
    }
}
