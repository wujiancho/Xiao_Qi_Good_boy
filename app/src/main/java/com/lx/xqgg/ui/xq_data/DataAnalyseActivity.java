package com.lx.xqgg.ui.xq_data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.tabs.TabLayout;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.xq_data.bean.ProductNameBean;
import com.lx.xqgg.widget.SlowlyProgressBar;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DataAnalyseActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.webview)
    WebView webview;

    private SlowlyProgressBar bar;

    private List<ProductNameBean> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_analyse;
    }

    @Override
    protected void initView() {
        bar = new SlowlyProgressBar((ProgressBar) findViewById(R.id.bar));
    }

    @Override
    protected void initData() {
        addSubscribe(ApiManage.getInstance().getMainApi().getProductForXq()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<ProductNameBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<ProductNameBean>> listBaseData) {
                        if (listBaseData.isSuccess()) {
                            if (listBaseData.getData() != null && listBaseData.getData().size() > 0) {
                                list = listBaseData.getData();
                                for (ProductNameBean bean : list) {
                                    tablayout.addTab(tablayout.newTab().setText(bean.getName() + ""));
                                }
                                tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                    @Override
                                    public void onTabSelected(TabLayout.Tab tab) {
                                        Log.e("zlz", tab.getText() + (list.get(tab.getPosition()).getId()));
                                        webview.evaluateJavascript("javascript:orderMoney(\""+ SharedPrefManager.getUser().getToken()+"\","+list.get(tab.getPosition()).getId()+")", new ValueCallback<String>() {
                                            @Override
                                            public void onReceiveValue(String value) {
                                                //此处为 js 返回的结果
                                            }
                                        });
                                        Log.e("zlz","javascript:orderMoney("+ SharedPrefManager.getUser().getToken()+","+list.get(tab.getPosition()).getId()+")");
                                    }

                                    @Override
                                    public void onTabUnselected(TabLayout.Tab tab) {

                                    }

                                    @Override
                                    public void onTabReselected(TabLayout.Tab tab) {

                                    }
                                });

                            }
                        }
                    }
                }));
        initwebview();
    }


    private void initwebview() {
        WebSettings settings = webview.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webview.getSettings().setGeolocationEnabled(true);
        webview.getSettings().setGeolocationDatabasePath(dir);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                bar.onProgressStart();
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);
//                webview.evaluateJavascript("javascript:orderMoney(\""+ SharedPrefManager.getUser().getToken()+"\","+list.get(0).getId()+")", new ValueCallback<String>() {
//                    @Override
//                    public void onReceiveValue(String value) {
//                        //此处为 js 返回的结果
//                    }
//                });
                webview.evaluateJavascript("javascript:orderMoney(\""+ SharedPrefManager.getUser().getToken()+"\")", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        //此处为 js 返回的结果
                    }
                });
            }

            @SuppressLint("MissingPermission")
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                sslErrorHandler.proceed();
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView webView, String url, String message, final JsResult jsResult) {
                new MaterialDialog.Builder(mContext)
                        .title("提示")
                        .content(message)
                        .positiveText(R.string.yes)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                jsResult.confirm();
                            }
                        })
                        .cancelable(false)
                        .build()
                        .show();
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                bar.onProgressChange(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissionsCallback callback) {
                final boolean remember = false;
                new MaterialDialog.Builder(mContext)
                        .title("地理位置授权")
                        .content("允许" + origin + "获取您当前的地理位置信息吗")
                        .positiveText("允许")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                callback.invoke(origin, true, remember);
                            }
                        })
                        .negativeText("拒绝")
                        .negativeColorRes(R.color.txt_normal)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                callback.invoke(origin, false, remember);
                            }
                        })
                        .cancelable(false)
                        .build()
                        .show();
            }

        });
        webview.loadUrl("http://192.168.1.106:8080");
    }


    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }
}
