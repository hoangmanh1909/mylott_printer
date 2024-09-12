package com.mbl.lottery.xskt;


import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class XSKTPresenter extends Presenter<XSKTContract.View, XSKTContract.Interactor>
        implements XSKTContract.Presenter{
    public XSKTPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public XSKTContract.Interactor onCreateInteractor() {
        return null;
    }

    @Override
    public XSKTContract.View onCreateView() {
        return XSKTFragment.getInstance();
    }
}

