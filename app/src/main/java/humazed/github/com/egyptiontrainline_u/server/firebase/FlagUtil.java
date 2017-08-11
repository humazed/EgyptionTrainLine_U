package humazed.github.com.egyptiontrainline_u.server.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java8.util.function.Consumer;


/**
 * User: YourPc
 * Date: 8/11/2017
 */

public class FlagUtil {
    private static final String TAG = FlagUtil.class.getSimpleName();
    private static final String REF_FLAGS = "flags";

    private static DatabaseReference mFlagsRef = FirebaseDatabase.getInstance().getReference(REF_FLAGS);

    public static void flag(int trainId) {
        DatabaseReference trainRef = mFlagsRef.child(String.valueOf(trainId));

        trainRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer flagCount = mutableData.getValue(Integer.class);
                if (flagCount == null) {
                    mutableData.setValue(1);
                } else {
                    mutableData.setValue(flagCount + 1);
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "onComplete() called with: " + "databaseError = [" + databaseError + "], b = [" + b + "], dataSnapshot = [" + dataSnapshot + "]");
            }
        });


    }

    public static void getFlagNumber(int trainId, Consumer<Integer> consumer) {
        DatabaseReference trainRef = mFlagsRef.child(String.valueOf(trainId));

        trainRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange() called with: " + "dataSnapshot = [" + dataSnapshot + "]");
                Integer flagCount = dataSnapshot.getValue(Integer.class);
                if (flagCount != null) consumer.accept(flagCount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError);
            }
        });
    }


}
