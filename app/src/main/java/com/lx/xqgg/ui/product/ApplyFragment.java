package com.lx.xqgg.ui.product;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.explain.ExplainActivity;
import com.lx.xqgg.ui.login.bean.MsgBean;
import com.lx.xqgg.ui.product.bean.ApplyHistoryBean;
import com.lx.xqgg.ui.product.bean.AuthBean;
import com.lx.xqgg.ui.product.bean.CaseInfoBean;
import com.lx.xqgg.ui.product.bean.FumingBean;
import com.lx.xqgg.ui.product.bean.ProductBean;
import com.lx.xqgg.ui.product.bean.QccBean;
import com.lx.xqgg.ui.webview.WebViewActivity;
import com.lx.xqgg.util.CountDownTimerUtils;
import com.lx.xqgg.util.FastClickUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ApplyFragment extends DialogFragment {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.et_cpmpany_name)
    EditText etCpmpanyName;
    @BindView(R.id.et_social_code)
    TextView etSocialCode;
    @BindView(R.id.et_id_num)
    EditText etIdNum;
    @BindView(R.id.et_fr_name)
    EditText etFrName;
    @BindView(R.id.tv_hy_type)
    TextView tvHyType;
    @BindView(R.id.sp_hy_type)
    Spinner spHyType;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.tv_announce)
    TextView tvAnnounce;
    @BindView(R.id.layout_msg)
    LinearLayout layoutMsg;
    @BindView(R.id.yincang)
    CheckBox yincang;

    private Unbinder mUnBinder;
    private ProductBean.RecordsBean bean;
    private boolean needMsg = true;

    protected CompositeDisposable mCompositeDisposable;

    private CountDownTimerUtils countDownTimer;

    protected Context mContext;

    private boolean edFocus = false;
    private boolean etNameFocus = false;
    private String address = "";
    private ListPopupWindow companyWindow;
    private List<String> companyList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private boolean isSelected = false;

    private String id_card;
    private String link_phone;
    private String link_man;
    public ApplyFragment(ProductBean.RecordsBean recordsBean) {
        this.bean = recordsBean;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.fragment_apply, null);
        mUnBinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        countDownTimer = new CountDownTimerUtils(btnGetCode, 60000, 1000, handler);

        arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, companyList);
        companyWindow = new ListPopupWindow(mContext);
        companyWindow.setAdapter(arrayAdapter);
        companyWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        companyWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        companyWindow.setAnchorView(etCpmpanyName);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
        companyWindow.setDropDownGravity(Gravity.CENTER_HORIZONTAL);
