package club.zhengjiadi.problem8;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    Button addData;

    ArrayAdapter<String> adapter;
    List<String> logMessageList = new ArrayList<>();
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        updateList(); //更新list

        ListView listView = findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, logMessageList);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String str[] = logMessageList.get(position).split("\n");
                ID = str[0];
                AlertDialog.Builder dialog = new AlertDialog.Builder(Main2Activity.this);
                dialog.setTitle("Are you sure to delete this message?");
                dialog.setMessage("whose id = "+ ID);
                dialog.setCancelable(false);
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){
                        DataSupport.deleteAll(LogMessage.class, "id=?", ID);

                        updateList();
                        adapter.notifyDataSetChanged();
//                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//                        startActivity(intent);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){

                    }
                });
                dialog.show();
            }
        });

        addData = findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 5; i++) {
                    LogMessage logMessage = new LogMessage();
                    logMessage.setCreateTime(new Date());
                    logMessage.setLog("Log at time : " + new Date());
                    logMessage.save();
                }
                updateList();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void updateList(){
        /*----Test Sample---*/
//        Date date = new Date();
//        for(int i=0; i<20; i++) {
//            LogMessage logMessage = new LogMessage();
//            DateFormat df = DateFormat.getTimeInstance();//只显示出时分秒
//            String time = df.format(date);
//            item.setCount(i);
//            item.setDate(time);
//            item.save();
//        }
        this.logMessageList.clear();
        List<LogMessage>logMessageList= DataSupport.order("id desc").find(LogMessage.class);
        for(LogMessage logMessage:logMessageList){
            String str = logMessage.getId()+"\n"+logMessage.getCreateTime() + "\n" + logMessage.getLog();
            this.logMessageList.add(str);
        }
    }
}
