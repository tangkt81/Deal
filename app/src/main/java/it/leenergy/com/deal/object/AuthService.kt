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
                        App.prefs.users_id_number = obj.getString("users_id_number")
                        App.prefs.users_mobile_number = obj.getString("users_mobile_number")
                        App.prefs.users_api_key = obj.getString("users_api_key")

                        complete(true)
                    } catch (e: JSONException) {
                        Log.d("JSON", "EXC:" + e.localizedMessage)
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

//        val jsonBody = JSONObject()
//        jsonBody.put("tag", "usersregister")
//        jsonBody.put("users_id_number", users_id_number)
//        jsonBody.put("users_mobile_number", users_mobile_number)
//        jsonBody.put("users_password", users_password)
//        val requestBody = jsonBody.toString()
//
//        val registerRequest = object : StringRequest(Request.Method.POST, REGISTER_API, Response.Listener { response ->
//
//            try {
//                //val obj = JSONObject(response)
//                Log.d("Response", "EXC:" + response)
//                //println(obj.getString("users_id_number"))
////                App.prefs.users_id_number = response.getString("users_id_number")
////                App.prefs.users_mobile_number = response.getString("users_mobile_number")
////                App.prefs.users_api_key = response.getString("users_api_key")
//                //complete(true)
//            } catch (e: JSONException) {
//                Log.d("JSON", "EXC:" + e.localizedMessage)
//                complete(false)
//            }
//        }, Response.ErrorListener { error ->
//            println("Could not register user: $error")
//            complete(false)
//        }) {
//            override fun getBodyContentType(): String {
//                return "application/json; charset=utf-8"
//            }
//
//            override fun getBody(): ByteArray {
//                return requestBody.toByteArray()
//            }
//        }
//        App.prefs.requestQueue.add(registerRequest)

    }
}