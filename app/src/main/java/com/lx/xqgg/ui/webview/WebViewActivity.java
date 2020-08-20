package com.lx.xqgg.ui.webview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.lx.xqgg.MainActivity;
import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.event.ProductDetailEvent;
import com.lx.xqgg.ui.company_auth.ChooseDialogFragment;
import com.lx.xqgg.ui.product.ApplyFragment;
import com.lx.xqgg.ui.product.bean.ProductBean;
import com.lx.xqgg.ui.share.ShareFaceActivity;
import com.lx.xqgg.widget.SlowlyProgressBar;
import com.qiyukf.unicorn.mediaselect.internal.utils.UIUtils;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebChromeClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.MediaAccessPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.umeng.socialize.utils.UmengText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WebViewActivity extends BaseActivity implements ChooseDialogFragment.OnChooseClickListener {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.webview)
    public WebView webview;
    @BindView(R.id.v_finish)
    View vFinish;
    @BindView(R.id.tv_share)
    TextView tvShare;
    private SlowlyProgressBar bar;
    private ChooseDialogFragment chooseDialogFragment;
    private MaterialDialog materialDialog;
    private ValueCallback<Uri> mUploadMessage;          // Android 4.0回调信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;

    private static int REQUEST_CODE_PHOTO = 1000;

    private static int REQUEST_CODE_CAMERA = 1001;

    private static final int REQUEST_UPLOAD_FILE_CODE = 1003;

    private static int REQUEST_CODE_VIDEO = 1004;

    private File imageFile;

    private String mTitle;

    private String mUrl;

    private boolean mAutoTitle;

    private boolean isFwb = false;

    private boolean needShare = false;
    private  File file;
    private  File filelogo;
    private  File filelogo2;
    private String decodeFile;
    private String decodeFile2;
    private String image;
    private String title;
    private String count;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }


    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        //设置布局ProgressBar加载长度+接受bundle对象
        bar = new SlowlyProgressBar((ProgressBar) findViewById(R.id.bar));
        chooseDialogFragment = new ChooseDialogFragment();
        chooseDialogFragment.setOnChooseClickListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTitle = bundle.getString("title");
            mUrl = bundle.getString("url");
            mAutoTitle = bundle.getBoolean("autotitle");
            isFwb = bundle.getBoolean("isFwb");
            needShare = bundle.getBoolean("needShare");
        }
        if (needShare) {
            tvShare.setVisibility(View.VISIBLE);
        } else {
            tvShare.setVisibility(View.GONE);
        }
        if (mTitle != null) {
            tvTitle.setText(mTitle);
        } else {
            toobar.setVisibility(View.GONE);
        }
        initwebview();
    }

    @Override
    protected void initData() {

    }
     //设置WebView的一些允许的权限
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
        settings.setDomStorageEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        if (mTitle != null) {
            if (mTitle.equals("税务局")) {
                settings.setUseWideViewPort(true);
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                settings.setLoadWithOverviewMode(true);
            }
        } else {
            toobar.setVisibility(View.GONE);
            settings.setUseWideViewPort(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            settings.setLoadWithOverviewMode(true);
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
                if (mAutoTitle) {
                    if (webView.getTitle() != null && !webView.getTitle().equals("") && !webView.getTitle().contains("11185")) {
                        tvTitle.setText(webView.getTitle());
                    } else {
                        tvTitle.setText(mTitle);
                    }
                }
                webview.evaluateJavascript("javascript:creditC(" + "\"" + Constans.PROVINCE + "\",\"" + Constans.CITY + "\")", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        //此处为 js 返回的结果
                    }
                });
            }

            @SuppressLint("MissingPermission")
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {

                if (s.startsWith("alipays:") || s.startsWith("alipay")) {
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(s)));
                    } catch (Exception e) {
                        new AlertDialog.Builder(mContext)
                                .setMessage("未检测到支付宝客户端，请安装后重试。")
                                .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                        startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                    }
                                }).setNegativeButton("取消", null).show();
                    }
                    return true;
                } else if (s.startsWith("weixin://wap/pay?")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(s));
                    startActivity(intent);

                    return true;
                } else if (s.startsWith("mbspay://netbank?") || s.startsWith("upwrp://uppayservice/")) {

                } else if (s.contains("market://details?")) {
                    Uri uri = Uri.parse(s);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    return true;
                } else if (s.contains("tel:")) {

                    String mobile = s.substring(s.lastIndexOf("/") + 1);
                    Log.e("mobile----------->", mobile);
                    Intent mIntent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse(mobile);
                    mIntent.setData(data);
                    //Android6.0以后的动态获取打电话权限
                    startActivity(mIntent);
                    return true;
                } else if (s.startsWith("http:") || s.startsWith("https:")) {
                    webView.loadUrl(s);
                    return true;
                } else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(s));
                        mContext.startActivity(intent);
                    } catch (Exception e) {
//                    ToastUtils.showShort("暂无应用打开此链接");
                    }
                    return true;
                }

                return true;
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                sslErrorHandler.proceed();
            }
        });

