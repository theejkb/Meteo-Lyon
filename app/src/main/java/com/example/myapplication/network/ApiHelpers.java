package com.example.myapplication.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.model.ForecastResult;
import com.example.myapplication.model.Weather;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Nicolas Churlet on 21/03/2018.
 */

public class ApiHelpers {

    public static final String ENDPOINT = "https://api.openweathermap.org/data/2.5/";
    public static final String API_KEY = "70788afea8bdd20eb1a3853e54100cc7";

    private ErpInterventionApiService apiservice;

    final private Context _context;

    SharedPreferences preferences;
     public ApiHelpers(Context context) {

        this._context = context;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        // region OkHttpClient
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        final Request request = chain.request();

                        preferences = _context.getSharedPreferences("login", MODE_PRIVATE);
                        String token = preferences.getString("token", "");

                        final Request newRequest;
                        // cas de l'auhtnetification
                        if (request.url().encodedPathSegments().get(0).equals("api") &&
                            request.url().encodedPathSegments().get(1).equals("Auth") &&
                            request.url().encodedPathSegments().get(2).equals("Login")) {
                            newRequest = request.newBuilder()
                                    .build();
                        } else if (token == null) {
                            return null;
                        } else {
                            newRequest = request.newBuilder()
                                    //ajoute "baerer: token" en header de chaque requête
                                    .addHeader("Authorization", "Bearer " + token)
                                    .build();
                        }
                        System.out.println("request = " + request);

                        return chain.proceed(newRequest);
                    }
                })
                .build();
        // endregion OkHttpClient

        // region API service
        apiservice = new Retrofit
                .Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
                .create(ErpInterventionApiService.class);
        // endregion API service
    }

    // async method to use the request on our API openweather map
    public void ApiRequestForecastById(int cityId, ApiRequestCallback<ForecastResult> callback) {
        (new ApiRequest<ForecastResult>())
                .requestAsync(apiservice.getForecastById(cityId, API_KEY), callback);
    }
//    public void ApiRequestForecastByName(String cityName, ApiRequestCallback<ForecastResult> callback) {
//        (new ApiRequest<ForecastResult>())
//                .requestAsync(apiservice.getWeatherByName(cityName, API_KEY), callback);
//    }
}

