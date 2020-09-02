package com.lx.xqgg.ui.mycommission;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.adapter.ErrorAdapter;
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
    private List<ThisMothPointsdetailstBean.DataBean> pointsdetailslist;
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
          month = getIntent().getStringExtra("month");
        Log.d("getTokenddddd", "initData: "+SharedPrefManager.getUser().getToken());
        //  if (!"".equals(token)||token!=null){
        // }
        integral = getIntent().getIntExtra("integral",-1);
        if (!"".equals(month)&&month!=null){
            //跳转时获取会动态改变
            toobarTitle.setText(month+" 佣金积分明细");
        }
        if (!"".equals(integral)){
            DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
            String cashRebatez= df.format(integral);
            selectPointsDetails.setText(cashRebatez);
        }
        ThisMothpointsdetails();
    }


    @OnClick(R.id.toobar_back)
    public void onViewClicked() {
        finish();
    }

    //当月佣金明细
    private void ThisMothpointsdetails(){
        Log.d("mytoken", "vipcard: " + SharedPrefManager.getUser().getToken());
        addSubscribe(ApiManage.getInstance().getMainApi().getThisMothPointsdetailst(SharedPrefManager.getUser().getToken(),month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<ThisMothPointsdetailstBean>(mContext, null) {

                    @Override
                    public void onNext(ThisMothPointsdetailstBean listBaseData) {
                        if (listBaseData.isSuccess()){
                            Log.d("listBaseData/////", "onNext: "+listBaseData.getData());
                            if (listBaseData.getData()!= null && listBaseData.getData().size() > 0){
                                pointsdetailslist = new ArrayList<>();
                                pointsdetailslist.addAll(listBaseData.getData());
                                pointsDetailsAdapter = new PointsDetailsAdapter(pointsdetailslist);
                                pointsDetailsRecyclerView.setAdapter(pointsDetailsAdapter);
                                pointsDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                            }
                            else {
                                List<String> error =new ArrayList<>();
                                ErrorAdapter eerr = new ErrorAdapter(error);
                                pointsDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                                pointsDetailsRecyclerView.setAdapter(eerr);
                                eerr.setEmptyView(R.layout.layout_empty, pointsDetailsRecyclerView);
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        List<String> error =new ArrayList<>();
                        ErrorAdapter eerr = new ErrorAdapter(error);
                        pointsDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        pointsDetailsRecyclerView.setAdapter(eerr);
                        eerr.setEmptyView(R.layout.layout_empty, pointsDetailsRecyclerView);
                    }
                }));
    }

    //返佣方法
    public void Returningaservant() {
        addSubscribe(ApiManage.getInstance().getMainApi().getReturningservant(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<ReturningservantBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<ReturningservantBean> returningservantBeanBaseData) {
                        if (returningservantBeanBaseData.isSuccess()) {
                            ReturningservantBean data = returningservantBeanBaseData.getData();
                            int allRebate = data.getAllCharge();
                            int cashRebate = data.getCashCharge();
                            int thismothRebate = data.getCurrentMonthCharge();
                            DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
                            String allRebatez = df.format(allRebate);//累计返佣
                            String cashRebatez = df.format(cashRebate);//可提返佣
                            String thismothRebatez = df.format(thismothRebate);//本月返佣
                            SpUtil.getInstance().saveString("returningservantdata", new Gson().toJson(returningservantBeanBaseData.getData()));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.getMessage());
                    }
                }));
    }
}
