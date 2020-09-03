package com.lx.xqgg.ui.mycommission;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.lx.xqgg.event.ServiseridurlEvent;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.adapter.VIpListAdapter;
import com.lx.xqgg.ui.mycommission.adapter.VipPackAdapter;
import com.lx.xqgg.ui.mycommission.bean.BuynowBean;
import com.lx.xqgg.ui.mycommission.bean.ListofinterestsBean;
import com.lx.xqgg.ui.mycommission.bean.ReturningservantBean;
import com.lx.xqgg.ui.mycommission.bean.SelectCommissionlevelBean;
import com.lx.xqgg.ui.mycommission.bean.SystemCommissionlevelBean;
import com.lx.xqgg.util.RoundedCornersTransformation;
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
    @BindView(R.id.vip_RecyclerViewstatusly)
    LinearLayout vipRecyclerViewstatusly;
    @BindView(R.id.check_tx)
    CheckBox checkTx;
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
    private String buyname1;
    private int rightsNum;
    private String endTime;
    private boolean checked;
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
        buyname1 = getIntent().getStringExtra("buyname1");
        imgurl = getIntent().getStringExtra("imgurl");
        rightsNum = getIntent().getIntExtra("rightsNum", 3);
        vipnameChange.setText(buyname1 + "服务包");
        icn = getIntent().getStringExtra("icn");
        positions = getIntent().getIntExtra("position", 0);
        endTime = getIntent().getStringExtra("endTime");
        if (!"".equals(id) && !"".equals(imgurl)) {
            selectCommissionlevel(id, imgurl, positions);
        }
        bugid = id;
        if (!"".equals(endTime) && endTime != null) {
            termOfValidity.setText("有效期：" + endTime);
        } else {
            termOfValidity.setVisibility(View.GONE);
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
        selectCommissionlevel(id, imgurl, positions);
        bugid = id;
        //jurisdictionCount.setText("当前" + vipname + "服务包解锁" + rightsNum + "项权益");
       /* if (!"".equals(endTime) && endTime != null) {
            termOfValidity.setText("有效期：" + endTime);
        } else {
            termOfValidity.setVisibility(View.GONE);
        }*/

    }

    @Override
    protected void initData() {

        buyname = getIntent().getStringExtra("buyname");
        Returningaservant();
        if (!"".equals(buyname) && buyname != null) {
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
        userName.setText(SharedPrefManager.getUserInfo().getUsername());//
        String servisername = SpUtil.getInstance().getSpString("servisername");
        if (!"".equals(servisername) && servisername != null) {
            usercompanyName.setText(servisername);
        }


        //获取权益列表
        // Listofinterests();

        vipcard();
    }

    @OnClick({R.id.vip_RecyclerViewstatus, R.id.guizhe1, R.id.guizhe2, R.id.btn_activate_now, R.id.toobar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vip_RecyclerViewstatus:
                break;
            case R.id.guizhe1:
                Intent intenxieyi = new Intent(BuyServicePackActivity.this, XieYiActivity.class);
                intenxieyi.putExtra("group", "serviceAgree");
                startActivity(intenxieyi);
                break;
            case R.id.guizhe2:
                Intent intenxieyi2 = new Intent(BuyServicePackActivity.this, XieYiActivity.class);
                intenxieyi2.putExtra("group", "rakebackRule");
                startActivity(intenxieyi2);
                break;
            case R.id.btn_activate_now:
               /* if(){

                }*/
                checked = checkTx.isChecked();
                if (checked == false) {
                    toast("请先勾选小麒乖乖服务包协议与返佣规则");
                    return;
                }
                //立即开通
                activatenow(bugid);
                break;
            case R.id.toobar_back:
                finish();
                break;
        }
    }

    //根据选择佣金等级计算服务商当月佣金
    private void selectCommissionlevel(int id, String imgurl, int positionss) {
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
                            // 圆角图片 new RoundedCornersTransformation 参数为 ：半径 , 外边距 , 圆角方向(ALL,BOTTOM,TOP,RIGHT,LEFT,BOTTOM_LEFT等等)
                            //顶部左边圆角
                            RoundedCornersTransformation transformation = new RoundedCornersTransformation
                                    (20, 0, RoundedCornersTransformation.CornerType.TOP_LEFT);
                            //顶部右边圆角
                            RoundedCornersTransformation transformation1 = new RoundedCornersTransformation
                                    (20, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT);

                            //组合各种Transformation,
                            MultiTransformation<Bitmap> mation = new MultiTransformation<>
                                    //Glide设置圆角图片后设置ImageVIew的scanType="centerCrop"无效解决办法,将new CenterCrop()添加至此
                                    (new CenterCrop(), transformation, transformation1);
                            Glide.with(mContext)
                                    .load(Config.IMGURL + imgurl)
                                    .apply(RequestOptions.bitmapTransform(mation))
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
                            vipPackAdapter = new VipPackAdapter(systemCommissionlevel, mContext, positions);
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
                                    addviplist(listBaseData, position);
                                    if (systemCommissionlevel.get(position).isBuy() == true) {
                                        btnActivateNow.setVisibility(View.GONE);
                                    } else {
                                        btnActivateNow.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }

                        addviplist(listBaseData, positions);
                        if (systemCommissionlevel.get(positions).isBuy() == true) {
                            btnActivateNow.setVisibility(View.GONE);
                        } else {
                            btnActivateNow.setVisibility(View.VISIBLE);
                        }
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

    private void addviplist(BaseData<List<SystemCommissionlevelBean>> data, int position) {
        jurisdictionCount.setText("当前" + data.getData().get(position).getName() + "服务包解锁" + data.getData().get(position).getRightsNum() + "项权益");
        viplist = new ArrayList<>();
        viplist.clear();
        viplist.addAll(data.getData().get(position).getRights());
        //设置vip权限服务列表
        vIpListAdapter = new VIpListAdapter(viplist);
        vipRecyclerView.setAdapter(vIpListAdapter);
        vipRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        vIpListAdapter.statusvip(4);
        vIpListAdapter.notifyDataSetChanged();
        vipRecyclerViewstatusly.setOnClickListener(new View.OnClickListener() {
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
                            builder1.setMessage("您已申请线下购买，小麒乖乖处理中，会有渠道人员联系您开通服务包申请，请耐心等待");
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



}
