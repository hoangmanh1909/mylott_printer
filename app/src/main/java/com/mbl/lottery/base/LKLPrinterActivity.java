package com.mbl.lottery.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import com.core.base.ContainerActivity;
import com.mbl.lottery.utils.DialogUtils;

public abstract class LKLPrinterActivity extends ContainerActivity {
    @Override
    public void showAlertDialog(String message) {
        DialogUtils.showAlert(this, message);
    }

    @Override
    public void showAlertDialog(String message, DialogInterface.OnClickListener onClickListener) {
        DialogUtils.showAlertAction(this, message,onClickListener);
    }

    @Override
    public void showProgress() {
        DialogUtils.showProgressDialog(this);
    }

    @Override
    public void hideProgress() {
        DialogUtils.dismissProgressDialog();
    }

    @Override
    public void onRequestError(String errorCode, String errorMessage) {
        DialogUtils.showErrorAlert(this, errorMessage);
    }

    @Override
    public void showErrorAlert(Context context, String string) {
        DialogUtils.showErrorAlert(context, string);
    }

    @Override
    public void showNetworkErrorDialog(Activity activity) {
        DialogUtils.showNetworkErrorDialog(activity);
    }

}
