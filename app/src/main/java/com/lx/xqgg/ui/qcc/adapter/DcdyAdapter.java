package com.lx.xqgg.ui.qcc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.product.bean.QccBean;

import java.util.List;

import androidx.annotation.Nullable;

public class DcdyAdapter extends BaseQuickAdapter<QccBean.ResultBean.DCDY, BaseViewHolder> {
    public DcdyAdapter(@Nullable List<QccBean.ResultBean.DCDY> data) {
        super(R.layout.item_dcdy, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QccBean.ResultBean.DCDY item) {
        helper.setText(R.id.tv_djbh,item.getRegisterNo()+"");
        if(item.getRegisterDate()!=null) {
            helper.setText(R.id.tv_dj_time, item.getRegisterDate().replace("00:00:00", "").trim());
        }
        helper.setText(R.id.tv_djjg,item.getRegisterOffice()+"");
    }
}
