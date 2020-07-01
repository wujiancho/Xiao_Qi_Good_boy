package com.lx.xqgg.ui.qcc;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.ui.product.bean.QccBean;
import com.lx.xqgg.ui.qcc.adapter.DcdyAdapter;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 动产抵押
 */
public class DcdyActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private DcdyAdapter dcdyAdapter;
    private QccBean qccBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initView() {
        tvTitle.setText("动产抵押");
        qccBean= (QccBean) getIntent().getSerializableExtra("data");
        dcdyAdapter = new DcdyAdapter(qccBean.getResult().getMPledge());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(dcdyAdapter);
        dcdyAdapter.setEmptyView(R.layout.layout_empty, recyclerView);
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }
}
