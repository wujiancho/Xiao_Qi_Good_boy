package com.lx.xqgg.ui.mycommission;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.hymane.expandtextview.utils.DensityUtils;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.adapter.VIpListAdapter;
import com.lx.xqgg.ui.mycommission.adapter.fanyongAdapter;
import com.lx.xqgg.ui.mycommission.bean.CommissionlevelBean;
import com.lx.xqgg.ui.mycommission.bean.ListofinterestsBean;
import com.lx.xqgg.ui.mycommission.bean.MonthlysettlementBean;
import com.lx.xqgg.ui.mycommission.bean.ReturningservantBean;
import com.lx.xqgg.ui.mycommission.bean.SystemCommissionlevelBean;
import com.lx.xqgg.ui.vip.bean.PayListBean;
import com.lx.xqgg.util.RoundedCornersTransformation;
import com.lx.xqgg.util.ScreenUtil;
import com.lx.xqgg.util.SpUtil;
import com.stx.xhb.xbanner.XBanner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MycommissionActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.riyuejie)
    Button riyuejie;
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
    @BindView(R.id.diglogxx)
    LinearLayout diglogxx;
    @BindView(R.id.vipbg)
    ImageView vipbg;
    @BindView(R.id.dangq)
    TextView dangq;
    @BindView(R.id.vip_name)
    TextView vipName;
    @BindView(R.id.vip_name_qy)
    TextView vipNameQy;
    @BindView(R.id.progress_bar_h)
    ProgressBar progressBarH;
    @BindView(R.id.vip_count)
    TextView vipCount;
    @BindView(R.id.xbanner_vip)
    XBanner xbannerVip;
    @BindView(R.id.jurisdiction_count)
    TextView jurisdictionCount;
    @BindView(R.id.vip_RecyclerViewstatus)
    TextView vipRecyclerViewstatus;
    @BindView(R.id.stuart_img)
    ImageView stuartImg;
    @BindView(R.id.vip_RecyclerViewstatusly)
    LinearLayout vipRecyclerViewstatusly;
    @BindView(R.id.vip_RecyclerView)
    RecyclerView vipRecyclerView;
    @BindView(R.id.vip)
    LinearLayout vip;
    @BindView(R.id.myjurisdiction)
    ConstraintLayout myjurisdiction;
    @BindView(R.id.cotent)
    TextView cotent;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.xqgg_gl)
    LinearLayout xqggGl;
    @BindView(R.id.fanyong_RecyclerView)
    RecyclerView fanyongRecyclerView;
    @BindView(R.id.btn_riyuejie)
    ImageView btnRiyuejie;
    private VIpListAdapter vIpListAdapter;
    private List<SystemCommissionlevelBean.RightsBean> viplist;
    private List<SystemCommissionlevelBean> systemCommissionlevel;
    private String currentLevel;
    private int rightsNum;
    private String name;
    private int positions;

    private static final float MIN_SCALE = 0.75f;
    private fanyongAdapter fanyongAdapter;
    private ArrayList<PayListBean> fanyonglist;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mycommission;
    }

    @Override
    protected void initView() {
        toobarTitle.setText("业绩返佣服务包");
        viplist = new ArrayList<>();
        //设置vip权限服务列表
        vIpListAdapter = new VIpListAdapter(viplist);
        vipRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        vipRecyclerView.setAdapter(vIpListAdapter);
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

    @Override
    protected void initData() {
        //获取用户信息
        userName.setText(SharedPrefManager.getUserInfo().getUsername());
        String servisername = SpUtil.getInstance().getSpString("servisername");
        if (!"".equals(servisername)) {
            usercompanyName.setText(servisername);
        }
        String returningservantdata = SpUtil.getInstance().getSpString("returningservantdata");
        if (!"".equals(returningservantdata)) {
            ReturningservantBean returningservantBean = new Gson().fromJson(returningservantdata, ReturningservantBean.class);
            int allRebate = returningservantBean.getAllCharge();
            DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
            String allRebatez = df.format(allRebate);
            accumulatedPoints.setText("小麒乖乖已累计为您返佣" + allRebatez + "积分");
        }
        String chargeType = SharedPrefManager.getUserInfo().getCharge_type();
        if ("2".equals(chargeType)) {
            riyuejie.setText("日结");
            btnRiyuejie.setVisibility(View.VISIBLE);
        } else if ("1".equals(chargeType)) {
            riyuejie.setText("月结");
            btnRiyuejie.setVisibility(View.GONE);
        } else {
            riyuejie.setVisibility(View.GONE);
        }
        //获取用户的可提返佣-本月返佣-累计返佣
        Returningaservant();
        //获取用户当前的vip等级
        Userlevel();
        //vip卡片
        vipcard();
        //小麒乖乖的购买
        goumai();
        fanyong();
        //获取权益列表
        //Listofinterests();
    }

    @OnClick({R.id.cash_withdrawal_rebatez, R.id.riyuejie, R.id.this_monthcommissionz, R.id.btn_riyuejie,R.id.accumulated_rebatez, R.id.vip_RecyclerViewstatus, R.id.myjurisdiction, R.id.xqgg_gl, R.id.toobar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //跳转可提现返佣
            case R.id.cash_withdrawal_rebatez:
                //Accesstobankinformation();
                Intent intentcashwr = new Intent(MycommissionActivity.this, CashWithdrawalRebateActivity.class);
                intentcashwr.putExtra("riyuejie", riyuejie.getText().toString());
                startActivityForResult(intentcashwr, 2);
                break;
            //跳转本月返佣
            case R.id.this_monthcommissionz:
                Intent intenthistmr = new Intent(MycommissionActivity.this, ThisMonthReturnActivity.class);
                intenthistmr.putExtra("vipname", currentLevel);
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
                Intent intenxieyi = new Intent(MycommissionActivity.this, XieYi2Activity.class);
                intenxieyi.putExtra("group", "buyStrategy");
                startActivity(intenxieyi);
                break;
          /*  //小麒乖乖的返佣
            case R.id.xqgg_fy:
                Intent intenxieyi2 = new Intent(MycommissionActivity.this, XieYiActivity.class);
                intenxieyi2.putExtra("group", "rakebackRule");
                startActivity(intenxieyi2);
                break;*/
            case R.id.toobar_back:
                finish();
                break;
            case R.id.btn_riyuejie:
                //申请月结
                initPopWindow();
                //DigyueFragment digyueFragment=new DigyueFragment();
               //digyueFragment.show(getSupportFragmentManager(), "");
                break;
        }
    }

    //积分返佣方法
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
                            cashWithdrawalRebate.setText(cashRebatez);
                            thisMonthcommission.setText(thismothRebatez);
                            accumulatedRebate.setText(allRebatez);
                            accumulatedPoints.setText("小麒乖乖已累计为您返佣" + allRebatez + "积分");
                            //SpUtil.getInstance().saveString("returningservantdata", new Gson().toJson(returningservantBeanBaseData.getData()));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.getMessage());
                    }
                }));
    }

    //获取用户当前的vip等级
    public void Userlevel() {
        addSubscribe(ApiManage.getInstance().getMainApi().getCommissionlevel(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<CommissionlevelBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<CommissionlevelBean> commissionlevelBeanBaseData) {
                        CommissionlevelBean data = commissionlevelBeanBaseData.getData();
                        if (data.getCurrentLevel() == null && "".equals(data.getCurrentLevel())) {
                            vipName.setText("准黄金");
                            /*vipName.setText("准普通");
                            dangq.setVisibility(View.GONE);
                        } else {
                            vipName.setText(data.getCurrentLevel());
                            if (data.getCurrentLevel().contains("准")) {
                                dangq.setVisibility(View.GONE);
                            }*/
                        } else {
                            vipName.setText(data.getCurrentLevel());
                        }
                        if ((int) data.getOrderMoney() == 0) {
                            progressBarH.setProgress(5);
                            progressBarH.setMax(100);
                        } else if (data.getNextLevelMoney() == 0) {
                            progressBarH.setProgress(100);
                            progressBarH.setMax(100);
                        } else {
                            //设置进度条经验 根据用户动态改变的
                            progressBarH.setProgress((int) data.getOrderMoney());
                            progressBarH.setMax(data.getNextLevelMoney());
                        }
                        currentLevel = data.getCurrentLevel();
                        yuanjiaogrild(vipbg, Config.IMGURL + data.getPicture());
                        if ("".equals(data.getNextLevel()) || data.getNextLevel() == null || data.getNextLevelMoney() == 0) {
                            vipCount.setText("您享受最高返佣");
                            vipNameQy.setText("享受" + data.getCurrentLevel() + "返佣权益");
                        } else if ("".equals(data.getCurrentLevel()) && data.getCurrentLevel() == null) {
                            vipNameQy.setText("享受准普通返佣权益");

                        } else {
                            vipNameQy.setText("享受" + data.getCurrentLevel() + "返佣权益");
                            vipCount.setText(data.getOrderMoney() + "/" + data.getNextLevelMoney() + " 本月还放款" + (data.getNextLevelMoney() - data.getOrderMoney()) + "，可升级" + (data.getNextLevel() + "，享受更高返佣"));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.getMessage());
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

    //圆角图片
    private void yuanjiaogrild(ImageView v, String url) {
        // 圆角图片 new RoundedCornersTransformation 参数为 ：半径 , 外边距 , 圆角方向(ALL,BOTTOM,TOP,RIGHT,LEFT,BOTTOM_LEFT等等)
        //顶部左边圆角
        RoundedCornersTransformation transformation = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.TOP_LEFT);
        //顶部右边圆角
        RoundedCornersTransformation transformation1 = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT);
        //顶部左边圆角
        RoundedCornersTransformation transformation2 = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT);
        //顶部右边圆角
        RoundedCornersTransformation transformation3 = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT);
        //组合各种Transformation,
        MultiTransformation<Bitmap> mation = new MultiTransformation<>
                //Glide设置圆角图片后设置ImageVIew的scanType="centerCrop"无效解决办法,将new CenterCrop()添加至此
                (new CenterCrop(), transformation, transformation1, transformation2, transformation3);
        Glide.with(mContext)
                .load(url)
                .apply(RequestOptions.bitmapTransform(mation))
                .into(v);
    }

    //vip卡片
    private void vipcard() {
        systemCommissionlevel = new ArrayList<>();
        addSubscribe(ApiManage.getInstance().getMainApi().getSystemCommissionlevel(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<SystemCommissionlevelBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<SystemCommissionlevelBean>> listBaseData) {
                        if (listBaseData.isSuccess()) {
                            systemCommissionlevel.addAll(listBaseData.getData());
                            xbannerVip.setBannerData(R.layout.xbanner_item, systemCommissionlevel);
                            //设置XBanner滑动动画
                            xbannerVip.setCustomPageTransformer(new ViewPager.PageTransformer() {
                                @Override
                                public void transformPage(@NonNull View page, float position) {
                                    final int pageWidth = page.getWidth();
                                    final float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));

                                    if (position < 0) { // [-1,0]
                                        // Scale the page down (between MIN_SCALE and 1)
                                        page.setTranslationY(scaleFactor);
                                        page.setTranslationX(scaleFactor);

                                    } else if (position == 0) {
                                        page.setTranslationY(scaleFactor);
                                        page.setTranslationX(scaleFactor);

                                    } else if (position <= 1) { // (0,1]
                                        // Scale the page down (between MIN_SCALE and 1)
                                        page.setTranslationY(scaleFactor);
                                        page.setTranslationX(scaleFactor);

                                    }
                                }

                            });
                            boolean selectbuy = false;
                            for (int i = 0; i < systemCommissionlevel.size(); i++) {
                                if (systemCommissionlevel.get(i).isBuy() == true) {
                                    xbannerVip.setBannerCurrentItem(i);
                                    jurisdictionCount.setText("当前" + systemCommissionlevel.get(i).getName() + "服务包已解锁" + systemCommissionlevel.get(i).getRightsNum() + "项权益");
                                    addviplist(i);
                                    selectbuy = true;
                                    break;
                                }
                            }
                            if (!selectbuy) {
                                jurisdictionCount.setText("当前" + systemCommissionlevel.get(0).getName() + "服务包解锁" + systemCommissionlevel.get(0).getRightsNum() + "项权益");
                                addviplist(0);
                            }
                            xbannerVip.loadImage(new XBanner.XBannerAdapter() {

                                private String endTime;

                                @Override
                                public void loadBanner(XBanner banner, Object model, View view, int position) {
                                    TextView vipname = (TextView) view.findViewById(R.id.vip_name);
                                    TextView tvContent = (TextView) view.findViewById(R.id.vip_jurisdiction);
                                    ImageView picture = (ImageView) view.findViewById(R.id.bgpicture);
                                    TextView termof_validity = (TextView) view.findViewById(R.id.termof_validity);
                                    TextView purchase = (TextView) view.findViewById(R.id.purchase);
                                    ImageView viplogo = (ImageView) view.findViewById(R.id.viplogo);
                                    RelativeLayout tiaoz = (RelativeLayout) view.findViewById(R.id.tiaoz);
                                    rightsNum = systemCommissionlevel.get(position).getRightsNum();
                                    name = systemCommissionlevel.get(position).getName();
                                    vipname.setText(systemCommissionlevel.get(position).getName() + "服务包");
                                    tvContent.setText("购买" + systemCommissionlevel.get(position).getName() + "服务包享受更高返佣、额外" + systemCommissionlevel.get(position).getRightsNum() + "项权益");
                                    yuanjiaogrild(picture, Config.IMGURL + systemCommissionlevel.get(position).getPicture());
                                    Glide.with(mContext)
                                            .load(Config.IMGURL + systemCommissionlevel.get(position).getIco())
                                            .into(viplogo);
                                    if (systemCommissionlevel.get(position).isBuy() == true) {
                                        purchase.setText("已购买");
                                        endTime = systemCommissionlevel.get(position).getEndTime();
                                        if (!"".equals(systemCommissionlevel.get(position).getEndTime()) && systemCommissionlevel.get(position).getEndTime() != null) {
                                            termof_validity.setVisibility(View.VISIBLE);
                                            termof_validity.setText("有效期至：" + systemCommissionlevel.get(position).getEndTime());

                                        } else {
                                            termof_validity.setVisibility(View.GONE);
                                        }
                                    } else {
                                        termof_validity.setVisibility(View.GONE);
                                    }
                                    tiaoz.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //跳转购买服务包
                                            Intent intentbuysp = new Intent(MycommissionActivity.this, BuyServicePackActivity.class);
                                            intentbuysp.putExtra("buyname", currentLevel);
                                            intentbuysp.putExtra("buyname1", systemCommissionlevel.get(position).getName());
                                            intentbuysp.putExtra("id", systemCommissionlevel.get(position).getId());
                                            intentbuysp.putExtra("imgurl", systemCommissionlevel.get(position).getPicture());
                                            intentbuysp.putExtra("icn", systemCommissionlevel.get(position).getIco());
                                            intentbuysp.putExtra("position", position);
                                            intentbuysp.putExtra("rightsNum", systemCommissionlevel.get(position).getRightsNum());
                                            intentbuysp.putExtra("endTime", endTime);
                                            startActivity(intentbuysp);
                                        }
                                    });
                                }
                            });
                            //联动改变权限
                            xbannerVip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    if (systemCommissionlevel.get(position).isBuy() == true) {
                                        jurisdictionCount.setText("当前" + systemCommissionlevel.get(position).getName() + "服务包已解锁" + systemCommissionlevel.get(position).getRightsNum() + "个权限");
                                    } else {
                                        jurisdictionCount.setText("当前" + systemCommissionlevel.get(position).getName() + "服务包可以解锁" + systemCommissionlevel.get(position).getRightsNum() + "个权限");
                                    }
                                    positions = position;
                                    vip.setVisibility(View.VISIBLE);
                                    addviplist(positions);
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

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

    //申请月结
    private void Monthlysettlement() {
        ApiManage.getInstance().getMainApi().getMonthlysettlement(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<MonthlysettlementBean>() {
                    @Override
                    public void onNext(MonthlysettlementBean monthlysettlementBean) {
                        initPopWindowqued();
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

    //申请月结提示
    private void initPopWindow() {
        LinearLayout mlinear = (LinearLayout) getLayoutInflater().inflate(R.layout.monthlybalanceitem, null);
        AlertDialog dialog = new AlertDialog.Builder(this).create();

        TextView mlinearViewById = mlinear.findViewById(R.id.riyuejiename);
        Button btn_ok = mlinear.findViewById(R.id.btn_ok);
        Button btn_no = mlinear.findViewById(R.id.btn_no);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Monthlysettlement();
            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mlinearViewById.setText(SharedPrefManager.getUserInfo().getUsername() + "您好,您的返佣结算为日结,在业绩返佣内无法提现，数据仅供参考。");
        dialog.setView(mlinear);
        //设置弹出位置
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.TOP | Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        float px2dp50 = DensityUtils.px2dp(mContext, 50);
        float px2dp150 = DensityUtils.px2dp(mContext, 150);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = 150;  //设置出现的高度，距离顶部
        dialogWindow.setAttributes(lp);
        dialog.show();

    }

    //申请月结确认
    private void initPopWindowqued() {
        LinearLayout mlinear = (LinearLayout) getLayoutInflater().inflate(R.layout.monthlybalanceitemqued, null);
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(mlinear);
        Window window = dialog.getWindow();
        //设置弹出位置
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.TOP | Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        float px2dp50 = DensityUtils.px2dp(mContext, 50);
        float px2dp150 = DensityUtils.px2dp(mContext, 150);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = 150;  //设置出现的高度，距离顶部
        window.setAttributes(lp);
        dialog.show();
    }

    //返回刷新积分
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == 2) {
                Returningaservant();
            }
        }
    }

    //复用权益适配器
    private void addviplist(int position) {
        viplist.clear();
        viplist.addAll(systemCommissionlevel.get(position).getRights());
        vIpListAdapter.statusvip(4);
        vIpListAdapter.notifyDataSetChanged();
    }

    //小麒乖乖的返佣
    private void fanyong() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("group", "buyRule");
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
                                fanyonglist = new ArrayList<>();
                                fanyonglist.addAll(listBaseData.getData());
                                fanyongAdapter = new fanyongAdapter(fanyonglist);
                                fanyongRecyclerView.setAdapter(fanyongAdapter);
                                fanyongRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                            }
                        }
                    }
                }));

    }

    //小麒乖乖的购买
    private void goumai() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("group", "buyStrategy");
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
                                cotent.setText(listBaseData.getData().get(0).getName());
                                Glide.with(mContext)
                                        .load(Config.IMGURL + listBaseData.getData().get(0).getValue())
                                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                                        .into(logo);
                            }
                        }
                    }
                }));
    }

}
