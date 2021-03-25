package com.example.simpleaccounting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaxReportAdapter extends RecyclerView.Adapter<TaxReportAdapter.ViewHolder>{
    private Context context;
    private List<tax_report_list> tax;
    private TaxReportAdapter.OnTaxReportListener mOnTaxReportListener;

    public TaxReportAdapter(Context context,List<tax_report_list> tax,OnTaxReportListener onTaxReportListener) {
        this.context=context;
        this.tax=tax;
        this.mOnTaxReportListener=onTaxReportListener;
    }

    @NonNull
    @Override
    public TaxReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.tax_report_item_list,parent,false);
        TaxReportAdapter.ViewHolder viewHolder = new TaxReportAdapter.ViewHolder(view, mOnTaxReportListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaxReportAdapter.ViewHolder holder, int position) {

        holder.textView.setText(tax.get(position).getTax_report_list_name());
        String s = tax.get(position).getTax_report_list_name();
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
        return tax.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        LinearLayout ll;

        TaxReportAdapter.OnTaxReportListener onTaxReportListener;

        public ViewHolder(@NonNull View itemView, TaxReportAdapter.OnTaxReportListener onTaxReportListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.tax_report_list_item_name);
            ll = itemView.findViewById(R.id.list_tr);
            this.onTaxReportListener = onTaxReportListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onTaxReportListener.onTaxReportClick(getAdapterPosition());
        }
    }
    public interface OnTaxReportListener{
        void onTaxReportClick(int position);
    }
}

