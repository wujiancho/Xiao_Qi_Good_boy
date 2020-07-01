package com.lx.xqgg.ui.match.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.ui.match.bean.MatchRequestBean;
import com.lx.xqgg.ui.match.bean.MatchResultBean;
import com.lx.xqgg.ui.match.bean.MatchSavedBean;
import com.lx.xqgg.ui.product.ProductDetailActivity;
import com.lx.xqgg.ui.product.bean.ProductBean;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class MatchResultAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private MatchRequestBean matchRequestBean;
    private boolean isShow=false;

    public MatchResultAdapter(@Nullable List<MultiItemEntity> data,boolean isShow) {
//        super(R.layout.item_match_result, data);
        super(data);
        addItemType(0, R.layout.item_match_type);
        addItemType(1, R.layout.item_match_product);
        this.matchRequestBean = matchRequestBean;
        this.isShow=isShow;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
//        String[] titles=item.getTitle().split("-");
//        helper.setText(R.id.tv_bank_name, titles[0].trim()+ "");
//        if(titles.length>1) {
//            helper.setText(R.id.textView2, titles[1].trim() + "");
//        }else {
//            helper.setText(R.id.textView2, "");
//        }
//        helper.setText(R.id.tv_ed, item.getQuota()/10000 + "万");
//        helper.setText(R.id.tv_rlv, item.getRate() + "");
//        Glide.with(mContext)
//                .load(Config.IMGURL + item.getImage())
//                .apply(new RequestOptions().placeholder(R.drawable.ic_default))
//                .into((ImageView) helper.getView(R.id.image_view));
        switch (item.getItemType()) {
            case 0:
                final MatchResultBean oneBean = (MatchResultBean) item;
                helper.setText(R.id.tv_type, oneBean.getCateName());
                break;
            case 1:
                final MatchResultBean.ProductBean twoBean = (MatchResultBean.ProductBean) item;

                TextView textView=helper.getView(R.id.tv_num);
                Log.e("zlz",textView.getTextSize()+"");
                float size=textView.getTextSize();

                switch (twoBean.getSortIndex()){
                    case 1:
                        size=24;
                        break;
                    case 2:
                        size=22;
                        break;
                    case 3:
                        size=20;
                        break;
                    case 4:
                        size=18;
                        break;
                    default:
                        size=16;
                        break;
                }
                textView.setTextSize(size);
                helper.setText(R.id.tv_num, twoBean.getSortIndex() + "");
                if(twoBean.getTitle()!=null) {
                    String[] titles = twoBean.getTitle().split("-");
                    helper.setText(R.id.tv_bank_name, titles[0].trim() + "");
                    if (titles.length > 1) {
                        helper.setText(R.id.textView2, titles[1].trim() + "");
                    } else {
                        helper.setText(R.id.textView2, "");
                    }
                }
                helper.setText(R.id.tv_ed, twoBean.getQuota()/10000 + "万");
                helper.setText(R.id.tv_rlv, twoBean.getRate() + "");
                Glide.with(mContext)
                        .load(Config.IMGURL + twoBean.getImage())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into((ImageView) helper.getView(R.id.image_view));
                if(twoBean.getGuarantee()!=null) {
                    String[] hk = twoBean.getGuarantee().replace("xi", "等额本息")
                            .replace("ben", "先息后本")
                            .replace("jin", "等额本金")
                            .split(",");
                    ArrayAdapter arrayAdapter = new ArrayAdapter<String>(mContext, R.layout.item_text, R.id.tv_info, hk);
                    ListView listView = helper.getView(R.id.list_view);
                    listView.setAdapter(arrayAdapter);
                }
                if(isShow) {
                    String status="";
                    if(twoBean.getOrderStatus()==null){
                        status="未申请";
                    }else {
                        switch (twoBean.getOrderStatus()) {
                            case "created":
                                status="审核中";
                                break;
                            case "precredit":
                                status="预授信";
                                break;
                            case "verify":
                                status="审核中";
                                break;
                            case "nopass":
                                status="终审拒绝";
                                break;
                            case "usecredit":
                                status="用信";
                                break;
                            case "pass":
                                status="终审";
                                break;
                        }
                    }
                    helper.setText(R.id.tv_status,status);
                    helper.setOnClickListener(R.id.layout, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if("offline".equals(twoBean.getStatus())){
                                Toast toast=Toast.makeText(mContext, "该产品未上架，请联系客服线下办理", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            }
                            Intent intent = new Intent(mContext, ProductDetailActivity.class);
                            intent.putExtra("data", twoBean.getId());
                            mContext.startActivity(intent);
                        }
                    });
                }
                if("offline".equals(twoBean.getStatus())){
                    Glide.with(mContext)
                            .load(R.drawable.flag_outline)
                            .into((ImageView) helper.getView(R.id.iv_flag));
                }
                break;
        }
    }
}
