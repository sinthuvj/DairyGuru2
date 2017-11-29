package com.jathu.sinthu.dairyguru;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class OperationaCommisionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operationa_commision);


        final LinearLayout lpdLayout = (LinearLayout)findViewById(R.id.calculatelayout2);
        final LinearLayout totalLitreLayout = (LinearLayout)findViewById(R.id.calculatetotallitrecontainer);
        totalLitreLayout.setVisibility(View.VISIBLE);
        lpdLayout.setVisibility(View.GONE);
        final ToggleButton toggleButton = (ToggleButton)findViewById(R.id.toggleButton);



        /**
         * Calculate with total litres*/
        final Spinner spinner1 = (Spinner)findViewById(R.id.monthSpinner1);
        final EditText totalLitreText = (EditText)findViewById(R.id.totalLitre);
        final TextView incentive1 = (TextView)findViewById(R.id.incentive1);
        final Button calculatewithTotalLitre = (Button)findViewById(R.id.btn_calculate1);
        final Button clear1 =  (Button)findViewById(R.id.btn_clear1);

        /**
         *
         * Calculate Incentive with LPD*/
        final Spinner spinner2 = (Spinner)findViewById(R.id.monthSpinner2);
        final EditText lpdText = (EditText)findViewById(R.id.avglpd);
        final TextView incentive2 = (TextView)findViewById(R.id.incentive2);
        final TextView lpd2 = (TextView)findViewById(R.id.lpd2);
        final Button calculatewithlpd = (Button)findViewById(R.id.btn_calculate2);
        final Button clear2 = (Button)findViewById(R.id.btn_clear2);

        final Button backButton =  (Button)findViewById(R.id.buttonBack);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lpdLayout.setVisibility(View.VISIBLE);
                    totalLitreLayout.setVisibility(View.GONE);

                } else {

                    totalLitreLayout.setVisibility(View.VISIBLE);
                    lpdLayout.setVisibility(View.GONE);

                }


            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),HomeActivity.class);
                startActivity(i);
            }
        });

        calculatewithTotalLitre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String TotalLitre = totalLitreText.getText().toString();
                String month = String.valueOf(spinner2.getSelectedItem());
                int noOfDays = getNoOfDays(month);

                if(TextUtils.isEmpty(TotalLitre)){
                    totalLitreText.setError("Please Enter Monthly total milk");
                    totalLitreText.requestFocus();
                    return;
                }

                float floattotalMilk = Float.parseFloat(TotalLitre);

                if(floattotalMilk<930){

                    totalLitreText.setError("Minimum total Litre per month is 930L");
                    totalLitreText.requestFocus();
                    return;

                }

                float floatlpd = calculateLpd(floattotalMilk,noOfDays);



                float incentive = calcualteIncentivePerLitre(floatlpd,noOfDays);
                Log.d("Incentives",String.valueOf(incentive));
                Log.d("Incentives",String.valueOf(floatlpd));
                if(incentive!=0.00){

                    incentive1.setText(String.valueOf(truncateDecimal(incentive, 2)));
                    lpd2.setText(String.valueOf(truncateDecimal(floatlpd, 2)));
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                }
                else {
                    incentive1.setText("");
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }



            }
        });

        calculatewithlpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lpdValue = lpdText.getText().toString();
                String month = String.valueOf(spinner2.getSelectedItem());
                int noOfDays = getNoOfDays(month);

                if(TextUtils.isEmpty(lpdValue)){
                    lpdText.setError("Please Enter Average LPD Value");
                    lpdText.requestFocus();
                    return;
                }

                float floatLpdValue = Float.parseFloat(lpdValue);

                if(floatLpdValue<30){
                    lpdText.setError("Minimum LPD value is 30");
                    lpdText.requestFocus();
                    return;
                }


                float incentive22 = calcualteIncentivePerLitre(floatLpdValue,noOfDays);
                if(incentive22!=0.00){

                    incentive2.setText(String.valueOf(truncateDecimal(incentive22, 2)));
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                }
                else {
                    incentive2.setText("");
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }


            }
        });
        clear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incentive2.setText("");
                lpdText.setText("");
                lpdText.setError(null);
                lpdText.clearFocus();
                spinner2.setSelection(0);


                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);


            }
        });


        clear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incentive1.setText("");
                totalLitreText.setText("");
                totalLitreText.setError(null);
                totalLitreText.clearFocus();
                spinner1.setSelection(0);
                lpd2.setText("");

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);


            }
        });




    }

    public float calcualteIncentivePerLitre(float lpd,int noOfDays){

        Log.d("Incentives",String.valueOf(lpd));
        Log.d("Days",String.valueOf(noOfDays));
        if(lpd>=30 && lpd<=50){
            return (float) 1000.00;

        }

        else if (lpd>50 && lpd<=75){
            return (float)((1000.00)+((lpd-50)*0.25*noOfDays));
        }
        else if(lpd>75 && lpd<=100){
            return (float)(1000.00+(0.25*25*noOfDays)+((lpd-75)*0.50*noOfDays));
        }

        else if(lpd>100 && lpd<=150){
            return (float)(1000.00+(0.25*25*noOfDays)+(0.50*25*noOfDays)+((lpd-100)*0.75*noOfDays));
        }
        else if(lpd>150 && lpd<=500){

            return (float)(1000.00+(0.25*25*noOfDays)+(0.50*25*noOfDays)+(50*0.75*noOfDays)+(lpd-150)*1.00*noOfDays);

        }
       else if(lpd>500){
            return (float)15000.00;
        }
        return (float)0.00;




    }

    public float calculateTotalIncentive(int incentivePerLitre,int noofDays){

        Log.d("Incentives",String.valueOf(incentivePerLitre));
        Log.d("Days",String.valueOf(noofDays));

        System.out.print(incentivePerLitre);
        System.out.print(noofDays);
        float totalIncentive = incentivePerLitre*noofDays;

        return totalIncentive;



    }

    public int getNoOfDays(String month){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int monthInt = 0;

        try{
            Date date = new SimpleDateFormat("MMM").parse(month);
            now.setTime(date);
            monthInt = now.get(Calendar.MONTH);

        }
        catch (Exception e){
            e.printStackTrace();

        }
        int day =1;
        Log.d("Year",String.valueOf(year));
        Log.d("MonthInt",String.valueOf(monthInt));
        Log.d("MonthString",month);

        Calendar myCalender = new GregorianCalendar(year,monthInt,day);

        int noOfDays = myCalender.getActualMaximum(Calendar.DAY_OF_MONTH);

        return noOfDays;

    }

    public float calculateLpd(float floattotalMilk,int noOfDays){
        Log.d("FloatTotalMilk",String.valueOf(floattotalMilk));
        float lpd = floattotalMilk/noOfDays;
        return lpd;

    }
    private static BigDecimal truncateDecimal(double x, int numberofDecimals)
    {
        if ( x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }

}
