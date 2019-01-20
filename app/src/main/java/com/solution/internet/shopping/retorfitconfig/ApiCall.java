package com.solution.internet.shopping.retorfitconfig;

import android.app.VoiceInteractor;

import com.solution.internet.shopping.models.ModelAddProductRequest.ModelAddProductRequest;
import com.solution.internet.shopping.models.ModelChangePasswordRequest.ModelChangePasswordRequest;
import com.solution.internet.shopping.models.ModelChatNewRequest.ModelChatNewRequest;
import com.solution.internet.shopping.models.ModelCommenResponse.ModelCommenResponse;
import com.solution.internet.shopping.models.ModelConfirmationCodeRequest.ModelConfirmationCodeRequest;
import com.solution.internet.shopping.models.ModelLoginRequest.ModelLoginRequest;
import com.solution.internet.shopping.models.ModelLoginResponse.ModelLoginResponse;
import com.solution.internet.shopping.models.ModelSignUpRequest.ModelSignUpRequest;
import com.solution.internet.shopping.utlities.Constant;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCall
{

    //region auth
    @POST(Constant.subUrl + "user/login")
    Call<ModelCommenResponse> callLogin(@Body ModelLoginRequest modelLoginRequest);

    @POST(Constant.subUrl + "user/signupDelivery")
    Call<ModelCommenResponse> callSignupDelivery(@Body ModelSignUpRequest modelSignUpRequest);

    @POST(Constant.subUrl + "user/signup")
    Call<ModelCommenResponse> callSignup(@Body ModelSignUpRequest modelSignUpRequest);

    @POST(Constant.subUrl + "user/ActivePhone")
    Call<ModelCommenResponse> callActivePhone(@Body ModelConfirmationCodeRequest modelConfirmationCodeRequest);

    @POST(Constant.subUrl + "user/changepassword")
    Call<ModelCommenResponse> callChangePassword(@Body ModelChangePasswordRequest modelChangePasswordRequest);

    @GET(Constant.subUrl + "map")
    Call<ModelCommenResponse> callMap();

    @GET(Constant.subUrl + "user/profile")
    Call<ModelCommenResponse> callGetProfile();

    @GET(Constant.subUrl + "delivery/{id}")
    Call<ModelCommenResponse> callDelivery(@Path("id") int id);

    @Multipart
    @POST(Constant.subUrl + "user/updateprofile")
    Call<ModelCommenResponse> callUpdateprofile(@Part MultipartBody.Part photo, @PartMap() Map<String, RequestBody> partMap);

    //endregion

    //region chat

    @GET(Constant.subUrl + "chat")
    Call<ModelCommenResponse> callChat();

    @POST(Constant.subUrl + "chat/new")
    Call<ModelCommenResponse> callChatNew(@Body ModelChatNewRequest modelChatNewRequest);

    @POST(Constant.subUrl + "chat/acceptInvoice")
    Call<ModelCommenResponse> callAcceptInvoice(@Body ModelChatNewRequest modelChatNewRequest);

    @GET(Constant.subUrl + "chat/{id}")
    Call<ModelCommenResponse> callChatWith(@Path("id") int id);

    @Multipart
    @POST(Constant.subUrl + "chat/uploadPhoto")
    Call<ModelCommenResponse> callUploadPhoto(@Part MultipartBody.Part photo);

    //endregion

    //region products

    @GET(Constant.subUrl + "products")
    Call<ModelCommenResponse> callProducts(@Query("city_id") int city_id, @Query("category_id") int category_id, @Query("q") String q);

    @GET(Constant.subUrl + "categories")
    Call<ModelCommenResponse> callcategories();

    @GET(Constant.subUrl + "cities")
    Call<ModelCommenResponse> callCities();

    @GET(Constant.subUrl + "products/{id}")
    Call<ModelCommenResponse> callProductsInfo(@Path("id") int id);

    @GET(Constant.subUrl + "ProductsByUser/{id}")
    Call<ModelCommenResponse> callProductsByUser(@Path("id") int id);

    @POST(Constant.subUrl + "products/delete")
    Call<ModelCommenResponse> callDeleteProduct(@Body ModelAddProductRequest body);

    @Multipart
    @POST(Constant.subUrl + "products/add")
    Call<ModelCommenResponse> callAddProduct(@Part MultipartBody.Part photo, @PartMap() Map<String, RequestBody> partMap);

    @Multipart
    @POST(Constant.subUrl + "products/update")
    Call<ModelCommenResponse> callUpdateProduct(@Part MultipartBody.Part photo, @PartMap() Map<String, RequestBody> partMap);
    //endregion

    //region customer

    @GET(Constant.subUrl + "invoices/customer")
    Call<ModelCommenResponse> callInvoicesCustomer();

    @GET(Constant.subUrl + "invoices/{id}/details")
    Call<ModelCommenResponse> callInvoiceDetails(@Path("id") int id);

    //endregion

    //region delivery

    @GET(Constant.subUrl + "invoices/delivery")
    Call<ModelCommenResponse> callInvoicesDelivery();


    //endregion

    //region special orders

    @Multipart
    @POST(Constant.subUrl + "PrivateOrder/new")
    Call<ModelCommenResponse> callPrivateOrder(@Part MultipartBody.Part photo, @PartMap() Map<String, RequestBody> partMap);


    //endregion
}

