package com.lx.xqgg.ui.mycommission;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.mycommission.bean.XbannerdataBean;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MycommissionActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.xbanner_vip)
    XBanner xbannerVip;
    @BindView(R.id.vip_RecyclerView)
    RecyclerView vipRecyclerView;

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
      final   List<XbannerdataBean> xbannerdata = new ArrayList<>();
        xbannerdata.add(new XbannerdataBean("黄金服务包","购买黄金服务包享受更高返佣、额外4项权益"));
        xbannerdata.add(new XbannerdataBean("铂金服务包","购买黄金服务包享受更高返佣、额外8项权益"));
        xbannerdata.add(new XbannerdataBean("钻石服务包","购买黄金服务包享受更高返佣、额外12项权益" ));
       // xbannerVip.setBannerData(R.layout.xbanner_item,xbannerdata);
        xbannerVip.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {

            }
        });
       xbannerVip.setOnItemClickListener(new XBanner.OnItemClickListener() {
           @Override
           public void onItemClick(XBanner banner, Object model, View view, int position) {

           }
       });

       xbannerVip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {

           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });


    }

    @OnClick(R.id.toobar_back)
    public void onViewClicked() {
        finish();
    }



}
