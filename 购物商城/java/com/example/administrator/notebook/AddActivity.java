package com.example.administrator.notebook;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et;
    private Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        et = findViewById(R.id.et);
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
                long a = db.insert("notebook",null,values);
                if(a > 0){
                    setResult(1);
                }else{
                    setResult(2);
                }
                db.close();
                finish();
                break;
        }
    }
}
