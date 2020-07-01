package com.lx.xqgg.ui.qcc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.product.bean.QccBean;

import java.util.List;

import androidx.annotation.Nullable;

public class GqczAdapter extends BaseQuickAdapter<QccBean.ResultBean.PledgeBean, BaseViewHolder> {
    public GqczAdapter(@Nullable List<QccBean.ResultBean.PledgeBean> data) {
        super(R.layout.item_gqcz, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QccBean.ResultBean.PledgeBean item) {
        helper.setText(R.id.tv_czr,item.getPledgor()+"");
        helper.setText(R.id.tv_zqr,item.getPledgee()+"");
        helper.setText(R.id.tv_czgqse,item.getPledgedAmount()+"");
        if(item.getPublicDate()!=null) {
            helper.setText(R.id.tv_dj_time, item.getPublicDate().replace("00:00:00", "").trim());
        }
        helper.setText(R.id.tv_status,item.getStatus()+"");
    }
}
