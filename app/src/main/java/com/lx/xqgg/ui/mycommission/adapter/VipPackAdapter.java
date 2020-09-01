package com.lx.xqgg.ui.mycommission.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.event.ServiseridurlEvent;
import com.lx.xqgg.ui.mycommission.bean.SystemCommissionlevelBean;
import com.lx.xqgg.util.RoundedCornersTransformation;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VipPackAdapter extends RecyclerView.Adapter<VipPackAdapter.MyHolder> {

   private  List<SystemCommissionlevelBean> data=new ArrayList<>();
   private Context context;

    public VipPackAdapter(List<SystemCommissionlevelBean> data, Context context,int mPosition) {
        this.data = data;
        this.context = context;
        this.mPosition = mPosition;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.vippackitem, parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.vip_moth.setText(data.get(position).getMonth()+"个月");
            holder.vip_name.setText(data.get(position).getName()+"服务包");
            holder.vip_money.setText(data.get(position).getPrice()+"元");
            addpich(holder,position);
     /*   if ("青铜".equals(data.get(position).getName())){
            holder.vippackitem.setBackgroundResource(R.drawable.momeybgh);
            holder.vip_money.setTextColor(Color.parseColor("#000000"));
        }
        else if ("黄金".equals(data.get(position).getName())){
            holder.vippackitem.setBackgroundResource(R.drawable.momeybgh);
            holder.vip_money.setTextColor(Color.parseColor("#8D5D19"));
        }
        else if ("铂金".equals(data.get(position).getName())){
            holder.vippackitem.setBackgroundResource(R.drawable.momeybgb);
            holder.vip_money.setTextColor(Color.parseColor("#606060"));
        }else  if ("钻石".equals(data.get(position).getName())){
            holder.vippackitem.setBackgroundResource(R.drawable.momeybgz);
            holder.vip_money.setTextColor(Color.parseColor("#9C27B0"));
        }*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // 一定要刷新适配器 当条目发生改变这是必须的
                    getListener.onClick(position);
                    ServiseridurlEvent event=new ServiseridurlEvent();
                    event.setId(data.get(position).getId());
                    event.setVipname(data.get(position).getName());
                    event.setImgurl(data.get(position).getPicture());
                    event.setEndTime(data.get(position).getEndTime());
                    event.setRightsNum(data.get(position).getRightsNum());
                    event.setPosition(position);
                    EventBus.getDefault().postSticky(event);
                    notifyDataSetChanged();
                }
            });

            if (position==getmPosition()||position==mPosition){
                addpich(holder,position);
                holder.vip_itembg.setBackgroundResource(R.drawable.bg_bian_vipitem);
            }else {
                /*if ("青铜".equals(data.get(position).getName())){
                    Glide.with(context)
                            .load(R.drawable.momeybgh)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                            .into(holder.vippackitem);
                }else if ("黄金".equals(data.get(position).getName())){
                    Glide.with(context)
                            .load(R.drawable.momeybgh)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                            .into(holder.vippackitem);

                }else if ("铂金".equals(data.get(position).getName())){
                    Glide.with(context)
                            .load(R.drawable.momeybgb)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                            .into(holder.vippackitem);
                }
                else  if ("钻石".equals(data.get(position).getName())){
                    Glide.with(context)
                            .load(R.drawable.momeybgz)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                            .into(holder.vippackitem);
                }*/
                addpich(holder,position);
                holder.vip_itembg.setBackgroundResource(R.drawable.bg_bian_vipitem2);
            }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyHolder extends  RecyclerView.ViewHolder{
        TextView vip_moth;
        TextView vip_name;
        TextView vip_money;
        LinearLayout vip_itembg;
        ImageView vippackitem;
        public MyHolder(View itemView) {
            super(itemView);
            vip_moth = itemView.findViewById(R.id.vip_moth);
            vip_name = itemView.findViewById(R.id.vip_name);
            vip_money = itemView.findViewById(R.id.vip_money);
            vip_itembg = itemView.findViewById(R.id.vip_itembg);
            vippackitem = itemView.findViewById(R.id.vippackitem);
        }
    }
    public interface GetListener {

        void onClick(int position);
    }

    private GetListener getListener;

    public void setGetListener(GetListener getListener) {
        this.getListener = getListener;
    }
    private  int mPosition=-1;

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    private void addpich(MyHolder holder ,int mPosition){
        RoundedCornersTransformation transformation = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.TOP_LEFT);
        //顶部右边圆角
        RoundedCornersTransformation transformation1 = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT);

        //组合各种Transformation,
        MultiTransformation<Bitmap> mation = new MultiTransformation<>
                //Glide设置圆角图片后设置ImageVIew的scanType="centerCrop"无效解决办法,将new CenterCrop()添加至此
                (new CenterCrop(), transformation, transformation1);
        Glide.with(context)
                .load(Config.IMGURL + data.get(mPosition).getPicture())
                .apply(RequestOptions.bitmapTransform(mation))
                .into(holder.vippackitem);
    }
}
