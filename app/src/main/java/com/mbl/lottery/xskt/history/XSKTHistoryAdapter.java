package com.mbl.lottery.xskt.history;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.mbl.lottery.R;
import com.mbl.lottery.delegate.RecyclerViewItemEnabler;
import com.mbl.lottery.model.response.XSKTTickeResponse;

import java.util.List;

import butterknife.BindView;

public class XSKTHistoryAdapter  extends RecyclerBaseAdapter implements RecyclerViewItemEnabler {
    Activity activity;
    List mItems;
    boolean mAllEnabled;
    public XSKTHistoryAdapter(Context context, List items) {
        super(context, items);
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public XSKTHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new XSKTHistoryAdapter.ViewHolder(inflateView(parent, R.layout.item_xskt_history));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder((XSKTHistoryAdapter.ViewHolder) holder, position);
    }
    public void setAllItemsEnabled(boolean enable) {
        mAllEnabled = enable;
        notifyItemRangeChanged(0, getItemCount());
    }
    @Override
    public boolean isAllItemsEnabled() {
        return mAllEnabled;
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_locker)
        TextView tv_locker;
        @BindView(R.id.tv_drawler)
        TextView tv_drawler;
        @BindView(R.id.tv_value)
        TextView tv_value;
        @BindView(R.id.tv_total)
        TextView tv_total;
        @BindView(R.id.tv_symbol)
        TextView tv_symbol;
        @BindView(R.id.tv_status)
        TextView tv_status;
        @BindView(R.id.tv_create_date)
        TextView tv_create_date;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            //L - ve nhap loi ,P - nhap ve, A - Da nhap Kho,
            //E - Ve Loi, S - Da ban, C - Da chuyen kho,
            //R - Da xuat hoan,  O - Da mo
            XSKTTickeResponse item = (XSKTTickeResponse)model;
            switch (item.getStatus()){
                case "P":
                    tv_status.setText("Nhập vé");
                    tv_status.setTextColor(ContextCompat.getColor(activity, R.color.blue));
                    break;
                case "O":
                    tv_status.setText("Đã mở");
                    tv_status.setTextColor(ContextCompat.getColor(activity, R.color.teal_700));
                    break;
                case "S":
                    tv_status.setTextColor(ContextCompat.getColor(activity, R.color.teal_700));
                    tv_status.setText("Đã bán");
                    break;
                case "R":
                    tv_status.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent));
                    tv_status.setText("Đã hoàn");
                    break;
            }
            tv_drawler.setText(item.getLockerCode());
            tv_value.setText(item.getValue());
            tv_total.setText(item.getRemainingTicket().toString());
            tv_symbol.setText(item.getSymbol());
            tv_create_date.setText(item.getDrawDate());
        }
    }
}