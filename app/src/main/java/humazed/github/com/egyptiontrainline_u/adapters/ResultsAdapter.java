package humazed.github.com.egyptiontrainline_u.adapters;

import android.support.annotation.Nullable;
import android.support.percent.PercentFrameLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.threeten.bp.Duration;
import org.threeten.bp.LocalTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import humazed.github.com.egyptiontrainline_u.R;
import humazed.github.com.egyptiontrainline_u.server.firebase.FlagUtil;
import humazed.github.com.egyptiontrainline_u.model.Result;
import humazed.github.com.egyptiontrainline_u.util.DateUtil;
import humazed.github.com.egyptiontrainline_u.util.TextUtil;

/**
 * User: YourPc
 * Date: 8/9/2017
 */

public class ResultsAdapter extends BaseQuickAdapter<Result, BaseViewHolder> {

    @BindView(R.id.duration) TextView mDuration;
    @BindView(R.id.startStationTime) TextView mStartStationTime;
    @BindView(R.id.startStationName) TextView mStartStationName;
    @BindView(R.id.arrivalStationTime) TextView mArrivalStationTime;
    @BindView(R.id.arrivalStationName) TextView mArrivalStationName;
    @BindView(R.id.journey_planner_bar) ImageView mJourneyPlannerBar;
    @BindView(R.id.journey_planner_changes_bar) PercentFrameLayout mJourneyPlannerChangesBar;
    @BindView(R.id.changesNumber) TextView mChangesNumber;
    @BindView(R.id.ticketPrice) TextView mTicketPrice;
    @BindView(R.id.rowDivider) View mRowDivider;
    @BindView(R.id.leavesIn) TextView mLeavesIn;
    @BindView(R.id.flagImageView) ImageView mFlagImageView;
    @BindView(R.id.flagsNumberTextView) TextView mFlagsNumberTextView;

    public ResultsAdapter(@Nullable List<Result> data) {
        super(R.layout.row_result, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Result result) {
        ButterKnife.bind(this, helper.itemView);

        helper.setText(R.id.startStationName, result.startStation().stationName());
        helper.setText(R.id.startStationTime, result.startTime().toLocalTime().toString());
        helper.setText(R.id.arrivalStationName, result.arrivalStation().stationName());
        helper.setText(R.id.arrivalStationTime, result.arriveTime().toLocalTime().toString());
        helper.setText(R.id.duration, DateUtil.prettyString(Duration.between(result.startTime(), result.arriveTime())));

        String leavesIn = DateUtil.prettyString(Duration.between(LocalTime.now(), result.arriveTime().toLocalTime()));
        helper.setText(R.id.leavesIn, TextUtil.fromHtml(mContext.getString(R.string.leaves_in, leavesIn)));

        helper.setText(R.id.changesNumber, mContext.getString(R.string.changes_number, result.changes().size() + 1));

        mFlagImageView.setOnClickListener(v -> FlagUtil.flag(result.trainID()));

        FlagUtil.getFlagNumber(result.trainID(),
                count -> helper.setText(R.id.flagsNumberTextView, String.valueOf(count)));
    }
}
