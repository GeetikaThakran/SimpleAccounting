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

public class AccountingReportAdapter extends RecyclerView.Adapter<AccountingReportAdapter.ViewHolder>{

    private Context context;
    private List<accountingReport_list> accounting;
    private AccountingReportAdapter.OnAccountingReportListener mOnAccountingReportListener;

    public AccountingReportAdapter(Context context,List<accountingReport_list> accounting,OnAccountingReportListener onAccountingReportListener) {
        this.context=context;
        this.accounting=accounting;
        this.mOnAccountingReportListener=onAccountingReportListener;
    }

    @NonNull
    @Override
    public AccountingReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.accounting_report_item_list,parent,false);
        AccountingReportAdapter.ViewHolder viewHolder = new AccountingReportAdapter.ViewHolder(view,mOnAccountingReportListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccountingReportAdapter.ViewHolder holder, int position) {

        holder.textView.setText(accounting.get(position).getAccountingReport_list_name());
        String s = accounting.get(position).getAccountingReport_list_name();
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
        return accounting.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView textView;
        LinearLayout ll;
        AccountingReportAdapter.OnAccountingReportListener onAccountingReportListener;

        public ViewHolder(@NonNull View itemView, AccountingReportAdapter.OnAccountingReportListener onAccountingReportListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.accounting_report_list_item_name);
            ll = itemView.findViewById(R.id.list_t);
            this.onAccountingReportListener = onAccountingReportListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onAccountingReportListener.onAccountingReportClick(getAdapterPosition());
        }
    }
    public interface OnAccountingReportListener{
        void onAccountingReportClick(int position);
    }
}
