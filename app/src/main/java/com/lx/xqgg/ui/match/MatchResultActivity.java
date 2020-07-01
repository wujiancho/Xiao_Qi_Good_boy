package com.lx.xqgg.ui.match;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.match.adapter.MatchResultAdapter;
import com.lx.xqgg.ui.match.bean.MatchRequestBean;
import com.lx.xqgg.ui.match.bean.MatchResultBean;
import com.lx.xqgg.ui.match.bean.MatchSavedBean;
import com.lx.xqgg.ui.match.bean.SaveRequestBean;
import com.lx.xqgg.ui.product.bean.ProductBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MatchResultActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.rv_result)
    RecyclerView rvResult;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_test_again)
    Button btnTestAgain;
    @BindView(R.id.tv_company_name)
    TextView tvCompany;
    @BindView(R.id.tv_fr_name)
    TextView tvFrName;
    @BindView(R.id.tv_time)
    TextView tvTime;

    private MatchRequestBean matchRequestBean;

    private MatchResultAdapter matchResultAdapter;

    private List<MatchResultBean> listProducts=new ArrayList<>();
    private List<MultiItemEntity> list1 = new ArrayList<>();

    private MaterialDialog confirmDialog;

    private MaterialDialog againDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_match_result;
    }

    @Override
    protected void initView() {
        tvTitle.setText("匹配结果");


        matchRequestBean = (MatchRequestBean) getIntent().getSerializableExtra("data");
        matchRequestBean.setToken(SharedPrefManager.getUser().getToken());

        tvCompany.setText(matchRequestBean.getCompanyName()+"");
        tvFrName.setText(matchRequestBean.getCustomerName()+"");

        Date date = new Date();

        String time = date.toLocaleString();

        Log.i("md", "时间time为： "+time);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String sim = dateFormat.format(date);


        tvTime.setText(sim);


        matchResultAdapter = new MatchResultAdapter(list1,false);

        rvResult.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvResult.setAdapter(matchResultAdapter);
        matchResultAdapter.setEmptyView(R.layout.layout_empty, rvResult);
        matchResultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        confirmDialog = new MaterialDialog.Builder(this)
                .title("提示")
                .content("该客户记录已保存至“我的—匹配结果”，保存30天。")
                .cancelable(false)
                .positiveText(R.string.yes)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        saveResult();
                        finish();
//                        if (MatchSecondActivity.instance != null) {
//                            MatchSecondActivity.instance.finish();
//                        }
//
//                        if (MatchFirstActivity.instance != null) {
//                            MatchFirstActivity.instance.finish();
//                        }
                        startActivity(new Intent(mContext,MatchSavedActivity.class));
                    }
                })
                .build();

        againDialog = new MaterialDialog.Builder(this)
                .title("")
                .content("")
                .cancelable(false)
                .positiveText(R.string.yes)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        saveResult();
                    }
                })
                .negativeText(R.string.no)
                .negativeColorRes(R.color.txt_normal)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
    }

    @Override
    protected void initData() {
        search();
    }

    /**
     * 查询接口
     */
    private void search() {
        Gson gson = new Gson();
        String obj = gson.toJson(matchRequestBean);
        Log.e("zlz", new Gson().toJson(matchRequestBean));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj);
        addSubscribe(ApiManage.getInstance().getMainApi().getMatchResult(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<MatchResultBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<MatchResultBean>> productBean) {
                        Log.e("zlz", new Gson().toJson(productBean));
                        if (!productBean.isSuccess()) {

                        } else {
                            if (productBean.getData() != null && productBean.getData().size() > 0) {
                                listProducts=productBean.getData();
                                list1.clear();

                                for (MatchResultBean matchSavedBean : listProducts) {
                                    List<MatchResultBean.ProductBean> productList = matchSavedBean.getProduct();
                                    for (MatchResultBean.ProductBean productBean1 : productList) {
                                        matchSavedBean.addSubItem(productBean1);
                                    }
                                    list1.add(matchSavedBean);
                                }

                                matchResultAdapter.setNewData(list1);
//                                listProducts.addAll(productBean.getData());
                                matchResultAdapter.expandAll();
                                matchResultAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }));
    }

    @OnClick({R.id.v_close, R.id.btn_save, R.id.btn_test_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_save:

                if (listProducts == null || listProducts.size() == 0) {
                    showDialog("无结果");
                    return;
                }
                confirmDialog.show();
//                saveResult();
                break;
            case R.id.btn_test_again:
//                againDialog.show();
//                search();
                startActivity(new Intent(mContext,MatchFirstActivity.class));
                finish();
                if (MatchSecondActivity.instance != null) {
                    MatchSecondActivity.instance.finish();
                }

                if (MatchFirstActivity.instance != null) {
                    MatchFirstActivity.instance.finish();
                }
                break;
        }
    }

    /**
     * 保存结果
     */
    private void saveResult() {

        SaveRequestBean saveRequestBean = new SaveRequestBean();
        saveRequestBean.setToken(SharedPrefManager.getUser().getToken());
        String productId = "";
        Set<Integer> set=new HashSet<>();
        for(MatchResultBean matchResultBean:listProducts){
            for(MatchResultBean.ProductBean bean:matchResultBean.getProduct()){
                set.add(bean.getId());
            }
        }

        for(Integer integer:set){
            productId = productId + "," + (integer+"");
        }

//        for (int i = 0; i < listProducts.size(); i++) {
////            productId = productId + "," + listProducts.get(i).getId();
//        }
        productId = productId.substring(1);
        saveRequestBean.setProductId(productId);
        saveRequestBean.setCondition(matchRequestBean);

        String obj = new Gson().toJson(saveRequestBean);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj);
        addSubscribe(ApiManage.getInstance().getMainApi().saveAutoProduct(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<Object>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<Object> objectBaseData) {
                        if (objectBaseData.isSuccess()) {
//                            confirmDialog.show();
                        } else {
                            toast(objectBaseData.getMessage() + "");
                        }
                    }
                }));
    }
}
