package cabiso.daphny.com.gcompanion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lenovo on 7/9/2017.
 */

public class DIYCommunityActivity extends Fragment {

    public DIYCommunityActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diys, container, false);
        return view;
    }

}
