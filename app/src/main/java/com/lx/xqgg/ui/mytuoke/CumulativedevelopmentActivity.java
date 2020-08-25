package com.lx.xqgg.ui.mytuoke;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.mytuoke.adapter.TokeAdapter;
import com.lx.xqgg.ui.mytuoke.bean.TokeBean;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//累计拓客
public class CumulativedevelopmentActivity extends BaseActivity {

    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.rb_all)
    RadioButton rbAll;
    @BindView(R.id.rb_finish)
    RadioButton rbFinish;
    @BindView(R.id.rb_nofinish)
    RadioButton rbNofinish;
    @BindView(R.id.rb_fail)
    RadioButton rbFail;
    @BindView(R.id.tuokerecyclerView)
    RecyclerView tuokerecyclerView;
    private List<TokeBean> toke;
    private TokeAdapter tokeAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_cumulativedevelopment;
    }

    @Override
    protected void initView() {
    tvTitle.setText("累计拓客");
    }

    @Override
    protected void initData() {
        toke=new ArrayList<>();
        toke.add(new TokeBean("2020.06.08","苏州罗信****","183***0","fail"));
        toke.add(new TokeBean("2020.06.08","苏州罗信****","183***0","uncertified"));
        toke.add(new TokeBean("2020.06.08","苏州罗信****","183***0","fail"));
        toke.add(new TokeBean("2020.06.08","苏州罗信****","183***0","normal"));
        toke.add(new TokeBean("2020.06.08","苏州罗信****","183***0","fail"));
        toke.add(new TokeBean("2020.06.08","苏州罗信****","183***0","fail"));
        tokeAdapter = new TokeAdapter(toke);
        tuokerecyclerView.setAdapter(tokeAdapter);
        tuokerecyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
    }


    @OnClick({R.id.v_close, R.id.rb_all, R.id.rb_finish, R.id.rb_nofinish, R.id.rb_fail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
                //全部
            case R.id.rb_all:
                break;
                //已完成
            case R.id.rb_finish:
                break;
                //未完成
            case R.id.rb_nofinish:
                break;
                //失败
            case R.id.rb_fail:
                break;
        }
    }
}
