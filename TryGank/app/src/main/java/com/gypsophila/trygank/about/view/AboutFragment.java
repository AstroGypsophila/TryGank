package com.gypsophila.trygank.about.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gypsophila.trygank.R;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/8/24
 */
public class AboutFragment extends Fragment {

    public static String TAG = "AboutFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, null);
        return view;
    }

    public static AboutFragment newInstance() {
        AboutFragment aboutFragment = new AboutFragment();
        return aboutFragment;
    }
}
