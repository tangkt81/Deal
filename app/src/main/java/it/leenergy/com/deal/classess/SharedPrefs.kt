package it.leenergy.com.deal.classess

import android.content.Context
import android.content.SharedPreferences
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

/**
 * Created by IT-LeEnergy on 08/01/2018.
 */
class SharedPrefs(context: Context) {
    val PREFS_FILENAME = "prefs"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    val IS_LOGGED_IN = "isLoggedIN"
    val USERS_ID_NUMBER = "users_id_number"
    val USERS_MOBILE_NUMBER = "users_mobile_number"
    val USERS_API_KEY = "users_api_key"


    var is_logged_in : Boolean
        get() = prefs.getBoolean(IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(IS_LOGGED_IN, value).apply()

    var users_id_number : String
        get() = prefs.getString(USERS_ID_NUMBER, "")
        set(value) = prefs.edit().putString(USERS_ID_NUMBER, value).apply()

    var users_mobile_number : String
        get() = prefs.getString(USERS_MOBILE_NUMBER, "")
        set(value) = prefs.edit().putString(USERS_MOBILE_NUMBER, value).apply()

    var users_api_key : String
        get() = prefs.getString(USERS_API_KEY, "")
        set(value) = prefs.edit().putString(USERS_API_KEY, value).apply()


    val requestQueue : RequestQueue = Volley.newRequestQueue(context)

}