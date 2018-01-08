package it.leenergy.com.deal.registration

import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import it.leenergy.com.deal.R
import it.leenergy.com.deal.classess.App
import kotlinx.android.synthetic.main.activity_smsverify.*

class SMSVerifyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smsverify)

        txtVerificationCode.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);

        println("${App.prefs.users_id_number}, ${App.prefs.users_mobile_number}, ${App.prefs.users_api_key}")
    }
}
