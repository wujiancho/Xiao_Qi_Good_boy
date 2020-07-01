package com.lx.xqgg.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.ui.vip.bean.PayListBean;

import java.util.List;

public class MultiSelectPopupWindows extends PopupWindow {
    private Context context;
    private List<PayListBean> listBeans;
    private int selectPosition = -1;
    private Myadapter adapter;
    private int type = -1;
    private onSelectListener onSelectListener;

    public void setOnSelectListener(onSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public MultiSelectPopupWindows(Context context, List<PayListBean> data, int type) {
        this.context = context;
        this.listBeans = data;
        this.type = type;
        initView();
    }

    private void initView() {
        View view = View.inflate(context, R.layout.popup_window_select, null);
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in_slow));
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layout);
        linearLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.list_top_in));
        ListView listView = (ListView) view.findViewById(R.id.listView_selector);
        Button button = view.findViewById(R.id.btn_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onSelect(selectPosition, type);
                dismiss();
            }
        });
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        update();
        initListView(listView, listBeans);
    }

    public void setCheckPostion(int postion) {
        selectPosition = postion;
        adapter.notifyDataSetChanged();
    }

    private void initListView(ListView listView, List<PayListBean> data) {
        adapter = new Myadapter(context, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectPosition == position) {
                    selectPosition = -1;
                } else {
                    selectPosition = position;
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public interface onSelectListener {
        void onSelect(int postion, int type);
    }

    public class Myadapter extends BaseAdapter {
        Context context;
        List<PayListBean> brandsList;
        LayoutInflater mInflater;

        public Myadapter(Context context, List<PayListBean> mList) {
            this.context = context;
            this.brandsList = mList;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return brandsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_select, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.select = (CheckBox) convertView.findViewById(R.id.checkbox);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(brandsList.get(position).getName());
            if (selectPosition == position) {
                viewHolder.select.setChecked(true);
                viewHolder.name.setTextColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                viewHolder.select.setChecked(false);
                viewHolder.name.setTextColor(context.getResources().getColor(R.color.txt_black));
            }
            return convertView;
        }

        public class ViewHolder {
            TextView name;
            CheckBox select;
        }
    }
}
