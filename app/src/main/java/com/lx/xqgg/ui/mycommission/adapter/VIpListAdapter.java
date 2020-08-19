package com.lx.xqgg.ui.mycommission.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.ui.mycommission.bean.DetailsofinterestBean;
import com.lx.xqgg.ui.mycommission.bean.ListofinterestsBean;
import com.lx.xqgg.ui.mycommission.bean.SystemCommissionlevelBean;
import com.lx.xqgg.ui.webview.WebViewActivity;
import com.lx.xqgg.util.Base64;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class VIpListAdapter extends BaseQuickAdapter<SystemCommissionlevelBean.RightsBean, BaseViewHolder> {
    public int listsize;
    public VIpListAdapter( @Nullable List<SystemCommissionlevelBean.RightsBean> data) {
        super(R.layout.viplistitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemCommissionlevelBean.RightsBean item) {
        Glide.with(mContext)
                .load(Config.IMGURL+item.getImage())
                .into((ImageView) helper.getView(R.id.vip_img));
//Config.IMGURL + item.getImage()
        helper.setText(R.id.vip_name,item.getRightsName());
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("id", item.getRightsId());
        paramsMap.put("ident", "app");
        paramsMap.put("statusHeight", "20");
        String json= new Gson().toJson(paramsMap);
        Log.d("quanyi", "convert: "+json);
        String jiajson= Base64.encode(json.getBytes());
        String jiekong= Config.URL+"view/rights.html?bean="+jiajson;
        Log.d("quanyi", "convert: "+jiekong);
        helper.setOnClickListener(R.id.detailsofinterests, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.open(new WebViewActivity.Builder()
                        .setContext(mContext)
                        .setAutoTitle(false)
                        .setIsFwb(false)
                        .setNeedShare(false)
                        .setUrl(jiekong),false);
            }
        });
    }
    @Override
    public int getItemCount() {
        return listsize;
    }

    public  int statusvip(int size){
        listsize=size;
        return size;
    }

}
