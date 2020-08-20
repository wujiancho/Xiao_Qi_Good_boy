package com.lx.xqgg.ui.product.adapter;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.event.ProductDetailEvent;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.person.bean.ProductDetailBean;
import com.lx.xqgg.ui.product.bean.ProductBean;
import com.lx.xqgg.ui.webview.WebViewActivity;
import com.lx.xqgg.util.Base64;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class ProductAdapter extends BaseQuickAdapter<ProductBean.RecordsBean, BaseViewHolder> {
    private OnApplyClickListener onApplyClickListener;
    private String bal;
    private String fee;
    private String dai;
    private String mon;
    private String wan;
    private ProductDetailBean productDetailBean;
    public ProductAdapter(@Nullable List<ProductBean.RecordsBean> data) {
        super(R.layout.item_product, data);
    }

    public void setOnApplyClickListener(OnApplyClickListener onApplyClickListener) {
        this.onApplyClickListener = onApplyClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean.RecordsBean item) {
        String[] titles=item.getTitle().split("-");
        bal = SharedPrefManager.getImitationexamination().getPro_bal();
        fee = SharedPrefManager.getImitationexamination().getPro_fee();
        dai = SharedPrefManager.getImitationexamination().getPro_dai();
        wan = SharedPrefManager.getImitationexamination().getPro_wan();
        helper.setText(R.id.tv_bank_name, titles[0].trim()+ "");
        if(titles.length>1) {
            helper.setText(R.id.textView2, titles[1].trim() + "");
        }else {
            helper.setText(R.id.textView2, "");
        }
        helper.setText(R.id.tv_ed, item.getQuota()/10000 + wan);
        helper.setText(R.id.tv_rlv, item.getRate() + "");
        helper.setText(R.id.tv_kded, "可"+dai+bal);
        helper.setText(R.id.textView3, "参考日"+fee);
        Glide.with(mContext)
                .load(Config.IMGURL + item.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                .into((ImageView) helper.getView(R.id.image_view));
        helper.setOnClickListener(R.id.btn_apply, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onApplyClickListener.onClick(item);
            }
        });
        helper.setOnClickListener(R.id.list_item, new AdapterView.OnClickListener() {

            @Override
            public void onClick(View v) {
                //接受的数据生成jsonbean数据
                String userphone= SharedPrefManager.getUser().getMobile();
                int userid= item.getId();
                String image = item.getImage();
                String  title =  item.getTitle();
                int quota =  item.getQuota();
                String rate =  item.getRate();
                String  ed= SharedPrefManager.getImitationexamination().getPro_bal();
                String  fee= SharedPrefManager.getImitationexamination().getPro_fee();
                String count=ed+":"+(quota/10000)+",日"+fee+":" + rate;
                ProductDetailEvent event=new ProductDetailEvent();
                event.setImage(image);
                event.setTitle(title);
                event.setCount(count);
                EventBus.getDefault().postSticky(event);
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

                if (!"".equals(jiekong2)) {
                    WebViewActivity.open(new WebViewActivity.Builder()
                            .setContext(mContext)
                            .setAutoTitle(false)
                            .setIsFwb(false)
                            .setTitle("产品详情")
                            .setNeedShare(false)
                            .setUrl(jiekong),false);
                }
            }
        });
    }

    public interface OnApplyClickListener {
        void onClick(ProductBean.RecordsBean bean);
    }

}
