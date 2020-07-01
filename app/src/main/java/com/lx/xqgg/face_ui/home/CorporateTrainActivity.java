package com.lx.xqgg.face_ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.face_ui.home.adapter.FaceProductAdapter;
import com.lx.xqgg.face_ui.home.bean.FaceProductBean;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 企业培训
 */
public class CorporateTrainActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.iv_xslb)
    ImageView ivXslb;
    @BindView(R.id.iv_zxkf)
    ImageView ivZxkf;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    private List<FaceProductBean> list = new ArrayList<>();
    private FaceProductAdapter faceProductAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_corporate_train;
    }

    @Override
    protected void initView() {
        tvTitle.setText("企业培训");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        faceProductAdapter = new FaceProductAdapter(list);
        recyclerView.setAdapter(faceProductAdapter);
    }

    @Override
    protected void initData() {
        initProduct();
    }

    /**
     * 获取表层产品
     */
    private void initProduct() {
        addSubscribe(ApiManage.getInstance().getMainApi().getFaceProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<FaceProductBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<FaceProductBean>> listBaseData) {
                        if (listBaseData.isSuccess()) {
                            if (listBaseData.getData() != null) {
                                list.clear();
                                list.addAll(listBaseData.getData());
                                faceProductAdapter.notifyDataSetChanged();
                                faceProductAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent=new Intent(mContext,BannerListActivity.class);
                                        intent.putExtra("data",list.get(position));
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }
                }));
    }


    @OnClick({R.id.v_close, R.id.iv_xslb, R.id.iv_zxkf,R.id.layout_constrain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.iv_xslb:
                startActivity(new Intent(mContext,BeginnerGiftActivity.class));
                break;
            case R.id.iv_zxkf:
                String title = "聊天窗口的标题";
                ConsultSource source = new ConsultSource("", title, "custom information string");
                Unicorn.openServiceActivity(mContext, title, source);
                break;
            case R.id.layout_constrain:
                startActivity(new Intent(mContext,GaoActivity.class));
                break;
        }
    }
}
