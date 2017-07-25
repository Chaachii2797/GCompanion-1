package cabiso.daphny.com.gcompanion.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import cabiso.daphny.com.gcompanion.HomePicture1;
import cabiso.daphny.com.gcompanion.HomePicture2;
import cabiso.daphny.com.gcompanion.R;

/**
 * Created by Lenovo on 7/9/2017.
 */

public class HomePage extends Fragment {

    private FrameLayout first, second;
    public HomePage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        first = (FrameLayout)view.findViewById(R.id.layoutOne);
        second = (FrameLayout)view.findViewById(R.id.layoutTwo);

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomePicture1.class);
                startActivity(intent);
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), HomePicture2.class);
                startActivity(intent);
            }
        });

        FragmentManager fm = getFragmentManager();
        FragmentTransaction one = fm.beginTransaction();
        one.add(R.id.layoutOne, new Picture01());
        one.add(R.id.layoutTwo, new Picture02());
        one.commit();

        return view;
    }
}
