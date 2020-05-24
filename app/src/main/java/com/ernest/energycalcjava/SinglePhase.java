package com.ernest.energycalcjava;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

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
   private TextInputEditText innerCurrent, innerVoltage, innerPf, innerAvail,innerMonth, innerTariff;
   private TextView noteText;


    private MaterialTextView mKw, mKwh, mAmount;

    public SinglePhase() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_phase, container, false);
        //initialise the views
        //Edit text views
        mCurrent = view.findViewById(R.id.current_edit);
         mVoltage = view.findViewById(R.id.voltage_edit);
         mPf = view.findViewById(R.id.pf_edit);
        mAvailability = view.findViewById(R.id.availability_edit);
         mTariff = view.findViewById(R.id.tariff_edit);
         mMonth = view.findViewById(R.id.num_months_edit);
        //Button views
       final MaterialButton mReset = view.findViewById(R.id.reset_single_button);
        final MaterialButton mCalculate = view.findViewById(R.id.calculate_single_button);
        //for text views
        mKw = view.findViewById(R.id.kw_first);
        mKwh = view.findViewById(R.id.kwh_first);
        mAmount = view.findViewById(R.id.amount_first);

        //the inner text fields
        innerCurrent = view.findViewById(R.id.current_main_edit);
        innerVoltage = view.findViewById(R.id.inner_voltage);
        innerAvail = view.findViewById(R.id.inner_avail);
        innerPf = view.findViewById(R.id.inner_pf);
        innerTariff = view.findViewById(R.id.inner_tariff);
        innerMonth = view.findViewById(R.id.inner_month);
        //for note text
        noteText = view.findViewById(R.id.note_text);



        //setting on click listener on the calculate button
        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
                hideKeyboard(getActivity());

            }

        });

        //reset
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             reset();
            }
        });

        //set on key listener
        setKeyListener();

        return view;
    }

    //reset all the edit texts to null
    private void reset() {
        mCurrent.getEditText().setText("");
        mVoltage.getEditText().setText("");
        mMonth.getEditText().setText("");
        mAvailability.getEditText().setText("");
        mTariff.getEditText().setText("");
        mPf.getEditText().setText("");

        mKw.setText(getString(R.string.power));
        mKwh.setText(getString(R.string.energy));
        mAmount.setText(getString(R.string.amount));
        //set all errors to null
        mCurrent.setError(null);
        mVoltage.setError(null);
        mPf.setError(null);
        mAvailability.setError(null);
        mTariff.setError(null);
        mMonth.setError(null);



        //set note text invisible
        noteText.setVisibility(View.INVISIBLE);

    }

    //calculate the values
    private void calculate() {

        //variables to hold int values
        double currentInt;
        double voltageInt;
        double pfInt;
        double availInt;
        double tariffInt;
        double monthInt;
        //getting the string values from the edit text
        //current

       if (!isValidNumber(mCurrent.getEditText().getText())){
           mCurrent.setError("Enter Valid Current!");
           currentInt =0.0;


       }else{
           currentInt = Double.parseDouble(mCurrent.getEditText().getText().toString());
           mCurrent.setError(null);
       }
        if (!isValidNumber(mVoltage.getEditText().getText())){
            mVoltage.setError("Enter Valid Voltage!");
            voltageInt =0.0;


        }else{
            voltageInt = Double.parseDouble(mVoltage.getEditText().getText().toString());
            mVoltage.setError(null);
        }
        if (!isValidNumber(mPf.getEditText().getText())){
            mPf.setError("Enter Valid Power Factor!");
            pfInt =0.0;


        }else{
            pfInt = Double.parseDouble(mPf.getEditText().getText().toString());
            mPf.setError(null);
        }
        if (!isValidNumber(mMonth.getEditText().getText())){
            mMonth.setError("Enter Valid Number of Month(s)!");
            monthInt =0.0;


        }else{
            monthInt = Double.parseDouble(mMonth.getEditText().getText().toString());
            mMonth.setError(null);
        }
        if (!isValidNumber(mAvailability.getEditText().getText())){
            mAvailability.setError("Enter Valid Hour(s) of Availability!");
            availInt =0.0;


        }else{
            availInt = Double.parseDouble(mAvailability.getEditText().getText().toString());
            mAvailability.setError(null);
        }
        if (!isValidNumber(mTariff.getEditText().getText())){
            mTariff.setError("Enter valid Tariff Class!");
            tariffInt =0.0;


        }else{
            tariffInt = Double.parseDouble(mTariff.getEditText().getText().toString());
            mTariff.setError(null);
        }

        //calculate the KW, KWh, and Amount
        double kw = (currentInt * voltageInt * pfInt) / 1000;
        double kwh = kw * monthInt * availInt;
        double amount = kwh * tariffInt * 1.075;

        //setting the value to text views
        mKw.setText(getString(R.string.power) + " "+ String.format("%.3f", kw)+ "KW");
        mKwh.setText(getString(R.string.energy)+" "+String.format("%.3f",kwh) + "KWh");
        mAmount.setText(getString(R.string.amount)+" "+"#" + String.format("%.2f", amount));

        //set note text visible
        noteText.setVisibility(View.VISIBLE);


    }
    public static boolean isValidNumber(@NonNull Editable number){
        try {
            Double.parseDouble(number.toString());
            return true;
        } catch (NumberFormatException e){
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
    private void setKeyListener(){
        //for current edit text
        innerCurrent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (isValidNumber(innerCurrent.getText())) {
                        mCurrent.setError(null);

                    }

                return false;
            }
        });
        //for voltage
        innerVoltage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isValidNumber(innerVoltage.getText())){
                    mVoltage.setError(null);
                }
                return false;
            }
        });
       innerTariff.setOnKeyListener(new View.OnKeyListener() {
           @Override
           public boolean onKey(View v, int keyCode, KeyEvent event) {
               if (isValidNumber(innerTariff.getText())){
                   mTariff.setError(null);
               }
               return false;
           }
       });
        innerPf.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isValidNumber(innerPf.getText())){
                    mPf.setError(null);
                }
                return false;
            }
        });
        innerMonth.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isValidNumber(innerMonth.getText())){
                    mMonth.setError(null);
                }
                return false;
            }
        });

        innerAvail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isValidNumber(innerAvail.getText())){
                    mAvailability.setError(null);
                }
                return false;
            }
        });





    }
}
