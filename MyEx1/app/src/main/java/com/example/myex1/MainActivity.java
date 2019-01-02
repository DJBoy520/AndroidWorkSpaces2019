package com.example.myex1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.String;

class MainActivity extends Activity {
    private TextView show;
    private Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8,
            btn_9, btn_0, btn_jian, btn_jia, btn_cheng, btn_chu, btn_deng, btn_dian,
            btn_AC, btn_Back, btn_zuo, btn_you;
    private String ex = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show = (TextView) findViewById(R.id.show);
        btn_0 = (Button) findViewById(R.id.number0);
        btn_1 = (Button) findViewById(R.id.number1);
        btn_2 = (Button) findViewById(R.id.number2);
        btn_3 = (Button) findViewById(R.id.number3);
        btn_4 = (Button) findViewById(R.id.number4);
        btn_5 = (Button) findViewById(R.id.number5);
        btn_6 = (Button) findViewById(R.id.number6);
        btn_7 = (Button) findViewById(R.id.number7);
        btn_8 = (Button) findViewById(R.id.number8);
        btn_9 = (Button) findViewById(R.id.number9);
        btn_zuo = (Button) findViewById(R.id.zuo);
        btn_you = (Button) findViewById(R.id.you);
        btn_jia = (Button) findViewById(R.id.jia);
        btn_jian = (Button) findViewById(R.id.jian);
        btn_cheng = (Button) findViewById(R.id.cheng);
        btn_chu = (Button) findViewById(R.id.chu);
        btn_deng = (Button) findViewById(R.id.deng);
        btn_dian = (Button) findViewById(R.id.dian);
        btn_AC = (Button) findViewById(R.id.AC);
        btn_Back = (Button) findViewById(R.id.Back);

        btn_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ex.equals("0")) {
                    ex = "0";
                } else {
                    ex += "0";
                }
                show.setText(ex);
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ex.equals("0")) {
                    ex = "1";
                } else {
                    ex += "1";
                }
                show.setText(ex);
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ex.equals("0")) {
                    ex = "2";
                } else {
                    ex += "2";
                }
                show.setText(ex);
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ex.equals("0")) {
                    ex = "3";
                } else {
                    ex += "3";
                }
                show.setText(ex);
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ex.equals("0")) {
                    ex = "4";
                } else {
                    ex += "4";
                }
                show.setText(ex);
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ex.equals("0")) {
                    ex = "5";
                } else {
                    ex += "5";
                }
                show.setText(ex);
            }
        });
        btn_6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ex.equals("0")) {
                    ex = "6";
                } else {
                    ex += "6";
                }
                show.setText(ex);
            }
        });
        btn_7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ex.equals("0")) {
                    ex = "7";
                } else {
                    ex += "7";
                }
                show.setText(ex);
            }
        });
        btn_8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ex.equals("0")) {
                    ex = "8";
                } else {
                    ex += "8";
                }
                show.setText(ex);
            }
        });
        btn_9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ex.equals("0")) {
                    ex = "9";
                } else {
                    ex += "9";
                }
                show.setText(ex);
            }
        });
        btn_zuo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ex.equals("0")) {
                    ex = "(";
                } else {
                    ex += "(";
                }
                show.setText(ex);
            }
        });
        btn_you.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ex.equals("0")) {
                    ex = "0";
                } else if (ex.substring(ex.length() - 1, ex.length()).matches("[0-9]")) {
                    ex += ")";
                }
                show.setText(ex);
            }
        });
        btn_jia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!ex.equals("0")) {
                    if (ex.substring(ex.length() - 1, ex.length()).matches("[0-9)]")) {
                        ex += "+";
                    }
                }
                show.setText(ex);
            }
        });
        btn_jian.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!ex.equals("0")) {
                    if (ex.substring(ex.length() - 1, ex.length()).matches("[0-9)]")) {
                        ex += "-";
                    }
                }
                show.setText(ex);
            }
        });
        btn_cheng.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!ex.equals("0")) {
                    if (ex.substring(ex.length() - 1, ex.length()).matches("[0-9)]")) {
                        ex += "*";
                    }
                }
                show.setText(ex);
            }
        });
        btn_chu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!ex.equals("0")) {
                    if (ex.substring(ex.length() - 1, ex.length()).matches("[0-9)]")) {
                        ex += "/";
                    }
                }
                show.setText(ex);
            }
        });
        btn_dian.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!ex.equals("0")) {
                    if (ex.substring(ex.length() - 1, ex.length()).matches("[0-9]")) {
                        ex += ".";
                    }
                }
                show.setText(ex);
            }
        });
        btn_AC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ex = "0";
                show.setText(ex);
            }
        });
        btn_Back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ex.length() == 1) {
                    ex = "0";
                } else {
                    ex = ex.substring(0, ex.length() - 1);
                }
                show.setText(ex);
            }
        });

        btn_deng.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ex += "=";
                show.setText(ex);
                String rstr = "";
                Calculate cal = new Calculate();
                double result = cal.caculate(ex);
                rstr = Double.toString(result);
                show.setText(rstr);
                ex = rstr;
            }
        });
    }
}

