package com.lx.xqgg.ui.qcc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.product.bean.QccBean;

import java.util.List;

import androidx.annotation.Nullable;

public class PenaltyChinaAdapter extends BaseQuickAdapter<QccBean.ResultBean.PenaltyChina, BaseViewHolder> {
    public PenaltyChinaAdapter(@Nullable List<QccBean.ResultBean.PenaltyChina> data) {
        super(R.layout.item_penalty_china, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QccBean.ResultBean.PenaltyChina item) {
        helper.setText(R.id.tv_order_num, item.getCaseNo() + "");
        helper.setText(R.id.tv_cfjg, item.getName() + "");
        helper.setText(R.id.tv_cfjd_time, item.getLiandate() + "");
    }
}
