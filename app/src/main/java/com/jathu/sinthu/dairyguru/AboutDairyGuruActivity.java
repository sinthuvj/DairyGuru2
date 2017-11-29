package com.jathu.sinthu.dairyguru;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toolbar;

public class AboutDairyGuruActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_dairy_guru);


        final Spinner spinner1 = (Spinner)findViewById(R.id.selctor);
        final LinearLayout snf = (LinearLayout)findViewById(R.id.snf);
        final LinearLayout mega = (LinearLayout)findViewById(R.id.mega);
        final LinearLayout opera = (LinearLayout)findViewById(R.id.opera);
        final Button backButton =  (Button)findViewById(R.id.buttonBack);

        spinner1.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                     if(position == 0){
                         snf.setVisibility(View.VISIBLE);
                         mega.setVisibility(View.GONE);
                         opera.setVisibility(View.GONE);

                     }

                     if(position==1){
                         snf.setVisibility(View.GONE);
                         mega.setVisibility(View.VISIBLE);
                         opera.setVisibility(View.GONE);
                     }

                     if(position==2){
                         snf.setVisibility(View.GONE);
                         mega.setVisibility(View.GONE);
                         opera.setVisibility(View.VISIBLE);
                     }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        snf.setVisibility(View.VISIBLE);
                        mega.setVisibility(View.GONE);
                        opera.setVisibility(View.GONE);

                    }
                });

backButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(getBaseContext(),HomeActivity.class);
        startActivity(i);
    }
});

    }


}
