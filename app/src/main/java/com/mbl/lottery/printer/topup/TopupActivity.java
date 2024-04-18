package com.mbl.lottery.printer.topup;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.base.LKLPrinterActivity;
import com.mbl.lottery.printer.PrinterPresenter;

public class TopupActivity extends LKLPrinterActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new TopupPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}
