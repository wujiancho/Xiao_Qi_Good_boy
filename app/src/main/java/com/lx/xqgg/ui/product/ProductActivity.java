package com.lx.xqgg.ui.product;

import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.Constans;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

public class ProductActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.framelayout)
    FrameLayout framelayout;

    private int finalPostion = -1;
    private ProductFragment productActivity;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_product;
    }

    @Override
    protected void initView() {
        tvTitle.setText("产品库");
        finalPostion = getIntent().getIntExtra("postion", -1);
        productActivity = new ProductFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, productActivity).commitAllowingStateLoss();

    }


    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    productActivity.setItemSelected(finalPostion);
                }catch (Exception e){

                }
            }
        }, 500);
    }

    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }
}