//        companyWindow.setModal(true);//设置是否是模式

        //这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
        companyWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edFocus = false;
                isSelected = true;
                checkHistory(companyList.get(position), "company");
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                etFrName.requestFocus();
                companyWindow.dismiss();
            }
        });


        etCpmpanyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("zlz", s.toString());
                Log.e("zlz", s.toString().length() + "");
                Log.e("zlz", isSelected + "");
                if (isSelected) {
                    return;
                }
                if (s.toString().length() > 0) {
                    HashMap<String, Object> paramsMap = new HashMap<>();
                    paramsMap.put("token", SharedPrefManager.getUser().getToken());
                    paramsMap.put("search_words", s.toString());
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                    addSubscribe(ApiManage.getInstance().getMainApi().getCustomerCompany(body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new BaseSubscriber<BaseData<List<String>>>(mContext, null) {
                                @Override
                                public void onNext(BaseData<List<String>> listBaseData) {
                                    Log.e("zlz", new Gson().toJson(listBaseData));
                                    if (listBaseData.isSuccess()) {
                                        if (listBaseData.getData() != null && listBaseData.getData().size() > 0) {
                                            companyList.clear();
                                            companyList.addAll(listBaseData.getData());
                                            if (!companyWindow.isShowing()) {
                                                companyWindow.show();
                                            }
                                            arrayAdapter.notifyDataSetChanged();
                                        } else {
                                            if (companyWindow.isShowing()) {
                                                companyWindow.dismiss();
                                            }
                                        }
                                    } else {
                                        if (companyWindow.isShowing()) {
                                            companyWindow.dismiss();
                                        }
                                    }
                                }
                            }));
                } else {
                    if (companyWindow.isShowing()) {
                        companyWindow.dismiss();
                    }
                   /* etIdNum.setText("");
                    etPhone.setText("");
                    etFrName.setText("");
                    etCode.setText("");
                    etSocialCode.setText("");
                    tvArea.setText("");*/
                }
            }
        });

        etCpmpanyName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isSelected = false;
                    edFocus = true;
                } else {
                    if (edFocus) {
                        if (!TextUtils.isEmpty(etCpmpanyName.getText().toString())) {
                            Log.e("zlz", "查询");
                            checkHistory(etCpmpanyName.getText().toString().trim(), "company");
//                            checkCompany(etCpmpanyName.getText().toString().trim());
                        }
                    }
                    edFocus = false;
                }
            }
        });

        etFrName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etNameFocus = true;
                } else {
                    if (etNameFocus) {
                        if (TextUtils.isEmpty(etCpmpanyName.getText())) {
                            checkHistory(etFrName.getText().toString().trim(), "personal");
                        }
                    }
                }
            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(link_phone)) {
                    etIdNum.setVisibility(View.VISIBLE);
                    etIdNum.setText("");
                    layoutMsg.setVisibility(View.VISIBLE);
                    needMsg = true;
                } else {
                    etIdNum.setText(id_card);
                    etIdNum.setVisibility(View.GONE);
                    layoutMsg.setVisibility(View.GONE);
                    needMsg = false;
                }

            }

        });


        if (TextUtils.isEmpty(SharedPrefManager.getPhone())) {
            needMsg = true;
        } else {
//            etPhone.setText(SharedPrefManager.getPhone());
//            etFrName.setText(SharedPrefManager.getRealName());
//            etIdNum.setText(SharedPrefManager.getIdNum());
            layoutMsg.setVisibility(View.GONE);
            needMsg = false;
        }
    }

    private void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 取消所有订阅
     */
    private void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        unSubscribe();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void toast(String str) {
        Toast toast = Toast.makeText(mContext, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void checkHistory(String keywords, String type) {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("type", type);
        paramsMap.put("search_words", keywords);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getCustomerInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<ApplyHistoryBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<ApplyHistoryBean> applyHistoryBeanBaseData) {
                        Log.e("test", new Gson().toJson(applyHistoryBeanBaseData));
                        if (applyHistoryBeanBaseData.isSuccess()) {
                            if (applyHistoryBeanBaseData.getData() != null) {
                                ApplyHistoryBean applyHistoryBean = applyHistoryBeanBaseData.getData();
                                isSelected = true;
                                etCpmpanyName.setText(TextUtils.isEmpty(applyHistoryBean.getCompany()) ? "" : applyHistoryBean.getCompany());
                                etSocialCode.setText(TextUtils.isEmpty(applyHistoryBean.getCreditCode()) ? "" : applyHistoryBean.getCreditCode());
                                etIdNum.setText(TextUtils.isEmpty(applyHistoryBean.getId_card()) ? "" : applyHistoryBean.getId_card());
                                etFrName.setText(TextUtils.isEmpty(applyHistoryBean.getLink_man()) ? "" : applyHistoryBean.getLink_man());
                                tvHyType.setText(TextUtils.isEmpty(applyHistoryBean.getIndustry()) ? "" : applyHistoryBean.getIndustry());
                                etPhone.setText(TextUtils.isEmpty(applyHistoryBean.getLink_phone()) ? "" : applyHistoryBean.getLink_phone());
                                tvArea.setText(TextUtils.isEmpty(applyHistoryBean.getArea()) ? "" : applyHistoryBean.getArea());
                                id_card = applyHistoryBean.getId_card();
                                link_man = applyHistoryBean.getLink_man();
                                link_phone = applyHistoryBean.getLink_phone();
                                if (!"".equals(id_card) && id_card !=null){
                                    etIdNum.setText(id_card);
                                    etIdNum.setVisibility(View.GONE);
                                }
                                etFrName.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }
                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(!s.toString().equals(link_man)) {
                                            etIdNum.setText("");
                                            etIdNum.setVisibility(View.VISIBLE);
                                        }
                                        else {
                                            etIdNum.setText(id_card);
                                            etIdNum.setVisibility(View.GONE);
                                        }
                                    }
                                });
                                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                // 创建普通字符型ClipData
                                ClipData mClipData = ClipData.newPlainText("Label", applyHistoryBean.getCreditCode() + "");
                                // 将ClipData内容放到系统剪贴板里。
                                cm.setPrimaryClip(mClipData);
                                toast("信用代码已复制到剪切板");

                            } else {
                                if (!TextUtils.isEmpty(etCpmpanyName.getText().toString())) {
                                    checkCompany(keywords);
                                }
                            }
                        } else {
                            if (!TextUtils.isEmpty(etCpmpanyName.getText().toString())) {
                                checkCompany(keywords);
                            }
                        }
                    }
                }));

    }



    /**
     * 调用企查查查询
     *
     * @param company
     */
    private void checkCompany(String company) {
        Log.e("zlz", company + "");
        addSubscribe(ApiManage.getInstance().getMainApi().getQiCcInfo(company)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<String>>(getActivity(), null) {
                    @Override
                    public void onNext(BaseData<String> s) {
                        Log.e("zlz", new Gson().toJson(s));
                        if (s.isSuccess()) {
                            QccBean qccBean = new Gson().fromJson(s.getData(), QccBean.class);
                            if (qccBean == null) {
                                toast("请输入正确的公司");
                                return;
                            }
                            if ("查询无结果".equals(qccBean.getMessage())) {
                                toast("抱歉未找到相关公司，请检查");
                                return;
                            }
                            if (qccBean.getMessage().equals("查询失败")) {
                                toast("请输入正确的公司");
                                return;
                            }
                            Log.e("zlz", new Gson().toJson(qccBean));
                            try {
                                 String subIndustry= qccBean.getResult().getIndustry().getSubIndustry();
                                 String industry= qccBean.getResult().getIndustry().getIndustry();
                                if (subIndustry!= null) {
                                    tvHyType.setText(subIndustry);
                                } else if (industry != null) {
                                    tvHyType.setText(industry);
                                }else{
                                    tvHyType.setText("未知行业");
                                }

                            } catch (Exception e) {

                            }
                            try {
                                etFrName.setText(TextUtils.isEmpty(qccBean.getResult().getOperName()) ? "" : qccBean.getResult().getOperName());
//                                if (!qccBean.getResult().getOperName().equals(SharedPrefManager.getRealName())) {
//                                    etPhone.setText("");
//                                    etIdNum.setText("");
//                                }
                            } catch (Exception e) {

                            }
                            try {
                                if (!TextUtils.isEmpty(qccBean.getResult().getCreditCode())) {
                                    etSocialCode.setText(qccBean.getResult().getCreditCode() + "");
                                    ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                    // 创建普通字符型ClipData
                                    ClipData mClipData = ClipData.newPlainText("Label", qccBean.getResult().getCreditCode() + "");
                                    // 将ClipData内容放到系统剪贴板里。
                                    cm.setPrimaryClip(mClipData);
                                    toast("信用代码已复制到剪切板");
                                } else if (!TextUtils.isEmpty(qccBean.getResult().getNo())) {
                                    etSocialCode.setText(qccBean.getResult().getNo());
                                    ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                    // 创建普通字符型ClipData
                                    ClipData mClipData = ClipData.newPlainText("Label", qccBean.getResult().getNo() + "");
                                    // 将ClipData内容放到系统剪贴板里。
                                    cm.setPrimaryClip(mClipData);
                                    toast("信用代码已复制到剪切板");
                                }
                            } catch (Exception e) {

                            }
                            try {
                                etPhone.setText(TextUtils.isEmpty(qccBean.getResult().getContactInfo().getPhoneNumber()) ? "" : qccBean.getResult().getContactInfo().getPhoneNumber());
//                                if (!qccBean.getResult().getContactInfo().getPhoneNumber().equals(SharedPrefManager.getRealName())) {
//                                    etFrName.setText("");
//                                    etIdNum.setText("");
//                                }
                            } catch (Exception e) {

                            }
                            try {
                                String  province = qccBean.getResult().getArea().getProvince();
                                String city=qccBean.getResult().getArea().getCity();
                                String county= qccBean.getResult().getArea().getCounty();
                                 if (province.equals(city)){
                                     tvArea.setText(city+" "+county);
                                 }else {
                                     tvArea.setText(province+" "+city);
                                 }

                            } catch (Exception e) {

                            }
                        }
                    }
                }));
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @OnClick({R.id.v_close, R.id.btn_get_code, R.id.btn_commit, R.id.tv_announce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                dismiss();
                break;
            case R.id.btn_get_code:
                if (etPhone.getText().toString().trim().length() < 11) {
                    toast("请输入正确的手机号");
                    return;
                }
                if (FastClickUtil.isFastClick()) {
                    return;
                }

              getIdAuth();

                break;
            case R.id.btn_commit:
                commit();
                break;
            case R.id.tv_announce:
                Intent intent1 = new Intent(getActivity(), ExplainActivity.class);
                intent1.putExtra("type", 1);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.bottom_in, R.anim.bottom_slient);
                break;
        }
    }

    /**
     * 三证合一
     */
    private void getIdAuth() {
        if (etIdNum.getText().toString().length() < 18) {
            toast("请输入正确的身份证号码");
            return;
        }
        if (TextUtils.isEmpty(etFrName.getText().toString())) {
            toast("请输入姓名");
            return;
        }
        if (etPhone.getText().toString().length() < 11) {
            toast("请输入正确的手机号码");
            return;
        }
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("idCard", etIdNum.getText().toString());
        paramsMap.put("mobile", etPhone.getText().toString());
        paramsMap.put("name", etFrName.getText().toString());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getIdAuth(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<AuthBean>>(getActivity(), null) {
                    @Override
                    public void onNext(BaseData<AuthBean> authBeanBaseData) {
                        if (authBeanBaseData.isSuccess()) {
                            if (authBeanBaseData.getData() != null) {
                                if (authBeanBaseData.getData().getStatus().equals("01")) {
                                    //通过
                                    getMsgCode();
                                    return;
                                } else {
                                    showDialog(authBeanBaseData.getData().getMsg());
                                    return;
                                }
                            } else {
                                getMsgCode();
                            }
                        } else {
                            getMsgCode();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        getMsgCode();
                    }
                }));
    }

    private void getMsgCode() {
        addSubscribe(ApiManage.getInstance().getMainApi().getMsg(etPhone.getText().toString(), "userauth", SharedPrefManager.getUser().getToken(), etFrName.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<MsgBean>(getActivity(), null) {
                    @Override
                    public void onNext(MsgBean msgBean) {
                        if (msgBean.isSuccess()) {
                            countDownTimer.start();
                        } else {
                            toast(msgBean.getMessage());
                        }
                    }
                }));
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (countDownTimer != null) {
                countDownTimer.onFinish();
                countDownTimer.cancel();
            }
        }
    };

    private void commit() {
        //只有企業貸款所有信息都要验证
            if ("2".equals(bean.getLoantype())) {
                if (TextUtils.isEmpty(etCpmpanyName.getText().toString().trim())) {
                    toast("请输入企业名称");
                    return;
                }
                if (TextUtils.isEmpty(etSocialCode.getText().toString().trim())) {
                    toast("请输入社会统一信用代码");
                    return;
                }
//            if (TextUtils.isEmpty(tvHyType.getText().toString().trim())) {
//                Toast.makeText(getContext(), "请选择行业类型", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (TextUtils.isEmpty(tvArea.getText().toString().trim())) {
//                Toast.makeText(getContext(), "请选择地区", Toast.LENGTH_SHORT).show();
//                return;
//            }
            }
        if (etIdNum.getText().toString().length() < 18) {
            toast("请输入正确的身份证号码");
            return;
        }
        if (TextUtils.isEmpty(etFrName.getText().toString())) {
            toast("请输入姓名");
            return;
        }
        if (etPhone.getText().toString().length() < 11) {
            toast("请输入正确的手机号码");
            return;
        }
        if (needMsg) {
            if (etCode.getText().toString().trim().length() < 4) {
                toast("请输入短信验证码");
                return;
            }
        }
        if (!checkbox.isChecked()) {
            toast("请阅读并同意《隐私保护声明》");
            return;
        }

        addSubscribe(ApiManage.getInstance().getMainApi().getCaseInfo(etCpmpanyName.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<String>>(getActivity(), null) {
                    @Override
                    public void onNext(BaseData<String> stringBaseData) {
                        Log.e("zlz", new Gson().toJson(stringBaseData));
                        if (stringBaseData.isSuccess()) {
                            CaseInfoBean caseInfoBean = new Gson().fromJson(stringBaseData.getData(), CaseInfoBean.class);
                            if (caseInfoBean.getResult() == null) {
                                applyProduct();
                            } else {
                                /**开庭时间**/
                                String time = caseInfoBean.getResult().get(0).getLianDate();
                                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                try {
                                    Date fromDate1 = simpleFormat.parse(time);
                                    Log.i("md", "时间time为： " + fromDate1.toLocaleString());
                                    Date date = new Date();
                                    String time1 = date.toLocaleString();
                                    Log.i("md", "时间time为： " + time1);
                                    String sim = simpleFormat.format(date);
                                    Date date_now = simpleFormat.parse(sim);
                                    long ktTime = fromDate1.getTime();
                                    long nowTime = date_now.getTime();
                                    int days = (int) ((nowTime - ktTime) / (1000 * 60 * 60 * 24));
                                    Log.i("md", "查： " + days);
                                    if (days < 730) {
                                        //提示
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                        builder1.setMessage("该企业用户存在历史风险记录，可能影响申请，请知悉。");
                                        builder1.setTitle("温馨提示");
                                        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                applyProduct();
                                            }
                                        });
                                        builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder1.show();
                                    } else {
                                        applyProduct();
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    Log.e("zlz", e.toString());
                                    applyProduct();
                                }
                            }
                        } else {
                            applyProduct();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.toString());
                        applyProduct();
                    }
                }));

    }

    private void applyProduct() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("idCard", etIdNum.getText().toString());
        paramsMap.put("mobile", etPhone.getText().toString());
        paramsMap.put("name", etFrName.getText().toString());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getIdAuth(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<AuthBean>>(getActivity(), null) {
                    @Override
                    public void onNext(BaseData<AuthBean> authBeanBaseData) {
                        if (authBeanBaseData.isSuccess()) {
                            if (authBeanBaseData.getData() != null) {
                                if (authBeanBaseData.getData().getStatus().equals("01")) {
                                    //通过
                                    commitApply();
                                } else {
                                    showDialog(authBeanBaseData.getData().getMsg());
                                }
                            } else {
                                commitApply();
                            }
                        } else {
                            commitApply();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        commitApply();
                    }
                }));


    }

    private void commitApply() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("appId", Constans.APPID);
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("link_man", etFrName.getText().toString());
        paramsMap.put("link_phone", etPhone.getText().toString().trim());
        paramsMap.put("id_card", etIdNum.getText().toString());
        paramsMap.put("company", etCpmpanyName.getText().toString());
        paramsMap.put("product_id", bean.getId());
        paramsMap.put("captcha", etCode.getText().toString());
        paramsMap.put("area", tvArea.getText().toString());
        paramsMap.put("industry", tvHyType.getText().toString().replace("请选择行业类型", ""));
        paramsMap.put("title", bean.getTitle());
        paramsMap.put("apply_money", bean.getQuota());
        paramsMap.put("cityName", Constans.CITY);
        paramsMap.put("creditCode", etSocialCode.getText().toString().trim());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().applyProduct(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<String>>(getActivity(), null) {
                    @Override
                    public void onNext(BaseData<String> objectBaseData) {
                        Log.e("fumin", new Gson().toJson(objectBaseData));
                        if (objectBaseData.isSuccess()) {
                            //申请成功之后保存信息
                            SharedPrefManager.setIdNum(etIdNum.getText().toString());
                            SharedPrefManager.setRealName(etFrName.getText().toString());
                            SharedPrefManager.setPhone(etPhone.getText().toString());
                            String url = bean.getCityLink();
                            if (Config.FUMIN_TITLE.equals(bean.getTitle())) {
                                FumingBean fuminBean = new Gson().fromJson(objectBaseData.getData(), FumingBean.class);
                                if (fuminBean.getStatus().equals("200")) {
                                    String token = fuminBean.getBusOutBody().getToken();
                                    try {
                                        String urlencode = URLEncoder.encode(Config.URL + "/view/fuminSuccess.html", "utf-8");
                                        url = Config.FUMIN_BANK + "?token=" + token + "&cburl=" + urlencode;
                                        Log.d("url", "onNext: " + url);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                } else if (fuminBean.getStatus().equals("400")) {
                                    toast(fuminBean.getMessage());
                                } else {
                                    toast(fuminBean.getMessage());
                                }
                            }

                            if (Config.NORMALURL.equals(url)) {
                                url = Config.NORMALURL + "?token=" + SharedPrefManager.getUser().getToken() +
                                        "&orderNo=" + objectBaseData.getData();
                            }

                            WebViewActivity.open(new WebViewActivity.Builder()
                                    .setContext(getContext())
                                    .setAutoTitle(false)
                                    .setIsFwb(false)
                                    .setTitle(bean.getTitle())
                                    // .setUrl("http://192.168.1.144:8081/xiaoqiguaiguai-mobile//view/fuminSuccess.html"));
                                    .setUrl(url));

//                            Uri uri = Uri.parse(url);
//                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                            startActivity(intent);+
//                            dismiss();
                            Log.e("zlz", url + "");
                        } else {
                            showDialog(objectBaseData.getMessage());
                        }
                    }
                }));
    }


    public void showDialog(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage(msg);
        builder1.setTitle("提示");
        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder1.show();
    }

}
