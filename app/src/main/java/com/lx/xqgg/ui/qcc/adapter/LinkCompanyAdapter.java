package com.lx.xqgg.ui.qcc.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.qcc.bean.QccPersonBean;

import java.util.List;

import androidx.annotation.Nullable;

public class LinkCompanyAdapter extends BaseQuickAdapter<QccPersonBean.ResultBean, BaseViewHolder> {
    private boolean isFr=false;
    private boolean isRz=false;
    public LinkCompanyAdapter(@Nullable List<QccPersonBean.ResultBean> data) {
        super(R.layout.item_link_company, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QccPersonBean.ResultBean item) {
        Glide.with(mContext)
                .load(item.getImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                .into((ImageView) helper.getView(R.id.iv_logo));
        helper.setText(R.id.tv_compamy_name,item.getName()+"");
        helper.setText(R.id.tv_fr_name,"法定代表人："+item.getOperName()+"");
        helper.setText(R.id.tv_register_zb,"注册资本："+item.getRegCap()+"");
        if(item.getDate()!=null) {
            helper.setText(R.id.tv_start_time, "成立日期：" + item.getDate().replace("00:00:00", "").trim());
        }
        helper.setText(R.id.tv_status,item.getStatus()+"");

        for(QccPersonBean.ResultBean.RelationListBean bean:item.getRelationList()){
            if(bean.getType().equals("1")){
                isFr=true;
                helper.setGone(R.id.layout_gd,true);
                helper.setText(R.id.tv_percent,bean.getValue()+"");
            }
            if(bean.getType().equals("2")){
                isRz=true;
                helper.setVisible(R.id.tv_rz,true);
                helper.setText(R.id.tv_rz,","+bean.getValue());
            }
        }
        if(isFr){
            helper.setGone(R.id.layout_gd,true);
        }else {
            helper.setGone(R.id.layout_gd,false);
        }
        if(isRz){
            helper.setVisible(R.id.tv_rz,true);
        }else {
            helper.setVisible(R.id.tv_rz,false);
        }
    }
}
