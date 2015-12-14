package mr.lmd.personal.contentprovider_02;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Mr.Lin on 2015/2/12.
 */
public class MyContentProvider extends ContentProvider {

    //在ContentProvider创建后被调用
    @Override
    public boolean onCreate() {
        return false;
    }

    //根据Uri查询出selection指定的条件所匹配的全部记录
    //并且可以指定查询哪些列
    //以什么方式排序(order)
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    //访问当前Uri的MIME类型
    //如果该URI对应的数据可能有多条记录，那么MIME类型字符串就是以vnd.android.dir/开头
    //如果该URI对应的数据只有一条记录，该MIME类型字符串就是以vnd.android.cursor.item/开头
    @Override
    public String getType(Uri uri) {
        return null;
    }

    //根据Uri插入Values对应的数据
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    //根据Uri删除selection指定的条件所匹配的全部记录
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    //根据Uri修改selection指定的条件所匹配的全部记录
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
