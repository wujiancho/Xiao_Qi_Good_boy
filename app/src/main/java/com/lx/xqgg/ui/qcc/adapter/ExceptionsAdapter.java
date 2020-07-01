package com.lx.xqgg.ui.qcc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.product.bean.QccBean;

import java.util.List;

import androidx.annotation.Nullable;

public class ExceptionsAdapter extends BaseQuickAdapter<QccBean.ResultBean.Excetions, BaseViewHolder> {
    public ExceptionsAdapter(@Nullable List<QccBean.ResultBean.Excetions> data) {
        super(R.layout.item_exceptions, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QccBean.ResultBean.Excetions item) {
        helper.setText(R.id.tv_lr_reason, item.getAddReason());
        if (item.getAddDate() != null) {
            helper.setText(R.id.tv_lr_time, item.getAddDate().replace("00:00:00", "").trim());
        }
        helper.setText(R.id.tv_yc_reason, item.getRomoveReason());
        if (item.getRemoveDate() != null) {
            helper.setText(R.id.tv_yc_time, item.getRemoveDate().replace("00:00:00", "").trim());
        }
    }
}
