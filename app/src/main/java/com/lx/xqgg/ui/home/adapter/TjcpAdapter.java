package com.lx.xqgg.ui.home.adapter;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.product.bean.ProductBean;

import java.util.List;

import androidx.annotation.Nullable;

public class TjcpAdapter extends BaseQuickAdapter<ProductBean.RecordsBean, BaseViewHolder> {
    public TjcpAdapter(@Nullable List<ProductBean.RecordsBean> data) {
        super(R.layout.item_tjcp, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean.RecordsBean item) {
        Log.d("convert-------", "convert: "+SharedPrefManager.getImitationexamination());
        String  ed= SharedPrefManager.getImitationexamination().getPro_bal();
        String  fee= SharedPrefManager.getImitationexamination().getPro_fee();
        String  wan= SharedPrefManager.getImitationexamination().getPro_wan();
        helper.setText(R.id.tv_name, item.getTitle() + "");
        helper.setText(R.id.ed, ed);
        Glide.with(mContext)
                .load(Config.IMGURL + item.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                .into((ImageView) helper.getView(R.id.iv_icon));
        helper.setText(R.id.tv_num, item.getQuota()/10000 + "");
        helper.setText(R.id.cppe, wan);

        helper.setText(R.id.tv_rlv, "日"+fee+item.getRate() + "");
    }
}
