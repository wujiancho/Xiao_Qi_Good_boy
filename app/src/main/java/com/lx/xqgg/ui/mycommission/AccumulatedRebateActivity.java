package com.lx.xqgg.ui.mycommission;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.mycommission.adapter.AccumulatedRebateAdapter;
import com.lx.xqgg.ui.mycommission.bean.AccumulatedPointslistBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//累计返佣
public class AccumulatedRebateActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.Accumulated_points)
    TextView accumulatedPoints;
    @BindView(R.id.accumulatedrebate_RecyclerView)
    RecyclerView accumulatedrebateRecyclerView;
    private List<AccumulatedPointslistBean> accumulatedPointslist;
    private AccumulatedRebateAdapter accumulatedRebateAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_accumulated_rebate;
    }

    @Override
    protected void initView() {
     toobarTitle.setText("累计佣金");
      //网络获取积分
     accumulatedPoints.setText("50,000");
    }

    @Override
    protected void initData() {
        accumulatedPointslist = new ArrayList<>();
        accumulatedPointslist.add(new AccumulatedPointslistBean("2020.08 佣金积分2,000",R.drawable.tuoke));
        accumulatedPointslist.add(new AccumulatedPointslistBean("2020.07 佣金积分3,000",R.drawable.mygm));
        accumulatedPointslist.add(new AccumulatedPointslistBean("2020.06 佣金积分5,000",R.drawable.mysc));
        accumulatedPointslist.add(new AccumulatedPointslistBean("2020.05 佣金积分1,000",R.drawable.ic_p_ppjg));
        accumulatedRebateAdapter = new AccumulatedRebateAdapter(accumulatedPointslist);
        accumulatedrebateRecyclerView.setAdapter(accumulatedRebateAdapter);
        accumulatedrebateRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        //点击条目跳转到点击月份的返佣明细
        accumulatedRebateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intentrd=new Intent(AccumulatedRebateActivity.this,RefundDetailsActivity.class);
                startActivity(intentrd);
            }
        });

    }




    @OnClick(R.id.toobar_back)
    public void onViewClicked() {
        finish();
    }
}
