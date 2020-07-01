package com.lx.xqgg.ui.product.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.product.bean.ProductBean;

import java.util.List;

import androidx.annotation.Nullable;

public class TraitAdapter extends BaseQuickAdapter<ProductBean.RecordsBean.ClassifyListBean, BaseViewHolder> {
    public TraitAdapter(@Nullable List<ProductBean.RecordsBean.ClassifyListBean> data) {
        super(R.layout.item_trait, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean.RecordsBean.ClassifyListBean item) {
        helper.setText(R.id.tv_name, item.getName()+"");
    }
}
