package com.example.simpleaccounting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class purchase_credit_debit extends AppCompatActivity {
    private Toolbar t;
    private Button save;
    private ImageButton datebtn,speak;
    private int count = 1;
    private TextInputEditText date,notes,invoice_number,amount,name;
    private DatePickerDialog datepicker;
    private databaseHelperPurchaseCD daatabaseHelperPurchaseCD;
    private final int REQ_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_credit_debit);

        name= findViewById(R.id.sname);
        save=findViewById(R.id.save_btn_purchase_cd);
        notes = findViewById(R.id.purchase_sc_narration);
        speak=findViewById(R.id.mic_purchase);
        datebtn=findViewById(R.id.imageButtonS);
        amount=findViewById(R.id.purchase_cd_amount);
        amount.requestFocus();
        amount.setInputType(InputType.TYPE_CLASS_NUMBER );
        invoice_number=findViewById(R.id.purchase_cd_invoiceNo);
        invoice_number.setText(Integer.toString(count));
        invoice_number.setEnabled(false);
        date=findViewById(R.id.purchase_cd_date);

        daatabaseHelperPurchaseCD = new databaseHelperPurchaseCD(this);
        //  phone=findViewById(R.id.cccphone);

        Intent intent = getIntent();
//        name.setText(intent.getStringExtra("name"));
        name.setText(intent.getStringExtra("keyname"));
//        phone.setText(intent.getStringExtra("phone"));

        //toolbar
        t =findViewById(R.id.toolbar_purchase_credit_debit);
        setSupportActionBar(t);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        }

        //set automatic date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        date.setText(currentDate);


        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(purchase_credit_debit.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                try {
                    startActivityForResult(intent, REQ_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AddDataPurchase()) {
                    Intent intent1 = new Intent(purchase_credit_debit.this,PurchaseFragment.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                }
            }
        });

    }
    public boolean AddDataPurchase(){
        String billNumF = invoice_number.getText().toString().trim();
        String dateF = date.getText().toString().trim();
        String clientNameF = name.getText().toString().trim();
        String notesF = notes.getText().toString().trim();
        String totalF = amount.getText().toString().trim();

        if(dateF.isEmpty()){
            date.setError("Date can't be empty.");
            date.requestFocus();
            return false;
        }
        if(totalF.isEmpty()){
            amount.setError("Amount can't be empty.");
            amount.requestFocus();
            return false;
        }
        if(daatabaseHelperPurchaseCD.addDataPurchase(

                billNumF,dateF,clientNameF,notesF,totalF)){
            Toast.makeText(this,"Data added successfully",Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(this,"Data not added.",Toast.LENGTH_SHORT).show();
            return  false;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK) {
                    ArrayList result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    notes.setText((CharSequence) result.get(0));
                }
                break;
            }
        }
    }
}