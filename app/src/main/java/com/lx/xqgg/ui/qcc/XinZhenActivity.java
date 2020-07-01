package com.lx.xqgg.ui.qcc;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseFragment;
import com.lx.xqgg.ui.order.OrderFragment;
import com.lx.xqgg.ui.order.OrderTypeFragment;
import com.lx.xqgg.ui.qcc.fragment.PenaltyCreditChinaFragment;
import com.lx.xqgg.ui.qcc.fragment.PermissionEciInfoFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XinZhenActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private String[] tabsTitle = {"工商局", "信用中国"};
    private List<BaseFragment> fragmentList=new ArrayList<>();
    private MyAdapter myAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_xinzhen;
    }

    @Override
    protected void initView() {
        tvTitle.setText("行政处罚");
        PenaltyCreditChinaFragment penaltyCreditChinaFragment=new PenaltyCreditChinaFragment();
        PermissionEciInfoFragment permissionEciInfoFragment=new PermissionEciInfoFragment();

        fragmentList.add(permissionEciInfoFragment);
        fragmentList.add(penaltyCreditChinaFragment);


        viewpager.setOffscreenPageLimit(tabsTitle.length);
        myAdapter = new MyAdapter(getSupportFragmentManager());
        viewpager.setAdapter(myAdapter);
        tablayout.setupWithViewPager(viewpager);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }

    public class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return tabsTitle.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabsTitle[position];
        }
//
//        @NonNull
//        @Override
//        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            return fragmentList.get(position);
//        }
//
//        @Override
//        public int getItemPosition(@NonNull Object object) {
//            return POSITION_NONE;
//        }
    }
}
