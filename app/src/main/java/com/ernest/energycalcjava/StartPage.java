package com.ernest.energycalcjava;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.ernest.energycalcjava.databinding.FragmentStartPageBinding;
import com.google.android.material.button.MaterialButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartPage extends Fragment {
    private MaterialButton singlePhaseButton, threePhaseButton;
    private FragmentStartPageBinding mBinding;


    public StartPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //binding to the view
        mBinding = FragmentStartPageBinding.inflate(inflater,container, false);
        View view = mBinding.getRoot();
        // Inflate the layout for this fragment
       // final View view = inflater.inflate(R.layout.fragment_start_page, container, false);
        //initialise the buttons
        singlePhaseButton = view.findViewById(R.id.single_phase_button);
        threePhaseButton = view.findViewById(R.id.three_phase_button);

        //set on click listeners to the views
        mBinding.singlePhaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = StartPageDirections.actionStartPageToSinglePhase();
                Navigation.findNavController(v).navigate(action);
            }
        });

       mBinding.threePhaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = StartPageDirections.actionStartPageToThreePhase();
                Navigation.findNavController(v).navigate(action);
            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //inflates the menu
        inflater.inflate(R.menu.main_menu, menu);
        //get the mode
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        //checks if the mode is Night
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            //changes the title string
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else {
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }
         super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.night_mode){
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


                Log.d("StartPage", "Changed to NightMode");
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                Log.d("StartPage", "Changed to DayMode");
            }


        }

        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbind from the view
        mBinding = null;
    }
}
