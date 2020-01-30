package com.example.myapplication.network;

import com.example.myapplication.model.Affaire;
import com.example.myapplication.model.ForecastResult;
import com.example.myapplication.model.Weather;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by Nicolas Churlet on 21/03/2018.
 */

public interface ErpInterventionApiService {
    // get an id of a city and a Api id to display some information of the weather of the city
    @GET("forecast?cnt=8&units=metric")
    Call<ForecastResult> getForecastById(
            @Query("id") int cityId,
            @Query("APPID") String apiKey
    );

    @GET("/api/weather/{city}")
    Call<Weather> getWeatherByName(@Path("city") String cityName);

}
