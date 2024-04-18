package com.mbl.lottery.printer.loto.detail;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.mbl.lottery.BuildConfig;
import com.mbl.lottery.R;
import com.mbl.lottery.model.DrawModel;
import com.mbl.lottery.model.EmployeeModel;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.LineModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.printer.detail.DetailFragment;
import com.mbl.lottery.printer.detail.RowAdapter;
import com.mbl.lottery.printer.upload.UploadPresenter;
import com.mbl.lottery.service.BluetoothServices;
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

public class DetailLotoFragment  extends ViewFragment<DetailLotoContract.Presenter> implements DetailLotoContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_game)
    TextView tv_game;
    @BindView(R.id.tv_fullName)
    TextView tv_fullName;
    @BindView(R.id.tv_pid_number)
    TextView tv_pid_number;
    @BindView(R.id.tv_mobile_number)
    TextView tv_mobile_number;
//    @BindView(R.id.tv_amount)
//    TextView tv_amount;
    @BindView(R.id.tv_quantity)
    TextView tv_quantity;
    @BindView(R.id.tv_system)
    TextView tvSystem;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.btn_capture)
    Button btnCapture;
//    @BindView(R.id.btn_reject)
//    Button btnReject;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.ll_image_after)
    LinearLayout ll_image_after;
    @BindView(R.id.ll_image)
    LinearLayout ll_image;

    @BindView(R.id.image_before)
    public SimpleDraweeView image_before;
    @BindView(R.id.image_after)
    public SimpleDraweeView image_after;
    @BindView(R.id.share_before)
    public Button share_before;
    @BindView(R.id.share_after)
    public Button share_after;

    List<ItemModel> mItemModels;
    List<DrawModel> mDrawModels;
    List<LineModel> mLineModels;
    RowAdapter rowAdapter;
    OrderModel mOrderModel;
    //    String mFileBefore;
//    String mFileAfter;
    boolean IsBefore = true;
    String systematic = "";
    SharedPref sharedPref;
    EmployeeModel employeeModel;
