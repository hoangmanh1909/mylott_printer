package com.mbl.lottery.printer.loto.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.network.CommonCallback;

import java.util.List;

public interface DetailLotoContract {
    interface Interactor extends IInteractor<DetailLotoContract.Presenter> {
        void getItemByCode(String code, CommonCallback<SimpleResult> callback);
        void postImage(String filePath, CommonCallback<SimpleResult> callback);
        void updateImages(List<OrderImagesRequest> request, CommonCallback<SimpleResult> callback);
        void updateImageKeno(OrderImagesRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<DetailLotoContract.Presenter> {
        void showItems(List<ItemModel> itemModels);
        void showOk();
        void showImage(String file);
    }

    interface Presenter extends IPresenter<DetailLotoContract.View, DetailLotoContract.Interactor> {
        void postImage(String filePath);
        void getItemByCode(String code);
        void updateImages(List<OrderImagesRequest> request);
        void updateImageKeno(OrderImagesRequest request);
    }
}
