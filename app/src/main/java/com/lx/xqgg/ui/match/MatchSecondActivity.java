package com.lx.xqgg.ui.match;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.match.bean.MatchRequestBean;
import com.lx.xqgg.ui.product.bean.QccBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MatchSecondActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.tv_sqr_select)
    TextView tvSqrSelect;
    @BindView(R.id.tv_company_type_select)
    TextView tvCompanyTypeSelect;
    @BindView(R.id.tv_tax_level)
    TextView tvTaxLevel;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.et_zgbl)
    EditText etZgbl;
    @BindView(R.id.et_frbg_month)
    EditText etFrbgMonth;
    @BindView(R.id.et_cl_month)
    EditText etClMonth;
    @BindView(R.id.et_12_kp)
    EditText et12Kp;
    @BindView(R.id.et_12_ns)
    EditText et12Ns;
    @BindView(R.id.et_jy_fd_count)
    EditText etJyFdCount;
    @BindView(R.id.et_jy_fd_number)
    EditText etJyFdNumber;
    @BindView(R.id.et_2_count)
    EditText et2Count;
    @BindView(R.id.et_6_count)
    EditText et6Count;
    @BindView(R.id.tv_company_hy_selector)
    TextView getTvCompanyTypeSelect;
    @BindView(R.id.dai)
    TextView dai;
    @BindView(R.id.dai12)
    TextView dai12;
    @BindView(R.id.dai6)
    TextView dai6;
    @BindView(R.id.nsdj)
    TextView nsdj;
    @BindView(R.id.ns12)
    TextView ns12;
    @BindView(R.id.pe)
    TextView pe;
    @BindView(R.id.nspe)
    TextView nspe;
    @BindView(R.id.kae)
    TextView kae;

    private MatchRequestBean matchRequestBean;

    public static MatchSecondActivity instance;

    private String[] level = {"A", "B", "M", "C", "D", "暂无"};
    private String[] levelId = {"1", "2", "3", "4", "5", ""};
    private ListPopupWindow lvlWindow;
    private String[] companyZw = {"法人", "股东", "法人/股东", "个人"};
    private ListPopupWindow sqrZwWindow;
    private String[] companyType = {"个体工商户", "有限责任公司", "有限合伙公司"};
    private ListPopupWindow typeWindow;

    /**
     * 职务
     **/

    private String beanPosition = "";
    /**
     * ns等级
     **/
    private String taxLevel = "";

    private String beanCompanyType = "";
    private String loa;
    private String nas;
    private String mon;
    private String wan;
    private String kai;
    private String shui;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_match_second;
    }

    @Override
    protected void initView() {
        instance = this;
        matchRequestBean = (MatchRequestBean) getIntent().getSerializableExtra("data");
        tvTitle.setText("智能匹配");
        loa = SharedPrefManager.getImitationexamination().getPro_loa();
        nas = SharedPrefManager.getImitationexamination().getPro_nas();
        wan = SharedPrefManager.getImitationexamination().getPro_wan();
        kai = SharedPrefManager.getImitationexamination().getPro_kai();
        shui = SharedPrefManager.getImitationexamination().getPro_shui();
        dai.setText("经营性" + loa + "笔数");
        dai6.setText("近6个月" + loa + "查询");
        dai12.setText("近12个月" + loa + "查询");
        nsdj.setText(nas + "等级");
        ns12.setText("近12月累计" + shui);
        pe.setText(wan + "元");
        nspe.setText(wan + "元");
        kae.setText("近12月累计"+kai);
        checkCompany(matchRequestBean.getCompanyName());
        etZgbl.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });

        et12Kp.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });

        et12Ns.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });

        etJyFdNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });

    }

    /**
     * 调用企查查查询
     *
     * @param company
     */
    private void checkCompany(String company) {
        addSubscribe(ApiManage.getInstance().getMainApi().getQiCcInfo(company)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<String>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<String> s) {
                        if (s.isSuccess()) {
                            Log.e("zlz", company + "");
                            QccBean qccBean = new Gson().fromJson(s.getData(), QccBean.class);
//                            Log.e("zlz", new Gson().toJson(qccBean));
                            if (qccBean.getMessage().equals("查询无结果")) {
                                toast("未查询相关信息，请确认企业名称输入无误");
                                return;
                            }
                            try {
                                getTvCompanyTypeSelect.setText(qccBean.getResult().getIndustry().getIndustry() + "");
                            } catch (Exception e) {
                                Log.e("zlz", e.toString());
                            }

                            try {
                                List<QccBean.ResultBean.PartnersBean> list = qccBean.getResult().getPartners();
                                for (QccBean.ResultBean.PartnersBean bean : list) {
                                    if (bean.getStockName().equals(matchRequestBean.getCustomerName())) {
                                        String percent = bean.getStockPercent().substring(0, bean.getStockPercent().indexOf("%"));
                                        etZgbl.setText(percent.trim());
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("zlz", e.toString());
                            }

                            try {
                                boolean isgetMsg = false;
                                List<QccBean.ResultBean.ChangeRecordsBean> list = qccBean.getResult().getChangeRecords();
                                for (QccBean.ResultBean.ChangeRecordsBean bean : list) {
                                    if (bean.getProjectName().equals(matchRequestBean.getCustomerName())) {
                                        isgetMsg = true;
                                        etFrbgMonth.setText(getMonthCha(bean.getChangeDate()) + "");
                                    }
                                }
                                if (!isgetMsg) {
                                    etFrbgMonth.setText(getMonthCha(qccBean.getResult().getStartDate()) + "");
                                }
                            } catch (Exception e) {

                            }
                            Log.e("zlz", qccBean.getResult().getStartDate() + "");
                            try {
                                etClMonth.setText(getMonthCha(qccBean.getResult().getStartDate()) + "");
                            } catch (Exception e) {
                                Log.e("zlz", e.toString());
                            }


                        }
                    }
                }));
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.v_close, R.id.tv_sqr_select, R.id.tv_company_type_select, R.id.tv_tax_level, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.tv_sqr_select:
                selectZw();
                break;
            case R.id.tv_company_type_select:
                selectType();
                break;
            case R.id.tv_tax_level:
                selectLevel();
                break;
            case R.id.btn_commit:
                commit();
                break;
        }
    }

    private void selectZw() {
        if (sqrZwWindow == null) {
            sqrZwWindow = new ListPopupWindow(mContext);
            sqrZwWindow.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, companyZw));
            sqrZwWindow.setWidth(360);
            sqrZwWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            sqrZwWindow.setAnchorView(tvSqrSelect);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
            sqrZwWindow.setDropDownGravity(Gravity.END);
            sqrZwWindow.setModal(true);//设置是否是模式
            sqrZwWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tvSqrSelect.setText(companyZw[position]);
                    sqrZwWindow.dismiss();
                    beanPosition = (position + 1) + "";
                }
            });
        }
        sqrZwWindow.show();
    }

    private void selectLevel() {
        if (lvlWindow == null) {
            lvlWindow = new ListPopupWindow(mContext);
            lvlWindow.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, level));
            lvlWindow.setWidth(240);
            lvlWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            lvlWindow.setAnchorView(tvTaxLevel);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
            lvlWindow.setDropDownGravity(Gravity.END);
            lvlWindow.setModal(true);//设置是否是模式
            lvlWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tvTaxLevel.setText(level[position]);
                    lvlWindow.dismiss();
