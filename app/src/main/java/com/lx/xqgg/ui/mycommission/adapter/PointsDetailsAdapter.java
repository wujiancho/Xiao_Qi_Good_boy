package com.lx.xqgg.ui.mycommission.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.mycommission.bean.ThisMothPointsdetailstBean;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.Nullable;

public class PointsDetailsAdapter extends BaseQuickAdapter<ThisMothPointsdetailstBean, BaseViewHolder> {

    public PointsDetailsAdapter(@Nullable List<ThisMothPointsdetailstBean> data) {
        super(R.layout.pointsdetailsitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ThisMothPointsdetailstBean item) {
     helper.setText(R.id.tv_product_name,item.getTitle());
     helper.setText(R.id.tv_usingletterstime,"用信时间："+item.getCreditTime().substring(0,item.getCreditTime().indexOf(" ")));
     helper.setText(R.id.tv_order_num,"订单编号："+item.getOrderNo());
     helper.setText(R.id.tv_company_name,"企业名称："+item.getCompanyName());
     helper.setText(R.id.tv_kh_name,"客户姓名："+item.getCustomerName());
     helper.setText(R.id.tv_phone,"手机号："+item.getCustomerPhone());
     helper.setText(R.id.tv_yg_name,"员工姓名："+item.getUserName());
     if ("usecredit".equals(item.getOrderStatus())){
         helper.setText(R.id.tv_status,"用信");
     } else if ("pass".equals(item.getOrderStatus())){
            helper.setText(R.id.tv_status,"终审通过");
        }
     helper.setText(R.id.tv_jiapoint,"+"+item.getProductCharge()+"佣金积分");
        int productCreditMoney = item.getProductCreditMoney();
        DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
        String productCreditMoneyz = df.format(productCreditMoney);
        helper.setText(R.id.credit_points,productCreditMoneyz+".00业务积分");
    }
}
