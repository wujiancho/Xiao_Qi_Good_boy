package com.lx.xqgg.base;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lx.xqgg.R;
import com.lx.xqgg.util.AppNetWorkUtil;


import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by xiarh on 2017/11/13.
 */

public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {

    private Context context;

    private String tip;

    private MaterialDialog materialDialog;

    public BaseSubscriber(Context context, String tip) {
        this.context = context;
        this.tip = tip;
        if (tip != null) {
            materialDialog = new MaterialDialog.Builder(context)
                    .content(tip)
                    .progress(true, 0)
                    .cancelable(false)
                    .build();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!AppNetWorkUtil.isNetworkConnected(context)) {
            Toast toast=Toast.makeText(context, R.string.no_network, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
//            Toast.makeText(context, R.string.no_network, Toast.LENGTH_SHORT).show();
            onComplete();
            return;
        }
        if (null != materialDialog) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    if (!materialDialog.isShowing()) {
                        materialDialog.show();
                    }
                }
            } else {
                if (!materialDialog.isShowing()) {
                    materialDialog.show();
                }
            }

        }
    }

    @Override
    public void onComplete() {
        if (null != materialDialog) {
            materialDialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable t) {
        if (null != materialDialog) {
            materialDialog.dismiss();
            Toast toast=Toast.makeText(context, t.toString(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
