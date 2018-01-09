package it.leenergy.com.deal.registration

import android.content.Intent
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import it.leenergy.com.deal.MainActivity
import it.leenergy.com.deal.R
import it.leenergy.com.deal.`object`.AuthService
import it.leenergy.com.deal.classess.App
import kotlinx.android.synthetic.main.activity_smsverify.*

class SMSVerifyActivity : AppCompatActivity() {
    internal var isRunning = false
    internal var timeInMillis: Long = 0
    lateinit var countDownTimer : CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smsverify)

        txtVerificationCode.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.background_dark), PorterDuff.Mode.SRC_ATOP);

        println("Test : ${App.prefs.users_id_number}, ${App.prefs.users_mobile_number}, ${App.prefs.users_api_key}")

        if (!isRunning)
            CountDownStart()

    }

    fun CancelClick (view: View) {
        finish()
    }

    fun VerifyClick (view: View) {
        if (txtVerificationCode.text.trim().toString().length == 6) {
            AuthService.VerificationCode(App.prefs.users_id_number, App.prefs.users_mobile_number, App.prefs.users_api_key, txtVerificationCode.text.trim().toString()) { verifySuccess ->
                if (verifySuccess) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    fun ReSendClick (view: View) {
        if (!isRunning) {
            AuthService.ReSendOTP(App.prefs.users_id_number, App.prefs.users_mobile_number, App.prefs.users_api_key) {otpSuccess ->
                if (otpSuccess){
                    CountDownStart()
                }
            }
        }

    }

    private fun CountDownStart() {
        btnResend.visibility = View.GONE
        timeInMillis = 60000
        pbReSend.max = timeInMillis.toInt() / 1000
        isRunning = true

        countDownTimer = object : CountDownTimer(timeInMillis, 100) {
            override fun onTick(millisUntilFinished: Long) {
                tvCoutdown.text = Math.round(millisUntilFinished * 0.001f).toString()
                pbReSend.progress = Math.round(millisUntilFinished * 0.001f)

            }

            override fun onFinish() {
                isRunning = false
                btnResend.visibility = View.VISIBLE
            }
        }.start()
        countDownTimer.start()
    }
}
