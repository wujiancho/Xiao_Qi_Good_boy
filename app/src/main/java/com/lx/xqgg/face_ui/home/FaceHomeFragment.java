package com.lx.xqgg.face_ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Outline;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseFragment;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.face_ui.home.adapter.FaceProductAdapter;
import com.lx.xqgg.face_ui.home.bean.FaceProductBean;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.home.adapter.HomeBaseAdapter;
import com.lx.xqgg.ui.home.bean.BannerBean;
import com.lx.xqgg.ui.home.bean.HotMsgBean;
import com.lx.xqgg.ui.home.bean.MatterBean;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FaceHomeFragment extends BaseFragment {
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.layout_city)
    LinearLayout layoutCity;
    @BindView(R.id.xbanner)
    XBanner xbanner;
    @BindView(R.id.recycler_view_top)
    RecyclerView recyclerViewTop;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.recycler_view_2)
    RecyclerView recyclerView2;
    @BindView(R.id.tv_hot_more)
    TextView tvHotMore;
    @BindView(R.id.tv_product_more)
    TextView tvProductMore;

    //轮播图list
    private List<BannerBean> bannerBeanList = new ArrayList<>();
    private LocationClient mLocClient;
    private MyLocationListener myListener = new MyLocationListener();

    /**
     * 第一个rv
     **/
    private List<MatterBean> list1 = new ArrayList<>();
    private HomeBaseAdapter homeBaseAdapter1;

    private List<FaceProductBean> list2 = new ArrayList<>();
    private FaceProductAdapter faceProductAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_face;
    }

    @Override
    protected void initView() {
        recyclerViewTop.setLayoutManager(new GridLayoutManager(mContext, 3));
        homeBaseAdapter1 = new HomeBaseAdapter(R.layout.item_home_top, list1);
        recyclerViewTop.setAdapter(homeBaseAdapter1);
        homeBaseAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, TopListActivity.class);
                intent.putExtra("type", position);
                startActivity(intent);
            }
        });

        recyclerView2.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        faceProductAdapter = new FaceProductAdapter(list2);
        recyclerView2.setAdapter(faceProductAdapter);
        faceProductAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, BannerListActivity.class);
                intent.putExtra("data", list2.get(position));
                startActivity(intent);
            }
        });

        initBanner();
        initFlipper();
        initLocation();
        initProduct();
    }

    @Override
    protected void initData() {
        list1.add(new MatterBean("软件开发", "ChooseProductActivity", R.drawable.ic_rjkf));
        list1.add(new MatterBean("法律咨询", "MatchFirstActivity", R.drawable.ic_flzx));
        list1.add(new MatterBean("财务策划", "MatchFirstActivity", R.drawable.ic_cwch));
        homeBaseAdapter1.notifyDataSetChanged();
    }

    /**
     * 获取首页轮播图
     */
    private void initBanner() {
        xbanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                ImageView iv = view.findViewById(R.id.iv_pic);

                iv.setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setRect(0, 0, view.getWidth(), view.getHeight());
                    }
                });


                RoundedCorners roundedCorners = new RoundedCorners(1);
                RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(0, 0);

                Glide.with(mActivity)
                        .load(Config.IMGURL + bannerBeanList.get(position).getImage())
                        .apply(options.placeholder(R.drawable.ic_default))
                        .into(iv);
            }
        });

        addSubscribe(ApiManage.getInstance().getMainApi().getBanner(Constans.APPID, Constans.FACE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<BannerBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<BannerBean>> listBaseData) {
                        if (listBaseData.isSuccess()) {
                            bannerBeanList = listBaseData.getData();
                            xbanner.setBannerData(R.layout.item_banner, bannerBeanList);
                        } else {

                        }
                    }
                }));
    }

    /**
     * 获取表层产品
     */
    private void initProduct() {
        addSubscribe(ApiManage.getInstance().getMainApi().getFaceProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<FaceProductBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<FaceProductBean>> listBaseData) {
                        Log.e("zlz", new Gson().toJson(listBaseData));
                        if (listBaseData.isSuccess()) {
                            if (listBaseData.getData() != null) {
                                list2.clear();
                                list2.addAll(listBaseData.getData());
                                faceProductAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }));
    }

    /**
     * 初始化上下轮播新闻
     */
    private void initFlipper() {
        viewFlipper.setInAnimation(mContext, R.anim.notice_in);
        viewFlipper.setOutAnimation(mContext, R.anim.notice_out);

        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("title", "");
        paramsMap.put("appid", Constans.APPID);
        paramsMap.put("page", "1");
        paramsMap.put("pageSize", "5");
        paramsMap.put("type", Constans.FACE);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getHotNotice(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<HotMsgBean>(mContext, null) {
                    @Override
                    public void onNext(HotMsgBean hotMsgBean) {
                        Log.e("zlz", new Gson().toJson(hotMsgBean));
                        if (hotMsgBean.isIsSuccess()) {
                            if (hotMsgBean.getRecords() != null) {
                                List<HotMsgBean.RecordsBean> list = hotMsgBean.getRecords();
                                for (HotMsgBean.RecordsBean bean : list) {
                                    View v = ((Activity) mContext).getLayoutInflater().inflate(R.layout.item_news, null);
                                    TextView titleTv = (TextView) v.findViewById(R.id.tv_news_title);
                                    titleTv.setText(bean.getTitle() + "");
                                    viewFlipper.addView(v);
                                }
                                viewFlipper.startFlipping();
                            }
                        }
                    }
                }));

    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        mLocClient = new LocationClient(mContext.getApplicationContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @OnClick({R.id.tv_hot_more, R.id.tv_product_more, R.id.iv_qypx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_hot_more:
                startActivity(new Intent(mContext, FaceHotListActivity.class));
                break;
            case R.id.tv_product_more:
                break;
            case R.id.iv_qypx:
                startActivity(new Intent(mContext, CorporateTrainActivity.class));
                break;
        }
    }

    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            int errorCode = location.getLocType();
            if (location == null) {
                toast("定位失败");
            }
            tvCity.setText(location.getCity() + "");

            if(TextUtils.isEmpty(SharedPrefManager.getUser().getBio())) {
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("token", SharedPrefManager.getUser().getToken());
                paramsMap.put("city", location.getCity() + "");
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                addSubscribe(ApiManage.getInstance().getMainApi().updateUser(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseSubscriber<BaseData>(mContext, null) {
                            @Override
                            public void onNext(BaseData baseData) {

                            }
                        }));
            }
        }
    }
}
