package com.example.medicineshopmanagement;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {


    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClicklistener.onItemlongClick(view,getAdapterPosition());
                return false;
            }
        });
    }

    public void setData(Context context,String name,String category,int quantity,String orginalprice){
        TextView textView = itemView.findViewById(R.id.textview_row);


        textView.setText("Name: " + name + "\n"  + "Category: "  + category +"\n"+ "Quantity: " + quantity+"\n"+ "Orginal Price: " + orginalprice+"৳");
    }

    private ViewHolder.Clicklistener mClicklistener;
    public interface Clicklistener{
        void onItemlongClick(View view , int position);
    }

    public void setOnClickListener(ViewHolder.Clicklistener clickListener){
        mClicklistener = clickListener;
    }



}