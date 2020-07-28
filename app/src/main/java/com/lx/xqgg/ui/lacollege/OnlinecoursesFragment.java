package com.lx.xqgg.ui.lacollege;

import android.view.View;
import android.widget.RadioButton;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseFragment;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class OnlinecoursesFragment extends BaseFragment {
    @BindView(R.id.rb_allcurriculum)
    RadioButton rbAllcurriculum;
    @BindView(R.id.rb_hotcurriculum)
    RadioButton rbHotcurriculum;
    @BindView(R.id.rb_youcurriculum)
    RadioButton rbYoucurriculum;
    @BindView(R.id.rb_legalassistant)
    RadioButton rbLegalassistant;
    @BindView(R.id.laCollege_RecyclerView)
    RecyclerView laCollegeRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.onlinecoursesfragment;

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.rb_allcurriculum, R.id.rb_hotcurriculum, R.id.rb_youcurriculum, R.id.rb_legalassistant})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_allcurriculum:
                break;
            case R.id.rb_hotcurriculum:
                break;
            case R.id.rb_youcurriculum:
                break;
            case R.id.rb_legalassistant:
                break;
        }
    }
}
