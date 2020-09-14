package me.duvu.loginapplication.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import me.duvu.loginapplication.ApiClient;
import me.duvu.loginapplication.data.model.LoggedInUser;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public LiveData<LoggedInUser> login(String username, String password) {
        // handle login
        LoginInterface loginInterface = ApiClient.getRetrofit().create(LoginInterface.class);
        String credStr = "grant_type=password&scope=read%20write&username=nzsysadmin&password=12345678";

        final MutableLiveData<LoggedInUser> data = new MutableLiveData<>();

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), credStr);
        loginInterface.postLogin(body).enqueue(new Callback<LoggedInUser>() {
            @Override
            public void onResponse(Call<LoggedInUser> call, Response<LoggedInUser> response) {
                LoggedInUser user = response.body();
                setLoggedInUser(user);
                data.setValue(user);
            }

            @Override
            public void onFailure(Call<LoggedInUser> call, Throwable t) {

            }
        });
        return data;
    }
}