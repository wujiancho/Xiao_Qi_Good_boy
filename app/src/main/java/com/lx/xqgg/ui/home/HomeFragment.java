package com.lx.xqgg.ui.home;

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
import com.lx.xqgg.event.ProductDetailEvent;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.city.CityPickerActivity;
import com.lx.xqgg.ui.city.bean.CityBean;
import com.lx.xqgg.ui.city.bean.CityHistoryBean;
import com.lx.xqgg.ui.home.adapter.CateAdapter;
import com.lx.xqgg.ui.home.adapter.HomeBaseAdapter;
import com.lx.xqgg.ui.home.adapter.TjcpAdapter;
import com.lx.xqgg.ui.home.bean.BannerBean;
import com.lx.xqgg.ui.home.bean.HotMsgBean;
import com.lx.xqgg.ui.home.bean.MatterBean;
import com.lx.xqgg.ui.hot.HotMsgListActivity;
import com.lx.xqgg.ui.message.MessageActivity;
import com.lx.xqgg.ui.person.bean.ProductDetailBean;
import com.lx.xqgg.ui.product.ProductActivity;
import com.lx.xqgg.ui.product.bean.CateBean;
import com.lx.xqgg.ui.product.bean.ProductBean;
import com.lx.xqgg.ui.search.SearchActivity;
import com.lx.xqgg.ui.vip.bean.PayListBean;
import com.lx.xqgg.ui.webview.WebViewActivity;
import com.lx.xqgg.util.Base64;
import com.stx.xhb.xbanner.XBanner;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.xbanner)
    XBanner xBanner;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.rv_1)
    RecyclerView recyclerView1;
    @BindView(R.id.rv_2)
    RecyclerView recyclerView2;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.layout_tjcp)
    LinearLayout layoutTjcp;
    @BindView(R.id.rv_tjcp)
    RecyclerView rvTjcp;
    @BindView(R.id.rv_xlgj)
    RecyclerView rvXlgj;
    @BindView(R.id.layout_city)
    LinearLayout layoutCity;
    @BindView(R.id.v_msg)
    View vMsg;
    @BindView(R.id.et_search)
    TextView etSearch;
    @BindView(R.id.v_hot)
    View vHot;

    //轮播图list
    private List<BannerBean> bannerBeanList;

    private LocationClient mLocClient;

    private static int SELECT_CITY_CODE = 1001;

    /**
     * 第一个rv
     **/
    private List<CateBean> list1 = new ArrayList<>();
    private CateAdapter cateAdapter;

    private List<MatterBean> list2;
    private HomeBaseAdapter homeBaseAdapter2;

    /**
     * 推荐产品
     */
    private List<ProductBean.RecordsBean> listTjcp;
    private TjcpAdapter tjcpAdapter;
    /**
     * 小麒工具
     **/
    private List<MatterBean> listXlgj;
    private HomeBaseAdapter xlgjAdapter;

    private MyLocationListener myListener = new MyLocationListener();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        list2 = new ArrayList<>();
        homeBaseAdapter2 = new HomeBaseAdapter(R.layout.item_home_pic, list2);
        recyclerView2.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView2.setAdapter(homeBaseAdapter2);
        recyclerView2.setNestedScrollingEnabled(false);
        homeBaseAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                try {
                    Intent intent = new Intent(list2.get(position).getAction());
                    startActivity(intent);
                } catch (Exception e) {
                    toast("功能暂未开放");
                }
            }
        });

        listTjcp = new ArrayList<>();
        tjcpAdapter = new TjcpAdapter(listTjcp);
        rvTjcp.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvTjcp.setAdapter(tjcpAdapter);
        rvTjcp.setNestedScrollingEnabled(false);
        tjcpAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            private ProductDetailBean productDetailBean;

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                /*Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("data", listTjcp.get(position).getId());
                startActivity(intent);*/
                //接受的数据生成jsonbean数据
                String userphone = SharedPrefManager.getUser().getMobile();
                int userid = listTjcp.get(position).getId();
                String image = listTjcp.get(position).getImage();
                String title = listTjcp.get(position).getTitle();
                int quota = listTjcp.get(position).getQuota();
                String rate = listTjcp.get(position).getRate();
               String  ed= SharedPrefManager.getImitationexamination().getPro_bal();
                String  fee= SharedPrefManager.getImitationexamination().getPro_fee();
                String count = ed+":" + (quota / 10000) + ",日"+fee+":" + rate;
                ProductDetailEvent event = new ProductDetailEvent();
                event.setImage(image);
                event.setTitle(title);
                event.setCount(count);
                EventBus.getDefault().postSticky(event);
                String cityname = Constans.CITY;

                //生成产品详细页的接口
                ArrayList<ProductDetailBean> gson2 = new ArrayList<>();
                productDetailBean = new ProductDetailBean();
                productDetailBean.setUserPhone(userphone);
                productDetailBean.setType("h5");
                productDetailBean.setId(userid + "");
                productDetailBean.setStatusHeight("30.000");
                productDetailBean.setCityName("");
                gson2.add(productDetailBean);
                String url2 = new Gson().toJson(gson2);
                String json2 = url2.substring(1, url2.length() - 1);
                //加密json
                Log.e("zlz", json2);
                String jiajson2 = Base64.encode(json2.getBytes());
                //生成产品详细页的接口
                String jiekong2 = Config.URL + "view/productDetails.html?bean=" + jiajson2;
                Constans.productDetails = jiekong2;

                ArrayList<ProductDetailBean> gson = new ArrayList<>();
                productDetailBean = new ProductDetailBean();
                productDetailBean.setUserPhone(userphone);
                productDetailBean.setCityName(cityname);
                productDetailBean.setType("app");
                productDetailBean.setId(userid + "");
                productDetailBean.setStatusHeight("30.000");
                gson.add(productDetailBean);
                String url = new Gson().toJson(gson);
                String json = url.substring(1, url.length() - 1);
                Log.e("zlz", json);
                //加密json
                String jiajson = Base64.encode(json.getBytes());

                String jiekong = Config.URL + "view/productDetails.html?bean=" + jiajson;
                Log.e("zlz", jiekong);

                if (!"".equals(jiekong2)) {
                    WebViewActivity.open(new WebViewActivity.Builder()
                            .setContext(mContext)
                            .setAutoTitle(false)
                            .setIsFwb(false)
                            .setTitle("产品详情")
                            .setNeedShare(true)
                            .setUrl(jiekong));
                }


            }

            // toast("跳转产品详细页H5");