//        webview.setWebChromeClientExtension(new IX5WebChromeClientExtension() {
//            @Override
//            public Object getX5WebChromeClientInstance() {
//                return null;
//            }
//
//            @Override
//            public View getVideoLoadingProgressView() {
//                return null;
//            }
//
//            @Override
//            public void onAllMetaDataFinished(IX5WebViewExtension ix5WebViewExtension, HashMap<String, String> hashMap) {
//
//            }
//
//            @Override
//            public void onBackforwardFinished(int i) {
//
//            }
//
//            @Override
//            public void onHitTestResultForPluginFinished(IX5WebViewExtension ix5WebViewExtension, IX5WebViewBase.HitTestResult hitTestResult, Bundle bundle) {
//
//            }
//
//            @Override
//            public void onHitTestResultFinished(IX5WebViewExtension ix5WebViewExtension, IX5WebViewBase.HitTestResult hitTestResult) {
//
//            }
//
//            @Override
//            public void onPromptScaleSaved(IX5WebViewExtension ix5WebViewExtension) {
//
//            }
//
//            @Override
//            public void onPromptNotScalable(IX5WebViewExtension ix5WebViewExtension) {
//
//            }
//
//            @Override
//            public boolean onAddFavorite(IX5WebViewExtension ix5WebViewExtension, String s, String s1, JsResult jsResult) {
//                return false;
//            }
//
//            @Override
//            public void onPrepareX5ReadPageDataFinished(IX5WebViewExtension ix5WebViewExtension, HashMap<String, String> hashMap) {
//
//            }
//
//            @Override
//            public boolean onSavePassword(String s, String s1, String s2, boolean b, Message message) {
//                return false;
//            }
//
//            @Override
//            public boolean onSavePassword(android.webkit.ValueCallback<String> valueCallback, String s, String s1, String s2, String s3, String s4, boolean b) {
//                return false;
//            }
//
//            @Override
//            public void onX5ReadModeAvailableChecked(HashMap<String, String> hashMap) {
//
//            }
//
//            @Override
//            public void addFlashView(View view, ViewGroup.LayoutParams layoutParams) {
//
//            }
//
//            @Override
//            public void h5videoRequestFullScreen(String s) {
//
//            }
//
//            @Override
//            public void h5videoExitFullScreen(String s) {
//
//            }
//
//            @Override
//            public void requestFullScreenFlash() {
//
//            }
//
//            @Override
//            public void exitFullScreenFlash() {
//
//            }
//
//            @Override
//            public void jsRequestFullScreen() {
//
//            }
//
//            @Override
//            public void jsExitFullScreen() {
//
//            }
//
//            @Override
//            public void acquireWakeLock() {
//
//            }
//
//            @Override
//            public void releaseWakeLock() {
//
//            }
//
//            @Override
//            public Context getApplicationContex() {
//                return null;
//            }
//
//            @Override
//            public boolean onPageNotResponding(Runnable runnable) {
//                return false;
//            }
//
//            @Override
//            public Object onMiscCallBack(String s, Bundle bundle) {
//                return null;
//            }
//
//            @Override
//            public void openFileChooser(android.webkit.ValueCallback<Uri[]> valueCallback, String s, String s1) {
//
//            }
//
//            @Override
//            public void onPrintPage() {
//
//            }
//
//            @Override
//            public void onColorModeChanged(long l) {
//
//            }
//
//            @Override
//            public boolean onPermissionRequest(String s, long l, MediaAccessPermissionsCallback mediaAccessPermissionsCallback) {
//                long allowed = 0;
//                allowed = allowed | MediaAccessPermissionsCallback.ALLOW_AUDIO_CAPTURE;
//                boolean retain = true;
//                mediaAccessPermissionsCallback.invoke(s, allowed,retain);
//                return true;
//            }
//        });
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

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                bar.onProgressChange(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                String title1 = webView.getTitle();
                if (mAutoTitle) {
                    if (title1 != null && !title1.equals("")) {
                        tvTitle.setText(title1);
                    }
                    else {
                        tvTitle.setText(mTitle);
                    }
                }
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

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                boolean isVideo = acceptType.contains("video");
                if (isVideo) {
                    try {
                        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        intent.putExtra("android.intent.extras.CAMERA_FACING", 1); // 调用前置摄像头
//                    intent.putExtra("autofocus", true); // 自动对焦
                        intent.putExtra("fullScreen", false); // 全屏
                        intent.putExtra("showActionIcons", false);
                        startActivityForResult(intent, REQUEST_CODE_VIDEO);
                    } catch (SecurityException e) {
                        toast("请在设置中打开权限！");
                    }
                } else {
                    chooseDialogFragment.show(getSupportFragmentManager(), "dialog");
                }
            }

            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                String[] acceptTypes = fileChooserParams.getAcceptTypes();
                boolean isVideo = false;
