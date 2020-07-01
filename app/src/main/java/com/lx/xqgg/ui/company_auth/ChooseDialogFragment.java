package com.lx.xqgg.ui.company_auth;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.lx.xqgg.R;

public class ChooseDialogFragment extends BottomSheetDialogFragment {
    private BottomSheetBehavior behavior;

    private LinearLayout layoutPhoto;

    private LinearLayout layoutCamera;

    private OnChooseClickListener onChooseClickListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        View view = View.inflate(getContext(), R.layout.layout_choose, null);
        dialog.setContentView(view);
        behavior = BottomSheetBehavior.from((View) view.getParent());
        layoutPhoto = view.findViewById(R.id.layout_photo);
        layoutCamera = view.findViewById(R.id.layout_camera);
        layoutPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChooseClickListener.clickPhoto();
                dialog.dismiss();
            }
        });
        layoutCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChooseClickListener.clickCamera();
                dialog.dismiss();
            }
        });
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        //默认全屏展开
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        onChooseClickListener.clickNull();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void close() {
        //点击任意布局关闭
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void setOnChooseClickListener(OnChooseClickListener onChooseClickListener) {
        this.onChooseClickListener = onChooseClickListener;
    }


    public interface OnChooseClickListener {
        void clickPhoto();

        void clickCamera();

        void clickNull();
    }
}
