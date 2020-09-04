package com.lx.xqgg.ui.mytuoke.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.mytuoke.bean.WithdrawAlrecordTuokelistBean;

import java.util.List;

import androidx.annotation.Nullable;

public class WithdrawAlrecordTuokeAdapter extends BaseQuickAdapter<WithdrawAlrecordTuokelistBean, BaseViewHolder> {
    public WithdrawAlrecordTuokeAdapter( @Nullable List<WithdrawAlrecordTuokelistBean> data) {
        super(R.layout.withdrawalrecordtuokelistitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawAlrecordTuokelistBean item) {

    }
}
