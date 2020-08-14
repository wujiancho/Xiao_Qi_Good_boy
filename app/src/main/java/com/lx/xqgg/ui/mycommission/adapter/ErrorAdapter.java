package com.lx.xqgg.ui.mycommission.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;

import java.util.List;

import androidx.annotation.Nullable;

public class ErrorAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ErrorAdapter( @Nullable List<String> data) {
        super(R.layout.layout_empty, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
