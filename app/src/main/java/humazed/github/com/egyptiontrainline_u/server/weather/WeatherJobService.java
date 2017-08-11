package humazed.github.com.egyptiontrainline_u.server.weather;

import android.content.Context;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.gson.Gson;

import humazed.github.com.egyptiontrainline_u.R;

public class WeatherJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters job) {
        // Do some work here
        WeatherService.getWeather(this, "cairo", weather -> {

            String json = new Gson().toJson(weather);
            getSharedPreferences(getString(R.string.pref_weather), Context.MODE_PRIVATE).edit()
                    .putString(getString(R.string.pref_weather_gson), json)
                    .apply();

            jobFinished(job, false);
        });

        return false; // Answers the question: "Is there still work going on?"
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false; // Answers the question: "Should this job be retried?"
    }
}