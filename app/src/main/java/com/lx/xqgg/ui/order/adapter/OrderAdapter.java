package com.lx.xqgg.ui.order.adapter;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.order.CreditNowActivity;
import com.lx.xqgg.ui.order.DealActivity;
import com.lx.xqgg.ui.order.FeiDanOrderFragment;
import com.lx.xqgg.ui.order.bean.DealBean;
import com.lx.xqgg.ui.order.bean.OrderBean;
import com.lx.xqgg.ui.vip.bean.NotifyBean;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OrderAdapter extends BaseQuickAdapter<OrderBean.RecordsBean, BaseViewHolder> {
    private DealBean dealBean;
    private OrderBean.RecordsBean orderBean;
    public OrderAdapter(@Nullable List<OrderBean.RecordsBean> data) {
        super(R.layout.item_order_spz, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean.RecordsBean item) {
        helper.setText(R.id.tv_name, item.getTitle() + "");
//        helper.setText(R.id.tv_money_num, (String.format("%.2f", (double) item.getApply_money() / 10000) + "万"));
        helper.setText(R.id.tv_time, item.getCreatetime() + "");
        helper.setText(R.id.tv_order_num, "订单编号：" + item.getOrderNo());
        helper.setText(R.id.tv_company_name, "企业名称：" + item.getApply_company());
        helper.setText(R.id.tv_kh_name, "客户姓名：" + item.getLink_man());
        helper.setText(R.id.tv_phone, "手机号：" + item.getLink_mobile());
        helper.setText(R.id.tv_yg_name, "员工姓名：" + item.getUser_name());

        switch (item.getStatus()) {
            //审核中
            case "created":
                helper.setText(R.id.tv_money_num, (String.format("%.2f", (double) item.getApply_money() / 10000)));
                helper.setVisible(R.id.tv_status, true);
                helper.setText(R.id.tv_status, "审核中");
                helper.setVisible(R.id.btn_deal, true);
                helper.setVisible(R.id.btn_yy_bl, false);
                helper.setVisible(R.id.layout_ysx, false);
                helper.setVisible(R.id.layout_zs, false);
                helper.setVisible(R.id.layout_ljcl, false);
                helper.setOnClickListener(R.id.allitem, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MaterialDialog permissionDialog=new MaterialDialog.Builder(mContext)
                                .title("提示")
                                .content("系统即将删除该笔订单，请确认")
                                .cancelable(false)
                                .positiveText(R.string.yes)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        orderBean=  new  OrderBean.RecordsBean();
                                        HashMap<String, Object> paramsMap = new HashMap<>();
                                        paramsMap.put("id", item.getId());
                                        paramsMap.put("token", SharedPrefManager.getUser().getToken());
                                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),new Gson().toJson(paramsMap));
                                        ApiManage.getInstance().getMainApi().disCardOrder(body)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeWith(new BaseSubscriber<BaseData>(mContext, null) {
                                                    @Override
                                                    public void onNext(BaseData baseData) {
                                                        Log.e("zlz", new Gson().toJson(baseData));
                                                        if (baseData.isSuccess()) {
                                                            toast("提交成功");
                                                            EventBus.getDefault().post(new NotifyBean(0, "提交成功"));
                                                        } else {
                                                            toast(baseData.getMessage());
                                                        }
                                                    }
                                                });

                                    }
                                })
                                .negativeText(R.string.no)
                                .negativeColorRes(R.color.txt_normal)
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .build();
                        permissionDialog.show();
                    }
                });
                break;
            //预授信
            case "precredit":
                if ("0".equals(item.getAppointment())) {
                    helper.setVisible(R.id.btn_yy_bl, true);
                    helper.setOnClickListener(R.id.allitem, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MaterialDialog permissionDialog=new MaterialDialog.Builder(mContext)
                                    .title("提示")
                                    .content("系统即将删除该笔订单，请确认")
                                    .cancelable(false)
                                    .positiveText(R.string.yes)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            orderBean=  new  OrderBean.RecordsBean();
                                            HashMap<String, Object> paramsMap = new HashMap<>();
                                            paramsMap.put("id", item.getId());
                                            paramsMap.put("token", SharedPrefManager.getUser().getToken());
                                            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),new Gson().toJson(paramsMap));
                                            ApiManage.getInstance().getMainApi().disCardOrder(body)
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribeWith(new BaseSubscriber<BaseData>(mContext, null) {
                                                        @Override
                                                        public void onNext(BaseData baseData) {
                                                            Log.e("zlz", new Gson().toJson(baseData));
                                                            if (baseData.isSuccess()) {
                                                                toast("提交成功");
                                                                EventBus.getDefault().post(new NotifyBean(0, "提交成功"));
                                                            } else {
                                                                toast(baseData.getMessage());
                                                            }
                                                        }
                                                    });

                                        }
                                    })
                                    .negativeText(R.string.no)
                                    .negativeColorRes(R.color.txt_normal)
                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .build();
                            permissionDialog.show();

                        }
                    });
                } else {
                    helper.setVisible(R.id.btn_yy_bl, false);
                }

                helper.setVisible(R.id.tv_status, true);
                helper.setText(R.id.tv_status, "预授信");
                if (item.getPre_credit_money() != null) {
                    helper.setText(R.id.tv_money_num,  (String.format("%.2f", Double.parseDouble(item.getPre_credit_money()) / 10000) + " 业务积分"));
                } else {
                    helper.setText(R.id.tv_money_num,  "0.00 业务积分");
                }
                helper.setVisible(R.id.btn_deal, false);
                helper.setVisible(R.id.layout_zs, false);
                helper.setVisible(R.id.layout_ljcl, false);
                if (item.getMeetTime() != null) {
                    helper.setVisible(R.id.layout_ysx, true);
                    helper.setOnClickListener(R.id.layout_ysx, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                            // 创建普通字符型ClipData
                            ClipData mClipData = ClipData.newPlainText("Label", item.getMeetAddress());
                            // 将ClipData内容放到系统剪贴板里。
                            cm.setPrimaryClip(mClipData);
                            toast("约见地点：" + item.getMeetAddress() + "已复制到剪切板");
                        }
                    });
                    helper.setText(R.id.tv_yjsj, item.getMeetTime() + "");
                    helper.setText(R.id.tv_yjdd, item.getMeetAddress() + "");
                } else {
                    helper.setVisible(R.id.layout_ysx, false);
                }
                break;
            //审核中
            case "verify":
                helper.setVisible(R.id.btn_yy_bl, false);
                helper.setVisible(R.id.tv_status, true);
                helper.setText(R.id.tv_status, "审核中");
                if (item.getPre_credit_money() != null) {
                    helper.setText(R.id.tv_money_num,  (String.format("%.2f", Double.parseDouble(item.getPre_credit_money()) / 10000)));
                } else {
                    helper.setText(R.id.tv_money_num,  "0.00");
                }
                helper.setVisible(R.id.layout_zs, true);
                helper.setVisible(R.id.v_sj, false);
                helper.setVisible(R.id.btn_deal, false);
                helper.setVisible(R.id.layout_ysx, false);
                helper.setVisible(R.id.tv_sx_status, false);
