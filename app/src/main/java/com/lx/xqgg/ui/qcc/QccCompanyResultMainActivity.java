package com.lx.xqgg.ui.qcc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.ui.product.bean.QccBean;
import com.lx.xqgg.ui.qcc.adapter.GdItemAdapter;
import com.lx.xqgg.ui.qcc.adapter.GgItemAdapter;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QccCompanyResultMainActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.tv_fddbr)
    TextView tvFddbr;
    @BindView(R.id.tv_zczb)
    TextView tvZczb;
    @BindView(R.id.tv_clsj)
    TextView tvClsj;
    @BindView(R.id.layout_gsxx)
    LinearLayout layoutGsxx;
    @BindView(R.id.tv_zyry_num)
    TextView tvZyryNum;
    @BindView(R.id.layout_zyry)
    LinearLayout layoutZyry;
    @BindView(R.id.tv_gdry_num)
    TextView tvGdryNum;
    @BindView(R.id.layout_gdxx)
    LinearLayout layoutGdxx;
    @BindView(R.id.layout_bgjl)
    LinearLayout layoutBgjl;
    @BindView(R.id.tv_jyyc_num)
    TextView tvJyycNum;
    @BindView(R.id.iv_jyyc)
    ImageView ivJyyc;
    @BindView(R.id.layout_jyyc)
    LinearLayout layoutJyyc;
    @BindView(R.id.tv_sxxx_num)
    TextView tvSxxxNum;
    @BindView(R.id.iv_sxxx)
    ImageView ivSxxx;
    @BindView(R.id.layout_sxxx)
    LinearLayout layoutSxxx;
    @BindView(R.id.tv_xzcf_num)
    TextView tvXzcfNum;
    @BindView(R.id.iv_xzcf)
    ImageView ivXzcf;
    @BindView(R.id.layout_xzcf)
    LinearLayout layoutXzcf;
    @BindView(R.id.tv_bzxr_num)
    TextView tvBzxrNum;
    @BindView(R.id.iv_bzxr)
    ImageView ivBzxr;
    @BindView(R.id.layout_bzxr)
    LinearLayout layoutBzxr;
    @BindView(R.id.tv_dcdy_num)
    TextView tvDcdyNum;
    @BindView(R.id.iv_dcdy)
    ImageView ivDcdy;
    @BindView(R.id.layout_dcdy)
    LinearLayout layoutDcdy;
    @BindView(R.id.tv_gqcz_num)
    TextView tvGqczNum;
    @BindView(R.id.iv_gqcz)
    ImageView ivGqcz;
    @BindView(R.id.layout_gqcz)
    LinearLayout layoutGqcz;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv_gd_num)
    TextView tvGdNum;
    @BindView(R.id.tv_gg_num)
    TextView tvGgNum;
    @BindView(R.id.recycler_view_gd)
    RecyclerView rvGd;
    @BindView(R.id.recycler_view_gg)
    RecyclerView rvGg;

    private QccBean qccBean;
    private GdItemAdapter gdItemAdapter;
    private GgItemAdapter ggItemAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qcc_company_result_main;
    }

    @Override
    protected void initView() {
        qccBean = (QccBean) getIntent().getSerializableExtra("data");
        tvTitle.setText(qccBean.getResult().getName());
        if(qccBean!=null) {
            tvFddbr.setText(qccBean.getResult().getOperName() + "");
            tvZczb.setText(qccBean.getResult().getRegistCapi().replace("元人民币", ""));
            tvClsj.setText(qccBean.getResult().getStartDate().replace("00:00:00", "").trim());

            gdItemAdapter = new GdItemAdapter(qccBean.getResult().getPartners(), qccBean.getResult().getName());
            rvGd.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
            rvGd.setAdapter(gdItemAdapter);

            ggItemAdapter = new GgItemAdapter(qccBean.getResult().getEmployees(), qccBean.getResult().getName());
            rvGg.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
            rvGg.setAdapter(ggItemAdapter);

            if (qccBean.getResult().getEmployees() != null && qccBean.getResult().getEmployees().size() > 0) {
                tvZyryNum.setText(qccBean.getResult().getEmployees().size() + "");
                tvGgNum.setText("高\n管\n" + qccBean.getResult().getEmployees().size());
            } else {
                tvZyryNum.setText("");
            }

            if (qccBean.getResult().getPartners() != null && qccBean.getResult().getPartners().size() > 0) {
                tvGdryNum.setText(qccBean.getResult().getPartners().size() + "");
                tvGdNum.setText("股\n东\n" + qccBean.getResult().getPartners().size());
            } else {
                tvGdryNum.setText("");
            }

            if (qccBean.getResult().getExceptions() != null && qccBean.getResult().getExceptions().size() > 0) {
                tvJyycNum.setText(qccBean.getResult().getExceptions().size() + "");
                Glide.with(mContext)
                        .load(R.drawable.ic_qcc_jyyc_red)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into(ivJyyc);
                tv1.setTextColor(getResources().getColor(R.color.txt_normal));
            } else {
                tvJyycNum.setText("");
                Glide.with(mContext)
                        .load(R.drawable.ic_qcc_jyyc_gray)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into(ivJyyc);
                tv1.setTextColor(getResources().getColor(R.color.txt_gray));
            }

            if (qccBean.getResult().getShiXinItems() != null && qccBean.getResult().getShiXinItems().size() > 0) {
                tvSxxxNum.setText(qccBean.getResult().getShiXinItems().size() + "");
                Glide.with(mContext)
                        .load(R.drawable.ic_qcc_sxxx_red)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into(ivSxxx);
                tv2.setTextColor(getResources().getColor(R.color.txt_normal));
            } else {
                tvSxxxNum.setText("");
                Glide.with(mContext)
                        .load(R.drawable.ic_qcc_sxxx_gray)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into(ivSxxx);
                tv2.setTextColor(getResources().getColor(R.color.txt_gray));
            }

//        if ((Constans.qccBean.getResult().getPenaltyCreditChina() != null && Constans.qccBean.getResult().getPenaltyCreditChina().size() > 0)
//        ||(Constans.qccBean.getResult().getPenalty()!=null&&Constans.qccBean.getResult().getPenalty().size()>0)) {
//            tvXzcfNum.setText((Constans.qccBean.getResult().getPenaltyCreditChina().size() +
//                    +Constans.qccBean.getResult().getPenalty().size())+"");
//            Glide.with(mContext)
//                    .load(R.drawable.ic_qcc_xzcf_red)
//                    .apply(new RequestOptions().placeholder(R.drawable.ic_default))
//                    .into(ivXzcf);
//        } else {
//            tvXzcfNum.setText("");
//            Glide.with(mContext)
//                    .load(R.drawable.ic_qcc_xzcf_gray)
//                    .apply(new RequestOptions().placeholder(R.drawable.ic_default))
//                    .into(ivXzcf);
//        }

            if (qccBean.getResult().getZhiXingItems() != null && qccBean.getResult().getZhiXingItems().size() > 0) {
                tvBzxrNum.setText(qccBean.getResult().getZhiXingItems().size() + "");
                Glide.with(mContext)
                        .load(R.drawable.ic_qcc_bcxr_red)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into(ivBzxr);
                tv3.setTextColor(getResources().getColor(R.color.txt_normal));
            } else {
                tvBzxrNum.setText("");
                Glide.with(mContext)
                        .load(R.drawable.ic_qcc_bcxr_gray)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into(ivBzxr);
                tv3.setTextColor(getResources().getColor(R.color.txt_gray));
            }

            if (qccBean.getResult().getMPledge() != null && qccBean.getResult().getMPledge().size() > 0) {
                tvDcdyNum.setText(qccBean.getResult().getMPledge().size() + "");
                Glide.with(mContext)
                        .load(R.drawable.ic_qcc_dcdy_red)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into(ivDcdy);
                tv4.setTextColor(getResources().getColor(R.color.txt_normal));
            } else {
                tvDcdyNum.setText("");
                Glide.with(mContext)
                        .load(R.drawable.ic_qcc_dcdy_gray)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into(ivDcdy);
                tv4.setTextColor(getResources().getColor(R.color.txt_gray));
            }

            if (qccBean.getResult().getPledge() != null && qccBean.getResult().getPledge().size() > 0) {
                tvGqczNum.setText(qccBean.getResult().getPledge().size() + "");
                Glide.with(mContext)
                        .load(R.drawable.ic_qcc_gqcz_red)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into(ivGqcz);
                tv5.setTextColor(getResources().getColor(R.color.txt_normal));
            } else {
                tvGqczNum.setText("");
                Glide.with(mContext)
                        .load(R.drawable.ic_qcc_gqcz_gray)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into(ivGqcz);
                tv5.setTextColor(getResources().getColor(R.color.txt_gray));
            }
        }else {
            toast("无结果");
        }

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.v_close, R.id.layout_gsxx, R.id.layout_zyry, R.id.layout_gdxx, R.id.layout_bgjl, R.id.layout_jyyc, R.id.layout_sxxx, R.id.layout_xzcf, R.id.layout_bzxr, R.id.layout_dcdy, R.id.layout_gqcz})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.layout_gsxx:
                intent = new Intent(mContext, BusinessInfoActivity.class);
                intent.putExtra("data", qccBean);
                startActivity(intent);
                break;
            case R.id.layout_zyry:
                intent = new Intent(mContext, KeyPersonActivity.class);
                intent.putExtra("data", qccBean);
                startActivity(intent);
                break;
            case R.id.layout_gdxx:
                intent = new Intent(mContext, StockHolderActivity.class);
                intent.putExtra("data", qccBean);
                startActivity(intent);
                break;
            case R.id.layout_bgjl:
                intent = new Intent(mContext, ChangeRecordActivity.class);
                intent.putExtra("data", qccBean);
                startActivity(intent);
                break;
            case R.id.layout_jyyc:
                intent = new Intent(mContext, ExceptionsActivity.class);
                intent.putExtra("data", qccBean);
                startActivity(intent);
                break;
            case R.id.layout_sxxx:
                intent = new Intent(mContext, ShiXinActivity.class);
                intent.putExtra("data", qccBean);
                startActivity(intent);
                break;
//            case R.id.layout_xzcf:
//                intent = new Intent(mContext, XinZhenActivity.class);
//                intent.putExtra("data",qccBean);
//                startActivity(intent);
//                break;
            case R.id.layout_bzxr:
                intent = new Intent(mContext, BcxrActivity.class);
                intent.putExtra("data", qccBean);
                startActivity(intent);
                break;
            case R.id.layout_dcdy:
                intent = new Intent(mContext, DcdyActivity.class);
                intent.putExtra("data", qccBean);
                startActivity(intent);
                break;
            case R.id.layout_gqcz:
                intent = new Intent(mContext, GqczActivity.class);
                intent.putExtra("data", qccBean);
                startActivity(intent);
                break;
        }
    }
}
