package com.mbl.lottery.together;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.home.HomeFragment;

public class TogetherPresenter extends Presenter<TogetherContract.View, TogetherContract.Interactor>
        implements TogetherContract.Presenter{
    public TogetherPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public TogetherContract.Interactor onCreateInteractor() {
        return null;
    }

    @Override
    public TogetherContract.View onCreateView() {
        return TogetherFragment.getInstance();
    }
}
