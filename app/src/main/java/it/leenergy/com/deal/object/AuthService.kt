package it.leenergy.com.deal.`object`

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import it.leenergy.com.deal.classess.App
import it.leenergy.com.deal.pbfunction.REGISTER_API
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by IT-LeEnergy on 06/01/2018.
 */
object AuthService {
    fun UsersRegister(users_id_number:String, users_mobile_number:String, users_password:String, complete:(Boolean) -> Unit){

        val registerRequest = object : StringRequest(Request.Method.POST, REGISTER_API,
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        if (!obj.getBoolean("error")) {
                            App.prefs.users_id_number = obj.getString("users_id_number")
                            App.prefs.users_mobile_number = obj.getString("users_mobile_number")
                            App.prefs.users_api_key = obj.getString("users_api_key")
                            complete(true)
                        } else {
                            println(obj.getString("error_msg"))
                            complete(false)
                        }
                    } catch (e: JSONException) {
                        Log.d("JSON-REG", "EXC:" + e.localizedMessage)
                        complete(false)
                    }
                },
                Response.ErrorListener {  error ->
                    println("Could not register user: $error")
                    complete(false)
                }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("tag", "usersregister")
                params.put("users_id_number", users_id_number)
                params.put("users_mobile_number", users_mobile_number)
                params.put("users_password", users_password)

                return params
            }
        }
        App.prefs.requestQueue.add(registerRequest)
    }

    fun ReSendOTP(users_id_number: String, users_mobile_number: String, users_api_key: String, complete: (Boolean) -> Unit) {
        val otpRequest = object : StringRequest(Request.Method.POST, REGISTER_API,
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        if (!obj.getBoolean("error")){
                            complete(true)
                        } else {
                            println(obj.getString("error_msg"))
                            complete(false)
                        }
                    } catch (e: JSONException) {
                        Log.d("JSON-OTP", "EXC:" + e.localizedMessage)
                        complete(false)
                    }
                },
                Response.ErrorListener { error ->
                    println("Could not OTP: $error")
                    complete(false)
                }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("tag", "resendotp")
                params.put("users_id_number", users_id_number)
                params.put("users_mobile_number", users_mobile_number)
                params.put("users_api_key", users_api_key)

                return params
            }
        }

        App.prefs.requestQueue.add(otpRequest)
    }

    fun VerificationCode (users_id_number: String, users_mobile_number: String, users_api_key: String, smscodes_code: String, complete:(Boolean) -> Unit) {
        val verificationRequest = object : StringRequest(Request.Method.POST, REGISTER_API,
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        if (!obj.getBoolean("error")){
                            complete(true)
                        } else {
                            println(obj.getString("error_msg"))
                            complete(false)
                        }
                    } catch (e: JSONException) {
                        Log.d("JSON-OTP", "EXC:" + e.localizedMessage)
                        complete(false)
                    }
                },
                Response.ErrorListener { error ->
                    println("Could not OTP: $error")
                    complete(false)
                }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("tag", "verifycode")
                params.put("users_id_number", users_id_number)
                params.put("users_mobile_number", users_mobile_number)
                params.put("users_api_key", users_api_key)
                params.put("smscodes_code", smscodes_code)

                return params
            }
        }
        App.prefs.requestQueue.add(verificationRequest)
    }
}