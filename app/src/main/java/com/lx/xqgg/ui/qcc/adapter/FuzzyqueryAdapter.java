package com.lx.xqgg.ui.qcc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.qcc.bean.FuzzyqueryQccBean;

import java.util.List;

import androidx.annotation.Nullable;

public class FuzzyqueryAdapter extends BaseQuickAdapter<FuzzyqueryQccBean.ResultBean, BaseViewHolder> {
    public FuzzyqueryAdapter(@Nullable List<FuzzyqueryQccBean.ResultBean> data) {
        super(R.layout.fuzzyqueryitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FuzzyqueryQccBean.ResultBean item) {
        helper.setText(R.id.corporate_name,item.getName());
        helper.setText(R.id.boss_name,item.getOperName());
        helper.setText(R.id.corporate_status,item.getStatus());
        helper.addOnClickListener(helper.itemView.getId());
    }


}
