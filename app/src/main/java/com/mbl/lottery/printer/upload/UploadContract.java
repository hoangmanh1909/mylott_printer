package com.mbl.lottery.printer.upload;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.PrintCommandModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.ChangeUpImageRequest;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.model.request.OrderTablesImagesRequest;
import com.mbl.lottery.model.response.UploadResponse;
import com.mbl.lottery.network.CommonCallback;

import java.util.List;

public interface UploadContract {
    interface Interactor extends IInteractor<UploadContract.Presenter> {
        void print(String orderCode, CommonCallback<SimpleResult> callback);
        void changeToPrinted(String orderCode, CommonCallback<SimpleResult> callback);
        void postImage(String filePath, CommonCallback<SimpleResult> callback);
        void getItemByCode(String code, CommonCallback<SimpleResult> callback);
        void changeToImage(ChangeUpImageRequest request, CommonCallback<SimpleResult> callback);

        void updateImages(List<OrderImagesRequest> request, CommonCallback<SimpleResult> callback);
        void updateImage(OrderImagesRequest request, CommonCallback<SimpleResult> callback);

        void updateImageV1(OrderTablesImagesRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<UploadContract.Presenter> {
        void showImage(String file);
        void showItems(List<ItemModel> itemModels);
        void showChangeToImage();
        void showPrint(String code, PrintCommandModel printCommandModel);
        void showPrintSuccess(String code);
        void showOkSuccess();
    }

    interface Presenter extends IPresenter<UploadContract.View, UploadContract.Interactor> {
        OrderModel getOrderModel();

        List<ItemModel> getItemModels();
        void getItemByCode(String code);
        void postImage(String filePath);
        void print(String orderCode);
        void changeToImage(String code);

        void updateImage(OrderImagesRequest request);
        void updateImages(List<OrderImagesRequest> request);
        void changeToPrinted(String orderCode);
        void updateImageV1(OrderTablesImagesRequest request);
    }
}
