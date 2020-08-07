package com.lx.xqgg.ui.qcc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.product.bean.QccBean;
import com.lx.xqgg.ui.qcc.bean.QccPersonBean;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 工商信息
 */
public class BusinessInfoActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.tv_company_num)
    TextView tvCompanyNum;
    @BindView(R.id.iv_company_logo)
    ImageView ivCompanyLogo;
    @BindView(R.id.tv_fddbr_name)
    TextView tvFddbrName;
    @BindView(R.id.tv_strat_time)
    TextView tvStratTime;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_register_money)
    TextView tvRegisterMoney;
    @BindView(R.id.tv_real_money)
    TextView tvRealMoney;
    @BindView(R.id.tv_social_code)
    TextView tvSocialCode;
    @BindView(R.id.tv_gszch_code)
    TextView tvGszchCode;
    @BindView(R.id.tv_zzjgdm_code)
    TextView tvZzjgdmCode;
    @BindView(R.id.tv_nsr_code)
    TextView tvNsrCode;
    @BindView(R.id.tv_nsr_zz)
    TextView tvNsrZz;
    @BindView(R.id.tv_company_type)
    TextView tvCompanyType;
    @BindView(R.id.tv_hy)
    TextView tvHy;
    @BindView(R.id.tv_people_num)
    TextView tvPeopleNum;
    @BindView(R.id.tv_yy_time)
    TextView tvYyTime;
    @BindView(R.id.tv_cbrs_num)
    TextView tvCbrsNum;
    @BindView(R.id.tv_djjg)
    TextView tvDjjg;
    @BindView(R.id.tv_hzrq)
    TextView tvHzrq;
    @BindView(R.id.tv_register_area)
    TextView tvRegisterArea;
    @BindView(R.id.tv_jyfw)
    TextView tvJyfw;
    @BindView(R.id.nsrsbh)
    TextView nsrsbh;
    @BindView(R.id.nsrzz)
    TextView nsrzz;

    private QccBean qccBean;
    private String nas;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_business_info;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        qccBean = (QccBean) getIntent().getSerializableExtra("data");
        tvTitle.setText("工商信息");
        nas = SharedPrefManager.getImitationexamination().getPro_nas();
        nsrsbh.setText(nas + "人识别号");
        nsrzz.setText(nas+"人资质");
        Glide.with(mContext)
                .load(qccBean.getResult().getImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                .into(ivCompanyLogo);
        tvFddbrName.setText(qccBean.getResult().getOperName() + "");
        tvStratTime.setText(qccBean.getResult().getStartDate().replace("00:00:00", "").trim());
        tvStatus.setText(qccBean.getResult().getStatus() + "");
        tvRegisterMoney.setText(qccBean.getResult().getRegistCapi() + "");
        tvRealMoney.setText(qccBean.getResult().getRecCap() + "");
        tvSocialCode.setText(qccBean.getResult().getCreditCode() + "");
        tvGszchCode.setText(qccBean.getResult().getNo() + "");
        tvZzjgdmCode.setText(qccBean.getResult().getOrgNo() + "");
        //ns人编号
        tvNsrCode.setText(qccBean.getResult().getCreditCode() + "");
        tvCompanyType.setText(qccBean.getResult().getEconKind() + "");
        tvHy.setText(qccBean.getResult().getIndustry().getIndustry() + "");
        tvPeopleNum.setText(qccBean.getResult().getPersonScope() + "");
        tvYyTime.setText(qccBean.getResult().getTermStart().replace("00:00:00", "").trim() + "至" + qccBean.getResult().getTeamEnd().replace("00:00:00", "").trim());
        tvCbrsNum.setText(qccBean.getResult().getInsuredCount() + "");
        tvDjjg.setText(qccBean.getResult().getBelongOrg() + "");
        tvHzrq.setText(qccBean.getResult().getCheckDate().replace("00:00:00", "").trim());
        tvRegisterArea.setText(qccBean.getResult().getAddress() + "");
        tvJyfw.setText(qccBean.getResult().getScope() + "");
    }

    private void getSeniorPerson() {
        addSubscribe(ApiManage.getInstance().getMainApi().getSeniorPerson(qccBean.getResult().getName(), qccBean.getResult().getOperName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<String>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<String> stringBaseData) {
                        if (stringBaseData.isSuccess()) {
                            QccPersonBean qccPersonBean = new Gson().fromJson(stringBaseData.getData(), QccPersonBean.class);
                            Log.e("zlz", new Gson().toJson(qccPersonBean));
                            if (qccPersonBean.getPaging() != null) {
                                tvCompanyNum.setText("他有" + qccPersonBean.getPaging().getTotalRecords() + "家公司");

                                tvCompanyNum.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(mContext, LinkCompanyListActivity.class);
                                        intent.putExtra("name", qccBean.getResult().getOperName());
                                        intent.putExtra("data", qccPersonBean);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }
                }));
    }

    @Override
    protected void initData() {
        getSeniorPerson();
    }


    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }


}
