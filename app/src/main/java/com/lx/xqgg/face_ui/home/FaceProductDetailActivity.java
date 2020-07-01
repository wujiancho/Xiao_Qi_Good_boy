package com.lx.xqgg.face_ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.face_ui.home.bean.TopListBean;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

public class FaceProductDetailActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.tv_introduction)
    TextView tvIntroduction;
    @BindView(R.id.tv_outline)
    TextView tvOutline;
    @BindView(R.id.tv_reap)
    TextView tvReap;
    @BindView(R.id.btn_phone)
    Button btnPhone;
    @BindView(R.id.btn_apply)
    Button btnApply;
    @BindView(R.id.tv_name)
    TextView tvName;

    private TopListBean topListBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_face_product_detail;
    }

    @Override
    protected void initView() {
        topListBean = (TopListBean) getIntent().getSerializableExtra("data");
    }

    @Override
    protected void initData() {
        Glide.with(mContext)
                .load(topListBean.getImg())
                .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                .into(image);
        tvTitle.setText(topListBean.getName());
        tvName.setText(topListBean.getName());
        tvIntroduction.setText(topListBean.getIntroduction());
        tvOutline.setText(topListBean.getOutline());
        tvReap.setText(topListBean.getReap());
    }

    @OnClick({R.id.v_close, R.id.btn_phone, R.id.btn_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_phone:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setMessage("是否拨打客服电话：4001391717");
                builder1.setTitle("温馨提示");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mIntent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:4001391717");
                        mIntent.setData(data);
                        //Android6.0以后的动态获取打电话权限
                        startActivity(mIntent);
                    }
                });
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.show();
                break;
            case R.id.btn_apply:
                break;
        }
    }
}
