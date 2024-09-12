package com.mbl.lottery.xskt.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.model.response.GetItemXsktResponse;
import com.mbl.lottery.network.CommonCallback;

import java.util.List;

public interface XSKTDetailContract {
    interface Interactor extends IInteractor<Presenter> {
        void getItemByCode(String code, CommonCallback<SimpleResult> callback);
        void postImage(String filePath, CommonCallback<SimpleResult> callback);
        void updateImage(OrderImagesRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showItem(List<GetItemXsktResponse> orderModels);
        void showImage(String file);

    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getItemByCode(String code);
        void updateImage(OrderImagesRequest request);
        OrderModel getOrderModel();
        void postImage(String filePath);
    }
}
