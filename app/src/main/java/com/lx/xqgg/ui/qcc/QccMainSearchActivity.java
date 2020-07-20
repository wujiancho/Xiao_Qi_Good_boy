package com.lx.xqgg.ui.qcc;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.product.bean.QccBean;
import com.lx.xqgg.ui.qcc.adapter.FuzzyqueryAdapter;
import com.lx.xqgg.ui.qcc.adapter.HistoryAdapter;
import com.lx.xqgg.ui.qcc.bean.FuzzyqueryQccBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class QccMainSearchActivity extends BaseActivity implements HistoryAdapter.onClearListener {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.et_cpmpany_name)
    EditText etCpmpanyName;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.v_clear_msg)
    View vClearMsg;
    @BindView(R.id.recycler_Fuzzyquery)
    RecyclerView recyclerFuzzyquery;

    private List<String> list = new ArrayList<>();
    private FuzzyqueryAdapter fuzzyqueryAdapter;
    private HistoryAdapter historyAdapter;
    private List<FuzzyqueryQccBean.ResultBean> arrayList;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_qcc_main_search;
    }

    @Override
    protected void initView() {
        etCpmpanyName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
                    //处理事件
                  //  search(etCpmpanyName.getText().toString().trim());
                    Fuzzyquerysearch(etCpmpanyName.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
         etCpmpanyName.setFocusable(true);
         etCpmpanyName.requestFocus();
        etCpmpanyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0){
                    return;
                }
                Fuzzyquerysearch(etCpmpanyName.getText().toString().trim());
            }
        });
        list = SharedPrefManager.getSearchHistory();
        historyAdapter = new HistoryAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(historyAdapter);
        historyAdapter.setOnClearListener(this);
        historyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                search(list.get(position));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = SharedPrefManager.getSearchHistory();
//        SharedPrefManager.setSearchHistory(list);
        historyAdapter.setNewData(list);
        historyAdapter.notifyDataSetChanged();
    }

    //企查查模糊查询
    private void Fuzzyquerysearch(String words) {
        addSubscribe(ApiManage.getInstance().getMainApi().getQiCc(words)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<String>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<String> stringBaseData) {
                        if (stringBaseData.isSuccess()) {
                            FuzzyqueryQccBean fuzzyqueryQccBean = new Gson().fromJson(stringBaseData.getData(), FuzzyqueryQccBean.class);
                            Log.e("qcc", new Gson().toJson(fuzzyqueryQccBean));
                            if ("202".equals(fuzzyqueryQccBean.getStatus())) {
                                toast("未查询相关信息，请确认企业名称输入无误");
                                return;
                            }
                            if ("传入参数有误，请检查".equals(fuzzyqueryQccBean.getMessage())) {
                                toast("未查询相关信息，请确认企业名称输入无误");
                                return;
                            }
                            arrayList = new ArrayList<>();
                            arrayList.addAll(fuzzyqueryQccBean.getResult());
                            fuzzyqueryAdapter = new FuzzyqueryAdapter(arrayList);
                            recyclerFuzzyquery.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                            recyclerFuzzyquery.setAdapter(fuzzyqueryAdapter);
                            fuzzyqueryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    search(fuzzyqueryAdapter.getData().get(position).getName());
                                }
                            });
                        } else {
                            toast(stringBaseData.getData());
                        }
                    }
                }));
    }

    //企查查详情
    private void search(String words) {
        addSubscribe(ApiManage.getInstance().getMainApi().getQiCcInfo(words)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<String>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<String> s) {
                        if (s.isSuccess()) {
                            QccBean qccBean = new Gson().fromJson(s.getData(), QccBean.class);
                            Log.e("qcc", new Gson().toJson(qccBean));
                            list = SharedPrefManager.getSearchHistory();
                            if (list == null) {
                                list = new ArrayList<>();
                                list.add(words);
                            } else {
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).equals(words)) {
                                        list.remove(list.get(i));
                                    }
                                }
                                list.add(0, words);
                            }
                            SharedPrefManager.setSearchHistory(list);
//                            Constans.qccBean=qccBean;
                            Intent intent = new Intent(mContext, QccCompanyResultMainActivity.class);
                            intent.putExtra("data", qccBean);
//                            intent.putExtra("name",words);
                            startActivity(intent);
                        } else {
                            toast(s.getData());
                        }
                    }
                }));
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.v_close, R.id.tv_clear, R.id.tv_search, R.id.v_clear_msg})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.tv_clear:
                SharedPrefManager.setSearchHistory(null);
                list = null;
                historyAdapter.setNewData(list);
                historyAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_search:
                // search(etCpmpanyName.getText().toString().trim());
                Fuzzyquerysearch(etCpmpanyName.getText().toString().trim());
                break;
            case R.id.v_clear_msg:
                etCpmpanyName.setText("");
                break;
        }
    }

    @Override
    public void onClear(String s) {
        list = historyAdapter.getData();
        list.remove(s);
        SharedPrefManager.setSearchHistory(list);
        historyAdapter.setNewData(list);
        historyAdapter.notifyDataSetChanged();
    }

}
