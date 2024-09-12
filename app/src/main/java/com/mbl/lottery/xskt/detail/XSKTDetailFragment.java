package com.mbl.lottery.xskt.detail;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.mbl.lottery.R;
import com.mbl.lottery.model.TicketXSKT;
import com.mbl.lottery.model.LineModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.model.response.GetItemXsktResponse;
import com.mbl.lottery.utils.BitmapUtils;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.DialogHelper;
import com.mbl.lottery.utils.DrawViewUtils;
import com.mbl.lottery.utils.MediaUltis;
import com.mbl.lottery.utils.NumberUtils;
import com.mbl.lottery.utils.SharedPref;
import com.mbl.lottery.utils.Toast;
import com.mbl.lottery.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class XSKTDetailFragment extends ViewFragment<XSKTDetailContract.Presenter> implements XSKTDetailContract.View {
    @BindView(R.id.tv_code)
    TextView tv_code;
    @BindView(R.id.tv_fullName)
    TextView tv_fullName;
    @BindView(R.id.tv_pid_number)
    TextView tv_pid_number;
    @BindView(R.id.tv_mobile_number)
    TextView tv_mobile_number;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_draw_code)
    TextView tv_draw_code;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.recycle)
    RecyclerView recycle;

    @BindView(R.id.image_before)
    public SimpleDraweeView image_before;
    @BindView(R.id.image_after)
    public SimpleDraweeView image_after;
    boolean IsPrint = false;
    String ticketType = Constants.TICKET_NO_AMOUNT;
    boolean IsBefore = true;
    SharedPref sharedPref;
    List<GetItemXsktResponse> mItemModels;
    List<TicketXSKT> mLineModels;
    OrderModel mOrderModel;
    XSKTDetailLineAdapter rowAdapter;
    OrderImagesRequest mOrderRequest;

    public static XSKTDetailFragment getInstance() {
        return new XSKTDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xskt_detail;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        sharedPref = new SharedPref(requireActivity());
        mItemModels = new ArrayList<>();
        mLineModels = new ArrayList<>();
        mOrderModel = mPresenter.getOrderModel();
        mOrderRequest = new OrderImagesRequest();
        if (mOrderModel != null) {
            tv_code.setText(mOrderModel.getCode());
            tv_fullName.setText(mOrderModel.getName());
            tv_pid_number.setText(mOrderModel.getPIDNumber());
            tv_amount.setText(NumberUtils.formatPriceNumber(mOrderModel.getQuantity()) + "/" + mOrderModel.getAmount());
            tv_mobile_number.setText(mOrderModel.getMobileNumber());
        }

        rowAdapter = new XSKTDetailLineAdapter(requireContext(), mLineModels);
        recycle.setLayoutManager(new FlexboxLayoutManager(requireContext()));
        recycle.setAdapter(rowAdapter);

        iv_back.setOnClickListener(view -> mPresenter.back());
    }

    @OnClick({R.id.image_before, R.id.image_after,R.id.btn_ok})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.image_before:
                capturePermission(false);
                IsBefore = true;
                break;
            case R.id.image_after:
                capturePermission(true);
                IsBefore = false;
                break;
            case R.id.btn_ok:
                onOk();
                break;
        }
    }
    void onOk(){
        if (TextUtils.isEmpty(mOrderRequest.getImgBefore())) {
            Toast.showToast(getViewContext(), "Bạn chưa cập nhật ảnh mặt trước!");
            return;
        }
//        if (TextUtils.isEmpty(mOrderRequest.getImgAfter())) {
//            Toast.showToast(getViewContext(), "Bạn chưa cập nhật ảnh mặt sau!");
//            return;
//        }
        mOrderRequest.setOrderCode(mOrderModel.getCode());
        mOrderRequest.setItemID(mItemModels.get(0).getId().toString());
        mPresenter.updateImage(mOrderRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                attemptSendMedia(data.getData().getPath());
            }
        }
    }

    @SuppressLint("CheckResult")
    private void attemptSendMedia(String path_media) {
        File file = new File(path_media);
        Uri picUri = Uri.fromFile(new File(path_media));
        if (IsBefore)
            image_before.setImageURI(picUri);
        else
            image_after.setImageURI(picUri);

        Observable.fromCallable(() -> {
                    Uri uri = Uri.fromFile(new File(path_media));
//            return BitmapUtils.processingBitmap(uri, getViewContext());
                    DrawViewUtils drawViewUtils = new DrawViewUtils(getContext());
                    if (IsBefore) {
                        String drawCode = "";
                        String drawDate = "";
//
                        if (mOrderModel.getProductID() == Constants.PRODUCT_KENO) {
                            if (mItemModels.size() > 1) {
                                drawCode = mItemModels.get(0).getDrawCode() + ";" + mItemModels.get(mItemModels.size() - 1).getDrawCode();
                                drawDate = mItemModels.get(0).getDrawDate() + ";" + mItemModels.get(mItemModels.size() - 1).getDrawDate();
                            } else {
                                drawCode = mItemModels.get(0).getDrawCode();
                                drawDate = mItemModels.get(0).getDrawDate();
                            }
                        } else {
                            drawCode = mItemModels.get(0).getDrawCode();
                            drawDate = mItemModels.get(0).getDrawDate();
                        }
                        List<LineModel> lines = new ArrayList<>();
                        for (int i = 0; i < mLineModels.size(); i++) {
                            TicketXSKT item = mLineModels.get(i);
                            LineModel lineModel = new LineModel();
                            lineModel.setLine(item.getLine());
                            lines.add(lineModel);
                        }
                        return drawViewUtils.processingBitmapBefore(uri,
                                Utils.getProductName(mOrderModel.getProductID()),
                                lines,
                                drawDate,
                                drawCode,
                                mOrderModel.getCode());
                    } else {
                        return drawViewUtils.processingBitmapAfter(uri, mOrderModel.getName(), mOrderModel.getPIDNumber(), mOrderModel.getMobileNumber());
                    }
                }).subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map(bitmap ->
                        BitmapUtils.saveImage(bitmap, file.getParent(), "hn_" + file.getName(), Bitmap.CompressFormat.JPEG, 50)
                )
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        isSavedImage -> {
                            String path = file.getParent() + File.separator + "hn_" + file.getName();
                            String type;
                            if (IsBefore)
                                type = Constants.IMAGE_BEFORE;
                            else
                                type = Constants.IMAGE_AFTER;

                            mPresenter.postImage(path);
                            if (file.exists())
                                file.delete();
                        },
                        onError -> Logger.e("error save image")
                );
    }

    private void capturePermission(boolean IsAfter) {
        CharSequence info;
        boolean IsAmount = ticketType.equals(Constants.TICKET_SHOW_AMOUNT);
        CharSequence infoAfter = Utils.getInfoImageAfter(requireActivity(), mOrderModel.getName(), mOrderModel.getPIDNumber(), mOrderModel.getMobileNumber());

        if (!IsAfter) {
            List<LineModel> lineModels = new ArrayList<>();
            String draw = "";

            String drawFist = mItemModels.get(0).getDrawCode();
            if (mOrderModel.getProductID() == Constants.PRODUCT_KENO) {
                if (mItemModels.size() > 1) {
                    String drawLast = mItemModels.get(mItemModels.size() - 1).getDrawCode();
                    draw = "Kỳ từ #" + drawFist + " đến #" + drawLast + ", Ngày quay:" + mItemModels.get(0).getDrawDate();
                } else {
                    draw = "Kỳ #" + drawFist + ", Ngày quay:" + mItemModels.get(0).getDrawDate();
                }
            } else {
                if (mItemModels.size() > 1) {
                    String drawLast = mItemModels.get(mItemModels.size() - 1).getDrawCode();
                    String drawLastDate = mItemModels.get(mItemModels.size() - 1).getDrawDate();
                    draw = "Kỳ từ #" + drawFist + " đến #" + drawLast + ", Ngày quay:" + mItemModels.get(0).getDrawDate() + "-" + drawLastDate;
                } else {
                    draw = "Kỳ #" + drawFist + ", Ngày quay:" + mItemModels.get(0).getDrawDate();
                }
            }
            List<LineModel> lines = new ArrayList<>();
            for (int i = 0; i < mLineModels.size(); i++) {
                TicketXSKT item = mLineModels.get(i);
                LineModel lineModel = new LineModel();
                lineModel.setLine(item.getLine());
                lines.add(lineModel);
            }
//            if (!TextUtils.isEmpty(systematic))
//                systematic = " - " + systematic;
            info = Utils.getInfoImageBefore(requireActivity(), lines, Utils.getProductName(mOrderModel.getProductID()), draw, IsAmount);
        } else {
            info = infoAfter;
        }

        PermissionUtils.permission(PermissionConstants.CAMERA)
                .rationale(DialogHelper::showRationaleDialog)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        MediaUltis.captureImage(XSKTDetailFragment.this, info, "#" + mOrderModel.getCode(), IsAfter);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog();
                        }
                    }
                })
                .request();
    }

    @Override
    public void showItem(List<GetItemXsktResponse> orderModels) {
        if (orderModels.size() > 0) {
            mItemModels = orderModels;
            GetItemXsktResponse item = orderModels.get(0);
            tv_draw_code.setText(item.getDrawDate());
            addLine(item);
            rowAdapter.setItems(mLineModels);
        }
    }

    private void addLine(GetItemXsktResponse item) {
        try {
            TicketXSKT lineModel = new TicketXSKT();
            if (!TextUtils.isEmpty(item.getLineA())) {
                lineModel.setDrawlerIndex(item.getDrawlerIndexA());
                lineModel.setLocker(item.getLockerA());
                lineModel.setSymbol(item.getSymbolA());
                lineModel.setSystem(item.getSystemA());
                lineModel.setLine(item.getLineA());
                mLineModels.add(lineModel);
            }

            if (!TextUtils.isEmpty(item.getLineB())) {
                lineModel = new TicketXSKT();
                lineModel.setDrawlerIndex(item.getDrawlerIndexB());
                lineModel.setLocker(item.getLockerB());
                lineModel.setSymbol(item.getSymbolB());
                lineModel.setSystem(item.getSystemB());
                lineModel.setLine(item.getLineB());
                mLineModels.add(lineModel);
            }

            if (!TextUtils.isEmpty(item.getLineC())) {
                lineModel = new TicketXSKT();
                lineModel.setDrawlerIndex(item.getDrawlerIndexC());
                lineModel.setLocker(item.getLockerC());
                lineModel.setSymbol(item.getSymbolC());
                lineModel.setSystem(item.getSystemC());
                lineModel.setLine(item.getLineC());
                mLineModels.add(lineModel);
            }


            if (!TextUtils.isEmpty(item.getLineD())) {
                lineModel = new TicketXSKT();
                lineModel.setDrawlerIndex(item.getDrawlerIndexD());
                lineModel.setLocker(item.getLockerD());
                lineModel.setSymbol(item.getSymbolD());
                lineModel.setSystem(item.getSystemD());
                lineModel.setLine(item.getLineD());
                mLineModels.add(lineModel);
            }

            if (!TextUtils.isEmpty(item.getLineE())) {
                lineModel = new TicketXSKT();
                lineModel.setDrawlerIndex(item.getDrawlerIndexE());
                lineModel.setLocker(item.getLockerE());
                lineModel.setSymbol(item.getSymbolE());
                lineModel.setSystem(item.getSystemE());
                lineModel.setLine(item.getLineE());
                mLineModels.add(lineModel);
            }

            if (!TextUtils.isEmpty(item.getLineF())) {
                lineModel = new TicketXSKT();
                lineModel.setDrawlerIndex(item.getDrawlerIndexF());
                lineModel.setLocker(item.getLockerF());
                lineModel.setSymbol(item.getSymbolF());
                lineModel.setSystem(item.getSystemF());
                lineModel.setLine(item.getLineF());
                mLineModels.add(lineModel);
            }
        } catch (Exception e) {
            Log.e("addLine", e.getMessage());
        }
    }

    @Override
    public void showImage(String file) {
        if (IsBefore) {
            mOrderRequest.setImgBefore(file);
//            IsBefore = false;
//            capturePermission(true);
        } else {
            mOrderRequest.setImgAfter(file);
        }
    }
}
