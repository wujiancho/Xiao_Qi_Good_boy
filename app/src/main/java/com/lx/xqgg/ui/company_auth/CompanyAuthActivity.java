package com.lx.xqgg.ui.company_auth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.company_auth.bean.ImgBean;
import com.lx.xqgg.ui.home.bean.UserServiceBean;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CompanyAuthActivity extends BaseActivity implements ChooseDialogFragment.OnChooseClickListener {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.et_cpmpany_name)
    EditText etCpmpanyName;
    @BindView(R.id.et_fzr_name)
    EditText etFzrName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_real_name)
    EditText etRealName;
    @BindView(R.id.et_address_detail)
    EditText etAddressDetail;
    @BindView(R.id.iv_yyzz)
    ImageView ivYyzz;
    @BindView(R.id.iv_qtzp)
    ImageView ivQtzp;
    @BindView(R.id.iv_bgshj)
    ImageView ivBgshj;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.img_clear1)
    ImageView imgClear1;
    @BindView(R.id.img_clear2)
    ImageView imgClear2;
    @BindView(R.id.img_clear3)
    ImageView imgClear3;

    private boolean edFocus = false;

    private int id = 0;

    private int code = 1;

    private static final int REQUEST_CAMERA1 = 0001;

    private static final int REQUEST_CAMERA2 = 0002;

    private static final int REQUEST_CAMERA3 = 0003;


    private static final int REQUEST_IMG1 = 0004;

    private static final int REQUEST_IMG2 = 0005;

    private static final int REQUEST_IMG3 = 0006;

    //占位照片文件1
    private File testFile1;

    //占位照片文件2
    private File testFile2;

    //占位照片文件3
    private File testFile3;

    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;

    private boolean img1IsShown = false;
    private boolean img2IsShown = false;
    private boolean img3IsShown = false;


    //照片文件父文件夹
    private File dirFile;

    private ChooseDialogFragment chooseDialogFragment;

    private Tiny.FileCompressOptions options;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_auth;
    }

    @Override
    protected void initView() {
        tvTitle.setText("企业认证");
        chooseDialogFragment = new ChooseDialogFragment();
        chooseDialogFragment.setOnChooseClickListener(this);
        options = new Tiny.FileCompressOptions();
        dirFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/XQGG");
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
    }

    @Override
    protected void initData() {
        etCpmpanyName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edFocus = true;
                } else {
                    if (edFocus) {
                        if (!TextUtils.isEmpty(etCpmpanyName.getText().toString())) {
                            Log.e("zlz", "查询");
                            searchInfo(etCpmpanyName.getText().toString());
                        }
                    }
                    edFocus = false;
                }
            }
        });
    }

    /**
     * 根据名字获取服务商信息
     *
     * @param msg
     */
    private void searchInfo(String msg) {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("service_name", msg);
        paramsMap.put("appId", Constans.APPID);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getSericeByName(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<UserServiceBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<UserServiceBean> userServiceBeanBaseData) {
                        Log.e("zlz", new Gson().toJson(userServiceBeanBaseData));
                        if (userServiceBeanBaseData.isSuccess()) {
                            if (userServiceBeanBaseData.getData() != null) {
                                //审核通过，什么也不用改
                                etCpmpanyName.setText(userServiceBeanBaseData.getData().getService_name() + "");
                                etFzrName.setText(userServiceBeanBaseData.getData().getLegal_person() + "");
                                etPhone.setText(userServiceBeanBaseData.getData().getPhone() + "");
                                etAddressDetail.setText(userServiceBeanBaseData.getData().getAddress() + "");

                                if(userServiceBeanBaseData.getData().getBusiness_license()!=null) {
                                    Glide.with(mContext)
                                            .load(Config.IMGURL + userServiceBeanBaseData.getData().getBusiness_license())
                                            .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                                            .into(ivYyzz);
                                    img1IsShown = true;
                                }

                                if(userServiceBeanBaseData.getData().getFrontImage()!=null) {
                                    Glide.with(mContext)
                                            .load(Config.IMGURL + userServiceBeanBaseData.getData().getFrontImage())
                                            .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                                            .into(ivQtzp);
                                    img2IsShown = true;
                                }

                                if(userServiceBeanBaseData.getData().getWorkImage()!=null) {
                                    Glide.with(mContext)
                                            .load(Config.IMGURL + userServiceBeanBaseData.getData().getWorkImage())
                                            .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                                            .into(ivBgshj);
                                    img3IsShown = true;
                                }


                                if (userServiceBeanBaseData.getData().getStatus().equals("normal")) {
                                    etCpmpanyName.setEnabled(false);
                                    etFzrName.setEnabled(false);
                                    etPhone.setEnabled(false);
                                    etAddressDetail.setEnabled(false);
                                } else {
                                    //未通过 可以修改
                                    id = userServiceBeanBaseData.getData().getId();
                                }
                            }
                        }
                    }
                }));
    }


    @OnClick({R.id.v_close, R.id.iv_yyzz, R.id.iv_qtzp, R.id.iv_bgshj, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.iv_yyzz:
                code = 0001;
                chooseDialogFragment.show(getSupportFragmentManager(), "");
//                testFile1 = new File(dirFile, "yyzz1.jpg");
//                openCamera(testFile1, 0001);
                break;
            case R.id.iv_qtzp:
                code = 0002;
                chooseDialogFragment.show(getSupportFragmentManager(), "");
//                testFile2 = new File(dirFile, "qtzp2.jpg");
//                openCamera(testFile2, 0002);
                break;
            case R.id.iv_bgshj:
                code = 0003;
                chooseDialogFragment.show(getSupportFragmentManager(), "");
//                testFile3 = new File(dirFile, "bgshj3.jpg");
//                openCamera(testFile3, 0003);
                break;
            case R.id.btn_commit:
                commit();
                break;
        }
    }

    private void commit() {
        if (TextUtils.isEmpty(etCpmpanyName.getText().toString())) {
            toast("请输入企业名称");
            return;
        }
        if (TextUtils.isEmpty(etFzrName.getText().toString())) {
            toast("请输入负责人姓名");
            return;
        }
        if (TextUtils.isEmpty(etPhone.getText().toString())) {
            toast("请输入负责人手机号");
            return;
        }
        if (TextUtils.isEmpty(etRealName.getText().toString())) {
            toast("请输入自己真实姓名");
            return;
        }
        if (TextUtils.isEmpty(etAddressDetail.getText().toString())) {
            toast("请输入详细地址");
            return;
        }
        if (img1IsShown && img2IsShown && img3IsShown) {
            Map<String, RequestBody> map = new HashMap<>();
            if (null != testFile1) {
                RequestBody file1 = RequestBody.create(MediaType.parse("image/*"), testFile1);
                map.put("file\"; filename=\"" + "1.png", file1);
            }
            if (null != testFile2) {
                RequestBody file2 = RequestBody.create(MediaType.parse("image/*"), testFile2);
                map.put("file\"; filename=\"" + "2.png", file2);
            }
            if (null != testFile3) {
                RequestBody file3 = RequestBody.create(MediaType.parse("image/*"), testFile3);
                map.put("file\"; filename=\"" + "3.png", file3);
            }

            if (testFile1 == null && testFile2 == null && testFile3 == null) {
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("token", SharedPrefManager.getUser().getToken());
                paramsMap.put("id", id == 0 ? "" : id + "");
                paramsMap.put("service_name", etCpmpanyName.getText().toString().trim());
                paramsMap.put("address", etAddressDetail.getText().toString().trim());
                paramsMap.put("principal_name", etFzrName.getText().toString().trim());
                paramsMap.put("mobile", etPhone.getText().toString());
                paramsMap.put("image", "");
                paramsMap.put("content", "");
                paramsMap.put("appId", Constans.APPID);
                paramsMap.put("name", etRealName.getText().toString().trim());
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                addSubscribe(ApiManage.getInstance().getMainApi().serviceAuth(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseSubscriber<BaseData<Object>>(mContext, "") {
                            @Override
                            public void onNext(BaseData<Object> objectBaseData) {
                                Log.e("zlz", new Gson().toJson(objectBaseData));
                                if (objectBaseData.isSuccess()) {
                                    toast("提交成功");
                                    startActivity(new Intent(mContext, CompanyAuthSuccActivity.class));
                                    finish();
                                } else {
                                    showDialog(objectBaseData.getMessage());
                                }
                            }
                        }));
            } else {
                addSubscribe(ApiManage.getInstance().getMainApi().upload(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseSubscriber<BaseData<List<ImgBean>>>(mContext, "") {
                            @Override
                            public void onNext(BaseData<List<ImgBean>> o) {
                                Log.e("zlz",new Gson().toJson(o));
                                if (o.isSuccess()) {
                                    List<ImgBean> list = o.getData();

                                    HashMap<String, Object> paramsMap = new HashMap<>();
                                    paramsMap.put("token", SharedPrefManager.getUser().getToken());
                                    paramsMap.put("id", id == 0 ? "" : id + "");
                                    paramsMap.put("service_name", etCpmpanyName.getText().toString().trim());

                                    paramsMap.put("address", etAddressDetail.getText().toString().trim());
                                    paramsMap.put("principal_name", etFzrName.getText().toString().trim());
                                    paramsMap.put("mobile", etPhone.getText().toString());
                                    paramsMap.put("image", "");
                                    paramsMap.put("content", "");
                                    paramsMap.put("appId", Constans.APPID);
                                    paramsMap.put("name", etRealName.getText().toString().trim());

                                    for(int i=0;i<list.size();i++){
                                        if(list.get(i).getFilename().contains("1.png")){
                                            paramsMap.put("business_license", list.get(i).getFjdz());
                                        }
                                        if(list.get(i).getFilename().contains("2.png")){
                                            paramsMap.put("frontImage", list.get(i).getFjdz());
                                        }
                                        if(list.get(i).getFilename().contains("3.png")){
                                            paramsMap.put("workImage", list.get(i).getFjdz());
                                        }
                                    }

                                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                                    addSubscribe(ApiManage.getInstance().getMainApi().serviceAuth(body)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribeWith(new BaseSubscriber<BaseData<Object>>(mContext, "") {
                                                @Override
                                                public void onNext(BaseData<Object> objectBaseData) {
                                                    Log.e("zlz", new Gson().toJson(objectBaseData));
                                                    if (objectBaseData.isSuccess()) {
                                                        toast("提交成功");
                                                        startActivity(new Intent(mContext, CompanyAuthSuccActivity.class));
                                                        finish();
                                                    } else {
                                                        showDialog(objectBaseData.getMessage());
                                                    }
                                                }
                                            }));
                                } else {
                                    showDialog("照片上传失败，请稍后重试");
                                }
                            }
                        }));
            }
        } else {
            toast("请上传照片！");
        }
    }

    @Override
    public void clickPhoto() {
        switch (code) {
            case 0001:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                startActivityForResult(intent, 0004);
                break;
            case 0002:
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");//相片类型
                startActivityForResult(intent1, 0005);
                break;
            case 0003:
                Intent intent2 = new Intent(Intent.ACTION_PICK);
                intent2.setType("image/*");//相片类型
                startActivityForResult(intent2, 0006);
                break;

        }
    }

    private void openCamera(File file, int requestCode) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, "com.lx.xqgg.fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void clickCamera() {
        switch (code) {
            case 0001:
                testFile1 = new File(dirFile, "yyzz1.jpg");
                openCamera(testFile1, code);
                break;
            case 0002:
                testFile2 = new File(dirFile, "qtzp2.jpg");
                openCamera(testFile2, code);
                break;
            case 0003:
                testFile3 = new File(dirFile, "bgshj3.jpg");
                openCamera(testFile3, code);
                break;
        }
    }

    @Override
    public void clickNull() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("zlz",requestCode+"");
        if (requestCode == REQUEST_CAMERA1) {
            if (resultCode == RESULT_OK) {
                Tiny.getInstance().source(testFile1).asFile().withOptions(options).compress(new FileCallback() {
                    @Override
                    public void callback(boolean isSuccess, String outfile, Throwable t) {
                        Log.e("zlz", outfile+"");
                        testFile1 = new File(outfile);
                        Bitmap bm = BitmapFactory.decodeFile(outfile);
                        ivYyzz.setImageBitmap(bm);
                        img1IsShown = true;
                        imgClear1.setVisibility(View.VISIBLE);
                    }
                });

            } else if (resultCode == RESULT_CANCELED) {
                img1IsShown = false;
                imgClear1.setVisibility(View.GONE);
            }
            return;
        }
        if (requestCode == REQUEST_CAMERA2) {
            if (resultCode == RESULT_OK) {
                Tiny.getInstance().source(testFile2).asFile().withOptions(options).compress(new FileCallback() {
                    @Override
                    public void callback(boolean isSuccess, String outfile, Throwable t) {
                        Log.e("zlz", outfile);
                        testFile2 = new File(outfile);
                        Bitmap bm = BitmapFactory.decodeFile(outfile);
                        ivQtzp.setImageBitmap(bm);
                        img2IsShown = true;
                        imgClear2.setVisibility(View.VISIBLE);
                    }
                });
            } else if (resultCode == RESULT_CANCELED) {
                img2IsShown = false;
                imgClear2.setVisibility(View.GONE);
            }
            return;
        }
        if (requestCode == REQUEST_CAMERA3) {
            if (resultCode == RESULT_OK) {
                Tiny.getInstance().source(testFile3).asFile().withOptions(options).compress(new FileCallback() {
                    @Override
                    public void callback(boolean isSuccess, String outfile, Throwable t) {
                        Log.e("zlz", outfile);
                        testFile3 = new File(outfile);
                        Bitmap bm = BitmapFactory.decodeFile(outfile);
                        ivBgshj.setImageBitmap(bm);
                        img3IsShown = true;
                        imgClear3.setVisibility(View.VISIBLE);
                    }
                });
            } else if (resultCode == RESULT_CANCELED) {
                img3IsShown = false;
                imgClear3.setVisibility(View.GONE);
            }
            return;
        }
        if (requestCode == 0004) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Tiny.getInstance().source(data.getData()).asFile().withOptions(options).compress(new FileCallback() {
                    @Override
                    public void callback(boolean isSuccess, String outfile, Throwable t) {
                        Log.e("zlz", outfile + "");
//                        Uri imageUri = Uri.fromFile(new File(outfile));
                        testFile1 = new File(outfile);
                        Bitmap bm = BitmapFactory.decodeFile(outfile);
                        ivYyzz.setImageBitmap(bm);
                        img1IsShown = true;
                        imgClear1.setVisibility(View.VISIBLE);
                    }
                });
            }
            return;
        }
        if (requestCode == 0005) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Tiny.getInstance().source(data.getData()).asFile().withOptions(options).compress(new FileCallback() {
                    @Override
                    public void callback(boolean isSuccess, String outfile, Throwable t) {
                        Log.e("zlz", outfile + "");
//                        Uri imageUri = Uri.fromFile(new File(outfile));
                        testFile2 = new File(outfile);
                        Bitmap bm = BitmapFactory.decodeFile(outfile);
                        ivQtzp.setImageBitmap(bm);
                        img2IsShown = true;
                        imgClear2.setVisibility(View.VISIBLE);
                    }
                });
            }
            return;
        }
        if (requestCode == 0006) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Tiny.getInstance().source(data.getData()).asFile().withOptions(options).compress(new FileCallback() {
                    @Override
                    public void callback(boolean isSuccess, String outfile, Throwable t) {
                        Log.e("zlz", outfile + "");
//                        Uri imageUri = Uri.fromFile(new File(outfile));
                        testFile3 = new File(outfile);
                        Bitmap bm = BitmapFactory.decodeFile(outfile);
                        ivBgshj.setImageBitmap(bm);
                        img3IsShown = true;
                        imgClear3.setVisibility(View.VISIBLE);
                    }
                });
            }
            return;
        }
    }
}
