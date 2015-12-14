package mr.lmd.personal.contentprovider_02;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

    private Button btnQuery, btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }

    private void findView() {

        //查询操作
        btnQuery = (Button) findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //使用系统提供的ContentProvider来查询联系人信息
                ContentResolver cr = MainActivity.this.getContentResolver();

                //从游标中取出信息
                String id = "";
                String display_name = "";
                String home_num = "";
                String celPhone_num = "";
                String email = "";

                Cursor c = cr.query(Contacts.CONTENT_URI,
                        new String[]{Contacts._ID, Contacts.DISPLAY_NAME},
                        null, null, null);

                //查询联系人的id和姓名
                if (c != null) {
                    while (c.moveToNext()) {
                        id = "" + c.getInt(c.getColumnIndex(Contacts._ID));
                        //id = ""+c.getInt(c.getColumnIndex("_id"));
                        display_name = c.getString(c.getColumnIndex(Contacts.DISPLAY_NAME));
                        //display_name = c.getString(c.getColumnIndex("display_name"));

                        //查询联系人的电话号码
                        Cursor c1 = cr.query(Phone.CONTENT_URI,
                                new String[]{Phone.NUMBER, Phone.TYPE},
                                Phone.CONTACT_ID + "=" + id,
                                null, null);
                        if (c1 != null) {
                            while (c1.moveToNext()) {
                                int type = c1.getInt(c1.getColumnIndex(Phone.TYPE));
                                if (type == Phone.TYPE_HOME) {
                                    home_num = c1.getString(c1.getColumnIndex(Phone.NUMBER));
                                } else if (type == Phone.TYPE_MOBILE) {
                                    celPhone_num = c1.getString(c1.getColumnIndex(Phone.NUMBER));
                                }
                            }
                            c1.close();
                        }

                        //查询联系人的邮箱
                        Cursor c2 = cr.query(Email.CONTENT_URI, new String[]{Email.DATA, Email.TYPE}, Email.CONTACT_ID + "=" + id, null, null);
                        if (c2 != null) {
                            while (c2.moveToNext()) {
                                int type = c2.getInt(c2.getColumnIndex(Email.TYPE));
                                if (type == Email.TYPE_HOME) {
                                    email = c2.getString(c2.getColumnIndex(Email.DATA)) + "(家庭邮箱)";
                                }
                            }
                            c2.close();
                        }

                        //显示查询的结果
                        System.out.println("id=" + id +
                                " || 姓名=" + display_name +
                                " || 家庭号码=" + (home_num.equals("") ? "空" : home_num) +
                                " || 手机号码=" + (celPhone_num.equals("") ? "空" : celPhone_num) +
                                " || 家庭邮箱=" + (email.equals("") ? "空" : email));
                    }
                    c.close();
                }
            }
        });

        /*****************************************************************************/

        //添加操作
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentResolver cr = MainActivity.this.getContentResolver();

                //向联系人当中插入一行数据
                ContentValues values = new ContentValues();
                //--->获取到这个uri的目的是到时候把这个Uri转换成对应的行的id
                //--->到时候插入数据要使用到这个id
                Uri uri = cr.insert(RawContacts.CONTENT_URI, values);
                Long raw_contact_id = ContentUris.parseId(uri);
                values.clear();

                //插入人名
                values.put(StructuredName.RAW_CONTACT_ID, raw_contact_id);
                values.put(StructuredName.DISPLAY_NAME, "Test_1");
                values.put(StructuredName.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
                //获取到这个Uri的目的就不解释了
                uri = cr.insert(ContactsContract.Data.CONTENT_URI, values);

                //插入电话信息
                values.clear();
                values.put(Phone.RAW_CONTACT_ID, raw_contact_id);
                values.put(Phone.NUMBER, "13309485521");
                values.put(Phone.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
                uri = cr.insert(ContactsContract.Data.CONTENT_URI, values);

                Toast.makeText(MainActivity.this, "添加联系人成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
