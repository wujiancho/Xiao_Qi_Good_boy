package com.lx.xqgg.face_ui.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.face_ui.home.bean.FaceProductBean;

import java.util.List;

import androidx.annotation.Nullable;

public class FaceProductAdapter extends BaseQuickAdapter<FaceProductBean, BaseViewHolder> {
    public FaceProductAdapter(@Nullable List<FaceProductBean> data) {
        super(R.layout.item_face_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FaceProductBean item) {
        Glide.with(mContext)
                .load(Config.IMGURL + item.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                .into((ImageView) helper.getView(R.id.imageView));
        helper.setText(R.id.tv_title, item.getTitle());
    }
}
