package com.mbl.lottery.network;

import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.request.ChangeUpImageRequest;
import com.mbl.lottery.model.request.FinishOrderKenoRequest;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.model.request.OrderTablesImagesRequest;
import com.mbl.lottery.model.request.OutOfNumberRequest;
import com.mbl.lottery.model.request.RequestObject;
import com.mbl.lottery.model.request.SearchOrderRequest;
import com.mbl.lottery.model.response.BaseResponse;
import com.mbl.lottery.model.response.DrawResponse;
import com.mbl.lottery.model.response.GetItemByCodeResponse;
import com.mbl.lottery.model.response.LoginResponse;
import com.mbl.lottery.model.response.ParamsResponse;
import com.mbl.lottery.model.response.PrintResponse;
import com.mbl.lottery.model.response.SearchOrderResponse;
import com.mbl.lottery.model.response.UploadResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface LuckyLotterApi {
    @POST("Gateway/Execute")
    Call<SimpleResult> commonService(@Body RequestObject requestObject);

    @Multipart
    @POST("Handle/UploadImage")
    Call<SimpleResult> postImage(@Part MultipartBody.Part image);

}
