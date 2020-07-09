package com.lx.xqgg.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.ui.home.bean.AdvertBean;
import com.lx.xqgg.ui.webview.WebViewActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;

public class PicFragment extends DialogFragment {

    private ImageView imageView;
    private ImageView guan;
    private AdvertBean advertBean;

    public PicFragment(AdvertBean advertBean) {
        this.advertBean = advertBean;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_pic, null);
        imageView = view.findViewById(R.id.imageView);
        guan = view.findViewById(R.id.guan);

        Glide.with(getActivity())
                .load(Config.IMGURL + advertBean.getImage())
                .into(imageView);
        Log.d("guangao", "onCreateView: "+Config.IMGURL + advertBean.getImage());
      guan.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              dismiss();
          }
      });
      imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              WebViewActivity.open(new WebViewActivity.Builder()
                      .setContext(getContext())
                      .setAutoTitle(false)
                      .setIsFwb(false)
                      .setUrl(advertBean.getUrl()));
              return;
          }
      });
        return view;
    }
}
