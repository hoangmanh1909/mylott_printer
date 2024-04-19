package com.mbl.lottery.together;

import android.content.Intent;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.R;
import com.mbl.lottery.home.HomeAdapter;
import com.mbl.lottery.home.HomeFragment;
import com.mbl.lottery.home.drawresult.AddPresenter;
import com.mbl.lottery.model.EmployeeModel;
import com.mbl.lottery.model.HomeModel;
import com.mbl.lottery.printer.PrinterActivity;
import com.mbl.lottery.printer.loto.PrinterLotoActivity;
import com.mbl.lottery.printer.together.HistoryTogetherActivity;
import com.mbl.lottery.printer.together.add.AddTogetherActivity;
import com.mbl.lottery.printer.topup.TopupActivity;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TogetherFragment extends ViewFragment<TogetherContract.Presenter> implements TogetherContract.View {
    @BindView(R.id.recycle)
    RecyclerView recyclerView;

    HomeAdapter adapter;
    List<HomeModel> homeModels = new ArrayList<>();
    EmployeeModel employeeModel;

    public static TogetherFragment getInstance() {
        return new TogetherFragment();
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
                        Intent intent = new Intent(getActivity(), AddTogetherActivity.class);
                        intent.putExtra(Constants.PRODUCT_ID, 1);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getActivity(), HistoryTogetherActivity.class);
                        startActivity(intent);
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
        homeModel.setTitle("Nhập vé");
        homeModels.add(homeModel);

        homeModel = new HomeModel();
        homeModel.setLogo(R.drawable.baseline_history_24);
        homeModel.setProductID(2);
        homeModel.setTitle("Lịch sử");
        homeModels.add(homeModel);

        return homeModels;
    }
}
