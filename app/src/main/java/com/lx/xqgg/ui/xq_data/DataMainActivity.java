package com.lx.xqgg.ui.xq_data;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.ui.mytuoke.bean.XqdataBean;
import com.lx.xqgg.ui.xq_data.adapter.Xqdataadapter;
import com.lx.xqgg.util.RoundedCornersTransformation;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DataMainActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.xqdata_RecyclerView)
    RecyclerView xqdataRecyclerView;
    @BindView(R.id.yuedao)
    Button yuedao;
    @BindView(R.id.cpjjll)
    Button cpjjll;
    @BindView(R.id.cpbg)
    ImageView cpbg;
    private Xqdataadapter xqdataadapter;
    private List<XqdataBean> xqdatalist;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_main;
    }

    @Override
    protected void initView() {
        tvTitle.setText("小麒数据");
        RoundedCornersTransformation transformation = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.TOP_LEFT);
        //顶部右边圆角
        RoundedCornersTransformation transformation1 = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT);

        //顶部右边圆角
        RoundedCornersTransformation transformation2 = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT);
        //顶部右边圆角
        RoundedCornersTransformation transformation3 = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT);
        //组合各种Transformation,
        MultiTransformation<Bitmap> mation = new MultiTransformation<>
                //Glide设置圆角图片后设置ImageVIew的scanType="centerCrop"无效解决办法,将new CenterCrop()添加至此
                (new CenterCrop(), transformation, transformation1,transformation2,transformation3);
        Glide.with(mContext)
                .load(R.drawable.xqdatabg)
                .apply(RequestOptions.bitmapTransform(mation))
                .into(cpbg);
    }

    @Override
    protected void initData() {
        //获取小麒数据产品
        xqdata();
    }

    //获取小麒数据产品
    private void xqdata() {
        addSubscribe(ApiManage.getInstance().getMainApi().getXqdata()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<XqdataBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<XqdataBean>> listBaseData) {
                        Log.e("xqdata", new Gson().toJson(listBaseData));
                        if (listBaseData.isSuccess()) {
                            List<XqdataBean> data = listBaseData.getData();
                            if (data != null && data.size() > 0) {
                                xqdatalist = new ArrayList<>();
                                xqdatalist.addAll(data);
                                xqdataadapter = new Xqdataadapter(xqdatalist);
                                xqdataRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
                                xqdataRecyclerView.setAdapter(xqdataadapter);

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.getMessage());
                    }
                }));
    }


    @OnClick({R.id.v_close, R.id.yuedao, R.id.cpjjll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.yuedao:
            /*    HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("productId",item.getId());
                paramsMap.put("proName", item.getName());
                paramsMap.put("logo", item.getLogo());
                paramsMap.put("statusHeight","30");
                //RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),);

                //加密json
                String jiajson = Base64.encode( new Gson().toJson(paramsMap).getBytes());

                String jiekong = Config.URL + "view/productDetails.html?bean=" + jiajson;


                if (!"".equals(jiekong)) {
                    WebViewActivity.open(new WebViewActivity.Builder()
                            .setContext(mContext)
                            .setAutoTitle(false)
                            .setIsFwb(false)
                            .setTitle("每日产品数据分析")
                            .setNeedShare(false)
                            .setUrl(jiekong),false);
                }*/
                toast("公司月报");
                break;
            case R.id.cpjjll:
             /*   HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("productId",item.getId());
                paramsMap.put("proName", item.getName());
                paramsMap.put("logo", item.getLogo());
                paramsMap.put("statusHeight","30");
                //RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),);

                //加密json
                String jiajson = Base64.encode( new Gson().toJson(paramsMap).getBytes());

                String jiekong = Config.URL + "view/productDetails.html?bean=" + jiajson;


                if (!"".equals(jiekong)) {
                    WebViewActivity.open(new WebViewActivity.Builder()
                            .setContext(mContext)
                            .setAutoTitle(false)
                            .setIsFwb(false)
                            .setTitle("每日产品数据分析")
                            .setNeedShare(false)
                            .setUrl(jiekong),false);
                }*/
                toast("小麒乖乖产品拒绝理由统计");
                break;
        }
    }

}
