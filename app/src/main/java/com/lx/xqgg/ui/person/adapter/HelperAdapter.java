package com.lx.xqgg.ui.person.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.person.bean.HelperBean;

import java.util.List;

import androidx.annotation.Nullable;

public class HelperAdapter extends BaseQuickAdapter<HelperBean.RecordsBean, BaseViewHolder> {

    public HelperAdapter(@Nullable List<HelperBean.RecordsBean> data) {
        super(R.layout.item_helper, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HelperBean.RecordsBean item) {
        helper.setText(R.id.tv_num, (helper.getLayoutPosition() + 1)+".");
        helper.setText(R.id.tv_title, item.getTitle());
    }
}
