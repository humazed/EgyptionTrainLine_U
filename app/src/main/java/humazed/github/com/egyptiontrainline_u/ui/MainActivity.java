package humazed.github.com.egyptiontrainline_u.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import humazed.github.com.egyptiontrainline_u.R;
import humazed.github.com.egyptiontrainline_u.custom_views.InstantAutoCompleteTextView;
import humazed.github.com.egyptiontrainline_u.database.Db;
import humazed.github.com.egyptiontrainline_u.model.Result;
import humazed.github.com.egyptiontrainline_u.model.Station;
import humazed.github.com.egyptiontrainline_u.ui.result.ResultListActivity;
import humazed.github.com.egyptiontrainline_u.util.auto_gson.GsonAutoValue;
import humazed.github.com.egyptiontrainline_u.widgets.ResultWidget;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_CONFIGURE;
import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.getInstance;
import static humazed.github.com.egyptiontrainline_u.R.id.startAutocomplete;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String KEY_RESULTS = "results";
    public static final String KEY_START_STATION = "MainActivity:arrivalStation";
    public static final String KEY_ARRIVAL_STATION = "MainActivity:startStation";

    @BindView(R.id.fromTextView) TextView mFromTextView;
    @BindView(R.id.toTextView) TextView mToTextView;
    @BindView(R.id.toggleStations) ImageView mToggleStations;
    @BindView(R.id.goButton) Button mGoButton;
    @BindView(R.id.arrivalAutocomplete) InstantAutoCompleteTextView mStartAutocomplete;
    @BindView(startAutocomplete) InstantAutoCompleteTextView mArrivalAutocomplete;
    @BindView(R.id.from_input_layout) TextInputLayout mFromInputLayout;
    @BindView(R.id.to_input_layout) TextInputLayout mToInputLayout;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        ArrayList<Station> stations = Db.getStations(this);

        List<String> stationNames = StreamSupport.stream(stations)
                .map(Station::stationName)
                .collect(Collectors.toList());

        mStartAutocomplete.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, stationNames));
        mArrivalAutocomplete.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, stationNames));


        mGoButton.setOnClickListener(v -> {
            if (mStartAutocomplete.getText().toString().isEmpty() || mArrivalAutocomplete.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, getString(R.string.choose_station_msg), Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(this, ResultListActivity.class);
                intent.putExtra(KEY_START_STATION, stations.get(stationNames.indexOf(mStartAutocomplete.getText().toString())));
                intent.putExtra(KEY_ARRIVAL_STATION, stations.get(stationNames.indexOf(mArrivalAutocomplete.getText().toString())));
                startActivity(intent);
            }
        });


        if (getIntent().getAction().equals(ACTION_APPWIDGET_CONFIGURE)) {
            Log.d(TAG, "getIntent().getAction() = " + getIntent().getAction());
            configWidget(stations.get(stationNames.indexOf(mStartAutocomplete.getText().toString())),
                    stations.get(stationNames.indexOf(mArrivalAutocomplete.getText().toString())));
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // Widget
    ///////////////////////////////////////////////////////////////////////////
    private static final String PREFS_NAME = "humazed.github.com.egyptiontrainline_u.widgets.ResultWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = INVALID_APPWIDGET_ID;

    private void configWidget(Station startStation, Station arrivalStation) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "added widget");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        mGoButton.setOnClickListener(v -> {
            // When the button is clicked, store the string locally
            saveResultsPref(this, mAppWidgetId, Db.getResults(startStation, arrivalStation, this));

            // It is the responsibility of the configuration activity to update the app widget
            ResultWidget.updateAppWidget(this, getInstance(this), mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        });

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(EXTRA_APPWIDGET_ID, INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == INVALID_APPWIDGET_ID) finish();
    }

    // Write the prefix to the SharedPreferences object for this widget
    public static void saveResultsPref(Context context, int appWidgetId, ArrayList<Result> results) {
        context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
                .putString(PREF_PREFIX_KEY + appWidgetId, GsonAutoValue.getInstance().toJson(results))
                .apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    public static ArrayList<Result> loadResultsPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        return GsonAutoValue.getInstance()
                .fromJson(prefs.getString(PREF_PREFIX_KEY + appWidgetId, ""),
                        new TypeToken<ArrayList<Result>>() {}.getType());
    }

    public static void deleteResultsPref(Context context, int appWidgetId) {
        context.getSharedPreferences(PREFS_NAME, 0).edit()
                .remove(PREF_PREFIX_KEY + appWidgetId)
                .apply();
    }
}

