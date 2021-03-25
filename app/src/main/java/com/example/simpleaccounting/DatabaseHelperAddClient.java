package com.example.simpleaccounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.common.api.Api;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DatabaseHelperAddClient extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DatabaseHelperAddClient";
    private static int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "addClient";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_CLIENT_NAME = "clientname";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_CLIENT_FIRM_NAME = "ownername";
    private static final String COLUMN_EMAIL = "email";


    public DatabaseHelperAddClient(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public boolean deleteClient(long id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,COLUMN_ID+"=?",new String[]{String.valueOf(id)})>0;
    }
    public boolean returnClient(ClientAllGS clientAllGS){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID,clientAllGS.getId());
        cv.put(COLUMN_IMAGE,clientAllGS.getImage());
        cv.put(COLUMN_CLIENT_NAME,clientAllGS.getClientName());
        cv.put(COLUMN_PHONE,String.valueOf(clientAllGS.getPhoneNumber()));
        //cv.put(COLUMN_GSTIN,company.getGstin());
        cv.put(COLUMN_CLIENT_FIRM_NAME,clientAllGS.getFirmrName());
        cv.put(COLUMN_EMAIL,clientAllGS.getPhoneNumber());

        return sqLiteDatabase.insert(TABLE_NAME,null,cv)!=-1;
    }
    public boolean updateClient(long id,String image, String clientName, String phone, String company, String email){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CLIENT_NAME,clientName);
        cv.put(COLUMN_IMAGE,image);
        cv.put(COLUMN_PHONE,phone);
        cv.put(COLUMN_CLIENT_FIRM_NAME,company);
        cv.put(COLUMN_EMAIL,email);

        return sqLiteDatabase.update(TABLE_NAME,cv,COLUMN_ID+"=?",new String[]{String.valueOf(id)})>0;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE "+TABLE_NAME+" ( "+
                COLUMN_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_CLIENT_NAME+" VARCHAR(200) NOT NULL, "+
                COLUMN_IMAGE+" VARCHAR(200), "+
                COLUMN_CLIENT_FIRM_NAME+" VARCHAR(200) NOT NULL, "+
                COLUMN_EMAIL+" VARCHAR(200) NOT NULL, "+
                COLUMN_PHONE+" VARCHAR(10) NOT NULL "+
                ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS "+TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String clientName,String image, String firmName, String phone, String email){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(COLUMN_CLIENT_NAME,clientName);
        cv.put(COLUMN_IMAGE,image);
        cv.put(COLUMN_CLIENT_FIRM_NAME,firmName);
        cv.put(COLUMN_PHONE,phone);
        cv.put(COLUMN_EMAIL,email);

        return sqLiteDatabase.insert(TABLE_NAME,null,cv)!= -1;
    }
    public ArrayList<ClientAllGS> getAllClient(){
        ArrayList<ClientAllGS> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);

        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String firm = cursor.getString(3);// 4 mail, 3 firm, 2 image,5 number
            String name = cursor.getString(1);
            String phone = cursor.getString(5);
            String email = cursor.getString(4);
            String image = cursor.getString(2);//

            Log.v("abc",name+"name");
            Log.v("abc",firm+"firm name");
            Log.v("abc",email+"email");
            Log.v("abc",phone+"phone");
            Log.v("abc",image+"image");



            ClientAllGS client  = new ClientAllGS(id,image,name,phone,firm,email);

            arrayList.add(client);

        }
        cursor.close();
        return arrayList;
    }
}
