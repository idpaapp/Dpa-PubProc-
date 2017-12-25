package ir.smartlab.persindatepicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class myPersianCalendar {

    private int day, month, year;

    private int jY, jM, jD;
    private int gY, gM, gD;
    private int leap;
    private int[] mDaysInPersianYear   = { 0, 31, 62, 93, 124, 155, 186, 216, 246, 276, 306, 336, 365 };
    private int[] mDaysInGregorianYear = { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365 };
    private String[] mMonthNames = {"فروردین",
    								"اردیبهشت",
    								"خرداد",
    								"تیر",
    								"مرداد",
    								"شهریور",
    								"مهر",
    								"آبان",
    								"آذر",
    								"دی",
    								"بهمن",
    								"اسفند"};
    
    private String[] mGMonthNames = {"January",
			"February",
			"March",
			"April",
			"May",
			"June",
			"July",
			"August",
			"September",
			"October",
			"November",
			"December"};    
    
    private String[] mWeekDayNames = {"شنبه",
									"یکشنبه",
									"دوشنبه",
									"سه شنبه",
									"چهارشنبه",
									"پنجشنبه",
									"جمعه"};    
    
    
    private int PersianDateToInt(int year, int month, int day) {
        int returnValue = 0;
        leap = year / 4;
        returnValue = ((year - 1) * 365) + leap;       
        returnValue += mDaysInPersianYear[month - 1];        
        returnValue += day;
        
        return returnValue;
    }

    private int GregorianDateToInt(int year, int month, int day) {
        int returnValue = 0;
        leap = year / 4;
        returnValue = ((year - 1) * 365) + leap;       
        returnValue += mDaysInGregorianYear[month - 1];        
        returnValue += day;
        if (IsLeapYear(year) && month > 2)
        	returnValue += 1;        
        return returnValue;
    }    
    
    private void GregorianDateFromInt(int DateValue) {    	
    	int fgY = (int) Math.floor(DateValue / 365);
        leap = fgY / 4;      
        DateValue -= leap;
        gY = (int) Math.floor(DateValue / 365);
        gY++;
        
        if ((fgY / 4) != (gY / 4))
        	DateValue++;
                
        int mDateValue = DateValue % 365;
                
        gM = 12;
        for (int i = 0; i <= 11; i++)
        {
        	if (mDateValue <= mDaysInGregorianYear[i]){
        		gM = i;
        		break;
        	}
        }
        if (gM == 0)
        	gM = 1;
                
        if (gY % 4 == 0 && gM < 3)
        	mDateValue += 1;          
        
        gD = mDateValue - mDaysInGregorianYear[gM - 1];
        if (gD <= 0)
        	switch (gM) {
			case 1 : {gD = 31; break;}
			case 2 : { if (IsLeapYear(gY)) gD = 29; else gD = 28; break;}
			case 3 : {gD = 31; break;}
			case 4 : {gD = 30; break;}
			case 5 : {gD = 31; break;}
			case 6 : {gD = 30; break;}
			case 7 : {gD = 31; break;}
			case 8 : {gD = 31; break;}
			case 9 : {gD = 30; break;}
			case 10: {gD = 31; break;}
			case 11: {gD = 30; break;}
			case 12: {gD = 31; break;}
			default:
				break;
			} 
    } 
    
    private String ifOneChar(int input)
    {
    	if (input < 10)
    		return "0" + String.valueOf(input);
    	else
    		return String.valueOf(input);
    }
    
    private void PersianDateFromInt(int DateValue) {    	
    	int fjY = (int) Math.floor(DateValue / 365);
        leap = fjY / 4;      
        DateValue -= leap;
        jY = (int) Math.floor(DateValue / 365);
        jY++;       
        	
        if ((fjY / 4) != (jY / 4))
        	DateValue++;	
        	
        int mDateValue = DateValue % 365;

        if (IsLeapYear(jY))
        	mDateValue += 1;
        
        jM = 12;
        for (int i = 0; i<=12; i++)
        {
        	if (mDateValue <= mDaysInPersianYear[i]){
        		jM = i;
        		break;
        	}
        }                  
        
        jD = mDateValue - mDaysInPersianYear[jM - 1]; 
        if (jD <= 0)
        	switch (jM) {
			case 1 : {jD = 31; break;}
			case 2 : {jD = 31; break;}
			case 3 : {jD = 31; break;}
			case 4 : {jD = 31; break;}
			case 5 : {jD = 31; break;}
			case 6 : {jD = 31; break;}
			case 7 : {jD = 30; break;}
			case 8 : {jD = 30; break;}
			case 9 : {jD = 30; break;}
			case 10: {jD = 30; break;}
			case 11: {jD = 30; break;}
			case 12: { if (IsLeapYear(jY)) jD = 30; else jD = 29; break;}
			default:
				break;
			}
    }         
        
    public void GregorianToPersian(int year, int month, int day) {
    	int gInt = GregorianDateToInt(year, month, day);
    	gInt -= 226899;
    	PersianDateFromInt(gInt);
    	
        this.year = jY;
        this.month = jM;
        this.day = jD;
    }

    public void PersianToGregorian(int year, int month, int day) {
    	int jInt = PersianDateToInt(year, month, day);  
    	jInt += 226899;
    	GregorianDateFromInt(jInt);
    	
        this.year = gY;
        this.month = gM;
        this.day = gD;
    }
    
    public String PersianToGregorianDate(int year, int month, int day) {
    	int jInt = PersianDateToInt(year, month, day);  
    	jInt += 226899;
    	GregorianDateFromInt(jInt);

    	this.year = gY;
        this.month = gM;
        this.day = gD;
    	
    	return String.valueOf(gY) + "/" + ifOneChar(gM)+"/"+ ifOneChar(gD); 
    }   
    
    public String PersianFullDate(int year, int month, int day) {
    	return String.valueOf(year) + "/" + ifOneChar(month)+"/"+ ifOneChar(day); 
    }
    
    public String PersianFullDateByInt(int Today) {
    	PersianDateFromInt(Today);
    	return String.valueOf(this.jY) + "/" + ifOneChar(this.jM)+"/"+ ifOneChar(this.jD); 
    }
    
    public String PersianToGregorianDateFull(int year, int month, int day) {
    	if(year == 0) return "";
    	int jInt = PersianDateToInt(year, month, day);  
    	jInt += 226899;
    	GregorianDateFromInt(jInt);

    	this.year = gY;
        this.month = gM;
        this.day = gD;
    	
    	return String.valueOf(gD) + " " + GetGMonthName(gM)+ " " + String.valueOf(gY); 
    }   
    
    public void AddDay(int year, int month, int day, int Adddays) {
    	if (year == 0) return;
    	int ActDay = PersianDateToInt(year, month, day);
    	ActDay += Adddays;
    	PersianDateFromInt(ActDay);
        this.year = jY;
        this.month = jM;
        this.day = jD;     	
    }
    
    public static String GetDayByDate(String ActiveDate)
    {
    	return ActiveDate.substring(8);
    }

    public static String GetMonthByDate(String ActiveDate)
    {
    	return ActiveDate.substring(5,7);
    }
    
    public static String GetYearByDate(String ActiveDate)
    {
    	return ActiveDate.substring(0,4);
    }    
    
    public void SetDay(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
       
    public int GetTodayInt() {        
    	SetToday();
    	int ljY = this.year;
    	int ljM = this.month;
    	int ljD = this.day;
    	
    	int jInt = PersianDateToInt(ljY, ljM, ljD);
        return jInt;
    }    
    
    public int GetDayInt(int year, int month, int day) {    
    	if (year == 0) return 0;
    	int jInt = PersianDateToInt(year, month, day);
        return jInt;
    }     
    
    public void SetDay(int jInt) {
        PersianDateFromInt(jInt);
        
        this.year = jY;
        this.month = jM;
        this.day = jD;        
    }    
    
    public boolean IsLeapYear(int y)
    {
        int[] matches = { 1, 5, 9, 13, 17, 22, 26, 30 };
        int modulus = y - ((y / 33) * 33);
        boolean K = false;
        for (int n = 0; n < 8; n++) if (matches[n] == modulus) K = true;
        return K;
    }
    
	private int getDay(int day) {
		switch (day) {
		case Calendar.SATURDAY:
			return 0;
		case Calendar.SUNDAY:
			return 1;
		case Calendar.MONDAY:
			return 2;
		case Calendar.TUESDAY:
			return 3;
		case Calendar.WEDNESDAY:
			return 4;
		case Calendar.THURSDAY:
			return 5;
		case Calendar.FRIDAY:
			return 6;
		default:
			return 0;
		}
	}  
	
	public String getDayName(int day) {		
		return mWeekDayNames[day];
	} 	
	
    public int DayOfWeek(int year, int month, int day)
    {
    	if (year == 0) return 0;
    	PersianToGregorian(year, month, day);
    	GregorianCalendar mCalendar = new GregorianCalendar(this.year, this.month - 1, this.day);    	
    	return getDay(mCalendar.get(Calendar.DAY_OF_WEEK));
    }
    
    public void SetToday()
    {    	
    	Calendar cal = Calendar.getInstance(); 
    	int gDay   = cal.get(Calendar.DAY_OF_MONTH);
    	int gMonth = cal.get(Calendar.MONTH);
    	int gYear  = cal.get(Calendar.YEAR);
    	
    	GregorianToPersian(gYear, gMonth + 1, gDay);    	    	
        this.year = jY;
        this.month = jM;
        this.day = jD;    	
    }    
    
    public String GetMonthName(int monthcode)
    {
    	if (monthcode == 0)
    		return "";
    	return mMonthNames[monthcode - 1];
    }
    
    public int GetMonthCode(String MonthName)
    {
    	int monthcode = 0;
    	for (int i=0; i<12; i++)
    	{
    		String mName = mMonthNames[i].trim();
    		if ( mName == MonthName.trim())
    			monthcode = i +1;
    	}
    	return monthcode;
    }    
 
    public String GetGMonthName(int monthcode)
    {
    	if (monthcode == 0)
    		return "";
    	return mGMonthNames[monthcode - 1];
    }
    public int getDay() {
        return day;
    }
    
    public String[] GetMonthList()
    {
    	return mMonthNames;
    }
    
    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;

    }
}
