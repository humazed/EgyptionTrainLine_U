package humazed.github.com.egyptiontrainline_u.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import humazed.github.com.egyptiontrainline_u.R;
import humazed.github.com.egyptiontrainline_u.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link MainActivity}
 */
public class ResultWidget extends AppWidgetProvider {

    public static final String KEY_APP_WIDGET_ID = "appWidgetId";

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_result);

        Intent intent = new Intent(context, ResultsWidgetRemoteViewsService.class);
        intent.putExtra(KEY_APP_WIDGET_ID, appWidgetId);
        views.setRemoteAdapter(R.id.resultsListView, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            MainActivity.deleteResultsPref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

