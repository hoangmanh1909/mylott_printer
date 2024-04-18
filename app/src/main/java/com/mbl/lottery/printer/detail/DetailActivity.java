package com.mbl.lottery.printer.detail;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.base.LKLPrinterActivity;

public class DetailActivity extends LKLPrinterActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new DetailPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}
