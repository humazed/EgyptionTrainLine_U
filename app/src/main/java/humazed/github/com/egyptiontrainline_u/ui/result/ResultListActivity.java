package humazed.github.com.egyptiontrainline_u.ui.result;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import humazed.github.com.egyptiontrainline_u.R;
import humazed.github.com.egyptiontrainline_u.adapters.ResultsAdapter;
import humazed.github.com.egyptiontrainline_u.database.Db;
import humazed.github.com.egyptiontrainline_u.model.Result;
import humazed.github.com.egyptiontrainline_u.model.Station;
import humazed.github.com.egyptiontrainline_u.ui.MainActivity;

import static android.support.v4.app.NavUtils.navigateUpFromSameTask;

public class ResultListActivity extends AppCompatActivity {
    private static final String TAG = ResultListActivity.class.getSimpleName();

    @BindView(R.id.resultRecyclerView) RecyclerView mResultRecyclerView;

    @BindBool(R.bool.isTablet) boolean isTablet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Station startStation = getIntent().getParcelableExtra(MainActivity.KEY_ARRIVAL_STATION);
        Station arrivalStation = getIntent().getParcelableExtra(MainActivity.KEY_START_STATION);

        ArrayList<Result> results = Db.getResults(startStation, arrivalStation, this);

        setupRecyclerView(results);

    }

    private void setupRecyclerView(ArrayList<Result> results) {
        ResultsAdapter adapter = new ResultsAdapter(results);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isTablet) {
                JourneyFragment fragment = JourneyFragment.newInstance(results.get(position));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.journey_list_container, fragment)
                        .commit();
            } else {
                Intent intent = new Intent(this, JourneyActivity.class);
                intent.putExtra(JourneyFragment.ARG_RESULT, results.get(position));
                startActivity(intent);
            }
        });
        mResultRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
