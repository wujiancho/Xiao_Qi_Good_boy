package com.lx.xqgg.ui.mycommission.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.AccumulatedRebateActivity;
import com.lx.xqgg.ui.mycommission.RefundDetailsActivity;
import com.lx.xqgg.ui.mycommission.bean.HistoryPointsdetailstBean;
import com.lx.xqgg.util.RoundedCornersTransformation;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.Nullable;

public class AccumulatedRebateAdapter extends BaseQuickAdapter<HistoryPointsdetailstBean.DataBean.ListBean, BaseViewHolder> {
    public AccumulatedRebateAdapter(@Nullable List<HistoryPointsdetailstBean.DataBean.ListBean> data) {
        super(R.layout.accumulatedrebateitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryPointsdetailstBean.DataBean.ListBean item) {
        int  productCharge = item.getProductCharge();
        DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
        String productChargez= df.format(productCharge);

       helper.setText(R.id.accumulated_pointscontent,item.getMonth()+" 佣金积分 "+productChargez);
        yuanjiaogrild((ImageView) helper.getView(R.id.accumulated_pointsimg),Config.IMGURL+item.getPicture());
        Glide.with(mContext)
                .load(Config.IMGURL+item.getIco())
                .into((ImageView) helper.getView(R.id.accumulated_pointsimglogo));
        helper.setText(R.id.accumulated_pointsname,item.getChargeName()+"会员");

       helper.addOnClickListener(R.id.integraldetailed);
    }

    //圆角图片
    private void yuanjiaogrild(ImageView v, String url) {
        // 圆角图片 new RoundedCornersTransformation 参数为 ：半径 , 外边距 , 圆角方向(ALL,BOTTOM,TOP,RIGHT,LEFT,BOTTOM_LEFT等等)
        //顶部左边圆角
        RoundedCornersTransformation transformation = new RoundedCornersTransformation
                (20, -10, RoundedCornersTransformation.CornerType.TOP_LEFT);
        //顶部右边圆角
        RoundedCornersTransformation transformation1 = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT);
        //底部左边圆角
        RoundedCornersTransformation transformation2 = new RoundedCornersTransformation
                (20, -10, RoundedCornersTransformation.CornerType.BOTTOM_LEFT);
        //底部右边圆角
        RoundedCornersTransformation transformation3 = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT);
        //组合各种Transformation,
        MultiTransformation<Bitmap> mation = new MultiTransformation<>
                //Glide设置圆角图片后设置ImageVIew的scanType="centerCrop"无效解决办法,将new CenterCrop()添加至此
                (new CenterCrop(), transformation, transformation1, transformation2, transformation3);
        Glide.with(mContext)
                .load(url)
                .apply(RequestOptions.bitmapTransform(mation))
                .into(v);
    }
}
