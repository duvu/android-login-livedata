package me.duvu.loginapplication.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import me.duvu.loginapplication.ApiClient;
import me.duvu.loginapplication.data.model.LoggedInUser;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public LiveData<LoggedInUser> login(String username, String password) {

        LoginInterface loginInterface = ApiClient.getRetrofit().create(LoginInterface.class);
        String credStr = "grant_type=password&scope=read%20write&username=nzsysadmin&password=12345678";

        final MutableLiveData<LoggedInUser> data = new MutableLiveData<>();

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), credStr);
        loginInterface.postLogin(body).enqueue(new Callback<LoggedInUser>() {
            @Override
            public void onResponse(Call<LoggedInUser> call, Response<LoggedInUser> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoggedInUser> call, Throwable t) {

            }
        });
//        try {
//            LoggedInUser loggedInUser = loginInterface.postLogin(body).execute().body();
//            return new Result.Success<>(loggedInUser);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return data;
//        try {
//            // TODO: handle loggedInUser authentication
//            LoggedInUser fakeUser =
//                    new LoggedInUser(
//                            java.util.UUID.randomUUID().toString(),
//                            "Jane Doe");
//            return new Result.Success<>(fakeUser);
//        } catch (Exception e) {
//            return new Result.Error(new IOException("Error logging in", e));
//        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}