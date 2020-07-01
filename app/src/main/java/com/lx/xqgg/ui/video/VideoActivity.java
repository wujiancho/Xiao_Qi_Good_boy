package com.lx.xqgg.ui.video;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoActivity extends BaseActivity {
    @BindView(R.id.videoplayer)
    JZVideoPlayerStandard videoplayer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void initView() {

        JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        jzVideoPlayerStandard.setUp("http://192.168.1.106:3000/video/?url=http://xq.xhsqy.com/xiaoqiguaiguai-mobile/common/image?fileId=meirigaojian2.mp4",
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                "饺子闭眼睛");
    }

    @Override
    protected void initData() {

    }


}
