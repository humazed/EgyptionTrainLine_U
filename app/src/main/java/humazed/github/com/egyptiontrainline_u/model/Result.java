package humazed.github.com.egyptiontrainline_u.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import humazed.github.com.egyptiontrainline_u.util.auto_gson.AutoGson;


/**
 * User: YourPc
 * Date: 8/9/2017
 */
@AutoGson
@AutoValue
public abstract class Result implements Parcelable {
    public abstract int trainID();

    public abstract int startStationID();

    public abstract int arriveStationID();

    public abstract LocalDateTime startTime();

    public abstract LocalDateTime arriveTime();

    public abstract String type();

    public abstract String typeArabic();

    public abstract String workingState();

    public abstract int fKTrainLineID();

    public abstract int trainNumber();

    public abstract Station startStation();

    public abstract Station arrivalStation();

    public abstract List<Change> changes();

    public static Result create(int trainID, int startStationID, int arriveStationID, LocalDateTime startTime, LocalDateTime arriveTime, String type, String typeArabic, String workingState, int fKTrainLineID, int trainNumber, Station startStation, Station arrivalStation, List<Change> changes) {return new AutoValue_Result(trainID, startStationID, arriveStationID, startTime, arriveTime, type, typeArabic, workingState, fKTrainLineID, trainNumber, startStation, arrivalStation, changes);}

}
