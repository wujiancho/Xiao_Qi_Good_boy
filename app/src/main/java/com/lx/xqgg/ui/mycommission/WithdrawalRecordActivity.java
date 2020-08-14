package com.lx.xqgg.ui.mycommission;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.lx.xqgg.ui.mycommission.bean.WithdrawalrecordBean;

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
}
