package com.lx.xqgg.face_ui.home.msg.dapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hymane.expandtextview.ExpandTextView;
import com.lx.xqgg.R;

import java.util.List;

import androidx.annotation.Nullable;

public class FaceMsgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FaceMsgAdapter(@Nullable List<String> data) {
        super(R.layout.item_face_msg, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ExpandTextView expandTextView= helper.getView(R.id.tv_expandtext);
        expandTextView.setContent(item);
    }
}
