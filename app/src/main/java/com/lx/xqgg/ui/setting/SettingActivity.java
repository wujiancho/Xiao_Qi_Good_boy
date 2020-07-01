package com.lx.xqgg.ui.setting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.company_auth.ChooseDialogFragment;
import com.lx.xqgg.ui.explain.ExplainActivity;
import com.lx.xqgg.ui.login.LoginActivity;
import com.lx.xqgg.util.AppActivityTaskManager;
import com.lx.xqgg.util.AppApplicationUtil;
import com.lx.xqgg.util.JPushUtil;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SettingActivity extends BaseActivity implements ChooseDialogFragment.OnChooseClickListener {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.tv_version_code)
    TextView tvVersionCode;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.iv_head)
    CircleImageView circleImageView;

    //照片文件父文件夹
    private File dirFile;
    //占位照片文件1
    private File headFile;

    private ChooseDialogFragment chooseDialogFragment;

    private Tiny.FileCompressOptions options;

    private static final int REQUEST_CAMERA1 = 0001;

    private static final int REQUEST_IMG1 = 0002;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        tvTitle.setText("设置");
        tvVersionCode.setText("V" + AppApplicationUtil.getVersionName(mContext));
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

    }

    @OnClick({R.id.v_close, R.id.btn_logout, R.id.layout_yhfwxy, R.id.layout_ysbhsm, R.id.layout_yjfk,R.id.iv_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.layout_yhfwxy:
                Intent intent = new Intent(mContext, ExplainActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_slient);
                break;
            case R.id.layout_ysbhsm:
                Intent intent1 = new Intent(mContext, ExplainActivity.class);
                intent1.putExtra("type", 1);
                startActivity(intent1);
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_slient);
                break;
            case R.id.layout_yjfk:
                startActivity(new Intent(mContext, FeedBackActivity.class));
                break;
            case R.id.btn_logout:
                SharedPrefManager.clearLoginInfo();
                JPushUtil.initJPush(SettingActivity.this, "", null);
                Intent intent_login = new Intent();
                intent_login.setClass(SettingActivity.this, LoginActivity.class);
                intent_login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                startActivity(intent_login);
                AppActivityTaskManager.getInstance().removeAllActivity();
                finish();
                break;
            case R.id.iv_head:
                chooseDialogFragment.show(getSupportFragmentManager(),"");
                break;
        }
    }


    @Override
    public void clickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_IMG1);
    }

    @Override
    public void clickCamera() {
        headFile = new File(dirFile, "xqhead.jpg");
        openCamera(headFile);
    }

    @Override
    public void clickNull() {

    }

    private void openCamera(File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, "com.lx.xqgg.fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CAMERA1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA1) {
            if (resultCode == RESULT_OK) {
                Tiny.getInstance().source(headFile).asFile().withOptions(options).compress(new FileCallback() {
                    @Override
                    public void callback(boolean isSuccess, String outfile, Throwable t) {
                        Log.e("zlz", outfile);
                        headFile = new File(outfile);
                        Bitmap bm = BitmapFactory.decodeFile(outfile);
                        circleImageView.setImageBitmap(bm);
                        //上传头像
                        uploadHead(headFile);
                    }
                });
            }
        }

        if (requestCode == REQUEST_IMG1) {
            if (resultCode == RESULT_OK) {
                Tiny.getInstance().source(data.getData()).asFile().withOptions(options).compress(new FileCallback() {
                    @Override
                    public void callback(boolean isSuccess, String outfile, Throwable t) {
                        Log.e("zlz", outfile + "");
                        Uri imageUri = Uri.fromFile(new File(outfile));
                        headFile = new File(outfile);
                        Bitmap bm = BitmapFactory.decodeFile(outfile);
                        circleImageView.setImageBitmap(bm);
                        uploadHead(headFile);
                    }
                });
            }
        }
    }

    private void uploadHead(File file) {
        RequestBody file1 = RequestBody.create(MediaType.parse("image/*"), file);
        Map<String, RequestBody> map = new HashMap<>();
        map.put("file\"; filename=\"" + "xqhead.png", file1);

    }
}
