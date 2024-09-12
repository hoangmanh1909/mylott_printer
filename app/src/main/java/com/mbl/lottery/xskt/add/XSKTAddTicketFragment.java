package com.mbl.lottery.xskt.add;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.core.base.viper.ViewFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mbl.lottery.BuildConfig;
import com.mbl.lottery.R;
import com.mbl.lottery.callback.DialogCallback;
import com.mbl.lottery.dialog.DialogList;
import com.mbl.lottery.model.CustomItemModel;
import com.mbl.lottery.model.ItemViewModel;
import com.mbl.lottery.model.request.ProviderSearchRequest;
import com.mbl.lottery.model.request.XSKTAddTicketRequest;
import com.mbl.lottery.model.request.XSKTBaseV1Request;
import com.mbl.lottery.model.request.XSKTRadioSearchRequest;
import com.mbl.lottery.model.response.ProviderSearchResponse;
import com.mbl.lottery.model.response.XSKTBaseInfoResponse;
import com.mbl.lottery.model.response.XSKTRadioSearchResponse;
import com.mbl.lottery.model.response.XSKTTickeResponse;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.DialogHelper;
import com.mbl.lottery.utils.DialogUtils;
import com.mbl.lottery.utils.MediaUltis;
import com.mbl.lottery.utils.TimeUtils;
import com.mbl.lottery.utils.Toast;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class XSKTAddTicketFragment extends ViewFragment<XSKTAddTicketContract.Presenter> implements XSKTAddTicketContract.View, DatePickerDialog.OnDateSetListener {
    @BindView(R.id.tv_warehouse_name)
    TextView tv_warehouse_name;
    @BindView(R.id.tv_lock)
    TextView tv_lock;
    @BindView(R.id.tv_list_symbol)
    TextView tv_list_symbol;
    @BindView(R.id.edt_draw_date)
    TextInputEditText edt_draw_date;
    @BindView(R.id.edt_total)
    TextInputEditText edt_total;
    @BindView(R.id.edt_value)
    TextInputEditText edt_value;
    @BindView(R.id.edt_symbol_s)
    TextInputEditText edt_symbol_s;
    @BindView(R.id.edt_symbol_e)
    TextInputEditText edt_symbol_e;
    @BindView(R.id.edit_type)
    TextInputEditText edit_type;
    @BindView(R.id.edit_drawler)
    TextInputEditText edit_drawler;
    @BindView(R.id.edt_symbol)
    TextInputEditText edt_symbol;
    @BindView(R.id.edit_radio)
    TextInputEditText edit_radio;
    @BindView(R.id.edt_provider)
    TextInputEditText edt_provider;
    @BindView(R.id.chk_symbol)
    CheckBox chk_symbol;
    //    @BindView(R.id.img_date_from)
//    ImageView img_date_from;
    @BindView(R.id.img_ticket)
    public SimpleDraweeView img_ticket;
    @BindView(R.id.btn_ok)
    Button btn_ok;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_approve)
    Button btn_approve;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;
    @BindView(R.id.ll_load)
    LinearLayout ll_load;
    @BindView(R.id.til_type)
    TextInputLayout til_type;
    @BindView(R.id.til_draw_date)
    TextInputLayout til_draw_date;
    @BindView(R.id.til_radio)
    TextInputLayout til_radio;
    @BindView(R.id.til_count)
    TextInputLayout til_count;
    @BindView(R.id.til_drawler)
    TextInputLayout til_drawler;
    @BindView(R.id.til_provider)
    TextInputLayout til_provider;
    private Calendar calFrom;
    private Calendar maxStart;
    XSKTBaseInfoResponse mModel;
    XSKTTickeResponse mModelTicket;
    String mFileName = "";
    String mode = "LOAD";
    int area = 1;
    int mType = 1;
    List<XSKTRadioSearchResponse> mRadios;
    List<ProviderSearchResponse> mProviders;
    ItemViewModel mProvider;
    ItemViewModel mRadio;

    public static XSKTAddTicketFragment getInstance() {
        return new XSKTAddTicketFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xskt_add_ticket;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        calFrom = Calendar.getInstance();   // this takes current date
        calFrom.set(Calendar.DAY_OF_MONTH, 1);
        maxStart = Calendar.getInstance();
        mRadios = new ArrayList<>();
        mProviders = new ArrayList<>();
        Intent intent = requireActivity().getIntent();
        mode = intent.getStringExtra(Constants.MODE);
        area = intent.getIntExtra(Constants.AREA, 1);
        assert mode != null;

        if (area == 1) {
            til_type.setVisibility(View.GONE);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            param.setMargins(50, 0, 50, 0);
            til_count.setLayoutParams(param);
        } else if (area == 2 || area == 3) {
            ll_load.setVisibility(View.GONE);
            edit_type.setOnClickListener(v -> selectedType());
            til_type.setEndIconOnClickListener(v -> selectedType());
            edt_draw_date.setOnClickListener(v -> selectedDrawDate());
            til_draw_date.setEndIconOnClickListener(v -> selectedDrawDate());
            edit_radio.setOnClickListener(v -> selectedRadio());
            til_radio.setEndIconOnClickListener(v -> selectedRadio());
        }
        edit_drawler.setOnClickListener(v -> showQRCode());
        til_drawler.setEndIconOnClickListener(v -> showQRCode());
        edt_provider.setOnClickListener(v -> selectedProvider());
        til_provider.setEndIconOnClickListener(v -> selectedProvider());

        ProviderSearchRequest req = new ProviderSearchRequest();
        req.setArea(area);
        req.setIsLock("N");
        mPresenter.searchProvider(req);
        mPresenter.getBaseInfo("KMB043001001A1B1");

        if (mode.equals("VIEW")) {
            mModelTicket = (XSKTTickeResponse) intent.getSerializableExtra(Constants.ORDER_MODEL);
            tv_lock.setText(mModelTicket.getLockerCode());
            edit_drawler.setText(mModelTicket.getDrawlerIndex());
            edt_symbol.setText(mModelTicket.getSymbol());
            edt_draw_date.setText(mModelTicket.getDrawDate());
            edt_value.setText(mModelTicket.getValue());
            Uri imageuri = Uri.parse(BuildConfig.IMAGE_BROWSER_URL + mModelTicket.getImages());
            img_ticket.setImageURI(imageuri);
            edt_total.setText(mModelTicket.getTotal().toString());
            iv_edit.setVisibility(View.GONE);
            tv_list_symbol.setText(mModelTicket.getSymbol());
            enableMode(false);
            if (!mModelTicket.getStatus().equals("P")) {
                btn_approve.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.GONE);
            }
            btn_ok.setVisibility(View.GONE);
            ll_load.setVisibility(View.GONE);
        } else {
            tv_list_symbol.setVisibility(View.GONE);
            iv_edit.setVisibility(View.GONE);
            btn_approve.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.GONE);
        }
    }

    void selectedRadio() {
//        if (TextUtils.isEmpty(tv_drawler.getText().toString())) {
//            android.widget.Toast.makeText(getActivity(), "Vui lòng quét mã ngăn", android.widget.Toast.LENGTH_SHORT).show();
//        } else {
        ArrayList<ItemViewModel> items1 = new ArrayList<>();
        int i = 0;
        for (XSKTRadioSearchResponse item : mRadios) {
            items1.add(new ItemViewModel(String.valueOf(item.getId()), item.getName()));
            i++;
        }
        new DialogList(getContext(), "Chọn đài", items1, new DialogCallback() {
            @Override
            public void onClickItem(ItemViewModel item) {
                mRadio = item;
                edit_radio.setText(item.getText());
            }
        }).show();
//        }
    }

    void selectedDrawDate() {
        new SpinnerDatePickerDialogBuilder()
                .context(getActivity())
                .callback(this)
                .spinnerTheme(R.style.DatePickerSpinner)
                .showTitle(true)
                .showDaySpinner(true)
                .defaultDate(calFrom.get(Calendar.YEAR), calFrom.get(Calendar.MONTH), calFrom.get(Calendar.DAY_OF_MONTH))
                .maxDate(2050, 0, 1)
                .minDate(maxStart.get(Calendar.YEAR), maxStart.get(Calendar.MONTH), maxStart.get(Calendar.DAY_OF_MONTH))
                .build()
                .show();
    }

    void selectedType() {
        ArrayList<ItemViewModel> items = new ArrayList<>();
        items.add(new ItemViewModel("1", "Vé thường"));
        items.add(new ItemViewModel("2", "Vé cặp nguyên"));
        img_ticket.setImageURI("");
        new DialogList(getContext(), "Chọn loại hình vé", items, new DialogCallback() {
            @Override
            public void onClickItem(ItemViewModel item) {
                edit_type.setText(item.getText());
                if (Integer.parseInt(item.getValue()) == 1) {
                    mType = 1;
                    edt_total.setText("");
                    edt_total.setEnabled(true);
                } else if (Integer.parseInt(item.getValue()) == 2) {
                    mType = 2;
                    edt_total.setText("120");
                    edt_total.setEnabled(false);
                }
            }
        }).show();
    }

    void selectedProvider() {
//        if (TextUtils.isEmpty(tv_drawler.getText().toString())) {
//            android.widget.Toast.makeText(getActivity(), "Vui lòng quét mã ngăn", android.widget.Toast.LENGTH_SHORT).show();
//        } else {
        ArrayList<ItemViewModel> items1 = new ArrayList<>();
        int i = 0;
        for (ProviderSearchResponse item : mProviders) {
            items1.add(new ItemViewModel(String.valueOf(item.getId()), item.getName()));
            i++;
        }
        new DialogList(getContext(), "Chọn nhà cung cấp", items1, new DialogCallback() {
            @Override
            public void onClickItem(ItemViewModel item) {
                mProvider = item;
                edt_provider.setText(item.getText());
            }
        }).show();
//        }
    }

    void showQRCode() {
        mPresenter.showBarcode(value -> {
//                        try {
//                            jsonObject = new JSONObject(value);
            mPresenter.getBaseInfo(value);
//                            if (jsonObject.getString("area").equals("1")) {
//                                tv_mangan.setText(jsonObject.getString("mangan"));
//                                mPresenter.getDrawler(jsonObject.getString("mangan"));
//                                tv_value_bold.setText("");
//                                tv_nhacungcap.setText("");
//                                tv_soluong.setText("");
//                                tv_nhadai.setText("");
//                                tv_date.setText("");
//                                edt_boso.setText("");
//                                rl_camera.setVisibility(View.VISIBLE);
//                                ll_boso.setVisibility(View.GONE);
//                            } else
//                                android.widget.Toast.makeText(getContext(), "Vui lòng quét đúng mã ngăn theo khu vực", android.widget.Toast.LENGTH_LONG).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

        });
    }

    void enableMode(boolean isEnable) {
        tv_lock.setEnabled(isEnable);
        edt_symbol.setEnabled(isEnable);
        edt_draw_date.setEnabled(isEnable);
        edt_value.setEnabled(isEnable);
        edt_total.setEnabled(isEnable);
//        img_date_from.setEnabled(isEnable);
        btn_ok.setEnabled(isEnable);
        chk_symbol.setEnabled(isEnable);
    }

    void resetvalue() {
//        edt_symbol.setText("");
//        edt_draw_date.setText("");
        edt_value.setText("");
//        edt_symbol_e.setText("");
//        edt_symbol_s.setText("");
//        edt_total.setText("");
        mFileName = "";
//        chk_symbol.setChecked(false);
    }

    @OnClick({R.id.img_ticket, R.id.btn_ok, R.id.iv_back, R.id.iv_edit,
            R.id.btn_cancel, R.id.btn_approve, R.id.chk_symbol, R.id.til_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                mPresenter.back();
                break;
            case R.id.iv_edit:
                enableMode(true);
                break;
            case R.id.img_ticket:
                capturePermission();
                break;
            case R.id.btn_ok:
                onOk();
                break;
            case R.id.btn_cancel:
                DialogUtils.showOptionAction(
                        getContext(),
                        "Bạn có chắc chắn muốn hủy bộ số này không?",
                        "Đồng ý hủy",
                        "Đóng",
                        (dialog, which) -> {
                            onCancelTicket();
                            dialog.dismiss();
                        }, (dialog, which) -> {
                            dialog.dismiss();
                        });
                break;
            case R.id.btn_approve:
                DialogUtils.showOptionAction(
                        getContext(),
                        "Bạn có chắc chắn muốn duyệt bán bộ số này không?",
                        "Đồng ý",
                        "Đóng",
                        (dialog, which) -> {
                            XSKTBaseV1Request req = new XSKTBaseV1Request();
                            req.setId(mModelTicket.getId());
                            req.setStatus("O");
                            mPresenter.changeStatus(req);
                            dialog.dismiss();
                        }, (dialog, which) -> {
                            dialog.dismiss();
                        });
                break;
            case R.id.chk_symbol:
                if (chk_symbol.isChecked()) {
                    edt_symbol_s.setEnabled(false);
                    edt_symbol_e.setEnabled(false);
                } else {
                    edt_symbol_s.setEnabled(true);
                    edt_symbol_e.setEnabled(true);
                }
                break;
        }
    }

    void onCancelTicket() {
        XSKTBaseV1Request req = new XSKTBaseV1Request();
        req.setId(mModelTicket.getId());
        req.setStatus("E");
        mPresenter.changeStatus(req);
    }

    void onOk() {
        if (mModel == null) {
            Toast.showToast(getContext(), "Bạn chưa quét mã ngăn");
            return;
        }
        if (TextUtils.isEmpty(mFileName)) {
            Toast.showToast(getContext(), "Bạn chưa cập nhật ảnh vé");
            return;
        }
        if (TextUtils.isEmpty(edt_draw_date.getText())) {
            Toast.showToast(getContext(), "Bạn chưa chọn ngày xổ");
            return;
        }
        if (TextUtils.isEmpty(edt_total.getText())) {
            Toast.showToast(getContext(), "Bạn chưa nhập số lượng");
            return;
        }
        if (TextUtils.isEmpty(edt_value.getText())) {
            Toast.showToast(getContext(), "Bạn chưa nhập bộ số");
            return;
        }
        if(mProvider == null){
            Toast.showToast(getContext(), "Bạn chọn nhà cung cấp");
            return;
        }

        if(area == 1) {
            if (!chk_symbol.isChecked()) {
                if (TextUtils.isEmpty(edt_symbol_s.getText())) {
                    Toast.showToast(getContext(), "Bạn chưa nhập Số đầu");
                    return;
                }
                if (TextUtils.isEmpty(edt_symbol_e.getText())) {
                    Toast.showToast(getContext(), "Bạn chưa nhập Số cuối");
                    return;
                }
            }
            if (TextUtils.isEmpty(edt_symbol.getText())) {
                Toast.showToast(getContext(), "Bạn chưa nhập ký hiệu");
                return;
            }
        }
        XSKTAddTicketRequest req = new XSKTAddTicketRequest();
        req.setAmndID(1);
        req.setDrawDate(edt_draw_date.getText().toString());
        req.setImages(mFileName);
        req.setDrawlerID(mModel.getDrawlerID());
        req.setProviderID(Integer.parseInt(mProvider.getValue()));
        req.setType(mType);
        req.setRadioID(Integer.parseInt(mRadio.getValue()));
        req.setValue(edt_value.getText().toString());
        req.setTotal(Integer.parseInt(edt_total.getText().toString()));

        if(area == 1) {
            String sym = "";
            if (!chk_symbol.isChecked()) {
                StringBuilder symbol = new StringBuilder();
                int start = Integer.parseInt(edt_symbol_s.getText().toString());
                int end = Integer.parseInt(edt_symbol_e.getText().toString());
                for (int i = start; i <= end; i++) {
                    symbol.append(i).append(edt_symbol.getText().toString()).append(",");
                }
                sym = symbol.substring(0, symbol.length() - 1);
            } else {
                sym = edt_symbol.getText().toString();
            }
            req.setSymbol(sym);
        }
        mPresenter.addTicket(req);
    }

    @Override
    public void showImage(String file) {
        mFileName = file;
    }

    @Override
    public void showBaseInfo(XSKTBaseInfoResponse model) {
        mModel = model;
        tv_warehouse_name.setText(model.getWarehouseName());
        if (model.getProviderID() == 0) {
            ProviderSearchRequest req = new ProviderSearchRequest();
            req.setArea(area);
            req.setIsLock("N");
            mPresenter.searchProvider(req);
        } else {
            edt_provider.setText(model.getProviderName());
        }
        tv_lock.setText(model.getLockerCode());
        edit_drawler.setText(model.getDrawlerIndex());
    }

    @Override
    public void showSuccess() {
        Toast.showToast(getViewContext(), "Cập nhật thành công");
        resetvalue();
    }

    @Override
    public void showRadioByDate(List<XSKTRadioSearchResponse> radios) {
        mRadios.clear();
        mRadios.addAll(radios);
    }

    @Override
    public void showProvider(List<ProviderSearchResponse> providers) {
        mProviders.clear();
        mProviders.addAll(providers);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        calFrom.set(year, monthOfYear, dayOfMonth);
        if (calFrom.after(calFrom)) {
            calFrom.setTime(calFrom.getTime());
        }
        String drawDate = TimeUtils.convertDateToString(calFrom.getTime(), TimeUtils.DATE_FORMAT_5);
        if (area == 2 || area == 3) {
            XSKTRadioSearchRequest req = new XSKTRadioSearchRequest();
            req.setArea(area);
            req.setDrawDate(drawDate);
            mPresenter.searchRadioByDate(req);
        }
        edt_draw_date.setText(drawDate);
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

        img_ticket.setImageURI(picUri);

        mPresenter.postImage(path_media);
//        if (file.exists())
//            file.delete();
    }


    private void capturePermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            PermissionUtils.permission(PermissionConstants.CAMERA)
                    .rationale(shouldRequest -> DialogHelper.showRationaleDialog(shouldRequest))
                    .callback(new PermissionUtils.FullCallback() {
                        @Override
                        public void onGranted(List<String> permissionsGranted) {
                            LogUtils.d(permissionsGranted);
                            MediaUltis.captureImage(XSKTAddTicketFragment.this);
                        }

                        @Override
                        public void onDenied(List<String> permissionsDeniedForever,
                                             List<String> permissionsDenied) {
                            if (!permissionsDeniedForever.isEmpty()) {
                                DialogHelper.showOpenAppSettingDialog();
                            }
                            LogUtils.d(permissionsDeniedForever, permissionsDenied);
                        }
                    })
                    .request();
        } else {
            MediaUltis.captureImage(XSKTAddTicketFragment.this);
        }
    }
}