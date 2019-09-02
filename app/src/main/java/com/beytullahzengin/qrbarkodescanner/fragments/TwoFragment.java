package com.beytullahzengin.qrbarkodescanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

import com.beytullahzengin.qrbarkodescanner.R;


public class TwoFragment extends Fragment implements View.OnClickListener {






    public TwoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);












        return view;
    }






    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }


}
