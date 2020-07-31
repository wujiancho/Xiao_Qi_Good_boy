package com.lx.xqgg.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lx.xqgg.util.AppActivityTaskManager;
import com.umeng.analytics.MobclickAgent;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseActivityflase extends SupportActivity {

    protected View view;
    protected Gson gson;

    protected abstract int getLayoutId();

    protected CompositeDisposable mCompositeDisposable;

    protected Activity mContext;

    private Dialog progressDialog;

    private BaseActivity.LoadingDismissListener dismissListener;

    private Long startTime;

    private AlertDialog.Builder builder1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        ButterKnife.bind(this);
        AppActivityTaskManager.getInstance().addActivity(this);

        mContext = this;
        gson = new Gson();
        setTitle("");
        initView();
        initData();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public View getView() {
        view = View.inflate(this, getLayoutId(), null);
        return view;
    }

    public void showDialog(String msg) {
        builder1 = new AlertDialog.Builder(BaseActivityflase.this);
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

    protected abstract void initView();

    protected abstract void initData();

    public void toast(String str) {
        Toast toast=Toast.makeText(mContext, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    @Override
    public boolean moveTaskToBack(boolean nonRoot) {
        return super.moveTaskToBack(true);
    }
    /**
     * 订阅事件
     *
     * @param
     */
    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 取消所有订阅
     */
    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppActivityTaskManager.getInstance().removeActivity(this);
        unSubscribe();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 回退
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void addLoadingDismissEvent(BaseActivity.LoadingDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }


    protected interface LoadingDismissListener {
        void dismissEvent();
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            if (checkDoubleClick()) {
//                return true;
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }


//    /**
//     * 判断是否是快速点击
//     */
//    private static long lastClickTime;
//
//    public static boolean checkDoubleClick() {
//        //点击时间
//        long clickTime = SystemClock.uptimeMillis();
//        //如果当前点击间隔小于300毫秒
//        if (lastClickTime >= clickTime - 300) {
//            return true;
//        }
//        //记录上次点击时间
//        lastClickTime = clickTime;
//        return false;
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
