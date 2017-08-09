package humazed.github.com.egyptiontrainline_u.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import humazed.github.com.egyptiontrainline_u.R;
import humazed.github.com.egyptiontrainline_u.custom_views.InstantAutoCompleteTextView;
import humazed.github.com.egyptiontrainline_u.database.Db;
import humazed.github.com.egyptiontrainline_u.model.Station;
import humazed.github.com.egyptiontrainline_u.ui.result.ResultListActivity;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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


    }
}

