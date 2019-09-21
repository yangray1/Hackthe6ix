package com.example.photogallarytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Button btn = (Button) findViewById(R.id.sendResults);
        TextView vin   = (TextView)findViewById(R.id.vin1);
        TextView pol   = (TextView)findViewById(R.id.policyNumber1);
        TextView em   = (TextView)findViewById(R.id.email1);
        TextView per   = (TextView)findViewById(R.id.percentDamaged);
        Intent temp=getIntent();

        vin.setText("VIN: " +temp.getStringExtra("VIN"));
        pol.setText("Policy Number: "+temp.getStringExtra("Policy Number"));
        em.setText("Email: "+temp.getStringExtra("Email"));

        double percent = Double.parseDouble(temp.getStringExtra("Front")) +
                Double.parseDouble(temp.getStringExtra("Back"));
        percent = percent/2;

        int roundedPercentage = 100 - (int)(percent * 100);
        String ok = roundedPercentage + "%";
        per.setText(ok);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(Results.this , MainActivity.class);
            startActivity(intent);
            }
        });
    }
}
