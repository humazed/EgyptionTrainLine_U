package humazed.github.com.egyptiontrainline_u.ui.result;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import humazed.github.com.egyptiontrainline_u.R;
import humazed.github.com.egyptiontrainline_u.adapters.JourneyAdapter;
import humazed.github.com.egyptiontrainline_u.model.Result;

/**
 * A fragment representing a single Result detail screen.
 * This fragment is either contained in a {@link ResultListActivity}
 * in two-pane mode (on tablets) or a {@link JourneyActivity}
 * on handsets.
 */
public class JourneyFragment extends Fragment {
    private static final String TAG = JourneyFragment.class.getSimpleName();

    public static final String ARG_RESULT = "JourneyFragment:ARG_RESULT";

    @BindView(R.id.journeyRecyclerView) RecyclerView mJourneyRecyclerView;
    Unbinder unbinder;

    private Result mResult;

    public JourneyFragment() {}


    public static JourneyFragment newInstance(Result result) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_RESULT, result);
        JourneyFragment fragment = new JourneyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_RESULT))
            mResult = getArguments().getParcelable(ARG_RESULT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.journey_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        mJourneyRecyclerView.setAdapter(new JourneyAdapter(mResult.changes()));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
