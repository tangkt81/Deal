package it.leenergy.com.deal.util

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import it.leenergy.com.deal.pbfunction.REGISTER_API
import org.json.JSONObject

/**
 * Created by IT-LeEnergy on 06/01/2018.
 */
object AuthService {
    fun JobSeekerRegister(context:Context, id_number:String, mobile_number:String, password:String, complete:(Boolean) -> Unit){

        val jsonBody = JSONObject()
        jsonBody.put("id_number", id_number)
        jsonBody.put("mobile_number", mobile_number)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()

        val registerRequest = object : StringRequest(Method.POST, REGISTER_API, Response.Listener { response ->
            println(response)
            complete(true)
        }, Response.ErrorListener { error ->
            println("Could not register user: $error")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(registerRequest)
    }
}