package humazed.github.com.egyptiontrainline_u;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * User: YourPc
 * Date: 8/9/2017
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        AndroidThreeTen.init(this);
    }
}
