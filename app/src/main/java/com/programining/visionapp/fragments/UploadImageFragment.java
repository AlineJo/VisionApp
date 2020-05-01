package com.programining.visionapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.programining.visionapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadImageFragment extends Fragment {


    public UploadImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_upload_image, container, false);


        return parentView;
    }

}
