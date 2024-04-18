package com.mbl.lottery.printer.loto;

import android.content.DialogInterface;
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
import com.mbl.lottery.model.DrawModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.ProductModel;
import com.mbl.lottery.printer.PrinterAdapter;
import com.mbl.lottery.printer.PrinterContract;
import com.mbl.lottery.printer.PrinterFragment;
import com.mbl.lottery.printer.detail.DetailActivity;
import com.mbl.lottery.printer.loto.detail.DetailLotoActivity;
import com.mbl.lottery.printer.upload.UploadPresenter;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.DialogUtils;
import com.mbl.lottery.utils.SharedPref;
import com.mbl.lottery.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PrinterLotoFragment extends ViewFragment<PrinterLotoContract.Presenter> implements PrinterLotoContract.View, SwipeRefreshLayout.OnRefreshListener {
    public final String TAG = "PrinterLotoFragment";
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.no_data)
    LinearLayout noData;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_filter)
    ImageView iv_filter;

    PrinterAdapter mAdapter;
    List<OrderModel> mOrderModels;
    SharedPref sharedPref;

    int productID = 0;
    public static PrinterLotoFragment getInstance() {
        return new PrinterLotoFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_printer_loto;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        sharedPref = new SharedPref(requireActivity());
        mOrderModels = new ArrayList<>();

        noData.setVisibility(View.GONE);

        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new PrinterAdapter(getContext(), mOrderModels) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                holder.itemView.setOnClickListener(v -> {
                    OrderModel item = mOrderModels.get(position);

                        Intent intent = new Intent(requireActivity(), DetailLotoActivity.class);
                        intent.putExtra(Constants.ORDER_MODEL, item);
                        startActivity(intent);
                        //new DetailPresenter((ContainerView) getBaseActivity(), mOrderModels.get(position), mDrawModels).pushView();

                });
            }
        };
        recycle.setAdapter(mAdapter);
        iv_filter.setOnClickListener(v -> pickProduct());
        iv_back.setOnClickListener(v -> mPresenter.back());
    }
    @Override
    public void onDisplay() {
        super.onDisplay();
        if (mPresenter != null) {
                mPresenter.getOrder(productID);
        } else {
            DialogUtils.showDialogMessage(getContext(), "Thông báo", "Hệ thông đang có lỗi! vui lòng quay lại sau.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mPresenter.back();
                    dialog.dismiss();
                }
            }, false);
        }
    }
    @Override
    public void onRefresh() {
        mAdapter.setAllItemsEnabled(false);
            mPresenter.getOrder(productID);
    }

    @Override
    public void showOrder(List<OrderModel> orderModels) {
        if (orderModels.size() > 0)
            noData.setVisibility(View.GONE);
        else
            noData.setVisibility(View.VISIBLE);

        mAdapter.setAllItemsEnabled(true);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mOrderModels.clear();
        mOrderModels.addAll(orderModels);
        mAdapter.setItems(mOrderModels);
    }

    private void pickProduct() {
        List<ProductModel> productModels = new ArrayList<>();

        ProductModel productModel = new ProductModel();

        productModel.setId(0);
        productModel.setName(Utils.getProductName(0));
        productModels.add(productModel);

        productModel = new ProductModel();
        productModel.setId(Constants.PRODUCT_LOTO235);
        productModel.setName(Utils.getProductName(Constants.PRODUCT_LOTO235));
        productModels.add(productModel);

        productModel = new ProductModel();
        productModel.setId(Constants.PRODUCT_LOTO636);
        productModel.setName(Utils.getProductName(Constants.PRODUCT_LOTO636));
        productModels.add(productModel);

        productModel = new ProductModel();
        productModel.setId(Constants.PRODUCT_LOTO234CAPSO);
        productModel.setName(Utils.getProductName(Constants.PRODUCT_LOTO234CAPSO));
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
                mPresenter.getOrder(productID);
                return true;
            }
        });

        popupMenu.show();
    }
}
