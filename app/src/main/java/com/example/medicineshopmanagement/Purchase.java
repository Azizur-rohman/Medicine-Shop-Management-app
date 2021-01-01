package com.example.medicineshopmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Purchase extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener{

    EditText namepurchase,quantitypurchase,pricepurchase;
    Button btnpurchaseReport,btnpurchase;
    String name ;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        this.setTitle("Purchase");
        namepurchase = findViewById(R.id.et_namepurchase);
        quantitypurchase = findViewById(R.id.et_quantitypurchase);
        pricepurchase = findViewById(R.id.et_pricepurchase);
        btnpurchaseReport = findViewById(R.id.btn_purchaseReportManage);
        btnpurchase = findViewById(R.id.et_btnpurchase);
        firebaseDatabase = FirebaseDatabase.getInstance();
        db = firebaseDatabase.getReference("purchaseinfo");

        btnpurchase.setOnClickListener(this);
        btnpurchaseReport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnpurchase){

            String name = namepurchase.getText().toString().trim();
            int quantity = Integer.parseInt(quantitypurchase.getText().toString().trim());
            int price = Integer.parseInt(pricepurchase.getText().toString().trim());

            String quan = String.valueOf(quantity);

            String pr = String.valueOf(price);


            if(name.isEmpty()) {

                Toast.makeText(this, "Enter medicine name", Toast.LENGTH_SHORT).show();

            }
            else if(quan.isEmpty())
            {
                Toast.makeText(this, "Enter quantity", Toast.LENGTH_SHORT).show();
            }

            else if(pr.isEmpty())
            {
                Toast.makeText(this, "Enter price", Toast.LENGTH_SHORT).show();
            }
            else {


                User_purchase obj = new User_purchase(name, quantity, price);
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference node = db.getReference("purchaseinfo");

                node.child(name).setValue(obj);
                namepurchase.setText(" ");
                quantitypurchase.setText(" ");
                pricepurchase.setText(" ");
                Toast.makeText(getApplicationContext(), "Purchase Successful..", Toast.LENGTH_LONG).show();

            }

        }

         if(v == btnpurchaseReport){

             Intent intent = new Intent(getApplicationContext(),purchaseReport.class);
             startActivity(intent);


        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }


}
