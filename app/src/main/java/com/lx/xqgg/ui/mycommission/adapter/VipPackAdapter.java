package com.lx.xqgg.ui.mycommission.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.event.ServiseridurlEvent;
import com.lx.xqgg.ui.mycommission.bean.SystemCommissionlevelBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class VipPackAdapter extends RecyclerView.Adapter<VipPackAdapter.MyHolder> {

   private  List<SystemCommissionlevelBean> data=new ArrayList<>();
   private Context context;

    public VipPackAdapter(List<SystemCommissionlevelBean> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.vippackitem, null);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.vip_moth.setText(data.get(position).getMonth()+"个月");
            holder.vip_name.setText(data.get(position).getName()+"服务包");
            holder.vip_money.setText(data.get(position).getPrice()+"元");
        if ("青铜".equals(data.get(position).getName())){
            holder.vippackitem.setBackgroundResource(R.drawable.momeybgh);
            holder.vip_money.setTextColor(Color.parseColor("#8D5D19"));
        }else if ("钻石".equals(data.get(position).getName())){
            holder.vippackitem.setBackgroundResource(R.drawable.momeybgb);
            holder.vip_money.setTextColor(Color.parseColor("#606060"));
        }else  if ("王者".equals(data.get(position).getName())){
            holder.vippackitem.setBackgroundResource(R.drawable.momeybgz);
            holder.vip_money.setTextColor(Color.parseColor("#8410BA"));
        }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // 一定要刷新适配器 当条目发生改变这是必须的
                    getListener.onClick(position);
                    ServiseridurlEvent event=new ServiseridurlEvent(data.get(position).getId(),data.get(position).getPicture(),data.get(position).getName());
                    EventBus.getDefault().postSticky(event);
                    notifyDataSetChanged();
                }
            });

            if (position==getmPosition()){
                Glide.with(context)
                        .load(Config.IMGURL + data.get(position).getPicture())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                        .into(holder.vippackitem);
                holder.vip_itembg.setBackgroundResource(R.drawable.bg_bian_vipitem);
            }else {
                if ("青铜".equals(data.get(position).getName())){
                    Glide.with(context)
                            .load(R.drawable.momeybgh)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                            .into(holder.vippackitem);
                }else if ("王者".equals(data.get(position).getName())){
                    Glide.with(context)
                            .load(R.drawable.momeybgb)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                            .into(holder.vippackitem);
                }else  if ("钻石".equals(data.get(position).getName())){
                    Glide.with(context)
                            .load(R.drawable.momeybgz)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                            .into(holder.vippackitem);
                }
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
}
