package com.lx.xqgg.ui.home;

import android.annotation.SuppressLint;

import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.ui.company_auth.CompanyAuthActivity;
import com.lx.xqgg.ui.home.bean.UserServiceBean;
import com.lx.xqgg.ui.vip.VipActivity;

import androidx.annotation.Nullable;

@SuppressLint("ValidFragment")
public class UserServiceFragment extends DialogFragment {
    private TextView tvMsg;
    private View vClose;
    private Button button;
    private int code;

    private BaseData<UserServiceBean> beanBaseData;

    public UserServiceFragment(BaseData<UserServiceBean> beanBaseData) {
        this.beanBaseData = beanBaseData;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_user_service, null);
        tvMsg = view.findViewById(R.id.tv_msg);
        vClose = view.findViewById(R.id.v_close);
        button = view.findViewById(R.id.btn_check);
        code = beanBaseData.getCode();
        switch (code) {
            case 0:
                //不是服务商
                button.setText("前往认证");
                break;
            case -1:
                //正在审核
                button.setText("前往认证");
                break;
            case -2:
                //审核被拒
                button.setText("前往认证");
                break;
            case -3:
                //vip时间到期
                button.setText("前往充值");
                break;
            case -4:
                //不是vip
                button.setText("前往充值");
                break;
            case -5:
                //未登录
                button.setText("前往登录");
                break;
            case -6:
                //未登录
                button.setText("联系客服");
                break;
        }
        vClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvMsg.setText(beanBaseData.getMessage() + "");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (code) {
                    case 0:
                        //不是服务商
                        intent = new Intent(getActivity(), CompanyAuthActivity.class);
                        startActivity(intent);
                        break;
                    case -1:
                        //正在审核
                        intent = new Intent(getActivity(), CompanyAuthActivity.class);
                        startActivity(intent);
                        break;
                    case -2:
                        //审核被拒
                        intent = new Intent(getActivity(), CompanyAuthActivity.class);
                        startActivity(intent);
                        break;
                    case -3:
                        //vip时间到期
                        intent = new Intent(getActivity(), VipActivity.class);
                        startActivity(intent);
                        break;
                    case -4:
                        //不是vip
                        intent = new Intent(getActivity(), VipActivity.class);
                        startActivity(intent);
                        break;
                    case -5:
                        //未登录

                        break;
                    case -6:
                        //未找到服务商
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setMessage("是否拨打客服电话：4001391717");
                        builder1.setTitle("温馨提示");
                        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent mIntent = new Intent(Intent.ACTION_DIAL);
                                Uri data = Uri.parse("tel:4001391717");
                                mIntent.setData(data);
                                //Android6.0以后的动态获取打电话权限
                                startActivity(mIntent);
                            }
                        });
                        builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder1.show();
                        break;
                }
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
