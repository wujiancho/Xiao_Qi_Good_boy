package com.lx.xqgg.ui.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.share.bean.Regmg;
import com.lx.xqgg.ui.vip.bean.PayListBean;
import com.lx.xqgg.util.Base64;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 表层分享 App二维码分享 长按二维码保存本地 加第三方分享
 */
public class ShareFaceActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.erweima)
    ImageView erweima;
    String erwermaurl;
    String erwermaphone;
    private MaterialDialog materialDialog;
    private String name;
    private String phone;
    private String imgUrl = "";
    private  Bitmap bitwangluo;
    private Bitmap bitmap;
    Bitmap bmp;
    File file;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_face_share;
    }

    @Override
    protected void initView() {
        tvTitle.setText("推荐有礼");
    }

    @Override
    protected void initData() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("group", "iosShare");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getPayList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<PayListBean>>>(mContext, null) {

                    private Regmg regmg;

                    @Override
                    public void onNext(BaseData<List<PayListBean>> listBaseData) {
                        Log.e("zlz", new Gson().toJson(listBaseData));
                        Log.e("zlz", Config.IMGURL + listBaseData.getData().get(0).getValue());
                        if (listBaseData.isSuccess()) {
                            if (listBaseData.getData() != null) {
                                imgUrl = Config.IMGURL + listBaseData.getData().get(0).getValue();
                                Glide.with(mContext)
                                        .load(Config.IMGURL + listBaseData.getData().get(0).getValue())
                                        .apply(new RequestOptions().placeholder(R.drawable.ic_default).diskCacheStrategy(DiskCacheStrategy.NONE))
                                        .into(imageView);
                                //接受的姓名和推荐的手机号
                                name=SharedPrefManager.getUserInfo().getUsername();
                                phone=SharedPrefManager.getUserInfo().getMobile();
                                //设置名字的隐藏
                                if(name!=null){
                                    if (name.length()==2){
                                        String name1=name.substring(0,1);
                                        String nameer =name1+"*";
                                        erwermaurl=nameer;
                                }
                                   else if (name.length()==3){
                                        String name1=name.substring(0,1);
                                        String name3=name.substring(1,2);
                                        String nameer2 =name1+"*"+name3;
                                        erwermaurl=nameer2;
                                }
                                }
                                // //设置手机号的隐藏
                                if (phone!=null){
                                    String phonel=String.valueOf(phone);
                                    String phoen1=  phonel.substring(0,3);
                                    String phoen2 =phonel.substring(phonel.length()-4,phonel.length());
                                    erwermaphone=phoen1+"****"+phoen2;
                                }
                                //接受的数据生成jsonbean数据
                                ArrayList<Regmg> gson= new ArrayList<>();
                                regmg = new Regmg();
                                regmg.setName(name);
                                regmg.setName1(erwermaurl);
                                regmg.setMobile(phone);
                                regmg.setMobile1(erwermaphone);
                                gson.add(regmg);
                                String url=new Gson().toJson(gson);
                                String json =url.substring(1,url.length()-1);
                                Log.e("zlz",json);
                                //加密json
                                String jiajson= Base64.encode(json.getBytes());
                                //生成注册的接口
                                String jiekong=Config.URL+"view/register.html?bean="+jiajson;
                                //生成二维码
                                bitmap= CodeUtils.createImage(jiekong, 600, 600, null);
                                //saveImage(bitmap);
                                Log.e("zlz",jiekong);
                                //设置二维码图片
                                erweima.setImageBitmap(bitmap);
                                //缓存数据布局图到本地
                                Observable.create(new ObservableOnSubscribe<File>() {
                                    @Override
                                    public void subscribe(ObservableEmitter<File> e) throws Exception {
                                        //通过gilde下载得到file文件,这里需要注意android.permission.INTERNET权限
                                        e.onNext(Glide.with(mContext)
                                                .load(imgUrl)
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
                                                File appDir = new File(pictureFolder, "BIsa");
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
                                                bitwangluo= BitmapFactory.decodeFile(destFile.getPath());
                                                Log.e("zlz", destFile.getPath());
                                                Looper.prepare();
                                                Looper.loop();
                                            }

                                        });




                                erweima.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View v) {
                                        showSaveDialog();
                                        return false;
                                    }
                                });
                                return;
                            }
                        }
                        toast("获取配置失败！");
                        finish();
                    }
                }));
    }
   //缓存到本地组合的图片
    public void saveImage2(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "小麒乖乖");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
         file= new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Log.e("zlz", file.getPath());

            fos.flush();
            fos.close();
            // 最后通知图库更新
            this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(file.getPath()))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //缩放
    private Bitmap secalBitmap (Bitmap bitmap,Float ratin){
        if(bitmap==null){
            return null;
        }
       int  width= bitmap.getWidth();
       int  height=bitmap.getHeight();
        Matrix matrix =new Matrix();
        matrix.preScale(ratin,ratin);
       Bitmap bd= Bitmap.createBitmap(bitmap,0,0,width,height,matrix,false);
        if (bd==bitmap){
            return bd;
        }
        bitmap.recycle();
        return bd;
    }
    //合拼图片
    private Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        firstBitmap=secalBitmap(firstBitmap,0.5f);
 Bitmap bitmap = Bitmap.createBitmap(secondBitmap.getWidth(), secondBitmap.getHeight(),
                secondBitmap.getConfig());
Canvas canvas = new Canvas(bitmap);
canvas.drawBitmap(secondBitmap, new Matrix(), null);
 canvas.drawBitmap(firstBitmap, 230f, 450f, null);
 return bitmap;
    }
    @OnClick({R.id.v_close, R.id.tv_share})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.tv_share:
                //分享图片
                //设置组合的图片
                bmp=mergeBitmap(bitmap,bitwangluo);
                saveImage2(bmp);
                if (file!=null) {
                    UMImage image = new UMImage(ShareFaceActivity.this,file );
                    image.setThumb(new UMImage(this, file ));
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
                    break;
                }
        }

    }

    UMShareListener umShareListener = new UMShareListener( ) {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            toast("分享成功");
            finish();
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


    private void showSaveDialog() {

        materialDialog = new MaterialDialog.Builder(this)
                .title("提示")
                .content("保存图片到相册")
                .cancelable(false)
                .positiveText(R.string.yes)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //设置组合的图片
                        bmp=mergeBitmap(bitmap,bitwangluo);
                        saveImage2(bmp);
                        if (bmp!=null){
                            dialog.dismiss();
                            toast("保存成功");
                        }
                        //二维码
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


}
