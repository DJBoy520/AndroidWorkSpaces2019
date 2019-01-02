package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Register extends Activity {

    private EditText username, password, repassword, others;
    private Spinner grade;
    private List<CheckBox> lan_chb;
    private RadioGroup sex_group;
    private Button submit;
    private String sgrade;
    private String[] mItems;
    private String FileName;
    private ArrayAdapter<String> adapter;

    public void init() {
        FileName = "user.txt";
        lan_chb = new ArrayList<CheckBox>();

        grade = (Spinner) findViewById(R.id.grade);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        others = (EditText) findViewById(R.id.others);

        lan_chb.add((CheckBox) findViewById(R.id.c));
        lan_chb.add((CheckBox) findViewById(R.id.cpp));
        lan_chb.add((CheckBox) findViewById(R.id.java));
        lan_chb.add((CheckBox) findViewById(R.id.py));

        sex_group = (RadioGroup) findViewById(R.id.sex);

        submit = (Button) findViewById(R.id.submit);
        mItems = getResources().getStringArray(R.array.greade);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grade.setAdapter(adapter);
    }

    public String getsex() {
        String sex = "";
        for (int i = 0; i < sex_group.getChildCount(); i++) {
            RadioButton sex_radiobut = (RadioButton) sex_group.getChildAt(i);
            if (sex_radiobut.isChecked()) {
                sex = sex_radiobut.getText().toString();
                break;
            }
        }
        return sex;
    }

    public String getgrade() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grade.setAdapter(adapter);
        grade.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == 0) {
                    sgrade = mItems[0];
                } else {
                    sgrade = mItems[arg2];
                }
                //设置显示当前选择的项
                arg0.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        return sgrade;
    }

    public String getlang() {
        String lang = "";
        for (int i = 0; i < lan_chb.size(); i++) {
            if (lan_chb.get(i).isChecked()) {
                lang += lan_chb.get(i).getText().toString() + " ";
            }
        }
        if (!others.getText().toString().equals("")) {
            lang += others.getText().toString();
        }
        return lang;
    }

    public String getusername() {
        return username.getText().toString();
    }

    public String getpassword() {
        return password.getText().toString();
    }

    public String getrepassword() {
        return repassword.getText().toString();
    }

    public String getothers() {
        return others.getText().toString();
    }

    public boolean checkup() {
        if (getusername().length() <= 15 && getusername().length() >= 5) {
            if (getpassword().length() >= 6 && getpassword().length() <= 16) {
                if (getpassword().equals(getrepassword())) {
                    if (!getlang().equals("")) {
                        return true;
                    } else {
                        Toast.makeText(Register.this, "请选择至少一种语言！", Toast.LENGTH_LONG).show();
                        return false;
                    }
                } else {
                    Toast.makeText(Register.this, "两次输入的密码不一致！", Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                Toast.makeText(Register.this, "密码长度应在6至16个字符之间！", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(Register.this, "用户名长度应在5至15个字符之间！", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public String ShowUser() {
        StringBuilder sshow = new StringBuilder("");

        sshow.append("Username:");
        sshow.append(getusername());
        sshow.append(";Grade:");
        sshow.append(sgrade);
        sshow.append(";Sex:");
        sshow.append(getsex());
        sshow.append(";Lang:");
        sshow.append(getlang());
        sshow.append("\n");

        return sshow.toString();
    }

    public void Submit() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkup()) {
                    Toast.makeText(Register.this, ShowUser(), Toast.LENGTH_SHORT).show();
                    //setContentView(R.layout.activity_main);
                    //show();
                    output();
                    input();

                    //Intent main_activity = new Intent(Register.this, MainActivity.class);
                    //main_activity.putExtra("uname", getusername());
                    //startActivity(main_activity);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        init();
        grade.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == 0) {
                    sgrade = mItems[0];
                } else {
                    sgrade = mItems[arg2];
                }
                arg0.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Submit();
    }

    public void output() {
        OutputStream out = null;
        try {
            FileOutputStream fos = openFileOutput(FileName, MODE_PRIVATE);
            out = new BufferedOutputStream(fos);
            try {
                out.write(this.ShowUser().getBytes(StandardCharsets.ISO_8859_1));
            } catch (Exception e) {
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } catch (Exception e) {
        }
    }

    private void input() {
        InputStream in = null;
        StringBuilder sb = new StringBuilder("");
        try {
            FileInputStream fis = openFileInput(FileName);
            in = new BufferedInputStream(fis);
            int c;
            try {
                while ((c = in.read()) != -1) {
                    sb.append((char) c);
                }
            } catch (Exception e) {
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (Exception e) {
        }
        Toast.makeText(Register.this, sb.toString(), Toast.LENGTH_LONG).show();
    }
}
