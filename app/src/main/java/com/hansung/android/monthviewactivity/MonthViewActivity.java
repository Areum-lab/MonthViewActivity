package com.hansung.android.monthviewactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MonthViewActivity extends AppCompatActivity {

    private int year;
    private int month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthview);

        Intent intent = getIntent();
        year = intent.getIntExtra("year",-1);
        month = intent.getIntExtra("month",-1);
        //-1사용한 이유: year,month 에는 -1이 없기 때문

        if(year == -1 || month == -1) {
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH); //month 는 0부터 시작
        }

        //현재 년,월에 맞는 캘린더 객체 생성
        Calendar sDay = Calendar.getInstance(); //시작일
        sDay.set(year,month,1); //시작일은 현재 년,월의 1일로 설정

        int START_DAY_OF_WEEK = sDay.get(Calendar.DAY_OF_WEEK); //시작일의 요일(1일의 요일)을 알아냄
        int END_DAY = sDay.getActualMaximum(Calendar.DATE); //현재 월의 마지막 날짜를 알아냄

        String[] date = new String[6*7]; //6*7사이즈의 String형 배열을 선언

        //배열에 요일 입력
        for(int i=0, n=1; i<date.length; i++){
            if(i < START_DAY_OF_WEEK-1)  //date배열에 첫번째 요일 전까지 공백으로 채움
                date[i] = "";            //DAY_OF_WEEK는 일요일:1 ~ 토요일:7로 결과가 나옴 -> START_DAT_OF_WEEK에서 -1을 해준 값 전까지가 공백

            else if((i >= START_DAY_OF_WEEK-1) && (n <= END_DAY)) {//START_DAY_OF_WEEK에서 -1해준 값부터 배열 시작
                date[i] = n+"";                                    //n값은 배열에 날짜를 입력하기위한 변수이고, 1일부터 END_DAY 즉, 마지막 날짜까지 배열에 입력
                n++;
            }

            else                    //이 전까지의 배열은 (START_DAY_OF_WEEK-1)+(END_DAY-1)까지 채워져있으니까
                date[i] = "";       //START_DAY_OF_WEEK+END_DAY-1부터 배열의 마지막까지 공백으로 채워줌.
        }

        TextView yearmonth = findViewById(R.id.YearMonth); //날짜를 쓰는 yearmonth 텍스트 뷰
        yearmonth.setText(year+"년 "+ (month+1)+"월 "); //month 는 0부터 시작이니까 +1한 값을 보여줘야 함

        Button prebtn = findViewById(R.id.monthPrevious); //이전버튼에 대한 onClickListener
        prebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                if(month == 0){ //현재 month값이 0(1월)일 때
                    intent.putExtra("year",year-1); //이전년으로
                    intent.putExtra("month",11); //12월로
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
                if(month == 11){ //현재 month값이 11(12월)일 때
                    intent.putExtra("year",year+1); //다음년으로
                    intent.putExtra("month",0); //1월로
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
        //어댑터를 GridView 객체에 연결
        gridview.setAdapter(adapt);

        //항목 클릭 이벤트 처리
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(!(date[position].equals(""))) //date[position]값이 공백이 아닐 경우만 toast 메세지 출력
                    Toast.makeText(MonthViewActivity.this,
                            year + "." + (month + 1) + "." + date[position],
                            Toast.LENGTH_SHORT).show();
            }
        });
    }
}
