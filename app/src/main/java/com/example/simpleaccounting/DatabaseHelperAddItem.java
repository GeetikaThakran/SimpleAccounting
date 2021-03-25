package com.example.simpleaccounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelperAddItem extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DatabaseHelperAddItem";
    private static int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "addItem";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ITEM_NAME = "itemname";
    private static final String COLUMN_HSN_CODE = "hsncode";
    private static final String COLUMN_BARCODE = "barcode";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_GST = "gst";
    public DatabaseHelperAddItem(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE "+TABLE_NAME+" ( "+
                COLUMN_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_ITEM_NAME+" VARCHAR(200) NOT NULL, "+
                COLUMN_HSN_CODE+" VARCHAR(200) NOT NULL, "+
                COLUMN_BARCODE+" VARCHAR(200) NOT NULL, "+
                COLUMN_PRICE+" DOUBLE NOT NULL ,"+
                COLUMN_GST+" VARCHAR(200) NOT NULL "+
                ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS "+TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String itemName, String hsnCode, String barcode, String price, String gst){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(COLUMN_ITEM_NAME,itemName);
        cv.put(COLUMN_HSN_CODE,String.valueOf(hsnCode));
        cv.put(COLUMN_BARCODE,String.valueOf(barcode));
        cv.put(COLUMN_PRICE,String.valueOf(price));
        cv.put(COLUMN_GST,gst);

        return sqLiteDatabase.insert(TABLE_NAME,null,cv)!= -1;
    }
    public Cursor getAllItems(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return  sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }
}
