package humazed.github.com.egyptiontrainline_u.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import humazed.github.com.egyptiontrainline_u.model.Change;
import humazed.github.com.egyptiontrainline_u.model.Result;
import humazed.github.com.egyptiontrainline_u.model.Station;
import humazed.github.com.egyptiontrainline_u.util.DateUtil;

/**
 * User: YourPc
 * Date: 8/9/2017
 */

public class Db {

    private static final String QUERY_STATION = "SELECT * FROM Station";

    private static final String QUERY_RESULT = "SELECT\n" +
            "   Train._id                 TrainID,\n" +
            "   start.FK_StartStationID   StartStationID,\n" +
            "   arrive.FK_ArriveStationID ArriveStationID,\n" +
            "   start.StartTime           StartTime,\n" +
            "   arrive.ArriveTime         ArriveTime,\n" +
            "   TrainNumber,\n" +
            "   Type,\n" +
            "   TypeArabic,\n" +
            "   State WorkingState,\n" +
            "   FK_TrainLineID\n" +
            " FROM\n" +
            "   (SELECT *\n" +
            "    FROM Travel t1\n" +
            "    WHERE FK_StartStationID = :arg0) start\n" +
            "   INNER JOIN\n" +
            "   (SELECT *\n" +
            "    FROM Travel\n" +
            "    WHERE FK_ArriveStationID = :arg1) arrive\n" +
            "     ON start.FK_TrainID = arrive.FK_TrainID\n" +
            "   INNER JOIN Train ON start.FK_TrainID = Train._id\n" +
            "   INNER JOIN WorkingState ON FK_WorkingState = WorkingState._id\n" +
            "   INNER JOIN TrolleyType ON FK_TrolleyTypeID = TrolleyType._id\n" +
            " WHERE FK_WorkingState IN (1, 2)\n" +
            " ORDER BY start.StartTime";

    private static final String QUERY_CHANGES = "SELECT\n" +
            "  startStation.StationName   StartStationName,\n" +
            "  arrivalStation.StationName ArriveStationName,\n" +
            "  StartTime,\n" +
            "  ArriveTime\n" +
            "FROM Travel\n" +
            "  INNER JOIN Station startStation ON FK_StartStationID = startStation._id\n" +
            "  INNER JOIN Station arrivalStation ON FK_ArriveStationID = arrivalStation._id\n" +
            "  INNER JOIN StationOrder ON StationOrder.StationID = startStation._id AND StationOrder.TrainLineID = :arg0\n" +
            "WHERE FK_TrainID = :arg1\n" +
            "ORDER BY Ordering";

    public static ArrayList<Station> getStations(Context context) {
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY_STATION, null);

        ArrayList<Station> stations = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                stations.add(Station.create(
                        getString(cursor, "_id"),
                        getString(cursor, "StationName"),
                        getString(cursor, "StationArabic")
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return stations;
    }

    public static ArrayList<Result> getResults(Station startStation, Station arrivalStation, Context context) {
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY_RESULT, new String[]{startStation.id(), arrivalStation.id()});

        ArrayList<Result> results = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                results.add(Result.create(
                        getInt(cursor, "TrainID"),
                        getInt(cursor, "StartStationID"),
                        getInt(cursor, "ArriveStationID"),
                        DateUtil.fromTimestamp(getString(cursor, "StartTime")),
                        DateUtil.fromTimestamp(getString(cursor, "ArriveTime")),
                        getString(cursor, "Type"),
                        getString(cursor, "TypeArabic"),
                        getString(cursor, "WorkingState"),
                        getInt(cursor, "FK_TrainLineID"),
                        startStation,
                        arrivalStation,
                        getChanges(getInt(cursor, "FK_TrainLineID"), getInt(cursor, "TrainID"), db)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return results;
    }

    private static List<Change> getChanges(int trainLineID, int trainID, SQLiteDatabase db) {
        Cursor cursor = db.rawQuery(QUERY_CHANGES, new String[]{String.valueOf(trainLineID), String.valueOf(trainID)});

        ArrayList<Change> changes = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                changes.add(Change.create(
                        getString(cursor, "StartStationName"),
                        getString(cursor, "ArriveStationName"),
                        DateUtil.fromTimestamp(getString(cursor, "StartTime")),
                        DateUtil.fromTimestamp(getString(cursor, "ArriveTime"))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return changes;
    }


    private static String getString(Cursor cursor, String s) { return cursor.getString(cursor.getColumnIndex(s)); }

    private static int getInt(Cursor cursor, String s) { return cursor.getInt(cursor.getColumnIndex(s)); }
}
