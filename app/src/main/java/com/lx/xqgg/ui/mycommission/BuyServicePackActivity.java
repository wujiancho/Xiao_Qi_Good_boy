package com.lx.xqgg.ui.mycommission;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

//购买服务包
public class BuyServicePackActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.usercompany_name)
    TextView usercompanyName;
    @BindView(R.id.accumulated_points)
    TextView accumulatedPoints;
    @BindView(R.id.term_of_validity)
    TextView termOfValidity;
    @BindView(R.id.vip_packRecyclerView)
    RecyclerView vipPackRecyclerView;
    @BindView(R.id.jurisdiction_count)
    TextView jurisdictionCount;
    @BindView(R.id.vip_RecyclerViewstatus)
    TextView vipRecyclerViewstatus;
    @BindView(R.id.stuart_img)
    TextView stuartImg;
    @BindView(R.id.vip_RecyclerView)
    RecyclerView vipRecyclerView;
    @BindView(R.id.guizhe1)
    TextView guizhe1;
    @BindView(R.id.guizhe2)
    TextView guizhe2;
    @BindView(R.id.btn_activate_now)
    Button btnActivateNow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_service_pack;
    }

    @Override
    protected void initView() {
        toobarTitle.setText("购买服务包");
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.vip_RecyclerViewstatus, R.id.guizhe1, R.id.guizhe2, R.id.btn_activate_now,R.id.toobar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vip_RecyclerViewstatus:
                break;
            case R.id.guizhe1:
                break;
            case R.id.guizhe2:
                break;
            case R.id.btn_activate_now:
                break;
            case R.id.toobar_back:
                finish();
                break;
        }
    }

    @OnClick()
    public void onViewClicked() {
    }
}
