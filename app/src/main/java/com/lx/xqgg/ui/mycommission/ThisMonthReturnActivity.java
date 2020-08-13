package com.lx.xqgg.ui.mycommission;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
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
    private List<ThisMothPointsdetailstBean> pointsdetailslist;
    private PointsDetailsAdapter pointsDetailsAdapter;
    private String token;
    private String time;

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
        //获取用户token
        token = SharedPrefManager.getUser().getToken();
        //当月佣金明细
        ThisMothpointsdetails();
        String returningservantdata = SpUtil.getInstance().getSpString("returningservantdata");
        if (!"".equals(returningservantdata)){
            ReturningservantBean returningservantBean = new Gson().fromJson(returningservantdata, ReturningservantBean.class);
            int cashRebate = returningservantBean.getCurrentMonthCharge();
            DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
            String cashRebatez= df.format(cashRebate);
            thisMonthPoints.setText(cashRebatez);
        }
    }

    @OnClick(R.id.toobar_back)
    public void onViewClicked() {
        finish();
    }
    //当月佣金明细
    private void ThisMothpointsdetails(){
        Date t = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm");
        time = df.format(t);
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
                              vipName.setText("享受"+listBaseData.getData().get(0).getChargeName()+"返佣权益");
                              pointsDetailsAdapter = new PointsDetailsAdapter(pointsdetailslist);
                              thisMonthpointsRecyclerView.setAdapter(pointsDetailsAdapter);
                              thisMonthpointsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                          }
                          else {
                              pointsDetailsAdapter = new PointsDetailsAdapter(pointsdetailslist);
                              thisMonthpointsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                              thisMonthpointsRecyclerView.setAdapter(pointsDetailsAdapter);
                              pointsDetailsAdapter.setEmptyView(R.layout.layout_empty, thisMonthpointsRecyclerView);
                          }

                      }
                  }

                  @Override
                  public void onError(Throwable t) {
                      super.onError(t);
                      pointsDetailsAdapter = new PointsDetailsAdapter(pointsdetailslist);
                      thisMonthpointsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                      thisMonthpointsRecyclerView.setAdapter(pointsDetailsAdapter);
                      pointsDetailsAdapter.setEmptyView(R.layout.layout_empty, thisMonthpointsRecyclerView);
                  }
              }));
    }


}
