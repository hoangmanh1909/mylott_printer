package com.mbl.lottery.printer.topup;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.mbl.lottery.R;
import com.mbl.lottery.delegate.RecyclerViewItemEnabler;
import com.mbl.lottery.model.SmsModel;

import java.util.List;

import butterknife.BindView;

public class TopupAdapter  extends RecyclerBaseAdapter implements RecyclerViewItemEnabler {
    Activity activity;
    boolean mAllEnabled;
    public TopupAdapter(Context context, List items) {
        super(context,items);
        activity = (Activity) context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopupAdapter.ViewHolder(inflateView(parent, R.layout.item_topup));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        super.onBindViewHolder((TopupAdapter.ViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public boolean isAllItemsEnabled() {
        return false;
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_address)
        TextView tv_address;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.btn_ok)
        public Button btn_ok;

        public ViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        public void bindView(Object model, int position) {
            SmsModel item = (SmsModel) model;
            tv_address.setText(item.getAddress());
            tv_date.setText(item.getId());
            tv_content.setText(item.getMsg());
        }
    }
}
