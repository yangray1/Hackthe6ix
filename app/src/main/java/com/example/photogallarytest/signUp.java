package com.example.photogallarytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class signUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button btn = (Button) findViewById(R.id.next2);
        final EditText vin   = (EditText)findViewById(R.id.vin);
        final EditText pol   = (EditText)findViewById(R.id.policyNumber);
        final EditText em   = (EditText)findViewById(R.id.email);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(signUp.this , backCar.class);
                intent.putExtra("Policy Number",pol.getText().toString());
                intent.putExtra("VIN",vin.getText().toString());
                intent.putExtra("Email",em.getText().toString());
                startActivity(intent);
                Log.d("rand",vin.getText().toString());

            }
        });
    }
}
