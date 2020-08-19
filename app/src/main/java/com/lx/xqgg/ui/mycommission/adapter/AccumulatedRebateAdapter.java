package com.lx.xqgg.ui.mycommission.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.ui.mycommission.AccumulatedRebateActivity;
import com.lx.xqgg.ui.mycommission.RefundDetailsActivity;
import com.lx.xqgg.ui.mycommission.bean.HistoryPointsdetailstBean;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.Nullable;

public class AccumulatedRebateAdapter extends BaseQuickAdapter<HistoryPointsdetailstBean.DataBean.ListBean, BaseViewHolder> {
    public AccumulatedRebateAdapter(@Nullable List<HistoryPointsdetailstBean.DataBean.ListBean> data) {
        super(R.layout.accumulatedrebateitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryPointsdetailstBean.DataBean.ListBean item) {
        int  productCharge = item.getProductCharge();
        DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
        String productChargez= df.format(productCharge);
       helper.setText(R.id.accumulated_pointscontent,item.getMonth()+" 佣金积分 "+productChargez);
        Glide.with(mContext)
                .load(Config.IMGURL+item.getIco())
                .into((ImageView) helper.getView(R.id.accumulated_pointsimg));
        //Config.IMGURL + item.getPicture()
       helper.setOnClickListener(R.id.integraldetailed, new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intentrd=new Intent(mContext, RefundDetailsActivity.class);
               intentrd.putExtra("month",item.getMonth());
               intentrd.putExtra("integral",item.getProductCharge());
               mContext.startActivity(intentrd);
           }
       });
    }
}
