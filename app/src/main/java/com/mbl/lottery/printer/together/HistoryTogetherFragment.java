package com.mbl.lottery.printer.together;

import android.content.Intent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.R;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.ProductModel;
import com.mbl.lottery.model.request.TogetherTicketSearchRequest;
import com.mbl.lottery.model.response.TogetherTicketSearchResponse;
import com.mbl.lottery.printer.PrinterAdapter;
import com.mbl.lottery.printer.PrinterFragment;
import com.mbl.lottery.printer.detail.DetailActivity;
import com.mbl.lottery.printer.together.add.AddTogetherActivity;
import com.mbl.lottery.printer.upload.UploadPresenter;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HistoryTogetherFragment extends ViewFragment<HistoryTogetherContract.Presenter> implements HistoryTogetherContract.View, SwipeRefreshLayout.OnRefreshListener{
    public final String TAG = "HistoryTogetherFragment";
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.no_data)
    LinearLayout noData;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.iv_filter)
    ImageView iv_filter;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    int productID = 0;
    HistoryTogetherAdapter mAdapter;
    List<TogetherTicketSearchResponse> mOrderModels;
    public static HistoryTogetherFragment getInstance() {
        return new HistoryTogetherFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_his_together_ticket;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        iv_filter.setOnClickListener(v -> pickFilter());
        mOrderModels = new ArrayList<>();
        noData.setVisibility(View.GONE);

        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HistoryTogetherAdapter(getContext(), mOrderModels) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                holder.itemView.setOnClickListener(v -> {
                    TogetherTicketSearchResponse item = mOrderModels.get(position);

                        if (item.getProductID() == Constants.PRODUCT_KENO) {
                            Intent intent = new Intent(requireActivity(), AddTogetherActivity.class);
//                            intent.putExtra(Constants.ORDER_MODEL, item);
                            startActivity(intent);
                        }

                });
            }
        };
        recycle.setAdapter(mAdapter);
        iv_back.setOnClickListener(v -> mPresenter.back());
        TogetherTicketSearchRequest req = new TogetherTicketSearchRequest();
        req.setProductID(productID);
        mPresenter.getTicket(req);
    }

    @Override
    public void onRefresh() {
        mAdapter.setAllItemsEnabled(false);
        TogetherTicketSearchRequest req = new TogetherTicketSearchRequest();
        req.setProductID(productID);
        mPresenter.getTicket(req);
    }

    @Override
    public void showTicket(List<TogetherTicketSearchResponse> models) {
        if (models.size() > 0)
            noData.setVisibility(View.GONE);
        else
            noData.setVisibility(View.VISIBLE);

        mAdapter.setAllItemsEnabled(true);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mOrderModels.clear();
        mOrderModels.addAll(models);
        mAdapter.setItems(mOrderModels);
    }

    private void pickFilter() {
        List<ProductModel> productModels = new ArrayList<>();

        ProductModel productModel = new ProductModel();

        productModel.setId(0);
        productModel.setName(Utils.getProductName(0));
        productModels.add(productModel);

        productModel = new ProductModel();
        productModel.setId(Constants.PRODUCT_POWER);
        productModel.setName(Utils.getProductName(Constants.PRODUCT_POWER));
        productModels.add(productModel);

        productModel = new ProductModel();
        productModel.setId(Constants.PRODUCT_MEGA);
        productModel.setName(Utils.getProductName(Constants.PRODUCT_MEGA));
        productModels.add(productModel);

        PopupMenu popupMenu = new PopupMenu(requireActivity(), iv_filter, Gravity.END);

        for (ProductModel productModel1 : productModels) {
            popupMenu.getMenu().add(1, productModel1.getId(), 0, productModel1.getName());
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String value = item.getTitle().toString();
                tv_title.setText(value);
                productID = item.getItemId();
                TogetherTicketSearchRequest req = new TogetherTicketSearchRequest();
                req.setProductID(productID);
                mPresenter.getTicket(req);
                return true;
            }
        });

        popupMenu.show();
    }
}
