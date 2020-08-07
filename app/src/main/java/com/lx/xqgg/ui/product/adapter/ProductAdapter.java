package com.lx.xqgg.ui.product.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.product.bean.ProductBean;

import java.util.List;

import androidx.annotation.Nullable;

public class ProductAdapter extends BaseQuickAdapter<ProductBean.RecordsBean, BaseViewHolder> {
    private OnApplyClickListener onApplyClickListener;
    private String bal;
    private String fee;

    public ProductAdapter(@Nullable List<ProductBean.RecordsBean> data) {
        super(R.layout.item_product, data);
    }

    public void setOnApplyClickListener(OnApplyClickListener onApplyClickListener) {
        this.onApplyClickListener = onApplyClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean.RecordsBean item) {
        String[] titles=item.getTitle().split("-");
        bal = SharedPrefManager.getImitationexamination().getPro_bal();
        fee = SharedPrefManager.getImitationexamination().getPro_fee();
        helper.setText(R.id.tv_bank_name, titles[0].trim()+ "");
        if(titles.length>1) {
            helper.setText(R.id.textView2, titles[1].trim() + "");
        }else {
            helper.setText(R.id.textView2, "");
        }
        helper.setText(R.id.tv_ed, item.getQuota()/10000 + "万");
        helper.setText(R.id.tv_rlv, item.getRate() + "");
        helper.setText(R.id.tv_kded, "可贷"+bal);
        helper.setText(R.id.textView3, "参考日"+fee);
        Glide.with(mContext)
                .load(Config.IMGURL + item.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.ic_default))
                .into((ImageView) helper.getView(R.id.image_view));
        helper.setOnClickListener(R.id.btn_apply, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onApplyClickListener.onClick(item);
            }
        });
    }

    public interface OnApplyClickListener {
        void onClick(ProductBean.RecordsBean bean);
    }
}
