package com.lx.xqgg.ui.mycommission;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.adapter.VIpListAdapter;
import com.lx.xqgg.ui.mycommission.bean.CommissionlevelBean;
import com.lx.xqgg.ui.mycommission.bean.ListofinterestsBean;
import com.lx.xqgg.ui.mycommission.bean.MonthlysettlementBean;
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
import io.reactivex.subscribers.DisposableSubscriber;

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
    @BindView(R.id.riyuejie)
    Button riyuejie;
    private VIpListAdapter vIpListAdapter;
    private List<SystemCommissionlevelBean.RightsBean> viplist;
    private List<SystemCommissionlevelBean> systemCommissionlevel;
    private String currentLevel;
    private int rightsNum;
    private String name;

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
        //获取用户的可提返佣-本月返佣-累计返佣
        Returningaservant();
        //获取用户当前的vip等级
        Userlevel();
        //vip卡片
        vipcard();

        //获取权益列表
        //Listofinterests();
    }

    @OnClick({R.id.cash_withdrawal_rebatez, R.id.riyuejie,R.id.this_monthcommissionz, R.id.accumulated_rebatez, R.id.vip_RecyclerViewstatus, R.id.myjurisdiction, R.id.xqgg_gl, R.id.xqgg_fy, R.id.toobar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //跳转可提现返佣
            case R.id.cash_withdrawal_rebatez:
                Intent intentcashwr = new Intent(MycommissionActivity.this, CashWithdrawalRebateActivity.class);
                intentcashwr.putExtra("riyuejie",riyuejie.getText().toString());
                startActivity(intentcashwr);
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
                toast("功能暂未开放");
                break;
            //小麒乖乖的返佣
            case R.id.xqgg_fy:
                toast("功能暂未开放");
                break;
            case R.id.toobar_back:
                finish();
                break;
            case R.id.riyuejie:
                //申请月结
                Monthlysettlement();
                break;
        }
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
                            cashWithdrawalRebate.setText(cashRebatez);
                            thisMonthcommission.setText(thismothRebatez);
                            accumulatedRebate.setText(allRebatez);
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

    //获取用户当前的vip等级
    public void Userlevel() {
        addSubscribe(ApiManage.getInstance().getMainApi().getCommissionlevel(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<CommissionlevelBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<CommissionlevelBean> commissionlevelBeanBaseData) {
                        CommissionlevelBean data = commissionlevelBeanBaseData.getData();
                        vipName.setText(data.getCurrentLevel());
                        //设置进度条经验 根据用户动态改变的
                        progressBarH.setProgress((int) data.getOrderMoney());
                        progressBarH.setMax(data.getNextLevelMoney());
                        currentLevel = data.getCurrentLevel();
                        Glide.with(mContext)
                                .load(Config.IMGURL + data.getPicture())
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .into(vipbg);
                        if ("".equals(data.getNextLevel() )||data.getNextLevel() == null) {
                            vipNameQy.setText("已享受最高返佣权益");
                            vipCount.setText(data.getOrderMoney() + "/99999，已享受最高返佣权益");
                        } else {
                            vipNameQy.setText("享受" + data.getCurrentLevel() + "返佣权益");
                            vipCount.setText(data.getOrderMoney() + "/" + data.getNextLevelMoney() + " 本月还放款" + data.getCurrentCharge() + "，可升级" + (data.getNextLevel() + "，享受更高返佣"));
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
                            xbannerVip.loadImage(new XBanner.XBannerAdapter() {
                                @Override
                                public void loadBanner(XBanner banner, Object model, View view, int position) {
                                    TextView vipname = (TextView) view.findViewById(R.id.vip_name);
                                    TextView tvContent = (TextView) view.findViewById(R.id.vip_jurisdiction);
                                    ImageView picture = (ImageView) view.findViewById(R.id.bgpicture);
                                    TextView termof_validity = (TextView) view.findViewById(R.id.termof_validity);
                                    TextView purchase = (TextView) view.findViewById(R.id.purchase);
                                    rightsNum = systemCommissionlevel.get(position).getRightsNum();
                                    name = systemCommissionlevel.get(position).getName();
                                    vipname.setText(systemCommissionlevel.get(position).getName() + "服务包");
                                    tvContent.setText("享受" + systemCommissionlevel.get(position).getName() + "级别返佣、额外" + systemCommissionlevel.get(position).getRightsNum() + "项权益");
                                    Glide.with(mContext)
                                            .load(Config.IMGURL + systemCommissionlevel.get(position).getPicture())
                                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                                            .into(picture);
                                    if (systemCommissionlevel.get(position).isBuy() == false) {
                                        purchase.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //跳转购买服务包
                                                Intent intentbuysp = new Intent(MycommissionActivity.this, BuyServicePackActivity.class);
                                                if (!"".equals(currentLevel)) {
                                                    intentbuysp.putExtra("buyname", currentLevel);
                                                    intentbuysp.putExtra("id", systemCommissionlevel.get(0).getId());
                                                    intentbuysp.putExtra("imgurl", systemCommissionlevel.get(0).getPicture());
                                                    intentbuysp.putExtra("icn", systemCommissionlevel.get(0).getIco());
                                                }
                                                startActivity(intentbuysp);
                                            }
                                        });
                                    } else {
                                        purchase.setText("已购买");
                                        termof_validity.setVisibility(View.VISIBLE);
                                        termof_validity.setText("有效期：" + systemCommissionlevel.get(position).getEndTime());
                                        jurisdictionCount.setText("当前" + name + "服务包可以解锁" + rightsNum + "个权限");
                                    }

                                }
                            });
                            viplist = new ArrayList<>();

                            //设置vip权限服务列表
                            vIpListAdapter = new VIpListAdapter(viplist);
                            vipRecyclerView.setAdapter(vIpListAdapter);
                            vipRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                            jurisdictionCount.setText("当前" + systemCommissionlevel.get(0).getName() + "服务包可以解锁" +  systemCommissionlevel.get(0).getRightsNum() + "个权限");
                            viplist.clear();
                            viplist.addAll(listBaseData.getData().get(0).getRights());
                            vIpListAdapter.notifyDataSetChanged();
                            for (int i = 0; i < listBaseData.getData().size(); i++) {
                                viplist.addAll(listBaseData.getData().get(i).getRights());
                            }
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


                            //联动改变权限
                            xbannerVip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    jurisdictionCount.setText("当前" + systemCommissionlevel.get(position).getName() + "服务包可以解锁" +  systemCommissionlevel.get(position).getRightsNum() + "个权限");
                                    viplist.clear();
                                    viplist.addAll(listBaseData.getData().get(position).getRights());
                                    vIpListAdapter.notifyDataSetChanged();
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
    private  void  Monthlysettlement(){
        ApiManage.getInstance().getMainApi().getMonthlysettlement(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<MonthlysettlementBean>() {
                    @Override
                    public void onNext(MonthlysettlementBean monthlysettlementBean) {
                        initPopWindow();
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
    //申请月结
   private void initPopWindow(){
       LinearLayout mlinear = (LinearLayout) getLayoutInflater().inflate(R.layout.monthlybalanceitem,null);
       AlertDialog dialog=new AlertDialog.Builder(this).setPositiveButton("确定", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
           }
       }).create();
       TextView mlinearViewById=mlinear.findViewById(R.id.riyuejiename);
       mlinearViewById.setText(SharedPrefManager.getUserInfo().getUsername()+",您的返佣结算为日结,在业绩返佣内无法提现，数据仅供参考。");
       dialog.setView(mlinear);
       Window window = dialog.getWindow();
       //设置弹出位置
       window.setGravity(Gravity.TOP);
       int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;//父布局的宽度
       Window dialogWindow = dialog.getWindow();
       dialogWindow.setGravity(Gravity.TOP | Gravity.CENTER);
       WindowManager.LayoutParams lp = dialogWindow.getAttributes();
       lp.width = matchParent;
       lp.height = matchParent;
       lp.x = matchParent;
       lp.y = 130;  //设置出现的高度，距离顶部
       window.setAttributes(lp);
       dialog.show();

   }
}
