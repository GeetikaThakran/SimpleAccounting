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

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private Context context;
    private List<report_list> report;
    private OnReportListener mOnReportListener;

    public ReportAdapter(Context context,List<report_list> report, OnReportListener onReportListener) {
        this.context=context;
        this.report=report;
        this.mOnReportListener = onReportListener;
    }

    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.report_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view, mOnReportListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder holder, int position) {
        holder.textView.setText(report.get(position).getReport_list_name());
        String s = report.get(position).getReport_list_name();
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
        return report.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        LinearLayout ll;

        OnReportListener onReportListener;

        public ViewHolder(@NonNull View itemView, OnReportListener onReportListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.report_list_item_name);
            ll = itemView.findViewById(R.id.list_r);
            this.onReportListener = onReportListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onReportListener.onReportClick(getAdapterPosition());
        }
    }
    public interface OnReportListener{
        void onReportClick(int position);
    }
}
