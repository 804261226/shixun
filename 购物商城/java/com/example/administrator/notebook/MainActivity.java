package com.example.administrator.notebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {
    private ListView lv;
    private List<Notebook> list = new ArrayList();
    private MyAdapter adapter;
    private MyHelper helper;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
        getData();
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
    }
    public static String getTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }
    public void getData(){
        helper = new MyHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("notebook",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            list.add(new Notebook(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
        }
        cursor.close();
        db.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,EditActivity.class);
        Notebook notebook = list.get(position);
        intent.putExtra("text",notebook.getText().trim());
        intent.putExtra("time",notebook.getTime().trim());
        intent.putExtra("id",notebook.getId());
        intent.putExtra("position",position);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1 && requestCode == 1){
            int id = data.getIntExtra("id",0);
            String text = data.getStringExtra("text");
            String time = data.getStringExtra("time");
            int position = data.getIntExtra("position",0);
            list.set(position,new Notebook(text,time));
            adapter.notifyDataSetChanged();
            Toast.makeText(this,"编辑成功",Toast.LENGTH_SHORT).show();
        }else if(resultCode == 2 && requestCode == 1){
            Toast.makeText(this,"编辑失败",Toast.LENGTH_SHORT).show();
        }else if(resultCode == 1 && requestCode == 2){
            helper = new MyHelper(this);
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query("notebook",null,null,null,null,null,null);
            cursor.moveToLast();
            list.add(new Notebook(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            cursor.close();
            db.close();
            adapter.notifyDataSetChanged();
            Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
        }else if(resultCode == 2 && requestCode == 2){
            Toast.makeText(this,"添加失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this).setMessage("是否删除此记录")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Notebook notebook = list.get(position);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        int i = db.delete("notebook","id=?",new String[]{notebook.getId()+""});
                        if(i > 0){
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                            db.close();
                            Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"删除失败",Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();
        dialog.show();
        return false;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,AddActivity.class);
        startActivityForResult(intent,2);
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(MainActivity.this,R.layout.item_list,null);
            TextView tv = convertView.findViewById(R.id.tv);
            TextView tv2 = convertView.findViewById(R.id.tv2);
            tv.setText(list.get(position).getText());
            tv2.setText(list.get(position).getTime());
            return convertView;
        }
    }
}
