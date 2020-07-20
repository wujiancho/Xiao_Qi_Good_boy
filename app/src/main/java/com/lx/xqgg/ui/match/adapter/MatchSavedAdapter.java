package com.lx.xqgg.ui.match.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.match.bean.MatchSavedBean;
import com.lx.xqgg.ui.person.bean.ProductDetailBean;
import com.lx.xqgg.ui.product.ProductDetailActivity;
import com.lx.xqgg.ui.webview.WebViewActivity;
import com.lx.xqgg.util.Base64;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class MatchSavedAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {


    public MatchSavedAdapter(@Nullable List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.item_match_one);
        addItemType(1, R.layout.item_match_result);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (item.getItemType()) {
            case 0:
                View view = helper.getView(R.id.view);
                final MatchSavedBean oneBean = (MatchSavedBean) item;
                if (oneBean.isExpanded()) {
                    view.setRotation(0);
                } else {
                    view.setRotation(270);
                }
                helper.setText(R.id.tv_company_name, (oneBean.getCompany() + "(" + oneBean.getLink_man() + ")"));
                helper.setText(R.id.tv_time, oneBean.getCreatetime() + "");
                helper.setText(R.id.tv_num, oneBean.getProduct().size() + "条");
                helper.setOnClickListener(R.id.layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (oneBean.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case 1:
                final MatchSavedBean.ProductBean productBean = (MatchSavedBean.ProductBean) item;
                String[] titles = productBean.getTitle().split("-");
                helper.setText(R.id.tv_bank_name, titles[0].trim() + "");
                if (titles.length > 1) {
                    helper.setText(R.id.textView2, titles[1].trim() + "");
                } else {
                    helper.setText(R.id.textView2, "");
                }
//                helper.setText(R.id.tv_bank_name, productBean.getTitle() + "");
                helper.setText(R.id.tv_ed, productBean.getQuota() + "");
                helper.setText(R.id.tv_rlv, productBean.getRate() + "");
                Glide.with(mContext)
                        .load(Config.IMGURL + productBean.getImage())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into((ImageView) helper.getView(R.id.image_view));
                helper.setOnClickListener(R.id.layout, new View.OnClickListener() {

                    private ProductDetailBean productDetailBean;

                    @Override
                    public void onClick(View v) {
                        /*Intent intent = new Intent(mContext, ProductDetailActivity.class);
                        intent.putExtra("data", productBean.getId());
                        mContext.startActivity(intent);*/
                      /*  Toast toast=Toast.makeText(mContext, "跳转产品详细页H5", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/
                        //toast("跳转产品详细页H5");
                        //接受的数据生成jsonbean数据
                        String userphone= SharedPrefManager.getUserInfo().getMobile();
                        int userid= productBean.getId();
                        String cityname= Constans.CITY;

                        //生成产品详细页的接口
                        ArrayList<ProductDetailBean> gson2= new ArrayList<>();
                        productDetailBean = new ProductDetailBean();
                        productDetailBean.setUserPhone(userphone);
                        productDetailBean.setType("h5");
                        productDetailBean.setId(userid+"");
                        productDetailBean.setStatusHeight("30.000");
                        productDetailBean.setCityName("");
                        gson2.add(productDetailBean);
                        String url2=new Gson().toJson(gson2);
                        String json2 =url2.substring(1,url2.length()-1);
                        //加密json
                        String jiajson2= Base64.encode(json2.getBytes());
                        Log.e("zlz",json2);

                        //生成产品详细页的接口
                        String jiekong2=Config.URL+"view/productDetails.html?bean="+jiajson2;
                        Constans.productDetails=jiekong2;

                        ArrayList<ProductDetailBean> gson= new ArrayList<>();
                        productDetailBean = new ProductDetailBean();
                        productDetailBean.setUserPhone(userphone);
                        productDetailBean.setCityName(cityname);
                        productDetailBean.setType("app");
                        productDetailBean.setId(userid+"");
                        productDetailBean.setStatusHeight("30.000");
                        gson.add(productDetailBean);
                        String url=new Gson().toJson(gson);
                        String json =url.substring(1,url.length()-1);
                        Log.e("zlz",json);
                        //加密json
                        String jiajson= Base64.encode(json.getBytes());

                        String jiekong= Config.URL+"view/productDetails.html?bean="+jiajson;
                        Log.e("zlz",jiekong);

                        if(!"".equals(jiekong2)){
                            WebViewActivity.open(new WebViewActivity.Builder()
                                    .setContext(mContext)
                                    .setAutoTitle(false)
                                    .setIsFwb(false)
                                    .setUrl(jiekong));
                        }

                    }
                });
                break;
        }
    }
}
