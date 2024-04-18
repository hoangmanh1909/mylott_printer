package com.mbl.lottery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.core.base.BaseActivity;
import com.mbl.lottery.R;

import java.util.List;

import butterknife.ButterKnife;

public class ShareDialog extends Dialog {
    private final BaseActivity mActivity;
    private final  List<String> mImages;
    public ShareDialog(@NonNull Context context, String code, List<String> images) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        mActivity = (BaseActivity) context;

        this.mImages = images;

        View view = View.inflate(getContext(), R.layout.dialog_share_images, null);
        setContentView(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void show() {
        super.show();
    }
}
