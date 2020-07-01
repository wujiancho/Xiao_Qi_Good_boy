package com.lx.xqgg.face_ui.home.msg;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseFragment;
import com.lx.xqgg.face_ui.home.msg.dapter.FaceMsgAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class MsgFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<String> list=new ArrayList<>();
    private FaceMsgAdapter msgAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_face_msg;
    }

    @Override
    protected void initView() {
        list.add("您好，欢迎使用鑫恒绅企业服务（无锡）有限公司旗下小麒乖乖APP，我们为您提供一站式企业服务，包括企业法律咨询、软件开发、财税策划及高端企业培训等等。");
        list.add("您好，小麒乖乖的法律咨询服务是帮助企业客户寻找律师的互联网法律服务平台，致力于让客户用最合适的费用请到最合适的律师，让律师更专业，让客户更放心。");
        list.add("您好，小麒乖乖是互联网技术服务的专业公司，我们的软件开发服务是致力于为用户提供易用，美观，创新的高价值互联网产品。");
        list.add("您好，小麒乖乖的企业培训服务为企业提供层次高、见效快的系统管理培训，搭配小房间会议、实战团队指导等多种形式培训服务。");
        list.add("您好，小麒乖乖的财税策划是在法律规定许可的范围内，通过对经营、投资、理财活动的事先筹划和安排，尽可能帮助企业客户取得节税的经济利益");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        msgAdapter=new FaceMsgAdapter(list);
        recyclerView.setAdapter(msgAdapter);
    }

    @Override
    protected void initData() {

    }
}