//                    taxLevel = level[position];
                    taxLevel = levelId[position];
                }
            });
        }
        lvlWindow.show();
    }

    private void selectType() {
        if (typeWindow == null) {
            typeWindow = new ListPopupWindow(mContext);
            typeWindow.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, companyType));
            typeWindow.setWidth(420);
            typeWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            typeWindow.setAnchorView(tvCompanyTypeSelect);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
            typeWindow.setDropDownGravity(Gravity.END);
            typeWindow.setModal(true);//设置是否是模式
            typeWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tvCompanyTypeSelect.setText(companyType[position]);
                    typeWindow.dismiss();
                    if (companyType[position].contains("个体")) {
                        beanCompanyType = "个体";
                    } else {
                        beanCompanyType = "有限公司";
                    }
                }
            });
        }
        typeWindow.show();
    }

    /**
     * 提交接口
     */
    private void commit() {
        if (TextUtils.isEmpty(beanPosition)) {
            toast("请选择公司职务");
            return;
        }
        if (TextUtils.isEmpty(etZgbl.getText().toString().trim())) {
            toast("请填写申请人占股比例");
            return;
        }
        if (TextUtils.isEmpty(etFrbgMonth.getText().toString().trim())) {
            toast("请填写法人变更月数");
            return;
        }
        if (TextUtils.isEmpty(beanCompanyType)) {
            toast("请选择公司类型");
            return;
        }
        if (TextUtils.isEmpty(etClMonth.getText().toString().trim())) {
            toast("请填写公司成立月数");
            return;
        }
        if (taxLevel == null) {
            toast("请选择" + nas + "等级");
            return;
        }
        if (TextUtils.isEmpty(et12Kp.getText().toString().trim())) {
            toast("请填写近12月累计"+kai);
            return;
        }
        if (TextUtils.isEmpty(et12Ns.getText().toString().trim())) {
            toast("请填写近12月累计" + nas + "额");
            return;
        }
        if (TextUtils.isEmpty(etJyFdCount.getText().toString().trim())) {
            toast("请填写经营性" + loa + "余额笔数");
            return;
        }
//        if (TextUtils.isEmpty(etJyFdNumber.getText().toString().trim())) {
//            toast("请填写经营性余额");
//            return;
//        }
        if (TextUtils.isEmpty(et2Count.getText().toString().trim())) {
            toast("请填写近2月" + loa + "查询");
            return;
        }
        if (TextUtils.isEmpty(et6Count.getText().toString().trim())) {
            toast("请填写近6月" + loa + "查询");
            return;
        }
        matchRequestBean.setCityName(Constans.CITY);
        matchRequestBean.setAppId(Constans.APPID);
        matchRequestBean.setPosition(beanPosition);
        matchRequestBean.setTaxLevel(taxLevel);
        matchRequestBean.setCompanyType(beanCompanyType);
        matchRequestBean.setShareHolding(Double.parseDouble(etZgbl.getText().toString().trim()));
        matchRequestBean.setChangeMonth(Integer.parseInt(etFrbgMonth.getText().toString().trim()));
        matchRequestBean.setFoundTime(Integer.parseInt(etClMonth.getText().toString().trim()));
        matchRequestBean.setYearInvoice((int) (Double.parseDouble(et12Kp.getText().toString().trim()) * 10000));
        matchRequestBean.setYeatTax((int) (Double.parseDouble(et12Ns.getText().toString().trim()) * 10000));
        matchRequestBean.setLoanTimes(Integer.parseInt(etJyFdCount.getText().toString().trim()));
//        matchRequestBean.setLoanBalance((int) (Double.parseDouble(etJyFdNumber.getText().toString().trim()) * 10000));
        matchRequestBean.setTwoMonthLoanSearchTimes(Integer.parseInt(et2Count.getText().toString().trim()));
        matchRequestBean.setSixMonthLoanSearchTImes(Integer.parseInt(et6Count.getText().toString().trim()));
        Intent intent = new Intent(mContext, MatchResultActivity.class);
        intent.putExtra("data", matchRequestBean);
        startActivity(intent);
    }


    /**
     * 计算先在和之前相差月份
     *
     * @param date
     * @return
     */
    private int getMonthCha(String date) {
        int resultMonth = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNow = new Date(System.currentTimeMillis());
        Date dateBefore = null;
        try {
            dateBefore = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(dateBefore);
        aft.setTime(dateNow);
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        System.out.println(Math.abs(month + result));
        resultMonth = Math.abs(month + result);
        return resultMonth;
    }



}
