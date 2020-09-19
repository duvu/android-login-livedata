package me.duvu.loginapplication.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.util.Patterns;

import me.duvu.loginapplication.data.LoginRepository;
import me.duvu.loginapplication.data.Result;
import me.duvu.loginapplication.data.model.LoggedInUser;
import me.duvu.loginapplication.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    private final MediatorLiveData<LoggedInUser> loggedInUser = new MediatorLiveData<>();


    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    LiveData<LoggedInUser> getLoggedInUser() {
        return loggedInUser;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        LiveData<LoggedInUser> result = loginRepository.login(username, password);

        loggedInUser.addSource(result, loggedInUser -> {
            Log.i(">_", "Updated here! #" + loggedInUser.getAccountName());
            loginResult.setValue(new LoginResult(new LoggedInUserView(loggedInUser.getAccountName())));
        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
//        }
//        if (username.contains("@")) {
//            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}