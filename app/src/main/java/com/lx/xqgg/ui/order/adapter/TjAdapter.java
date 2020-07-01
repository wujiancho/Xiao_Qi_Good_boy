package com.lx.xqgg.ui.order.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.order.bean.TjBean;

import java.util.List;

import androidx.annotation.Nullable;

public class TjAdapter extends BaseQuickAdapter<TjBean, BaseViewHolder> {
    public TjAdapter(int layoutResId, @Nullable List<TjBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TjBean item) {
        Log.e("zlz", item.getId() + "id");
        String[] titles = item.getName().split("-");
        if (titles.length == 2) {
            helper.setText(R.id.tv_name, titles[0].trim() + "\n" + titles[1]);
        } else {
            helper.setText(R.id.tv_name, item.getName().trim());
        }
        helper.setText(R.id.tv_jjl, item.getTotalOrderNum() + "");
        helper.setText(R.id.tv_spz, item.getCreatedOrderNum() + "");
        helper.setText(R.id.tv_ysx, item.getPreCreditOrderNum() + "");
        helper.setText(R.id.tv_zs, item.getPassNum() + "");
        helper.setText(R.id.tv_yx, item.getUseCreditOrderNum() + "");
        helper.setText(R.id.tv_tgl, item.getPassRate() == null ? "0" : Math.round(((Double.valueOf(item.getPassRate()))*100))+"%");
        helper.setText(R.id.tv_yxl, item.getUserCreditRate() == null ? "0" : Math.round(((Double.valueOf(item.getUserCreditRate()))*100)) + "%");
        helper.setText(R.id.tv_jj, item.getAverageMoney() == null ? "0" : (String.format("%.2f", Double.valueOf(item.getAverageMoney()) / 10000) + ""));
        helper.setText(R.id.tv_yxe, item.getUserCreditMoney() == null ? "0" : (String.format("%.2f", Double.valueOf(item.getUserCreditMoney()) / 10000) + ""));
    }
}