//                helper.setText(R.id.tv_sx_status, "终审中");
                helper.setVisible(R.id.tv_result, false);
                helper.setVisible(R.id.layout_ljcl, false);
                helper.setOnClickListener(R.id.allitem, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MaterialDialog permissionDialog=new MaterialDialog.Builder(mContext)
                                .title("提示")
                                .content("系统即将删除该笔订单，请确认")
                                .cancelable(false)
                                .positiveText(R.string.yes)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        orderBean=  new  OrderBean.RecordsBean();
                                        HashMap<String, Object> paramsMap = new HashMap<>();
                                        paramsMap.put("id", item.getId());
                                        paramsMap.put("token", SharedPrefManager.getUser().getToken());
                                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),new Gson().toJson(paramsMap));
                                        ApiManage.getInstance().getMainApi().disCardOrder(body)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeWith(new BaseSubscriber<BaseData>(mContext, null) {
                                                    @Override
                                                    public void onNext(BaseData baseData) {
                                                        Log.e("zlz", new Gson().toJson(baseData));
                                                        if (baseData.isSuccess()) {
                                                            toast("提交成功");
                                                            EventBus.getDefault().post(new NotifyBean(0, "提交成功"));
                                                        } else {
                                                            toast(baseData.getMessage());
                                                        }
                                                    }
                                                });

                                    }
                                })
                                .negativeText(R.string.no)
                                .negativeColorRes(R.color.txt_normal)
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .build();
                        permissionDialog.show();
                    }
                });

                break;
            //终审
            case "nopass":
                helper.setVisible(R.id.btn_yy_bl, false);
                helper.setVisible(R.id.tv_status, true);
                helper.setText(R.id.tv_status, "终审");
                if (item.getCredit_money() != null) {
                    helper.setText(R.id.tv_money_num, (String.format("%.2f", Double.parseDouble(item.getCredit_money()) / 10000) + " 业务积分"));
                } else {
                    helper.setText(R.id.tv_money_num,  "0.00 业务积分");
                }
                helper.setVisible(R.id.layout_zs, true);
                helper.setVisible(R.id.btn_deal, false);
                helper.setVisible(R.id.layout_ysx, false);
                helper.setVisible(R.id.tv_sx_status, true);
                helper.setText(R.id.tv_sx_status, "终审拒绝");
                helper.setVisible(R.id.v_sj, true);
                helper.setVisible(R.id.tv_result, true);
                helper.setVisible(R.id.layout_ljcl, false);
                helper.setText(R.id.tv_result, item.getRej_reason() + "");
                helper.setOnClickListener(R.id.allitem, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MaterialDialog permissionDialog=new MaterialDialog.Builder(mContext)
                                .title("提示")
                                .content("系统即将删除该笔订单，请确认")
                                .cancelable(false)
                                .positiveText(R.string.yes)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        orderBean=  new  OrderBean.RecordsBean();
                                        HashMap<String, Object> paramsMap = new HashMap<>();
                                        paramsMap.put("id", item.getId());
                                        paramsMap.put("token", SharedPrefManager.getUser().getToken());
                                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),new Gson().toJson(paramsMap));
                                        ApiManage.getInstance().getMainApi().disCardOrder(body)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeWith(new BaseSubscriber<BaseData>(mContext, null) {
                                                    @Override
                                                    public void onNext(BaseData baseData) {
                                                        Log.e("zlz", new Gson().toJson(baseData));
                                                        if (baseData.isSuccess()) {
                                                            toast("提交成功");
                                                            EventBus.getDefault().post(new NotifyBean(0, "提交成功"));
                                                        } else {
                                                            toast(baseData.getMessage());
                                                        }
                                                    }
                                                });

                                    }
                                })
                                .negativeText(R.string.no)
                                .negativeColorRes(R.color.txt_normal)
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .build();
                        permissionDialog.show();
                    }
                });    helper.setOnClickListener(R.id.tv_result, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder1=new AlertDialog.Builder(mContext);
                        builder1.setMessage(item.getRej_reason() + "");
                        builder1.show();
                    }
                });
                break;
            //用信
            case "usecredit":
                helper.setVisible(R.id.btn_yy_bl, false);
                helper.setVisible(R.id.tv_status, true);
                helper.setText(R.id.tv_status, "用信");
                if (item.getReal_money() != null) {
                    helper.setText(R.id.tv_money_num, (String.format("%.2f", Double.parseDouble(item.getReal_money()) / 10000) + "业务积分"));
                } else {
                    helper.setText(R.id.tv_money_num,  "0.00 业务积分");
                }
                helper.setVisible(R.id.layout_zs, true);
                helper.setVisible(R.id.v_sj, false);
                helper.setVisible(R.id.btn_deal, false);
                helper.setVisible(R.id.layout_ysx, false);

                helper.setVisible(R.id.tv_sx_status, false);
