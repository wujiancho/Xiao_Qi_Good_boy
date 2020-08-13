package com.lx.xqgg.ui.mycommission;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.adapter.VIpListAdapter;
import com.lx.xqgg.ui.mycommission.bean.CommissionlevelBean;
import com.lx.xqgg.ui.mycommission.bean.ListofinterestsBean;
import com.lx.xqgg.ui.mycommission.bean.ReturningservantBean;
import com.lx.xqgg.ui.mycommission.bean.SystemCommissionlevelBean;
import com.lx.xqgg.util.SpUtil;
import com.stx.xhb.xbanner.XBanner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MycommissionActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.xbanner_vip)
    XBanner xbannerVip;
    @BindView(R.id.vip_RecyclerView)
    RecyclerView vipRecyclerView;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.usercompany_name)
    TextView usercompanyName;
    @BindView(R.id.accumulated_points)
    TextView accumulatedPoints;
    @BindView(R.id.cash_withdrawal_rebate)
    TextView cashWithdrawalRebate;
    @BindView(R.id.cash_withdrawal_rebatez)
    LinearLayout cashWithdrawalRebatez;
    @BindView(R.id.this_monthcommission)
    TextView thisMonthcommission;
    @BindView(R.id.this_monthcommissionz)
    LinearLayout thisMonthcommissionz;
    @BindView(R.id.accumulated_rebate)
    TextView accumulatedRebate;
    @BindView(R.id.accumulated_rebatez)
    LinearLayout accumulatedRebatez;
    @BindView(R.id.vip_name)
    TextView vipName;
    @BindView(R.id.progress_bar_h)
    ProgressBar progressBarH;
    @BindView(R.id.vip_count)
    TextView vipCount;
    @BindView(R.id.jurisdiction_count)
    TextView jurisdictionCount;
    @BindView(R.id.vip_RecyclerViewstatus)
    TextView vipRecyclerViewstatus;
    @BindView(R.id.stuart_img)
    ImageView stuartImg;
    @BindView(R.id.myjurisdiction)
    ConstraintLayout myjurisdiction;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.xqgg_gl)
    ConstraintLayout xqggGl;
    @BindView(R.id.logo2)
    ImageView logo2;
    @BindView(R.id.xqgg_fy)
    ConstraintLayout xqggFy;
    @BindView(R.id.vip_name_qy)
    TextView vipNameQy;
    @BindView(R.id.vipbg)
    ImageView vipbg;
    private VIpListAdapter vIpListAdapter;
    private List<ListofinterestsBean> viplist;
    private List<SystemCommissionlevelBean> systemCommissionlevel;
    private String token;
    private String currentLevel;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_mycommission;
    }

    @Override
    protected void initView() {
        toobarTitle.setText("业绩返佣服务包");
    }

    @Override
    protected void initData() {
        //获取用户信息
        userName.setText(SharedPrefManager.getUserInfo().getUsername());
       String servisername= SpUtil.getInstance().getSpString("servisername");
       if (!"".equals(servisername)){
           usercompanyName.setText(servisername);
       }
        //获取用户token
        token = SharedPrefManager.getUser().getToken();
        //获取用户的可提返佣-本月返佣-累计返佣
        Returningaservant();
        //获取用户当前的vip等级
        Userlevel();
        //vip卡片
        vipcard();
        jurisdictionCount.setText("当前黄金服务包可以解锁4个权限");
        //联动改变权限
        xbannerVip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    // jurisdictionCount.setText("当前"+xbannerdata.get(0).getVipname()+"可以解锁4个权限");
                }
                if (position == 1) {
                    // jurisdictionCount.setText("当前"+xbannerdata.get(1).getVipname()+"可以解锁8个权限");
                }
                if (position == 2) {
                    //  jurisdictionCount.setText("当前"+xbannerdata.get(2).getVipname()+"可以解锁12个权限");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //获取权益列表
        Listofinterests();
    }

    @OnClick({R.id.cash_withdrawal_rebatez, R.id.this_monthcommissionz, R.id.accumulated_rebatez, R.id.vip_RecyclerViewstatus, R.id.myjurisdiction, R.id.xqgg_gl, R.id.xqgg_fy, R.id.toobar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //跳转可提现返佣
            case R.id.cash_withdrawal_rebatez:
                Intent intentcashwr = new Intent(MycommissionActivity.this, CashWithdrawalRebateActivity.class);
                startActivity(intentcashwr);
                break;
            //跳转本月返佣
            case R.id.this_monthcommissionz:
                Intent intenthistmr = new Intent(MycommissionActivity.this, ThisMonthReturnActivity.class);
                startActivity(intenthistmr);
                break;
            //跳转累计返佣
            case R.id.accumulated_rebatez:
                Intent intentacclr = new Intent(MycommissionActivity.this, AccumulatedRebateActivity.class);
                startActivity(intentacclr);
                break;
            //vip状态展开点击事件
            case R.id.vip_RecyclerViewstatus:
                break;
            //我的权益
            case R.id.myjurisdiction:
                toast("功能暂未开放");
                break;
            //小麒乖乖的攻略
            case R.id.xqgg_gl:
                toast("功能暂未开放");
                break;
            //小麒乖乖的返佣
            case R.id.xqgg_fy:
                toast("功能暂未开放");
                break;
            case R.id.toobar_back:
                finish();
                break;
        }
    }

    //返佣方法
    public void Returningaservant() {
        addSubscribe(ApiManage.getInstance().getMainApi().getReturningservant(token)
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
                            String allRebatez = df.format(allRebate);
                            String cashRebatez = df.format(cashRebate);
                            String thismothRebatez= df.format(thismothRebate);
                            cashWithdrawalRebate.setText(allRebatez);//可提返佣
                            thisMonthcommission.setText(cashRebatez);//本月返佣
                            accumulatedRebate.setText(thismothRebatez);//累计返佣
                            accumulatedPoints.setText("小麒乖乖已累计为您返佣"+thismothRebatez+"积分");
                            SpUtil.getInstance().saveString("returningservantdata",new Gson().toJson(returningservantBeanBaseData.getData()));
                        }
                    }
                }));
    }

    //获取用户当前的vip等级
    public void Userlevel() {
        addSubscribe(ApiManage.getInstance().getMainApi().getCommissionlevel(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<CommissionlevelBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<CommissionlevelBean> commissionlevelBeanBaseData) {
                        CommissionlevelBean data = commissionlevelBeanBaseData.getData();
                        vipName.setText(data.getCurrentLevel());
                        currentLevel = data.getCurrentLevel();
                        //设置进度条经验 根据用户动态改变的
                        progressBarH.setProgress(data.getNextLevelMoney());
                        progressBarH.setMax((int) data.getOrderMoney());
                        if ("准铂金".equals(data.getCurrentLevel())) {
                            ClipDrawable drawable = new ClipDrawable(new ColorDrawable(Color.parseColor("#646464")), Gravity.LEFT, ClipDrawable.HORIZONTAL);
                          progressBarH.setProgressDrawable(drawable);
                        } else if ("准钻石".equals(data.getCurrentLevel())) {
                            ClipDrawable drawable = new ClipDrawable(new ColorDrawable(Color.parseColor("#000000")), Gravity.LEFT, ClipDrawable.HORIZONTAL);
                            progressBarH.setProgressDrawable(drawable);
                        }
                        Glide.with(mContext)
                                .load(Config.IMGURL +data.getPicture())
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .into(vipbg);
                        if (data.getNextLevel() == null) {
                            vipNameQy.setText("已享受最高返佣权益");
                            vipCount.setText("309/800 本月还放款491，已享受最高返佣权益");
                        } else {
                            vipNameQy.setText("享受" + data.getCurrentLevel() + "返佣权益");
                            vipCount.setText(data.getNextLevelMoney() + "/" + data.getOrderMoney() + " 本月还放款" + data.getCurrentCharge() + "，可升级" + (data.getNextLevel() + "，享受更高返佣"));
                        }
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
                        viplist = new ArrayList<>();
                        viplist.addAll(listBaseData.getData());
                        //设置vip权限服务列表
                        vIpListAdapter = new VIpListAdapter(viplist);
                        vipRecyclerView.setAdapter(vIpListAdapter);
                        vipRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                        vIpListAdapter.statusvip(4);
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
                        super.onError(t);
                        Log.e("zlz", t.toString());
                    }
                });

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
                            xbannerVip.setBannerData(R.layout.xbanner_item, systemCommissionlevel);
                            xbannerVip.loadImage(new XBanner.XBannerAdapter() {
                                @Override
                                public void loadBanner(XBanner banner, Object model, View view, int position) {
                                    TextView vipname = (TextView) view.findViewById(R.id.vip_name);
                                    TextView tvContent = (TextView) view.findViewById(R.id.vip_jurisdiction);
                                    ImageView picture = (ImageView) view.findViewById(R.id.bgpicture);
                                    Button no_purchase = (Button) view.findViewById(R.id.no_purchase);
                                    TextView termof_validity = (TextView) view.findViewById(R.id.termof_validity);
                                    Button purchase = (Button) view.findViewById(R.id.purchase);

                                    vipname.setText(systemCommissionlevel.get(position).getName() + "服务包");
                                    tvContent.setText("享受" + systemCommissionlevel.get(position).getName() + "级别返佣、额外13项权益");
                                    Glide.with(mContext)
                                            .load(Config.IMGURL + systemCommissionlevel.get(position).getPicture())
                                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                                            .into(picture);
                                    if ("铂金".equals(systemCommissionlevel.get(position).getName())) {
                                        purchase.setTextColor(Color.parseColor("#676767"));
                                        purchase.setBackgroundResource(R.drawable.purchasebgp);
                                    } else if ("钻石".equals(systemCommissionlevel.get(position).getName())) {
                                        purchase.setTextColor(Color.parseColor("#ffffff"));
                                        purchase.setBackgroundResource(R.drawable.purchasebgz);
                                    }

                                    if (systemCommissionlevel.get(position).isBuy() == false) {
                                        purchase.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //跳转购买服务包
                                                Intent intentbuysp = new Intent(MycommissionActivity.this, BuyServicePackActivity.class);
                                                if (!"".equals(currentLevel)){
                                                    intentbuysp.putExtra("buyname",currentLevel);
                                                }
                                                startActivity(intentbuysp);
                                            }
                                        });
                                    } else {
                                        purchase.setText("已购买");
                                        no_purchase.setVisibility(View.GONE);
                                        termof_validity.setVisibility(View.VISIBLE);
                                        termof_validity.setText("有效期:2021-12-31");
                                    }

                                }
                            });
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        toast(t.getMessage());
                        super.onError(t);
                    }
                }));

    }


}
