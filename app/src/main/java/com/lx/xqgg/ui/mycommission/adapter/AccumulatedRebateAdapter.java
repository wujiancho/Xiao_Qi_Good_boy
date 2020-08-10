package com.lx.xqgg.ui.mycommission.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.mycommission.bean.AccumulatedPointslistBean;

import java.util.List;

import androidx.annotation.Nullable;

public class AccumulatedRebateAdapter extends BaseQuickAdapter<AccumulatedPointslistBean, BaseViewHolder> {
    public AccumulatedRebateAdapter(@Nullable List<AccumulatedPointslistBean> data) {
        super(R.layout.accumulatedrebateitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccumulatedPointslistBean item) {
       helper.setText(R.id.accumulated_pointscontent,item.getTime());
       helper.setImageResource(R.id.accumulated_pointsimg,item.getVipimg());
       helper.addOnClickListener(R.id.integraldetailed);
    }
}
