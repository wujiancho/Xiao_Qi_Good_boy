package com.lx.xqgg.ui.integral_query.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.integral_query.bean.IntegralqueryhistoryBean;

import java.util.List;

import androidx.annotation.Nullable;

public class IntegralqueryHistorryAdapter extends BaseQuickAdapter<IntegralqueryhistoryBean, BaseViewHolder> {
    public static final int typeji = 0;
    public static final int typeo = 1;
    public IntegralqueryHistorryAdapter(@Nullable List<IntegralqueryhistoryBean> data) {
        super(R.layout.integralqueryhistorryitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegralqueryhistoryBean item) {
     helper.setText(R.id.history_time,item.getTime());
     helper.setText(R.id.history_integral,item.getIntegral());
     if (item.getStatus()==0){
         helper.setText(R.id.history_status,"受理中");
     }else if (item.getStatus()==1){
         helper.setText(R.id.history_status,"完成");
     }
        switch (helper.getItemViewType()){
            case typeji:
                helper.setBackgroundColor(R.id.history_time, Color.parseColor("#F0F0F0"));
                helper.setBackgroundColor(R.id.history_integral, Color.parseColor("#F0F0F0"));
                helper.setBackgroundColor(R.id.history_status, Color.parseColor("#F0F0F0"));
                break;
            case typeo:
                helper.setBackgroundColor(R.id.history_time,Color.parseColor("#FFFFFF"));
                helper.setBackgroundColor(R.id.history_integral,Color.parseColor("#FFFFFF"));
                helper.setBackgroundColor(R.id.history_status,Color.parseColor("#FFFFFF"));
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position%2==0){
            return typeji;
        }
        return  typeo;
    }
}
