package com.lx.xqgg.ui.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.home.bean.ResultBean;

import java.util.List;

public class ResultAdapter extends BaseMultiItemQuickAdapter<ResultBean, BaseViewHolder> {

    private String mon;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ResultAdapter(List<ResultBean> data) {
        super(data);
        addItemType(0, R.layout.item_search_company);
        addItemType(1, R.layout.item_search_product);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResultBean item) {
        mon = SharedPrefManager.getImitationexamination().getPro_mon();
        switch (item.getItemType()) {
            case 0:
                helper.setText(R.id.tv_company_name, item.getCompany1());
                helper.setText(R.id.tv_phone,  item.getName1() + " "+item.getPhone1());
                Glide.with(mContext)
                        .load(item.getIsTop().equals("0") ? R.drawable.ic_not_top : R.drawable.ic_is_top)
                        .into((ImageView) helper.getView(R.id.iv_top));
                break;
            case 1:
                String[] titles=item.getName().split("-");
                helper.setText(R.id.tv_bank_name, titles[0]+ "");
                if(titles.length>1) {
                    helper.setText(R.id.textView2, titles[1] + "");
                }else {
                    helper.setText(R.id.textView2, "");
                }
//                helper.setText(R.id.tv_bank_name, item.getName() + "");
                helper.setText(R.id.tv_ed, item.getQuota()/10000 + mon);
                helper.setText(R.id.tv_rlv, item.getRate() + "");
                Glide.with(mContext)
                        .load(Config.IMGURL+item.getImage())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                        .into((ImageView) helper.getView(R.id.image_view));
                break;
        }
    }
}
