package com.mbl.lottery.xskt.add;


import com.core.base.viper.Interactor;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.ProviderSearchRequest;
import com.mbl.lottery.model.request.XSKTAddTicketRequest;
import com.mbl.lottery.model.request.XSKTBaseV1Request;
import com.mbl.lottery.model.request.XSKTRadioSearchRequest;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;

public class XSKTAddTicketInteractor  extends Interactor<XSKTAddTicketContract.Presenter>
        implements XSKTAddTicketContract.Interactor {

    public XSKTAddTicketInteractor(XSKTAddTicketContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void addTicket(XSKTAddTicketRequest req, CommonCallback<SimpleResult> callback) {
        NetWorkController.xsktAddTicket(req,callback);
    }

    @Override
    public void postImage(String filePath, CommonCallback<SimpleResult> callback) {
        NetWorkController.postImage(filePath, callback);
    }

    @Override
    public void getBaseInfo(String code, CommonCallback<SimpleResult> callback) {
        NetWorkController.getBaseInfo(code,callback);
    }

    @Override
    public void changeStatus(XSKTBaseV1Request req, CommonCallback<SimpleResult> callback) {
        NetWorkController.xsktChangeStatus(req, callback);
    }

    @Override
    public void searchRadioByDate(XSKTRadioSearchRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.searchRadioByDate(request, callback);
    }

    @Override
    public void searchProvider(ProviderSearchRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.searchProvider(request, callback);
    }
}
