package com.example.simpleaccounting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    private Context context;
    private List<Company> companyList;


    public CompanyAdapter(Context context,List<Company> companyList){
        this.context=context;
        this.companyList=companyList;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item_company,parent,false);
        return new CompanyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {


        holder.textViewCompanyName.setText(companyList.get(position).getCompanyName());
        holder.textViewOwnerName.setText(companyList.get(position).getOwnerName());
        String s = companyList.get(position).getCompanyName();
        holder.ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //sending company name to mainActivity2
                Intent i =new Intent(context,MainActivity2.class);
                i.putExtra("keyname",s);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewCompanyName;
        private TextView textViewOwnerName;
        LinearLayout ly;


        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCompanyName = itemView.findViewById(R.id.text_view_company_name);
            textViewOwnerName = itemView.findViewById(R.id.text_view_owner_name);
            ly = itemView.findViewById(R.id.listtt);





        }

    }
}
