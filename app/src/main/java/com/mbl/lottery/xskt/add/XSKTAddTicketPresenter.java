package com.mbl.lottery.xskt.add;


import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.mbl.lottery.callback.BarCodeCallback;
import com.mbl.lottery.model.FileInfoModel;
import com.mbl.lottery.model.OrderModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.ProviderSearchRequest;
import com.mbl.lottery.model.request.XSKTAddTicketRequest;
import com.mbl.lottery.model.request.XSKTBaseV1Request;
import com.mbl.lottery.model.request.XSKTRadioSearchRequest;
import com.mbl.lottery.model.response.ProviderSearchResponse;
import com.mbl.lottery.model.response.XSKTBaseInfoResponse;
import com.mbl.lottery.model.response.XSKTRadioSearchResponse;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;
import com.mbl.lottery.scanner.ScannerCodePresenter;
import com.mbl.lottery.utils.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class XSKTAddTicketPresenter  extends Presenter<XSKTAddTicketContract.View, XSKTAddTicketContract.Interactor>
        implements XSKTAddTicketContract.Presenter{

    public XSKTAddTicketPresenter(ContainerView containerView) {
        super(containerView);
    }
    @Override
    public void getBaseInfo(String code) {
        mView.showProgress();
        mInteractor.getBaseInfo(code ,new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    XSKTBaseInfoResponse xsktBaseInfoResponse;
                    xsktBaseInfoResponse = NetWorkController.getGson().fromJson(response.body().getData(), XSKTBaseInfoResponse.class);
                    mView.showBaseInfo(xsktBaseInfoResponse);
                }else Toast.showToast(getViewContext(), response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        });
    }

    @Override
    public void changeStatus(XSKTBaseV1Request req) {
        mView.showProgress();
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    Toast.showToast(getViewContext(), "Cập nhật thành công");
                    back();
                }else Toast.showToast(getViewContext(), response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        };
        mInteractor.changeStatus(req, callback);
    }

    @Override
    public void searchRadioByDate(XSKTRadioSearchRequest request) {
        mView.showProgress();
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    List<XSKTRadioSearchResponse> radios = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<XSKTRadioSearchResponse>>() {
                    }.getType());
                    mView.showRadioByDate(radios);
                }else Toast.showToast(getViewContext(), response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        };
        mInteractor.searchRadioByDate(request, callback);
    }

    @Override
    public void searchProvider(ProviderSearchRequest request) {
        mView.showProgress();
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    List<ProviderSearchResponse> providers = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<ProviderSearchResponse>>() {
                    }.getType());
                    mView.showProvider(providers);
                }else Toast.showToast(getViewContext(), response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        };
        mInteractor.searchProvider(request, callback);
    }


    @Override
    public void start() {

    }

    @Override
    public XSKTAddTicketContract.Interactor onCreateInteractor() {
        return new XSKTAddTicketInteractor(this);
    }

    @Override
    public XSKTAddTicketContract.View onCreateView() {
        return XSKTAddTicketFragment.getInstance();
    }

    @Override
    public void addTicket(XSKTAddTicketRequest req) {
        mView.showProgress();
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {

                    mView.showSuccess();
                }else Toast.showToast(getViewContext(), response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        };
        mInteractor.addTicket(req, callback);
    }

    @Override
    public void postImage(String filePath) {
        mView.showProgress();
        CommonCallback<SimpleResult> callback = new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    FileInfoModel fileInfoModel = NetWorkController.getGson().fromJson(response.body().getData(), FileInfoModel.class);
                    mView.showImage(fileInfoModel.getFileName());
                }else Toast.showToast(getViewContext(), response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        };
        mInteractor.postImage(filePath, callback);
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }
}
