package it.leenergy.com.deal.classess

import android.app.Application

/**
 * Created by IT-LeEnergy on 08/01/2018.
 */

class App : Application() {

    companion object {
        lateinit var prefs: SharedPrefs
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }
}