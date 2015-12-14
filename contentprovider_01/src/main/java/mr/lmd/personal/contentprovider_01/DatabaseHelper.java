package mr.lmd.personal.contentprovider_01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context,name,factory,version);
    }

    public DatabaseHelper(Context context, String name) {
        this(context,name,VERSION);
    }

    public DatabaseHelper(Context context, String name, int version) {
        this(context,name,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("create a database");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("create table ");
        stringBuilder.append(CPMetaData.USERS_TABLE_NAME + "(");
        stringBuilder.append(CPMetaData.UserTableMetaData._ID);
        stringBuilder.append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
        stringBuilder.append(CPMetaData.UserTableMetaData.USER_NAME);
        stringBuilder.append(" varchar(20));");
        System.out.println(stringBuilder.toString());
        db.execSQL(stringBuilder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("update database");
    }
}
