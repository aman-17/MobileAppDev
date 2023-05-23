package com.christopherhield.sampleapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText totalBill;
    private TextView tipA;
    private TextView twTA;
    private EditText numberpeople;
    private TextView perPersonAmount;
    private static final String TAG = "MainActivity";
    private double totalWithTip;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalBill=findViewById(R.id.editTextNumberDecimal);
        tipA=findViewById(R.id.tipA);
        twTA=findViewById(R.id.twTA);
        numberpeople=findViewById(R.id.numberpeople);
        perPersonAmount=findViewById(R.id.perPersonAmount);
        radioGroup = findViewById(R.id.radioGroup);
    }

    public void calculateAmounts(View v)
    {
        if(totalBill.getText().toString().matches("")){
            radioGroup.clearCheck();
            return;
        }
        String billAmount = totalBill.getText().toString();
        Log.d(TAG,billAmount);
        int percents=0;
        if(v.getId()==R.id.radioButton12){
            percents=12;
        }
        else if(v.getId()==R.id.radioButton15){
            percents=15;
        }
        else if(v.getId()==R.id.radioButton18){
            percents=18;
        }
        else if(v.getId()==R.id.radioButton20){
            percents=20;
        }
        else{

        }
        double intBillAmount = Double.parseDouble(billAmount);
        double tipamount = intBillAmount*percents/100;
        totalWithTip =  intBillAmount + tipamount;
        putamount(tipamount,totalWithTip);
    }

    public void putamount(double tipamount, double totalWithTip){
        tipA.setText("$"+String.format("%.2f",tipamount));
        twTA.setText("$"+String.format("%.2f",totalWithTip));
    }
    public void goclick(View v) {
        if(numberpeople.getText().toString().matches("")){
            return;
        }
        else {
            String numberofpeople = numberpeople.getText().toString();
            double intPerPersonAmount = Double.parseDouble(numberofpeople);
            if (intPerPersonAmount != 0) {
                perPersonAmount.setText("$"+String.format("%.2f", totalWithTip / intPerPersonAmount));
            } else {
                return;
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tipA.setText(savedInstanceState.getString("tipA"));
        twTA.setText(savedInstanceState.getString("twTA"));
        perPersonAmount.setText(savedInstanceState.getString("perPersonAmount"));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tipA",tipA.getText().toString());
        outState.putString("twTA",twTA.getText().toString());
        outState.putString("perPersonAmount",perPersonAmount.getText().toString());
    }

    public void clearingFields(View v){
        String emptyString = "";
        totalBill.setText(emptyString);
        tipA.setText(emptyString);
        twTA.setText(emptyString);
        numberpeople.setText(emptyString);
        perPersonAmount.setText(emptyString);
        radioGroup.clearCheck();
    }
}