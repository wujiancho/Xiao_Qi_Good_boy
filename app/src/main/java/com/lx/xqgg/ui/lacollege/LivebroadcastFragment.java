package com.lx.xqgg.ui.lacollege;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseFragment;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class LivebroadcastFragment extends BaseFragment {
    @BindView(R.id.laCollegelive_RecyclerView)
    RecyclerView laCollegeliveRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.livebroadcastfragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