//                WebViewActivity.open(new WebViewActivity.Builder()
//                        .setContext(mContext)
//                        .setAutoTitle(false)
//                        .setIsFwb(false)
//                        .setTitle("")
//                        .setUrl("http://xq.xhsqy.com/xiaoqiguaiguai-mobile/view/invloancuts.html?token="+SharedPrefManager.getUser().getToken()
//                        +"&city="+Constans.CITY+"&proTitle=測試過hi"));
//                Log.e("zlz",SharedPrefManager.getUser().getToken());
//                Log.e("zlz",Constans.CITY);
//                Log.e("zlz","測試過hi");
        });

        listXlgj = new ArrayList<>();
        xlgjAdapter = new HomeBaseAdapter(R.layout.item_xqgj, listXlgj);
        rvXlgj.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvXlgj.setAdapter(xlgjAdapter);
        rvXlgj.setNestedScrollingEnabled(false);
        xlgjAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MobclickAgent.onEvent(mContext, "click", listXlgj.get(position).getName() + "");
                if (listXlgj.get(position).getName().equals("征信网点")) {
                    WebViewActivity.open(new WebViewActivity.Builder()
                            .setContext(mContext)
                            .setAutoTitle(false)
                            .setIsFwb(false)
                            .setTitle("征信网点")
                            .setUrl(Config.ZXWDURL));
                    return;
                }
                if (listXlgj.get(position).getName().equals("人法网")) {
                    WebViewActivity.open(new WebViewActivity.Builder()
                            .setContext(mContext)
                            .setAutoTitle(false)
                            .setIsFwb(false)
                            .setTitle("人法网")
                            .setUrl(Config.RFWURL));
                    return;
                }
                try {
                    Intent intent = new Intent(listXlgj.get(position).getAction());
                    startActivity(intent);
                } catch (Exception e) {
                    toast("功能暂未开放");
                }
            }
        });

        initBanner();
        initFlipper();
        initLocation();
        initCate();
        initCharacter();
        getCityList();

