package mr.lmd.personal.contentprovider_01;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private Button btnInsert,btnQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        System.out.println(getContentResolver().getType(CPMetaData.UserTableMetaData.CONTENT_URI));
    }

    private void findView() {

        //插入数据
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(CPMetaData.UserTableMetaData.USER_NAME,"test_1");
                Uri uri = getContentResolver().insert(CPMetaData.UserTableMetaData.CONTENT_URI,values);
                System.out.println("uri--->"+uri.toString());
            }
        });

        //查询数据
        btnQuery = (Button) findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = getContentResolver().query(CPMetaData.UserTableMetaData.CONTENT_URI,null,null,null,null);
                while (c.moveToNext()){
                    System.out.println(c.getString(c.getColumnIndex(CPMetaData.UserTableMetaData.USER_NAME)));
                }
                c.close();
            }
        });
    }

}
