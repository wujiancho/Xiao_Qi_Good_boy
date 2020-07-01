package com.lx.xqgg.ui.qcc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.product.bean.QccBean;

import java.util.List;

import androidx.annotation.Nullable;

public class ChangeAdapter extends BaseQuickAdapter<QccBean.ResultBean.ChangeRecordsBean, BaseViewHolder> {
    public ChangeAdapter(@Nullable List<QccBean.ResultBean.ChangeRecordsBean> data) {
        super(R.layout.item_change_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QccBean.ResultBean.ChangeRecordsBean item) {
        helper.setText(R.id.tv_num, (helper.getLayoutPosition() + 1) + "");
        if (item.getChangeDate() != null) {
            helper.setText(R.id.tv_time, item.getChangeDate().replace("00:00:00", "").trim());
        }
        helper.setText(R.id.tv_before, item.getBeforeContent()+"");
        helper.setText(R.id.tv_after, item.getAfterContent()+"");
        helper.setText(R.id.tv_project_name,item.getProjectName()+"");
    }
}
