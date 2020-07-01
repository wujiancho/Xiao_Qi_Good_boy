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
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.ui.product.bean.QccBean;
import com.lx.xqgg.ui.qcc.LinkCompanyListActivity;
import com.lx.xqgg.ui.qcc.bean.QccPersonBean;

import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KeyPeopleAdapter extends BaseQuickAdapter<QccBean.ResultBean.EmployeesBean, BaseViewHolder> {
    private String company;

    public KeyPeopleAdapter(@Nullable List<QccBean.ResultBean.EmployeesBean> data,String company) {
        super(R.layout.item_key_people, data);
        this.company=company;
    }

    @Override
    protected void convert(BaseViewHolder helper, QccBean.ResultBean.EmployeesBean item) {
        helper.setText(R.id.tv_first,item.getName().substring(0,1));
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_job,item.getJob());
        ApiManage.getInstance().getMainApi().getSeniorPerson(company,item.getName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<String>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<String> stringBaseData) {
                        if(stringBaseData.isSuccess()){
                            QccPersonBean qccPersonBean=new Gson().fromJson(stringBaseData.getData(),QccPersonBean.class);
                            if(qccPersonBean.getPaging()!=null) {
                                Log.e("zlz", new Gson().toJson(qccPersonBean));
                                helper.setText(R.id.tv_company_num, "他有" + qccPersonBean.getPaging().getTotalRecords() + "家公司");
//                            tvCompanyNum.setText("关联企业"+qccPersonBean.getPaging().getTotalRecords()+"家");
                                helper.setOnClickListener(R.id.tv_company_num, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(mContext, LinkCompanyListActivity.class);
                                        intent.putExtra("name", item.getName());
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
