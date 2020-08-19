package com.lx.xqgg.ui.person;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseFragment;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.company_auth.CompanyAuthActivity;
import com.lx.xqgg.ui.home.adapter.HomeBaseAdapter;
import com.lx.xqgg.ui.home.bean.MatterBean;
import com.lx.xqgg.ui.home.bean.UserServiceBean;
import com.lx.xqgg.ui.login.bean.UserInfoBean;
import com.lx.xqgg.ui.login.bean.crmLoginBean;
import com.lx.xqgg.ui.setting.SettingActivity;
import com.lx.xqgg.ui.vip.VipActivity;
import com.lx.xqgg.ui.vip.bean.PayListBean;
import com.lx.xqgg.ui.webview.WebViewActivity;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PersonFragment extends BaseFragment {
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_vip_time)
    TextView tvVipTime;
    @BindView(R.id.rv_wdxx)
    RecyclerView rvWdxx;
    @BindView(R.id.rv_wdfw)
    RecyclerView rvWdfw;
    @BindView(R.id.rv_xqgj)
    RecyclerView rvXqgj;
    @BindView(R.id.tv_setting)
    LinearLayout tvSetting;
    @BindView(R.id.tv_vip_msg)
    TextView tvVipMsg;

    private HomeBaseAdapter homeBaseAdapter1;
    private List<MatterBean> list1;
    private HomeBaseAdapter homeBaseAdapter2;
    private List<MatterBean> list2;
    private HomeBaseAdapter homeBaseAdapter3;
    private List<MatterBean> list3;
    //vip code
    private static final int VIP_RESLUT_CODE = 10086;
    private String dai;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_person;
    }

    @Override
    protected void initView() {
        dai = SharedPrefManager.getImitationexamination().getPro_dai();
        Log.d("qqqq", "initView: "+dai);
        list1 = new ArrayList<>();
        homeBaseAdapter1 = new HomeBaseAdapter(R.layout.item_person, list1);
        rvWdxx.setLayoutManager(new GridLayoutManager(mContext, 4));
        rvWdxx.setAdapter(homeBaseAdapter1);

        homeBaseAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(list1.get(position).getName().equals("服务商信息")){
                    HashMap<String, Object> paramsMap = new HashMap<>();
                    paramsMap.put("token", SharedPrefManager.getUser().getToken());
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                    addSubscribe(ApiManage.getInstance().getMainApi().getUserServiceInfo(body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new BaseSubscriber<BaseData<UserServiceBean>>(mContext, null) {
                                @Override
                                public void onNext(BaseData<UserServiceBean> objectBaseData) {
                                    Log.e("zlz", new Gson().toJson(objectBaseData));
                                    Intent intent;
                                    if (objectBaseData.isSuccess()) {
                                        switch (objectBaseData.getCode()) {
                                            case 1:
                                                //服务商认证通过
                                                intent = new Intent(getActivity(), ServiceInfoActivity.class);
                                                startActivity(intent);
                                                break;
                                            case 0:
                                                //不是服务商
                                                toast(objectBaseData.getMessage()+"");
                                                intent = new Intent(getActivity(), CompanyAuthActivity.class);
                                                startActivity(intent);
                                                break;
                                            case -1:
                                                //正在审核
                                                toast(objectBaseData.getMessage()+"");
//                                                intent = new Intent(getActivity(), ServiceInfoActivity.class);
//                                                startActivity(intent);
                                                break;
                                            case -2:
                                                //审核被拒
                                                toast(objectBaseData.getMessage()+"");
                                                intent = new Intent(getActivity(), CompanyAuthActivity.class);
                                                startActivity(intent);
                                                break;
                                            case -3:
                                                //vip时间到期
                                                toast(objectBaseData.getMessage()+"");
                                                intent = new Intent(getActivity(), ServiceInfoActivity.class);
                                                startActivity(intent);
                                                break;
                                            case -4:
                                                //不是vip
                                                toast(objectBaseData.getMessage()+"");
                                                intent = new Intent(getActivity(), ServiceInfoActivity.class);
                                                startActivity(intent);
                                                break;
                                            case -5:
                                                //未登录
                                                toast(objectBaseData.getMessage()+"");
                                                break;
                                            case -6:
                                                //未找到服务商
                                                toast(objectBaseData.getMessage()+"");
                                                break;
                                        }
                                    } else {
                                        toast(objectBaseData.getMessage());
                                    }
                                }

                                @Override
                                public void onError(Throwable t) {
                                    super.onError(t);
                                    toast(t.toString());
                                }
                            }));
                }else {
                    try {
                        Intent intent = new Intent(list1.get(position).getAction());
                        startActivity(intent);
                    }
                    catch (Exception e) {
                        toast("功能暂未开放");
                    }
                }
            }
        });

        list2 = new ArrayList<>();
        homeBaseAdapter2 = new HomeBaseAdapter(R.layout.item_person, list2);
        rvWdfw.setLayoutManager(new GridLayoutManager(mContext, 4));
        rvWdfw.setAdapter(homeBaseAdapter2);
        homeBaseAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list2.get(position).getName().equals("在线客服")) {
                    String title = "聊天窗口的标题";
                    ConsultSource source = new ConsultSource("", "个人中心", "custom information string");
                    Unicorn.openServiceActivity(mContext, title, source);
                    return;
                }
                if (list2.get(position).getName().equals("电话咨询")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage("是否拨打客服电话：4001391717");
                    builder1.setTitle("温馨提示");
                    builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mIntent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:4001391717");
                            mIntent.setData(data);
                            //Android6.0以后的动态获取打电话权限
                            startActivity(mIntent);
                        }
                    });
                    builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder1.show();
                    return;
                }
                if (list2.get(position).getName().equals("CRM")) {
                    HashMap<String, Object> paramsMap = new HashMap<>();
                    paramsMap.put("mobile", SharedPrefManager.getUserInfo().getMobile());
                    paramsMap.put("identity", "app");
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                    ApiManage.getInstance().getMainApi().crmlogin(Config.URL+"crm/login",body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new BaseSubscriber<BaseData<crmLoginBean>>(mContext,null) {
                                @Override
                                public void onNext(BaseData<crmLoginBean> crmLoginbean) {
                                    if (crmLoginbean.isSuccess()) {
                                            WebViewActivity.open(new WebViewActivity.Builder()
                                                    .setContext(mContext)
                                                    .setAutoTitle(false)
                                                    .setIsFwb(false)
                                                    //.setUrl(Config.CRMURL+ "?token=" +crmLoginbean.getData().getToken()+ "&identity=app&statusHeight=30"));
                                                     .setUrl(Config.CRMURLS + "?token=" +crmLoginbean.getData().getToken()+ "&identity=app&statusHeight=44"));

                                    }
                                    else {
                                        toast(crmLoginbean.getMessage());
                                    }
                                }
                                @Override
                                public void onError(Throwable t) {
                                    super.onError(t);
                                    toast(t.toString());
                                }
                            });
                    return;
                }
                try {
                    Intent intent = new Intent(list2.get(position).getAction());
                            startActivity(intent);
                } catch (Exception e) {
                    toast("功能暂未开放");
                }
            }
        });

        list3 = new ArrayList<>();
        homeBaseAdapter3 = new HomeBaseAdapter(R.layout.item_person, list3);
        rvXqgj.setLayoutManager(new GridLayoutManager(mContext, 4));
        rvXqgj.setAdapter(homeBaseAdapter3);
        homeBaseAdapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MobclickAgent.onEvent(mContext, "click", list3.get(position).getName()+"");
                if (list3.get(position).getName().equals("征信网点")) {
                    WebViewActivity.open(new WebViewActivity.Builder()
                            .setContext(mContext)
                            .setAutoTitle(false)
                            .setIsFwb(false)
                            .setTitle("征信网点")
                            .setUrl(Config.ZXWDURL));
                    return;
                }
                if (list3.get(position).getName().equals("人法网")) {
                    WebViewActivity.open(new WebViewActivity.Builder()
                            .setContext(mContext)
                            .setAutoTitle(false)
                            .setIsFwb(false)
                            .setTitle("人法网")
                            .setUrl(Config.RFWURL));
                    return;
                }
                if (list3.get(position).getName().equals("税务局")) {
                    addSubscribe(ApiManage.getInstance().getMainApi().getTaxUrl(Constans.PROVINCE)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new BaseSubscriber<BaseData<String>>(mContext, null) {
                                @Override
                                public void onNext(BaseData<String> stringBaseData) {
                                    Log.e("zlz",stringBaseData.getData());
                                        if (stringBaseData.isSuccess()) {
                                        WebViewActivity.open(new WebViewActivity.Builder()
                                                .setContext(mContext)
                                                .setAutoTitle(false)
                                                .setIsFwb(false)
                                                .setTitle("税务局")
                                                .setUrl(stringBaseData.getData()));
                                    }
                                }
                            }));
                    return;
                }
                try {
                    Intent intent = new Intent(list3.get(position).getAction());
                    startActivity(intent);
                } catch (Exception e) {
                    toast("功能暂未开放");
                }
            }
        });
        tvPhone.setText(SharedPrefManager.getUser().getUsername() + "");
        initUserInfo();
    }

    private void initUserInfo() {
        ApiManage.getInstance().getMainApi().getUserInfo(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<UserInfoBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<UserInfoBean> userInfoBeanBaseData) {
                        Log.e("userinfo", new Gson().toJson(userInfoBeanBaseData));
                        if (userInfoBeanBaseData.isSuccess()) {
                            SharedPrefManager.setUserInfo(userInfoBeanBaseData.getData());
                            refreshUI();
                            boolean crmUser =new Boolean(new Gson().toJson(SharedPrefManager.getUserInfo().isCrmUser()));
                            Log.d("crmUser", "onNext:=== "+crmUser);
                            if (crmUser){
                                list2.add(new MatterBean("CRM", "", R.drawable.crm));
                                homeBaseAdapter2.notifyDataSetChanged();
                            }
                         /*   boolean legal=new Boolean(new Gson().toJson(SharedPrefManager.getUserInfo().isLegal()));
                            if (legal){
                                list1.add(new MatterBean("业绩返佣", "MycommissionActivity", R.drawable.yongjin));
                                homeBaseAdapter1.notifyDataSetChanged();
                            }*/
                        }else {
                            toast(userInfoBeanBaseData.getMessage());
                        }
                    }
                });
    }

    private void refreshUI() {
        if (SharedPrefManager.getUserInfo().getIsVip() == 1) {
            tvVipTime.setText("VIP时间:" + SharedPrefManager.getUserInfo().getEndtime().substring(0, SharedPrefManager.getUserInfo().getEndtime().indexOf(" ")));
        } else {
            tvVipTime.setVisibility(View.INVISIBLE);
        }
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("group", "vipPrice");

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getPayList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<PayListBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<PayListBean>> listBaseData) {
                        Log.e("zlz", new Gson().toJson(listBaseData));
                        if (listBaseData.isSuccess()) {
                            tvVipMsg.setText(listBaseData.getData().get(0).getValue()+"元开通VIP");
                        }
                    }
                }));
    }

    @Override
    protected void initData() {
        list1.add(new MatterBean("服务商信息", "ServiceInfoActivity", R.drawable.ic_p_fws));
        list1.add(new MatterBean("我的客户", "MyClientActivity", R.drawable.ic_p_wdkh));
        list1.add(new MatterBean("推荐有礼", "ShareFaceActivity", R.drawable.ic_p_tjyl));
        //list1.add(new MatterBean("积分查询", "", R.drawable.ic_p_jfcx));//IntegralQueryActivity
       // list1.add(new MatterBean("我的拓客", "", R.drawable.tuoke));//MyTuokeActivity
       // list1.add(new MatterBean("业绩返佣", "MycommissionActivity", R.drawable.yongjin));
        homeBaseAdapter1.notifyDataSetChanged();

//
//
//        if (SharedPrefManager.getServiceInfo() == null || SharedPrefManager.getServiceInfo().getCode() == 0 || SharedPrefManager.getServiceInfo().getCode() == -1
//                || SharedPrefManager.getServiceInfo().getCode() == -2) {
//
//        } else {
//            list1.add(new MatterBean("服务商信息", "ServiceInfoActivity", R.drawable.ic_p_fwsxx));
//            list1.add(new MatterBean("我的客户", "MyClientActivity", R.drawable.ic_p_wdkh));
//            list1.add(new MatterBean("推荐有礼", "", R.drawable.ic_p_tjyl));
//            list1.add(new MatterBean("积分查询", "", R.drawable.ic_p_jfcx));
//            homeBaseAdapter1.notifyDataSetChanged();
//        }

        list2.add(new MatterBean("在线客服", "", R.drawable.ic_p_zxkf));
        list2.add(new MatterBean("电话咨询", "", R.drawable.ic_p_dhzx));
        list2.add(new MatterBean("帮助中心", "HelperActivity", R.drawable.ic_p_bzxx));
        list2.add(new MatterBean("匹配结果", "MatchSavedActivity", R.drawable.ic_p_ppjg));
        //list2.add(new MatterBean("CRM", "", R.drawable.crm));
       // list2.add(new MatterBean("我的购买", "", R.drawable.mygm));
       // list2.add(new MatterBean("我的收藏", "", R.drawable.mysc));
        homeBaseAdapter2.notifyDataSetChanged();

        list3.add(new MatterBean("智能匹配", "MatchFirstActivity", R.drawable.ic_znpp));
        list3.add(new MatterBean("小麒数据", "", R.drawable.ic_p_xqsj));
        list3.add(new MatterBean("企查查", "QccMainSearchActivity", R.drawable.ic_p_qcc));
        list3.add(new MatterBean("计算器", "CounterActivity", R.drawable.ic_p_jsj));
        list3.add(new MatterBean("征信网点", "", R.drawable.ic_p_zxwd));
        list3.add(new MatterBean("企业培训", "CorporateTrainActivity", R.drawable.ic_p_qypx));
        list3.add(new MatterBean("人法网", "", R.drawable.ic_p_rfw));
        list3.add(new MatterBean("税务局", "", R.drawable.ic_p_swj));
       // list3.add(new MatterBean("助"+dai+"学院", "LACollegeActivity", R.drawable.ic_p_zdxy));

        homeBaseAdapter3.notifyDataSetChanged();
    }

    @OnClick({R.id.circleImageView, R.id.tv_setting, R.id.iv_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.circleImageView:
                break;
            case R.id.tv_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.iv_vip:
                startActivityForResult(new Intent(mContext, VipActivity.class), VIP_RESLUT_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIP_RESLUT_CODE) {
            if (resultCode == RESULT_OK) {
                initUserInfo();
            }
        }
    }
}
