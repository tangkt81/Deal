package it.leenergy.com.deal.jobseeker.registration

import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import it.leenergy.com.deal.R
import kotlinx.android.synthetic.main.activity_jssmsverify.*

class JSSMSVerifyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jssmsverify)

        txtVerificationCode.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
    }
}
