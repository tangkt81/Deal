package it.leenergy.com.deal
import it.leenergy.com.deal.`employer`.*
import it.leenergy.com.deal.jobseeker.registration.*
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnJobseeker.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, JSRegActivity::class.java)
            startActivity(intent)
        })

        btnEmployer.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, jobseekerMapsActivity::class.java)
            startActivity(intent)
        })
    }
}
