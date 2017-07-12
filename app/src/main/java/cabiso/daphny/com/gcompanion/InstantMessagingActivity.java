package cabiso.daphny.com.gcompanion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lenovo on 7/9/2017.
 */

public class InstantMessagingActivity extends Fragment {

    public InstantMessagingActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instant_messaging, container, false);
        return view;
    }
}
