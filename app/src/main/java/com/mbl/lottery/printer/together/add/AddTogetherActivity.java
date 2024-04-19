package com.mbl.lottery.printer.together.add;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.base.LKLPrinterActivity;
import com.mbl.lottery.printer.topup.TopupPresenter;

public class AddTogetherActivity extends LKLPrinterActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new AddTogetherPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}
