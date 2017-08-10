package humazed.github.com.egyptiontrainline_u.widgets;


import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import humazed.github.com.egyptiontrainline_u.R;
import humazed.github.com.egyptiontrainline_u.model.Result;
import humazed.github.com.egyptiontrainline_u.ui.MainActivity;

/**
 * User: huma
 * Date: 04-Sep-16
 */
public class ResultsWidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private final String TAG = ResultsWidgetRemoteViewsService.class.getSimpleName();

            int appWidgetId = intent.getIntExtra(ResultWidget.KEY_APP_WIDGET_ID, 0);

            private ArrayList<Result> mResults;

            @Override
            public void onCreate() {
                mResults = MainActivity.loadResultsPref(getApplicationContext(), appWidgetId);
            }

            @Override
            public void onDataSetChanged() {
                mResults = MainActivity.loadResultsPref(getApplicationContext(), appWidgetId);
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION || mResults == null) return null;

                Result result = mResults.get(position);

                RemoteViews views = new RemoteViews(getPackageName(), R.layout.row_widget);

                views.setTextViewText(R.id.nameTextView,
                        String.format("#%s1  arrives: %s2 - leaves: %s3",
                                result.trainNumber(), result.startTime().toLocalTime(), result.arriveTime().toLocalTime()));

                // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
//                Intent intent = new Intent();
//                intent.putExtra(KEY_STEPS, new ArrayList<>(mResults.steps()));
//                intent.putExtra(KEY_POSITION, position);
//
//                views.setOnClickFillInIntent(R.id.row_steps_container, intent);

                return views;
            }

            @Override
            public int getViewTypeCount() { return 1; }

            @Override
            public int getCount() { return mResults == null ? 0 : mResults.size(); }

            @Override
            public void onDestroy() { }

            @Override
            public RemoteViews getLoadingView() { return null; }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}