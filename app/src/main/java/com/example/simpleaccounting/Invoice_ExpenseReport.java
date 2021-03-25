package com.example.simpleaccounting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Invoice_ExpenseReport extends AppCompatActivity implements InvoiceExpenseReportAdapter.OnInvoiceReportListener {
    List<invoice_expense_report_list> t= new ArrayList<invoice_expense_report_list>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice__expense_report);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_InvoiceExpenseReport);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //report list

        t.add(new invoice_expense_report_list("Sales Register"));
        t.add(new invoice_expense_report_list("Day Book"));
        t.add(new invoice_expense_report_list("Monthly Balance"));
        t.add(new invoice_expense_report_list("Item Summary"));
        t.add(new invoice_expense_report_list("Item Sales/Purchase"));



        InvoiceExpenseReportAdapter invoiceExpenseReportAdapter =new InvoiceExpenseReportAdapter(Invoice_ExpenseReport.this,t, this);
        recyclerView.setAdapter(invoiceExpenseReportAdapter);
    }
    public void onInvoiceReportClick(int position) {
        t.get(position);
        if (position ==0) {
            Intent intent = new Intent(this, SalesRegister.class);
            startActivity(intent);
        }
        if (position == 1){
            Intent intent1 = new Intent(this, DayBook.class);
            startActivity(intent1);
        }
        if (position == 2){
            Intent intent1 = new Intent(this, MonthlyBalance.class);
            startActivity(intent1);
        }
        if (position == 3){
            Intent intent1 = new Intent(this, ItemSummary.class);
            startActivity(intent1);
        }
        if (position == 4){
            Intent intent1 = new Intent(this, ItemSales_Purchase.class);
            startActivity(intent1);
        }
    }
}