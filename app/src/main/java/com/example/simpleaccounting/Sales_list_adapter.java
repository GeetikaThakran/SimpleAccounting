package com.example.simpleaccounting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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

public class Sales_list_adapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<ClientAllGS> arrayList;
    ArrayList<salesCD_gs> arrayListt;
    ArrayList<ClientAllGS> arrayListAll;
    TextView clientPhNo;

    public Sales_list_adapter(Context context,ArrayList<ClientAllGS> arrayList,ArrayList<salesCD_gs> arrayListt){  //,ArrayList<salesCD_gs> arrayListt
        this.context=context;
        this.arrayList=arrayList;
        this.arrayListt=arrayListt;
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
            convertView = inflater.inflate(R.layout.custom_list_sales,null);
            TextView t1_firm = (TextView) convertView.findViewById(R.id.cFIRM);
            TextView t2_name = (TextView) convertView.findViewById(R.id.cNAME);
            TextView t3_phone = (TextView) convertView.findViewById(R.id.cPhone);
            TextView t4_amount = (TextView) convertView.findViewById(R.id.textView5);
             ImageView t6_image = convertView.findViewById(R.id.imageView5);
            String n = arrayList.get(position).getClientName();
            clientPhNo = AddClientFragment.getclientPhoneNo();


            String total="";
        salesCD_gs salesCD_gss = null;
            ClientAllGS clientAllGS = arrayList.get(position);
            if (position<arrayListt.size()){
                salesCD_gss = arrayListt.get(position);
            }

//            total+=salesCD_gss.getAmount();
            t1_firm.setText(clientAllGS.getFirmrName());
            t2_name.setText(clientAllGS.getClientName());
            t3_phone.setText(clientAllGS.getPhoneNumber());
            if (clientAllGS.getImage()!=null) {
                t6_image.setImageURI(Uri.parse(clientAllGS.getImage()));
            }else{
                t6_image.setImageResource(R.drawable.share);
            }
            if (salesCD_gss!=null) {
                    t4_amount.setText(salesCD_gss.getAmount());
            }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(context,sales_credit_debit.class);
                i.putExtra("name",n);
                i.putExtra("id",clientAllGS.getId());
                i.putExtra("amount", t4_amount.getText().toString());
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
            ArrayList<ClientAllGS> tempList = new ArrayList<>();
            if (searchText.length() == 0 || searchText.isEmpty()) {
                tempList.addAll(arrayListAll);
            } else {
                for (ClientAllGS item : arrayListAll) {
                    if (item.getClientName().toLowerCase().contains(searchText)) {
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
            arrayList.addAll((Collection<? extends ClientAllGS>) filterResults.values);
            notifyDataSetChanged();
        }
    };


}
