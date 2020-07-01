package com.lx.xqgg.ui.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.home.bean.MatterBean;

import java.util.List;

import androidx.annotation.Nullable;

public class HomeBaseAdapter extends BaseQuickAdapter<MatterBean,BaseViewHolder> {

    public HomeBaseAdapter(int layoutResId, @Nullable List<MatterBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatterBean item) {
        helper.setText(R.id.tv_name,item.getName()+"");
        Glide.with(mContext)
                .load(item.getImgRes())
                .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                .into((ImageView) helper.getView(R.id.iv_icon));
    }
}
