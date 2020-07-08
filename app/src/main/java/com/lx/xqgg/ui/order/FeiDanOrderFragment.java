package com.lx.xqgg.ui.order;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.order.bean.DealBean;
import com.lx.xqgg.ui.order.bean.OrderBean;
import com.lx.xqgg.ui.vip.bean.NotifyBean;
import com.lx.xqgg.util.CountDownTimerUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FeiDanOrderFragment extends DialogFragment {
    protected CompositeDisposable mCompositeDisposable;

    private CountDownTimerUtils countDownTimer;
    protected Context mContext;
    private ListPopupWindow companyWindow;
    private List<String> companyList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private Unbinder unbinder;
    private DealBean dealBean;
    private OrderBean.RecordsBean orderBean;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.feidanorderfragment, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }
    private void init() {
        dealBean = new DealBean();
        orderBean = (OrderBean.RecordsBean) getActivity().getIntent().getSerializableExtra("data");
        showDialog();
    }

    public FeiDanOrderFragment() {
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
        unbinder.unbind();
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
    public void showDialog() {
        MaterialDialog permissionDialog = new MaterialDialog.Builder(mContext)
                .title("提示")
                .content("系统即将删除该笔订单，请确认")
                .cancelable(false)
                .positiveText(R.string.yes)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        HashMap<String, Object> paramsMap = new HashMap<>();
                        paramsMap.put("id", orderBean.getId());
                        paramsMap.put("token", SharedPrefManager.getUser().getToken());
                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                        addSubscribe(ApiManage.getInstance().getMainApi().disCardOrder(body)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new BaseSubscriber<BaseData>(mContext, null) {
                                    @Override
                                    public void onNext(BaseData baseData) {
                                        Log.e("zlz", new Gson().toJson(baseData));
                                        if (baseData.isSuccess()) {
                                            toast("提交成功");
                                            EventBus.getDefault().post(new NotifyBean(0, "提交成功"));
                                        } else {
                                            toast(baseData.getMessage());
                                        }
                                    }
                                }));
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
        permissionDialog.show();
    }
}
