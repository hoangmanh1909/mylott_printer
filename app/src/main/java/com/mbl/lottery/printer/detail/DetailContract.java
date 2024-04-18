package com.mbl.lottery.printer.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.mbl.lottery.model.DrawModel;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.PrintCommandModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.ChangeUpImageRequest;
import com.mbl.lottery.model.request.FinishOrderKenoRequest;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.model.response.BaseResponse;
import com.mbl.lottery.model.response.GetItemByCodeResponse;
import com.mbl.lottery.model.response.PrintResponse;
import com.mbl.lottery.model.response.UploadResponse;
import com.mbl.lottery.network.CommonCallback;

import java.util.List;

public interface DetailContract {
    interface Interactor extends IInteractor<DetailContract.Presenter> {
        void getDateTimeNow(CommonCallback<SimpleResult> callback);
        void getItemByCode(String code, CommonCallback<SimpleResult> callback);

        void print(List<ItemModel> itemModels, CommonCallback<SimpleResult> callback);

        void postImage(String filePath, CommonCallback<SimpleResult> callback);

        void finishOrderKeno(FinishOrderKenoRequest request, CommonCallback<SimpleResult> callback);

        void updateImage(OrderImagesRequest request, CommonCallback<SimpleResult> callback);
        void updateImageKeno(OrderImagesRequest request, CommonCallback<SimpleResult> callback);
        void updateImages(List<OrderImagesRequest> request, CommonCallback<SimpleResult> callback);
        void changeToImage(ChangeUpImageRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<DetailContract.Presenter> {
        void showTimeNow(String timeNoe);
        void showItem(List<ItemModel> orderModels);

        void showPrint(String orderCode, PrintCommandModel printCommandModel);

        void showImage(String file);
        void showOk();
        void showSuccessImage();

    }

    interface Presenter extends IPresenter<DetailContract.View, DetailContract.Interactor> {
        void getDateTimeNow();
        void getItemByCode();
        void updateImages(List<OrderImagesRequest> request);
        OrderModel getOrderModel();
        List<DrawModel> getDrawModels();

        void print(List<ItemModel> models);

        void postImage(String filePath, String type);

        void finishOrderKeno(FinishOrderKenoRequest request);

        void updateImage(OrderImagesRequest request);
        void updateImageKeno(OrderImagesRequest request);

        void changeToImage(String code);
    }
}
