package humazed.github.com.egyptiontrainline_u.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import org.threeten.bp.LocalDateTime;

/**
 * User: YourPc
 * Date: 8/9/2017
 */
@AutoValue
public abstract class Change implements Parcelable {
    public abstract String startStationName();

    public abstract String arriveStationName();

    public abstract LocalDateTime startTime();

    public abstract LocalDateTime arriveTime();

    public static Change create(String startStationName, String arriveStationName, LocalDateTime startTime, LocalDateTime arriveTime) {return new AutoValue_Change(startStationName, arriveStationName, startTime, arriveTime);}
}
