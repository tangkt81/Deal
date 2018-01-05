package it.leenergy.com.deal.jobseeker.registration

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import it.leenergy.com.deal.R
import it.leenergy.com.deal.`object`.ToolTips
import kotlinx.android.synthetic.main.activity_jsreg.*
import it.leenergy.com.deal.`pbfunction`.*
import it.leenergy.com.deal.util.PhoneUtil

class JSRegActivity : AppCompatActivity() {

    lateinit var toolTips : ToolTips

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jsreg)
        invisibleOfAllErrMessage()
        toolTips = ToolTips(this)

        txtCountryCode.setText(PhoneUtil.getCountryCode(this))
        btnSubmit.setOnClickListener{
            invisibleOfAllErrMessage()
            if (!checkTextField()){
                val intent = Intent(this, JSSMSVerifyActivity::class.java)
                startActivity(intent)
//                println("Password & ID Number ${checkPasswordAndIDNumber(txtIDNumber.text.trim().toString(), txtPassword.text.trim().toString())}")
//                println("Password ${checkPasswordPattern(txtPassword.text.trim().toString())}")
//                println("Confirm ${checkConfirmPassword(txtPassword.text.trim().toString(), txtConfirmPassword.text.trim().toString())}")
            }

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
