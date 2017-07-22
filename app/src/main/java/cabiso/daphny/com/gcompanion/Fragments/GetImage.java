package cabiso.daphny.com.gcompanion.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cabiso.daphny.com.gcompanion.R;

/**
 * Created by Lenovo on 7/20/2017.
 */

public class GetImage extends Fragment{

    public GetImage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_image, container, false);

    }

}
