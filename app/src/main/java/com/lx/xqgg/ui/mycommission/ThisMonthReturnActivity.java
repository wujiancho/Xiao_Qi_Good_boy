package com.lx.xqgg.ui.mycommission;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.mycommission.adapter.PointsDetailsAdapter;
import com.lx.xqgg.ui.mycommission.bean.PointsdetailsBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

//本月返佣
public class ThisMonthReturnActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.this_month_points)
    TextView thisMonthPoints;
    @BindView(R.id.this_monthpoints_RecyclerView)
    RecyclerView thisMonthpointsRecyclerView;
    private List<PointsdetailsBean> pointsdetailslist;
    private PointsDetailsAdapter pointsDetailsAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_this_month_return;
    }

    @Override
    protected void initView() {
      toobarTitle.setText("本月佣金积分");
    }

    @Override
    protected void initData() {
        pointsdetailslist = new ArrayList<>();
        pointsdetailslist.add(new PointsdetailsBean("华夏银行 - 龙惠贷","2020-08-04","20200804112515","苏州罗信网络科技有限公司","顾某","138458977777","吴健","用信","+5佣金积分","10,000.00"));
        pointsdetailslist.add(new PointsdetailsBean("微众银行 - 微税贷","2020-08-04","20200804112515","苏州小星星网络科技有限公司","李某","13895656565","飒飒","终审通过","+10佣金积分","60,000.00"));
        pointsdetailslist.add(new PointsdetailsBean("华夏银行 - 龙惠贷","2020-08-04","20200804112515","苏州是滴是滴网络科技有限公司","张某","13842323887","MM","用信","+10佣金积分","80,000.00"));
        pointsDetailsAdapter = new PointsDetailsAdapter(pointsdetailslist);
        thisMonthpointsRecyclerView.setAdapter(pointsDetailsAdapter);
        thisMonthpointsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL,false));
    }

    @OnClick(R.id.toobar_back)
    public void onViewClicked() {
        finish();
    }
}
