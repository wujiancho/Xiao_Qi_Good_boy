package com.lx.xqgg.ui.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.et_feedback_msg)
    EditText etFeedbackMsg;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("意见反馈");
    }

    @OnClick({R.id.v_close, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_commit:
                if(TextUtils.isEmpty(etFeedbackMsg.getText().toString().trim())){
                    toast("请输入反馈内容");
                    return;
                }
                toast("提交成功");
                finish();
                break;
        }
    }
}
