package com.mbl.lottery.xskt;


import android.content.Intent;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.R;
import com.mbl.lottery.home.HomeAdapter;
import com.mbl.lottery.model.EmployeeModel;
import com.mbl.lottery.model.HomeModel;
import com.mbl.lottery.printer.PrinterActivity;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.SharedPref;
import com.mbl.lottery.xskt.add.XSKTAddTicketActivity;
import com.mbl.lottery.xskt.history.XSKTHistoryPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class XSKTFragment extends ViewFragment<XSKTContract.Presenter> implements XSKTContract.View {
    @BindView(R.id.recycle)
    RecyclerView recyclerView;

    HomeAdapter adapter;
    List<HomeModel> homeModels = new ArrayList<>();
    EmployeeModel employeeModel;

    public static XSKTFragment getInstance() {
        return new XSKTFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        SharedPref sharedPref = new SharedPref(getActivity());
        employeeModel = sharedPref.getEmployeeModel();
        this.itemHome();

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new HomeAdapter(getContext(), homeModels) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                holder.itemView.setOnClickListener(v -> {
                    HomeModel homeModel = homeModels.get(position);
                    if (homeModel.getProductID() == 1) {
                        Intent intent = new Intent(getActivity(), XSKTAddTicketActivity.class);
                        intent.putExtra(Constants.MODE, "LOAD");
                        intent.putExtra(Constants.PRODUCT_ID, 1);
                        startActivity(intent);
                    }else if (homeModel.getProductID() == 5) {
                        Intent intent = new Intent(getActivity(), XSKTAddTicketActivity.class);
                        intent.putExtra(Constants.MODE, "LOAD");
                        intent.putExtra(Constants.PRODUCT_ID, 1);
                        intent.putExtra(Constants.AREA, 3);
                        startActivity(intent);
                    } else if (homeModel.getProductID() == 3) {
                        Intent intent = new Intent(getActivity(), PrinterActivity.class);
                        intent.putExtra(Constants.PRODUCT_ID, 7);
                        intent.putExtra(Constants.MODE, "LOAD");
                        startActivity(intent);
                    }else if (homeModel.getProductID() == 4) {
                        new XSKTHistoryPresenter((ContainerView) mContext,"R").pushView();
                    }else {
                        new XSKTHistoryPresenter((ContainerView) mContext,"P").pushView();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private List<HomeModel> itemHome() {
        Resources res = getResources();

        HomeModel homeModel = new HomeModel();

        homeModel = new HomeModel();
        homeModel.setLogo(R.drawable.baseline_add_circle_outline_24);
        homeModel.setProductID(1);
        homeModel.setTitle("Nhập vé Miền Bắc");
        homeModels.add(homeModel);

        homeModel = new HomeModel();
        homeModel.setLogo(R.drawable.baseline_add_circle_outline_24);
        homeModel.setProductID(5);
        homeModel.setTitle("Nhập vé Miền Nam");
        homeModels.add(homeModel);

        homeModel = new HomeModel();
        homeModel.setLogo(R.drawable.baseline_check_circle_outline_24);
        homeModel.setProductID(2);
        homeModel.setTitle("Duyệt vé");
        homeModels.add(homeModel);

        homeModel = new HomeModel();
        homeModel.setLogo(R.drawable.ic_baseline_camera_alt_24);
        homeModel.setProductID(3);
        homeModel.setTitle("Cập nhật ảnh vé");
        homeModels.add(homeModel);

        homeModel = new HomeModel();
        homeModel.setLogo(R.drawable.baseline_assignment_late_24);
        homeModel.setProductID(4);
        homeModel.setTitle("Hoàn trả vé");
        homeModels.add(homeModel);

        return homeModels;
    }
}