//                boolean isVideo = fileChooserParams.createIntent().getType().contains("video");
                for (int i = 0; i < acceptTypes.length; i++) {
                    if (acceptTypes[i].contains("video")) {
                        isVideo = true;
                        break;
                    }
                }
                if (isVideo) {
                    try {
                        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        intent.putExtra("android.intent.extras.CAMERA_FACING", 1); // 调用前置摄像头
//                    intent.putExtra("autofocus", true); // 自动对焦
                        intent.putExtra("fullScreen", false); // 全屏
                        intent.putExtra("showActionIcons", false);
                        startActivityForResult(intent, REQUEST_CODE_VIDEO);
                        return true;
                    } catch (SecurityException e) {
                        toast("请在设置中打开权限！");
                        return true;
                    }

                } else {
                    chooseDialogFragment.show(getSupportFragmentManager(), "dialog");
                    return true;
                }
            }

        });

        webview.addJavascriptInterface(new JavaScriptClass(), "android");

        if (isFwb) {
            vFinish.setVisibility(View.GONE);
            webview.loadDataWithBaseURL(null, mUrl, "text/html", "utf-8", null);
        } else {
            webview.loadUrl(mUrl);
        }


        webview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               WebView.HitTestResult result = webview.getHitTestResult();
                if (result != null) {
                    int type = result.getType();
                    if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                        String longClickUrl = result.getExtra();
                        showSaveDialog(longClickUrl);
                    }
        }
                return false;
            }
        });
    }

    private void showSaveDialog(final String url) {

        materialDialog = new MaterialDialog.Builder(this)
                .title("提示")
                .content("保存图片到相册")
                .cancelable(false)
                .positiveText(R.string.yes)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Observable.create(new ObservableOnSubscribe<File>() {
                            @Override
                            public void subscribe(ObservableEmitter<File> e) throws Exception {
                                //通过gilde下载得到file文件,这里需要注意android.permission.INTERNET权限
                                e.onNext(Glide.with(mContext)
                                        .load(url)
                                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                        .get());
                                e.onComplete();
                            }
                        }).subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.newThread())
                                .subscribe(new Consumer<File>() {
                                    @Override
                                    public void accept(File file) throws Exception {
                                        //获取到下载得到的图片，进行本地保存
                                        File pictureFolder = Environment.getExternalStorageDirectory();
                                        //第二个参数为你想要保存的目录名称
                                        File appDir = new File(pictureFolder, "小麒乖乖");
                                        if (!appDir.exists()) {
                                            appDir.mkdirs();
                                        }
                                        String fileName = System.currentTimeMillis() + ".jpg";
                                        File destFile = new File(appDir, fileName);
                                        //把gilde下载得到图片复制到定义好的目录中去
                                        copy(file, destFile);
                                        // 最后通知图库更新
                                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                                Uri.fromFile(new File(destFile.getPath()))));
                                        file= new File(destFile.getPath());
                                        Looper.prepare();
                                        toast("保存成功");
                                        Looper.loop();
                                    }

                                });
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
        materialDialog.show();
    }

    public void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @OnClick({R.id.v_close, R.id.v_finish, R.id.tv_share})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.v_close:
                goback();
                break;
            case R.id.v_finish:
                finish();
                break;
            case R.id.tv_share:
                //share2(Config.URL+image,title,count);
                share();
                break;
        }
    }
