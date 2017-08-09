package humazed.github.com.egyptiontrainline_u.ui.result;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import humazed.github.com.egyptiontrainline_u.R;

public class JourneyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            JourneyFragment fragment = JourneyFragment.newInstance(getIntent().getParcelableExtra(JourneyFragment.ARG_RESULT));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.journey_list_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, ResultListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
