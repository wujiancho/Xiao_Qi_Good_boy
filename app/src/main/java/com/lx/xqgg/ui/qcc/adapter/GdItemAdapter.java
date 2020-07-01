package com.lx.xqgg.ui.qcc.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.ui.product.bean.QccBean;
import com.lx.xqgg.ui.qcc.LinkCompanyListActivity;
import com.lx.xqgg.ui.qcc.bean.QccPersonBean;

import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GdItemAdapter extends BaseQuickAdapter<QccBean.ResultBean.PartnersBean, BaseViewHolder> {
    private String company;

    public GdItemAdapter(@Nullable List<QccBean.ResultBean.PartnersBean> data,String company) {
        super(R.layout.item_qcc_main_gd, data);
        this.company=company;
    }

    @Override
    protected void convert(BaseViewHolder helper, QccBean.ResultBean.PartnersBean item) {
        helper.setText(R.id.tv_first, item.getStockName().substring(0, 1));
        helper.setText(R.id.tv_name,item.getStockName()+"");
        if (item.getTagsList() != null && item.getTagsList().size() > 0) {
            helper.setText(R.id.tv_tag1, item.getTagsList().get(0));
        }
        helper.setText(R.id.tv_percent,item.getStockPercent()+"");
        ApiManage.getInstance().getMainApi().getSeniorPerson(company,item.getStockName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<String>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<String> stringBaseData) {
                        if(stringBaseData.isSuccess()){
                            QccPersonBean qccPersonBean=new Gson().fromJson(stringBaseData.getData(),QccPersonBean.class);
                            Log.e("zlz",new Gson().toJson(qccPersonBean));
                            if(qccPersonBean.getPaging()!=null) {
                                helper.setText(R.id.tv_num, qccPersonBean.getPaging().getTotalRecords() + "");
                                helper.setOnClickListener(R.id.layout, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(mContext, LinkCompanyListActivity.class);
                                        intent.putExtra("name", item.getStockName());
                                        intent.putExtra("data", qccPersonBean);
                                        mContext.startActivity(intent);
                                    }
                                });
                            }
                        }
                    }
                });
    }
}
