package com.example.administrator.notebook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private String text;
    private EditText et;
    private Button btn1,btn2;
    private int id;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        et = findViewById(R.id.et);
        Intent intent = getIntent();
        text = intent.getStringExtra("text");
        id = intent.getIntExtra("id",0);
        position= intent.getIntExtra("position",0);
        et.setText(text);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                finish();
                break;
            case R.id.btn2:
                String text = et.getText().toString().trim();
                String time = MainActivity.getTime();
                MyHelper helper = new MyHelper(this);
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("text",text);
                values.put("time",time);
                int a = db.update("notebook",values,"id=?",new String[]{id+""});
                if(a > 0){
                    Intent intent = new Intent();
                    intent.putExtra("id",id);
                    intent.putExtra("time",time);
                    intent.putExtra("text",text);
                    intent.putExtra("position",position);
                    setResult(1,intent);
                }else{
                    setResult(2);
                }
                db.close();
                finish();
                break;
        }
    }
}
