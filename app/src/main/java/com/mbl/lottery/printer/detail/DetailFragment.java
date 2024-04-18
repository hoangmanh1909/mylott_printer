package com.mbl.lottery.printer.detail;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.mbl.lottery.dialog.ProcessFragDialog;
import com.mbl.lottery.model.DrawModel;
import com.mbl.lottery.model.EmployeeModel;
import com.mbl.lottery.model.ImageModel;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.LineModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.PrintCommandModel;
import com.mbl.lottery.model.request.FinishOrderKenoRequest;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.printer.detail.outOfNumber.OutOfNumberPresenter;
import com.mbl.lottery.printer.upload.UploadPresenter;
import com.mbl.lottery.service.BluetoothServices;
import com.mbl.lottery.utils.BitmapUtils;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.DateTimeUtils;
import com.mbl.lottery.utils.DialogHelper;
import com.mbl.lottery.utils.DrawViewUtils;
import com.mbl.lottery.utils.MediaUltis;
import com.mbl.lottery.utils.NumberUtils;
import com.mbl.lottery.utils.SharedPref;
import com.mbl.lottery.utils.Toast;
import com.mbl.lottery.utils.Utils;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailFragment extends ViewFragment<DetailContract.Presenter> implements DetailContract.View {
    @BindView(R.id.tv_bluetooth)
    TextView tvBluetooth;
    @BindView(R.id.iv_type_ticket)
    ImageView img_logo;
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
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_quantity)
    TextView tv_quantity;
    @BindView(R.id.tv_system)
    TextView tvSystem;
    @BindView(R.id.tv_draw_code)
    TextView tv_draw_code;
    @BindView(R.id.tv_draw)
    TextView tv_draw;
    @BindView(R.id.tv_time)
    TextView tv_time;

    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.btn_print)
    Button btnPrint;
    @BindView(R.id.btn_capture)
    Button btnCapture;
    @BindView(R.id.btn_reject)
    Button btnReject;
    @BindView(R.id.rl_keno)
    RelativeLayout rl_keno;
    @BindView(R.id.btn_printed)
    Button btnPrinted;

    @BindView(R.id.btnVe1)
    Button btnVe1;
    @BindView(R.id.btnVe2)
    Button btnVe2;
    @BindView(R.id.btnVe3)
    Button btnVe3;
    @BindView(R.id.btnVe4)
    Button btnVe4;
    @BindView(R.id.btnVe5)
    Button btnVe5;
    @BindView(R.id.btnVe6)
    Button btnVe6;

    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.ll_image_after)
    LinearLayout ll_image_after;
    @BindView(R.id.ll_image)
    LinearLayout ll_image;
    @BindView(R.id.ll_bag)
    LinearLayout ll_bag;

    @BindView(R.id.image_before)
    public SimpleDraweeView image_before;
    @BindView(R.id.image_after)
    public SimpleDraweeView image_after;
    @BindView(R.id.share_before)
    public Button share_before;
    @BindView(R.id.share_after)
    public Button share_after;
    boolean IsConnectedBluetooth = false;
    String deviceBluetooth = "";

    List<ItemModel> mItemModels;
    List<DrawModel> mDrawModels;

    List<LineModel> mLineModels;
    RowAdapter rowAdapter;

    ProcessFragDialog processFragDialog;
    long SymbolSpecial = Constants.SymbolSpecial;
    long SymbolBase = Constants.SymbolBase;
    long SymbolNumber = Constants.SymbolNumber;

    boolean IsPrint = false;
    String ticketType = Constants.TICKET_NO_AMOUNT;
    boolean IsBefore = true;
    OrderModel mOrderModel;
    //    String mFileBefore;
