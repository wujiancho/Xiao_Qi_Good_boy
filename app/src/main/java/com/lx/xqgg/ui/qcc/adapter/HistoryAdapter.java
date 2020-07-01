package com.lx.xqgg.ui.qcc.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;

import java.util.List;

import androidx.annotation.Nullable;

public class HistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private onClearListener onClearListener;
    public HistoryAdapter(@Nullable List<String> data) {
        super(R.layout.item_history, data);
    }

    public void setOnClearListener(onClearListener onClearListener){
        this.onClearListener=onClearListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name,item);
        helper.setOnClickListener(R.id.v_close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClearListener!=null){
                    onClearListener.onClear(item);
                }
            }
        });
    }

    public interface onClearListener{
        void onClear(String s);
    }
}
