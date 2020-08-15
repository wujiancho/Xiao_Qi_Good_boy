package com.lx.xqgg.ui.match.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.match.bean.ExistCustomerBean;
import com.lx.xqgg.ui.match.bean.ExistCustomerInfortionBean;
import com.lx.xqgg.ui.match.bean.MatchRequestBean;
import com.lx.xqgg.ui.match.bean.MatchResultBean;
import com.lx.xqgg.ui.match.bean.MatchSavedBean;
import com.lx.xqgg.ui.person.bean.ProductDetailBean;
import com.lx.xqgg.ui.product.ProductDetailActivity;
import com.lx.xqgg.ui.product.bean.ProductBean;
import com.lx.xqgg.ui.webview.WebViewActivity;
import com.lx.xqgg.util.Base64;
import com.lx.xqgg.util.SpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MatchResultAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private MatchRequestBean matchRequestBean;
    private boolean isShow=false;
    private String rat;
    private String pay;
    private String bal;
    private String fee;
    private String cre;
    private String dai;
    private String ben;
    private String jin;
    private String xi;
    private String mon;
    private String yus;

    public MatchResultAdapter(@Nullable List<MultiItemEntity> data,boolean isShow) {
//        super(R.layout.item_match_result, data);
        super(data);
        addItemType(0, R.layout.item_match_type);
        addItemType(1, R.layout.item_match_product);
        this.matchRequestBean = matchRequestBean;
        this.isShow=isShow;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
//        String[] titles=item.getTitle().split("-");
//        helper.setText(R.id.tv_bank_name, titles[0].trim()+ "");
//        if(titles.length>1) {
//            helper.setText(R.id.textView2, titles[1].trim() + "");
//        }else {
//            helper.setText(R.id.textView2, "");
//        }
//        helper.setText(R.id.tv_ed, item.getQuota()/10000 + "");
//        helper.setText(R.id.tv_rlv, item.getRate() + "");
//        Glide.with(mContext)
//                .load(Config.IMGURL + item.getImage())
//                .apply(new RequestOptions().placeholder(R.drawable.ic_default))
//                .into((ImageView) helper.getView(R.id.image_view));
        rat = SharedPrefManager.getImitationexamination().getPro_rat();
        pay = SharedPrefManager.getImitationexamination().getPro_pay();
        bal = SharedPrefManager.getImitationexamination().getPro_bal();
        fee = SharedPrefManager.getImitationexamination().getPro_fee();
        cre = SharedPrefManager.getImitationexamination().getPro_cre();
        dai = SharedPrefManager.getImitationexamination().getPro_dai();
        ben = SharedPrefManager.getImitationexamination().getPro_ben();
        jin = SharedPrefManager.getImitationexamination().getPro_jin();
        xi = SharedPrefManager.getImitationexamination().getPro_xi();
        mon = SharedPrefManager.getImitationexamination().getPro_mon();
        yus = SharedPrefManager.getImitationexamination().getPro_yus();
        switch (item.getItemType()) {
            case 0:
                final MatchResultBean oneBean = (MatchResultBean) item;
                helper.setText(R.id.tv_type, oneBean.getCateName());
                helper.setText(R.id.liyu, rat);
                helper.setText(R.id.huakk, pay);

                break;
            case 1:
                final MatchResultBean.ProductBean twoBean = (MatchResultBean.ProductBean) item;
                helper.setText(R.id.tv_kded, "可"+dai+bal);
                helper.setText(R.id.textView3, "参考日"+fee);
                helper.setText(R.id.tv_status, cre);
                TextView textView=helper.getView(R.id.tv_num);
                Log.e("zlz",textView.getTextSize()+"");
                float size=textView.getTextSize();

                switch (twoBean.getSortIndex()){
                    case 1:
                        size=24;
                        break;
                    case 2:
                        size=22;
                        break;
                    case 3:
                        size=20;
                        break;
                    case 4:
                        size=18;
                        break;
                    default:
                        size=16;
                        break;
                }
                textView.setTextSize(size);
                helper.setText(R.id.tv_num, twoBean.getSortIndex() + "");
                if(twoBean.getTitle()!=null) {
                    String[] titles = twoBean.getTitle().split("-");
                    helper.setText(R.id.tv_bank_name, titles[0].trim() + "");
                    if (titles.length > 1) {
                        helper.setText(R.id.textView2, titles[1].trim() + "");
                    } else {
                        helper.setText(R.id.textView2, "");
                    }
                }
                helper.setText(R.id.tv_ed, twoBean.getQuota()/10000 + mon);
                helper.setText(R.id.tv_rlv, twoBean.getRate() + "");
                Glide.with(mContext)
                        .load(Config.IMGURL + twoBean.getImage())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into((ImageView) helper.getView(R.id.image_view));
                if(twoBean.getGuarantee()!=null) {
                    String[] hk = twoBean.getGuarantee().replace("xi", xi)
                            .replace("ben", ben)
                            .replace("jin", jin)
                            .split(",");
                    ArrayAdapter arrayAdapter = new ArrayAdapter<String>(mContext, R.layout.item_text, R.id.tv_info, hk);
                    ListView listView = helper.getView(R.id.list_view);
                    listView.setAdapter(arrayAdapter);
                }
                if(isShow) {
                    String status="";
                    if(twoBean.getOrderStatus()==null){
                        status="未申请";
                    }else {
                        switch (twoBean.getOrderStatus()) {
                            case "created":
                                status="审核中";
                                break;
                            case "precredit":
                                status=yus;
                                break;
                            case "verify":
                                status="审核中";
                                break;
                            case "nopass":
                                status="终审拒绝";
                                break;
                            case "usecredit":
                                status=cre;
                                break;
                            case "pass":
                                status="终审";
                                break;
                        }
                    }
                    helper.setText(R.id.tv_status,status);
                    helper.setOnClickListener(R.id.layout, new View.OnClickListener() {

                        private String companymach;
                        private ProductDetailBean productDetailBean;

                        @Override
                        public void onClick(View v) {
                            if("offline".equals(twoBean.getStatus())){
                                Toast toast=Toast.makeText(mContext, "该产品未上架，请联系客服线下办理", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            }
                            /*Intent intent = new Intent(mContext, ProductDetailActivity.class);
                            intent.putExtra("data", twoBean.getId());
                            mContext.startActivity(intent);*/
                            /*Toast toast=Toast.makeText(mContext, "跳转产品详细页H5", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();*/
                            //接受的数据生成jsonbean数据
                            String userphone= SharedPrefManager.getUserInfo().getMobile();
                            int userid= twoBean.getId();
                            String cityname= Constans.CITY;
                            HashMap<String, Object> paramsMap = new HashMap<>();
                            paramsMap.put("token", SharedPrefManager.getUser().getToken());
                            paramsMap.put("mobile", userphone);
                            paramsMap.put("name", SharedPrefManager.getUser().getUsername());
                            paramsMap.put("company", companymach);
                            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                            ApiManage.getInstance().getMainApi().getExistCustomerInfortion(body)
                                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new BaseSubscriber<BaseData<ExistCustomerInfortionBean>>(mContext,null) {
                                @Override
                                public void onNext(BaseData<ExistCustomerInfortionBean> existCustomerInfortionBeanBaseData) {
                                    ExistCustomerInfortionBean data= existCustomerInfortionBeanBaseData.getData();
                                    if (existCustomerInfortionBeanBaseData.isSuccess()){
                                       companymach = SpUtil.getInstance().getSpString("companymach");
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
                                       ArrayList<ExistCustomerBean> gson= new ArrayList<>();
                                       ExistCustomerBean existCustomerBean = new ExistCustomerBean();
                                       existCustomerBean.setCityName(cityname+"");
                                       existCustomerBean.setCompany(companymach+"");
                                       existCustomerBean.setType("match");
                                       existCustomerBean.setId(userid+"");
                                       existCustomerBean.setCreditCode(data.getCreditCode()+"");
                                       existCustomerBean.setId_card(data.getId_card()+"");
                                       existCustomerBean.setLink_man(data.getLink_man()+"");
                                       existCustomerBean.setIndustry(data.getIndustry()+"");
                                       existCustomerBean.setId_card1(data.getId_card1()+"");
                                       existCustomerBean.setLink_phone(userphone+"");
                                       existCustomerBean.setArea(data.getArea()+"");
                                       existCustomerBean.setStatusHeight("30.000");
                                       gson.add(existCustomerBean);
                                       String url=new Gson().toJson(gson);
                                       String json =url.substring(1,url.length()-1);
                                       Log.e("zlz",json);
                                       //加密json
                                       String jiajson= Base64.encode(json.getBytes());
                                       //生成产品详细页的接口

                                       String jiekong= Config.URL+"view/productDetails.html?bean="+jiajson;
                                       Log.e("zlz",jiekong);

                                       if(!"".equals(jiekong2)){
                                           WebViewActivity.open(new WebViewActivity.Builder()
                                                   .setContext(mContext)
                                                   .setAutoTitle(false)
                                                   .setIsFwb(false)
                                                   .setTitle("产品详情")
                                                   .setNeedShare(false)
                                                   .setUrl(jiekong),false);
                                       }
                                   }
                                }
                            });


                        }
                    });
                }
                if("offline".equals(twoBean.getStatus())){
                    Glide.with(mContext)
                            .load(R.drawable.flag_outline)
                            .into((ImageView) helper.getView(R.id.iv_flag));
                }
                break;
        }
    }
}
