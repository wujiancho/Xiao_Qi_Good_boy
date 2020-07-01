package com.lx.xqgg.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.ui.home.bean.AdvertBean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PicFragment extends DialogFragment {
    private ImageView imageView;
    private AdvertBean advertBean;

    public PicFragment(AdvertBean advertBean){
        this.advertBean=advertBean;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_pic, null);
        imageView=view.findViewById(R.id.imageView);

        Glide.with(getActivity())
                .load(Config.IMGURL + advertBean.getImage())
                .into(imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 4000);
        return view;
    }
}
