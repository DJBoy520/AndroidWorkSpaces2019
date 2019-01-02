package com.example.alltext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends Activity {
    private DatePicker datePicker;

    Bundle savedInstanceState;
    private Button transfrom_time;

    public void time_run() {
        setContentView(R.layout.time);

        TimePicker timePicker = (TimePicker) findViewById(R.id.tpPicker);
        Button transfrom_data = (Button) findViewById(R.id.transftom_data);

        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(MainActivity.this, hourOfDay + "小时" + minute + "分钟", Toast.LENGTH_SHORT).show();
            }
        });

        transfrom_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data_run();
            }
        });
    }

    public void data_run() {
        setContentView(R.layout.activity_main);

        datePicker = (DatePicker) findViewById(R.id.dpPicker);
        transfrom_time = (Button) findViewById(R.id.transfrom_time);

        datePicker.init(2017, 9, 25, new OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 获取一个日历对象，并初始化为当前选中的时间
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
                Toast.makeText(MainActivity.this, format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
            }
        });

        transfrom_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_run();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data_run();

    }
}
