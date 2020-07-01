package com.lx.xqgg.ui.qcc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.product.bean.QccBean;

import java.util.List;

import androidx.annotation.Nullable;

public class BcxrAdapter extends BaseQuickAdapter<QccBean.ResultBean.ZhiXin, BaseViewHolder> {
    public BcxrAdapter(@Nullable List<QccBean.ResultBean.ZhiXin> data) {
        super(R.layout.item_bcxr, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QccBean.ResultBean.ZhiXin item) {
        helper.setText(R.id.tv_ah,item.getAnno()+"");
        helper.setText(R.id.tv_cxfy,item.getExecuteGov()+"");
        helper.setText(R.id.tv_cxbd,item.getBiaodi()+"");
        helper.setText(R.id.tv_larq,item.getLiandate()+"");
        helper.setText(R.id.tv_fbrq,item.getUpdatedate()+"");
    }
}
