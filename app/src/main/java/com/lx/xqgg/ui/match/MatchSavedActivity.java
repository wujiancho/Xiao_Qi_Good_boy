package com.lx.xqgg.ui.match;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.match.adapter.MatchSaveFirstAdapter;
import com.lx.xqgg.ui.match.bean.MatchSaveFirstBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MatchSavedActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.yinh)
    TextView yinh;


    //    private MatchSavedAdapter matchSavedAdapter;
    private MatchSaveFirstAdapter matchSaveFirstAdapter;
    private List<MatchSaveFirstBean> list = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_match_saved;
    }

    @Override
    protected void initView() {
        tvTitle.setText("匹配结果");
        String ban=  SharedPrefManager.getImitationexamination().getPro_ban();
        yinh.setText("匹配结果仅供参考，实际申请以"+ban+"反馈为准。");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        matchSaveFirstAdapter = new MatchSaveFirstAdapter(list);
        recyclerView.setAdapter(matchSaveFirstAdapter);
        matchSaveFirstAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, MatchSavedDetailActivity.class);
                intent.putExtra("ids", list.get(position).getId());
                intent.putExtra("company", list.get(position).getCompany());
                startActivity(intent);
            }
        });

//        etSearch.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
//                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
//                    //处理事件
//                    List<MultiItemEntity> list=search(etSearch.getText().toString());
//                    list1.clear();
//                    list1.addAll(list);
//                    matchSavedAdapter.notifyDataSetChanged();
//                    return true;
//                }
//                return false;
//            }
//        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                List<MultiItemEntity> list=search(etSearch.getText().toString());
//                list1.clear();
//                list1.addAll(list);
//                matchSavedAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData() {
        getData("");
    }

    private void getData(String keywords) {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("search_words", keywords);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getAutoProduct(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<MatchSaveFirstBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<MatchSaveFirstBean>> listBaseData) {
                        Log.e("zlz", new Gson().toJson(listBaseData));
                        if (listBaseData.isSuccess()) {
                            if (null != listBaseData.getData() && listBaseData.getData().size() > 0) {
                                list.clear();
                                list.addAll(listBaseData.getData());
                                matchSaveFirstAdapter.notifyDataSetChanged();
                            }
                        } else {
                            toast(listBaseData.getMessage());
                        }
                    }
                }));
    }

    private List<MultiItemEntity> search(String s) {
        List<MultiItemEntity> list = new ArrayList<>();
        list.clear();
//        for (MatchSavedBean matchSavedBean : list2) {
//            matchSavedBean.getSubItems().clear();
//            if (matchSavedBean.getCompany().contains(s) || matchSavedBean.getLink_man().contains(s)) {
//                List<MatchSavedBean.ProductBean> productList = matchSavedBean.getProduct();
//                for (MatchSavedBean.ProductBean productBean : productList) {
//                    matchSavedBean.addSubItem(productBean);
//                }
//                list.add(matchSavedBean);
//            }
//        }
        return list;
    }

    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }

}
