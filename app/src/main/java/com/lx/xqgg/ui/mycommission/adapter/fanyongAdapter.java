package com.lx.xqgg.ui.mycommission.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.ui.mycommission.MycommissionActivity;
import com.lx.xqgg.ui.mycommission.XieYiActivity;
import com.lx.xqgg.ui.vip.bean.PayListBean;

import java.util.List;

import androidx.annotation.Nullable;

public class fanyongAdapter extends BaseQuickAdapter<PayListBean, BaseViewHolder>  {

    public fanyongAdapter( @Nullable List<PayListBean> data) {
        super(R.layout.fanyongitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayListBean item) {
      helper.setText(R.id.countitem,item.getName());
      Glide.with(mContext)
                .load(Config.IMGURL+item.getValue())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into((ImageView) helper.getView(R.id.logo2));

      helper.setOnClickListener(R.id.fanyongzz, new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intenxieyi = new Intent(mContext, XieYiActivity.class);
              intenxieyi.putExtra("group", "buyRule");
              mContext.startActivity(intenxieyi);
          }
      });
    }


}
