package com.example.simpleaccounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DatabaseHelperAddSupplier extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DatabaseHelperAddSupplier";
    private static int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "addSupplier";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SUPPLIER_NAME = "suppliername";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_CLIENT_FIRM_NAME = "ownername";
    private static final String COLUMN_EMAIL = "email";


    Context mContext;
    public DatabaseHelperAddSupplier(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABLE_NAME+" ( "+
                COLUMN_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_SUPPLIER_NAME+" VARCHAR(200) NOT NULL, "+
                COLUMN_IMAGE+" BLOB DEFAULT x'00', "+
                COLUMN_CLIENT_FIRM_NAME+" VARCHAR(200) NOT NULL, "+
                COLUMN_EMAIL+" VARCHAR(200) NOT NULL, "+
                COLUMN_PHONE+" VARCHAR(10) NOT NULL "+
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }//firmName supplierName  phone image email
    public boolean addDataSupplier(String firmName, String image,String supplierName, String phone, String email){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        byte[] petimage = new byte[0];
        int image_size = 0;

        try {
            InputStream is = mContext.getAssets().open(image);
            image_size = is.available();
            petimage = new byte[image_size];
            is.read(petimage);
            is.close();

        } catch (IOException e) {
        }
        ContentValues cv  = new ContentValues();
        cv.put(COLUMN_SUPPLIER_NAME,supplierName);
        if (image_size > 0) {
            cv.put(COLUMN_IMAGE,image);
        }
        cv.put(COLUMN_PHONE,phone);
        cv.put(COLUMN_CLIENT_FIRM_NAME,firmName);
        cv.put(COLUMN_EMAIL,email);

        return sqLiteDatabase.insert(TABLE_NAME,null,cv)!= -1;
    }
    public Cursor getAllSupplier(){
//        ArrayList<SupplierAllGS> arrayList = new ArrayList<>();
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
//
//        while(cursor.moveToNext()){
//            int id = cursor.getInt(0);
//            String image = cursor.getString(1);
//            String firm = cursor.getString(3);
//            String name = cursor.getString(2);
//            String phone = cursor.getString(5);
//            String email = cursor.getString(4);
//
//
//
//            SupplierAllGS supplier  = new SupplierAllGS(id,name,phone,firm,email,image);
//
//            arrayList.add(supplier);
//
//        }
//        cursor.close();
//        return arrayList;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return  sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }
}
