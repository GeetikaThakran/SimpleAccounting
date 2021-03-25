package com.example.simpleaccounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class databaseHelperPurchaseCD extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DatabaseHelperPurchaseCD";
    private static int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "addDataPurchase";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_BILL_NUMBER = "billNumber";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_SUPPLIER_NAME = "suppliername";
    private static final String COLUMN_NOTES = "notes";
    private static final String COLUMN_AMOUNT = "amount";

    public databaseHelperPurchaseCD(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE "+TABLE_NAME+" ( "+
                COLUMN_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_BILL_NUMBER+" VARCHAR(200) NOT NULL, "+
                COLUMN_DATE+" VARCHAR(200) NOT NULL, "+
                COLUMN_SUPPLIER_NAME+" VARCHAR(200) NOT NULL, "+
                COLUMN_NOTES+" VARCHAR(200) NOT NULL, "+
                COLUMN_AMOUNT+" VARCHAR(200) NOT NULL "+
                ");";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
    public boolean addDataPurchase(String billNumber, String date, String supplierName, String notes,String amount){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(COLUMN_BILL_NUMBER,billNumber);
        cv.put(COLUMN_DATE,date);
        cv.put(COLUMN_SUPPLIER_NAME,supplierName);
        cv.put(COLUMN_NOTES,notes);
        cv.put(COLUMN_AMOUNT,amount);

        return sqLiteDatabase.insert(TABLE_NAME,null,cv)!= -1;
    }
    public ArrayList<purchaseCD_gs> getAllPurchase(){
        ArrayList<purchaseCD_gs> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);

        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String billNum = cursor.getString(1);
            String date = cursor.getString(2);
            String supplierName = cursor.getString(3);
            String notes = cursor.getString(4);
            String amount = cursor.getString(5);


            purchaseCD_gs purchase  = new purchaseCD_gs(id,billNum,date,supplierName,notes,amount);

            arrayList.add(purchase);

        }
        cursor.close();
        return arrayList;
    }
}
