package com.mbl.lottery.printer.loto.detail;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.base.LKLPrinterActivity;
import com.mbl.lottery.printer.detail.DetailPresenter;

public class DetailLotoActivity extends LKLPrinterActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new DetailLotoPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}