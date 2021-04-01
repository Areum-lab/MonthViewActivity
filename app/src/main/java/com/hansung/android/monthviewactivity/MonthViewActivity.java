package com.hansung.android.monthviewactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MonthViewActivity extends AppCompatActivity {

    private int year;
    private int month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthview);

        Intent intent = getIntent();
        year = intent.getIntExtra("year",-1); //-1사용한 이유: year에는 -1이 없음.
        month = intent.getIntExtra("month",-1);


        if(year == -1 || month == -1) {
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH); //month는 0부터 시작.
        }TextView yearmonth = findViewById(R.id.YearMonth); //날짜를 쓰는 yearmonth 텍스트 뷰
        yearmonth.setText(year+"년 "+ (month+1)+"월 "); //month는 0부터 시작이니까 plus 1한 값을 보여줘야 함

        Button prebtn = findViewById(R.id.monthPrevious); //이전버튼에 대한 onClickListener
        prebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                if(month-1 == -1){
                    intent.putExtra("year",year-1);
                    intent.putExtra("month",11);
                }
                else {
                    intent.putExtra("year", year);
                    intent.putExtra("month", month - 1);
                }

                startActivity(intent);
                finish();
            }
        });

        Button nexbtn = findViewById(R.id.monthNext);
        nexbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                if(month+1 == 12){
                    intent.putExtra("year",year+1);
                    intent.putExtra("month",0);
                }
                else {
                    intent.putExtra("year", year);
                    intent.putExtra("month", month + 1);
                }

                startActivity(intent);
                finish();
            }
        });
    }
}