package com.example.myapplication.recyclerview.recyclerview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.activity.DetailsActivity
import com.example.myapplication.model.ForecastList
import com.example.myapplication.model.ForecastResult
import kotlinx.android.synthetic.main.item_forecast.view.*
import java.time.LocalDateTime

class ForecastAdapter(private val myDataset: MutableList<ForecastList>)  : RecyclerView.Adapter<ForecastAdapter.MachinViewHolder>() {



    class MachinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val _label = itemView.item_machin_label
        val _desc = itemView.item_machin_desc
        val _icon = itemView.item_machin_icon
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ForecastAdapter.MachinViewHolder {
        // create a new view
        var itemView =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_forecast, parent, false)
                    as View
        return MachinViewHolder(itemView)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MachinViewHolder, position: Int) {
        
        val data = myDataset[position]
        
        holder._label.text = data.weather[0].main
        holder._desc.text = " ${data.main.temp.toString()} °C"

        //Glide allow us to get an icon
        Glide.with(holder._icon.context)
            .load("https://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png")
            .into(holder._icon)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            val current = LocalDateTime.now()

            intent.putExtra("time", current)
            intent.putExtra("temp", data.weather[0].description)
            intent.putExtra("degres",  "${data.main.temp.toString()} °C")
            intent.putExtra("vent", "Vent : ${data.wind.speed * 3.6} Km/h" )
            intent.putExtra("icon", "https://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png")

            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount() = myDataset.size
}
