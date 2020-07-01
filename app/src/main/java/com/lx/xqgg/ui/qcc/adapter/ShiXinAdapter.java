package com.lx.xqgg.ui.qcc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.product.bean.QccBean;

import java.util.List;

import androidx.annotation.Nullable;

public class ShiXinAdapter extends BaseQuickAdapter<QccBean.ResultBean.ShiXin, BaseViewHolder> {
    public ShiXinAdapter(@Nullable List<QccBean.ResultBean.ShiXin> data) {
        super(R.layout.item_shixin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QccBean.ResultBean.ShiXin item) {
        helper.setText(R.id.tv_ah, item.getOrgno() + "");
        helper.setText(R.id.tv_cxfy, item.getExecutegov() + "");
        helper.setText(R.id.tv_lxqk, item.getExecutestatus() + "");
        helper.setText(R.id.tv_larq, item.getLiandate() + "");
        helper.setText(R.id.tv_fbrq, item.getPublicdate() + "");
    }
}
