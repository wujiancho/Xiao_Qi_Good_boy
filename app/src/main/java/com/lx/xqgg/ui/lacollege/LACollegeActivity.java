package com.lx.xqgg.ui.lacollege;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.lacollege.adapter.MylacollegeAadapter;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LACollegeActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.laCollege_tab)
    TabLayout laCollegeTab;
    @BindView(R.id.laCollege_vp)
    ViewPager laCollegeVp;
    private MylacollegeAadapter mylacollegeAadapter;


/*    @BindView(R.id.loanassistance_College)
    FrameLayout loanassistanceCollege;
    @BindView(R.id.bottomBar_College)
    BottomBar bottomBarCollege;*/

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lacollege;
    }

    @Override
    protected void initView() {
        tvTitle.setText("助贷学院");
    }

    @Override
    protected void initData() {
        mylacollegeAadapter = new MylacollegeAadapter(getSupportFragmentManager());
        laCollegeTab.setupWithViewPager(laCollegeVp);
        laCollegeVp.setAdapter(mylacollegeAadapter);
        laCollegeVp.setOffscreenPageLimit(2);
    }


    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }

}
