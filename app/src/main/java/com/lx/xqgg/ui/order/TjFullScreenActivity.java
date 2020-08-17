package com.lx.xqgg.ui.order;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.order.adapter.TjAdapter;
import com.lx.xqgg.ui.order.bean.TjBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//统计全屏页面
public class TjFullScreenActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.rv_header)
    RecyclerView rvHeader;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.yxj)
    TextView yxj;
    @BindView(R.id.spj)
    TextView spj;
    @BindView(R.id.ysxj)
    TextView ysxj;
    @BindView(R.id.yxl)
    TextView yxl;
    @BindView(R.id.yxe)
    TextView yxe;

    private TjAdapter tjAllAdapter;
    private List<TjBean> listAll = new ArrayList<>();
    private TjAdapter tjDetailAdapter;
    private List<TjBean> listDetail = new ArrayList<>();
    private String cre;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tj_full;
    }

    @Override
    protected void initView() {
        String cre = SharedPrefManager.getImitationexamination().getPro_cre();
        String pro = SharedPrefManager.getImitationexamination().getPro_pro();
        String yus = SharedPrefManager.getImitationexamination().getPro_yus();
        yxj.setText(cre+ "(件)");
        yxl.setText(cre+ "率(%)");
        yxe.setText(cre+ "额（分）");
        spj.setText(pro+ "中(件");
        ysxj.setText(yus+"(件)");
        listAll = (List<TjBean>) getIntent().getSerializableExtra("list1");
        listDetail = (List<TjBean>) getIntent().getSerializableExtra("list2");
    }

    @Override
    protected void initData() {
        tjAllAdapter = new TjAdapter(R.layout.item_tj_min_with_line, listAll);
        rvHeader.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvHeader.setAdapter(tjAllAdapter);
        rvHeader.setNestedScrollingEnabled(false);

        tjDetailAdapter = new TjAdapter(R.layout.item_tj_min_with_line, listDetail);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvContent.setAdapter(tjDetailAdapter);
    }

    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }


}
