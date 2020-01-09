package com.example.administrator.car;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CarActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    ListView lv;
    List<Car> list = new ArrayList<Car>();
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        getData();
        lv = findViewById(R.id.lv);
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(this);
    }
    public void getData(){
        MyHelper helper = new MyHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("car",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            list.add(new Car(cursor.getInt(0),cursor.getString(1),cursor.getString(2),Integer.valueOf(cursor.getString(3))));
        }
    }
    public int  getResource(String imageName){
        Context ctx=getBaseContext();
        int resId = getResources().getIdentifier(imageName, "mipmap", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(CarActivity.this).setMessage("是否删除此记录")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Car car = list.get(position);
                        MyHelper helper = new MyHelper(CarActivity.this);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        int i = db.delete("car","id=?",new String[]{car.getId()+""});
                        if(i > 0){
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                            db.close();
                            Toast.makeText(CarActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CarActivity.this,"删除失败",Toast.LENGTH_SHORT).show();

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

    class MyAdapter extends BaseAdapter {

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
            convertView = View.inflate(CarActivity.this,R.layout.cat_item,null);
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
