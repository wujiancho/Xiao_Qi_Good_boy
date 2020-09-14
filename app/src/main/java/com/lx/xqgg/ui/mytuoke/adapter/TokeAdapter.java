package com.lx.xqgg.ui.mytuoke.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.mytuoke.bean.TokeBean;

import java.util.List;

import androidx.annotation.Nullable;

public class TokeAdapter extends BaseQuickAdapter<TokeBean, BaseViewHolder> {
    public TokeAdapter(@Nullable List<TokeBean> data) {
        super(R.layout.tokeitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TokeBean item) {
        helper.setText(R.id.user_time,item.getCreatetime());
        helper.setText(R.id.user_name,item.getService_name());
        helper.setText(R.id.user_phone,item.getMobile().substring(0,3)+"****"+item.getMobile().substring(item.getMobile().length()-1,item.getMobile().length()));
        helper.setText(R.id.user_status,item.getVerification());
    }


}
