package com.mbl.lottery.printer.loto.detail;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.mbl.lottery.R;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.LineModel;
import com.mbl.lottery.printer.detail.RowAdapter;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.NumberUtils;
import com.mbl.lottery.utils.Utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class OrderItemAdapter extends RecyclerBaseAdapter {

    Activity activity;

    public OrderItemAdapter(Context context, List items) {
        super(context, items);
        activity = (Activity) context;
    }

    @Override
    public OrderItemAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderItemAdapter.HolderView(inflateView(parent, R.layout.item_order_item));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder((OrderItemAdapter.HolderView) holder, position);
    }

    public class HolderView extends BaseViewHolder {

        @BindView(R.id.recycle)
        RecyclerView recycle;
        @BindView(R.id.tv_title)
        TextView tv_title;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {

            ItemModel item = (ItemModel)model;
            List<LineModel> mLineModels = addLine(item);
            tv_title.setText("Vé số: " + (position + 1));

            RowAdapter rowAdapter; rowAdapter = new RowAdapter(mContext, mLineModels);
            recycle.setLayoutManager(new FlexboxLayoutManager(mContext));
            recycle.setAdapter(rowAdapter);
        }
        public List<LineModel> addLine(ItemModel item) {
            List<LineModel> mLineModels = new ArrayList<>();
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
            return mLineModels;
        }

    }
}
