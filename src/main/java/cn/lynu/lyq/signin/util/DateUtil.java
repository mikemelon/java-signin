package cn.lynu.lyq.signin.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date[] getBetweenDates(String dateStr){
		Date[] date=new Date[2];
		try{
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			Date theDate = sdf1.parse(dateStr);
			Calendar mycal=Calendar.getInstance();
			mycal.setTime(theDate);
			
			Calendar beginCal=(Calendar)mycal.clone();
			beginCal.set(Calendar.HOUR_OF_DAY, 0);
			beginCal.set(Calendar.MINUTE, 0);
			beginCal.set(Calendar.SECOND, 0);
			Calendar endCal=(Calendar)mycal.clone();
			endCal.set(Calendar.HOUR_OF_DAY, 23);
			endCal.set(Calendar.MINUTE, 59);
			endCal.set(Calendar.SECOND, 59);	
			
			
			date[0]=beginCal.getTime();
			date[1]=endCal.getTime();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return date;
	}
	
	
	public static void main(String[] args) {
		Date[] dates = getBetweenDates("2012-04-01");
		System.out.println("begin="+dates[0]+"/end="+dates[1]);

	}

}
