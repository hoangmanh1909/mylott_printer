package com.mbl.lottery.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mbl.lottery.BuildConfig;
import com.mbl.lottery.model.ItemModel;
import com.mbl.lottery.model.SimpleResult;
import com.mbl.lottery.model.TopupAddRequest;
import com.mbl.lottery.model.request.AddDrawResultRequest;
import com.mbl.lottery.model.request.BaseCoreRequest;
import com.mbl.lottery.model.request.BaseRequest;
import com.mbl.lottery.model.request.ChangeUpImageRequest;
import com.mbl.lottery.model.request.FinishOrderKenoRequest;
import com.mbl.lottery.model.request.LoginRequest;
import com.mbl.lottery.model.request.OrderImagesRequest;
import com.mbl.lottery.model.request.OrderTablesImagesRequest;
import com.mbl.lottery.model.request.OutOfNumberRequest;
import com.mbl.lottery.model.request.RequestObject;
import com.mbl.lottery.model.request.SearchOrderRequest;
import com.mbl.lottery.model.request.TogetherTicketAddRequest;
import com.mbl.lottery.model.request.TogetherTicketEditRequest;
import com.mbl.lottery.model.request.TogetherTicketSearchRequest;
import com.mbl.lottery.model.response.BaseResponse;
import com.mbl.lottery.model.response.DrawResponse;
import com.mbl.lottery.model.response.GetItemByCodeResponse;
import com.mbl.lottery.model.response.LoginResponse;
import com.mbl.lottery.model.response.ParamsResponse;
import com.mbl.lottery.model.response.PrintResponse;
import com.mbl.lottery.model.response.SearchOrderResponse;
import com.mbl.lottery.model.response.UploadResponse;
import com.mbl.lottery.utils.RSAUtil;
import com.mbl.lottery.utils.Utils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkController {
    private NetWorkController() {
    }

    private static volatile LuckyLotterApi apiBuilder;
    private static volatile LuckyLotterApi apiBuilderImage;
    private static String AccessKey = "c7071a90bee2311c4cdde635fff5ca6607ea462f8d8b3f3d359e64d2cad3d486";

    public static Gson getGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    private static LuckyLotterApi getAPIBuilder() {
        if (apiBuilder == null) {
            // mPrivateKeySignature = Constants.PRIVATE_KEY;
            // mApiKey = Constants.API_KEY;
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(Utils.getUnsafeOkHttpClient(120, 120, BuildConfig.API_KEY))
                    .build();
            apiBuilder = retrofit.create(LuckyLotterApi.class);
        }
        return apiBuilder;
    }

    private static LuckyLotterApi getAPIBuilderImg() {
        if (apiBuilderImage == null) {
            // mPrivateKeySignature = Constants.PRIVATE_KEY;
            // mApiKey = Constants.API_KEY;
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.IMAGE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(Utils.getUnsafeOkHttpClient(120, 120, BuildConfig.API_KEY))
                    .build();
            apiBuilderImage = retrofit.create(LuckyLotterApi.class);
        }
        return apiBuilderImage;
    }

    public static void postImage(String filePath, CommonCallback<SimpleResult> callback) {
        File file = new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("ticket", "file_ticket.jpg", reqFile);
        Call<SimpleResult> call = getAPIBuilderImg().postImage(body);
        call.enqueue(callback);
    }


    public static void login(LoginRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "EMP_LOGIN", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchOrder(SearchOrderRequest searchOrderRequest, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(searchOrderRequest);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "OD_SEARCH", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void searchOrderKeno(SearchOrderRequest searchOrderRequest, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(searchOrderRequest);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "OD_GET_KENO_PRINT", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void searchOrderLoto(SearchOrderRequest searchOrderRequest, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(searchOrderRequest);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "OD_GET_PRINT_LOTO", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void printByOrderCode(String code, CommonCallback<SimpleResult> callback) {
        try {
            BaseCoreRequest req = new BaseCoreRequest();
            req.setCode(code);
            String data = getGson().toJson(req);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "OD_PRINT_BY_CODE", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void changeToPrinted(String code, CommonCallback<SimpleResult> callback) {
        try {
            BaseCoreRequest req = new BaseCoreRequest();
            req.setCode(code);
            String data = getGson().toJson(req);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "OD_CHANGE_TO_PRINTED", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getDateTimeNow(CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson("");
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "DIC_GET_DATE_TIME", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getItemByOrderCode(String code, CommonCallback<SimpleResult> callback) {
        try {
            BaseCoreRequest req = new BaseCoreRequest();
            req.setCode(code);
            String data = getGson().toJson(req);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "OD_GET_ITEM_BY_CODE", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void topupAdd(TopupAddRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "TRANS_TOPUP_ADD_V1", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addDrawResult(AddDrawResultRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "DRAW_RESULT_ADD", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getDrawKeno(CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson("");
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "DIC_GET_DRAW_KENO_BY_DAY", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void print(List<ItemModel> request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "OD_PRINT", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateImage(OrderImagesRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "OD_UPLOAD_IMG", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateImages(List<OrderImagesRequest> request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "OD_UPLOAD_IMGS", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateImageKeno(OrderImagesRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "OD_UPLOAD_IMG_KENO", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchTogetherTicket(TogetherTicketSearchRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "TT_SEARCH", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addTogetherTicket(TogetherTicketAddRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "TT_ADD_NEW", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void editTogetherTicket(TogetherTicketEditRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "TT_EDIT", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getDrawMega(CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson("");
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "DIC_GET_DRAW_MEGA", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getDrawPower(CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson("");
            String signature = RSAUtil.SHA256(AccessKey + data);
            RequestObject requestData = new RequestObject("", "", "DIC_GET_DRAW_POWER", data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestData);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void finsishOrderKeno(FinishOrderKenoRequest request, CommonCallback<SimpleResult> callback) {
//        Call<SimpleResult> call = getAPIBuilder().finishOrderKeno(request);
//        call.enqueue(callback);
    }


    public static void updateImageV1(OrderTablesImagesRequest request, CommonCallback<SimpleResult> callback) {
//        Call<SimpleResult> call = getAPIBuilder().updateImageV1(request);
//        call.enqueue(callback);
    }

    public static void changeToImage(ChangeUpImageRequest request, CommonCallback<SimpleResult> callback) {
//        Call<SimpleResult> call = getAPIBuilder().changeToUpImage(request);
//        call.enqueue(callback);
    }

    public static void outOfNumber(OutOfNumberRequest overTicket, CommonCallback<SimpleResult> callback) {
//        Call<SimpleResult> call = getAPIBuilder().outOfNumber(overTicket);
//        call.enqueue(callback);
    }




    public static void getAllConfig(CommonCallback<ParamsResponse> callback) {
//        Call<ParamsResponse> call = getAPIBuilder().getAllConfig();
//        call.enqueue(callback);
    }

    public static void countOrderWattingPrint(int productID, int POSID, CommonCallback<BaseResponse> callback) {
//        Call<BaseResponse> call = getAPIBuilder().countOrderWattingPrint(productID, POSID);
//        call.enqueue(callback);
    }

}
