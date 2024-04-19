package com.mbl.lottery.printer.together.add;

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
import com.mbl.lottery.model.LineModel;

import java.util.List;

import butterknife.BindView;

public class LineAdapter extends RecyclerBaseAdapter {

    Activity activity;

    public LineAdapter(Context context, List items) {
        super(context, items);
        activity = (Activity) context;
    }

    public void setBetType(String betType) {
        notifyDataSetChanged();
    }

    @Override
    public LineAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LineAdapter.HolderView(inflateView(parent, R.layout.item_line));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder((HolderView) holder, position);
    }

    public class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_number)
        public TextView tvNumber;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            LineModel lineModel = (LineModel) model;
            tvNumber.setText(lineModel.getLine());
            tvNumber.setVisibility(View.VISIBLE);
        }

    }
}
