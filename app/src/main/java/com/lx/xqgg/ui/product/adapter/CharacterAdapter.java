package com.lx.xqgg.ui.product.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.product.bean.ProductBean;

import java.util.List;

import androidx.annotation.Nullable;

public class CharacterAdapter extends BaseQuickAdapter<ProductBean.RecordsBean.ClassifyListBean, BaseViewHolder> {
    public CharacterAdapter(@Nullable List<ProductBean.RecordsBean.ClassifyListBean> data) {
        super(R.layout.item_character, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean.RecordsBean.ClassifyListBean item) {
        int drawableId = 0;
        switch (helper.getLayoutPosition()) {
            case 0:
                drawableId=R.drawable.ic_td_1;
                break;
            case 1:
                drawableId=R.drawable.ic_td_2;
                break;
            case 2:
                drawableId=R.drawable.ic_td_3;
                break;
        }
        helper.setText(R.id.tv_name, item.getName());
        Glide.with(mContext)
                .load(drawableId)
                .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                .into((ImageView) helper.getView(R.id.image_view));
    }
}
