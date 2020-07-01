package com.lx.xqgg.ui.hot.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.ui.home.bean.HotMsgBean;

import java.util.List;

import androidx.annotation.Nullable;

public class HotMsgAdapter extends BaseQuickAdapter<HotMsgBean.RecordsBean, BaseViewHolder> {
    public HotMsgAdapter(@Nullable List<HotMsgBean.RecordsBean> data) {
        super(R.layout.item_hot_msg, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotMsgBean.RecordsBean item) {
        helper.setText(R.id.tv_title,item.getTitle()+"");
        helper.setText(R.id.tv_time,item.getCreate_time()+"");
        helper.setText(R.id.tv_read_num,item.getRead_num()+"人阅读");
        Glide.with(mContext)
                .load(Config.IMGURL + item.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                .into((ImageView) helper.getView(R.id.image_view));
    }
}
