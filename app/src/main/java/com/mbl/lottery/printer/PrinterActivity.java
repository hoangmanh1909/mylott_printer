package com.mbl.lottery.printer;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.base.LKLPrinterActivity;

public class PrinterActivity extends LKLPrinterActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new PrinterPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}
