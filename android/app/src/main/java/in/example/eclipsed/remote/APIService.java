package in.example.eclipsed.remote;

import in.example.eclipsed.models.GlobalRequest;
import in.example.eclipsed.models.GlobalResponse;
import in.example.eclipsed.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @POST("register")
    @Headers("Accept: application/json")
    Call<GlobalResponse> registerUser(@Body GlobalRequest request);

    @POST("login")
    @Headers("Accept: application/json")
    Call<GlobalResponse> loginUser(@Body GlobalRequest request);

    @GET("my-messages")
    @Headers("Accept: application/json")
    Call<GlobalResponse> getMessages(@Header("Authorization") String token);

    @POST("search-user")
    @Headers("Accept: application/json")
    Call<GlobalResponse> getUsers(@Body GlobalRequest request);

}
