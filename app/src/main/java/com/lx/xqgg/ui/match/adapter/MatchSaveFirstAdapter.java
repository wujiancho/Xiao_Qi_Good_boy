package com.lx.xqgg.ui.match.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.match.bean.MatchSaveFirstBean;

import java.util.List;

import androidx.annotation.Nullable;

public class MatchSaveFirstAdapter extends BaseQuickAdapter<MatchSaveFirstBean,BaseViewHolder> {

    public MatchSaveFirstAdapter(@Nullable List<MatchSaveFirstBean> data) {
        super(R.layout.item_match_one, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchSaveFirstBean item) {
        helper.setText(R.id.tv_company_name, (item.getCompany() + "(" + item.getLink_man() + ")"));
        helper.setText(R.id.tv_time, item.getCreatetime() + "");
//        helper.setText(R.id.tv_num, oneBean.getProduct().size() + "条");
    }
}
