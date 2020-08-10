package com.lx.xqgg.ui.mycommission;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.mycommission.adapter.VIpListAdapter;
import com.lx.xqgg.ui.mycommission.bean.ViplistBean;
import com.lx.xqgg.ui.mycommission.bean.XbannerdataBean;
import com.stx.xhb.xbanner.XBanner;

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
    TextView stuartImg;
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
    private VIpListAdapter vIpListAdapter;
    private List<ViplistBean> viplist;

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
        final List<XbannerdataBean> xbannerdata = new ArrayList<>();
        xbannerdata.add(new XbannerdataBean("黄金服务包", "购买黄金服务包享受更高返佣、额外4项权益"));
        xbannerdata.add(new XbannerdataBean("铂金服务包", "购买铂金服务包享受更高返佣、额外8项权益"));
        xbannerdata.add(new XbannerdataBean("钻石服务包", "购买钻石服务包享受更高返佣、额外12项权益"));
        xbannerVip.setBannerData(R.layout.xbanner_item, xbannerdata);
        xbannerVip.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                TextView vipname = (TextView) view.findViewById(R.id.vip_name);
                TextView tvContent = (TextView) view.findViewById(R.id.vip_jurisdiction);
                vipname.setText(xbannerdata.get(position).getVipname());
                tvContent.setText(xbannerdata.get(position).getJurisdiction());
            }
        });
        //跳转购买服务包
        xbannerVip.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Intent intentbuysp=new Intent(MycommissionActivity.this,BuyServicePackActivity.class);
                startActivity(intentbuysp);
            }
        });
        jurisdictionCount.setText("当前黄金服务包可以解锁4个权限");
       //联动改变权限
        xbannerVip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
             if (position==0){
                 jurisdictionCount.setText("当前"+xbannerdata.get(0).getVipname()+"可以解锁4个权限");
             }if (position==1){
                    jurisdictionCount.setText("当前"+xbannerdata.get(1).getVipname()+"可以解锁8个权限");
                }
             if (position==2){
                 jurisdictionCount.setText("当前"+xbannerdata.get(2).getVipname()+"可以解锁12个权限");
             }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置权限列表数据
        viplist = new ArrayList<>();
        viplist.add(new ViplistBean(R.drawable.tuoke,"返佣权益"));
        viplist.add(new ViplistBean(R.drawable.yongjin,"专属客服"));
        viplist.add(new ViplistBean(R.drawable.tuoke,"线上学院"));
        viplist.add(new ViplistBean(R.drawable.yongjin,"专家上门"));

        viplist.add(new ViplistBean(R.drawable.tuoke,"返佣权益"));
        viplist.add(new ViplistBean(R.drawable.yongjin,"专属客服"));
        viplist.add(new ViplistBean(R.drawable.tuoke,"线上学院"));
        viplist.add(new ViplistBean(R.drawable.yongjin,"专家上门"));

        viplist.add(new ViplistBean(R.drawable.tuoke,"返佣权益"));
        viplist.add(new ViplistBean(R.drawable.yongjin,"专属客服"));
        viplist.add(new ViplistBean(R.drawable.tuoke,"线上学院"));
        viplist.add(new ViplistBean(R.drawable.yongjin,"专家上门"));

        viplist.add(new ViplistBean(R.drawable.tuoke,"返佣权益"));
        viplist.add(new ViplistBean(R.drawable.yongjin,"专属客服"));
        viplist.add(new ViplistBean(R.drawable.tuoke,"线上学院"));
        //设置vip权限服务列表
        vIpListAdapter = new VIpListAdapter(viplist);
        vipRecyclerView.setAdapter(vIpListAdapter);
        vipRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        vIpListAdapter.statusvip(4);
        vipRecyclerViewstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("展开".equals(vipRecyclerViewstatus.getText().toString())) {
                    vIpListAdapter.statusvip(viplist.size());
                    vIpListAdapter.notifyDataSetChanged();
                    vipRecyclerViewstatus.setText("收起");
                } else if ("收起".equals(vipRecyclerViewstatus.getText().toString())) {
                    vIpListAdapter.statusvip(4);
                    vIpListAdapter.notifyDataSetChanged();
                    vipRecyclerViewstatus.setText("展开");
                }
            }
        });

        //设置进度条经验 根据用户动态改变的
        progressBarH.setProgress(100);
        progressBarH.setMax(800);
    }



    @OnClick({R.id.cash_withdrawal_rebatez, R.id.this_monthcommissionz, R.id.accumulated_rebatez, R.id.vip_RecyclerViewstatus, R.id.myjurisdiction, R.id.xqgg_gl, R.id.xqgg_fy,R.id.toobar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
           //跳转可提现返佣
            case R.id.cash_withdrawal_rebatez:
                Intent intentcashwr=new Intent(MycommissionActivity.this,CashWithdrawalRebateActivity.class);
                startActivity(intentcashwr);
                break;
                //跳转本月返佣
            case R.id.this_monthcommissionz:
                Intent intenthistmr=new Intent(MycommissionActivity.this,ThisMonthReturnActivity.class);
                startActivity(intenthistmr);
                break;
                //跳转累计返佣
            case R.id.accumulated_rebatez:
                Intent intentacclr=new Intent(MycommissionActivity.this,AccumulatedRebateActivity.class);
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
}
