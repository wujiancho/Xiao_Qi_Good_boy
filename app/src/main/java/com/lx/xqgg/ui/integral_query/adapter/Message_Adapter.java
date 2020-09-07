package com.lx.xqgg.ui.integral_query.adapter;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import java.util.List;
import androidx.annotation.Nullable;
public class Message_Adapter extends BaseQuickAdapter<String , BaseViewHolder> {
    public static final int typeji = 0;
    public static final int typeo = 1;
    public Message_Adapter( @Nullable List<String> data) {
        super(R.layout.messageitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.messagecoument,item);
       switch (helper.getItemViewType()){
           case typeji:
               helper.setBackgroundColor(R.id.itembg,Color.parseColor("#FFFFFF"));
               break;
           case typeo:
               helper.setBackgroundColor(R.id.itembg,Color.parseColor("#F0F0F0"));

               break;
       }
    }
    @Override
    public int getItemViewType(int position) {
        if (position%2==0){
            return typeji;
        }
        return  typeo;
    }
}
