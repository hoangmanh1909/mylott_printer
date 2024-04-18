package com.mbl.lottery.printer.upload;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.mbl.lottery.R;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.utils.Constants;

import java.io.File;
import java.util.List;

import butterknife.BindView;

public class UploadAdapter extends RecyclerBaseAdapter {

    Activity activity;

    public UploadAdapter(Context context, List items) {
        super(context, items);
        activity = (Activity) context;
    }

    @Override
    public UploadAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UploadAdapter.HolderView(inflateView(parent, R.layout.item_upload));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder((UploadAdapter.HolderView) holder, position);
    }
    public void setAllItemsEnabled() {
        notifyItemRangeChanged(0, getItemCount());
    }

    public class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_draw)
        TextView tv_draw;
        @BindView(R.id.image_before)
        public SimpleDraweeView image_before;
        @BindView(R.id.image_after)
        public SimpleDraweeView image_after;
        @BindView(R.id.share_before)
        public  Button share_before;
        @BindView(R.id.share_after)
        public  Button share_after;
        @BindView(R.id.ll_image_after)
        LinearLayout ll_image_after;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            ItemModel itemModel = (ItemModel) model;
            tv_draw.setText("#" + itemModel.getDrawCode() + " " + itemModel.getDrawDate());

            if (itemModel.getProductID() == Constants.PRODUCT_MAX3D
            || itemModel.getProductID() == Constants.PRODUCT_LOTO235
            || itemModel.getProductID() == Constants.PRODUCT_LOTO234CAPSO
            || itemModel.getProductID() == Constants.PRODUCT_LOTO636) {
                ll_image_after.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(itemModel.getImgBefore())) {
                File imgFile = new File(itemModel.getImgBefore());
                if (imgFile.exists()) {
                    ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.fromFile(imgFile));
                    PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(imageRequestBuilder.build());
                    image_before.setController(controllerBuilder.build());
                }
                share_before.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(itemModel.getImgAfter())) {
                File imgFileAfter = new File(itemModel.getImgAfter());
                if (imgFileAfter.exists()) {
                    ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.fromFile(imgFileAfter));

                    PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(imageRequestBuilder.build());
                    image_after.setController(controllerBuilder.build());
                }
                share_after.setVisibility(View.VISIBLE);
            }

        }
    }
}
