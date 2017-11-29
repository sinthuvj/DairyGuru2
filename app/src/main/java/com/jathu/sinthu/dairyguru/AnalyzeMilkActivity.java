package com.jathu.sinthu.dairyguru;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.hardware.input.InputManager;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class AnalyzeMilkActivity extends AppCompatActivity {


    Button calculateSNF;
    Button clearButton;


    Button clearPrice;
    Button calculatePrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_milk);


        final LinearLayout analyzesnfLayout = (LinearLayout)findViewById(R.id.calculateSNFContainer);
        final LinearLayout analyzePriceLayout = (LinearLayout)findViewById(R.id.calculatePriceContainer);
        analyzePriceLayout.setVisibility(View.VISIBLE);
        analyzesnfLayout.setVisibility(View.GONE);
        final ToggleButton toggleButton = (ToggleButton)findViewById(R.id.toggleButton);

        /**
         * Calculate SNF Layout*/
        final NestedScrollView scrollingParent = (NestedScrollView)findViewById(R.id.scrollingParent);
        final EditText fat = (EditText)findViewById(R.id.fat);
        final EditText lr = (EditText)findViewById(R.id.lr);
        final TextView snf1 = (TextView)findViewById(R.id.table1snf1);
        final TextView price1 = (TextView)findViewById(R.id.table1price1);
        final TextView solid1 = (TextView)findViewById(R.id.table1solid1);

        calculateSNF = (Button)findViewById(R.id.btn_calculate);
        clearButton = (Button)findViewById(R.id.btn_clear);

        /**
         *
         * Calculate Price Layout*/

        final EditText fat2 = (EditText)findViewById(R.id.fat2);
        final EditText snf2 = (EditText)findViewById(R.id.snf2);
        final TextView price2 = (TextView)findViewById(R.id.table2price2);
        final TextView solid2 = (TextView)findViewById(R.id.table2solid2);

        calculatePrice = (Button)findViewById(R.id.btn_calculate_price);
        clearPrice = (Button)findViewById(R.id.btn_clear_price);
        final Button backButton =  (Button)findViewById(R.id.buttonBack);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    analyzesnfLayout.setVisibility(View.VISIBLE);
                    analyzePriceLayout.setVisibility(View.GONE);

                } else {

                    analyzePriceLayout.setVisibility(View.VISIBLE);
                    analyzesnfLayout.setVisibility(View.GONE);

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

        calculateSNF.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String stringFatValue = fat.getText().toString();
                String stringLrValue = lr.getText().toString();

                if(TextUtils.isEmpty(stringFatValue)){
                    fat.setError("Please Enter Fat Value");
                    fat.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(stringLrValue)){
                    lr.setError("Please Enter LR Value");
                    lr.requestFocus();
                    return;
                }

                float floatFatValue = Float.parseFloat(stringFatValue);
                float floatLrValue = Float.parseFloat(stringLrValue);

                if(floatFatValue>8.0||floatFatValue <2.5){
                    fat.setError("Valid fat value range is 2.5 - 8.0");
                    fat.requestFocus();
                    return;
                }

                if(floatLrValue>40||floatLrValue <20){
                    lr.setError("Valid LR value range is 20 - 40");
                    lr.requestFocus();
                    return;
                }
                float floatSnf = calculateSNF(floatFatValue,floatLrValue);

                float roundedFloatSnf = Math.round(floatSnf*10.0)/(float)10.0;
                float roundedFloatFat = Math.round(floatFatValue*10.0)/(float)10.0;

                float totalSolid = floatSnf+floatFatValue;


                String floatPrice = calculatePrice(roundedFloatSnf,roundedFloatFat);



                snf1.setText(String.valueOf(Math.round(floatSnf*100.0)/(float)100.0));
                price1.setText(floatPrice);
                solid1.setText(String.valueOf(Math.round(totalSolid*100.0)/(float)100.0));


                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);


            }



        });

        clearButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                solid1.setText("");
                lr.setText("");
                snf1.setText("");
                price1.setText("");
                fat.setText("");

                lr.setError(null);
                lr.clearFocus();

                fat.setError(null);
                fat.clearFocus();
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }

        });

        clearPrice.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                fat2.setText("");
                snf2.setText("");
                price2.setText("");
                solid2.setText("");

                fat2.setError(null);
                fat2.clearFocus();

                snf2.setError(null);
                snf2.clearFocus();
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });


        calculatePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringfat2Value = fat2.getText().toString();
                String snf2Value = snf2.getText().toString();


                if(TextUtils.isEmpty(stringfat2Value)){
                    fat2.setError("Please Enter Fat Value");
                    fat2.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(snf2Value)){
                    snf2.setError("Please Enter SNF Value");
                    snf2.requestFocus();
                    return;
                }

                float floatFat2Value = Float.parseFloat(stringfat2Value);
                float floatsnf2Value = Float.parseFloat(snf2Value);

                if(floatFat2Value>8.0||floatFat2Value <2.5){
                    fat2.setError("Valid fat value range is 2.5 - 8.0");
                    fat2.requestFocus();
                    return;
                }

                if(floatsnf2Value>9.4 || floatsnf2Value<8.0){
                    snf2.setError("Valid SNF value range is 8.0 - 9.4");
                    snf2.requestFocus();
                    return;
                }


                float roundedFloat2Snf = Math.round(floatsnf2Value*10.0)/(float)10.0;
                float roundedFloat2Fat = Math.round(floatFat2Value*10.0)/(float)10.0;

                String floatPrice2 = calculatePrice(roundedFloat2Snf,roundedFloat2Fat);

                float totalSolid2 = floatFat2Value+floatsnf2Value;

                price2.setText(floatPrice2);
                solid2.setText(String.valueOf(Math.round(totalSolid2*100.0)/(float)100.0));

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);




            }
        });

    }

    public float calculateSNF(float floatFatValue,float floatLrValue){

        return (float) ((floatFatValue*0.22) + (floatLrValue*0.25)+0.72);
    }

    public String calculatePrice(float roundedFloatSnf, float roundedFloatFat) {

        int row = 0;
        int col = 0;
        try {

            AssetManager am=getAssets();
            InputStream is=am.open("pricechart.xls");
            Workbook wb =Workbook.getWorkbook(is);
            Sheet s=wb.getSheet(0);



            for (Cell cell : s.getColumn(0)) {


                if(cell.getContents().equalsIgnoreCase(String.valueOf(roundedFloatFat))){
                    row =cell.getRow();
                    break;

                }
                else{
                    row = 155;
                }

            }

            for (Cell cell : s.getRow(0)) {



                if(cell.getContents().equalsIgnoreCase(String.valueOf(roundedFloatSnf))){
                    col =cell.getColumn();
                    break;

                }

                else{
                    col = 155;
                }



            }


            Cell c = s.getCell(col,row);

            String value = c.getContents();
            return value;


        }

        catch (Exception e)
        {
            return "";
        }


    }

}
