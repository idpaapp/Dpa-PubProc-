package ir.smartlab.persindatepicker;

public class IslamicCalendar {

	static double gmod(double n,double  m) {  
		return ((n % m) + m) % m;  
	} 
	private int Year, Month, Day;
	
	private String[] iMonthNames = {"محرم", "صفر", "ربیع الاول",  
			"ربیع الثانی", "جمادی الاول", "جمادی الثانی", "رجب",  
			"شعبان", "رمضان", "شوال", "ذیقعده", "ذیحجه"}; 	
	
	public int[] GregorianToArabic(int year, int month, int day) {   
		
		myPersianCalendar pCalendar = new myPersianCalendar();
		pCalendar.PersianToGregorian(year, month, day);
		
		double  hD = pCalendar.getDay();  
		double  hM = pCalendar.getMonth() - 1;  
		double  hY = pCalendar.getYear();

		double  m = hM + 1;  
		double  y = hY;  
		if (m < 3) {  
			y -= 1;  
			m += 12;  
		}  

		double a = Math.floor(y / 100.);  
		double b = 2 - a + Math.floor(a / 4.);  

		if (y < 1583)  
			b = 0;  
		if (y == 1582) {  
			if (m > 10)  
				b = -10;  
			if (m == 10) {  
				b = 0;  
				if (hD > 4)  
					b = -10;  
			}  
		}  

		double jd = Math.floor(365.25 * (y + 4716)) + Math.floor(30.6001 * (m + 1)) + hD  
				+ b - 1524;
		b = 0;  
		if (jd > 2299160) {  
			a = Math.floor((jd - 1867216.25) / 36524.25);  
			b = 1 + a - Math.floor(a / 4.);  
		}  
		double bb = jd + b + 1524;  
		double cc = Math.floor((bb - 122.1) / 365.25);  
		double dd = Math.floor(365.25 * cc);  
		double ee = Math.floor((bb - dd) / 30.6001);  
		hD = (bb - dd) - Math.floor(30.6001 * ee);  
		hM = ee - 1;  
		if (ee > 13) {  
			cc += 1;  
			hM = ee - 13;  
		}  
		hY = cc - 4716;  

		double wd = gmod(jd + 1, 7) + 1;  

		double iyear = 10631. / 30.;  
		double epochastro = 1948084;  
		double shift1 = 8.01 / 60.;  

		double z = jd - epochastro;  
		double cyc = Math.floor(z / 10631.);  
		z = z - 10631 * cyc;  
		double j = Math.floor((z - shift1) / iyear);  
		double iy = 30 * cyc + j;  
		z = z - Math.floor(j * iyear + shift1);  
		double im = Math.floor((z + 28.5001) / 29.5001);  
		if (im == 13)  
			im = 12;  
		double id = z - Math.floor(29.5001 * im - 28.5001);  

		int[]  myRes = new int[8];  

		myRes[0] = (int) hD; // calculated day (CE)  
		myRes[1] = (int) hM - 1; // calculated month (CE)  
		myRes[2] = (int) hY; // calculated year (CE)  
		myRes[3] = (int) jd - 1; // julian day number  
		myRes[4] = (int) wd - 1; // weekday number  
		myRes[5] = (int) id; // islamic date  
		myRes[6] = (int) im - 1; // islamic month  
		myRes[7] = (int) iy; // islamic year
		
		Year = (int) iy;
		Month = (int) im - 1;
		Day = (int) id;

		return myRes;  
	}  
	
    public int getDay() {
        return Day;
    }

    public int getMonth() {
        return Month;
    }

    public int getYear() {
        return Year;

    }	

	public void PersianToIslamic(int year, int month, int day) {  
		if(year == 0) return;
		
		int[] iDate = GregorianToArabic(year, month, day); 
				  
		Year = iDate[7];
		Month = iDate[6] + 1;
		Day = iDate[5];
	}
	
	public String IslamicDateFull(int year, int month, int day) { 
		if (year == 0) return "";
		int[] iDate = GregorianToArabic(year, month, day); 
				
		String outputIslamicDate = iDate[5] + " " + iMonthNames[(int) iDate[6]] + " " + iDate[7];  

		return outputIslamicDate;  
	}  
}  