package com.lx.xqgg.ui.qcc.fragment;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseFragment;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.ui.qcc.adapter.PermissionEciInfoAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class PermissionEciInfoFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private PermissionEciInfoAdapter permissionEciInfoAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void initView() {
        permissionEciInfoAdapter=new PermissionEciInfoAdapter(Constans.qccBean.getResult().getPenalty());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(permissionEciInfoAdapter);
        permissionEciInfoAdapter.setEmptyView(R.layout.layout_empty,recyclerView);
    }

    @Override
    protected void initData() {

    }
}
