package club.zhengjiadi.problem3;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.wifi.WifiEnterpriseConfig;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    List<Contact> contactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getPermission() == 0) {
            readAllContacts();
        }
        Log.d(TAG, String.valueOf(contactsList));

        ListView contactsView = findViewById(R.id.contacts_list);
        ContactAdapter adapter = new ContactAdapter(MainActivity.this, R.layout.activity_item, contactsList);
        contactsView.setAdapter(adapter);

        contactsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contactsList.get(position);
                String name = contact.getName();
                List<String> number = contact.getNumber();
                List<String> email = contact.getEmail();
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Contact Details");
                dialog.setMessage(String.format("名字: %s\n手机号: %s\n邮箱: %s", name, number, email));
                dialog.setCancelable(false);

                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();

            }
        });
    }

    public void readAllContacts() {
        /**
         * 读取所有联系人的信息（name number email）并添加到 contactsList
         */
        Toast.makeText(this, "读取联系人", Toast.LENGTH_SHORT).show();

        Cursor cursor = this.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        //获取id 和 名字对应的列号
        int contactIdIndex = 0;
        int nameIndex = 0;
        if (cursor.getCount() > 0) {
            contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        }
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(contactIdIndex);

            Contact contact = new Contact();
            String name = cursor.getString(nameIndex);

            contact.setName(name);
            Log.i(TAG, contactId);
            Log.i(TAG, name);

            /*
             * 查找该联系人的phone信息
             */
            Cursor phones = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null, null);
            int phoneIndex = 0;
            if (phones.getCount() > 0) {
                phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            }
            while (phones.moveToNext()) {
                String phoneNumber = phones.getString(phoneIndex);
                if (phoneNumber != null) {
                    contact.setNumber(phoneNumber);
                }
                Log.i(TAG, phoneNumber);
            }

                        /*
             * 查找该联系人的email信息
             */
            Cursor emails = this.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contactId,
                    null, null);
            int emailIndex = 0;
            if (emails.getCount() > 0) {
                emailIndex = emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
            }
            while (phones.moveToNext()) {
                String email = emails.getString(emailIndex);
                if (email != null) {
                    contact.setEmail(email);
                }
                Log.i(TAG, email);
            }

            contactsList.add(contact);
        }

        Toast.makeText(this, "读取完成。", Toast.LENGTH_SHORT).show();


    }

    public int getPermission() {
        /**
         * 返回未获得权限的数量
         */
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_CONTACTS);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_CONTACTS);
        }

        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            Log.d(TAG, "request");
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }

        return permissionList.size();
    }


    private void readContacts() {
        Cursor cursor = null;
        Cursor cursorE = null;
        try {
            // 查询联系人数据
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            cursorE = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null, null, null);

            if (cursor != null && cursorE != null) {
                while (cursor.moveToNext() && cursorE.moveToNext()) {
                    // 获取联系人姓名
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    // 获取联系人手机号
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    // 获取联系人email
                    String email = cursorE.getString(cursorE.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    contactsList.add(new Contact(displayName, number, email));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    readAllContacts();
//                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

}