//                helper.setText(R.id.tv_sx_status, "用信");
                helper.setVisible(R.id.tv_result, true);
                helper.setVisible(R.id.layout_ljcl, false);
                if ("1".equals(item.getCreditAudit())) {
                    helper.setText(R.id.tv_result, (String.format("%.2f", (Double.valueOf(item.getCredit_money()) / 10000)) + " 业务积分"));
                } else {
                    helper.setText(R.id.tv_result, "用信金额审核中");
                }
                break;
            //终审通过
            case "pass":
                helper.setVisible(R.id.btn_yy_bl, false);
                helper.setText(R.id.tv_status, "终审");
//                helper.setVisible(R.id.tv_status,false);
                if (item.getReal_money() != null) {
                    helper.setText(R.id.tv_money_num, (String.format("%.2f", Double.parseDouble(item.getReal_money()) / 10000) + " 业务积分"));
                } else {
                    helper.setText(R.id.tv_money_num, "0.00 业务积分");
                }
                helper.setVisible(R.id.layout_zs, false);
                helper.setVisible(R.id.btn_deal, false);
                helper.setVisible(R.id.layout_ysx, false);
//                helper.setText(R.id.tv_sx_status, "终审通过");
//                helper.setVisible(R.id.tv_result, true);
                helper.setVisible(R.id.layout_ljcl, true);
//                helper.setText(R.id.tv_result,item.get)
                helper.setOnClickListener(R.id.allitem, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MaterialDialog permissionDialog=new MaterialDialog.Builder(mContext)
                                .title("提示")
                                .content("确认该订单已谈妥，提交至银行处")
                                .cancelable(false)
                                .positiveText(R.string.yes)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        DealBean dealBean=new DealBean();
                                        dealBean.setId(item.getId()+"");
                                        dealBean.setAppointment("1");

                                        Gson gson = new Gson();
                                        String obj = gson.toJson(dealBean);
                                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj);
                                        ApiManage.getInstance().getMainApi().updateOrder(body)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeWith(new BaseSubscriber<BaseData<Object>>(mContext, null) {
                                                    @Override
                                                    public void onNext(BaseData<Object> objectBaseData) {
                                                        if (objectBaseData.isSuccess()) {
                                                            toast("提交成功");
                                                            EventBus.getDefault().post(new NotifyBean(0, "提交成功"));
                                                            dialog.dismiss();
                                                        } else {
                                                            toast(objectBaseData.getMessage());
                                                        }
                                                    }
                                                });

                                    }
                                })
                                .negativeText(R.string.no)
                                .negativeColorRes(R.color.txt_normal)
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .build();
                        permissionDialog.show();
                    }
                });
                break;
        }
    }

    public void toast(String str) {
        Toast toast = Toast.makeText(mContext, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
