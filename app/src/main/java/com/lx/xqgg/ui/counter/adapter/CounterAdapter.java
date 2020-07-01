package com.lx.xqgg.ui.counter.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.counter.bean.CountBean;

import java.util.List;

import androidx.annotation.Nullable;

public class CounterAdapter extends BaseQuickAdapter<CountBean, BaseViewHolder> {
    public CounterAdapter(@Nullable List<CountBean> data) {
        super(R.layout.item_counter_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CountBean item) {
        helper.setText(R.id.tv_count, item.getCount() + "");
        helper.setText(R.id.tv_yg, String.format("%.2f", item.getYg()));
        helper.setText(R.id.tv_ygbj, String.format("%.2f", item.getYgbj()));
        helper.setText(R.id.tv_yglx, item.getYglx() < 0 ? "0" : String.format("%.2f", item.getYglx()));
        helper.setText(R.id.tv_byye, item.getByye() < 0 ? "0" : String.format("%.2f", item.getByye()));
    }
}
