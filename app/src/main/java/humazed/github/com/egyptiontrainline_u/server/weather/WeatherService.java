package humazed.github.com.egyptiontrainline_u.server.weather;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

import humazed.github.com.egyptiontrainline_u.model.weather.Weather;
import java8.util.function.Consumer;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * User: YourPc
 * Date: 8/11/2017
 */

public class WeatherService {
    private static final String TAG = WeatherService.class.getSimpleName();

    private static final String API_KEY = "9e260ab03af5814e41fd7315ef2968f2";

    public static void getWeather(Context context, String city, Consumer<Weather> onLoadFinish) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                final OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + API_KEY)
                        .build();

                try {
                    String response = client.newCall(request).execute().body().string();
                    Weather weather = new Gson().fromJson(response, Weather.class);

                    onLoadFinish.accept(weather);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(city);


    }

}
