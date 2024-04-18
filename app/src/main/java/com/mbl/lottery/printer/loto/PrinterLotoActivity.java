package com.mbl.lottery.printer.loto;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.base.LKLPrinterActivity;
import com.mbl.lottery.printer.PrinterPresenter;

public class PrinterLotoActivity  extends LKLPrinterActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new PrinterLotoPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}