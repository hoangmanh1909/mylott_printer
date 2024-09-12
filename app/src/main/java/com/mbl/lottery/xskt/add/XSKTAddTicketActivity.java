package com.mbl.lottery.xskt.add;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.base.LKLPrinterActivity;

public class XSKTAddTicketActivity  extends LKLPrinterActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new XSKTAddTicketPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}