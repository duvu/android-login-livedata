package me.duvu.loginapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static final String PREF_NAME = "pref-name";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setPassword(String password) {
        editor.putString("password", password);
        editor.apply();
    }

    public String getPassword() {
        return pref.getString("password", null);
    }

    public void setUsername(String username) {
        editor.putString("username", username);
        editor.apply();
    }

    public String getUsername() {
        return pref.getString("username", null);
    }

    public void setToken(String token) {
        editor.putString("token", token);
        editor.apply();
    }

    public String getToken() {
        return pref.getString("token", null);
    }
}
