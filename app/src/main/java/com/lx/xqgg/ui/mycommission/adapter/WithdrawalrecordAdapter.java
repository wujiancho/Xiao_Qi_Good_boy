package com.lx.xqgg.ui.mycommission.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.mycommission.bean.WithdrawalrecordBean;

import java.util.List;

import androidx.annotation.Nullable;

public class WithdrawalrecordAdapter extends BaseQuickAdapter<WithdrawalrecordBean, BaseViewHolder> {
    public static final int typeji = 0;
    public static final int typeo = 1;
    public WithdrawalrecordAdapter(@Nullable List<WithdrawalrecordBean> data) {
        super(R.layout.integralqueryhistorryitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawalrecordBean item) {
     helper.setText(R.id.history_time,item.getTime());
     helper.setText(R.id.last_number,item.getBandlastnumber());
     helper.setText(R.id.money,item.getMoney());
     if (item.getStatus()==0){
         helper.setText(R.id.history_status,"失败");
     }else if (item.getStatus()==1){
         helper.setText(R.id.history_status,"待结算");
     }else  if (item.getStatus()==2){
         helper.setText(R.id.history_status,"已完成");
     }
        switch (helper.getItemViewType()){
            case typeji:
                helper.setBackgroundColor(R.id.history_time,Color.parseColor("#FFFFFF"));
                helper.setBackgroundColor(R.id.last_number,Color.parseColor("#FFFFFF"));
                helper.setBackgroundColor(R.id.money,Color.parseColor("#FFFFFF"));
                helper.setBackgroundColor(R.id.history_status,Color.parseColor("#FFFFFF"));
                break;
            case typeo:
                helper.setBackgroundColor(R.id.history_time, Color.parseColor("#F0F0F0"));
                helper.setBackgroundColor(R.id.last_number, Color.parseColor("#F0F0F0"));
                helper.setBackgroundColor(R.id.money, Color.parseColor("#F0F0F0"));
                helper.setBackgroundColor(R.id.history_status, Color.parseColor("#F0F0F0"));
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
