package com.lx.xqgg.ui.mycommission;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.event.ServiseridurlEvent;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.adapter.VIpListAdapter;
import com.lx.xqgg.ui.mycommission.adapter.VipPackAdapter;
import com.lx.xqgg.ui.mycommission.bean.BuynowBean;
import com.lx.xqgg.ui.mycommission.bean.ListofinterestsBean;
import com.lx.xqgg.ui.mycommission.bean.ReturningservantBean;
import com.lx.xqgg.ui.mycommission.bean.SelectCommissionlevelBean;
import com.lx.xqgg.ui.mycommission.bean.SystemCommissionlevelBean;
import com.lx.xqgg.ui.vip.bean.PayListBean;
import com.lx.xqgg.util.SpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.MediaType;
import okhttp3.RequestBody;

//购买服务包
public class BuyServicePackActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.usercompany_name)
    TextView usercompanyName;
    @BindView(R.id.accumulated_points)
    TextView accumulatedPoints;
    @BindView(R.id.term_of_validity)
    TextView termOfValidity;
    @BindView(R.id.vip_packRecyclerView)
    RecyclerView vipPackRecyclerView;
    @BindView(R.id.jurisdiction_count)
    TextView jurisdictionCount;
    @BindView(R.id.vip_RecyclerViewstatus)
    TextView vipRecyclerViewstatus;
    @BindView(R.id.stuart_img)
    ImageView stuartImg;
    @BindView(R.id.vip_RecyclerView)
    RecyclerView vipRecyclerView;
    @BindView(R.id.guizhe1)
    TextView guizhe1;
    @BindView(R.id.guizhe2)
    TextView guizhe2;
    @BindView(R.id.btn_activate_now)
    Button btnActivateNow;
    @BindView(R.id.vipselectbg)
    ImageView vipselectbg;
    @BindView(R.id.vip_commissionno)
    TextView vipCommissionno;
    @BindView(R.id.vip_commissioncountno)
    TextView vipCommissioncountno;
    @BindView(R.id.vipname_change)
    TextView vipnameChange;
    @BindView(R.id.vip_commissioncountchamge)
    TextView vipCommissioncountchamge;
    @BindView(R.id.welfare)
    TextView welfare;
    @BindView(R.id.user_viplogo)
    ImageView userViplogo;
    private String token;
    private VIpListAdapter vIpListAdapter;
    private List<SystemCommissionlevelBean.RightsBean> viplist;
    private List<SystemCommissionlevelBean> systemCommissionlevel;
    private VipPackAdapter vipPackAdapter;
    private String buyname;
    private int bugid;
    private int positions;
    private int id;
    private String imgurl;
    private String icn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_service_pack;
}

    @Override
    protected void initView() {
        toobarTitle.setText("购买服务包");
        //eventbus注册
        EventBus.getDefault().register(this);
        //获取用户token
        token = SharedPrefManager.getUser().getToken();
        //默认
        id = getIntent().getIntExtra("id", 24);
        imgurl = getIntent().getStringExtra("imgurl");
        icn = getIntent().getStringExtra("icn");
        if (!"".equals(id) || !"".equals(imgurl)) {
            selectCommissionlevel(id, imgurl);
        }

    }

    //获取eventbus参数
    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void ServiseridurlEvent(ServiseridurlEvent event) {
        int id = event.getId();
        String imgurl = event.getImgurl();
        String vipname = event.getVipname();
        String endTime = event.getEndTime();
        int rightsNum = event.getRightsNum();
        int position = event.getPosition();
        vipnameChange.setText(vipname + "服务包");
        positions = position;
        //根据选择佣金等级计算服务商当月佣金
        selectCommissionlevel(id, imgurl);
        bugid = id;
        jurisdictionCount.setText("当前" + vipname + "服务包可以解锁" + rightsNum + "个权限");
        if (!"".equals(endTime)||endTime!=null){
            termOfValidity.setText("有效期：" + endTime);
        }
       else {
            termOfValidity.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initData() {

        buyname = getIntent().getStringExtra("buyname");
        if (!"".equals(buyname)) {
            vipCommissionno.setText("当前用户等级：" + buyname);
        }
        String returningservantdata = SpUtil.getInstance().getSpString("returningservantdata");
        if (!"".equals(returningservantdata)) {
            ReturningservantBean returningservantBean = new Gson().fromJson(returningservantdata, ReturningservantBean.class);
            int allRebate = returningservantBean.getAllCharge();
            DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
            String allRebatez = df.format(allRebate);
            accumulatedPoints.setText("小麒乖乖已累计为您返佣" + allRebatez + "积分");
        }

        //获取用户信息
        userName.setText(SharedPrefManager.getUserInfo().getUsername());
        String servisername = SpUtil.getInstance().getSpString("servisername");
        if (!"".equals(servisername)) {
            usercompanyName.setText(servisername);
        }


        vipcard();

        //获取权益列表
        // Listofinterests();


    }

    @OnClick({R.id.vip_RecyclerViewstatus, R.id.guizhe1, R.id.guizhe2, R.id.btn_activate_now, R.id.toobar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vip_RecyclerViewstatus:
                break;
            case R.id.guizhe1:
                initCharacter("buyAgreement");
                break;
            case R.id.guizhe2:
                initCharacter("buyRule");
                break;
            case R.id.btn_activate_now:
               /* if(){

                }*/
                //立即开通
                activatenow(bugid);
                break;
            case R.id.toobar_back:
                finish();
                break;
        }
    }

    //根据选择佣金等级计算服务商当月佣金
    private void selectCommissionlevel(int id, String imgurl) {
        addSubscribe(ApiManage.getInstance().getMainApi().getSelectCommissionlevel(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<SelectCommissionlevelBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<SelectCommissionlevelBean> selectCommissionlevelBeanBaseData) {
                        SelectCommissionlevelBean data = selectCommissionlevelBeanBaseData.getData();
                        if (selectCommissionlevelBeanBaseData.isSuccess()) {
                            int newCharge = data.getNewCharge();//计算出的新佣金
                            int oldCharge = data.getOldCharge();//之前的佣金
                            String promote = data.getPromote();//提升的比例
                            int rightsNum = data.getRightsNum();//新的等级对应的权益个数
                            DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
                            String newChargez = df.format(newCharge);
                            String oldChargez = df.format(oldCharge);
                            vipCommissioncountno.setText(oldChargez);
                            vipCommissioncountchamge.setText(newChargez);
                            Glide.with(mContext)
                                    .load(Config.IMGURL + imgurl)
                                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                                    .into(vipselectbg);
                            welfare.setText("佣金至少提升" + promote + "%,获得额外" + rightsNum + "项权益");
                        }
                    }
                }));
    }

    //vip卡片
    private void vipcard() {
        systemCommissionlevel = new ArrayList<>();
        addSubscribe(ApiManage.getInstance().getMainApi().getSystemCommissionlevel(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<SystemCommissionlevelBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<SystemCommissionlevelBean>> listBaseData) {
                        if (listBaseData.isSuccess()) {
                            systemCommissionlevel.addAll(listBaseData.getData());
                            vipPackAdapter = new VipPackAdapter(systemCommissionlevel, mContext);
                            vipPackRecyclerView.setAdapter(vipPackAdapter);
                            vipPackRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
                            Glide.with(mContext)
                                    .load(Config.IMGURL + icn)
                                    .into(userViplogo);
                            vipPackAdapter.setGetListener(new VipPackAdapter.GetListener() {
                                @Override
                                public void onClick(int position) {
                                    Glide.with(mContext)
                                            .load(Config.IMGURL + listBaseData.getData().get(position).getIco())
                                            .into(userViplogo);
//                                 把点击的下标回传给适配器 确定下标
                                    vipPackAdapter.setmPosition(position);
                                    vipPackAdapter.notifyDataSetChanged();
                                    viplist.clear();
                                    viplist.addAll(listBaseData.getData().get(position).getRights());
                                }
                            });
                        }
                        viplist = new ArrayList<>();
                        viplist.clear();
                        viplist.addAll(listBaseData.getData().get(positions).getRights());
                        //设置vip权限服务列表
                        vIpListAdapter = new VIpListAdapter(viplist);
                        vipRecyclerView.setAdapter(vIpListAdapter);
                        vipRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                        vIpListAdapter.statusvip(4);
                        vIpListAdapter.notifyDataSetChanged();
                        vipRecyclerViewstatus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ("展开".equals(vipRecyclerViewstatus.getText().toString())) {
                                    vIpListAdapter.statusvip(viplist.size());
                                    vIpListAdapter.notifyDataSetChanged();
                                    vipRecyclerViewstatus.setText("收起");
                                    stuartImg.setImageResource(R.drawable.sla);
                                } else if ("收起".equals(vipRecyclerViewstatus.getText().toString())) {
                                    vIpListAdapter.statusvip(4);
                                    vIpListAdapter.notifyDataSetChanged();
                                    vipRecyclerViewstatus.setText("展开");
                                    stuartImg.setImageResource(R.drawable.xla);
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable t) {
                        toast(t.getMessage());
                        super.onError(t);
                    }
                }));

    }

    //获取权益列表
    private void Listofinterests() {
        ApiManage.getInstance().getMainApi().getListofinterests()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<ListofinterestsBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<ListofinterestsBean>> listBaseData) {

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Log.e("zlz", t.toString());
                    }
                });

    }

    //立即开通
    private void activatenow(int id) {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("configId", id);
        Log.d("mydata", "activatenow: " + id + token);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        ApiManage.getInstance().getMainApi().getBuynow(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<BuynowBean>() {
                    @Override
                    public void onNext(BuynowBean buynowBean) {
                        if (buynowBean.isSuccess()) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(BuyServicePackActivity.this);
                            builder1.setMessage("已申请线下购买，小麒乖乖处理中，会有渠道人员联系您开通服务包申请，请耐心等待");
                            builder1.setTitle("温馨提示");
                            builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                            builder1.show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        toast(t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    //返佣方法
    public void Returningaservant() {
        addSubscribe(ApiManage.getInstance().getMainApi().getReturningservant(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<ReturningservantBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<ReturningservantBean> returningservantBeanBaseData) {
                        if (returningservantBeanBaseData.isSuccess()) {
                            ReturningservantBean data = returningservantBeanBaseData.getData();
                            int allRebate = data.getAllCharge();
                            int cashRebate = data.getCashCharge();
                            int thismothRebate = data.getCurrentMonthCharge();
                            DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
                            String allRebatez = df.format(allRebate);//累计返佣
                            String cashRebatez = df.format(cashRebate);//可提返佣
                            String thismothRebatez = df.format(thismothRebate);//本月返佣
                            accumulatedPoints.setText("小麒乖乖已累计为您返佣" + allRebatez + "积分");
                            SpUtil.getInstance().saveString("returningservantdata", new Gson().toJson(returningservantBeanBaseData.getData()));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.getMessage());
                    }
                }));
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    private void initCharacter(String name) {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("group",name);
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
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(BuyServicePackActivity.this);
                                builder1.setMessage(listBaseData.getData().get(0).getValue());
                                builder1.setTitle(listBaseData.getData().get(0).getName());
                                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder1.show();
                            }
                        }
                    }
                }));
    }
}
