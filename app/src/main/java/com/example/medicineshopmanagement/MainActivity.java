package com.example.medicineshopmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }



    public void medicine(View view) {
        Intent intent = new Intent(MainActivity.this,add_medicine.class);
        startActivity(intent);

    }


    public void category(View view) {
        Intent intent = new Intent(MainActivity.this,add_category.class);
        startActivity(intent);
    }

    public void purchase(View view) {
        Intent intent = new Intent(MainActivity.this,Purchase.class);
        startActivity(intent);
    }

    public void sell(View view) {
        Intent intent = new Intent(MainActivity.this,sell.class);
        startActivity(intent);
    }

    public void report(View view) {
        Intent intent = new Intent(MainActivity.this,Report.class);
        startActivity(intent);
    }

}

