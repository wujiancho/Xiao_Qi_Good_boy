package com.lx.xqgg.ui.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.city.bean.CityBean;
import com.lx.xqgg.ui.city.bean.CityHistoryBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CitiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int HEAD = 0;
    private final int WORD = 1;
    private final int CITY = 2;
    List<CityBean> cityBeanList;
    private onCityPickListener onCityPickListener;
    private TopViewHolder topViewHolder;
    private Context mContext;

    public CitiesAdapter(List<CityBean> cityBeanList, Context context) {
        this.cityBeanList = cityBeanList;
        this.mContext = context;
    }

    public void setOnCityPickListener(onCityPickListener onCityPickListener) {
        this.onCityPickListener = onCityPickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEAD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_head, parent, false);
            return new TopViewHolder(view);
        } else if (viewType == WORD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
            return new WordViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_name, parent, false);
            return new CityViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

//        int count = -1;
        if (position == 0) {
            topViewHolder = (TopViewHolder) holder;
            initTopViewHolder(topViewHolder);
        } else {
            int count = 0;
            for (int i = 0; i < cityBeanList.size(); i++) {
                count += 1;
                if (position == count) {
                    WordViewHolder wordViewHolder = (WordViewHolder) holder;
                    wordViewHolder.textWord.setText(cityBeanList.get(i).getFisrt());
                } else {
                    List<CityBean.ListCityBean> addressList = cityBeanList.get(i).getListCity();
                    for (int j = 0; j < addressList.size(); j++) {
                        count += 1;
                        if (position == count) {
                            final CityBean.ListCityBean addressListBean = addressList.get(j);
                            CityViewHolder schoolViewHolder = (CityViewHolder) holder;
                            schoolViewHolder.textCity.setText(addressListBean.getName());
                            schoolViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String province = "";
                                    String[] nams = addressListBean.getMergename().split(",");
                                    if (null != nams || nams.length > 2) {
                                        province = nams[1];
                                    }
                                    onCityPickListener.select(province, addressListBean.getName());
                                }
                            });
                        }
                    }
                }
            }
        }

    }

    public interface onCityPickListener {
        void select(String province, String city);
    }

    @Override
    public int getItemCount() {
//        int childCount = cityBeanList.size();//字母的数量
//        for (int i = 0; i < cityBeanList.size(); i++) {
//            childCount += cityBeanList.get(i).getListCity().size();//地区的数量
//        }
//        return childCount;

        if (cityBeanList == null) return 1;//用于显示头部搜索、定位地区、热门地区
        int childCount = cityBeanList.size();//字母的数量
        for (int i = 0; i < cityBeanList.size(); i++) {
            childCount += cityBeanList.get(i).getListCity().size();//地区的数量
        }
        return childCount + 1;

    }

    @Override
    public int getItemViewType(int position) {
        int count = 0;
        if (position == count) {
            return HEAD;
        }
        for (int i = 0; i < cityBeanList.size(); i++) {
            count++;
            if (position == count) {
                return WORD;
            }
            List<CityBean.ListCityBean> addressList = cityBeanList.get(i).getListCity();
            for (int j = 0; j < addressList.size(); j++) {
                count++;
                if (position == count) {
                    return CITY;
                }
            }
        }
        return super.getItemViewType(position);
    }

    private void initTopViewHolder(TopViewHolder topViewHolder) {
        topViewHolder.tvLocation.setText(Constans.GPSCITY);
        topViewHolder.tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCityPickListener.select(Constans.GPSPROVINCE,Constans.GPSCITY);
            }
        });
        List<CityHistoryBean> list = SharedPrefManager.getCityHistory();
        HistoryAdapter historyAdapter = new HistoryAdapter(list);
        topViewHolder.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        topViewHolder.recyclerView.setAdapter(historyAdapter);
        historyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CityHistoryBean cityHistoryBean=list.get(position);
                onCityPickListener.select(cityHistoryBean.getProvince(),cityHistoryBean.getCity());
            }
        });
    }

    public class HistoryAdapter extends BaseQuickAdapter<CityHistoryBean, BaseViewHolder> {


        public HistoryAdapter(@Nullable List<CityHistoryBean> data) {
            super(R.layout.item_city, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CityHistoryBean item) {
            helper.setText(R.id.tv_name,item.getCity());
        }
    }

    public static class TopViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocation;
        RecyclerView recyclerView;

        public TopViewHolder(View view) {
            super(view);
            tvLocation = view.findViewById(R.id.tv_gps_location);
            recyclerView = view.findViewById(R.id.recycler_view);
        }
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {
        TextView textWord;

        public WordViewHolder(View view) {
            super(view);
            textWord = (TextView) view.findViewById(R.id.tv_word);
        }
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {
        TextView textCity;

        public CityViewHolder(View view) {
            super(view);
            textCity = (TextView) view.findViewById(R.id.tv_city_name);
        }
    }
}
