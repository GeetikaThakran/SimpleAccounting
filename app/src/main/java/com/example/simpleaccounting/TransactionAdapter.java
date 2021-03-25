package com.example.simpleaccounting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.simpleaccounting.R;
import com.example.simpleaccounting.transaction_list;

import java.util.List;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{
    private Context context;
    private List<transaction_list> transaction;

    public TransactionAdapter(Context context,List<transaction_list> transaction) {
        this.context=context;
        this.transaction=transaction;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.transaction_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(transaction.get(position).getTransaction_list_name());
        String s = transaction.get(position).getTransaction_list_name();
//        holder.ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //sending company name to mainActivity2
//                Intent i =new Intent(context,Sales.class);
//                //i.putExtra("keyname",s);
//                context.startActivity(i);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return transaction.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        LinearLayout ll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.transaction_list_item_name);
            ll = itemView.findViewById(R.id.list_t);

        }
    }
}
