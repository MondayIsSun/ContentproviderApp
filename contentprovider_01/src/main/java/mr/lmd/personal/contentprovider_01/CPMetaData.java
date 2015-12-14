package mr.lmd.personal.contentprovider_01;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

/**
 * Created by Mr.Lin on 2015/2/11.
 */
public class CPMetaData {

    //这个类的功能就是定义ContentProvider要使用到的一些常量啦

    public static final String AUTHORIY = "com.study.mrlin.contentprovider_01.FirstContentProvider";
    //数据库信息
    public static final String DATABASE_NAME = "FirstProvider.db";
    public static final int DATABASE_VERSION = 1;
    public static final String USERS_TABLE_NAME = "users";

    public static final class UserTableMetaData implements BaseColumns {

        //这个ContentProvider处理的表名
        public static final String TABLE_NAME = "users";
        //访问该ContentProvider的URI
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORIY + "/users");
        //该ContentProvider所返回的数据类型定义
        //dir--->返回多条记录
        //item--->返回某条记录
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.firstprovider.user";
        public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd.firstprovider.user";
        //列名
        //注意由于这个是我们自定义的ContentProvider,所以图方便列名我们就这样指定了
        //在Android的API当中其实提供了一个接口BaseColumns来处理列名的
        public static final String USER_NAME = "name";
        //默认的排序方法
        public static final String DEFAULT_SORT_ORDER = "_id desc";
    }

}
