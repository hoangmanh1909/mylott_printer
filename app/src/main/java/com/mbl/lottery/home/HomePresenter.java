package com.mbl.lottery.home;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.mbl.lottery.model.DateTimeServer;
import com.mbl.lottery.model.ParamsModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.response.BaseResponse;
import com.mbl.lottery.model.response.ParamsResponse;
import com.mbl.lottery.network.CommonCallback;
import com.mbl.lottery.network.NetWorkController;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.SharedPref;
import com.mbl.lottery.utils.TimerThread;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Response;

public class HomePresenter extends Presenter<HomeContract.View, HomeContract.Interactor>
        implements HomeContract.Presenter {
    SharedPref sharedPref;
    Activity activity;

    public HomePresenter(ContainerView containerView) {
        super(containerView);

    }

    @Override
    public void start() {
        activity = (Activity) mContainerView;
        sharedPref = new SharedPref(activity);

//        getParams();
        //threadGetDateTimeNow();
    }

    private void threadGetDateTimeNow(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                getDateTimeNow();
            }
        };
        long delay = 60 * 1000L;
        Timer timer = new Timer("TimerGetDateTimeNow");
        timer.schedule(timerTask, 0, delay);
    }

    private void getDateTimeNow(){
        mInteractor.getDateTimeNow(new CommonCallback<SimpleResult>(activity) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    DateTimeServer dateTimeServer = NetWorkController.getGson().fromJson(response.body().getData(), DateTimeServer.class);
                    new TimerThread(dateTimeServer.getDateTimeNow());
//                    sharedPref.putString(Constants.KEY_DATE_TIME_NOW, String.valueOf(response.body().getValue()));
//                    //Log.e("TimeSever", String.valueOf(response.body().getValue()));
//                    TimeService timeService = new TimeService();
//                    Intent intent = activity.getIntent();
//                    intent.putExtra(Constants.KEY_DATE_TIME_NOW,String.valueOf(response.body().getValue()));
//                    timeService.onStartCommand(intent, 0, 0);
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    private void getParams() {
        mView.showProgress();
        mInteractor.getParams(new CommonCallback<ParamsResponse>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<ParamsResponse> call, Response<ParamsResponse> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    List<ParamsModel> paramsResponseList = response.body().getParamsModels();
                    for (ParamsModel paramsModel : paramsResponseList) {
                        if (paramsModel.getParameter().equals("DIFF_PRINT_SECOND")) {
                            sharedPref.putString(Constants.KEY_DIFF_PRINT_SECOND, paramsModel.getValue());
                        }
                    }
                }
            }

            @Override
            protected void onError(Call<ParamsResponse> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public HomeContract.Interactor onCreateInteractor() {
        return new HomeInteractor(this);
    }

    @Override
    public HomeContract.View onCreateView() {
        return HomeFragment.getInstance();
    }
}