//        getTestDeviceInfo(mContext);

    }

//    public static String[] getTestDeviceInfo(Context context){
//        String[] deviceInfo = new String[2];
//        try {
//            if(context != null){
//                deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
//                deviceInfo[1] = DeviceConfig.getMac(context);
//                Log.e("test",deviceInfo[0]);
//                Log.e("test",deviceInfo[1]);
//            }
//        } catch (Exception e){
//        }
//        return deviceInfo;
//    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            try {
                if (tvCity.getText().toString().equals(Constans.CITY)) {
                    return;
                }
                if (TextUtils.isEmpty(Constans.CITY)) {
                    return;
                }
                tvCity.setText(Constans.CITY);
                initCity();
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initMsg();
        try {
            if (TextUtils.isEmpty(tvCity.getText())) {
                return;
            }
            if (tvCity.getText().toString().equals(Constans.CITY)) {
                return;
            }

            tvCity.setText(Constans.CITY);
            initCity();
        } catch (Exception e) {
            Log.e("msg", e.toString());
        }

    }

    private void initMsg() {
        addSubscribe(ApiManage.getInstance().getMainApi().getUnReadNum(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<Integer>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<Integer> baseData) {
                        Log.e("msg", new Gson().toJson(baseData));
                        if (baseData.isSuccess()) {
                            if (baseData.getData() == 0) {
                                vMsg.setBackground(getResources().getDrawable(R.drawable.message));
                            } else {
                                vMsg.setBackground(getResources().getDrawable(R.drawable.message1));
                            }
                        } else {
                            vMsg.setBackground(getResources().getDrawable(R.drawable.message));
                        }
                    }
                }));
    }

    @Override
    protected void initData() {
        list2.add(new MatterBean("自选产品", "ChooseProductActivity", R.drawable.pic_zxcp));
        list2.add(new MatterBean("智能查询", "MatchFirstActivity", R.drawable.pic_zncx));
        homeBaseAdapter2.notifyDataSetChanged();

        listXlgj.add(new MatterBean("计算器", "CounterActivity", R.drawable.ic_jsq));
        listXlgj.add(new MatterBean("企查查", "QccMainSearchActivity", R.drawable.ic_qcc));
        listXlgj.add(new MatterBean("智能匹配", "MatchFirstActivity", R.drawable.ic_home_znpp));
        listXlgj.add(new MatterBean("征信网点", "", R.drawable.ic_zxwd));
        listXlgj.add(new MatterBean("人法网", "", R.drawable.icon_rfw));
        listXlgj.add(new MatterBean("小麒数据", "", R.drawable.ic_sj));
        xlgjAdapter.notifyDataSetChanged();
    }

    private void getCityList() {
        addSubscribe(ApiManage.getInstance().getMainApi().getCity("2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<CityBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<CityBean>> listBaseData) {
//                        Log.e("zlz", new Gson().toJson(listBaseData));
                        if (listBaseData.isSuccess()) {
                            SharedPrefManager.setCityList(listBaseData.getData());
                        }
                    }
                }));
    }

    @OnClick({R.id.layout_city, R.id.et_search, R.id.view_flipper, R.id.v_msg,R.id.v_hot})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.layout_city:
                startActivityForResult(new Intent(getContext(), CityPickerActivity.class), SELECT_CITY_CODE);
                break;
            case R.id.et_search:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.view_flipper:
                startActivity(new Intent(getContext(), HotMsgListActivity.class));
                break;
            case R.id.v_msg:
                startActivity(new Intent(getContext(), MessageActivity.class));
                break;
            case  R.id.v_hot:
                startActivity(new Intent(getContext(), HotMsgListActivity.class));
                break;
        }
    }

    /**
     * 初始化产品分类
     */
    private void initCate() {
        addSubscribe(ApiManage.getInstance().getMainApi().getCateList(Constans.APPID, Constans.INDOOR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<CateBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<CateBean>> listBaseData) {
                        if (listBaseData.isSuccess()) {
                            if (listBaseData.getData() != null) {
                                List<CateBean> allList = listBaseData.getData();
                                SharedPrefManager.setCate(allList);

                                for (int i = 0; i < allList.size(); i++) {
                                    if (i < 4) {
                                        list1.add(allList.get(i));
                                    }
                                }
                                list1.add(new CateBean(0, "", "更多"));
                                cateAdapter = new CateAdapter(list1);
                                recyclerView1.setLayoutManager(new GridLayoutManager(mContext, 5));
                                recyclerView1.setAdapter(cateAdapter);
                                cateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent(mContext, ProductActivity.class);
                                        int finalPostion = -1;
                                        if (position == (list1.size() - 1)) {
                                            finalPostion = 0;
                                        } else {
                                            finalPostion = position + 1;
                                        }
                                        intent.putExtra("postion", finalPostion);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }
                }));
    }

    /**
     * 初始化产品特点
     */
    private void initCharacter() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("group", "proFeature");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getPayList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<PayListBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<PayListBean>> listBaseData) {
                        Log.e("zlz", new Gson().toJson(listBaseData));
                        if (listBaseData.isSuccess()) {
                            if (listBaseData.getData() != null) {
                                SharedPrefManager.setCharacter(listBaseData.getData());
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

    /**
     * 获取推荐产品
     */
    private void getTjcp() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("title", "");
        paramsMap.put("classifyId", "");
        paramsMap.put("appid", Constans.APPID);
        paramsMap.put("loantype", "");
        paramsMap.put("guarantee", "");
        paramsMap.put("cityName", Constans.CITY);
//        paramsMap.put("orderby", "weigh");
//        paramsMap.put("orderway", "desc");
        paramsMap.put("orderby", "");
        paramsMap.put("orderway", "");
        paramsMap.put("page", 1);
        paramsMap.put("pageSize", 6);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getProductList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<ProductBean>(mContext, null) {
                    @Override
                    public void onNext(ProductBean productBean) {
                        Log.e("zlz", new Gson().toJson(productBean));
                        if (productBean.isIsSuccess()) {
                            if (listTjcp == null) {
                                listTjcp = new ArrayList<>();
                            }
                            listTjcp.clear();
                            listTjcp.addAll(productBean.getRecords());
                            tjcpAdapter.notifyDataSetChanged();
                        } else {
                            toast(productBean.getMessage() + "");
                        }
                    }
                }));
    }

    /**
     * 获取首页轮播图
     */
    private void initBanner() {
        xBanner.loadImage(new XBanner.XBannerAdapter() {
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

        addSubscribe(ApiManage.getInstance().getMainApi().getBanner(Constans.APPID, Constans.INDOOR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<BannerBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<BannerBean>> listBaseData) {
                        Log.e("xbnner", new Gson().toJson(listBaseData));
                        if (listBaseData.isSuccess()) {
                            bannerBeanList = listBaseData.getData();
                            xBanner.setBannerData(R.layout.item_banner, bannerBeanList);
                        } else {

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
        paramsMap.put("type", Constans.INDOOR);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getHotNotice(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<HotMsgBean>(mContext, null) {
                    @Override
                    public void onNext(HotMsgBean hotMsgBean) {
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
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            int errorCode = location.getLocType();
            if (location == null) {
                toast("定位失败");
                tvCity.setText("定位失败");
                return;
            }
            tvCity.setText(location.getCity() + "");
            Constans.CITY = location.getCity();
            Constans.PROVINCE = location.getProvince();

            Constans.GPSCITY = location.getCity();
            Constans.GPSPROVINCE = location.getProvince();

            List<CityHistoryBean> list = new ArrayList<>();
            list = SharedPrefManager.getCityHistory();

            if (location.getCity() == null || location.getProvince() == null) {
                return;
            }
            if (list == null) {
                list = new ArrayList<>();
                list.add(new CityHistoryBean(location.getProvince(), location.getCity()));
            } else {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getCity() != null) {
                        if (list.get(i).getCity().equals(location.getCity())) {
                            list.remove(list.get(i));
                            break;
                        }
                    }
                }
                list.add(0, new CityHistoryBean(location.getProvince(), location.getCity()));
            }

            if (list.size() > 3) {
                list.remove(list.get(list.size() - 1));
            }
            SharedPrefManager.setCityHistory(list);
            initCity();
        }
    }

    private void initCity() {
        getTjcp();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_CITY_CODE) {
            if (resultCode == RESULT_OK) {
                tvCity.setText(Constans.CITY);
                initCity();
            }
        }
    }
}
