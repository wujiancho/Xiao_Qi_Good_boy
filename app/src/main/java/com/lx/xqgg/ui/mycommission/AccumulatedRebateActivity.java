package com.lx.xqgg.ui.mycommission;

import android.content.Intent;
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
import com.lx.xqgg.ui.mycommission.adapter.AccumulatedRebateAdapter;
import com.lx.xqgg.ui.mycommission.adapter.ErrorAdapter;
import com.lx.xqgg.ui.mycommission.adapter.PointsDetailsAdapter;
import com.lx.xqgg.ui.mycommission.bean.HistoryPointsdetailstBean;
import com.lx.xqgg.ui.mycommission.bean.ReturningservantBean;
import com.lx.xqgg.util.SpUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private List<HistoryPointsdetailstBean.DataBean.ListBean> historyaccumulatedPointslist;
    private AccumulatedRebateAdapter accumulatedRebateAdapter;
    private String token;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_accumulated_rebate;
    }

    @Override
    protected void initView() {
        //获取用户token
        token = SharedPrefManager.getUser().getToken();
        toobarTitle.setText("累计佣金");
        String returningservantdata = SpUtil.getInstance().getSpString("returningservantdata");
        if (!"".equals(returningservantdata)){
            ReturningservantBean returningservantBean = new Gson().fromJson(returningservantdata, ReturningservantBean.class);
            int allRebate = returningservantBean.getAllCharge();
            DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
            String allRebatez= df.format(allRebate);
            //网络获取积分
            accumulatedPoints.setText(allRebatez);
        }
    }

    @Override
    protected void initData() {
     //获取累计积分
        Accumulatedpoints();
    }


    @OnClick(R.id.toobar_back)
    public void onViewClicked() {
        finish();
    }

    private  void Accumulatedpoints(){
        addSubscribe(ApiManage.getInstance().getMainApi().getHistoryPointsdetailst(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<HistoryPointsdetailstBean>(mContext, null) {
                    @Override
                    public void onNext(HistoryPointsdetailstBean listBean) {
                        if (listBean.isSuccess()){
                            if (listBean.getData()!= null && listBean.getData().getList().size()>0){
                                historyaccumulatedPointslist = new ArrayList<>();
                                historyaccumulatedPointslist.addAll(listBean.getData().getList());
                                accumulatedRebateAdapter = new AccumulatedRebateAdapter(historyaccumulatedPointslist);
                                accumulatedrebateRecyclerView.setAdapter(accumulatedRebateAdapter);
                                accumulatedrebateRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
                                //点击条目跳转到点击月份的返佣明细
                                accumulatedRebateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                    @Override
                                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intentrd=new Intent(AccumulatedRebateActivity.this,RefundDetailsActivity.class);
                                        intentrd.putExtra("month",historyaccumulatedPointslist.get(position).getMonth());
                                        intentrd.putExtra("integral",historyaccumulatedPointslist.get(position).getProductCharge());
                                        startActivity(intentrd);
                                    }
                                });
                            }else {
                                List<String> error =new ArrayList<>();
                                ErrorAdapter eerr = new ErrorAdapter(error);
                                accumulatedrebateRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                                accumulatedrebateRecyclerView.setAdapter(eerr);
                                eerr.setEmptyView(R.layout.layout_empty, accumulatedrebateRecyclerView);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        List<String> error =new ArrayList<>();
                        ErrorAdapter eerr = new ErrorAdapter(error);
                        accumulatedrebateRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        accumulatedrebateRecyclerView.setAdapter(eerr);
                        eerr.setEmptyView(R.layout.layout_empty, accumulatedrebateRecyclerView);
                    }
                }));

    }
}
