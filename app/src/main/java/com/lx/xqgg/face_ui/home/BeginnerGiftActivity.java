package com.lx.xqgg.face_ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

public class BeginnerGiftActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.btn_draw)
    Button btnDraw;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_beginner_gift;
    }

    @Override
    protected void initView() {
        tvTitle.setText("新手礼包");
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.v_close, R.id.btn_draw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_draw:
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
        }
    }
}
