package com.mbl.lottery.printer.topup;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.mbl.lottery.R;
import com.mbl.lottery.model.EmployeeModel;
import com.mbl.lottery.model.SmsModel;
import com.mbl.lottery.model.TopupAddRequest;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.SharedPref;
import com.mbl.lottery.utils.Toast;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class TopupFragment extends ViewFragment<TopupContract.Presenter> implements TopupContract.View {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    TopupAdapter mAdapter;
    List<SmsModel> mSmsModels;
    EmployeeModel employeeModel;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topup;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        SharedPref sharedPref = new SharedPref(getActivity());
        employeeModel = sharedPref.getEmployeeModel();

        try {
            mSmsModels = getAllSms();
            recycle.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new TopupAdapter(getContext(), mSmsModels) {
                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    ((ViewHolder) holder).btn_ok.setOnClickListener(view -> {
                        SmsModel item = mSmsModels.get(position);
                        if (item.getMsg().toUpperCase().contains("NAP")) {
                            String msg = item.getMsg();
                            String mobile = msg.substring(msg.toUpperCase().indexOf("NAP") + 3, msg.toUpperCase().indexOf("NAP") + 13).trim();
                            String amount = "0";
                            if (item.getAddress().toUpperCase().equals("TECHCOMBANK")) {
                                try {
                                    amount = msg.substring(msg.toUpperCase().indexOf("+") + 1, msg.toUpperCase().indexOf("SO DU")).trim();
                                } catch (Exception ex) {
                                }
                            } else {
                                try {
                                    amount = msg.substring(msg.toUpperCase().indexOf("+") + 1, msg.toUpperCase().indexOf("VND")).trim();
                                } catch (Exception ex) {

                                }
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Xác nhận nạp tiền");
                            builder.setMessage("Bạn có chắc chắn nạp tiền cho số điện thoại " + mobile + ", Với số tiền " + amount);
                            String finalAmount = amount.replaceAll(",", "");
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User taps OK button.

                                    TopupAddRequest request = new TopupAddRequest();
                                    request.setAmount(Integer.parseInt(finalAmount));
                                    request.setMessage(msg);
                                    request.setEmpID(employeeModel.getiD());
                                    request.setMobileNumber(mobile);
                                    request.setRefCode(item.getId() + item.getTime());
                                    mPresenter.topupAdd(request);
                                }
                            });
//                            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    // User cancels the dialog.
//                                }
//                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }
            };
            recycle.setAdapter(mAdapter);
        } catch (Exception e) {
        }
    }

    public List<SmsModel> getAllSms() {
        List<SmsModel> lstSms = new ArrayList<SmsModel>();
        SmsModel objSms = new SmsModel();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = getActivity().getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        getActivity().startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                try {
                    long unixTime = System.currentTimeMillis();
                    long smsDate = Long.parseLong(c.getString(c.getColumnIndexOrThrow("date")));
                    Calendar date1 = Calendar.getInstance();
                    date1.setTimeInMillis(unixTime);

                    Calendar date2 = Calendar.getInstance();
                    date2.setTimeInMillis(smsDate);

                    long days = ChronoUnit.DAYS.between(date1.toInstant(), date2.toInstant());
                    //TECHCOMBANK
                    if ((c.getString(c.getColumnIndexOrThrow("address")).toUpperCase().equals("TECHCOMBANK")) || (c.getString(c.getColumnIndexOrThrow("address")).toUpperCase().equals("VIETCOMBANK"))
                            && c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                        objSms = new SmsModel();
                        objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                        objSms.setAddress(c.getString(c
                                .getColumnIndexOrThrow("address")));
                        objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                        objSms.setReadState(c.getString(c.getColumnIndexOrThrow("read")));

                        objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
//                    if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
//                        objSms.setFolderName("inbox");
//                    } else {
//                        objSms.setFolderName("sent");
//                    }

                        lstSms.add(objSms);
                        c.moveToNext();
                    }
                } catch (Exception exception) {
                    Log.i("TopupFragment", exception.getMessage());
                }
            }

        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        c.close();

        return lstSms;
    }


    public static TopupFragment getInstance() {
        return new TopupFragment();
    }

    @Override
    public void showSuccess() {
        Toast.showToast(getContext(), "Nạp tiền thành công");
    }
}
