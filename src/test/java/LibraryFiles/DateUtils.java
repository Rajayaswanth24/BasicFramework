package LibraryFiles;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils
{
public String getCurrentTime(String timeZone) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		TimeZone T1 = TimeZone.getTimeZone(timeZone);
		dateFormat.setTimeZone(T1);
		String currentdate = dateFormat.format(date);
		return currentdate;
	}

	public String getCurrentDate(String timeZone) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		TimeZone T1 = TimeZone.getTimeZone(timeZone);
		dateFormat.setTimeZone(T1);
		String currentdate = dateFormat.format(date);
		return currentdate;
	}
	public String getTodaysDateyyyyMMddFormat() {
		Date dNow = new Date( );
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		String dateVal=sdfDate.format(dNow);
		return dateVal;
	}
	public String getTodaysDateddMMyyyyFormat() {
		Date dNow = new Date( );
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
		String dateVal=sdfDate.format(dNow);
		return dateVal;
	}
	public String getFiveDaysLaterDateyyyyMMddFormat() {
		Date dNow = new Date( );
		 Calendar c = Calendar.getInstance();
		 c.setTime(dNow);
		 c.add(Calendar.DATE, 5);
		 dNow=c.getTime();
		 SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		return sdfDate.format(dNow);
	}
	public String getTwoDaysLaterDateyyyyMMddFormat() {
		Date dNow = new Date( );
		 Calendar c = Calendar.getInstance();
		 c.setTime(dNow);
		 c.add(Calendar.DATE, 3);
		 dNow=c.getTime();
		 SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		return sdfDate.format(dNow);
	}
	public String getTwoDaysLaterDateddMMyyyyFormat() {
		Date dNow = new Date( );
		 Calendar c = Calendar.getInstance();
		 c.setTime(dNow);
		 c.add(Calendar.DATE, 3);
		 dNow=c.getTime();
		 SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
		return sdfDate.format(dNow);
	}
	public String getFifteenDaysLaterDateyyyyMMddFormat() {
		Date dNow = new Date( );
		 Calendar c = Calendar.getInstance();
		 c.setTime(dNow);
		 c.add(Calendar.DATE, 15);
		 dNow=c.getTime();
		 SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		return sdfDate.format(dNow);
	}
	public String getFifteenDaysLaterDateddMMyyyyFormat() {
		Date dNow = new Date( );
		 Calendar c = Calendar.getInstance();
		 c.setTime(dNow);
		 c.add(Calendar.DATE, 15);
		 dNow=c.getTime();
		 SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
		return sdfDate.format(dNow);
	}
	public String getNintyDaysLaterDateyyyyMMddFormat() {
		Date dNow = new Date( );
		 Calendar c = Calendar.getInstance();
		 c.setTime(dNow);
		 c.add(Calendar.MONTH, 5);
		 dNow=c.getTime();
		 SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		return sdfDate.format(dNow);
	}
	
	public String getNintyDaysLaterDateddMMyyyyFormat() {
		Date dNow = new Date( );
		 Calendar c = Calendar.getInstance();
		 c.setTime(dNow);
		 c.add(Calendar.MONTH, 5);
		 dNow=c.getTime();
		 SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
		return sdfDate.format(dNow);
	}
	public String getSixtyDaysLaterDateyyyyMMddFormat() {
		Date dNow = new Date( );
		 Calendar c = Calendar.getInstance();
		 c.setTime(dNow);
		 c.add(Calendar.MONTH, 2);
		 dNow=c.getTime();
		 SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		return sdfDate.format(dNow);
	}
	public String getSixtyDaysLaterDateddMMyyyyFormat() {
		Date dNow = new Date( );
		 Calendar c = Calendar.getInstance();
		 c.setTime(dNow);
		 c.add(Calendar.MONTH, 2);
		 dNow=c.getTime();
		 SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
		return sdfDate.format(dNow);
	}
	public String getParseDateyyyyMMddFormat(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yy");
    	Date myDate;
    	String nwDate="";
		try {
			myDate = dateFormat.parse(date);
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
			nwDate=dateFormat2.format(myDate);;
        	System.out.println(dateFormat2.format(myDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nwDate;
	}
	public String getParseDateMdyyyyFormat(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	Date myDate;
    	String nwDate="";
		try {
			myDate = dateFormat.parse(date);
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("M/d/yyyy");
			nwDate=dateFormat2.format(myDate);
        	System.out.println(dateFormat2.format(myDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nwDate;
	}
	
	public String getPastDateyyyyMMddFormat() {
		Date dNow = new Date( );
		 Calendar c = Calendar.getInstance();
		 c.setTime(dNow);
		 c.add(Calendar.DATE, -3);
		 dNow=c.getTime();
		 SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		return sdfDate.format(dNow);
	}
	public  String addDaysByOne(Date sd, int n)
    {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Calendar cal = Calendar.getInstance();
        cal.setTime((sd));
        cal.add(Calendar.DATE, n); //minus number would decrement the days
        return sdf.format(cal.getTime());
    }
}
