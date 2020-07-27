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
        helper.setText(R.id.user_time,item.getTime());
        helper.setText(R.id.user_name,item.getServiceprovider());
        helper.setText(R.id.user_phone,item.getPhone());
        if ("fail".equals(item.getStatus())){
            helper.setText(R.id.user_status,"失败");
        }else if ("normal".equals(item.getStatus())){
            helper.setText(R.id.user_status,"已完成");
        }
        else if ("uncertified".equals(item.getStatus())){
            helper.setText(R.id.user_status,"未完成");
        }
    }


}
