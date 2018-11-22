package com.solution.internet.shopping.retorfitconfig;

import com.solution.internet.shopping.models.ModelAddProductRequest.ModelAddProductRequest;
import com.solution.internet.shopping.models.ModelChangePasswordRequest.ModelChangePasswordRequest;
import com.solution.internet.shopping.models.ModelCommenResponse.ModelCommenResponse;
import com.solution.internet.shopping.models.ModelLoginRequest.ModelLoginRequest;
import com.solution.internet.shopping.models.ModelLoginResponse.ModelLoginResponse;
import com.solution.internet.shopping.models.ModelSignUpRequest.ModelSignUpRequest;
import com.solution.internet.shopping.utlities.Constant;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCall {

    //region user
    @POST(Constant.subUrl + "user/login")
    Call<ModelCommenResponse> callLogin(@Body ModelLoginRequest modelLoginRequest);

    @POST(Constant.subUrl + "user/signup")
    Call<ModelCommenResponse> callSignup(@Body ModelSignUpRequest modelSignUpRequest);

    @POST(Constant.subUrl + "user/changepassword")
    Call<ModelCommenResponse> callChangePassword(@Body ModelChangePasswordRequest modelChangePasswordRequest);

    @GET(Constant.subUrl + "map")
    Call<ModelCommenResponse> callMap();

    @GET(Constant.subUrl + "user/profile")
    Call<ModelCommenResponse> callGetProfile();

    @GET(Constant.subUrl + "delivery/{id}")
    Call<ModelCommenResponse> callDelivery(@Path("id") int id);

    //endregion

    //region chat

    @GET(Constant.subUrl + "chat")
    Call<ModelCommenResponse> callChat();

    @GET(Constant.subUrl + "/chat/{id}")
    Call<ModelCommenResponse> callChatWith(@Path("id") int id);

    @Multipart
    @POST(Constant.subUrl + "chat/uploadPhoto")
    Call<ModelCommenResponse> callUploadPhoto(@Part MultipartBody.Part postImage);

    //endregion

    //region products

    @GET(Constant.subUrl + "products")
    Call<ModelCommenResponse> callProducts(@Query("city_id") int city_id, @Query("category_id") int category_id);

    @GET(Constant.subUrl + "categories")
    Call<ModelCommenResponse> callcategories();

    @GET(Constant.subUrl + "cities")
    Call<ModelCommenResponse> callCities();

    @GET(Constant.subUrl + "products/{id}")
    Call<ModelCommenResponse> callProductsInfo(@Path("id") int id);

    @GET(Constant.subUrl + "ProductsByUser/{id}")
    Call<ModelCommenResponse> callProductsByUser(@Path("id") int id);

    /*@Multipart*/
    @POST(Constant.subUrl + "products/add")
    Call<ModelCommenResponse> callProductsAdd(/*@Part MultipartBody.Part photo,*/ @Body ModelAddProductRequest modelAddProductRequest);

    //endregion
}

