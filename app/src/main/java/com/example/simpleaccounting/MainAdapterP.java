package com.example.simpleaccounting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapterP extends RecyclerView.Adapter<MainAdapterP.PurchaseViewHolder>{

    private Context context;
    private List<SupplierAllGS> supplierList;


    public MainAdapterP(Context context,List<SupplierAllGS> supplierList){
        this.context=context;
        this.supplierList=supplierList;
    }
    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item_purchase,parent,false);
        return new MainAdapterP.PurchaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapterP.PurchaseViewHolder holder, int position) {
        holder.textViewFirmName.setText(supplierList.get(position).getFirmrName());
        holder.textViewSupplierName.setText(supplierList.get(position).getSupplierName());
        holder.textViewPhoneNumber.setText(supplierList.get(position).getPhoneNumber());
        context.grantUriPermission("com.example.simpleaccounting", Uri.parse(String.valueOf(supplierList.get(position).getImage())), Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        holder.imageViewSup.setImageURI(Uri.parse(String.valueOf(supplierList.get(position).getImage())));
        String s = supplierList.get(position).getFirmrName();
        holder.ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //sending company name to mainActivity2
                Intent i =new Intent(context,purchase_credit_debit.class);
                i.putExtra("keyname",s);
                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return supplierList.size();
    }

    public class PurchaseViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewFirmName;
        private TextView textViewSupplierName;
        private TextView textViewPhoneNumber;
        private CircularImageView imageViewSup;
        private LinearLayout ly;


        public PurchaseViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewFirmName = itemView.findViewById(R.id.firm_pur);
            textViewSupplierName = itemView.findViewById(R.id.name_pur);
            textViewPhoneNumber = itemView.findViewById(R.id.phone_pur);
            imageViewSup = itemView.findViewById(R.id.imageView_pur);
            ly = itemView.findViewById(R.id.list_pur);
        }
    }
}
