package com.lx.xqgg.ui.message.adapter;

import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.xqgg.R;
import com.lx.xqgg.ui.message.bean.MessageBean;
import com.lx.xqgg.widget.AutoSplitTextView;
import com.lx.xqgg.widget.TypesetTextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;

public class MessageAdapter extends BaseQuickAdapter<MessageBean.RecordsBean, BaseViewHolder> {
    private onDelListener onDelListener;

    public MessageAdapter(@Nullable List<MessageBean.RecordsBean> data) {
        super(R.layout.item_del_message, data);
    }

    public void setOnDelListener(onDelListener onDelListener) {
        this.onDelListener = onDelListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean.RecordsBean item) {
//        helper.setIsRecyclable(false);
//        String msg=item.getContent();
//        TypesetTextView typesetTextView=helper.getView(R.id.tv_message);
//        typesetTextView.setTag(helper.getAdapterPosition());
//        typesetTextView.setText(msg);
        helper.setText(R.id.tv_message, ToDBC(item.getContent()));
        helper.setText(R.id.tv_time, item.getCreatetime());
        helper.setOnClickListener(R.id.btn_del, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDelListener != null) {
                    onDelListener.onDel(item);
                }
            }
        });
    }

    public interface onDelListener {
        void onDel(MessageBean.RecordsBean bean);
    }

    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

}
