package com.example.simpleaccounting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

public class Purchase_list_adapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<SupplierAllGS> arrayList;
   // ArrayList<purchaseCD_gs> arrayListt;
    ArrayList<SupplierAllGS> arrayListAll;
    TextView supplierPhNo;

    public Purchase_list_adapter(Context context,ArrayList<SupplierAllGS> arrayList){  //,ArrayList<purchaseCD_gs> arrayListt
        this.context=context;
        this.arrayList=arrayList;
       // this.arrayListt=arrayListt;
        arrayListAll = new ArrayList<>(arrayList);
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_list_purchase,null);
        TextView t1_firm = (TextView) convertView.findViewById(R.id.sFIRM);
        TextView t2_name = (TextView) convertView.findViewById(R.id.sNAME);
        TextView t3_phone = (TextView) convertView.findViewById(R.id.sPhone);
        ImageView t6_image = convertView.findViewById(R.id.imageViewC);
        //TextView t4_amount = (TextView) convertView.findViewById(R.id.textViewSup);
        String n = arrayList.get(position).getSupplierName();
        supplierPhNo = AddSupplierFragment.getSupplierPhoneNo();


        purchaseCD_gs purchaseCD_gss=null;
        SupplierAllGS supplierAllGS = arrayList.get(position);
      //  if (position<arrayListt.size()) {
      //      purchaseCD_gss = arrayListt.get(position);
       // }
        t1_firm.setText(supplierAllGS.getFirmrName());
        t2_name.setText(supplierAllGS.getSupplierName());
        t3_phone.setText(supplierAllGS.getPhoneNumber());
        t6_image.setImageURI(Uri.parse(String.valueOf(supplierAllGS.getImage())));
        //if (purchaseCD_gss!=null) {
        //    t4_amount.setText(purchaseCD_gss.getAmount());
       // }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(context,purchase_credit_debit.class);
                i.putExtra("name",n);
                context.startActivity(i);
            }
        });


        return convertView;
    }

    @Override
    public Filter getFilter() {
        return FilterUser;
    }
    private Filter FilterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchText = charSequence.toString().toLowerCase();
            ArrayList<SupplierAllGS> tempList = new ArrayList<>();
            if (searchText.length() == 0 || searchText.isEmpty()) {
                tempList.addAll(arrayListAll);
            } else {
                for (SupplierAllGS item : arrayListAll) {
                    if (item.getSupplierName().toLowerCase().contains(searchText)) {
                        tempList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = tempList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            arrayList.clear();
            arrayList.addAll((Collection<? extends SupplierAllGS>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
