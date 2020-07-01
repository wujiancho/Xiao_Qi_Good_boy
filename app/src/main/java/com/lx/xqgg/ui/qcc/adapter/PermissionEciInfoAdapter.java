package com.lx.xqgg.ui.qcc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.product.bean.QccBean;

import java.util.List;

import androidx.annotation.Nullable;

public class PermissionEciInfoAdapter extends BaseQuickAdapter<QccBean.ResultBean.PenaltyBean, BaseViewHolder> {
    public PermissionEciInfoAdapter(@Nullable List<QccBean.ResultBean.PenaltyBean> data) {
        super(R.layout.item_permisson_ecilnfo, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QccBean.ResultBean.PenaltyBean item) {
        helper.setText(R.id.tv_order_num,item.getDocNo()+"");
        helper.setText(R.id.tv_type,item.getPenaltyType()+"");
//        helper.setText(R.id.tv_cfjd,item.)
        if(item.getPenaltyDate()!=null) {
            helper.setText(R.id.tv_cfjd_time, item.getPenaltyDate().replace("00:00:00", "").trim());
        }
    }
}
