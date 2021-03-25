package com.example.simpleaccounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperAddPurchase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DatabaseHelperAddpURCHASE";
    private static int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "addPurchase";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_INVOICE_NOP = "invoicenumber";
    private static final String COLUMN_DATEP = "date";
    private static final String COLUMN_ITEM_NAMEP = "itemname";
    private static final String COLUMN_QUANTITYP = "quantity";
    private static final String COLUMN_PRICEP = "price";
    private static final String COLUMN_TOTALP = "total";
    private static final String COLUMN_CLIENT_NAMEP = "clientname";
    public DatabaseHelperAddPurchase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE "+TABLE_NAME+" ( "+
                COLUMN_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_INVOICE_NOP+"  DOUBLE NOT NULL , "+
                COLUMN_DATEP+" VARCHAR(200) NOT NULL, "+
                COLUMN_ITEM_NAMEP+" VARCHAR(200) NOT NULL, "+
                COLUMN_QUANTITYP+" DOUBLE NOT NULL ,"+
                COLUMN_PRICEP+"  DOUBLE NOT NULL  , "+
                COLUMN_TOTALP+" DOUBLE NOT NULL ,"+
                COLUMN_CLIENT_NAMEP+" VARCHAR(200) NOT NULL "+
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
        cv.put(COLUMN_INVOICE_NOP,String.valueOf(invoiceNumber));
        cv.put(COLUMN_DATEP,date);
        cv.put(COLUMN_ITEM_NAMEP,itemName);
        cv.put(COLUMN_QUANTITYP,String.valueOf(quantity));
        cv.put(COLUMN_PRICEP,String.valueOf(price));
        cv.put(COLUMN_TOTALP,String.valueOf(total));
        cv.put(COLUMN_CLIENT_NAMEP,clientName);

        return sqLiteDatabase.insert(TABLE_NAME,null,cv)!= -1;
    }
    public Cursor getAllItems(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return  sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }
}
