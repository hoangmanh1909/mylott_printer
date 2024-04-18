package com.mbl.lottery.main;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mbl.lottery.R;
import com.mbl.lottery.home.HomePresenter;
import com.mbl.lottery.personal.PersonalPresenter;
import com.mbl.lottery.service.BluetoothServices;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.SharedPref;

import java.util.Set;

import butterknife.BindView;

public class MainFragment extends ViewFragment<MainContract.Presenter> implements MainContract.View {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    private Fragment fragmentHome;
    private Fragment fragmentPersonal;

    FragmentPagerAdapter adapter;
    BluetoothAdapter bluetoothAdapter;
    int REQUEST_ENABLE_BLUETOOTH = 1;
    String deviceBluetooth;

    BluetoothDevice[] btArray;

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        initAdapter();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BLUETOOTH);
            }

            SharedPref sharedPref = new SharedPref(getActivity());
            deviceBluetooth = sharedPref.getString(Constants.BLUETOOTH_NAME, "");
            if (!TextUtils.isEmpty(deviceBluetooth)) {
                registerBluetoothService(deviceBluetooth);
            }
        }
    }

    public void registerBluetoothService(String name) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(),new String[] { Manifest.permission.BLUETOOTH_CONNECT,Manifest.permission.BLUETOOTH_SCAN, },
                    1);
            return;
        }
        Set<BluetoothDevice> bt = bluetoothAdapter.getBondedDevices();

        btArray = new BluetoothDevice[bt.size()];
        BluetoothDevice mDevice = null;
        int index = 0;
        if (bt.size() > 0) {
            for (BluetoothDevice device : bt) {
                btArray[index] = device;
                index++;
            }
        }

        for (BluetoothDevice device : btArray) {
            if (device.getName().equals(name)) {
                mDevice = device;
                break;
            }
        }
        if (mDevice != null) {
            BluetoothServices bluetoothServices = new BluetoothServices();
            Intent intent = getActivity().getIntent();
            intent.putExtra("bluetooth_device", mDevice.getAddress());
            bluetoothServices.onStartCommand(intent, 0, 0);
        }

    }

    private void initAdapter() {
        adapter = new FragmentPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

            @Override
            public Fragment getItem(int position) {
                return getFragmentItem(position);
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigation.setSelectedItemId(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //on page scroll state changed
            }
        });

        viewPager.setOffscreenPageLimit(1);

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.page_home:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.page_persional:
                    viewPager.setCurrentItem(1);
                    break;
            }
            return true;
        });
    }

    private Fragment getFragmentItem(int position) {
        switch (position) {
            case 0:
                if (fragmentHome == null) {
                    fragmentHome = new HomePresenter((ContainerView) getActivity()).getFragment();
                }
                return fragmentHome;
            case 1:
                if (fragmentPersonal == null) {
                    fragmentPersonal = new PersonalPresenter((ContainerView) getActivity()).getFragment();
                }
                return fragmentPersonal;
        }
        return new Fragment();
    }
}
