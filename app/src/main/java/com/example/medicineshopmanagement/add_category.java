package com.example.medicineshopmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class add_category extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener  {

    EditText etmedicinename,etcategory;
    Button btncategory;
    RecyclerView recyclerView;
    String name ;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;
    private ArrayList<String> mylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        this.setTitle("Category");

        recyclerView = findViewById(R.id.lv_addcategory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etmedicinename = findViewById(R.id.et_medicinename);
        etcategory = findViewById(R.id.et_categoryname);
        btncategory = findViewById(R.id.btn_category);
        firebaseDatabase = FirebaseDatabase.getInstance();
        db = firebaseDatabase.getReference("categoryinfo");

        btncategory.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        if (v == btncategory){

            String name = etmedicinename.getText().toString().trim();
            String category = etcategory.getText().toString().trim();

            if(name.isEmpty()) {

                Toast.makeText(this, "Enter medicine name", Toast.LENGTH_SHORT).show();

            }
            else if(category.isEmpty())
            {
                Toast.makeText(this, "Enter category name", Toast.LENGTH_SHORT).show();
            }
            else {


                Category obj = new Category(name, category);
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference node = db.getReference("categoryinfo");

                node.child(name).setValue(obj);
                etcategory.setText(" ");
                etmedicinename.setText(" ");
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

        FirebaseRecyclerOptions<Category> options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(db,Category.class)
                        .build();


        FirebaseRecyclerAdapter<Category, ViewHoldercategory> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Category, ViewHoldercategory>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHoldercategory holder, int position, @NonNull Category model) {
                        holder.setData(getApplicationContext(),model.getName(),model.getCategory());

                        holder.setOnClickListener(new ViewHoldercategory.Clicklistener() {
                            @Override
                            public void onItemlongClick(View view, int position) {
                                name = getItem(position).getName();

                                showDeleteDataDialog(name);
                            }
                        });


                    }


                    @NonNull
                    @Override
                    public ViewHoldercategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.sample_medicine,parent,false);

                        return new ViewHoldercategory(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    private void showDeleteDataDialog(final String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(add_category.this);
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
                        Toast.makeText(add_category.this, "Data deleted", Toast.LENGTH_SHORT).show();
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
