package com.lx.xqgg.ui.product;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.home.UserServiceFragment;
import com.lx.xqgg.ui.home.bean.UserServiceBean;
import com.lx.xqgg.ui.product.adapter.CharacterAdapter;
import com.lx.xqgg.ui.product.bean.ProductBean;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ProductDetailActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_apply_num)
    TextView tvApplyNum;
    @BindView(R.id.tv_kded)
    TextView tvKded;
    @BindView(R.id.tv_rlv)
    TextView tvRlv;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_jkfs)
    TextView tvJkfs;
    @BindView(R.id.tv_hkfs)
    TextView tvHkfs;
    @BindView(R.id.iv_flow)
    ImageView ivFlow;
    @BindView(R.id.tv_sqtj)
    TextView tvSqtj;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.scrollview)
    ScrollView scrollView;
    @BindView(R.id.layout_undercarriage)
    LinearLayout layoutUnder;


    private ProductBean.RecordsBean bean;

    private ApplyFragment applyFragment;

    private UserServiceFragment userServiceFragment;

    private int id = 0;

    private List<ProductBean.RecordsBean.ClassifyListBean> characterList = new ArrayList<>();
    private CharacterAdapter characterAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initView() {
        id = getIntent().getIntExtra("data", -1);
        tvTitle.setText("产品详情");
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        characterAdapter = new CharacterAdapter(characterList);
        recyclerView.setAdapter(characterAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        getProductById();
    }

    /**
     * 根据id查找详情
     */
    private void getProductById() {
        Log.e("zlz", id + "");
        addSubscribe(ApiManage.getInstance().getMainApi().getLoanProductById(id, Constans.CITY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<ProductBean.RecordsBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<ProductBean.RecordsBean> recordsBeanBaseData) {
                        if (recordsBeanBaseData.isSuccess()) {
                            if (recordsBeanBaseData.getData() != null) {
                                bean = recordsBeanBaseData.getData();
                                String[] jkfs = bean.getGuarantee().split(",");
                                String jkfs1 = "";
                                String jkfs2 = "";
                                if (jkfs.length == 2) {
                                    jkfs1 = jkfs[0];
                                    jkfs2 = jkfs[1];
                                    switch (jkfs1) {
                                        case "ben":
                                            jkfs1 = "先息后本";
                                            break;
                                        case "jin":
                                            jkfs1 = "等额本金";
                                            break;
                                        case "xi":
                                            jkfs1 = "等额本息";
                                            break;
                                    }
                                    tvJkfs.setText(jkfs1);
                                    switch (jkfs2) {
                                        case "ben":
                                            jkfs2 = "先息后本";
                                            break;
                                        case "jin":
                                            jkfs2 = "等额本金";
                                            break;
                                        case "xi":
                                            jkfs2 = "等额本息";
                                            break;
                                    }
                                    tvHkfs.setText(jkfs2);
                                } else {
                                    jkfs1 = jkfs[0];
                                    switch (jkfs1) {
                                        case "ben":
                                            jkfs1 = "先息后本";
                                            break;
                                        case "jin":
                                            jkfs1 = "等额本金";
                                            break;
                                        case "xi":
                                            jkfs1 = "等额本息";
                                            break;
                                    }
                                    tvJkfs.setText(jkfs1);
                                    tvHkfs.setVisibility(View.GONE);
                                }
                                Log.e("zlz", new Gson().toJson(bean));

                                characterList.clear();
                                characterList.addAll(bean.getFeatureList());
                                characterAdapter.notifyDataSetChanged();


                                tvName.setText(bean.getTitle() + "");
                                Glide.with(mContext)
                                        .load(Config.IMGURL + bean.getImage())
                                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                                        .into(ivLogo);
                                tvApplyNum.setText(bean.getApply_num() + "人已申请");
                                tvKded.setText(bean.getQuota() / 10000 + "万");
                                tvRlv.setText(bean.getRate() + "");
                                tvMonth.setText(bean.getLimitMonth() + "");
                                Glide.with(mContext)
                                        .load(Config.IMGURL + bean.getFlowImage())
                                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                                        .into(ivFlow);
                                tvSqtj.setText(Html.fromHtml(bean.getContent() + ""));
                                return;
                            }
                        } else {
                            if(recordsBeanBaseData.getCode()==4040){
                                scrollView.setVisibility(View.GONE);
                                layoutBottom.setVisibility(View.GONE);
                                layoutUnder.setVisibility(View.VISIBLE);
                            }else {
                                toast(recordsBeanBaseData.getMessage());
                            }
                        }
                    }
                }));
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.v_close, R.id.btn_apply, R.id.btn_kf})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_apply:
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("token", SharedPrefManager.getUser().getToken());
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                addSubscribe(ApiManage.getInstance().getMainApi().getUserServiceInfo(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseSubscriber<BaseData<UserServiceBean>>(mContext, null) {
                            @Override
                            public void onNext(BaseData<UserServiceBean> objectBaseData) {
                                Log.e("zlz", new Gson().toJson(objectBaseData));
                                if (objectBaseData.isSuccess()) {
                                    if (objectBaseData.getCode() != 1) {
                                        userServiceFragment = new UserServiceFragment(objectBaseData);
                                        userServiceFragment.show(getSupportFragmentManager(), "");
                                    } else {
                                        applyFragment = new ApplyFragment(bean);
                                        applyFragment.show(getSupportFragmentManager(), null);
                                    }
                                } else {
                                    toast(objectBaseData.getMessage());
                                }
                            }
                        }));

                break;
            case R.id.btn_kf:
                String title = "聊天窗口的标题";
                ConsultSource source = new ConsultSource(bean.getTitle(), bean.getTitle(), "custom information string");
                Unicorn.openServiceActivity(mContext, title, source);
                break;
        }
    }
}
