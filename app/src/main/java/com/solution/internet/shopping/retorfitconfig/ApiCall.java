package com.solution.internet.shopping.retorfitconfig;

import com.solution.internet.shopping.models.ModelAddProductRequest.ModelAddProductRequest;
import com.solution.internet.shopping.models.ModelChangePasswordRequest.ModelChangePasswordRequest;
import com.solution.internet.shopping.models.ModelChatNewRequest.ModelChatNewRequest;
import com.solution.internet.shopping.models.ModelCommenResponse.ModelCommenResponse;
import com.solution.internet.shopping.models.ModelConfirmSeller.ModelConfirmSeller;
import com.solution.internet.shopping.models.ModelConfirmationCodeRequest.ModelConfirmationCodeRequest;
import com.solution.internet.shopping.models.ModelLoginRequest.ModelLoginRequest;
import com.solution.internet.shopping.models.ModelRefreshTokenRequest.ModelRefreshTokenRequest;
import com.solution.internet.shopping.models.ModelSignUpRequest.ModelSignUpRequest;
import com.solution.internet.shopping.models.ModelPrivateOrderOfferNewRequest.ModelPrivateOrderOfferNewRequest;
import com.solution.internet.shopping.utlities.Constant;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCall {

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

    @POST(Constant.subUrl + "user/refreshtoken")
    Call<ModelCommenResponse> callRefreshToken(@Body ModelRefreshTokenRequest model);

    @GET(Constant.subUrl + "map")
    Call<ModelCommenResponse> callMap();

    @GET(Constant.subUrl + "user/notifications")
    Call<ModelCommenResponse> callGetNotifications();

    @GET(Constant.subUrl + "user/notifications/clear")
    Call<ModelCommenResponse> callClearNotifications();

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

    @Multipart
    @POST(Constant.subUrl + "chat/acceptInvoice")
    Call<ModelCommenResponse> callAcceptInvoice(@Part MultipartBody.Part photo, @PartMap() Map<String, RequestBody> partMap);


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

    @POST(Constant.subUrl + "products/report")
    Call<ModelCommenResponse> callReportProduct(@Body ModelAddProductRequest body);

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

    @POST(Constant.subUrl + "invoices/confirm_seller")
    Call<ModelCommenResponse> callConfirm_seller(@Body ModelConfirmSeller modelConfirmSeller);

    @Multipart
    @POST(Constant.subUrl + "user/sellerTransfers")
    Call<ModelCommenResponse> callSellerTransfers(@Part MultipartBody.Part photo, @PartMap() Map<String, RequestBody> partMap);

    //endregion

    //region special orders

    @Multipart
    @POST(Constant.subUrl + "PrivateOrder/new")
    Call<ModelCommenResponse> callPrivateOrderNew(@Part MultipartBody.Part photo, @PartMap() Map<String, RequestBody> partMap);


    @POST(Constant.subUrl + "PrivateOrder/offer/new")
    Call<ModelCommenResponse> callPrivateOrderOfferNew(@Body ModelPrivateOrderOfferNewRequest body);

    @POST(Constant.subUrl + "PrivateOrder/offer/accept")
    Call<ModelCommenResponse> callPrivateOrderOfferAccept(@Body ModelPrivateOrderOfferNewRequest body);

    @POST(Constant.subUrl + "PrivateOrder/offer/reject")
    Call<ModelCommenResponse> callPrivateOrderOfferReject(@Body ModelPrivateOrderOfferNewRequest body);

    @GET(Constant.subUrl + "PrivateOrder")
    Call<ModelCommenResponse> callPrivateOrder();


    @GET(Constant.subUrl + "PrivateOrder/getUserPrivateOrders")
    Call<ModelCommenResponse> callPrivateOrderGetUserPrivateOrders();


    @GET(Constant.subUrl + "PrivateOrder/{id}")
    Call<ModelCommenResponse> callPrivateOrderInfo(@Path("id") int id);

    //endregion

    //region pages

    @GET(Constant.subUrl + "contact")
    Call<ModelCommenResponse> callGetContact();

    //endregion
}

