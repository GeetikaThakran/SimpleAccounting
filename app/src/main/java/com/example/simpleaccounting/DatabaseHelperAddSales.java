package com.example.simpleaccounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperAddSales extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DatabaseHelperAddSales";
    private static int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "addSales";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_INVOICE_NO = "invoicenumber";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_ITEM_NAME = "itemname";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_TOTAL = "total";
    private static final String COLUMN_CLIENT_NAME = "clientname";
    public DatabaseHelperAddSales(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE "+TABLE_NAME+" ( "+
                COLUMN_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_INVOICE_NO+"  DOUBLE NOT NULL , "+
                COLUMN_DATE+" VARCHAR(200) NOT NULL, "+
                COLUMN_ITEM_NAME+" VARCHAR(200) NOT NULL, "+
                COLUMN_QUANTITY+" DOUBLE NOT NULL ,"+
                COLUMN_PRICE+"  DOUBLE NOT NULL  , "+
                COLUMN_TOTAL+" DOUBLE NOT NULL ,"+
                COLUMN_CLIENT_NAME+" VARCHAR(200) NOT NULL "+
                ");";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS "+TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String invoiceNumber, String date, String itemName, String quantity, String price, String total, String clientName){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(COLUMN_INVOICE_NO,String.valueOf(invoiceNumber));
        cv.put(COLUMN_DATE,date);
        cv.put(COLUMN_ITEM_NAME,itemName);
        cv.put(COLUMN_QUANTITY,String.valueOf(quantity));
        cv.put(COLUMN_PRICE,String.valueOf(price));
        cv.put(COLUMN_TOTAL,String.valueOf(total));
        cv.put(COLUMN_CLIENT_NAME,clientName);

        return sqLiteDatabase.insert(TABLE_NAME,null,cv)!= -1;
    }
    public Cursor getAllItems(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return  sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }
}
