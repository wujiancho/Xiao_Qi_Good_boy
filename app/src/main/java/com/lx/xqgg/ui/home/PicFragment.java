package com.lx.xqgg.ui.home;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.home.bean.AdvertBean;
import com.lx.xqgg.ui.person.bean.ProductDetailBean;
import com.lx.xqgg.ui.webview.WebViewActivity;
import com.lx.xqgg.util.Base64;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;

public class PicFragment extends DialogFragment {

    private ImageView imageView;
    private ImageView guan;
    private AdvertBean advertBean;
    private int userid;
    private ProductDetailBean productDetailBean;
    public PicFragment(AdvertBean advertBean) {
        this.advertBean = advertBean;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View view = inflater.inflate(R.layout.fragment_pic, null);
        imageView = view.findViewById(R.id.imageView);
        guan = view.findViewById(R.id.guan);

        Glide.with(getActivity())
                .load(Config.IMGURL + advertBean.getImage())
                .into(imageView);
        Log.d("guangao", "onCreateView: "+Config.IMGURL + advertBean.getImage());
        Log.d("guangao2", "onCreateView: "+advertBean.getUrl());
        userid = advertBean.getId();
        guan.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              dismiss();
          }
      });
        if (userid!=0 && !"0".equals(userid)) {
            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //接受的数据生成jsonbean数据
                    String userphone= SharedPrefManager.getUserInfo().getMobile();

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
                    Log.e("zlz",json2);
                    //加密json
                    String jiajson2= Base64.encode(json2.getBytes());
                    //生成产品详细页的接口
                    String jiekong2=Config.URL+"view/productDetails.html?bean="+jiajson2;
                    Constans.productDetails=jiekong2;

                    ArrayList<ProductDetailBean> gson= new ArrayList<>();
                    productDetailBean = new ProductDetailBean();
                    productDetailBean.setUserPhone(userphone);
                    productDetailBean.setType("app");
                    productDetailBean.setId(userid+"");
                    productDetailBean.setStatusHeight("30.000");
                    productDetailBean.setCityName(cityname);
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
                                .setContext(getActivity())
                                .setAutoTitle(false)
                                .setIsFwb(false)
                                .setUrl(jiekong));
                    }
                }
            });
    }
        return view;
    }
}
