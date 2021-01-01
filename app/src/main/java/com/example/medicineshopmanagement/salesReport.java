package com.example.medicineshopmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class salesReport extends AppCompatActivity {
    TextView textView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_report);
        textView = (TextView)findViewById(R.id.money);
        FirebaseDatabase db2 = FirebaseDatabase.getInstance();
        final DatabaseReference node2 = db2.getReference("sellingprice");

        node2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String quantityint = dataSnapshot.child("quantitymedicine").getValue().toString();

                textView.setText(  quantityint +"৳");
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
