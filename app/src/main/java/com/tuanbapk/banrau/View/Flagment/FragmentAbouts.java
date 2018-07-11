package com.tuanbapk.banrau.View.Flagment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tuanbapk.banrau.R;

/**
 * Created by buituan on 2017-12-06.
 */

public class FragmentAbouts extends Fragment{

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_abouts,container,false);

        return rootView;
    }

}
