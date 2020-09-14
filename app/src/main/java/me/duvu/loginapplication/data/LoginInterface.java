package me.duvu.loginapplication.data;

import me.duvu.loginapplication.data.model.LoggedInUser;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginInterface {
    @Headers({
            "Authorization: Basic d2ViYXBwOjEyMzQ1Ng==",
            "Content-Type: application/x-www-form-urlencoded"
    })
    @POST("/oauth/token")
    Call<LoggedInUser> postLogin(@Body RequestBody body);
}