//H5调用Android
    class JavaScriptClass {
        @JavascriptInterface
        //分享标题链接
        public void share(String url, String title) {
            UMImage image1 = new UMImage(mContext, R.drawable.logo);//分享图标
            final UMWeb web1 = new UMWeb(url); //切记切记 这里分享的链接必须是http开头
            web1.setTitle(mTitle);//标题
            web1.setThumb(image1);  //缩略图
            web1.setDescription(title + "");
            new ShareAction(mContext)
                    .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setShareboardclickCallback(new ShareBoardlistener() {
                        @Override
                        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                            if (share_media == SHARE_MEDIA.QQ) {
                                new ShareAction(mContext).setPlatform(SHARE_MEDIA.QQ)
                                        .withMedia(web1)
                                        .setCallback(umShareListener)
                                        .share();
                            } else if (share_media == SHARE_MEDIA.WEIXIN) {
                                new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN)
                                        .withMedia(web1)
                                        .setCallback(umShareListener)
                                        .share();
                            } else if (share_media == SHARE_MEDIA.QZONE) {
                                new ShareAction(mContext).setPlatform(SHARE_MEDIA.QZONE)
                                        .withMedia(web1)
                                        .setCallback(umShareListener)
                                        .share();
                            } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
                                new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                        .withMedia(web1)
                                        .setCallback(umShareListener)
                                        .share();
                            }
                        }
                    }).open();
        }
        //H5返回键销毁当前页面
        @JavascriptInterface
        public void handleArrow() {
          finish();
        }

    //H5返回键销毁当前页面权益详情
    @JavascriptInterface
    public void handleReturn() {
        finish();
    }

    //权益详情分享
    @JavascriptInterface
    public void handleShare(String shareId,String name,String image,String desc){
        returnBitMap2(Config.IMGURL+image);
        if (!"".equals(filelogo2)) {
            UMWeb web = new UMWeb(Constans.interestsDetails);
            Log.d("onstansinterestsetails", "convert: "+Constans.interestsDetails);
            web.setThumb(new UMImage(WebViewActivity.this,filelogo2));
            web.setTitle(name);
            //  web.setThumb(new UMImage(WebViewActivity.this, proLogo));
            web.setDescription(desc);
            new ShareAction(mContext)
                    .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setShareboardclickCallback(new ShareBoardlistener() {
                        @Override
                        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                            if (share_media == SHARE_MEDIA.QQ) {
                                web.setThumb(new UMImage(WebViewActivity.this, decodeFile2));
                                new ShareAction(mContext).setPlatform(SHARE_MEDIA.QQ)
                                        .withMedia(web)
                                        .setCallback(umShareListener)
                                        .share();
                            } else if (share_media == SHARE_MEDIA.WEIXIN) {
                                web.setThumb(new UMImage(WebViewActivity.this, filelogo2));
                                new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN)
                                        .withMedia(web)
                                        .setCallback(umShareListener)
                                        .share();
                            } else if (share_media == SHARE_MEDIA.QZONE) {
                                new ShareAction(mContext).setPlatform(SHARE_MEDIA.QZONE)
                                        .withMedia(web)
                                        .setCallback(umShareListener)
                                        .share();
                            } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
                                new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                        .withMedia(web)
                                        .setCallback(umShareListener)
                                        .share();
                            }
                        }
                    }).open();
        }
    }

        //详细页H5页面分享
        @JavascriptInterface
        public void sharePic(String proTitle,String proLogo ,String proDesc ) {
            returnBitMap(proLogo);
            if (!"".equals(filelogo)) {
                UMWeb webpic = new UMWeb(Constans.productDetails);
                webpic.setThumb(new UMImage(WebViewActivity.this, filelogo));
                webpic.setTitle(proTitle);
             //  web.setThumb(new UMImage(WebViewActivity.this, proLogo));
                webpic.setDescription(proDesc);
                new ShareAction(mContext)
                        .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                if (share_media == SHARE_MEDIA.QQ) {
                                    webpic.setThumb(new UMImage(WebViewActivity.this, decodeFile));
                                    new ShareAction(mContext).setPlatform(SHARE_MEDIA.QQ)
                                            .withMedia(webpic)
                                            .setCallback(umShareListener)
                                            .share();
                                } else if (share_media == SHARE_MEDIA.WEIXIN) {
                                    webpic.setThumb(new UMImage(WebViewActivity.this, filelogo));
                                    new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN)
                                            .withMedia(webpic)
                                            .setCallback(umShareListener)
                                            .share();
                                } else if (share_media == SHARE_MEDIA.QZONE) {
                                    new ShareAction(mContext).setPlatform(SHARE_MEDIA.QZONE)
                                            .withMedia(webpic)
                                            .setCallback(umShareListener)
                                            .share();
                                } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
                                    new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                            .withMedia(webpic)
                                            .setCallback(umShareListener)
                                            .share();
                                }
                            }
                        }).open();
            }
        }
        //H5CRM我的日报分享
        @JavascriptInterface
        public   void  sharemessage(){
            bimpic();
            if(file!=null){
                UMImage image = new UMImage(WebViewActivity.this, file);
                image.setThumb(new UMImage(WebViewActivity.this, file ));
                new ShareAction(mContext)
                        .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                if (share_media == SHARE_MEDIA.QQ) {
                                    new ShareAction(mContext).setPlatform(SHARE_MEDIA.QQ)
                                            .withMedia(image)
                                            .setCallback(umShareListener)
                                            .share();
                                } else if (share_media == SHARE_MEDIA.WEIXIN) {
                                    new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN)
                                            .withMedia(image)
                                            .setCallback(umShareListener)
                                            .share();
                                } else if (share_media == SHARE_MEDIA.QZONE) {
                                    new ShareAction(mContext).setPlatform(SHARE_MEDIA.QZONE)
                                            .withMedia(image)
                                            .setCallback(umShareListener)
                                            .share();
                                } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
                                    new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                            .withMedia(image)
                                            .setCallback(umShareListener)
                                            .share();
                                }
                            }
                        }).open();
            }
        }
    }

    //H5CRM我的日报分享----把Webview生成图片
    private void bimpic() {
        webview.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        webview.layout(0, 0, webview.getMeasuredWidth(), webview.getMeasuredHeight());
        webview.setDrawingCacheEnabled(true);
        webview.buildDrawingCache();
        Bitmap longImage = Bitmap.createBitmap(webview.getMeasuredWidth(),
                webview.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(longImage);  // 画布的宽高和 WebView 保持一致
        Paint paint = new Paint();
        canvas.drawBitmap(longImage, 0, webview.getMeasuredHeight(), paint);
        webview.draw(canvas);
        //将截取的图片保存到本地
        File appDir = new File(Environment.getExternalStorageDirectory(), "小麒乖乖的日报");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        file= new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            longImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);


            fos.flush();
            fos.close();
            // 最后通知图库更新
            this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(file.getPath()))));
            } catch (Exception e) {
                toast(e.getMessage());
            }
        }
    //H5获取商店logo图片保存到本地
    public void returnBitMap( String url){
        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> e) throws Exception {
                //通过gilde下载得到file文件,这里需要注意android.permission.INTERNET权限
                e.onNext(Glide.with(mContext)
                        .load(url)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {
                        //获取到下载得到的图片，进行本地保存
                        File pictureFolder = Environment.getExternalStorageDirectory();
                        //第二个参数为你想要保存的目录名称
                        File appDir = new File(pictureFolder, "bandlogo");
                        if (!appDir.exists()) {
                            appDir.mkdirs();
                        }
                        String fileName = System.currentTimeMillis() + ".jpg";
                        File destFile = new File(appDir, fileName);
                        //把gilde下载得到图片复制到定义好的目录中去
                        copy(file, destFile);
                        filelogo=new File(destFile.getPath());
                        // 最后通知图库更新
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.fromFile(new File(destFile.getPath()))));
                        decodeFile = destFile.getPath();
                        Looper.prepare();
                        Looper.loop();
                    }

                });

    }
    //H5获取商店logo图片保存到本地
    public void returnBitMap2( String url){
        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> e) throws Exception {
                //通过gilde下载得到file文件,这里需要注意android.permission.INTERNET权限
                e.onNext(Glide.with(mContext)
                        .load(url)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {
                        //获取到下载得到的图片，进行本地保存
                        File pictureFolder = Environment.getExternalStorageDirectory();
                        //第二个参数为你想要保存的目录名称
                        File appDir = new File(pictureFolder, "quanyilogo");
                        if (!appDir.exists()) {
                            appDir.mkdirs();
                        }
                        String fileName = System.currentTimeMillis() + ".jpg";
                        File destFile = new File(appDir, fileName);
                        //把gilde下载得到图片复制到定义好的目录中去
                        copy(file, destFile);
                        filelogo2=new File(destFile.getPath());
                        // 最后通知图库更新
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.fromFile(new File(destFile.getPath()))));
                        decodeFile2 = destFile.getPath();
                        Looper.prepare();
                        Looper.loop();
                    }

                });

    }
     //H5分享--帮助中心
    private void share() {
        UMImage image = new UMImage(this, webview.getTitle().contains("帮助信息") ? R.drawable.logo : R.drawable.img_gch);//分享图标
        final UMWeb web = new UMWeb(webview.getUrl()); //切记切记 这里分享的链接必须是http开头
        web.setTitle(mTitle);//标题
        web.setThumb(image);  //缩略图
//        if (webview.getTitle().contains("帮助中心")) {
//            web.setDescription();//描述
//        }else {
//            web.setDescription();
//        }

        web.setDescription(webview.getTitle().contains("帮助信息") ? "小麒乖乖注册，开启你的财富人生" : "小麒乖乖企业培训");

        new ShareAction(mContext)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (share_media == SHARE_MEDIA.QQ) {
                            new ShareAction(mContext).setPlatform(SHARE_MEDIA.QQ)
                                    .withMedia(web)
                                    .setCallback(umShareListener)
                                    .share();
                        } else if (share_media == SHARE_MEDIA.WEIXIN) {
                            new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN)
                                    .withMedia(web)
                                    .setCallback(umShareListener)
                                    .share();
                        } else if (share_media == SHARE_MEDIA.QZONE) {
                            new ShareAction(mContext).setPlatform(SHARE_MEDIA.QZONE)
                                    .withMedia(web)
                                    .setCallback(umShareListener)
                                    .share();
                        } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
                            new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                    .withMedia(web)
                                    .setCallback(umShareListener)
                                    .share();
                        }
                    }
                }).open();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
            public  void shape(ProductDetailEvent event){
        image = event.getImage();
        title = event.getTitle();
        count = event.getCount();
    }
    public void share2(String image,String title ,String count ) {
        returnBitMap(image);
        if (!"".equals(filelogo)) {
            UMWeb webpic = new UMWeb(Constans.productDetails);
            webpic.setThumb(new UMImage(WebViewActivity.this, filelogo));
            webpic.setTitle(title);
            //  web.setThumb(new UMImage(WebViewActivity.this, proLogo));
            webpic.setDescription(count);
            new ShareAction(mContext)
                    .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setShareboardclickCallback(new ShareBoardlistener() {
                        @Override
                        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                            if (share_media == SHARE_MEDIA.QQ) {
                                webpic.setThumb(new UMImage(WebViewActivity.this, decodeFile));
                                new ShareAction(mContext).setPlatform(SHARE_MEDIA.QQ)
                                        .withMedia(webpic)
                                        .setCallback(umShareListener)
                                        .share();
                            } else if (share_media == SHARE_MEDIA.WEIXIN) {
                                webpic.setThumb(new UMImage(WebViewActivity.this, filelogo));
                                new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN)
                                        .withMedia(webpic)
                                        .setCallback(umShareListener)
                                        .share();
                            } else if (share_media == SHARE_MEDIA.QZONE) {
                                new ShareAction(mContext).setPlatform(SHARE_MEDIA.QZONE)
                                        .withMedia(webpic)
                                        .setCallback(umShareListener)
                                        .share();
                            } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
                                new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                        .withMedia(webpic)
                                        .setCallback(umShareListener)
                                        .share();
                            }
                        }
                    }).open();
        }
    }





    //分享后回调的方法
    UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            toast("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            toast("分享失败" + t.toString());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            toast("分享取消");
        }
    };

    //设置系统返回键监听H5返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String url = webview.getOriginalUrl();
            Log.d("xxxx", "onKeyDown: " + url);
            webview.loadUrl(mUrl);
            if (url.contains("h5FuminSuccess.html")){
                webview.loadUrl(mUrl);
            }
            else if(url.contains(mUrl)) {
                return super.onKeyDown(keyCode, event);
            }else {
                if (webview != null && webview.canGoBack()) {
                    webview.goBack();
                    return true;
                } else {
                    return super.onKeyDown(keyCode, event);
                }
            }
        }
        return false;
    }
    //设置监听H5返回键
    private void goback() {
     String url=   webview.getUrl();
        if (url.contains("h5FuminSuccess.html")){
          webview.loadUrl(mUrl);
        }else if(url.contains(mUrl)){
          finish();
        }else {
            if (webview != null && webview.canGoBack()) {
                Log.e("zlz", "goback");
                webview.goBack();
                return;
            }
            else {
                Log.e("zlz", "finish");
                finish();
            }
        }
    }

    //设置图片类型
    @Override
    public void clickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PHOTO);
    }
    //设置相机属性
    @Override
    public void clickCamera() {
        try {
            imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), System.currentTimeMillis() + ".jpg");
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(this, "com.lx.xqgg.fileProvider", imageFile);
            } else {
                uri = Uri.fromFile(imageFile);
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        } catch (SecurityException e) {
            toast("请在设置中打开权限！");
        }
    }
    //设置Android4.0点击监听
    @Override
    public void clickNull() {
        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
        } else if (mUploadCallbackAboveL != null) {
            mUploadCallbackAboveL.onReceiveValue(null);
            mUploadCallbackAboveL = null;
        }
    }
    //设置返回当前Activity设置的数据返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PHOTO) {
            /**选图**/
            if (null == mUploadMessage && null == mUploadCallbackAboveL) {
                return;
            }
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (result != null) {
                Uri imageUri = result;
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;
                } else if (mUploadCallbackAboveL != null) {
                    mUploadCallbackAboveL.onReceiveValue(new Uri[]{imageUri});
                    mUploadCallbackAboveL = null;
                }
            } else {
                clickNull();
            }
        } else if (requestCode == REQUEST_CODE_CAMERA) {
            /**拍照**/
            if (null == mUploadMessage && null == mUploadCallbackAboveL) {
                return;
            }
            if (resultCode == RESULT_OK) {
                Uri imageUri = Uri.fromFile(imageFile);
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;
                } else if (mUploadCallbackAboveL != null) {
                    mUploadCallbackAboveL.onReceiveValue(new Uri[]{imageUri});
                    mUploadCallbackAboveL = null;
                }
            } else if (resultCode == RESULT_CANCELED) {
                clickNull();
            }
        } else if (requestCode == REQUEST_CODE_VIDEO) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) {
                return;
            }
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (result != null) {
                Uri imageUri = result;
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;
                } else if (mUploadCallbackAboveL != null) {
                    mUploadCallbackAboveL.onReceiveValue(new Uri[]{imageUri});
                    mUploadCallbackAboveL = null;
                }
            } else {
                clickNull();
            }
        }
    }
