package com.mbl.lottery.xskt.detail;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.mbl.lottery.R;
import com.mbl.lottery.model.TicketXSKT;

import java.util.List;

import butterknife.BindView;

public class XSKTDetailLineAdapter  extends RecyclerBaseAdapter {
    Activity activity;

    public XSKTDetailLineAdapter(Context context, List items) {
        super(context, items);
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public XSKTDetailLineAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new XSKTDetailLineAdapter.HolderView(inflateView(parent, R.layout.item_line_xskt));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder((XSKTDetailLineAdapter.HolderView) holder, position);
    }
    public class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_drawler)
        TextView tv_drawler;
        @BindView(R.id.tv_locker)
        TextView tv_locker;
        @BindView(R.id.tv_system)
        TextView tv_system;
        @BindView(R.id.tv_line)
        TextView tv_line;
        @BindView(R.id.tv_symbol)
        TextView tv_symbol;


        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            TicketXSKT lineModel = (TicketXSKT) model;

            tv_line.setText(lineModel.getLine());
            tv_drawler.setText(lineModel.getDrawlerIndex());
            tv_locker.setText(lineModel.getLocker());
            tv_symbol.setText(lineModel.getSymbol());
            tv_system.setText(lineModel.getSystem().toString());
        }

    }
}
