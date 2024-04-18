package com.mbl.lottery.printer.detail.outOfNumber;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.mbl.lottery.R;
import com.mbl.lottery.model.LineModel;
import com.mbl.lottery.printer.detail.NumberAdapter;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class OutOfNumberAdapter extends RecyclerBaseAdapter {

    Activity activity;

    public OutOfNumberAdapter(Context context, List items) {
        super(context, items);
        activity = (Activity) context;
    }

    @Override
    public OutOfNumberAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OutOfNumberAdapter.HolderView(inflateView(parent, R.layout.item_out_of_number));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder((OutOfNumberAdapter.HolderView) holder, position);
    }

    public List<LineModel> getItemSelected() {
        List<LineModel> items = getItems();
        return FluentIterable.from(items).filter(new Predicate<LineModel>() {
            @Override
            public boolean apply(LineModel input) {
                return input.isSelected();
            }
        }).toList();
    }

    public class HolderView extends BaseViewHolder {

        @BindView(R.id.checkbox)
        public CheckBox checkBox;

        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_draw)
        TextView tvDraw;
        @BindView(R.id.recycle)
        RecyclerView recycle;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            LineModel lineModel = (LineModel)model;
            List<String> listNumber=  Arrays.asList(StringUtils.trim(lineModel.getLine()).split(","));

            tvDate.setText("Ngày: " + lineModel.getDrawDate());
            tvDraw.setText("Kỳ: " + lineModel.getDrawCode());

            recycle.setLayoutManager(new FlexboxLayoutManager(mContext));
            NumberAdapter adapter = new NumberAdapter(mContext,listNumber);
            recycle.setAdapter(adapter);
        }
    }
}
