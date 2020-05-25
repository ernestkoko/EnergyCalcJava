package com.ernest.energycalcjava;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;

import com.ernest.energycalcjava.databinding.FragmentSinglePhaseBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SinglePhase extends Fragment {
    private TextInputLayout mCurrent, mVoltage, mPf, mAvailability, mMonth, mTariff;
    //inter edit fields
    private TextInputEditText innerCurrent, innerVoltage, innerPf, innerAvail, innerMonth, innerTariff;
    private MaterialTextView noteText;
    private FragmentSinglePhaseBinding mBinding;


    private MaterialTextView mKw, mKwh, mAmount;

    public SinglePhase() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSinglePhaseBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        //setting on click listener on the calculate button
        mBinding.calculateSingleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
                hideKeyboard(getActivity());

            }

        });

        //reset
        mBinding.resetSingleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        //set on key listener
        setOnTouchListener();

        //set state changed listener
        mBinding.dfSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //check if the switch is checked
                if (mBinding.dfSwitch1.isChecked()){
                    //if true set the texts
                    mBinding.switchText.setText(getString(R.string.diversity_factor_on));
                    mBinding.noteText.setText(getText(R.string.vat_is_seven_point_five_percent));
                    mBinding.dfText.setText(getString(R.string.diversity_factor_is_sixty_percent));
                    //set the visibility of the text VISIBLE
                    mBinding.dfText.setVisibility(View.VISIBLE);
                }else{
                    //set the text
                    mBinding.switchText.setText(getString(R.string.diversity_factor_off));
                    //set the visibility INVISIBLE
                   mBinding.dfText.setVisibility(View.INVISIBLE);
                }
            }
        });

        //seton click listener on switch
//        mBinding.dfSwitch1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mBinding.dfSwitch1.isChecked()){
//                    mBinding.switchText.setText(getString(R.string.diversity_factor_on));
//                }else{
//                    mBinding.switchText.setText(getString(R.string.diversity_factor_off));
//                }
//            }
//        });

        return view;
    }

    //reset all the edit texts to null
    private void reset() {
        mBinding.currentEdit.getEditText().setText("");
        mBinding.voltageEdit.getEditText().setText("");
        mBinding.numMonthsEdit.getEditText().setText("");
        mBinding.availabilityEdit.getEditText().setText("");
        mBinding.tariffEdit.getEditText().setText("");
        mBinding.pfEdit.getEditText().setText("");

        mBinding.kwFirst.setText(getString(R.string.power));
        mBinding.kwhFirst.setText(getString(R.string.energy));
        mBinding.amountFirst.setText(getString(R.string.amount));
        //set all errors to null
        mBinding.currentEdit.setError(null);
        mBinding.voltageEdit.setError(null);
        mBinding.pfEdit.setError(null);
        mBinding.availabilityEdit.setError(null);
        mBinding.tariffEdit.setError(null);
        mBinding.numMonthsEdit.setError(null);


        //set note text invisible
        mBinding.noteText.setVisibility(View.INVISIBLE);

    }

    //calculate the values
    @SuppressLint("SetTextI18n")
    private void calculate() {

        //variables to hold int values
        double currentInt;
        double voltageInt;
        double pfInt;
        double availInt;
        double tariffInt;
        double monthInt;
        double kw, kwh, amount;
        //getting the string values from the edit text
        //current

        if (!isValidNumber(mBinding.currentMainEdit.getText())) {
            mBinding.currentEdit.setError("Enter Valid Current!");
            currentInt = 0.0;


        } else {
            currentInt = Double.parseDouble(mBinding.currentMainEdit.getText().toString());
            mBinding.currentEdit.setError(null);
        }
        if (!isValidNumber(mBinding.innerVoltage.getText())) {
            mBinding.voltageEdit.setError("Enter Valid Voltage!");
            voltageInt = 0.0;


        } else {
            voltageInt = Double.parseDouble(mBinding.innerVoltage.getText().toString());
            mBinding.voltageEdit.setError(null);
        }
        if (!isValidNumber(mBinding.innerPf.getText())) {
            mBinding.pfEdit.setError("Enter Valid Power Factor!");
            pfInt = 0.0;


        } else {
            pfInt = Double.parseDouble(mBinding.innerPf.getText().toString());
            mBinding.pfEdit.setError(null);
        }
        if (!isValidNumber(mBinding.innerMonth.getText())) {
            mBinding.numMonthsEdit.setError("Enter Valid Number of Month(s)!");
            monthInt = 0.0;


        } else {
            monthInt = Double.parseDouble(mBinding.innerMonth.getText().toString());
            mBinding.numMonthsEdit.setError(null);
        }
        if (!isValidNumber(mBinding.innerAvail.getText())) {
            mBinding.availabilityEdit.setError("Enter Valid Hour(s) of Availability!");
            availInt = 0.0;


        } else {
            availInt = Double.parseDouble(mBinding.innerAvail.getText().toString());
           mBinding.availabilityEdit.setError(null);
        }
        if (!isValidNumber(mBinding.innerTariff.getText())) {
            mBinding.tariffEdit.setError("Enter valid Tariff Class!");
            tariffInt = 0.0;


        } else {
            tariffInt = Double.parseDouble(mBinding.innerTariff.getText().toString());
            mBinding.tariffEdit.setError(null);
        }

        //calculate the Power in KW
        kw = (currentInt * voltageInt * pfInt) / 1000;
        //check if the switch is checked
        if (mBinding.dfSwitch1.isChecked()){
            //if true calculate for energy  and make the customer pay for 60% of it (energy)
            kwh = kw * monthInt * availInt * 0.6;
            //calculate the amount
            amount = kwh * tariffInt * 1.075;

        }else {
            // else calculate the KWh, and Amount for 100%
            kwh = kw * monthInt * availInt;
            amount = kwh * tariffInt * 1.075;


        }


        //setting the value to text views
        mBinding.kwFirst.setText(getString(R.string.power) + " " + String.format("%.3f", kw) + "KW");
        mBinding.kwhFirst.setText(getString(R.string.energy) + " " + String.format("%.3f", kwh) + "KWh");
        mBinding.amountFirst.setText(getString(R.string.amount) + " " + "#" + String.format("%.2f", amount));

        //set note text visible
        mBinding.noteText.setVisibility(View.VISIBLE);


    }

    public static boolean isValidNumber(@NonNull Editable number) {
        try {
            Double.parseDouble(number.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;

        }


    }

    //hide the keyboard
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //set on keyboard key listener
    private void setOnTouchListener() {
        //for current edit text
        mBinding.currentMainEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBinding.currentEdit.setError(null);
                return false;
            }
        });

        //for voltage
        mBinding.innerVoltage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBinding.voltageEdit.setError(null);
                return false;
            }
        });

        mBinding.innerTariff.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBinding.tariffEdit.setError(null);
                return false;
            }
        });

        mBinding.innerPf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBinding.pfEdit.setError(null);
                return false;
            }
        });

        mBinding.innerMonth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBinding.numMonthsEdit.setError(null);
                return false;
            }
        });

        mBinding.innerAvail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBinding.availabilityEdit.setError(null);
                return false;
            }
        });


    }
}
