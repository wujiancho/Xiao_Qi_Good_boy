package com.lx.xqgg.ui.mycommission.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.mycommission.bean.ViplistBean;

import java.util.List;

import androidx.annotation.Nullable;

public class VIpListAdapter extends BaseQuickAdapter<ViplistBean, BaseViewHolder> {
    public int listsize;
    public VIpListAdapter( @Nullable List<ViplistBean> data) {
        super(R.layout.viplistitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ViplistBean item) {
        helper.setImageResource(R.id.vip_img,item.getImgid());
        helper.setText(R.id.vip_name,item.getName());
    }
    @Override
    public int getItemCount() {
        return listsize;
    }

    public  int statusvip(int size){
        listsize=size;
        return size;
    }

}
