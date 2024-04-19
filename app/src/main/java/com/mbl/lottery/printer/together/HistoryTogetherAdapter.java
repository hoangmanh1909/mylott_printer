package com.mbl.lottery.printer.together;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.mbl.lottery.R;
import com.mbl.lottery.delegate.RecyclerViewItemEnabler;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.response.TogetherTicketSearchResponse;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.NumberUtils;

import java.util.List;

import butterknife.BindView;

public class HistoryTogetherAdapter extends RecyclerBaseAdapter implements RecyclerViewItemEnabler {

    Activity activity;
    List mItems;
    boolean mAllEnabled;

    public HistoryTogetherAdapter(Context context, List items) {
        super(context, items);

        activity = (Activity) context;
        this.mItems = items;
    }

    @NonNull
    @Override
    public HistoryTogetherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryTogetherAdapter.ViewHolder(inflateView(parent, R.layout.item_together_ticket));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder((HistoryTogetherAdapter.ViewHolder) holder, position);
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
        @BindView(R.id.img_logo)
        ImageView img_logo;
        @BindView(R.id.tv_code)
        TextView tv_code;
        @BindView(R.id.tv_draw)
        TextView tv_draw;
        @BindView(R.id.tv_bag)
        TextView tv_bag;
        @BindView(R.id.tv_amount)
        TextView tv_amount;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_quantity)
        TextView tv_quantity;
        @BindView(R.id.tv_percent)
        TextView tv_percent;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            TogetherTicketSearchResponse orderModel = (TogetherTicketSearchResponse) model;

            switch (orderModel.getProductID()){
                case Constants.PRODUCT_MEGA:
                    img_logo.setImageResource(R.drawable.home_mega);
                    break;
                case Constants.PRODUCT_MAX3D:
                    img_logo.setImageResource(R.drawable.home_max3d);
                    break;
                case Constants.PRODUCT_MAX3D_PLUS:
                    img_logo.setImageResource(R.drawable.home_max3d_plus);
                    break;
                case Constants.PRODUCT_MAX3D_PRO:
                    img_logo.setImageResource(R.drawable.logomax3dpro);
                    break;
                case Constants.PRODUCT_POWER:
                    img_logo.setImageResource(R.drawable.home_power);
                    break;
                case Constants.PRODUCT_KENO:
                    img_logo.setImageResource(R.drawable.home_keno);
                    break;
                case Constants.PRODUCT_LOTO235:
                case Constants.PRODUCT_LOTO234CAPSO:
                case Constants.PRODUCT_LOTO636:
                    img_logo.setImageResource(R.drawable.mienbac);
                    break;
            }
            tv_bag.setText("Bao " + orderModel.getSystematic());
            tv_code.setText("#" + orderModel.getCode());
            tv_date.setText(orderModel.getCreatedDate());
            tv_draw.setText("#" + orderModel.getDrawCode() + "-" + orderModel.getDrawDate());
            tv_amount.setText(NumberUtils.formatPriceNumber(orderModel.getPrice()));
            tv_quantity.setText(orderModel.getQuantity().toString());
            tv_percent.setText(orderModel.getPercent() + "%");
        }
    }
}
