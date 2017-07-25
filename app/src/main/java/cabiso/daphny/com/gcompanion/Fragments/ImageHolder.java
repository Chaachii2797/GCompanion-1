package cabiso.daphny.com.gcompanion.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cabiso.daphny.com.gcompanion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageHolder extends Fragment {


    public ImageHolder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_holder, container, false);
    }

    public static ImageHolder newInstance(String name){
        Bundle bundle = new Bundle();
        bundle.putString("name",name);

        ImageHolder fragment = new ImageHolder();
        fragment.setArguments(bundle);
        return fragment;
    }

}
