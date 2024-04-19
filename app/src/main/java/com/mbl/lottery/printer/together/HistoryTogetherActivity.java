package com.mbl.lottery.printer.together;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.base.LKLPrinterActivity;
import com.mbl.lottery.printer.PrinterPresenter;

public class HistoryTogetherActivity extends LKLPrinterActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new HistoryTogetherPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}
