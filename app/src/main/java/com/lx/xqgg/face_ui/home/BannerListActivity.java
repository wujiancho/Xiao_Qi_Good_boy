package com.lx.xqgg.face_ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.face_ui.home.adapter.ProductDetailAdapter;
import com.lx.xqgg.face_ui.home.bean.FaceProductBean;
import com.lx.xqgg.ui.webview.WebViewActivity;

import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class BannerListActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ProductDetailAdapter productDetailAdapter;
    private List<FaceProductBean.ProductBean> list;
    private FaceProductBean faceProductBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banner_list;
    }

    @Override
    protected void initView() {
        faceProductBean = (FaceProductBean) getIntent().getSerializableExtra("data");
        tvTitle.setText(faceProductBean.getTitle());
        list = faceProductBean.getProduct();
        switch (faceProductBean.getTitle()) {
            case "百问百答系列":
                Glide.with(mContext)
                        .load(R.drawable.pic_bwbd)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into(imageView);
                break;
            case "每日高见系列":
                Glide.with(mContext)
                        .load(R.drawable.pic_mrgj)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into(imageView);
                break;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        productDetailAdapter = new ProductDetailAdapter(list);
        recyclerView.setAdapter(productDetailAdapter);
        productDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WebViewActivity.open(new WebViewActivity.Builder()
                        .setContext(mContext)
                        .setAutoTitle(false)
                        .setIsFwb(false)
                        .setNeedShare(true)
                        .setTitle(list.get(position).getSeriesTitle())
                        .setUrl(Config.URL+list.get(position).getSeriesVideo()));
            }
        });
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }
}
