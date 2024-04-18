package com.mbl.lottery.printer.upload;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.mbl.lottery.BuildConfig;
import com.mbl.lottery.R;
import com.mbl.lottery.dialog.ProcessFragDialog;
import com.mbl.lottery.model.EmployeeModel;
import com.mbl.lottery.model.ImageModel;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.LineModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.PrintCommandModel;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.model.request.OrderTablesImagesRequest;
import com.mbl.lottery.model.request.OrderTablesItemImages;
import com.mbl.lottery.service.BluetoothServices;
import com.mbl.lottery.utils.BitmapUtils;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.DialogHelper;
import com.mbl.lottery.utils.DrawViewUtils;
import com.mbl.lottery.utils.MediaUltis;
import com.mbl.lottery.utils.SharedPref;
import com.mbl.lottery.utils.Toast;
import com.mbl.lottery.utils.Utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UploadFragment extends ViewFragment<UploadContract.Presenter> implements UploadContract.View {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_fullName)
    TextView tv_fullName;
    @BindView(R.id.tv_pid_number)
    TextView tv_pid_number;
    @BindView(R.id.tv_mobile_number)
    TextView tv_mobile_number;
    @BindView(R.id.btn_print)
    Button btn_print;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.iv_copy)
    ImageView iv_copy;
    UploadAdapter adapter;
    String ticketType = Constants.TICKET_NO_AMOUNT;

    OrderModel mOrderModel = new OrderModel();
    List<ItemModel> itemModelList = new ArrayList<>();
    boolean IsBefore = true;
    boolean IsSuccess = false;
    List<LineModel> mLineModels = new ArrayList<>();
    ItemModel mItem;
    int mPosition;
    int mCountImage;
    EmployeeModel employeeModel;
    String systematic = "";

    SharedPref sharedPref;
    boolean IsConnectedBluetooth = false;
    String deviceBluetooth = "";
    RecyclerView.ViewHolder mHolder;
    ProcessFragDialog processFragDialog;
    long SymbolSpecial = Constants.SymbolSpecial;
    long SymbolBase = Constants.SymbolBase;
    long SymbolNumber = Constants.SymbolNumber;

    public static UploadFragment getInstance() {
        return new UploadFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_upload;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mCountImage = 0;
        SharedPref sharedPref = new SharedPref(requireActivity());
        employeeModel = sharedPref.getEmployeeModel();

        if (mPresenter != null) {
            mOrderModel = mPresenter.getOrderModel();
            itemModelList = mPresenter.getItemModels();

            tv_title.setText("#" + mOrderModel.getCode());
            tv_fullName.setText(mOrderModel.getName());
            tv_pid_number.setText(mOrderModel.getPIDNumber());
            tv_mobile_number.setText(mOrderModel.getMobileNumber());

            switch (mOrderModel.getProductID()) {
                case Constants.PRODUCT_MEGA:
                case Constants.PRODUCT_POWER:
                    ticketType = Constants.TICKET_NO_AMOUNT;
                    break;
                case Constants.PRODUCT_MAX3D:
                case Constants.PRODUCT_MAX3D_PLUS:
                case Constants.PRODUCT_MAX3D_PRO:
                case Constants.PRODUCT_KENO:
                case Constants.PRODUCT_LOTO235:
                case Constants.PRODUCT_LOTO234CAPSO:
                case Constants.PRODUCT_LOTO636:
                    ticketType = Constants.TICKET_SHOW_AMOUNT;
                    break;
            }

            recycle.setLayoutManager(new FlexboxLayoutManager(requireContext()));
            adapter = new UploadAdapter(requireContext(), itemModelList) {
                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
                    super.onBindViewHolder(holder, position);
                    ((HolderView) holder).image_before.setOnClickListener(v -> {
                        IsBefore = true;
                        capturePermission(itemModelList.get(position));
                        mItem = itemModelList.get(position);
                        mPosition = position;
                    });

                    ((HolderView) holder).image_after.setOnClickListener(v -> {
                        IsBefore = false;
                        capturePermission(itemModelList.get(position));
                        mItem = itemModelList.get(position);
                        mPosition = position;
                    });
                    ((HolderView) holder).share_before.setOnClickListener(v -> {
                        if(IsSuccess) {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, BuildConfig.IMAGE_BROWSER_URL + itemModelList.get(position).getImgBefore());
                            sendIntent.setType("image/*");
                            startActivity(sendIntent);
                        }else{
                            Toast.showToast(getViewContext(), "Vui lòng cập nhật ảnh vé trước khi chia sẻ ảnh");
                        }
                    });
                    ((HolderView) holder).share_after.setOnClickListener(v -> {
                        if(IsSuccess) {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, BuildConfig.IMAGE_BROWSER_URL + itemModelList.get(position).getImgAfter());
                            sendIntent.setType("image/*");
                            startActivity(sendIntent);
                        }else{
                            Toast.showToast(getViewContext(), "Vui lòng cập nhật ảnh vé trước khi chia sẻ ảnh");
                        }
                    });
                }
            };
            recycle.setAdapter(adapter);

            if (itemModelList.size() > 0) {
                mItem = itemModelList.get(0);
                mPosition = 0;

                if (mItem.getProductID() == Constants.PRODUCT_KENO) {
                    systematic = " - Bậc " + mItem.getSystemA();
                } else {
                    if(mItem.getProductID() == Constants.PRODUCT_MAX3D_PRO){
                        if(mItem.getProductTypeID() == 2) {
                            if (mItem.getBag() == 2) {
                                systematic = " - Bao bộ số";
                            }else{
                                systematic = " - Bao " + mItem.getBag() + " số";
                            }
                        }
                    }else {
                        if (mItem.getSystemA() > 6)
                            systematic = " - Bao " + mItem.getSystemA();
                        if (mItem.getSystemA() == 5)
                            systematic = " - Bao 5";
                    }
                }

                capturePermission(itemModelList.get(0));
            }
        }
    }

    @OnClick({R.id.btn_print, R.id.iv_back, R.id.iv_printer,R.id.iv_copy})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_print:
                ok();
                break;
            case R.id.iv_back:
                mPresenter.back();
                break;
            case R.id.iv_printer:
                printAgain();
                break;
            case R.id.iv_copy:
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Đã sao chép", mOrderModel.getMobileNumber());
                clipboard.setPrimaryClip(clip);
                Toast.showToast(getContext(), "Đã sao chép " +  mOrderModel.getMobileNumber());
                break;
        }
    }

    void ok() {
        boolean IsCheckImage = true;
        if (mOrderModel.getProductID() == Constants.PRODUCT_MAX3D || mOrderModel.getProductID() == Constants.PRODUCT_LOTO235
                || mOrderModel.getProductID() == Constants.PRODUCT_LOTO234CAPSO
                || mOrderModel.getProductID() == Constants.PRODUCT_LOTO636) {
            for (ItemModel itemModel : itemModelList) {
                if (TextUtils.isEmpty(itemModel.getImgBefore())) {
                    IsCheckImage = false;
                    break;
                }
            }
        } else {
            for (ItemModel itemModel : itemModelList) {
                if (TextUtils.isEmpty(itemModel.getImgBefore()) || TextUtils.isEmpty(itemModel.getImgAfter())) {
                    IsCheckImage = false;
                    break;
                }
            }
        }
        if (IsCheckImage) {
            List<OrderImagesRequest> request = new ArrayList<>();

            for (ItemModel itemModel : itemModelList) {
                OrderImagesRequest item = new OrderImagesRequest();
                item.setItemID(itemModel.getId().toString());
                item.setImgAfter(itemModel.getImgAfter());
                item.setImgBefore(itemModel.getImgBefore());
                item.setOrderCode(itemModel.getOrderCode());
                request.add(item);
            }
            mPresenter.updateImages(request);
        } else {
            Toast.showToast(getViewContext(), "Bạn chưa cập nhật đủ ảnh!");
        }
    }

    private List<LineModel> getLineModel(ItemModel item) {
        LineModel lineModel = new LineModel();
        mLineModels = new ArrayList<>();
        if (!TextUtils.isEmpty(item.getLineA())) {
            lineModel.setAmount(item.getPriceA());
            lineModel.setTitle("A");
            lineModel.setProductID(item.getProductID());
            if (item.getProductTypeID() == 2 && item.getProductID() == Constants.PRODUCT_KENO)
                lineModel.setLine(Utils.getKenoPlus(item.getLineA()));
            else
                lineModel.setLine(item.getLineA());
            lineModel.setType(ticketType);
            lineModel.setDrawCode(item.getDrawCode());
            lineModel.setDrawDate(item.getDrawDate());
//                lineModel.setId(item.getOrderItemID());
//                lineModel.setSystematic(item.getSystemTypeA());
//
//                if (item.getItemType() != null) {
//                    lineModel.setItemType(item.getItemType());
//                }
            mLineModels.add(lineModel);
        }

        if (!TextUtils.isEmpty(item.getLineB())) {
            lineModel = new LineModel();
            lineModel.setAmount(item.getPriceB());
            lineModel.setTitle("B");
            lineModel.setProductID(item.getProductID());
            if (item.getProductTypeID() == 3 && item.getProductID() == Constants.PRODUCT_KENO)
                lineModel.setLine(Utils.getKenoPlus(item.getLineB()));
            else
                lineModel.setLine(item.getLineB());
            lineModel.setType(ticketType);
            lineModel.setDrawCode(item.getDrawCode());
            lineModel.setDrawDate(item.getDrawDate());
//                lineModel.setId(item.getOrderItemID());
//                lineModel.setSystematic(item.getSystemTypeB());
//                if (item.getItemType() != null) {
//                    lineModel.setItemType(item.getItemType());
//                }
            mLineModels.add(lineModel);
        }

        if (!TextUtils.isEmpty(item.getLineC())) {
            lineModel = new LineModel();
            lineModel.setAmount(item.getPriceC());
            lineModel.setTitle("C");
            lineModel.setProductID(item.getProductID());
            if (item.getProductTypeID() == 3 && item.getProductID() == Constants.PRODUCT_KENO)
                lineModel.setLine(Utils.getKenoPlus(item.getLineC()));
            else
                lineModel.setLine(item.getLineC());
            lineModel.setType(ticketType);
            lineModel.setDrawCode(item.getDrawCode());
            lineModel.setDrawDate(item.getDrawDate());
//                lineModel.setId(item.getOrderItemID());
//                lineModel.setSystematic(item.getSystemTypeC());
//
//                if (item.getItemType() != null) {
//                    lineModel.setItemType(item.getItemType());
//                }

            mLineModels.add(lineModel);
        }

        if (!TextUtils.isEmpty(item.getLineD())) {
            lineModel = new LineModel();
            lineModel.setAmount(item.getPriceD());
            lineModel.setTitle("D");
            lineModel.setProductID(item.getProductID());
            if (item.getProductTypeID() == 3 && item.getProductID() == Constants.PRODUCT_KENO)
                lineModel.setLine(Utils.getKenoPlus(item.getLineD()));
            else
                lineModel.setLine(item.getLineD());
            lineModel.setType(ticketType);
            lineModel.setDrawCode(item.getDrawCode());
//                lineModel.setItemType(item.getItemType());
//                lineModel.setDrawDate(item.getDrawDate());
//                lineModel.setId(item.getOrderItemID());
//                lineModel.setSystematic(item.getSystemTypeD());
//                if (item.getItemType() != null)
//                    lineModel.setItemType(item.getItemType());
            mLineModels.add(lineModel);
        }

        if (!TextUtils.isEmpty(item.getLineE())) {
            lineModel = new LineModel();
            lineModel.setAmount(item.getPriceE());
            lineModel.setTitle("E");
            lineModel.setProductID(item.getProductID());
            if (item.getProductTypeID() == 3 && item.getProductID() == Constants.PRODUCT_KENO)
                lineModel.setLine(Utils.getKenoPlus(item.getLineE()));
            else
                lineModel.setLine(item.getLineE());
            lineModel.setType(ticketType);
            lineModel.setDrawCode(item.getDrawCode());
            lineModel.setDrawDate(item.getDrawDate());
//                lineModel.setId(item.getOrderItemID());
//                lineModel.setSystematic(item.getSystemTypeE());
//                if (item.getItemType() != null)
//                    lineModel.setItemType(item.getItemType());
            mLineModels.add(lineModel);
        }

        if (!TextUtils.isEmpty(item.getLineF())) {
            lineModel = new LineModel();
            lineModel.setAmount(item.getPriceF());
            lineModel.setTitle("F");
            lineModel.setProductID(item.getProductID());
            if (item.getProductTypeID() == 3 && item.getProductID() == Constants.PRODUCT_KENO)
                lineModel.setLine(Utils.getKenoPlus(item.getLineF()));
            else
                lineModel.setLine(item.getLineF());
            lineModel.setDrawCode(item.getDrawCode());
            lineModel.setDrawDate(item.getDrawDate());
            lineModel.setType(ticketType);
//                lineModel.setId(item.getOrderItemID());
//                lineModel.setSystematic(item.getSystemTypeF());
//                if (item.getItemType() != null)
//                    lineModel.setItemType(item.getItemType());

            mLineModels.add(lineModel);
        }
        return mLineModels;
    }

    private void capturePermission(ItemModel itemModel) {
        CharSequence info;
        boolean IsAmount = ticketType.equals(Constants.TICKET_SHOW_AMOUNT);
        CharSequence infoAfter = Utils.getInfoImageAfter(requireActivity(), mOrderModel.getName(), mOrderModel.getPIDNumber(), mOrderModel.getMobileNumber());
        List<LineModel> mLineModels = getLineModel(itemModel);
        if (IsBefore) {
            String draw = "Kỳ #" + itemModel.getDrawCode() + ", Ngày quay:" + itemModel.getDrawDate();
            info = Utils.getInfoImageBefore(requireActivity(), mLineModels, Utils.getProductName(mOrderModel.getProductID()) + systematic, draw, IsAmount);
        } else {
            info = infoAfter;
        }

        PermissionUtils.permission(PermissionConstants.CAMERA)
                .rationale(DialogHelper::showRationaleDialog)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        MediaUltis.captureImage(UploadFragment.this, info, "#" + mOrderModel.getCode(), !IsBefore);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                attemptSendMedia(data.getData().getPath());
            }
        }
    }

    @SuppressLint("CheckResult")
    private void attemptSendMedia(String path_media) {
        File file = new File(path_media);
        Uri picUri = Uri.fromFile(new File(path_media));

        Observable.fromCallable(() -> {
                    Uri uri = Uri.fromFile(new File(path_media));
//            return BitmapUtils.processingBitmap(uri, getViewContext());
                    DrawViewUtils drawViewUtils = new DrawViewUtils(getContext());
                    if (IsBefore) {
                        return drawViewUtils.processingBitmapBefore(uri,
                                Utils.getProductName(mItem.getProductID()) + systematic,
                                mLineModels,
                                mItem.getDrawDate(),
                                mItem.getDrawCode(),
                                mOrderModel.getCode());
                    } else {
                        return drawViewUtils.processingBitmapAfter(uri, mOrderModel.getName(), mOrderModel.getPIDNumber(), mOrderModel.getMobileNumber());
                    }
                }).subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map(bitmap ->
                        BitmapUtils.saveImage(bitmap, file.getParent(), "lkl" + file.getName(), Bitmap.CompressFormat.JPEG, 50)
                )
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        isSavedImage -> {
                            String path = file.getParent() + File.separator + "lkl" + file.getName();
                            if (IsBefore) {
                                ((ItemModel) adapter.getItem(mPosition)).setImgBefore(path);
                            } else
                                ((ItemModel) adapter.getItem(mPosition)).setImgAfter(path);
                            adapter.notifyDataSetChanged();

                            mPresenter.postImage(path);
                            if (file.exists())
                                file.delete();
                        },
                        onError -> Logger.e("error save image")
                );
    }

    @Override
    public void showImage(String file) {
        if (IsBefore) {
            itemModelList.get(mPosition).setImgBefore(file);
            if (mOrderModel.getProductID() == Constants.PRODUCT_MAX3D
                    || mOrderModel.getProductID() == Constants.PRODUCT_LOTO235
                    || mOrderModel.getProductID() == Constants.PRODUCT_LOTO234CAPSO
                    || mOrderModel.getProductID() == Constants.PRODUCT_LOTO636) {
                if (mPosition < (itemModelList.size() - 1)) {
                    mPosition++;
                    IsBefore = true;
                    mItem = itemModelList.get(mPosition);
                    capturePermission(mItem);
                }
            } else {
                IsBefore = false;
                capturePermission(mItem);
            }

        } else {
            itemModelList.get(mPosition).setImgAfter(file);
            if (mPosition < (itemModelList.size() - 1)) {
                mPosition++;
                IsBefore = true;
                mItem = itemModelList.get(mPosition);
                capturePermission(mItem);
            }
        }
        mCountImage++;
    }

    @Override
    public void showItems(List<ItemModel> itemModels) {
        itemModelList.clear();
        itemModelList.addAll(itemModels);
        adapter.setItems(itemModelList);
        if (itemModelList.size() > 0) {
            mItem = itemModelList.get(0);
            mPosition = 0;

            if (mItem.getProductID() == Constants.PRODUCT_KENO) {
                systematic = " - Bậc " + mItem.getSystemA();
            } else {
                if(mItem.getProductID() == Constants.PRODUCT_MAX3D_PRO){
                    if(mItem.getProductTypeID() == 2) {
                        if (mItem.getBag() == 2) {
                            systematic = " - Bao bộ số";
                        }else{
                            systematic = " - Bao " + mItem.getBag() + " số";
                        }
                    }
                }else {
                    if (mItem.getSystemA() > 6)
                        systematic = " - Bao " + mItem.getSystemA();
                    if (mItem.getSystemA() == 5)
                        systematic = " - Bao 5";
                }
            }

            capturePermission(itemModelList.get(0));
        }
    }

    @Override
    public void showChangeToImage() {
        if (mOrderModel.getProductTypeID() == 2) {
            OrderTablesImagesRequest request = new OrderTablesImagesRequest();

            List<OrderTablesItemImages> itemImages = new ArrayList<>();
            List<String> drawCode = new ArrayList<>();

            for (ItemModel itemModel : itemModelList) {
                drawCode.add(itemModel.getDrawCode());

                OrderTablesItemImages item = new OrderTablesItemImages();
                item.setOrderItemID(itemModel.getId().toString());
                item.setAfterImages(itemModel.getImgAfter());
                item.setBeforeImages(itemModel.getImgBefore());
                itemImages.add(item);
            }

            request.setDrawCode(drawCode);
            request.setItems(itemImages);
            request.setOrderCode(mOrderModel.getCode());
            request.setPointOfSaleID(employeeModel.getTerminalID());
            mPresenter.updateImageV1(request);

        } else {
            List<OrderImagesRequest> request = new ArrayList<>();

            for (ItemModel itemModel : itemModelList) {
                OrderImagesRequest item = new OrderImagesRequest();
                item.setItemID(itemModel.getId().toString());
                item.setImgAfter(itemModel.getImgAfter());
                item.setImgBefore(itemModel.getImgBefore());
                item.setOrderCode(itemModel.getOrderCode());
                request.add(item);
            }
            mPresenter.updateImages(request);
        }
    }

    void printAgain(){
        sharedPref = new SharedPref(requireActivity());
        deviceBluetooth = sharedPref.getString(Constants.BLUETOOTH_NAME, "");
        if (BluetoothServices.mState == BluetoothServices.STATE_CONNECTED) {
            IsConnectedBluetooth = true;
            mPresenter.print(mOrderModel.getCode());
        }
        else if (BluetoothServices.mState == BluetoothServices.STATE_CONNECTING)
            Toast.showToast(getContext(),"Đang kết nối bluetooth " + deviceBluetooth);
        else
            Toast.showToast(getContext(),"Kết nối bluetooth thất bại " + deviceBluetooth);
    }

    @Override
    public void showPrint(String code, PrintCommandModel printCommandModel) {
        processFragDialog = new ProcessFragDialog(requireContext());
        processFragDialog.show();

        new Thread(() -> print(printCommandModel)).start();
    }

    private void print(PrintCommandModel printCommandModel) {

        String[] printCommand = printCommandModel.getCommandText().split(",");

        for (String message : printCommand) {
            BluetoothServices.sendData(message);
            if (Constants.POSKeyArray.contains(message)) {
                try {
                    Thread.sleep(SymbolSpecial);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                if (StringUtils.isNumeric(message)) {
                    try {
                        Thread.sleep(SymbolNumber);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(SymbolBase);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        processFragDialog.dismiss();
        capturePermission(itemModelList.get(0));
    }

    @Override
    public void showPrintSuccess(String code) {
        try {
            capturePermission(itemModelList.get(0));
        } catch (Exception ignored) {

        }
    }

    @Override
    public void showOkSuccess() {
        Toast.showToast(getViewContext(), "Cập nhật thành công");
        IsSuccess = true;
        btn_print.setVisibility(View.GONE);
    }
}