//    String mFileAfter;
    String systematic = "";

    EmployeeModel employeeModel;
    boolean IsKenoPlus = false;
    SharedPref sharedPref;
    CountDownTimer cdt;
    int diffPrintSecond = 0;
    Date serverTime;
    int mIndexItem = 0;
    boolean IsSuccess = false;

    public static DetailFragment getInstance() {
        return new DetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_printer_detail;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        sharedPref = new SharedPref(requireActivity());
        mItemModels = new ArrayList<>();
        mLineModels = new ArrayList<>();
        mDrawModels = new ArrayList<>();

        SharedPref sharedPref = new SharedPref(getActivity());
        deviceBluetooth = sharedPref.getString(Constants.BLUETOOTH_NAME, "");
        employeeModel = sharedPref.getEmployeeModel();

        if (BluetoothServices.mState == BluetoothServices.STATE_CONNECTED) {
            IsConnectedBluetooth = true;
            tvBluetooth.setText("Đã kết nối " + deviceBluetooth);
        } else if (BluetoothServices.mState == BluetoothServices.STATE_CONNECTING)
            tvBluetooth.setText("Đang kết nối " + deviceBluetooth);
        else
            tvBluetooth.setText("Kết nối thất bại " + deviceBluetooth);

        Intent intent = requireActivity().getIntent();
        mDrawModels = (List<DrawModel>) intent.getSerializableExtra(Constants.DRAW_MODEL);
        mOrderModel = (OrderModel) intent.getSerializableExtra(Constants.ORDER_MODEL);

        if (mOrderModel != null) {
            //mOrderModel = mPresenter.getOrderModel();
            switch (mOrderModel.getProductID()) {
                case Constants.PRODUCT_MEGA:
                    img_logo.setImageResource(R.drawable.home_mega);
                    break;
                case Constants.PRODUCT_MAX3D:
                    img_logo.setImageResource(R.drawable.home_max3d);
                    ticketType = Constants.TICKET_SHOW_AMOUNT;
                    break;
                case Constants.PRODUCT_MAX3D_PLUS:
                    img_logo.setImageResource(R.drawable.home_max3d_plus);
                    ticketType = Constants.TICKET_SHOW_AMOUNT;
                    break;
                case Constants.PRODUCT_MAX3D_PRO:
                    img_logo.setImageResource(R.drawable.logomax3dpro);
                    ticketType = Constants.TICKET_SHOW_AMOUNT;
                    break;
                case Constants.PRODUCT_POWER:
                    img_logo.setImageResource(R.drawable.home_power);
                    break;
                case Constants.PRODUCT_KENO:
                    img_logo.setImageResource(R.drawable.home_keno);
                    ticketType = Constants.TICKET_SHOW_AMOUNT;
                    break;
            }

            tv_game.setText(Utils.getProductName(mOrderModel.getProductID()));
            tv_fullName.setText(mOrderModel.getName());
            tv_pid_number.setText(mOrderModel.getPIDNumber());
            tv_amount.setText(NumberUtils.formatPriceNumber(mOrderModel.getAmount()) + "/" + mOrderModel.getQuantity());
            tv_mobile_number.setText(mOrderModel.getMobileNumber());
            tv_quantity.setText(String.valueOf(mOrderModel.getQuantity()));
            tvTitle.setText("In vé #" + mOrderModel.getCode());

            rl_keno.setVisibility(View.GONE);
            if (mOrderModel.getProductID() == Constants.PRODUCT_KENO || mOrderModel.getProductID() == Constants.PRODUCT_MAX3D) {
                ll_image_after.setVisibility(View.GONE);
                if (mOrderModel.getProductID() == Constants.PRODUCT_KENO) {
                    btnReject.setEnabled(false);
                    rl_keno.setVisibility(View.VISIBLE);
//                    btnPrinted.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void showTimeNow(String timeNow) {
        serverTime = DateTimeUtils.convertStringToDateDefault(timeNow);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                serverTime = DateUtils.addSeconds(serverTime, 1);
                //Log.d("Timer", DateTimeUtils.convertDateToString(serverTime, ""));
            }
        };
        long delay = 1000L;
        Timer timer = new Timer("ServerTimer");
        timer.schedule(timerTask, 0, delay);
        if (mPresenter != null)
            mPresenter.getItemByCode();
    }

    @Override
    public void showItem(List<ItemModel> itemModels) {
        mItemModels.addAll(itemModels);
        IsKenoPlus = false;
        mIndexItem = 0;
        if (itemModels.get(0).getProductID() == Constants.PRODUCT_KENO) {
            mDrawModels = mPresenter.getDrawModels();
            findCurrentDraw();
            if (itemModels.get(0).getProductTypeID() == 3) {
                IsKenoPlus = true;
                systematic = "Chẵn lẻ - Lớn nhỏ";
            } else {
                systematic = "Bậc " + itemModels.get(0).getSystemA();
            }
        } else {
            if (itemModels.get(0).getSystemA() > 6)
                systematic = "Bao " + itemModels.get(0).getSystemA();
            if (itemModels.get(0).getSystemA() == 5)
                systematic = "Bao 5";
        }
        tvSystem.setText(systematic);
        tv_draw_code.setText(String.valueOf(itemModels.size()));

        addLine(itemModels.get(0));

        rowAdapter = new RowAdapter(requireContext(), mLineModels);
        recycle.setLayoutManager(new FlexboxLayoutManager(requireContext()));
        recycle.setAdapter(rowAdapter);

        if (itemModels.size() > 1 && (mOrderModel.getProductTypeID() == 2 || mOrderModel.getProductTypeID() == 4)) {
            ll_bag.setVisibility(View.VISIBLE);
            if (itemModels.size() < 3) {
                btnVe3.setVisibility(View.GONE);
                btnVe4.setVisibility(View.GONE);
                btnVe5.setVisibility(View.GONE);
                btnVe6.setVisibility(View.GONE);
            } else if (itemModels.size() < 4) {
                btnVe4.setVisibility(View.GONE);
                btnVe5.setVisibility(View.GONE);
                btnVe6.setVisibility(View.GONE);
            } else if (itemModels.size() < 5) {
                btnVe5.setVisibility(View.GONE);
                btnVe6.setVisibility(View.GONE);
            } else if (itemModels.size() < 6) {
                btnVe6.setVisibility(View.GONE);
            }
        } else {
            ll_bag.setVisibility(View.GONE);
        }
    }

    public void addLine(ItemModel item) {
        try {
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
        } catch (Exception e) {
            Log.e("addLine", e.getMessage());
        }
    }

    @OnClick({R.id.btn_print, R.id.btn_capture, R.id.btn_ok, R.id.image_before, R.id.image_after, R.id.iv_back, R.id.btn_reject, R.id.btn_printed,
            R.id.btnVe1, R.id.btnVe2, R.id.btnVe3, R.id.btnVe4, R.id.btnVe5, R.id.btnVe6, R.id.share_before, R.id.iv_copy})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_copy:
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Đã sao chép", mOrderModel.getMobileNumber());
                clipboard.setPrimaryClip(clip);
                Toast.showToast(getContext(), "Đã sao chép " + mOrderModel.getMobileNumber());
                break;
            case R.id.btn_print:
                printTicket();
                btnPrinted.setEnabled(false);
                break;
            case R.id.btn_printed:
                printed();
                break;
            case R.id.share_before:
                if (IsSuccess) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, BuildConfig.IMAGE_BROWSER_URL + mItemModels.get(mIndexItem).getImgBefore());
                    sendIntent.setType("image/*");
                    startActivity(sendIntent);
                } else {
                    Toast.showToast(getViewContext(), "Vui lòng cập nhật ảnh vé trước khi chia sẻ ảnh");
                }
                break;
            case R.id.btn_capture:
            case R.id.image_before:
                if (IsPrint) {
                    capturePermission(false);
                    IsBefore = true;
                }
                break;
            case R.id.btn_ok:
                if (mOrderModel.getProductID() == Constants.PRODUCT_KENO) {
                    ok();
                } else {
                    if (mItemModels.size() > 1) {
                        new UploadPresenter((ContainerView) requireActivity(), mOrderModel, mItemModels).pushView();
                    } else {
                        ok1();
                    }
                }
                break;
            case R.id.image_after:
                if (IsPrint) {
                    capturePermission(true);
                    IsBefore = false;
                }
                break;
            case R.id.iv_back:
                mPresenter.back();
                break;
            case R.id.btn_reject:
                reject();
                break;
            case R.id.btnVe1:
                mIndexItem = 0;
                initRow();
                break;
            case R.id.btnVe2:
                mIndexItem = 1;
                initRow();
                break;
            case R.id.btnVe3:
                mIndexItem = 2;
                initRow();
                break;
            case R.id.btnVe4:
                mIndexItem = 3;
                initRow();
                break;
            case R.id.btnVe5:
                mIndexItem = 4;
                initRow();
                break;
            case R.id.btnVe6:
                mIndexItem = 5;
                initRow();
                break;
        }
    }

    private void printTicket() {
        if (mOrderModel.getProductID() == Constants.PRODUCT_KENO) {
            DrawModel currentDrawModel = null;
            for (DrawModel drawModel : mDrawModels) {
                if (drawModel.getDrawCode().equals(mItemModels.get(0).getDrawCode())) {
                    currentDrawModel = drawModel;
                    break;
                }
            }
            if (currentDrawModel == null) {
                Toast.showToast(getViewContext(), "Đơn hàng hết thời gian mở bán");
                return;
            }

            Date drawDateTime = DateTimeUtils.convertStringToDateDefault(currentDrawModel.getDrawDate() + " " + currentDrawModel.getDrawTime());

            if (DateTimeUtils.compareToDay(serverTime, DateUtils.addMinutes(DateUtils.addSeconds(drawDateTime, diffPrintSecond), -8)) < 0) {
                Toast.showToast(getViewContext(), "Đơn hàng chưa đến thời gian mở bán");
                return;
            }
            if (DateTimeUtils.compareToDay(serverTime, DateUtils.addSeconds(drawDateTime, -diffPrintSecond)) > 0) {
                Toast.showToast(getViewContext(), "Đơn hàng hết thời gian mở bán");
                return;
            }
        }
        if (mOrderModel.getProductTypeID() == 2 || mOrderModel.getProductTypeID() == 4) {
            List<ItemModel> _items = new ArrayList<>();
            _items.add(mItemModels.get(mIndexItem));
            mPresenter.print(_items);
        } else {
            mPresenter.print(mItemModels);
        }

    }

    private void printed() {
//        if (mOrderModel.getProductID() == Constants.PRODUCT_KENO) {
//            DrawModel currentDrawModel = null;
//            for (DrawModel drawModel : mDrawModels) {
//                if (drawModel.getDrawCode().equals(mItemModels.get(0).getDrawCode())) {
//                    currentDrawModel = drawModel;
//                    break;
//                }
//            }
//            if (currentDrawModel == null) {
//                Toast.showToast(getViewContext(), "Đơn hàng hết thời gian mở bán");
//                return;
//            }
//
//            Date drawDateTime = DateTimeUtils.convertStringToDateDefault(currentDrawModel.getDrawDate() + " " + currentDrawModel.getDrawTime());
//
//            if (DateTimeUtils.compareToDay(serverTime, DateUtils.addMinutes(DateUtils.addSeconds(drawDateTime, diffPrintSecond), -8)) < 0) {
//                Toast.showToast(getViewContext(), "Đơn hàng chưa đến thời gian mở bán");
//                return;
//            }
//            if (DateTimeUtils.compareToDay(serverTime, DateUtils.addSeconds(drawDateTime, -diffPrintSecond)) > 0) {
//                Toast.showToast(getViewContext(), "Đơn hàng hết thời gian mở bán");
//                return;
//            }
//        }

        btnPrint.setEnabled(false);
        btnReject.setEnabled(false);
        IsPrint = true;
        btnPrint.setEnabled(false);
        btnCapture.setEnabled(true);
        btnReject.setEnabled(false);
    }

    private void reject() {
        new OutOfNumberPresenter((ContainerView) requireActivity(), mLineModels, mOrderModel.getCode()).pushView();
    }

    private void ok() {
        if ((mOrderModel.getProductTypeID() == 1 || mOrderModel.getProductTypeID() == 3) && mOrderModel.getQuantity() > 1) {
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
        } else {
            boolean isCheck = true;
            List<OrderImagesRequest> request = new ArrayList<>();

            for (ItemModel _item : mItemModels) {
                if (TextUtils.isEmpty(_item.getImgBefore())) {
                    isCheck = false;
                    break;
                }
                OrderImagesRequest item = new OrderImagesRequest();
                item.setItemID(_item.getId().toString());
                item.setImgBefore(_item.getImgBefore());
                item.setOrderCode(mOrderModel.getCode());
                request.add(item);
            }
            if (isCheck)
                mPresenter.updateImages(request);
            else
                Toast.showToast(getViewContext(), "Bạn chưa cập nhật đủ ảnh!");
        }
    }

    private void ok1() {
//        if (!TextUtils.isEmpty(mFileBefore)) {
//            if (!TextUtils.isEmpty(mFileAfter) || mOrderModel.getProductID() == Constants.PRODUCT_MAX3D) {
//                mPresenter.changeToImage(mOrderModel.getCode());
//            } else {
//                Toast.showToast(getViewContext(), "Bạn chưa cập nhật ảnh mặt sau!");
//            }
//        } else {
//            Toast.showToast(getViewContext(), "Bạn chưa cập nhật ảnh mặt trước!");
//        }
    }

    @Override
    public void showPrint(String orderCode, PrintCommandModel printCommandModel) {
        processFragDialog = new ProcessFragDialog(requireContext());
        processFragDialog.show();
        IsPrint = true;
        btnPrint.setEnabled(false);
        if (mItemModels.size() == 1 || mOrderModel.getProductID() == Constants.PRODUCT_KENO) {
            btnCapture.setEnabled(true);
        } else {
            btnOk.setEnabled(true);
        }
        btnReject.setEnabled(false);
        if (IsKenoPlus)
            new Thread(() -> printKenoPlus(printCommandModel)).start();
        else
            new Thread(() -> print(printCommandModel)).start();
    }

    @Override
    public void showImage(String file) {
        mItemModels.get(mIndexItem).setImgBefore(file);
        if (mOrderModel.getProductTypeID() == 2 || mOrderModel.getProductTypeID() == 4) {
            if (mIndexItem == mItemModels.size() - 1) {
                btnOk.setEnabled(true);
            } else {
                mIndexItem++;
                initRow();
            }
        } else {
            btnOk.setEnabled(true);
        }
        share_before.setVisibility(View.VISIBLE);
    }

    @Override
    public void showOk() {
        Toast.showToast(getViewContext(), "Cập nhật thành công");
        IsSuccess = true;
        btnOk.setEnabled(false);
        btnPrint.setEnabled(false);
        btnPrinted.setEnabled(false);
        btnCapture.setEnabled(false);
//        new Handler().postDelayed(() -> {
//            mPresenter.back();
//        }, 2000);
    }

    void initRow() {
        changeBackground();
        btnPrint.setEnabled(true);
        btnPrinted.setEnabled(true);
        btnCapture.setEnabled(false);
        btnOk.setEnabled(false);
        mLineModels.clear();
        if (!TextUtils.isEmpty(mItemModels.get(mIndexItem).getImgBefore())) {
            Uri imageuri = Uri.parse(BuildConfig.IMAGE_BROWSER_URL + mItemModels.get(mIndexItem).getImgBefore());
            image_before.setImageURI(imageuri);
//            Picasso
//                    .get()
//                    .load(BuildConfig.IMAGE_URL + mItemModels.get(mIndexItem).getImgBefore())
//                    .into(image_before);
        } else {
            image_before.setImageResource(R.drawable.ic_baseline_camera_alt_24);
        }
        addLine(mItemModels.get(mIndexItem));
        rowAdapter.setItems(mLineModels);
    }

    private void changeBackground() {
        switch (mIndexItem) {
            case 0:
                btnVe1.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                btnVe2.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe3.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe4.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe5.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe6.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                break;
            case 1:
                btnVe1.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe2.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                btnVe3.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe4.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe5.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe6.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                break;
            case 2:
                btnVe1.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe2.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe3.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                btnVe4.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe5.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe6.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                break;
            case 3:
                btnVe1.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe2.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe3.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe4.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                btnVe5.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe6.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                break;
            case 4:
                btnVe1.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe2.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe3.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe4.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe5.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                btnVe6.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                break;
            case 5:
                btnVe1.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe2.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe3.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe4.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe5.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.gray));
                btnVe6.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                break;
        }
    }

    @Override
    public void showSuccessImage() {
        OrderImagesRequest request = new OrderImagesRequest();

        request.setItemID(mItemModels.get(0).getId().toString());
        request.setOrderCode(mOrderModel.getCode());

        mPresenter.updateImage(request);
    }

    private void printKenoPlus(PrintCommandModel printCommandModel) {

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
                        Thread.sleep(SymbolSpecial);
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
        capturePermission(false);
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
        if (mOrderModel.getProductID() == Constants.PRODUCT_KENO || (mItemModels.size() == 1 && mOrderModel.getProductID() == Constants.PRODUCT_MAX3D)) {
            capturePermission(false);
        }
//        else {
//            new UploadPresenter((ContainerView) requireActivity(), mOrderModel, mItemModels, mPrintCode).pushView();
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
                            String type;
                            if (IsBefore)
                                type = Constants.IMAGE_BEFORE;
                            else
                                type = Constants.IMAGE_AFTER;

                            mPresenter.postImage(path, type);
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
                        MediaUltis.captureImage(DetailFragment.this, info, "#" + mOrderModel.getCode(), IsAfter);
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

    private void findCurrentDraw() {
        Date drawDateTime = null;

        DrawModel currentDrawModel = null;
        for (DrawModel drawModel : mDrawModels) {
            try {
                drawDateTime = DateTimeUtils.convertStringToDateDefault(drawModel.getDrawDate() + " " + drawModel.getDrawTime());
                if (DateTimeUtils.compareToDay(serverTime, drawDateTime) < 0
                        && DateTimeUtils.compareToDay(DateUtils.addMinutes(serverTime, 10), drawDateTime) >= 0) {
                    currentDrawModel = drawModel;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (currentDrawModel != null) {
            diffPrintSecond = Integer.parseInt(Constants.KEY_DIFF_PRINT_SECOND);
            assert drawDateTime != null;
            Date fromPrintTime = DateUtils.addSeconds(DateUtils.addMinutes(drawDateTime, -8), diffPrintSecond);
            Date toPrintTime = DateUtils.addSeconds(drawDateTime, -diffPrintSecond);
            String printTime = DateTimeUtils.convertDateToString(fromPrintTime, DateTimeUtils.SIMPLE_TIME_FORMAT) + " - " + DateTimeUtils.convertDateToString(toPrintTime, DateTimeUtils.SIMPLE_TIME_FORMAT);
            tv_draw.setText("#" + currentDrawModel.getDrawCode());
            tv_time.setText(printTime);
            long diff = drawDateTime.getTime() - serverTime.getTime();
            CountdownKeno(diff);
        } else {
            if (cdt != null)
                cdt.cancel();
            tv_draw.setText("#");
            tv_time.setText("Hết thời gian in vé");
        }
    }

    private void CountdownKeno(long diff) {
        cdt = new CountDownTimer(diff, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                btnPrint.setText("IN VÉ " + mIndexItem + " " + StringUtils.leftPad(String.valueOf(minutes), 2, '0') + ":" + StringUtils.leftPad(String.valueOf(seconds), 2, '0'));
            }

            @Override
            public void onFinish() {
                cancel();
            }
        };
        cdt.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (cdt != null)
            cdt.cancel();
    }
}
