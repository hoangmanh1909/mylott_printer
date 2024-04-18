package com.mbl.lottery.main;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.base.LKLPrinterActivity;

public class MainActivity extends LKLPrinterActivity {

    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new MainPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}