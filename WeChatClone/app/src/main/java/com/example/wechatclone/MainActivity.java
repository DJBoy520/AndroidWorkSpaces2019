package com.example.wechatclone;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
    ListView list_friend;
    String[] array_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_friend = (ListView) findViewById(R.id.ListView_friend);
        array_friend = getResources().getStringArray(R.array.friend_names);

        /* 第一个参数 this 代表的是当前上下文，可以理解为你当前所处的activity
         * 第二个参数 getData() 一个包含了数据的List,注意这个List里存放的必须是map对象。
           simpleAdapter中的限制是这样的List<? extends Map<String, ?>> data
         * 第三个参数 R.layout.user 展示信息的组件
         * 第四个参数 一个string数组，数组内存放的是你存放数据的map里面的key。
         * 第五个参数：一个int数组，数组内存放的是你展示信息组件中，每个数据的具体展示位置，与第四个参数一一对应
         */
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.user,
                new String[]{"image", "userName"}, new int[]{R.id.image, R.id.userName});
        list_friend.setAdapter(adapter);
    }

    private ArrayList<HashMap<String, Object>> getData() {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 6; i++) {
            HashMap<String, Object> tempHashMap = new HashMap<String, Object>();
            tempHashMap.put("image", R.drawable.friend);
            tempHashMap.put("userName", array_friend[i]);
            arrayList.add(tempHashMap);
        }
        return arrayList;
    }
}
