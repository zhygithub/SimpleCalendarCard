package network.scau.com.calendarcard;

import java.util.Calendar;

public class CalendarUtils {
private static Calendar calendar = Calendar.getInstance();
	
	public static int getCurrentYear(){
		return calendar.get(Calendar.YEAR);
	}
	
	public static int getCurrentMonth(){
		return calendar.get(Calendar.MONTH)+1;
	}
	
	public static int getCurrentDate(){
		return calendar.get(Calendar.DATE);
	}
	
	public static int getCurrentMaxNumOfMonth(){
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static int getCurrentFirstWeekdayOfMoth(){
		int today = getCurrentDate();
		calendar.set(Calendar.DATE, 1);
		int weekday = calendar.get(Calendar.DAY_OF_WEEK)-1;
		calendar.set(Calendar.DATE, today);
		return weekday;
	}
	
	public static void nextMonth(){
		calendar.add(Calendar.MONTH, 1);
	}
	
	public static void preMonth(){
		calendar.add(Calendar.MONTH, -1);
	}
	
	public static void printCalendar(){
		System.out.println(getCurrentYear()+"年"+getCurrentMonth()+"月"+getCurrentDate()+"日");
		System.out.println("总共有"+getCurrentMaxNumOfMonth()+"天"+"第一天是星期"+getCurrentFirstWeekdayOfMoth());
	}
	
	
}
