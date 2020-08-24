package com.lx.xqgg.ui.mycommission;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.adapter.AccumulatedRebateAdapter;
import com.lx.xqgg.ui.mycommission.adapter.ErrorAdapter;
import com.lx.xqgg.ui.mycommission.adapter.WithdrawalrecordAdapter;
import com.lx.xqgg.ui.mycommission.bean.HistoryCommissionwithdrawalBean;
import com.lx.xqgg.ui.mycommission.bean.ReturningservantBean;
import com.lx.xqgg.ui.mycommission.bean.WithdrawalrecordBean;
import com.lx.xqgg.util.SpUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//提现记录
public class WithdrawalRecordActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.accumulative_withdrawal)
    TextView accumulativeWithdrawal;
    @BindView(R.id.withdrawal_recordRecyclerView)
    RecyclerView withdrawalRecordRecyclerView;
    private List<HistoryCommissionwithdrawalBean.DataBean.ListBean> withdrawalrecordlist =new ArrayList<>();

    private WithdrawalrecordAdapter withdrawalrecordAdapter;
    private String token;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawal_record;
    }

    @Override
    protected void initView() {
       toobarTitle.setText("提现记录");
    }

    @Override
    protected void initData() {
        //获取用户token
        token = SharedPrefManager.getUser().getToken();
        /*String returningservantdata = SpUtil.getInstance().getSpString("returningservantdata");
        if (!"".equals(returningservantdata)){
            ReturningservantBean returningservantBean = new Gson().fromJson(returningservantdata, ReturningservantBean.class);
            int allRebate = returningservantBean.getAllCharge();
            DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
            String allRebatez= df.format(allRebate);
            accumulativeWithdrawal.setText("您已累计提现积分"+allRebatez+"积分");
        }*/
        Returningaservant();
        //提现数据获取
        Withdrawalrecord();
    }
    //提现数据获取
    private void Withdrawalrecord() {
        addSubscribe(ApiManage.getInstance().getMainApi().getHistoryCommissionwithdrawal(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<HistoryCommissionwithdrawalBean>(mContext, null) {
                    @Override
                    public void onNext(HistoryCommissionwithdrawalBean listBaseData) {
                       if (listBaseData.isSuccess()){
                            if (listBaseData.getData().getList()!=null&&listBaseData.getData().getList().size()>0){
                                withdrawalrecordlist.addAll(listBaseData.getData().getList());
                                withdrawalrecordAdapter = new WithdrawalrecordAdapter(withdrawalrecordlist);
                                withdrawalRecordRecyclerView.setAdapter(withdrawalrecordAdapter);
                                withdrawalRecordRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
                            }else {
                                List<String> error =new ArrayList<>();
                                ErrorAdapter eerr = new ErrorAdapter(error);
                                withdrawalRecordRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                                withdrawalRecordRecyclerView.setAdapter(eerr);
                                eerr.setEmptyView(R.layout.layout_empty, withdrawalRecordRecyclerView);
                            }
                        }
                    }
                                   @Override
                                   public void onError(Throwable t) {
                                       super.onError(t);
                                       List<String> error =new ArrayList<>();
                                       ErrorAdapter eerr = new ErrorAdapter(error);
                                       withdrawalRecordRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                                       withdrawalRecordRecyclerView.setAdapter(eerr);
                                       eerr.setEmptyView(R.layout.layout_empty, withdrawalRecordRecyclerView);
                                   }
                               }
                ));
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
                            accumulativeWithdrawal.setText("您已累计提现积分"+allRebatez+"积分");
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
