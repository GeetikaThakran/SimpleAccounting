package com.example.simpleaccounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class databaseHelperSalesCD extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DatabaseHelperSalesCD";
    private static int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "addDataSales";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_BILL_NUMBER = "billNumber";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_CLIENT_NAME = "clientname";
    private static final String COLUMN_NOTES = "notes";
    private static final String COLUMN_AMOUNT = "amount";

    public databaseHelperSalesCD(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABLE_NAME+" ( "+
                COLUMN_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_BILL_NUMBER+" VARCHAR(200) NOT NULL, "+
                COLUMN_DATE+" VARCHAR(200) NOT NULL, "+
                COLUMN_CLIENT_NAME+" VARCHAR(200) NOT NULL, "+
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
    public boolean addDataSales(String billNumber, String date, String clientName, String notes,String amount){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(COLUMN_BILL_NUMBER,billNumber);
        cv.put(COLUMN_DATE,date);
        cv.put(COLUMN_CLIENT_NAME,clientName);
        cv.put(COLUMN_NOTES,notes);
        cv.put(COLUMN_AMOUNT,amount);

        return sqLiteDatabase.insert(TABLE_NAME,null,cv)!= -1;
    }
    public int updateDataSales(long id,String billNumber, String date, String clientName, String notes,String amount){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(COLUMN_ID,id);
        cv.put(COLUMN_BILL_NUMBER,billNumber);
        cv.put(COLUMN_DATE,date);
        cv.put(COLUMN_CLIENT_NAME,clientName);
        cv.put(COLUMN_NOTES,notes);
        cv.put(COLUMN_AMOUNT,amount);
        return sqLiteDatabase.update(TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(id)});
       // return sqLiteDatabase.update(TABLE_NAME,null,cv)!= -1;
    }
    public ArrayList<salesCD_gs> getAllSales(){
        ArrayList<salesCD_gs> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);

        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String billNum = cursor.getString(1);
            String date = cursor.getString(2);
            String clientName = cursor.getString(3);
            String notes = cursor.getString(4);
            String amount = cursor.getString(5);


            salesCD_gs sales  = new salesCD_gs(id,billNum,date,clientName,notes,amount);

            arrayList.add(sales);

        }
        cursor.close();
        return arrayList;
    }
}
