package mr.lmd.personal.contentprovider_01;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Mr.Lin on 2015/2/11.
 */
public class FirstContentProvider extends ContentProvider {

    public static final UriMatcher uriMatcher;
    public static final int INCOMING_USER_COLLECTION = 1;
    public static final int INCOMING_USER_SINGLE = 2;

    private DatabaseHelper dh;

    //定义规则
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CPMetaData.AUTHORIY,"users",INCOMING_USER_COLLECTION);
        uriMatcher.addURI(CPMetaData.AUTHORIY,"users/#",INCOMING_USER_SINGLE);
    }

    //功能是给列起别名
    public static HashMap<String,String> userProjectionMap;
    static {
        userProjectionMap = new HashMap<>();
        userProjectionMap.put(CPMetaData.UserTableMetaData._ID, CPMetaData.UserTableMetaData._ID);
        userProjectionMap.put(CPMetaData.UserTableMetaData.USER_NAME, CPMetaData.UserTableMetaData.USER_NAME);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        System.out.println("delete");
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    //该函数的返回值是一个Uri,这个Uri表示的是刚刚使用这个函数所插入的数据
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        System.out.println("insert");
        SQLiteDatabase db = dh.getWritableDatabase();
        long rowId = db.insert(CPMetaData.UserTableMetaData.TABLE_NAME,null,values);
        if (rowId > 0) {
            Uri insertedUserUri = ContentUris.withAppendedId(CPMetaData.UserTableMetaData.CONTENT_URI,rowId);
            //通知监听器数据已经改变
            getContext().getContentResolver().notifyChange(insertedUserUri,null);
            return insertedUserUri;
        }
        try {
            throw new SQLException("Failed to insert row into" + uri);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据传入的URI,返回该URI所表示的数据类型
    @Override
    public String getType(Uri uri) {
        System.out.println("getType");
        switch(uriMatcher.match(uri)) {
            case INCOMING_USER_COLLECTION:
                return CPMetaData.UserTableMetaData.CONTENT_TYPE;
            case INCOMING_USER_SINGLE:
                return CPMetaData.UserTableMetaData.CONTENT_TYPE_ITEM;
            default:
                try {
                    throw new IllegalAccessException("Unknow URI:"+uri);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    //是一个回调方法,在ContentProvider创建的时候执行
    @Override
    public boolean onCreate() {
        //打开数据库
        dh = new DatabaseHelper(getContext(),CPMetaData.DATABASE_NAME);
        System.out.println("onCreate");
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)){
            case INCOMING_USER_COLLECTION:
                qb.setTables(CPMetaData.UserTableMetaData.TABLE_NAME);
                qb.setProjectionMap(userProjectionMap);
                break;
            case INCOMING_USER_SINGLE:
                qb.setTables(CPMetaData.UserTableMetaData.TABLE_NAME);
                qb.setProjectionMap(userProjectionMap);
                qb.appendWhere(CPMetaData.UserTableMetaData._ID + "=" + uri.getPathSegments().get(1));
                break;
        }
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)){
            orderBy = CPMetaData.UserTableMetaData.DEFAULT_SORT_ORDER;
        }else {
            orderBy = sortOrder;
        }
        SQLiteDatabase db = dh.getWritableDatabase();
        Cursor c = qb.query(db,projection,selection,selectionArgs,null,null,orderBy);
        c.setNotificationUri(getContext().getContentResolver(),uri);
        System.out.println("query");
        return c;
    }
}
