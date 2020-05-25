package com.ernest.energycalcjava;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThreePhase extends Fragment {
    //text fields
    private MaterialTextView mKw, mKwh, mAmount;
    private MaterialButton mReset, mCalculate;
    private TextInputEditText mCurrent1, mCurrent2, mCurrent3;
    private TextInputEditText mVoltage1, mVoltage2, mVoltage3;
    private TextInputEditText mTariff, mAvail, mMonth, mPf;
    private TextInputLayout mCurrentLay1, mCurrentLay2, mCurrentLay3, mVoltageLay1, mVoltageLay2, mVoltageLay3;
    private TextInputLayout mTariffLay, mPfLay, mAvailLay, mMonthLay;
    private TextView noteText;


    public ThreePhase() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three_phase, container, false);
        //initialise the view
        initialiseView(view);
        //set on click listener on the calculate button
        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
                SinglePhase.hideKeyboard(getActivity());
            }
        });

        //set on click listener on the reset button
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        //set on key listener
        setOnTouchListener();

        return view;
    }

    private void setOnTouchListener() {
        mCurrent1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mCurrentLay1.setError(null);
                return false;
            }
        });
        mCurrent2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mCurrentLay2.setError(null);
                return false;
            }
        });
        mCurrent3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mCurrentLay3.setError(null);
                return false;
            }
        });



        mVoltage1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mVoltageLay1.setError(null);
                return false;
            }
        });


        mVoltage2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mVoltageLay2.setError(null);
                return false;
            }
        });

        mVoltage3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mVoltageLay3.setError(null);
                return false;
            }
        });

        mPf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPfLay.setError(null);
                return false;
            }
        });

        mMonth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mMonthLay.setError(null);
                return false;
            }
        });

        mAvail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mAvailLay.setError(null);
                return false;
            }
        });

        mTariff.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTariffLay.setError(null);
                return false;
            }
        });


    }

    private void calculate() {
        Double current1, current2, current3, volt1, volt2, volt3, pf, month, tariff, avail;

        if (SinglePhase.isValidNumber(mCurrent1.getText())) {
            current1 = Double.parseDouble(mCurrent1.getText().toString());
            mCurrentLay1.setError(null);
        } else {
            mCurrentLay1.setError(getString(R.string.invalid_current1));
            current1 = 0.0;
        }
        if (SinglePhase.isValidNumber(mCurrent2.getText())) {
            current2 = Double.parseDouble(mCurrent2.getText().toString());
            mCurrentLay2.setError(null);
        } else {
            mCurrentLay2.setError(getString(R.string.invalid_current2));
            current2 = 0.0;
        }
        if (SinglePhase.isValidNumber(mCurrent3.getText())) {
            current3 = Double.parseDouble(mCurrent3.getText().toString());
            mCurrentLay3.setError(null);
        } else {
            mCurrentLay3.setError(getString(R.string.invalid_current3));
            current3 = 0.0;
        }
        if (SinglePhase.isValidNumber(mVoltage1.getText())) {
            volt1 = Double.parseDouble(mVoltage1.getText().toString());
            mVoltageLay1.setError(null);
        } else {
            mVoltageLay1.setError(getString(R.string.invalid_voltage1));
            volt1 = 0.0;
        }
        if (SinglePhase.isValidNumber(mVoltage2.getText())) {
            volt2 = Double.parseDouble(mVoltage2.getText().toString());
            mVoltageLay2.setError(null);
        } else {
            mVoltageLay2.setError(getString(R.string.invalid_voltage2));
            volt2 =0.0;
        }
        if (SinglePhase.isValidNumber(mVoltage3.getText())) {
            volt3 = Double.parseDouble(mVoltage3.getText().toString());
            mVoltageLay3.setError(null);
        } else {
            mVoltageLay3.setError(getString(R.string.invalid_voltage3));
            volt3 =0.0;
        }
        if (SinglePhase.isValidNumber(mAvail.getText())) {
            avail = Double.parseDouble(mAvail.getText().toString());
            mAvailLay.setError(null);
        } else {
            mAvailLay.setError(getString(R.string.enter_valid_hour_avail));
            avail =0.0;
        }
        if (SinglePhase.isValidNumber(mPf.getText())) {
            pf = Double.parseDouble(mPf.getText().toString());
            mPfLay.setError(null);
        } else {
            mPfLay.setError(getString(R.string.enter_valid_pf));
            pf = 0.0;
        }
        if (SinglePhase.isValidNumber(mMonth.getText())) {
            month = Double.parseDouble(mMonth.getText().toString());
            mMonthLay.setError(null);
        } else {
            mMonthLay.setError(getString(R.string.enter_valid_month));
            month = 0.0;
        }
        if (SinglePhase.isValidNumber(mTariff.getText())) {
            tariff = Double.parseDouble(mTariff.getText().toString());
            mTariffLay.setError(null);
        } else {
            mTariffLay.setError(getString(R.string.enter_valid_tariff));
            tariff =0.0;
        }

        //the calculation
        double current = (current1 + current2 + current3)/3;
        double voltage = (volt1 + volt2 + volt3)/3;

        /**
         * power is current * voltage * pf /1000
         * for three phase, take the average of the currents and voltages separately and then times
         * the final value with 3
         * Energy is the power multiplied by the availability in hours
         * and amount in Naira is the energy times the tariff and VAT
         * VAT is currently 7.5% in Nigeria
         *
          */
        double kw = 3 * current * voltage * pf/1000;
        double kwh = kw * avail* month;
        double amount = kwh * tariff * 1.075 ;

        //set the values to the text views

        mKw.setText(getString(R.string.power)+" "+ String.format("%.3f", kw) +"KW");
        mKwh.setText(getString(R.string.energy)+ " "+ String.format("%.3f",kwh)+ "KWh");
        mAmount.setText(getString(R.string.amount)+ " "+"#"+String.format("%.2f", amount));

        //set visible for note text
        noteText.setVisibility(View.VISIBLE);

    }

    //reset the views
    private void reset() {
        mCurrent1.setText(null);
        mCurrent2.setText(null);
        mCurrent3.setText(null);
        mVoltage1.setText(null);
        mVoltage2.setText(null);
        mVoltage3.setText(null);

        mAvail.setText(null);
        mPf.setText(null);
        mTariff.setText(null);
        mMonth.setText(null);

        //reset the text views
        mKw.setText(getString(R.string.power));
        mKwh.setText(getString(R.string.energy));
        mAmount.setText(getString(R.string.amount));

        //set all the errors to null
        mTariffLay.setError(null);
        mMonthLay.setError(null);
        mAvailLay.setError(null);
        mPfLay.setError(null);
        mCurrentLay3.setError(null);
        mCurrentLay2.setError(null);
        mCurrentLay1.setError(null);
        mVoltageLay3.setError(null);
        mVoltageLay2.setError(null);
        mVoltageLay1.setError(null);

        // hide not text
        noteText.setVisibility(View.INVISIBLE);
    }


    //initialise the views
    private void initialiseView(View view) {
        //text edit views
        //currents
        mCurrent1 = view.findViewById(R.id.current_main_edit1);
        mCurrent2 = view.findViewById(R.id.current_main_edit2);
        mCurrent3 = view.findViewById(R.id.current_main_edit3);
        //voltages
        mVoltage1 = view.findViewById(R.id.inner_voltage1);
        mVoltage2 = view.findViewById(R.id.inner_voltage2);
        mVoltage3 = view.findViewById(R.id.inner_voltage3);
        //avail, pf, month and tariff
        mAvail = view.findViewById(R.id.inner_avail3);
        mTariff = view.findViewById(R.id.inner_tariff3);
        mMonth = view.findViewById(R.id.inner_month3);
        mPf = view.findViewById(R.id.inner_pf3);

        //for buttons
        mReset = view.findViewById(R.id.reset_single_button3);
        mCalculate = view.findViewById(R.id.calculate_single_button3);

        //text fields layouts
        //currents
        mCurrentLay1 = view.findViewById(R.id.current_edit1);
        mCurrentLay2 = view.findViewById(R.id.current_edit2);
        mCurrentLay3 = view.findViewById(R.id.current_edit3);
        //voltages
        mVoltageLay1 = view.findViewById(R.id.voltage_edit1);
        mVoltageLay2 = view.findViewById(R.id.voltage_edit2);
        mVoltageLay3 = view.findViewById(R.id.voltage_edit3);
        // for the avail, tariff, pf, and month
        mAvailLay = view.findViewById(R.id.availability_edit3);
        mTariffLay = view.findViewById(R.id.tariff_edit3);
        mPfLay = view.findViewById(R.id.pf_edit3);
        mMonthLay = view.findViewById(R.id.num_months_edit3);

        //for text views
        mKw = view.findViewById(R.id.kw_first3);
        mKwh = view.findViewById(R.id.kwh_first3);
        mAmount = view.findViewById(R.id.amount_first3);

        //note text
        noteText = view.findViewById(R.id.note_text);


    }
}
