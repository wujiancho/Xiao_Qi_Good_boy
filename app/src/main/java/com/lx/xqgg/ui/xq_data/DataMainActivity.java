package com.lx.xqgg.ui.xq_data;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

public class DataMainActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.imageView4)
    ImageView imageView4;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_main;
    }

    @Override
    protected void initView() {
        tvTitle.setText("小麒数据");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.v_close, R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.imageView1:
                break;
            case R.id.imageView2:
                break;
            case R.id.imageView3:
                break;
            case R.id.imageView4:
                break;
        }
    }
}
