package it.leenergy.com.deal.`object`

/**
 * Created by IT-LeEnergy on 29/12/2017.
 */
data class LatLngJobSeeker (private val _latitude: Double,
                            private val _longitude: Double,
                            private val _nickname: String,
                            private val _jobseeker_id: String) {

    var latitude : Double = _latitude
//        get() {
//            return latitude
//        }
    var longitude : Double = _longitude

    var nickname : String = _nickname

    var jobseeker_id : String = _jobseeker_id

}