package com.lx.xqgg.ui.mycommission;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.adapter.PointsDetailsAdapter;
import com.lx.xqgg.ui.mycommission.bean.ReturningservantBean;
import com.lx.xqgg.ui.mycommission.bean.ThisMothPointsdetailstBean;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    @BindView(R.id.vip_name)
    TextView vipName;
    @BindView(R.id.wudata)
    TextView wudata;
    private List<ThisMothPointsdetailstBean.DataBean> pointsdetailslist;
    private PointsDetailsAdapter pointsDetailsAdapter;
    private String time;
    private String vipname;

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

        vipname = getIntent().getStringExtra("vipname");
        if (!"".equals(vipname)) {
            vipName.setText("享受" + vipname + "返佣权益");
        }
        Returningaservant();
        //当月佣金明细
        ThisMothpointsdetails();
      /*  String returningservantdata = SpUtil.getInstance().getSpString("returningservantdata");
        if (!"".equals(returningservantdata)){
            ReturningservantBean returningservantBean = new Gson().fromJson(returningservantdata, ReturningservantBean.class);
            int cashRebate = returningservantBean.getCurrentMonthCharge();
            DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
            String cashRebatez= df.format(cashRebate);
            thisMonthPoints.setText(cashRebatez);
        }*/
    }

    @OnClick(R.id.toobar_back)
    public void onViewClicked() {
        finish();
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
                            thisMonthPoints.setText(thismothRebatez);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.getMessage());
                    }
                }));
    }

    //当月佣金明细
    private void ThisMothpointsdetails() {
        Date t = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        time = df.format(t);
        Log.d("tome00000", "ThisMothpointsdetails: " + time);
        Log.d("mytoken", "vipcard: " + SharedPrefManager.getUser().getToken());
        addSubscribe(ApiManage.getInstance().getMainApi().getThisMothPointsdetailst(SharedPrefManager.getUser().getToken(), time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<ThisMothPointsdetailstBean>(mContext, null) {
                    @Override
                    public void onNext(ThisMothPointsdetailstBean listBaseData) {
                        if (listBaseData.isSuccess()) {
                            if (listBaseData.getData() != null && listBaseData.getData().size() > 0) {
                                pointsdetailslist = new ArrayList<>();
                                pointsdetailslist.addAll(listBaseData.getData());
                                vipName.setText("享受" + listBaseData.getData().get(0).getChargeName() + "返佣权益");
                                pointsDetailsAdapter = new PointsDetailsAdapter(pointsdetailslist);
                                thisMonthpointsRecyclerView.setAdapter(pointsDetailsAdapter);
                                thisMonthpointsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                                thisMonthpointsRecyclerView.setVisibility(View.VISIBLE);
                                wudata.setVisibility(View.GONE);
                            } else {
                                thisMonthpointsRecyclerView.setVisibility(View.GONE);
                                wudata.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        thisMonthpointsRecyclerView.setVisibility(View.GONE);
                        wudata.setVisibility(View.VISIBLE);
                    }
                }));
    }


}
