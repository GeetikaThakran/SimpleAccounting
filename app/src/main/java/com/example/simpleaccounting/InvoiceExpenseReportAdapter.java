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

public class InvoiceExpenseReportAdapter extends RecyclerView.Adapter<InvoiceExpenseReportAdapter.ViewHolder> {
    private Context context;
    private List<invoice_expense_report_list> invoice_expense_report;
    private InvoiceExpenseReportAdapter.OnInvoiceReportListener mOnInvoiceReportListener;

    public InvoiceExpenseReportAdapter(Context context,List<invoice_expense_report_list> invoice_expense_report, InvoiceExpenseReportAdapter.OnInvoiceReportListener onInvoiceReportListener) {
        this.context=context;
        this.invoice_expense_report=invoice_expense_report;
        this.mOnInvoiceReportListener = onInvoiceReportListener;
    }

    @NonNull
    @Override
    public InvoiceExpenseReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ie_report_list_item,parent,false);
        InvoiceExpenseReportAdapter.ViewHolder viewHolder = new InvoiceExpenseReportAdapter.ViewHolder(view, mOnInvoiceReportListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceExpenseReportAdapter.ViewHolder holder, int position) {

        holder.textView.setText(invoice_expense_report.get(position).getInvoiceExpenseReport_list_name());
        String s = invoice_expense_report.get(position).getInvoiceExpenseReport_list_name();
    }

    @Override
    public int getItemCount() {
        return invoice_expense_report.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        LinearLayout ll;
        InvoiceExpenseReportAdapter.OnInvoiceReportListener onInvoiceReportListener;

        public ViewHolder(@NonNull View itemView,  InvoiceExpenseReportAdapter.OnInvoiceReportListener onInvoiceReportListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.ie_report_list_item_name);
            ll = itemView.findViewById(R.id.list_ier);

            this.onInvoiceReportListener = onInvoiceReportListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onInvoiceReportListener.onInvoiceReportClick(getAdapterPosition());
        }
    }
    public interface OnInvoiceReportListener{
        void onInvoiceReportClick(int position);
    }
}
