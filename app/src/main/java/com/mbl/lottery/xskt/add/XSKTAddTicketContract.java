package com.mbl.lottery.xskt.add;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.mbl.lottery.callback.BarCodeCallback;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.ProviderSearchRequest;
import com.mbl.lottery.model.request.XSKTAddTicketRequest;
import com.mbl.lottery.model.request.XSKTBaseV1Request;
import com.mbl.lottery.model.request.XSKTRadioSearchRequest;
import com.mbl.lottery.model.response.ProviderSearchResponse;
import com.mbl.lottery.model.response.XSKTBaseInfoResponse;
import com.mbl.lottery.model.response.XSKTRadioSearchResponse;
import com.mbl.lottery.network.CommonCallback;

import java.util.List;

public interface XSKTAddTicketContract {
    interface Interactor extends IInteractor<Presenter> {
        void  addTicket(XSKTAddTicketRequest req, CommonCallback<SimpleResult> callback);
        void postImage(String filePath, CommonCallback<SimpleResult> callback);
        void getBaseInfo(String code, CommonCallback<SimpleResult> callback);
        void  changeStatus(XSKTBaseV1Request req, CommonCallback<SimpleResult> callback);
        void searchRadioByDate(XSKTRadioSearchRequest request, CommonCallback<SimpleResult> callback);
        void searchProvider(ProviderSearchRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showImage(String file);
        void showBaseInfo(XSKTBaseInfoResponse model);
        void showSuccess();
        void showRadioByDate(List<XSKTRadioSearchResponse> radios);
        void showProvider(List<ProviderSearchResponse> providers);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void addTicket(XSKTAddTicketRequest req);
        void postImage(String filePath);
        void showBarcode(BarCodeCallback barCodeCallback);
        void getBaseInfo(String code);
        void  changeStatus(XSKTBaseV1Request req);
        void searchRadioByDate(XSKTRadioSearchRequest request);

        void searchProvider(ProviderSearchRequest request);
    }
}
