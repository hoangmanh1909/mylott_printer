package com.mbl.lottery.xskt.history;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.mbl.lottery.R;
import com.mbl.lottery.model.response.XSKTTickeResponse;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.xskt.add.XSKTAddTicketActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class XSKTHistoryFragment  extends ViewFragment<XSKTHistoryContract.Presenter> implements XSKTHistoryContract.View, SwipeRefreshLayout.OnRefreshListener {
    public final String TAG = "XSKTHistoryFragment";
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
    int productID = 0;
    XSKTHistoryAdapter mAdapter;
    List<XSKTTickeResponse> mModels;

    public static XSKTHistoryFragment getInstance() {
        return new XSKTHistoryFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xskt_history;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        mModels = new ArrayList<>();
        noData.setVisibility(View.GONE);

        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new XSKTHistoryAdapter(getContext(), mModels) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                holder.itemView.setOnClickListener(v -> {
                    XSKTTickeResponse item = mModels.get(position);

                    Intent intent = new Intent(requireActivity(), XSKTAddTicketActivity.class);
                    intent.putExtra(Constants.ORDER_MODEL, (Serializable) item);
                    intent.putExtra(Constants.MODE,  "VIEW");
                    startActivity(intent);


                });
            }
        };
        recycle.setAdapter(mAdapter);

        iv_back.setOnClickListener(v -> mPresenter.back());
    }

    @Override
    public void onRefresh() {
        mAdapter.setAllItemsEnabled(false);

        mPresenter.getTicket();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        if(mPresenter != null)
            mPresenter.getTicket();
    }

    @Override
    public void showTicket(List<XSKTTickeResponse> models) {
        mAdapter.setAllItemsEnabled(true);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mModels.clear();
        mModels.addAll(models);
        mAdapter.setItems(mModels);
    }
}
