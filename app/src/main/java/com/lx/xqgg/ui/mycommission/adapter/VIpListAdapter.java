package com.lx.xqgg.ui.mycommission.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.ui.mycommission.bean.ListofinterestsBean;

import java.util.List;

import androidx.annotation.Nullable;

public class VIpListAdapter extends BaseQuickAdapter<ListofinterestsBean, BaseViewHolder> {
    public int listsize;
    public VIpListAdapter( @Nullable List<ListofinterestsBean> data) {
        super(R.layout.viplistitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListofinterestsBean item) {
        Glide.with(mContext)
                .load(Config.IMGURL + item.getImage())
                .into((ImageView) helper.getView(R.id.vip_img));

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
