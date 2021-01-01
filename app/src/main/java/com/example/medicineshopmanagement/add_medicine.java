package com.example.medicineshopmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class add_medicine extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;
    EditText addmedicinename,addquantity,addoriginalprice;
    Button btnaddmedicine;
    Spinner sp;
    RecyclerView recyclerView;
    String name ;
    Update_user user;
    String[] categorynames;
    public boolean  firstitemspinner  = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        this.setTitle("Add Medicine");

        recyclerView = findViewById(R.id.lv_addmedicine);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        db = firebaseDatabase.getReference("userinfo");

        addmedicinename = (EditText)findViewById(R.id.add_name);
        addquantity = (EditText)findViewById(R.id.add_quantity);
        addoriginalprice = (EditText)findViewById(R.id.add_originalPrice);
        btnaddmedicine = (Button)findViewById(R.id.btn_addmedicine);
        sp = findViewById(R.id.spinner_category);
        categorynames = getResources().getStringArray(R.array.Category_name);

        btnaddmedicine.setOnClickListener(this);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.sample_spinner_category,R.id.tv_spinnercategory,categorynames);
        sp.setAdapter(adapter1);


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if(firstitemspinner== true){
                    firstitemspinner = false ;
                }else {
                    Toast.makeText(getApplicationContext(), categorynames[pos] + " is selected ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





    }



    @Override
    public void onClick(View v) {


        if (v == btnaddmedicine) {
            String name = addmedicinename.getText().toString().trim();
            String category = sp.getSelectedItem().toString();
            int Quantity = Integer.parseInt(addquantity.getText().toString().trim());
            int original = Integer.parseInt(addoriginalprice.getText().toString().trim());
            double percent = original * 15 / 100;
            double result = original + percent;
            double sellingprice = result / Quantity;
            String selling_price = String.valueOf(sellingprice);
            String Quantity1 = String.valueOf(Quantity);

            if(name.isEmpty()) {

                Toast.makeText(this, "Enter medicine name", Toast.LENGTH_SHORT).show();

            }

            else {

                User obj = new User(name, category, Quantity, selling_price);
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference node = db.getReference("userinfo");
                node.child(name).setValue(obj);


                medicinequantity objects = new medicinequantity(Quantity1);
                FirebaseDatabase db2 = FirebaseDatabase.getInstance();
                DatabaseReference node2 = db2.getReference("sellingqinfo");
                node2.child(name).setValue(objects);

                addmedicinename.setText("");
                addquantity.setText("");
                addoriginalprice.setText("");

                Toast.makeText(getApplicationContext(), "Value inserted", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(db,User.class)
                        .build();


        FirebaseRecyclerAdapter<User, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<User, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull User model) {
                        holder.setData(getApplicationContext(),model.getName(),model.getCategory(),model.getQuantity(),model.getOrginalprice());

                        holder.setOnClickListener(new ViewHolder.Clicklistener() {
                            @Override
                            public void onItemlongClick(View view, int position) {

                                name = getItem(position).getName();

                                showDeleteDataDialog(name);
                            }
                        });


                    }


                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.sample_medicine,parent,false);

                        return new ViewHolder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    private void showDeleteDataDialog(final String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(add_medicine.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you Sure to Delete this Data");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Query query = db.orderByChild("name").equalTo(name);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(add_medicine.this, "Data deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
