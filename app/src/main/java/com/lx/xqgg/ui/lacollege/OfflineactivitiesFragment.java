package com.lx.xqgg.ui.lacollege;

import android.widget.Button;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class OfflineactivitiesFragment extends BaseFragment {
    @BindView(R.id.btn_baoming)
    Button btnBaoming;

    @Override
    protected int getLayoutId() {
        return R.layout.offlineactivitiesfragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_baoming)
    public void onViewClicked() {
    }
}
