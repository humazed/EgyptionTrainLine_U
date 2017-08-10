package humazed.github.com.egyptiontrainline_u.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import humazed.github.com.egyptiontrainline_u.util.auto_gson.AutoGson;

/**
 * User: YourPc
 * Date: 8/9/2017
 */
@AutoGson
@AutoValue
public abstract class Station implements Parcelable {
    public abstract String id();

    public abstract String stationName();

    public abstract String stationArabic();

    public static Station create(String id, String stationName, String stationArabic) {return new AutoValue_Station(id, stationName, stationArabic);}

}
