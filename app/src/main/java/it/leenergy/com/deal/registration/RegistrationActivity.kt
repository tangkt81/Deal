package it.leenergy.com.deal.registration

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import it.leenergy.com.deal.R
import it.leenergy.com.deal.classess.ToolTips
import it.leenergy.com.deal.pbfunction.checkConfirmPassword
import it.leenergy.com.deal.pbfunction.checkPasswordAndIDNumber
import it.leenergy.com.deal.pbfunction.checkPasswordPattern
import it.leenergy.com.deal.`object`.GetPhoneCode
import it.leenergy.com.deal.`object`.AuthService
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    lateinit var toolTips : ToolTips

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        invisibleOfAllErrMessage()
        toolTips = ToolTips(this)

        txtCountryCode.setText(GetPhoneCode.getCountryCode(this))
        btnSubmit.setOnClickListener{
            invisibleOfAllErrMessage()
            if (!checkTextField()){
                val users_id_number = txtIDNumber.text.trim().toString()
                val users_mobile_number = txtMobile.text.trim().toString()
                val users_password = txtPassword.text.trim().toString()
                AuthService.UsersRegister(users_id_number, users_mobile_number, users_password) { registerSuccess ->
                    if (registerSuccess) {
                        val intent = Intent(this, SMSVerifyActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
//            val intent = Intent(this, SMSVerifyActivity::class.java)
//            startActivity(intent)
        }

    }

    fun IDNumberInfoOnClick(view: View){
        if (!toolTips.isTooltipShown)
            toolTips.showToolTip(view, getText(R.string.id_number_info).toString())

    }

    fun MobileNoInfoOnClick(view: View) {
        if (!toolTips.isTooltipShown)
            toolTips.showToolTip(view, getText(R.string.mobile_number_info).toString())
    }

    fun PasswordInfoOnClick(view:View){
        if (!toolTips.isTooltipShown)
            toolTips.showToolTip(view, getText(R.string.password_info).toString())
    }

    fun ConfirmPasswordInfoOnClick(view:View){
        if (!toolTips.isTooltipShown)
            toolTips.showToolTip(view, getText(R.string.confirm_password_info).toString())
    }

    fun invisibleOfAllErrMessage(){
        tvErrIDNumber.visibility = View.GONE
        tvErrMobileNumber.visibility = View.GONE
        tvErrPassword.visibility = View.GONE
        tvErrConfirmPassword.visibility = View.GONE
        tvErrAgree.visibility = View.GONE
    }


    fun checkTextField() : Boolean {
        var error : Boolean = false
        if (txtIDNumber.text.isEmpty()) {
            tvErrIDNumber.visibility = View.VISIBLE
            error = true
        }
        if (txtMobile.text.isEmpty()) {
            tvErrMobileNumber.visibility = View.VISIBLE
            error = true
        }
        if (txtPassword.text.isEmpty()) {
            tvErrPassword.visibility = View.VISIBLE
            error = true
        }
        if (txtConfirmPassword.text.isEmpty()) {
            tvErrConfirmPassword.visibility = View.VISIBLE
            error = true
        }
        if (!cbAgree.isChecked){
            tvErrAgree.visibility = View.VISIBLE
            error = true
        }
        if (txtIDNumber.text.length != 8) {
            tvErrIDNumber.visibility = View.VISIBLE
            tvErrIDNumber.text = getText(R.string.id_number_info).toString()
            error = true
        }
        if (txtMobile.text.length != 7) {
            tvErrMobileNumber.visibility = View.VISIBLE
            tvErrMobileNumber.text = getText(R.string.mobile_number_info).toString()
            error = true
        }
        if (!checkPasswordPattern(txtPassword.text.trim().toString())
                || !checkPasswordAndIDNumber(txtIDNumber.text.trim().toString(), txtPassword.text.trim().toString())) {
            tvErrPassword.visibility = View.VISIBLE
            tvErrPassword.text = getText(R.string.password_info).toString()
            error = true
        }
        if (!checkConfirmPassword(txtPassword.text.trim().toString(), txtConfirmPassword.text.trim().toString())){
            tvErrConfirmPassword.visibility = View.VISIBLE
            tvErrConfirmPassword.text = getText(R.string.confirm_password_info).toString()
            error = true
        }

        return error

    }

}