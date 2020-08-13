package com.lx.xqgg.ui.mycommission;

import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.adapter.PointsDetailsAdapter;
import com.lx.xqgg.ui.mycommission.bean.ReturningservantBean;
import com.lx.xqgg.ui.mycommission.bean.ThisMothPointsdetailstBean;
import com.lx.xqgg.util.SpUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//返佣明细
public class RefundDetailsActivity extends BaseActivity {
    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.select_points_details)
    TextView selectPointsDetails;
    @BindView(R.id.points_details_RecyclerView)
    RecyclerView pointsDetailsRecyclerView;
    private String token;
    private List<ThisMothPointsdetailstBean> pointsdetailslist;
    private PointsDetailsAdapter pointsDetailsAdapter;
    private String month;
    private int integral;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund_details;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //获取用户token
        token = SharedPrefManager.getUser().getToken();
        month = getIntent().getStringExtra("month");
        integral = getIntent().getIntExtra("integral",-1);
        if (!"".equals(month)){
            //跳转时获取会动态改变
            toobarTitle.setText(month+"佣金积分明细");
            //当月佣金明细
            ThisMothpointsdetails(month);
        }
        if (!"".equals(integral)){
            DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
            String cashRebatez= df.format(integral);
            selectPointsDetails.setText(cashRebatez);
        }
    }


    @OnClick(R.id.toobar_back)
    public void onViewClicked() {
        finish();
    }

    //当月佣金明细
    private void ThisMothpointsdetails(String time){
        addSubscribe(ApiManage.getInstance().getMainApi().getThisMothPointsdetailst(token,time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<ThisMothPointsdetailstBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<ThisMothPointsdetailstBean>> listBaseData) {
                        if (listBaseData.isSuccess()){
                            if (listBaseData.getData()!= null && listBaseData.getData().size() > 0){
                                pointsdetailslist = new ArrayList<>();
                                pointsdetailslist.addAll(listBaseData.getData());
                                pointsDetailsAdapter = new PointsDetailsAdapter(pointsdetailslist);
                                pointsDetailsRecyclerView.setAdapter(pointsDetailsAdapter);
                                pointsDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                            }
                            else {
                                pointsDetailsAdapter = new PointsDetailsAdapter(pointsdetailslist);
                                pointsDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                                pointsDetailsRecyclerView.setAdapter(pointsDetailsAdapter);
                                pointsDetailsAdapter.setEmptyView(R.layout.layout_empty, pointsDetailsRecyclerView);
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        pointsDetailsAdapter = new PointsDetailsAdapter(pointsdetailslist);
                        pointsDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        pointsDetailsRecyclerView.setAdapter(pointsDetailsAdapter);
                        pointsDetailsAdapter.setEmptyView(R.layout.layout_empty, pointsDetailsRecyclerView);
                    }
                }));
    }
}
