package it.leenergy.com.deal.`object`

/**
 * Created by IT-LeEnergy on 29/12/2017.
 */
data class LatLngJobSeeker (private var _latitude: Double,
                            private var _longitude: Double,
                            private var _nickname: String,
                            private var _jobseeker_id: String) {

    var latitude : Double = _latitude
//        get() {
//            return latitude
//        }
    var longitude : Double = _longitude
//        get() {
//            return longitude
//        }
    var nickname : String = _nickname
//        get() {
//            return nickname
//        }
    var jobseeker_id : String = _jobseeker_id
//        get() {
//            return jobseeker_id
//        }

}