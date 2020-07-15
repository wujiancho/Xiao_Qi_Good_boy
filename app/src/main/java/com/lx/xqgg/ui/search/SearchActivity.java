package com.lx.xqgg.ui.search;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.home.adapter.ResultAdapter;
import com.lx.xqgg.ui.home.bean.ResultBean;
import com.lx.xqgg.ui.my_client.ClientDetailActivity;
import com.lx.xqgg.ui.product.ProductDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.et_search_content)
    EditText etSearchContent;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<ResultBean> list = new ArrayList<>();
    private ResultAdapter resultAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {

        etSearchContent.setFocusable(true);
        etSearchContent.requestFocus();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        resultAdapter = new ResultAdapter(list);
        recyclerView.setAdapter(resultAdapter);
        resultAdapter.setEmptyView(R.layout.layout_empty, recyclerView);
        resultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ResultBean resultBean = list.get(position);
                switch (resultBean.getItemType()) {
                    case 0:
                        Intent intent1 = new Intent(mContext, ClientDetailActivity.class);
                        intent1.putExtra("customerName1", resultBean.getName1() + "");
                        intent1.putExtra("commpanyName1", resultBean.getCompany1() + "");
                        intent1.putExtra("phone1", resultBean.getPhone1() + "");
                        intent1.putExtra("customerName", resultBean.getName() + "");
                        intent1.putExtra("phone", resultBean.getPhone() + "");
                        intent1.putExtra("commpanyName", resultBean.getCompany() + "");
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent = new Intent(mContext, ProductDetailActivity.class);
                        intent.putExtra("data", list.get(position).getId());
                        startActivity(intent);
                        break;
                }
            }
        });

        etSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search(s.toString());
            }
        });
    }

    @Override
    protected void initData() {
//        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//        // 创建普通字符型ClipData
//        ClipData mClipData = ClipData.newPlainText("Label", "这里是要复制的文字");
//        // 将ClipData内容放到系统剪贴板里。
//        cm.setPrimaryClip(mClipData);
    }

    private void search(String msg) {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("search_words", etSearchContent.getText().toString());
        paramsMap.put("cityName", Constans.CITY);
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().search(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<ResultBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<ResultBean>> listBaseData) {
                        Log.e("zlz", new Gson().toJson(listBaseData));
                        if (listBaseData.isSuccess()) {
                            if (listBaseData.getData() != null) {
                                list.clear();
                                list.addAll(listBaseData.getData());
                                resultAdapter.notifyDataSetChanged();
                            }
                        } else {
                            toast(listBaseData.getMessage());
                        }
                    }
                }));
    }


    @OnClick({R.id.v_close, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.tv_search:
                search(etSearchContent.getText().toString().trim());
                break;
        }
    }
}
