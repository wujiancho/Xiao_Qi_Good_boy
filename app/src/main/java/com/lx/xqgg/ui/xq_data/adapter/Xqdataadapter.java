package com.lx.xqgg.ui.xq_data.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.adapter.VipPackAdapter;
import com.lx.xqgg.ui.mytuoke.bean.XqdataBean;
import com.lx.xqgg.ui.webview.WebViewActivity;
import com.lx.xqgg.util.Base64;
import com.lx.xqgg.util.RoundedCornersTransformation;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Xqdataadapter extends BaseQuickAdapter<XqdataBean, BaseViewHolder> {
    public Xqdataadapter( @Nullable List<XqdataBean> data) {
        super(R.layout.xqdataitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, XqdataBean item) {
        helper.setText(R.id.xqdataname,item.getName().substring(item.getName().indexOf("-")+1,item.getName().length()));
        RoundedCornersTransformation transformation = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.TOP_LEFT);
        //顶部右边圆角
        RoundedCornersTransformation transformation1 = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT);

        //顶部右边圆角
        RoundedCornersTransformation transformation2 = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT);
        //顶部右边圆角
        RoundedCornersTransformation transformation3 = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT);
        //组合各种Transformation,
        MultiTransformation<Bitmap> mation = new MultiTransformation<>
                //Glide设置圆角图片后设置ImageVIew的scanType="centerCrop"无效解决办法,将new CenterCrop()添加至此
                (new CenterCrop(), transformation, transformation1,transformation2,transformation3);
        Glide.with(mContext)
                .load(Config.IMGURL+item.getLogo())
                .apply(RequestOptions.bitmapTransform(mation))
                .into((ImageView) helper.getView(R.id.xqdatalogo));
      helper.setOnClickListener(R.id.xqdataxq, new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              HashMap<String, Object> paramsMap = new HashMap<>();
              paramsMap.put("productId",item.getId());
              paramsMap.put("proName", item.getName());
              paramsMap.put("logo", item.getLogo());
              paramsMap.put("statusHeight","30");
              //RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),);

              //加密json
              String jiajson = Base64.encode( new Gson().toJson(paramsMap).getBytes());

              String jiekong = Config.URL + "view/productDaily.html?bean=" + jiajson;

              Log.d("cpdd", "onViewClicked: "+jiajson);
              Log.d("cpdd", "onViewClicked: "+jiekong);
                  WebViewActivity.open(new WebViewActivity.Builder()
                          .setContext(mContext)
                          .setAutoTitle(false)
                          .setIsFwb(false)
                          .setNeedShare(false)
                          .setUrl(jiekong),false);
          }
      });
    }

}
