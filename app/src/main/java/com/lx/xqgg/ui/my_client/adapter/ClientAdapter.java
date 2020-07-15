package com.lx.xqgg.ui.my_client.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.my_client.bean.ServiceCustomerBean;

import java.util.List;

import androidx.annotation.Nullable;

public class ClientAdapter extends BaseQuickAdapter<ServiceCustomerBean.RecordsBean, BaseViewHolder> {
    private OnStarClickListener onStarClickListener;

    public ClientAdapter(@Nullable List<ServiceCustomerBean.RecordsBean> data) {
        super(R.layout.item_search_company, data);
    }

    public void setStarListener(OnStarClickListener onStarClickListener) {
        this.onStarClickListener = onStarClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceCustomerBean.RecordsBean item) {
        if(TextUtils.isEmpty(item.getCompany())) {
//            helper.setVisible(R.id.tv_company_name,false);
            helper.setGone(R.id.tv_company_name,false);
        }else {
            helper.setGone(R.id.tv_company_name,true);
            helper.setText(R.id.tv_company_name, item.getCompany1());
        }
        helper.setText(R.id.tv_phone, item.getLink_man1() + " " + item.getLink_phone1());
        if (null != item.getId()) {
            helper.setVisible(R.id.iv_top, true);
            Glide.with(mContext)
                    .load(item.getIsTop().equals("0") ? R.drawable.ic_not_top : R.drawable.ic_is_top)
                    .into((ImageView) helper.getView(R.id.iv_top));
            helper.setOnClickListener(R.id.iv_top, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onStarClickListener) {
                        onStarClickListener.onClick(item);
                    }
                }
            });
        } else {
            helper.setVisible(R.id.iv_top, false);
        }

    }

    public interface OnStarClickListener {
        void onClick(ServiceCustomerBean.RecordsBean item);
    }
}
