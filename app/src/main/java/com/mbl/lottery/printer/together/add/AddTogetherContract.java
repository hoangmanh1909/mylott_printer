package com.mbl.lottery.printer.together.add;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.mbl.lottery.model.DrawModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.TopupAddRequest;
import com.mbl.lottery.model.request.TogetherTicketAddRequest;
import com.mbl.lottery.model.request.TogetherTicketEditRequest;
import com.mbl.lottery.network.CommonCallback;

import java.util.List;

public interface AddTogetherContract {
    interface Interactor extends IInteractor<AddTogetherContract.Presenter> {
        void  addTogether(TogetherTicketAddRequest req, CommonCallback<SimpleResult> callback);
        void  editTogether(TogetherTicketEditRequest req, CommonCallback<SimpleResult> callback);
        void postImage(String filePath, CommonCallback<SimpleResult> callback);
        void getDrawMega( CommonCallback<SimpleResult> callback);
        void getDrawPower( CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<AddTogetherContract.Presenter> {
        void showImage(String file);
        void showDraw(List<DrawModel> drawModels);
    }

    interface Presenter extends IPresenter<AddTogetherContract.View, AddTogetherContract.Interactor> {
        void  editTogether(TogetherTicketEditRequest req);
        void  addTogether(TogetherTicketAddRequest req);
        void postImage(String filePath);
        void getDrawMega();
        void getDrawPower();
    }
}