boolean IsSuccess = false;
    public static DetailLotoFragment getInstance() {
        return new DetailLotoFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_loto;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        sharedPref = new SharedPref(requireActivity());
        mItemModels = new ArrayList<>();
        mLineModels = new ArrayList<>();
        mDrawModels = new ArrayList<>();

        SharedPref sharedPref = new SharedPref(getActivity());
        employeeModel = sharedPref.getEmployeeModel();


        Intent intent = requireActivity().getIntent();
        mDrawModels = (List<DrawModel>) intent.getSerializableExtra(Constants.DRAW_MODEL);
        mOrderModel = (OrderModel) intent.getSerializableExtra(Constants.ORDER_MODEL);

        if (mOrderModel != null) {
            tv_game.setText(Utils.getProductName(mOrderModel.getProductID()));
            tv_fullName.setText(mOrderModel.getName());
            tv_pid_number.setText(mOrderModel.getPIDNumber());
//            tv_amount.setText(NumberUtils.formatPriceNumber(mOrderModel.getAmount()) + "/" + mOrderModel.getQuantity());
            tv_mobile_number.setText(mOrderModel.getMobileNumber());
            tv_quantity.setText(String.valueOf(mOrderModel.getQuantity()));
            tvTitle.setText("In vé #" + mOrderModel.getCode());
        }
    }

    @OnClick({ R.id.btn_capture, R.id.btn_ok, R.id.image_before, R.id.image_after,
            R.id.iv_back, R.id.share_before, R.id.iv_copy})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_copy:
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Đã sao chép", mOrderModel.getMobileNumber());
                clipboard.setPrimaryClip(clip);
                Toast.showToast(getContext(), "Đã sao chép " + mOrderModel.getMobileNumber());
                break;
            case R.id.share_before:
                if (IsSuccess) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, BuildConfig.IMAGE_BROWSER_URL + mItemModels.get(0).getImgBefore());
                    sendIntent.setType("image/*");
                    startActivity(sendIntent);
                } else {
                    Toast.showToast(getViewContext(), "Vui lòng cập nhật ảnh vé trước khi chia sẻ ảnh");
                }
                break;
            case R.id.btn_capture:
            case R.id.image_before:
                    capturePermission(false);
                    IsBefore = true;
                break;
            case R.id.btn_ok:
                    if (mItemModels.size() > 1) {
                        new UploadPresenter((ContainerView) requireActivity(), mOrderModel, mItemModels).pushView();
                    } else {
                        ok();
                    }
                break;
            case R.id.image_after:
                    capturePermission(true);
                    IsBefore = false;

                break;
            case R.id.iv_back:
                mPresenter.back();
                break;
        }
    }

    @Override
    public void showItems(List<ItemModel> itemModels) {
        mItemModels.addAll(itemModels);
//        for (int i = 0;i< itemModels.size();i++){
//            addLine(itemModels.get(i));
//        }

        tvSystem.setText(systematic);
        if(itemModels.size() > 1){
            ll_image.setVisibility(View.GONE);
            btnCapture.setVisibility(View.GONE);
            btnOk.setText("Chụp ảnh vé");
        }
        OrderItemAdapter rowAdapter = new OrderItemAdapter(requireContext(), mItemModels);
        recycle.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycle.setNestedScrollingEnabled(false);
        recycle.setAdapter(rowAdapter);
    }

    @Override
    public void showOk() {
        Toast.showToast(getViewContext(), "Cập nhật thành công");
        IsSuccess = true;
    }

    @Override
    public void showImage(String file) {
        if(IsBefore){
            mItemModels.get(0).setImgBefore(file);
        }
        else {
            mItemModels.get(0).setImgAfter(file);
        }
        share_before.setVisibility(View.VISIBLE);
    }

    public void addLine(ItemModel item) {
        try {
            String    ticketType = Constants.TICKET_SHOW_AMOUNT;
            LineModel lineModel = new LineModel();
            if (!TextUtils.isEmpty(item.getLineA())) {
                lineModel.setAmount(item.getPriceA());
                lineModel.setTitle("A");
                lineModel.setProductID(item.getProductID());
                if (item.getProductTypeID() == 3 && item.getProductID() == Constants.PRODUCT_KENO)
                    lineModel.setLine(Utils.getKenoPlus(item.getLineA()));
                else
                    lineModel.setLine(item.getLineA());
                lineModel.setType(ticketType);
                lineModel.setDrawCode(item.getDrawCode());
                lineModel.setDrawDate(item.getDrawDate());
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

                mLineModels.add(lineModel);
            }
        } catch (Exception e) {
            Log.e("addLine", e.getMessage());
        }
    }

    private void ok() {
//        if ((mOrderModel.getProductTypeID() == 1 ||
//                mOrderModel.getProductTypeID() == 3) && mOrderModel.getQuantity() > 1) {
            ItemModel _item = mItemModels.get(0);
            if (!TextUtils.isEmpty(_item.getImgBefore())) {
                OrderImagesRequest item = new OrderImagesRequest();
                item.setImgAfter(_item.getImgBefore());
                item.setImgBefore(_item.getImgBefore());
                item.setOrderCode(mOrderModel.getCode());
                mPresenter.updateImageKeno(item);
            } else {
                Toast.showToast(getViewContext(), "Bạn chưa cập nhật ảnh!");
            }
//        } else {
//            boolean isCheck = true;
//            List<OrderImagesRequest> request = new ArrayList<>();
//
//            for (ItemModel _item : mItemModels) {
//                if (TextUtils.isEmpty(_item.getImgBefore())) {
//                    isCheck = false;
//                    break;
//                }
//                OrderImagesRequest item = new OrderImagesRequest();
//                item.setItemID(_item.getId().toString());
//                item.setImgBefore(_item.getImgBefore());
//                item.setOrderCode(mOrderModel.getCode());
//                request.add(item);
//            }
//            if (isCheck)
//                mPresenter.updateImages(request);
//            else
//                Toast.showToast(getViewContext(), "Bạn chưa cập nhật đủ ảnh!");
//        }
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
//                if (!TextUtils.isEmpty(systematic))
//                    systematic = " - " + systematic;
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
                        return drawViewUtils.processingBitmapBefore(uri,
                                Utils.getProductName(mOrderModel.getProductID()) + " " + systematic,
                                mLineModels,
                                drawDate,
                                drawCode,
                                mOrderModel.getCode());
                    } else {
                        return drawViewUtils.processingBitmapAfter(uri, mOrderModel.getName(), mOrderModel.getPIDNumber(), mOrderModel.getMobileNumber());
                    }
                }).subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map(bitmap ->
                        BitmapUtils.saveImage(bitmap, file.getParent(), "lkl_" + file.getName(), Bitmap.CompressFormat.JPEG, 50)
                )
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        isSavedImage -> {
                            String path = file.getParent() + File.separator + "lkl_" + file.getName();

                            mPresenter.postImage(path);
                            if (file.exists())
                                file.delete();
                        },
                        onError -> Logger.e("error save image")
                );
    }


    private void capturePermission(boolean IsAfter) {
        CharSequence info;
        boolean IsAmount = true;
        CharSequence infoAfter = Utils.getInfoImageAfter(requireActivity(), mOrderModel.getName(), mOrderModel.getPIDNumber(), mOrderModel.getMobileNumber());

        if (!IsAfter) {
            List<LineModel> lineModels = new ArrayList<>();
            String draw = "";
//            for (LineModel lineModel : mLineModels) {
//                LineModel lineModel1 = new LineModel();
//                String[] arrNumber = lineModel.getLine().split(",");
//                List<String> stringList = new ArrayList<>();
//                for (String s : arrNumber) {
//                    stringList.add(StringUtils.leftPad(StringUtils.trim(s), 2, '0'));
//                }
//                lineModel1.setLine(lineModel.getTitle() + ": " + StringUtils.join(stringList, " "));
//                lineModel1.setAmount(lineModel.getAmount());
//                lineModel1.setType(ticketType);
//                lineModels.add(lineModel1);
//            }
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
//            if (!TextUtils.isEmpty(systematic))
//                systematic = " - " + systematic;
            info = Utils.getInfoImageBefore(requireActivity(), mLineModels, Utils.getProductName(mOrderModel.getProductID()) + " " + systematic, draw, IsAmount);
        } else {
            info = infoAfter;
        }

        PermissionUtils.permission(PermissionConstants.CAMERA)
                .rationale(DialogHelper::showRationaleDialog)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        MediaUltis.captureImage(DetailLotoFragment.this, info, "#" + mOrderModel.getCode(), IsAfter);
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

}
