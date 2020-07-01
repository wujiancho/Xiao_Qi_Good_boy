package com.lx.xqgg.ui.city;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.city.adapter.CitiesAdapter;
import com.lx.xqgg.ui.city.bean.CityBean;
import com.lx.xqgg.ui.city.bean.CityHistoryBean;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.solver.GoalRow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CityPickerActivity extends BaseActivity implements CitiesAdapter.onCityPickListener {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.quickIndexView)
    QuickIndexView quickIndexView;

    private List<CityBean> cityBeanList;
    private CitiesAdapter citiesAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city_picker;
    }

    @Override
    protected void initView() {
        tvTitle.setText("请选择城市");
        cityBeanList = SharedPrefManager.getCityList();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        citiesAdapter = new CitiesAdapter(cityBeanList, mContext);
        recyclerView.setAdapter(citiesAdapter);
        citiesAdapter.setOnCityPickListener(this);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<CityBean> list = search(s.toString());
                cityBeanList.clear();
                cityBeanList.addAll(list);
                citiesAdapter.notifyDataSetChanged();
            }
        });

        if (cityBeanList == null || cityBeanList.size() == 0) {
            addSubscribe(ApiManage.getInstance().getMainApi().getCity("2")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new BaseSubscriber<BaseData<List<CityBean>>>(mContext, null) {
                        @Override
                        public void onNext(BaseData<List<CityBean>> listBaseData) {
                            SharedPrefManager.setCityList(listBaseData.getData());
                            cityBeanList.clear();
                            cityBeanList.addAll(listBaseData.getData());
                            citiesAdapter.notifyDataSetChanged();
                        }
                    }));
        }

        quickIndexView.setOnIndexChangeListener(new QuickIndexView.OnIndexChangeListener() {
            @Override
            public void onIndexChange(String words) {
                if (cityBeanList != null && cityBeanList.size() > 0) {
                    int count = 0;
                    for (int i = 0; i < cityBeanList.size(); i++) {
                        CityBean datasBean = cityBeanList.get(i);
                        if (datasBean.getFisrt().equals(words)) {
                            LinearLayoutManager llm = (LinearLayoutManager) recyclerView
                                    .getLayoutManager();
                            llm.scrollToPositionWithOffset(count + 1, 0);
                            return;
                        }
                        count += datasBean.getListCity().size() + 1;
                    }
                }
            }
        });
    }

    private List<CityBean> search(String s) {
        if (TextUtils.isEmpty(s)) {
            return SharedPrefManager.getCityList();
        }
        Log.e("zlz", s.toUpperCase());
        Log.e("zlz", cityBeanList.size() + "");
        List<CityBean> mlist = new ArrayList<>();
        for (CityBean cityBean : SharedPrefManager.getCityList()) {
            if (cityBean.getFisrt().equals(s.toUpperCase())) {
                Log.e("zlz", "首字母等于");
                mlist.add(cityBean);
            } else {
                CityBean cityBean1 = new CityBean();
                cityBean1.setFisrt(cityBean.getFisrt());
                Log.e("zlz", "setFirst" + cityBean.getFisrt());
                List<CityBean.ListCityBean> list = cityBean.getListCity();
                List<CityBean.ListCityBean> result = new ArrayList<>();
                for (CityBean.ListCityBean bean : list) {
                    if (bean.getName().contains(s)) {
                        Log.e("zlz", bean.getName() + "");
                        result.add(bean);
                    }
                }
                cityBean1.setListCity(result);
                if (cityBean1.getListCity().size() != 0) {
                    mlist.add(cityBean1);
                }
            }
        }
        Log.e("zlz", new Gson().toJson(mlist));
        Log.e("zlz", s + "size" + mlist.size());
        return mlist;
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.v_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
        }
    }

    @Override
    public void select(String province, String city) {
        Constans.CITY = city;
        Constans.PROVINCE = province;

        List<CityHistoryBean> list = new ArrayList<>();
        list = SharedPrefManager.getCityHistory();

        if (list == null) {
            list = new ArrayList<>();
            list.add(new CityHistoryBean(province, city));
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCity() != null) {
                    if (list.get(i).getCity().equals(city)) {
                        list.remove(list.get(i));
                        break;
                    }
                }
            }
            list.add(0, new CityHistoryBean(province, city));
        }

        if (list.size() > 3) {
            list.remove(list.get(list.size() - 1));
        }
        SharedPrefManager.setCityHistory(list);
        Log.e("zlz", Constans.PROVINCE);
        setResult(RESULT_OK);
        finish();
    }
}
