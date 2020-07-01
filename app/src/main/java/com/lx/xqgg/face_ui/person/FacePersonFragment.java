package com.lx.xqgg.face_ui.person;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseFragment;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.company_auth.CompanyAuthActivity;
import com.lx.xqgg.ui.home.bean.UserServiceBean;
import com.lx.xqgg.ui.person.ServiceInfoActivity;
import com.lx.xqgg.ui.setting.SettingActivity;
import com.lx.xqgg.ui.share.ShareFaceActivity;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FacePersonFragment extends BaseFragment {
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.v_qr_code)
    View vQrCode;
    @BindView(R.id.tv_setting)
    LinearLayout tvSetting;
    @BindView(R.id.layout_hyrz)
    LinearLayout layoutHyrz;
    @BindView(R.id.layout_hycz)
    LinearLayout layoutHycz;
    @BindView(R.id.layout_lxkf)
    LinearLayout layoutLxkf;
    @BindView(R.id.layout_setting)
    LinearLayout layoutSetting;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_person_face;
    }

    @Override
    protected void initView() {
        tvPhone.setText(SharedPrefManager.getUser().getMobile());
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.layout_hyrz, R.id.layout_hycz, R.id.layout_lxkf, R.id.layout_setting,R.id.v_qr_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_hyrz:

                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("token", SharedPrefManager.getUser().getToken());
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                addSubscribe(ApiManage.getInstance().getMainApi().getUserServiceInfo(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseSubscriber<BaseData<UserServiceBean>>(mContext, null) {
                            @Override
                            public void onNext(BaseData<UserServiceBean> objectBaseData) {
                                Log.e("zlz", new Gson().toJson(objectBaseData));
                                Intent intent;
                                if (objectBaseData.isSuccess()) {
                                    switch (objectBaseData.getCode()) {
                                        case 1:
                                            //服务商认证通过
                                            intent = new Intent(getActivity(), ServiceInfoActivity.class);
                                            startActivity(intent);
                                            break;
                                        case 0:
                                            //不是服务商
                                            toast(objectBaseData.getMessage()+"");
                                            intent = new Intent(getActivity(), CompanyAuthActivity.class);
                                            startActivity(intent);
                                            break;
                                        case -1:
                                            //正在审核
                                            toast(objectBaseData.getMessage()+"");
//                                            intent = new Intent(getActivity(), ServiceInfoActivity.class);
//                                            startActivity(intent);
                                            break;
                                        case -2:
                                            //审核被拒
                                            toast(objectBaseData.getMessage()+"");
                                            intent = new Intent(getActivity(), CompanyAuthActivity.class);
                                            startActivity(intent);
                                            break;
                                        case -3:
                                            //vip时间到期
                                            toast(objectBaseData.getMessage()+"");
                                            intent = new Intent(getActivity(), ServiceInfoActivity.class);
                                            startActivity(intent);
                                            break;
                                        case -4:
                                            //不是vip
                                            toast(objectBaseData.getMessage()+"");
                                            intent = new Intent(getActivity(), ServiceInfoActivity.class);
                                            startActivity(intent);
                                            break;
                                        case -5:
                                            //未登录
                                            toast(objectBaseData.getMessage()+"");
                                            break;
                                        case -6:
                                            //未找到服务商
                                            toast(objectBaseData.getMessage()+"");
                                            break;
                                    }
                                } else {
                                    toast(objectBaseData.getMessage());
                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                super.onError(t);
                                toast(t.toString());
                            }
                        }));


//                startActivity(new Intent(mContext, CompanyAuthActivity.class));
                break;
            case R.id.layout_hycz:
                break;
            case R.id.layout_lxkf:
                String title = "聊天窗口的标题";
                ConsultSource source = new ConsultSource("", title, "custom information string");
                Unicorn.openServiceActivity(mContext, title, source);
                break;
            case R.id.layout_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.v_qr_code:
                startActivity(new Intent(mContext, ShareFaceActivity.class));
                break;
        }
    }
}
