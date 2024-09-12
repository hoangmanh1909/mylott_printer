package com.mbl.lottery.scanner;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.core.base.viper.ViewFragment;
import com.google.zxing.Result;
import com.mbl.lottery.R;
import com.mbl.lottery.base.LKLPrinterActivity;

import butterknife.BindView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerCodeFragment extends ViewFragment<ScannerCodeContract.Presenter> implements ScannerCodeContract.View, ZXingScannerView.ResultHandler {

    @BindView(R.id.camera_view)
    ZXingScannerView cameraView;
    @BindView(R.id.iv_back)

    ImageView imgBack;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    public static ScannerCodeFragment getInstance() {
        return new ScannerCodeFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scanner_code;
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
//        cameraView.setFormats(BarcodeFormat.ALL_FORMATS);
        cameraView.setResultHandler(this); // Register ourselves as a handler for scan results.
        cameraView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void initLayout() {
        super.initLayout();
        checkSelfPermission();
        if (((LKLPrinterActivity) getActivity()).getSupportActionBar() != null)
            ((LKLPrinterActivity) getActivity()).getSupportActionBar().hide();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.back();
            }
        });
    }
    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }
//    @Override
//    public void handleResult(Result result) {
//        mPresenter.getDelegate().scanQrcodeResponse(result.getContents());
//        mPresenter.back();
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                cameraView.startCamera();
            }
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        mPresenter.getDelegate().scanQrcodeResponse(rawResult.getText());
        mPresenter.back();
    }
}