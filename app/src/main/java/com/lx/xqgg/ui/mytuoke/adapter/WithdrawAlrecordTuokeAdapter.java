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
      helper.setText(R.id.tv_zfborder_num,"支付宝账号："+item.getZfborbernum());
      helper.setText(R.id.tv_money,"¥"+item.getMoney1());
      helper.setText(R.id.tv_starttime,item.getStarttime());
      helper.setText(R.id.tuoke_endtime,item.getEndtime());
      helper.setText(R.id.tuoke_jj,"-"+item.getJif());
      helper.setText(R.id.txmoney,item.getMoney2());
      helper.setText(R.id.txstuart,item.getStuart());
    }
}
