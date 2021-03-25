package com.example.simpleaccounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "companydtabase";
    private static int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "company";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_COMPANY_NAME = "companyname";
    private static final String COLUMN_OWNER_NAME = "ownername";
    //private static final String COLUMN_GSTIN = "gstin";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHONE = "phone";

    public DatabaseManager(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE "+TABLE_NAME+" ( "+
                COLUMN_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_COMPANY_NAME+" VARCHAR(200) NOT NULL, "+
                COLUMN_OWNER_NAME+" VARCHAR(200) NOT NULL, "+
               // COLUMN_GSTIN+" VARCHAR(200) NOT NULL, "+
                COLUMN_ADDRESS+" VARCHAR(200) NOT NULL, "+
                COLUMN_PHONE+" DOUBLE NOT NULL "+
                ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS "+TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
    public boolean deleteCompany(long id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,COLUMN_ID+"=?",new String[]{String.valueOf(id)})>0;
    }
    public boolean returnCompany(Company company){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID,company.getId());
        cv.put(COLUMN_COMPANY_NAME,company.getCompanyName());
        cv.put(COLUMN_OWNER_NAME,company.getOwnerName());
        //cv.put(COLUMN_GSTIN,company.getGstin());
        cv.put(COLUMN_ADDRESS,company.getAddress());
        cv.put(COLUMN_PHONE,String.valueOf(company.getPhoneNumber()));

        return sqLiteDatabase.insert(TABLE_NAME,null,cv)!=-1;
    }
    public boolean updateCompany(long id, String companyName, String ownerName, String address, String phone){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_COMPANY_NAME,companyName);
        cv.put(COLUMN_OWNER_NAME,ownerName);
        //cv.put(COLUMN_GSTIN,gstin);
        cv.put(COLUMN_ADDRESS,address);
        cv.put(COLUMN_PHONE,phone);

        return sqLiteDatabase.update(TABLE_NAME,cv,COLUMN_ID+"=?",new String[]{String.valueOf(id)})>0;
    }
    public boolean addCompany(String companyName, String ownerName, String address, String phone){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(COLUMN_COMPANY_NAME,companyName);
        cv.put(COLUMN_OWNER_NAME,ownerName);
        //cv.put(COLUMN_GSTIN,gstin);
        cv.put(COLUMN_ADDRESS,address);
        cv.put(COLUMN_PHONE,String.valueOf(phone));

        return sqLiteDatabase.insert(TABLE_NAME,null,cv)!= -1;
    }
    public Cursor  getAllCompany(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return  sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }
}
