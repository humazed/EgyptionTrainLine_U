package humazed.github.com.egyptiontrainline_u;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
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

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
    }
}
