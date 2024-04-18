package com.mbl.lottery.home;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.BuildConfig;
import com.mbl.lottery.R;
import com.mbl.lottery.home.drawresult.AddPresenter;
import com.mbl.lottery.model.EmployeeModel;
import com.mbl.lottery.model.HomeModel;
import com.mbl.lottery.printer.PrinterActivity;
import com.mbl.lottery.printer.loto.PrinterLotoActivity;
import com.mbl.lottery.printer.topup.TopupActivity;
import com.mbl.lottery.printer.topup.TopupPresenter;
import com.mbl.lottery.service.BluetoothServices;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.SharedPref;
import com.mbl.lottery.utils.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends ViewFragment<HomeContract.Presenter> implements HomeContract.View {

    @BindView(R.id.recycle)
    RecyclerView recyclerView;

    HomeAdapter adapter;
    List<HomeModel> homeModels = new ArrayList<>();
    EmployeeModel employeeModel;
    public static HomeFragment getInstance() {
        return new HomeFragment();
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
                        Intent intent = new Intent(getActivity(), PrinterActivity.class);
                        intent.putExtra(Constants.PRODUCT_ID, 1);
                        startActivity(intent);
                    } else {
                        if(homeModel.getProductID() == 10){
                            Intent intent = new Intent(getActivity(), TopupActivity.class);
                            intent.putExtra(Constants.PRODUCT_ID, homeModel.getProductID());
                            startActivity(intent);
                        }else if(homeModel.getProductID() == 100 || homeModel.getProductID() == 110){
                            int product = 1;
                            if(homeModel.getProductID() == 110)
                                product = 2;
                            new AddPresenter((ContainerView) getActivity(),product).pushView();
                        }else if(homeModel.getProductID() == 12){
                            Intent intent = new Intent(getActivity(), PrinterLotoActivity.class);
                            intent.putExtra(Constants.PRODUCT_ID, homeModel.getProductID());
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(getActivity(), PrinterActivity.class);
                            intent.putExtra(Constants.PRODUCT_ID, homeModel.getProductID());
                            startActivity(intent);
//                            if (BluetoothServices.mState == BluetoothServices.STATE_CONNECTED) {
//                                Intent intent = new Intent(getActivity(), PrinterActivity.class);
//                                intent.putExtra(Constants.PRODUCT_ID, homeModel.getProductID());
//                                startActivity(intent);
//                            } else {
//                                if (BluetoothServices.mState == BluetoothServices.STATE_CONNECTING)
//                                    Toast.showToast(getContext(), "Đang tiến hành kết nối Bluetooth");
//                                if (BluetoothServices.mState == BluetoothServices.STATE_FAIL)
//                                    Toast.showToast(getContext(), "Kết nối Bluetooth thất bại");
//                                else
//                                    Toast.showToast(getContext(), "Không có kết nối Bluetooth");
//                            }
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private List<HomeModel> itemHome() {
        Resources res = getResources();


        HomeModel homeModel = new HomeModel();

        if(employeeModel.getUserName().equals("ketoan")) {
            homeModel = new HomeModel();
            homeModel.setLogo(R.drawable.baseline_paid_24);
            homeModel.setProductID(10);
            homeModel.setTitle("Nạp tiền");
            homeModels.add(homeModel);

            homeModel = new HomeModel();
            homeModel.setLogo(R.drawable.home_mega);
            homeModel.setProductID(100);
            homeModel.setTitle("Kết quả Mega");
            homeModels.add(homeModel);

            homeModel = new HomeModel();
            homeModel.setLogo(R.drawable.home_power);
            homeModel.setProductID(110);
            homeModel.setTitle("Kết quả Power");
            homeModels.add(homeModel);
        }else{
            homeModel.setLogo(R.drawable.home_keno);
            homeModel.setProductID(Constants.PRODUCT_KENO);
            homeModel.setTitle(res.getString(R.string.printer_keno));
            homeModels.add(homeModel);

            homeModel = new HomeModel();
            homeModel.setLogo(R.drawable.home_mega);
            homeModel.setProductID(Constants.PRODUCT_NORMAL);
            homeModel.setTitle(res.getString(R.string.printer_normal));
            homeModels.add(homeModel);

            homeModel = new HomeModel();
            homeModel.setLogo(R.drawable.mienbac);
            homeModel.setProductID(12);
            homeModel.setTitle(res.getString(R.string.printer_loto));
            homeModels.add(homeModel);

            homeModel = new HomeModel();
            homeModel.setLogo(R.drawable.ic_baseline_camera_alt_24);
            homeModel.setProductID(1);
            homeModel.setTitle(res.getString(R.string.upload_img));
            homeModels.add(homeModel);
        }
        return homeModels;

    }
}
