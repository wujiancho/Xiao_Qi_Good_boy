package com.lx.xqgg.ui.order;

import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.ui.product.ProductTypeFragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;
//订单搜索框加内层FrameLayout的订单内容 自选产品
public class ChooseProductActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    @BindView(R.id.et_search_content)
    EditText etSearchContent;
    @BindView(R.id.tv_search)
    TextView tvSearch;

    private ProductTypeFragment productTypeFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_product;
    }

    @Override
    protected void initView() {
        productTypeFragment = ProductTypeFragment.newInstance("", "", "", "", "","");
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, productTypeFragment).commitAllowingStateLoss();
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.v_close, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.tv_search:
                productTypeFragment.update(etSearchContent.getText().toString().trim(), "", "", "", "","");
                productTypeFragment.getProductList();
                break;
        }
    }
}
