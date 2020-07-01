package com.lx.xqgg.ui.my_client;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

public class MyClientActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<MyClientFragment> fragments = new ArrayList<>();
    private MyAdapter myAdapter;
    private String[] tabsTitle = {"企业客户", "个人客户"};
    private String[] typeStrings = {"company", "personal"};

    private String search_words;
    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_client;
    }

    @Override
    protected void initView() {
        tvTitle.setText("我的客户");
        for (int i = 0; i < tabsTitle.length; i++) {
            fragments.add(MyClientFragment.newInstance("", typeStrings[i]));
        }
        viewPager.setOffscreenPageLimit(tabsTitle.length);
        myAdapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myAdapter);
        tablayout.setupWithViewPager(viewPager);

//        etSearch.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
//                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
//                    //处理事件
//                    search_words = etSearch.getText().toString().trim();
//                    myAdapter.notifyDataSetChanged();
//                    return true;
//                }
//                return false;
//            }
//        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //处理事件
                search_words = etSearch.getText().toString().trim();
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData() {

    }

    public class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return tabsTitle.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabsTitle[position];
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            MyClientFragment fragment = (MyClientFragment) super.instantiateItem(container, position);
            fragment.update(search_words, typeStrings[position]);
            return fragment;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }
}
