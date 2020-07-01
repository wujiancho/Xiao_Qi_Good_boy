package com.lx.xqgg;

import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.face_ui.home.FaceHomeFragment;
import com.lx.xqgg.face_ui.home.msg.MsgFragment;
import com.lx.xqgg.face_ui.person.FacePersonFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;

public class FaceMainActivity extends BaseActivity implements OnTabSelectListener {
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private FaceHomeFragment faceHomeFragment;
    private MsgFragment msgFragment;
    private FacePersonFragment facePersonFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_face_main;
    }

    @Override
    protected void initView() {
        faceHomeFragment=new FaceHomeFragment();
        msgFragment=new MsgFragment();
        facePersonFragment=new FacePersonFragment();
        bottomBar.setOnTabSelectListener(this);
        loadMultipleRootFragment(R.id.contentContainer, 0,faceHomeFragment,msgFragment,facePersonFragment);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onTabSelected(int tabId) {
        switch (tabId){
            case R.id.tab_home:
                showHideFragment(faceHomeFragment);
                break;
            case R.id.tab_msg:
                showHideFragment(msgFragment);
                break;
            case R.id.tab_person:
                showHideFragment(facePersonFragment);
                break;
        }
    }
}
