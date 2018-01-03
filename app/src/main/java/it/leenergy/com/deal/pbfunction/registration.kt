package it.leenergy.com.deal.pbfunction

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by IT-LeEnergy on 03/01/2018.
 */
fun checkPasswordAndIDNumber(_idNumber : String, _password : String) : Boolean{

    val separate1fpw  = _password.split(_idNumber.toRegex())
    val separatelfid = _idNumber.split(_password.toRegex())

    return (separate1fpw.size <= 1 && separatelfid.size <= 1)
}

fun checkPasswordPattern(_password: String) : Boolean{
    //must be contain letter A-Z/a-z and number 0-9 and length 6 - 15
    val PASSWORD_PATTERN : String = "((?=.*\\d)(?=.*[a-zA-Z]).{6,15})"
    val pattern : Pattern = Pattern.compile(PASSWORD_PATTERN)
    val matcher : Matcher = pattern.matcher(_password)

    return matcher.matches()
}

fun checkConfirmPassword(_password: String, _confirmPassword : String) : Boolean{
    return (_password.contentEquals(_confirmPassword))
}