//设置传值的Builde对象
    public static class Builder {

        private String title;
        private String url;
        private Context context;
        private boolean autoTitle;
        private boolean isFWB;
        private boolean needShare;

        public Builder() {

        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setAutoTitle(boolean autoTitle) {
            this.autoTitle = autoTitle;
            return this;
        }

        public Builder setIsFwb(boolean isFWB) {
            this.isFWB = isFWB;
            return this;
        }

        public Builder setNeedShare(boolean needShare) {
            this.needShare = needShare;
            return this;
        }
    }
     //设置WebView的构建者模式的参数类型
    public static void open(Builder builder) {
        Intent intent = new Intent(builder.context, WebViewActivity.class);
        intent.putExtra("title", builder.title);
        intent.putExtra("url", builder.url);
        intent.putExtra("autotitle", builder.autoTitle);
        intent.putExtra("isFwb", builder.isFWB);
        intent.putExtra("needShare", builder.needShare);
        builder.context.startActivity(intent);
    }
    public static void open(Builder builder,Boolean fumin) {
        Intent intent = new Intent(builder.context, WebViewActivity.class);
        intent.putExtra("title", builder.title);
        intent.putExtra("url", builder.url);
        intent.putExtra("autotitle", builder.autoTitle);
        intent.putExtra("isFwb", builder.isFWB);
        intent.putExtra("needShare", builder.needShare);
        intent.putExtra("fumin",fumin);
        builder.context.startActivity(intent);
    }
    //设置销毁回收
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        webview.destroy();
    }
}
