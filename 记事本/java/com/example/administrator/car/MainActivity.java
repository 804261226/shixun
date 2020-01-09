package com.example.administrator.car;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView lv;
    List<Car> list = new ArrayList<Car>();
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list.add(new Car("电脑主机","1999",R.drawable.comdfs));
        list.add(new Car("手机","4444",R.drawable.phone));
        list.add(new Car("电视","9999",R.drawable.tv));
        lv = findViewById(R.id.lv);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
        MyAdapter adapter = new MyAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyHelper helper = new MyHelper(MainActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",list.get(position).getTitle());
        values.put("price",list.get(position).getPrice());
        values.put("icon",list.get(position).getIcon()+"");
        long a = db.insert("car",null,values);
        if(a > 0){
            Toast.makeText(MainActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,CarActivity.class);
        startActivity(intent);
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
            TextView title = convertView.findViewById(R.id.title);
            TextView price = convertView.findViewById(R.id.price);
            ImageView iv = convertView.findViewById(R.id.iv);
            title.setText(list.get(position).getTitle());
            price.setText(list.get(position).getPrice());
            iv.setImageResource(list.get(position).getIcon());
            return convertView;
        }
    }
}
