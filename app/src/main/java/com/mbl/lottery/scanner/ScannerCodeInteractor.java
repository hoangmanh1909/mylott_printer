package com.mbl.lottery.scanner;

import com.core.base.viper.Interactor;

class ScannerCodeInteractor extends Interactor<ScannerCodeContract.Presenter>
        implements ScannerCodeContract.Interactor {

    ScannerCodeInteractor(ScannerCodeContract.Presenter presenter) {
        super(presenter);
    }
}
