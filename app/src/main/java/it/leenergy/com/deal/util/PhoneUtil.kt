package it.leenergy.com.deal.util

import android.content.Context
import android.telephony.TelephonyManager



/**
 * Created by IT-LeEnergy on 05/01/2018.
 */
object PhoneUtil {
    fun getCountryCode(context: Context): String? {
        val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (manager != null) {
            getCountryCodeFromMap(manager.simCountryIso.toUpperCase())
        } else {
            null

        }
    }

    private fun getCountryCodeFromMap(countryCode: String): String? {
        val countryCodeMap = HashMap<String, String>()

        countryCodeMap.put("BN", "+673")
        return countryCodeMap.get(countryCode)
    }
}