package com.lx.xqgg.ui.mycommission.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.mycommission.bean.PointsdetailsBean;

import java.util.List;

import androidx.annotation.Nullable;

public class PointsDetailsAdapter extends BaseQuickAdapter<PointsdetailsBean, BaseViewHolder> {
    public PointsDetailsAdapter( @Nullable List<PointsdetailsBean> data) {
        super(R.layout.pointsdetailsitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PointsdetailsBean item) {
     helper.setText(R.id.tv_product_name,item.getProduct_name());
     helper.setText(R.id.tv_usingletterstime,"用信时间："+item.getUsingletterstime());
     helper.setText(R.id.tv_order_num,"订单编号："+item.getOrder_num());
     helper.setText(R.id.tv_company_name,"企业名称："+item.getCompany_name());
     helper.setText(R.id.tv_kh_name,"客户姓名："+item.getKh_name());
     helper.setText(R.id.tv_phone,"手机号："+item.getPhone());
     helper.setText(R.id.tv_yg_name,"员工姓名："+item.getYg_name());
     helper.setText(R.id.tv_status,item.getStatus());
     helper.setText(R.id.tv_jiapoint,item.getJiapoint());
     helper.setText(R.id.credit_points,item.getCredit_points()+"业务积分");
    }
}
