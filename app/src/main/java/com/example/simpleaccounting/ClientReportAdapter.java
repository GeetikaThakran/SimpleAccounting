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

public class ClientReportAdapter extends RecyclerView.Adapter<ClientReportAdapter.ViewHolder>{

    private Context context;
    private List<client_report_list> client;
    private ClientReportAdapter.OnClientReportListener mOnClientReportListener;

    public ClientReportAdapter(Context context,List<client_report_list> client, OnClientReportListener onClientReportListener) {
        this.context=context;
        this.client=client;
        this.mOnClientReportListener=onClientReportListener;
    }

    @NonNull
    @Override
    public ClientReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.client_report_item_list,parent,false);
        ClientReportAdapter.ViewHolder viewHolder = new ClientReportAdapter.ViewHolder(view, mOnClientReportListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientReportAdapter.ViewHolder holder, int position) {

        holder.textView.setText(client.get(position).getClient_report_list_name());
        String s = client.get(position).getClient_report_list_name();
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
        return client.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        LinearLayout ll;
        ClientReportAdapter.OnClientReportListener onClientReportListener;

        public ViewHolder(@NonNull View itemView, ClientReportAdapter.OnClientReportListener onClientReportListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.client_report_list_item_name);
            ll = itemView.findViewById(R.id.list_cpr);
            this.onClientReportListener = onClientReportListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onClientReportListener.onclientReportClick(getAdapterPosition());
        }
    }
    public interface OnClientReportListener{
        void onclientReportClick(int position);
    }
}
