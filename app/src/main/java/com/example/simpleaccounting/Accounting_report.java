package com.example.simpleaccounting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Accounting_report extends AppCompatActivity implements AccountingReportAdapter.OnAccountingReportListener {
    List<accountingReport_list> t= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounting_report);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_accounting_report);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //report list

        t.add(new accountingReport_list("Profit & Loss A/c"));
        t.add(new accountingReport_list("A/c Statement/Ledger"));
        t.add(new accountingReport_list("Balance Sheet"));
        t.add(new accountingReport_list("Trial Balance"));
        t.add(new accountingReport_list("Cash Flow"));
        t.add(new accountingReport_list("Ratio Analysis"));



        AccountingReportAdapter accountingReportAdapter =new AccountingReportAdapter(Accounting_report.this,t,this);
        recyclerView.setAdapter(accountingReportAdapter);
    }

    @Override
    public void onAccountingReportClick(int position) {
        t.get(position);
        if (position ==0) {
            Intent intent = new Intent(this, ProfitLossAccount.class);
            startActivity(intent);
        }
        if (position == 1){
            Intent intent1 = new Intent(this, AccountStatementLadger.class);
            startActivity(intent1);
        }
        if (position == 2){
            Intent intent1 = new Intent(this, BalanceSheet.class);
            startActivity(intent1);
        }
        if (position == 3){
            Intent intent1 = new Intent(this, TrialBalance.class);
            startActivity(intent1);
        }
        if (position == 4){
            Intent intent1 = new Intent(this, CashFlow.class);
            startActivity(intent1);
        }
        if (position == 5){
            Intent intent1 = new Intent(this, RatioAnalysis.class);
            startActivity(intent1);
        }
    }
}