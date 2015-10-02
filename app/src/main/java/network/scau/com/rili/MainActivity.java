package network.scau.com.rili;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MyCalendarCard card;

    private int NowDay;
    private int NowMonth;
    private int NowYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        card = (MyCalendarCard) findViewById(R.id.id_mcc);
        //----------------------------------------------

//        int tyear = CalendarUtils.getCurrentYear();
//        int tmonth = CalendarUtils.getCurrentMonth();
//        int tday = CalendarUtils.getCurrentDate();
//        int tdayOfWeek = CalendarUtils.getCurrentFirstWeekdayOfMoth();
//        int tmaxDayNum = CalendarUtils.getCurrentMaxNumOfMonth();
//        NowDay = tday;
//        NowMonth = tmonth;
//        NowYear = tyear;
//        card.setYear(tyear);
//        card.setMonth(tmonth);
//        card.setAllDays(tmaxDayNum);
//        card.setToday(tday);
//        card.setWeekOfFirstDay(tdayOfWeek);

        //----------------------------------------------

        card.setCanClick(true);
        card.setOnChooseListener(new OnChooseListener() {
            @Override
            public void onSingleChoose(int day) {
//                Toast.makeText(MainActivity.this, "you choice is "+day, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDoubleChoose(int[] days) {
//                Toast.makeText(MainActivity.this, "you choice is "+printDats(days), Toast.LENGTH_SHORT).show();
            }
        });

        card.setOnTurnPageListener(new OnTurnPageListener() {
            @Override
            public void OnLeftUp(int today, int month, int year) {
//                CalendarUtils.preMonth();
//                int tyear = CalendarUtils.getCurrentYear();
//                int tmonth = CalendarUtils.getCurrentMonth() + 1;
//                int tday = CalendarUtils.getCurrentDate();
//                int tdayOfWeek = CalendarUtils.getCurretnFirstDayOfWeek() - 1;
//                int tmaxDayNum = CalendarUtils.getCurrentMaxDayNumOfMonth();
//                card.setYear(tyear);
//                card.setMonth(tmonth);
//                card.setAllDays(tmaxDayNum);
//                card.resetClick();
//
//                if (NowDay == tday && NowMonth == tmonth && NowYear == tyear) {
//                    card.setToday(tday);
//                } else {
//                    card.setToday(0);
//                }
//
//                card.setWeekOfFirstDay(tdayOfWeek);
            }

            @Override
            public void OnLeftDown(int today, int month, int year) {

            }

            @Override
            public void OnRightUp(int today, int month, int year) {
//                CalendarUtils.nextMonth();
//                int tyear = CalendarUtils.getCurrentYear();
//                int tmonth = CalendarUtils.getCurrentMonth() + 1;
//                int tday = CalendarUtils.getCurrentDate();
//                int tdayOfWeek = CalendarUtils.getCurretnFirstDayOfWeek() - 1;
//                int tmaxDayNum = CalendarUtils.getCurrentMaxDayNumOfMonth();
//                card.setYear(tyear);
//                card.setMonth(tmonth);
//                card.setAllDays(tmaxDayNum);
//                card.resetClick();
//                if (NowDay == tday && NowMonth == tmonth && NowYear == tyear) {
//                    card.setToday(tday);
//                } else {
//                    card.setToday(0);
//                }
//                card.setWeekOfFirstDay(tdayOfWeek);
            }

            @Override
            public void OnRightDown(int today, int month, int year) {

            }
        });
    }


    private String printDats(int[] days) {
        StringBuilder res = new StringBuilder("");
        for (int i = 0; i < days.length; i++) {
            res.append("|" + days[i]);
        }
        return res.toString();
    }

}
