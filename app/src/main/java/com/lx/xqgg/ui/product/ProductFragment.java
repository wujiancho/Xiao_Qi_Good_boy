package com.lx.xqgg.ui.product;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseFragment;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.city.CityPickerActivity;
import com.lx.xqgg.ui.product.bean.CateBean;
import com.lx.xqgg.ui.vip.bean.PayListBean;
import com.lx.xqgg.widget.MultiSelectPopupWindows;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductFragment extends BaseFragment implements MultiSelectPopupWindows.onSelectListener {
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rb_ed)
    RadioButton rbEd;
    @BindView(R.id.rb_fl)
    RadioButton rbFl;
    @BindView(R.id.rb_hkfs)
    RadioButton rbHkfs;
    @BindView(R.id.rb_cptd)
    RadioButton rbCptd;
    @BindView(R.id.rg)
    RadioGroup rg;

    private String[] titles;
    private List<ProductTypeFragment> fragments;
    private List<CateBean> cateBeanList;

    private static int SELECT_CITY_CODE = 1001;

    private String title = "";
    private String classifyId = "";
    private String guarantee = "";
    private String orderby = "";
    private String orderway = "desc";
    private String proFeature = "";

    private MyAdapter myAdapter;


    private MultiSelectPopupWindows hkType;
    private int hkTypePostion = -1;
    private MultiSelectPopupWindows cpCharacter;
    private int cpCharacterPostion = -1;

    private List<PayListBean> listCharacter = new ArrayList<>();
    private List<PayListBean> listType = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    protected void initView() {
        tvCity.setText(Constans.CITY);
//        etSearch.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
//                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
//                    //处理事件
//                    title = etSearch.getText().toString().trim();
//                    orderby = "weigh";
//                    guarantee = "";
//                    myAdapter.notifyDataSetChanged();
//
//                    Log.e("zlz", "sarch");
//                    return true;
//                }
//                return false;
//            }
//        });
//        rbEd.setChecked(true);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                title = etSearch.getText().toString().trim();
                orderby = "weigh";
                guarantee = "";
                myAdapter.notifyDataSetChanged();
            }
        });

        listType.add(new PayListBean(0, "先息后本", "ben"));
        listType.add(new PayListBean(1, "等额本金", "jin"));
        listType.add(new PayListBean(2, "等额本息", "xi"));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            try {
                if (tvCity.getText().toString().equals(Constans.CITY)) {
                    return;
                }
                tvCity.setText(Constans.CITY);
                if (myAdapter == null) {
                    return;
                }
                myAdapter.notifyDataSetChanged();
            } catch (Exception e) {

            }
        }
    }

    @OnClick({R.id.rb_ed, R.id.rb_fl, R.id.rb_hkfs, R.id.rb_cptd, R.id.layout_city})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_ed:
                orderby = "quota";
                guarantee = "";
                if (orderway.equals("asc")) {
                    orderway = "desc";
                } else {
                    orderway = "asc";
                }
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.rb_fl:
                orderby = "rate";
                guarantee = "";
                if (orderway.equals("asc")) {
                    orderway = "desc";
                } else {
                    orderway = "asc";
                }
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.rb_hkfs:
                if (hkType == null) {
                    hkType = new MultiSelectPopupWindows(mContext, listType, 1);
                    hkType.setOnSelectListener(ProductFragment.this);
                }
                hkType.setCheckPostion(hkTypePostion);
                hkType.showAsDropDown(rbHkfs);
                break;
            case R.id.rb_cptd:
                if (cpCharacter == null) {
                    cpCharacter = new MultiSelectPopupWindows(mContext, listCharacter, 2);
                    cpCharacter.setOnSelectListener(ProductFragment.this);
                }
                cpCharacter.setCheckPostion(cpCharacterPostion);
                cpCharacter.showAsDropDown(rbCptd);
                break;
            case R.id.layout_city:
                startActivityForResult(new Intent(getContext(), CityPickerActivity.class), SELECT_CITY_CODE);
                break;
        }
    }


    @Override
    protected void initData() {
        cateBeanList = SharedPrefManager.getCate();
        if (cateBeanList == null || cateBeanList.size() == 0) {
            addSubscribe(ApiManage.getInstance().getMainApi().getCateList(Constans.APPID, Constans.INDOOR)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new BaseSubscriber<BaseData<List<CateBean>>>(mContext, null) {
                        @Override
                        public void onNext(BaseData<List<CateBean>> listBaseData) {
                            if (listBaseData.isSuccess()) {
                                if (listBaseData.getData() != null) {
                                    cateBeanList = listBaseData.getData();
                                    initFragment();
                                }
                            }
                        }
                    }));
        } else {
            initFragment();
        }
        listCharacter = SharedPrefManager.getCharacter();

        if (listCharacter == null || listCharacter.size() == 0) {

        }
    }

    private void initFragment() {
        titles = new String[cateBeanList.size() + 1];
        fragments = new ArrayList<>();
        titles[0] = "全部";
        fragments.add(ProductTypeFragment.newInstance("", "", "", "", "", proFeature));
        for (int i = 0; i < cateBeanList.size(); i++) {
            Log.e("zlz", cateBeanList.get(i).getId() + "");
            titles[i + 1] = cateBeanList.get(i).getName();
            fragments.add(ProductTypeFragment.newInstance("", cateBeanList.get(i).getId() + "", "", "", "", proFeature));
//            fragments.add(ProductTypeFragment.newInstance("", "", "", "", ""));
        }
        viewPager.setOffscreenPageLimit(titles.length);
        myAdapter = new MyAdapter(getChildFragmentManager());
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(2).select();
    }

    public void setItemSelected(int poistion) {
        try {
            tabLayout.getTabAt(poistion).select();
        } catch (Exception e) {

        }
    }

    @Override
    public void onSelect(int postion, int type) {
        switch (type) {
            case 1:
                hkTypePostion = postion;
                if (hkTypePostion == -1) {
                    rbHkfs.setChecked(false);
                    guarantee = "";
                    myAdapter.notifyDataSetChanged();
                    return;
                }
                guarantee = listType.get(postion).getValue();
                myAdapter.notifyDataSetChanged();
                break;
            case 2:
                cpCharacterPostion = postion;
                if (cpCharacterPostion == -1) {
                    rbCptd.setChecked(false);
                    proFeature = "";
                    myAdapter.notifyDataSetChanged();
                    return;
                }
                proFeature = listCharacter.get(postion).getId() + "";
                myAdapter.notifyDataSetChanged();
                break;
        }
    }

    public class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            String id = "";
            if (position == 0) {

            } else {
                id = cateBeanList.get(position - 1).getId() + "";
            }
            ProductTypeFragment fragment = (ProductTypeFragment) super.instantiateItem(container, position);
            fragment.update(title, id, guarantee, orderby, orderway, proFeature);
            return fragment;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_CITY_CODE) {
            if (resultCode == RESULT_OK) {
                tvCity.setText(Constans.CITY);
                myAdapter.notifyDataSetChanged();
            }
        }
    }
}
