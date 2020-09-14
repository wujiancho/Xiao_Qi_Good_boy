package com.lx.xqgg.ui.mytuoke;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.adapter.ErrorAdapter;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    @BindView(R.id.wudata)
    TextView wudata;
    private List<TokeBean> toke;
    private TokeAdapter tokeAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cumulativedevelopment;
    }

    @Override
    protected void initView() {
        tvTitle.setText("累计拓客");
        toke = new ArrayList<>();
        toke.add(new TokeBean("2020-08-20","18360186490","苏州罗信****","失败"));
        toke.add(new TokeBean("2020-08-20","18360186490","苏州罗信****","已完成"));
        toke.add(new TokeBean("2020-08-20","18360186490","苏州罗信****","失败"));
        toke.add(new TokeBean("2020-08-20","18360186490","苏州罗信****","未完成"));
        toke.add(new TokeBean("2020-08-20","18360186490","苏州罗信****","失败"));
        toke.add(new TokeBean("2020-08-20","18360186490","苏州罗信****","已完成"));
        toke.add(new TokeBean("2020-08-20","18360186490","苏州罗信****","失败"));
        tokeAdapter = new TokeAdapter(toke);
        tuokerecyclerView.setAdapter(tokeAdapter);
        tuokerecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
    }

    @Override
    protected void initData() {
        //tokenlist(SharedPrefManager.getUser().getToken(), "");
    }

    @OnClick({R.id.v_close, R.id.rb_all, R.id.rb_finish, R.id.rb_nofinish, R.id.rb_fail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            //全部
            case R.id.rb_all:
                tokenlist(SharedPrefManager.getUser().getToken(), "");
                break;
            //已完成
            case R.id.rb_finish:
                tokenlist(SharedPrefManager.getUser().getToken(), "normal");
                break;
            //未完成
            case R.id.rb_nofinish:
                tokenlist(SharedPrefManager.getUser().getToken(), "uncertified");
                break;
            //失败
            case R.id.rb_fail:
                tokenlist(SharedPrefManager.getUser().getToken(), "fail");
                break;
        }
    }

    private void tokenlist(String token, String status) {
        addSubscribe(ApiManage.getInstance().getMainApi().getTokeBean(token, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<TokeBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<TokeBean>> listBaseData) {
                        if (listBaseData.getData() != null && listBaseData.getData().size() > 0) {
                            toke.addAll(listBaseData.getData());
                            tuokerecyclerView.setVisibility(View.VISIBLE);
                            wudata.setVisibility(View.GONE);
                        } else {
                            tuokerecyclerView.setVisibility(View.GONE);
                            wudata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.getMessage());
                        tuokerecyclerView.setVisibility(View.GONE);
                        wudata.setVisibility(View.VISIBLE);
                    }
                }));
    }

}
