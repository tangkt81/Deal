package it.leenergy.com.deal.jobseeker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import it.leenergy.com.deal.R
import kotlinx.android.synthetic.main.activity_jsreg.*
import it.leenergy.com.deal.`pbfunction`.*

class JSRegActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jsreg)
        invisibleOfAllErrMessage()
        btnSubmit.setOnClickListener{
            println("Password & ID Number ${checkPasswordAndIDNumber(txtIDNumber.text.trim().toString(), txtPassword.text.trim().toString())}")
            println("Password ${checkPasswordPattern(txtPassword.text.trim().toString())}")
            println("Confirm ${checkConfirmPassword(txtPassword.text.trim().toString(), txtConfirmPassword.text.trim().toString())}")
        }
    }

    fun invisibleOfAllErrMessage(){
        tvErrIDNumber.visibility = View.GONE
        tvErrMobileNumber.visibility = View.GONE
        tvErrPassword.visibility = View.GONE
        tvErrConfirmPassword.visibility = View.GONE
        tvErrAgree.visibility = View.GONE
    }
}
