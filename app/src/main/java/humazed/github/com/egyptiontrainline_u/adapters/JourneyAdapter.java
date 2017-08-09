package humazed.github.com.egyptiontrainline_u.adapters;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import humazed.github.com.egyptiontrainline_u.R;
import humazed.github.com.egyptiontrainline_u.model.Change;

/**
 * User: YourPc
 * Date: 8/9/2017
 */

public class JourneyAdapter extends BaseQuickAdapter<Change, BaseViewHolder> {


    @BindView(R.id.stationNameTextView) TextView mStationNameTextView;
    @BindView(R.id.arriveTimeTextView) TextView mArriveTimeTextView;
    @BindView(R.id.departTimeTextView) TextView mDepartTimeTextView;

    public JourneyAdapter(@Nullable List<Change> data) {
        super(R.layout.row_journy_default, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Change change) {
        ButterKnife.bind(this, helper.itemView);

        helper.setText(R.id.stationNameTextView, change.startStationName());
        helper.setText(R.id.arriveTimeTextView, change.startTime().toLocalTime().toString());
        helper.setText(R.id.departTimeTextView, change.arriveTime().toLocalTime().toString());

    }
}
