package in.example.eclipsed.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import in.example.eclipsed.models.User;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private static final String KEY_ACCESS_TOKEN = "accessToken";
    private static final String KEY_USER_DATA = "userData";
    private static String KEY_IS_LOGGED_IN = "isLoggedIn";
    public SessionManager(Context context) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = sharedPreferences.edit();
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public boolean isIsLoggedIn() { return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false); }

    public void setKeyAccessToken(String accessToken)
    {
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.commit();
    }

    public void setUserData(User user)
    {
        editor.putString(KEY_USER_DATA, new Gson().toJson(user).toString());
        editor.commit();
    }

    public String getKeyAccessToken() {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN,null);
    }

    public User getUserData()
    {
        return new Gson().fromJson(sharedPreferences.getString(KEY_USER_DATA,null),User.class);
    }

}
