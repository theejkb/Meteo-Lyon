package com.example.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val bundleExtra = intent.extras

        temp.text = bundleExtra?.getString("temp")
        degres.text = bundleExtra?.getString("degres")
        vent.text = bundleExtra?.getString("vent")
        time.text = bundleExtra?.getString("time")
        Glide.with(this)
            .load(bundleExtra?.getString("icon"))
            .into(icon)
    }
}
