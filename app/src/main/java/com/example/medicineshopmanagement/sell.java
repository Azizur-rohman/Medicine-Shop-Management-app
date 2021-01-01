package com.example.medicineshopmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.PublicKey;
import java.util.ArrayList;

public class sell extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    DatabaseReference ref;
    private  AutoCompleteTextView txtSearch;
    private EditText edquantity,edprice;
    private  RecyclerView listData;
    private Button btnsells,btnsellsmanage;
   public EditText editText;
   public String name;
   public int priceint;
   public int value3;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        this.setTitle("Sell");
        ref=FirebaseDatabase.getInstance().getReference("userinfo");
        edquantity=(EditText)findViewById(R.id.et_quantitysell);
        edprice= (EditText)findViewById(R.id.et_pricesell);
        btnsells=(Button)findViewById(R.id.btn_sell);
        btnsellsmanage=(Button)findViewById(R.id.btn_sellReportManage);
        txtSearch=(AutoCompleteTextView)findViewById(R.id.txtsearch);
        listData=(RecyclerView) findViewById(R.id.rclist);
        editText = (EditText)findViewById(R.id.quantityview);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        listData.setLayoutManager(layoutManager);

        btnsells.setOnClickListener(this);

        populateSearch();



    }



    private void populateSearch() {
        ValueEventListener eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    ArrayList<String> names=new ArrayList<>();
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        String n=ds.child("name").getValue(String.class);
                        names.add(n);
                    }
                    ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,names);
                    txtSearch.setAdapter(adapter);
                    txtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selection=parent.getItemAtPosition(position).toString();
                            getUsers(selection);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);
    }

    private void getUsers(String selection) {

        Query query=ref.orderByChild("name").equalTo(selection);
        ValueEventListener eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    ArrayList<UserInfo> userInfos=new ArrayList<>();
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        UserInfo userInfo=new UserInfo(ds.child("quantity").getValue().toString());
                        userInfos.add(userInfo);
                    }
                    MyAdapter adapter=new MyAdapter(userInfos, sell.this);
                    listData.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(eventListener);

    }

    @Override
    public void onClick(View v) {

        if(v == btnsells) {

            name = txtSearch.getText().toString().trim();
            int quantityint = Integer.parseInt(edquantity.getText().toString().trim());
            priceint = Integer.parseInt(edprice.getText().toString().trim());


            User_sell obj = new User_sell(name, quantityint, priceint);
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference node = db.getReference("sellinfo");
            node.child(name).setValue(obj);


            FirebaseDatabase db2 = FirebaseDatabase.getInstance();
            final DatabaseReference node2 = db2.getReference("sellingprice");

            node2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int quantityint = Integer.parseInt(dataSnapshot.child("quantitymedicine").getValue().toString());
                    value3 = quantityint + priceint;

                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            Totalamountsell objects = new Totalamountsell(value3);

            node2.setValue(objects);




            txtSearch.setText(" ");
            edquantity.setText(" ");
            edprice.setText(" ");
            Toast.makeText(getApplicationContext(), "Sell Successful..", Toast.LENGTH_LONG).show();

        }







    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    public void sellreport(View view) {
        Intent intent = new Intent(getApplicationContext(),Sellmenureport.class);
        startActivity(intent);
    }


    class UserInfo
    {



       public String quantity;

        public UserInfo() {
        }

        public UserInfo(String quantity) {
            this.quantity = quantity;
        }

        public String getQuantity() {
            return quantity;
        }
    }
    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<UserInfo> mDataset;
        private Context mContext;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            TextView quantity ;


            public MyViewHolder(View v) {
                super(v);

                quantity = itemView.findViewById(R.id.quantityview);

            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<UserInfo> myDataset, Context mContext) {
            this.mDataset = myDataset;
            this.mContext=mContext;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            View view=layoutInflater.inflate(R.layout.sample_sell,parent,false);
            return new MyViewHolder(view);

        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            final UserInfo thisuser=mDataset.get(position);
            holder.quantity.setText("Number of Stock: "+thisuser.getQuantity()+" Items");

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }



}