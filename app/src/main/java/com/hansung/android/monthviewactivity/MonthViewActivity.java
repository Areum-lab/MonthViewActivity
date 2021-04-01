package com.hansung.android.monthviewactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
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
        }

        //현재 년,월에 맞는 캘린더 객체 생성
        Calendar sDay = Calendar.getInstance(); //시작일
        Calendar eDay = Calendar.getInstance(); //끝일
        sDay.set(year,month,1); //시작일은 현재 년,월의 1일로 설정
        eDay.set(year,month+1,1); //끝일은 현재 년,다음달 1일로 설정
        eDay.add(Calendar.DATE,  -1); //다음 월 1일에서 -1을 하면 현재달의 마지막 날이 된다.

        int START_DAY_OF_WEEK = sDay.get(Calendar.DAY_OF_WEEK); //첫번째 요일이 무슨 요일인지 알아낸다.
        int END_DAY = eDay.get(Calendar.DATE); //eDay에 저장된 마지막날의 날짜를 알아낸다.

        String[] date = new String[6*7]; //6*7사이즈의 String형 배열을 선언한다.

        for(int i=0, n=1; i<date.length; i++){
            if(i < START_DAY_OF_WEEK-1) //date배열에 첫번째 요일까지 공백으로 채운다.
                date[i] = "";           //DAY_OF_WEEK는 일요일:1 ~ 토요일:7로 결과가 나오기때문에 START_DAT_OF_WEEK에서 -1을 해준 값 전까지가 공백이다.
            else if((i >= START_DAY_OF_WEEK-1) && (n <= END_DAY)) { //START_DAY_OF_WEEK에서 -1해준 값부터 배열은 시작한다.
                date[i] = n+"";                                     //n값은 배열에 날짜를 입력하기위한 변수이고, 1일부터 END_DAY 즉, 마지막 날짜까지 배열에 입력한다.
                n++;
            }
            else                //이 전까지의 배열은 (START_DAY_OF_WEEK-1)+(END_DAY-1)까지 채워져있으니까
                date[i] = "";   //START_DAY_OF_WEEK+END_DAY-1부터 배열의 마지막까지 공백으로 채워준다.
        }

        TextView yearmonth = findViewById(R.id.YearMonth); //날짜를 쓰는 yearmonth 텍스트 뷰
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


        //어댑터 준비 (date 배열 객체 이용, simple_list_item_1 리소스 사용)
        ArrayAdapter<String> adapt
                = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                date);

        // gridview id를 가진 화면 레이아웃에 정의된 GridView 객체 로딩
        GridView gridview = (GridView) findViewById(R.id.gridview);
        // 어댑터를 GridView 객체에 연결
        gridview.setAdapter(adapt);
    }
}