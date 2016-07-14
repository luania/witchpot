package com.luania.witchpot.service;

import android.content.Context;
import android.widget.Toast;

import com.luania.witchpot.R;
import com.wilddog.client.AuthData;
import com.wilddog.client.Wilddog;

import java.util.Map;

/**
 * Created by luania on 16/7/14.
 */
public class UserService {
    public static final String WILDDOG = "https://witchpot.wilddogio.com/";
    public static final String WILDDOG_USER = "https://witchpot.wilddogio.com/user";

    public static AuthData getAutDATA() {
        return new Wilddog(WILDDOG).getAuth();
    }

    public static boolean checkAuthData(Context context){
        if (getAutDATA() == null) {
            Toast.makeText(context, "R.string.user_please_login:" + context.getString(R.string.user_please_login), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static void loginWithPassword(String email, String password, Wilddog.AuthResultHandler handler) {
        new Wilddog(WILDDOG).authWithPassword(email, password, handler);
    }

    public static void logout() {
        new Wilddog(WILDDOG).unauth();
    }

    public static void createUser(String email, String password, Wilddog.ValueResultHandler<Map<String, Object>> handler) {
        new Wilddog(WILDDOG).createUser(email, password, handler);
    }

}
