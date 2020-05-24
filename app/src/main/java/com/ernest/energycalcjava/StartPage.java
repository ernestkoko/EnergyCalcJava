package com.ernest.energycalcjava;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartPage extends Fragment {
    private MaterialButton singlePhaseButton, threePhaseButton;


    public StartPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_start_page, container, false);
        //initialise the buttons
        singlePhaseButton = view.findViewById(R.id.single_phase_button);
        threePhaseButton = view.findViewById(R.id.three_phase_button);

        //set on click listeners to the views
        singlePhaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = StartPageDirections.actionStartPageToSinglePhase();
                Navigation.findNavController(v).navigate(action);
            }
        });

        threePhaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = StartPageDirections.actionStartPageToThreePhase();
                Navigation.findNavController(v).navigate(action);
            }
        });


        return view;
    }


}
