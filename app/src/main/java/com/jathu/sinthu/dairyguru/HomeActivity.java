package com.jathu.sinthu.dairyguru;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        /**Define objects in the page*/
        final Button aboutDairyGuru = (Button) findViewById(R.id.formulaButton);
        final Button analyzeMilk = (Button) findViewById(R.id.analyzeMilkButton);
        final Button farmerIncentives = (Button) findViewById(R.id.farmerIncentivesButton);
        final Button opperationalCommision = (Button) findViewById(R.id.opperCommButton);

        /**
         * On click listeners for Frame Layout
         */

        aboutDairyGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),AboutDairyGuruActivity.class);
                startActivity(i);
            }
        });

        analyzeMilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),AnalyzeMilkActivity.class);
                startActivity(i);
            }
        });

        farmerIncentives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),FarmerIncentivesActivity.class);
                startActivity(i);
            }
        });

        opperationalCommision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),OperationaCommisionActivity.class);
                startActivity(i);
            }
        });


    }
}
