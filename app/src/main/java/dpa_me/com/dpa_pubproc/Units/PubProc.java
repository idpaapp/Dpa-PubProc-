package dpa_me.com.dpa_pubproc.Units;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import dpa_me.com.dpa_pubproc.BuildConfig;
import dpa_me.com.dpa_pubproc.CustomViews.RoundedImageView;
import dpa_me.com.dpa_pubproc.CustomViews.SlidingTabLayout;
import dpa_me.com.dpa_pubproc.CustomViews.SlidingTabPageAdapter;
import dpa_me.com.dpa_pubproc.Dialogs.MyProgressDialog;
import dpa_me.com.dpa_pubproc.Dialogs.QuestionDialogClass;
import dpa_me.com.dpa_pubproc.Dialogs.ShowMessageDialogClass;
import dpa_me.com.dpa_pubproc.R;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

public class PubProc {
	public static final int MY_PERMISSIONS = 123;

	public static MenuAdapter adapter;
	public static int MessageCount;
	public static int NotificationCount;
	public static int UserID;
	public static int ResultOfPayment;
	public static String UserImage;
	public static String LocalPath;
	public static String InvoiceNo;
	public static List<String> LastTitle;
	public static int xdpi = 0;
	public static int ydpi = 0;
	public static float DisplayDensityDpi = 0;
	public static FragmentManager mFragmentManager;
	public static FragmentManager mMainFragmentManager;
	public static FragmentManager mOrginalFragmentManager;
	public static Context mContext;
	public static AppCompatActivity mActivity;
	public static AppCompatActivity mMainPageActivity;
	public static LayoutInflater mInflater;
	public static String PicUri;
	public static String PicURL;
	public static String current = "";
	public static RoundedImageView imgProfile;
	public static TextView txtTitle;
	public static Typeface MainTypeFace;
	public static DrawerLayout mDrawerLayout;
	public static ListView mDrawerList;
	public static int SlidingTabLayoutSize;
	public static MyProgressDialog mProgressDialog;
	public static QuestionDialogClass mQuestionDialog;
	public static ShowMessageDialogClass mShowMessageDialog;
	public static String HttpRootAddress;
	public static String UserName;
	public static String UserTitleStr;

	public static ArrayList<MenuModel> SignedInMenu;
	public static ArrayList<MenuModel> SignedOutMenu;
	public static OnItemClickListener menuOnItemClickListener;
	public static View.OnClickListener imgProfileClick;

	public static SQLiteDatabase mDatabaseRead;
	public static SQLiteDatabase mDatabaseWrite;

	public static class HandleDate {
		public static int[] PersianToGregorian(int year, int month, int day) {
			int[] Gregorian = new int[3];
			PersianCalendar pCalendar = new PersianCalendar();
			pCalendar.PersianToGregorian(year, month, day);
			Gregorian[0] = pCalendar.getYear();
			Gregorian[1] = pCalendar.getMonth();
			Gregorian[2] = pCalendar.getDay();

			return Gregorian;
		}

		public static int[] PersianToGregorian(String year, String month, String day) {
			int[] Gregorian = new int[3];
			PersianCalendar pCalendar = new PersianCalendar();
			int mYear = Integer.valueOf(year);
			int mMonth = Integer.valueOf(month);
			int mDay = Integer.valueOf(day);
			pCalendar.PersianToGregorian(mYear, mMonth, mDay);

			Gregorian[0] = pCalendar.getYear();
			Gregorian[1] = pCalendar.getMonth();
			Gregorian[2] = pCalendar.getDay();

			return Gregorian;
		}

		public static String CalcAge(String BirthDate) {
			Calendar calendar = Calendar.getInstance();
			int mNow = calendar.get(Calendar.YEAR);
			String mYear = GetYearByDate(BirthDate);
			String mMonth = GetMonthByDate(BirthDate);
			String mday = GetDayByDate(BirthDate);
			int bYear = PersianToGregorian(mYear, mMonth, mday)[0];

			return String.valueOf(mNow - bYear);
		}

		public static String PersianToGregorianDate(int year, int month, int day) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.PersianToGregorianDate(year, month, day);
		}

		public static String PersianSimpleDate(int aDate) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.PersianSimpleDate(aDate);
		}

		public static String PersianSimpleDate(int year, int month, int day) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.PersianSimpleDate(year, month, day);
		}

		public static String PersianFullDate(int aDate) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.PersianFullDate(aDate);
		}

		public static String PersianFullDateDesc(int aDate) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.PersianFullDateDesc(aDate);
		}

		public static String PersianFullDateDesc(int year, int month, int day) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.PersianFullDateDesc(year, month, day);
		}

		public static String PersianToGregorianFullDate(int year, int month, int day, boolean PersianMonthName) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.PersianToGregorianFullDate(year, month, day, PersianMonthName);
		}

		public static String AddDay(int year, int month, int day, int Adddays) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.AddDay(year, month, day, Adddays);
		}

		public static String GetDayByDate(String ActiveDate) {
			return ActiveDate.substring(8);
		}

		public static String GetMonthByDate(String ActiveDate) {
			return ActiveDate.substring(5, 7);
		}

		public static String GetYearByDate(String ActiveDate) {
			return ActiveDate.substring(0, 4);
		}

		public static int GetDayInt() {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.GetTodayInt();
		}

		public static int GetDayInt(int year, int month, int day) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.GetDayInt(year, month, day);
		}

		public static boolean IsLeapYear(int y) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.IsLeapYear(y);
		}

		public static String getWeekDayNamebyDay(int year, int month, int day) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.getWeekDayNamebyDay(year, month, day);
		}

		public static int DayOfWeek(int year, int month, int day) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.DayOfWeek(year, month, day);
		}

		public static String GetMonthName(int monthcode) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.GetMonthName(monthcode);
		}

		public static int GetMonthCode(String MonthName) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.GetMonthCode(MonthName);
		}

		public static String GetGMonthName(int monthcode) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.GetGMonthName(monthcode);
		}

		public static String GetGMonthNamePer(int monthcode) {
			PersianCalendar pCalendar = new PersianCalendar();
			return pCalendar.GetGMonthNamePer(monthcode);
		}

	}

	public static class HandleDataBase {
		public static JSONArray fromJSON(String json){
			JSONArray jsonArray = null;
			try {
				jsonArray = new JSONArray(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return jsonArray;
		}

		public static String JSONStringCorrector(String JSON){
			JSON = JSON.replace("\\\"", "\"");
			JSON = JSON.replace("\\r\\n", "");
			JSON = JSON.replace("\"[", "[");
			JSON = JSON.replace("]\"", "]");
			JSON = JSON.replace("\":\"{\"", "\":{\"");
			JSON = JSON.replace("}\",\"", "},\"");
			JSON = JSON.replace("\\/", "/");
			JSON = JSON.replace("}\"}", "}}");

			return JSON;
		}

		public static String ReadJSON_NotAsync(String ServiceName, String JSON){
			String result = "";
			try {
				JSONObject mJSON;
				if (!JSON.equals(""))
					mJSON = new JSONObject(JSON);
				else
					mJSON = new JSONObject();

				DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
				HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),30000);
				HttpPost httpPost   = new HttpPost(HttpRootAddress + "Scripts/Android/" + ServiceName + ".php");

				if (!JSON.equals("")) {
					mJSON.put("SID", BuildConfig.TS);
					mJSON.put("DID", Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID));
					StringEntity requestEntity = new StringEntity(mJSON.toString(), "UTF-8");
					httpPost.setEntity(requestEntity);
				}

				Log.d("SentJSON:", mJSON.toString());

				HttpResponse response = httpClient.execute(httpPost);
				InputStream inputStream = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
				StringBuilder sb = new StringBuilder();

				String line;
				while ((line = reader.readLine()) != null)
					sb.append(line).append("\n");

				Log.d("JSONResponce:", sb.toString());
				if (!sb.toString().contains("Parameter Error"))
					result = sb.toString();
			} catch (Exception ex) {
				Log.e("JSONError", ex.toString());
			}

			return result;
		}

		public static JSONArray ReadJSONData(String mSqlTxt) {
			JSONArray mJSONArray = null;
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
				HttpPost httppost = new HttpPost(HttpRootAddress + "Scripts/Select.php");
				List<NameValuePair> nameValuePairs = new ArrayList<>(1);
				nameValuePairs.add(new BasicNameValuePair("Query", HandleString.SimpleHash(mSqlTxt,1)));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
				InputStream inputStream = null;

				try {
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity entity = response.getEntity();
					inputStream = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
					StringBuilder sb = new StringBuilder();

					String line = null;
					while ((line = reader.readLine()) != null)
						sb.append(line + "\n");

					if (!sb.toString().contains("Parameter Error"))
						mJSONArray = new JSONArray(sb.toString());

				} catch (Exception e) {
					Log.e("Error", e.toString());
				} finally {
					try {
						if (inputStream != null) inputStream.close();
					} catch (Exception ignored) {}
				}
			} catch (Exception ignored) {}

            return mJSONArray;
	}

		public static JSONArray ReadJSONData(String wsName, String WhereClouse) {
		JSONArray mJSONArray = null;
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
			HttpPost httppost = new HttpPost(HttpRootAddress + "Scripts/" + wsName + ".php");
			List<NameValuePair> nameValuePairs = new ArrayList<>(1);
			nameValuePairs.add(new BasicNameValuePair("WHERE", HandleString.SimpleHash(WhereClouse,1)));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			InputStream inputStream = null;

			try {
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				inputStream = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
				StringBuilder sb = new StringBuilder();

				String line = null;
				while ((line = reader.readLine()) != null)
					sb.append(line + "\n");

				if (!sb.toString().contains("Parameter Error"))
					mJSONArray = new JSONArray(sb.toString());

			} catch (Exception e) {
				Log.e("Error", e.toString());
			} finally {
				try {
					if (inputStream != null) inputStream.close();
				} catch (Exception ignored) {}
			}
		} catch (Exception ignored) {}
            return mJSONArray;
}

		public static JSONArray ReadJSONData(String mSqlTxt, boolean ShowMessage) {
		JSONArray mJSONArray = null;
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
			HttpPost httppost = new HttpPost(HttpRootAddress + "Scripts/Select.php");
			List<NameValuePair> nameValuePairs = new ArrayList<>(1);
			nameValuePairs.add(new BasicNameValuePair("Query", HandleString.SimpleHash(mSqlTxt, 1)));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			InputStream inputStream = null;

			try {
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				inputStream = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
				StringBuilder sb = new StringBuilder();

				String line = null;
				while ((line = reader.readLine()) != null)
					sb.append(line + "\n");

				if (!sb.toString().contains("Parameter Error"))
					mJSONArray = new JSONArray(sb.toString());

			} catch (Exception e) {
				Log.e("mSqlTxt", mSqlTxt);
			} finally {
				try {
					if (inputStream != null) inputStream.close();
				} catch (Exception ignored) {}
			}
		} catch (Exception ignored) {
		}

		return mJSONArray;
	}

		public static void ExportDB(String db_name) {
			HandleDisk.SetLocalPath();
			FileChannel source = null;
			FileChannel destination = null;
			String currentDBPath = LocalPath + "/databases/" + db_name;
			String backupDBPath = Environment.getExternalStorageDirectory() + "/fitnessica.db3";

			File currentDB = new File(currentDBPath);
			File backupDB = new File(backupDBPath);
			try {
				source = new FileInputStream(currentDB).getChannel();
				destination = new FileOutputStream(backupDB).getChannel();
				destination.transferFrom(source, 0, source.size());
				source.close();
				destination.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static void ImportDB(String db_name) {
			HandleDisk.SetLocalPath();
			FileChannel source = null;
			FileChannel destination = null;
			String currentDBPath = LocalPath + "/databases/" + db_name;
			String backupDBPath = Environment.getExternalStorageDirectory() + "/fitnessica.db3";

			File currentDB = new File(currentDBPath);
			File backupDB = new File(backupDBPath);

			try {
				source = new FileInputStream(backupDB).getChannel();
				destination = new FileOutputStream(currentDB).getChannel();
				destination.transferFrom(source, 0, source.size());
				source.close();
				destination.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static void ImportDBFromAssets(String db_name) {
			HandleDisk.SetLocalPath();
			String currentDBPath = LocalPath + "/databases/" + db_name;
			try {
				InputStream source = mContext.getAssets().open(db_name);
				OutputStream destination = new FileOutputStream(currentDBPath, false);

				byte[] buffer = new byte[1024];
				int length;
				while ((length = source.read(buffer)) > 0) {
					destination.write(buffer, 0, length);
				}

				destination.flush();
				source.close();
				destination.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static Cursor ReadData(SQLiteDatabase database, String sqltxt) {
			Cursor cursor;
			try {
				cursor = database.rawQuery(sqltxt, null);
				cursor.moveToFirst();
			} catch (Exception ex) {
				HandleDisk.appendLog(ex.toString());
				cursor = null;
			}
			return cursor;
		}

		public static boolean InsertData(SQLiteDatabase database, String sqltxt) {
			try {
				database.execSQL(sqltxt);
				database.close();
				return true;
			} catch (Exception ex) {
				Log.e("SQLite INSERT ERROR", " <<" + sqltxt + ">> " + ex.toString());
				return false;
			}
		}

		public static boolean CheckConnection(final String mURL) {
			HandleApplication.DiscareRunOnMainThread();
			try {
				InetAddress ipAddr = InetAddress.getByName(mURL);
				return !ipAddr.equals("");
			} catch (Exception e) {
				return false;
			}
		}

		public static boolean MultiUpdateDataJSON_NotAsync(String sqlStmt) {
			if (!HandleDataBase.CheckConnection("www.google.com")) {
				Toast.makeText(mContext, mContext.getString(R.string.messNoInternetConnection), Toast.LENGTH_SHORT).show();
				return false;
			} else {
				String[] mQueries = sqlStmt.split(";");
				try {
					DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
					HttpPost httppost = new HttpPost(HttpRootAddress + "/Scripts/MultiInsert.php");
					List<NameValuePair> nameValuePairs = new ArrayList<>(mQueries.length);
					for (int i = 1; i <= mQueries.length; i++)
						nameValuePairs.add(new BasicNameValuePair("sqlStmt" + String.valueOf(i), mQueries[i - 1]));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
					httpclient.execute(httppost);
					return true;
				} catch (Exception ex) {
					return false;
				}
			}
		}

		public static boolean UpdateDataJSON_NotAsync(String mSqlTxt) {
			Log.d("Insert JSON", mSqlTxt);
			if (!HandleDataBase.CheckConnection("www.google.com")) {
				Toast.makeText(mContext, mContext.getString(R.string.messNoInternetConnection), Toast.LENGTH_SHORT).show();
				return false;
			} else {
				try {
					mSqlTxt = mSqlTxt.replaceAll("\\s+", " ");
					DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
					HttpPost httppost = new HttpPost(HttpRootAddress + "Scripts/Insert.php");
					List<NameValuePair> nameValuePairs = new ArrayList<>(1);
					nameValuePairs.add(new BasicNameValuePair("Query", mSqlTxt));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
					httpclient.execute(httppost);
					return true;
				} catch (Exception ex) {
					Log.e("JSONError", ex.toString());
					return false;
				}
			}
		}

		public static boolean UpdateDataJSON_NotAsync(String mSqlTxt, boolean ShowMessage) {
			if (!HandleDataBase.CheckConnection("www.google.com")) {
				if (ShowMessage)
					Toast.makeText(mContext, mContext.getString(R.string.messNoInternetConnection), Toast.LENGTH_SHORT).show();
				return false;
			} else {
				try {
					DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
					HttpPost httppost = new HttpPost(HttpRootAddress + "Scripts/Insert.php");
					List<NameValuePair> nameValuePairs = new ArrayList<>(1);
					nameValuePairs.add(new BasicNameValuePair("Query", mSqlTxt));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
					httpclient.execute(httppost);
					return true;
				} catch (Exception ex) {
					return false;
				}
			}
		}

		public static int GetIDJSON(String TableName, String IDFieldName, String Condition) {
			HandleApplication.DiscareRunOnMainThread();
			try {
				JSONArray mJSONArray = PubProc.HandleDataBase.ReadJSONData("SELECT " + IDFieldName + " AS ID FROM " + TableName + " WHERE " + Condition);
				JSONObject rs = mJSONArray.getJSONObject(0);
				return rs.getInt("ID");
			} catch (Exception ex) {
				return 0;
			}
		}

		public static JSONObject GetOnRowJSON(String TableName, String FieldNames, String Condition) {
			try {
				JSONArray mJSONArray = PubProc.HandleDataBase.ReadJSONData("SELECT " + FieldNames + " FROM " + TableName + " WHERE " + Condition);
				JSONObject rs = mJSONArray.getJSONObject(0);
				return rs;
			} catch (Exception ex) {
				return null;
			}
		}

		public static JSONObject GetOnRowJSON_NotAsync(String TableName, String FieldNames, String Condition) {
			HandleApplication.DiscareRunOnMainThread();
			try {
				JSONArray mJSONArray = PubProc.HandleDataBase.ReadJSONData("SELECT " + FieldNames + " FROM " + TableName + " WHERE " + Condition);
				JSONObject rs = mJSONArray.getJSONObject(0);
				return rs;
			} catch (Exception ex) {
				return null;
			}
		}

		public static JSONObject GetOnRowJSON_NotAsync(String wsName, String WhereClouse) {
			HandleApplication.DiscareRunOnMainThread();
			try {
				JSONArray mJSONArray = PubProc.HandleDataBase.ReadJSONData(wsName, WhereClouse);
				JSONObject rs = mJSONArray.getJSONObject(0);
				return rs;
			} catch (Exception ex) {
				return null;
			}
		}

		public static JSONObject GetOnRowJSON_NotAsync(String Qurty, boolean ShowMessage) {
			HandleApplication.DiscareRunOnMainThread();
			try {
				JSONArray mJSONArray = PubProc.HandleDataBase.ReadJSONData(Qurty, ShowMessage);
				JSONObject rs = mJSONArray.getJSONObject(0);
				return rs;
			} catch (Exception ex) {
				return null;
			}
		}

		public static JSONObject GetOnRowJSON_NotAsync(String Qurty) {
			HandleApplication.DiscareRunOnMainThread();
			try {
				JSONArray mJSONArray = PubProc.HandleDataBase.ReadJSONData(Qurty);
				JSONObject rs = mJSONArray.getJSONObject(0);
				return rs;
			} catch (Exception ex) {
				return null;
			}
		}

		public static boolean UpdateData(SQLiteDatabase database, String sqltxt) {
			try {
				database.execSQL(sqltxt);
				database.close();
				return true;
			} catch (Exception ex) {
				return false;
			}
		}

		public static String GetMaxValue(SQLiteDatabase database, String TableName, String FieldName) {
			Cursor cur = ReadData(database, "SELECT MAX(" + FieldName + ") FROM " + TableName);
			cur.moveToFirst();
			return cur.getString(0);
		}

		public static String GetMaxValue(SQLiteDatabase database, String TableName, String FieldName, String Condition) {
			Cursor cur = ReadData(database, "SELECT MAX(" + FieldName + ") FROM " + TableName + " WHERE " + Condition);
			return cur.getString(0);
		}

		public static boolean isCurEmpty(Cursor cur) {
			return !(cur.moveToFirst()) || cur.getCount() == 0;
		}
	}

	public static class HandleViewAndFontSize {
		public static void overrideFontSize(TextView tv) {
			float lastsize = tv.getTextSize() / DisplayDensityDpi;
			tv.setTextSize(lastsize);
		}

		public static void overrideViewSize(View v, int widthsize, int heightsize) {
			RelativeLayout.LayoutParams params = ((RelativeLayout.LayoutParams) v.getLayoutParams());
			int newXSize = (int) (xdpi * widthsize * 1.5 / 480);
			int newYSize = (int) (ydpi * heightsize * 1.5 / 782);
			if (widthsize == heightsize && newXSize != newYSize) {
				if (newXSize < newYSize)
					newYSize = newXSize;
				else newXSize = newYSize;
			}

			if (newYSize < 50) newYSize = 50;

			params.width = newXSize;
			params.height = newYSize;
			v.setLayoutParams(params);
		}

		public static void overrideViewSize(View v, int widthsize, int heightsize, int MinHeight) {
			RelativeLayout.LayoutParams params = ((RelativeLayout.LayoutParams) v.getLayoutParams());
			int newXSize = (int) (xdpi * widthsize * 1.5 / 480);
			int newYSize = (int) (ydpi * heightsize * 1.5 / 782);
			if (widthsize == heightsize && newXSize != newYSize) {
				if (newXSize < newYSize)

					newYSize = newXSize;
				else newXSize = newYSize;
			}

			if (newYSize < MinHeight) newYSize = MinHeight;
			params.width = newXSize;
			params.height = newYSize;
			v.setLayoutParams(params);
		}

		public static void overrideViewSizeLinear(View v, int widthsize, int heightsize) {
			LinearLayout.LayoutParams params = ((LinearLayout.LayoutParams) v.getLayoutParams());
			int newXSize = (int) (xdpi * widthsize * 1.5 / 480);
			int newYSize = (int) (ydpi * heightsize * 1.5 / 782);
			if (widthsize == heightsize && newXSize != newYSize) {
				if (newXSize < newYSize)

					newYSize = newXSize;
				else newXSize = newYSize;
			}

			if (newYSize < 50) newYSize = 50;

			params.width = newXSize;
			params.height = newYSize;
			v.setLayoutParams(params);
		}

		public static void overrideFonts(final Context context, final View v) {
			try {
				if (v instanceof ViewGroup) {
					ViewGroup vg = (ViewGroup) v;
					for (int i = 0; i < vg.getChildCount(); i++) {
						View child = vg.getChildAt(i);
						overrideFonts(context, child);
					}
				} else if (v instanceof TextView) {
					((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "BTrafcBd.ttf"));
				}
			} catch (Exception e) {
			}
		}

		public static void overrideFonts(final Context context, final View v, String fontName) {
			try {
				if (v instanceof ViewGroup) {
					ViewGroup vg = (ViewGroup) v;
					for (int i = 0; i < vg.getChildCount(); i++) {
						View child = vg.getChildAt(i);
						overrideFonts(context, child, fontName);
					}
				} else if (v instanceof TextView) {
					((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), fontName));
				}
			} catch (Exception e) {
			}
		}

		public static void replaceFont(String staticTypefaceFieldName,
									   final Typeface newTypeface) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				Map<String, Typeface> newMap = new HashMap<>();
				newMap.put("sans-serif", newTypeface);
				try {
					final Field staticField = Typeface.class
							.getDeclaredField("sSystemFontMap");
					staticField.setAccessible(true);
					staticField.set(null, newMap);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else {
				try {
					final Field staticField = Typeface.class
							.getDeclaredField(staticTypefaceFieldName);
					staticField.setAccessible(true);
					staticField.set(null, newTypeface);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static class HandleMainMenu {

		public static void CreateDrawerMenu(final DrawerLayout mDrawerLayout,
											ListView mDrawerList, View mCustomView, final ActionBar mActionBar)
		{
			HandleViewAndFontSize.overrideFonts(mContext, mDrawerList);
			mDrawerList.setOnItemClickListener(menuOnItemClickListener);

			View rowView = mInflater.inflate(R.layout.profile_image_item, null);

			txtTitle = rowView.findViewById(R.id.txtTitle);
			if (UserID == 0){
				adapter = new MenuAdapter(mContext, SignedOutMenu);
				mDrawerList.addHeaderView(rowView);
				mDrawerList.setAdapter(adapter);
				txtTitle.setText(UserName);
			}else {
				adapter = new MenuAdapter(mContext, SignedInMenu);
				mDrawerList.addHeaderView(rowView);
				mDrawerList.setAdapter(adapter);
				txtTitle.setText(UserTitleStr);
			}

			imgProfile = rowView.findViewById(R.id.imgProfile);
			HandleViewAndFontSize.overrideViewSizeLinear(imgProfile, 100, 100);
			Picasso.with(mContext).load(UserImage).into(imgProfile);
			HandleViewAndFontSize.overrideFonts(mContext, rowView);

			imgProfile.setOnClickListener(imgProfileClick);
		}

	}

	public static class HandleString {
		public static String SimpleHash(String AStr, int Mode){
			ApiCrypter apiCrypter = new ApiCrypter();
			try {
				return ApiCrypter.bytesToHex(apiCrypter.encrypt(AStr));
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}

		public static String ReplacePersianToDigit(String str) {
			str = str.replaceAll("[,.٬]", "");
			str = PersianNumToEnglish(str);
			return str;
		}

		public static String ClearSigns(String str) {
			str = str.replaceAll("[,.٬]", "");
			str = PersianNumToEnglish(str);
			return str;
		}

		public static String PersianNumStrToEnglish(String number) {
			number = number.replace(",", "").replace("٬", "").trim();

			final String arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9";
			int j = 0;
			char[] chars = new char[number.length()];
			for (int i = 0; i < number.length(); i++)
				chars[i] = ' ';

			for (int i = 0; i < number.length(); i++) {
				char ch = number.charAt(i);
				if (ch >= 0x0660 && ch <= 0x0669)
					ch -= 0x0660 - '0';
				else if (ch >= 0x06f0 && ch <= 0x06F9)
					ch -= 0x06f0 - '0';

				chars[j++] = ch;
			}
			return new String(chars).trim();
		}

		public static String PersianNumToEnglish(String number) {
			number = number.replace(",", "").replace("٬", "").trim();

			final String arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9";
			int j = 0;
			char[] chars = new char[number.length()];
			for (int i = 0; i < number.length(); i++)
				chars[i] = ' ';

			for (int i = 0; i < number.length(); i++) {
				char ch = number.charAt(i);
				if (ch >= 0x0660 && ch <= 0x0669)
					ch -= 0x0660 - '0';
				else if (ch >= 0x06f0 && ch <= 0x06F9)
					ch -= 0x06f0 - '0';

				if (Character.isDigit(ch))
					chars[j++] = ch;
			}
			return new String(chars).trim();
		}

		public static String SetThousandSeparator(String Value) {
			String cleanString = ReplacePersianToDigit(Value);
			if (cleanString.length() > 0) {
				double parsed = Double.parseDouble(cleanString);
				NumberFormat formatter = NumberFormat.getCurrencyInstance();
				DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
				decimalFormatSymbols.setCurrencySymbol("");
				((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
				formatter.setMaximumFractionDigits(0);
				return formatter.format(parsed);
			} else {
				return cleanString;
			}
		}



		public static String QoutedStr(String txt) {
			return "'" + txt + "'";
		}

		public static String md5(String string) {
			byte[] hash;

			try {
				hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("Huh, MD5 should be supported?", e);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Huh, UTF-8 should be supported?", e);
			}

			StringBuilder hex = new StringBuilder(hash.length * 2);
			for (byte b : hash) {
				if ((b & 0xFF) < 0x10) {
					hex.append("0");
				}
				hex.append(Integer.toHexString(b & 0xFF));
			}
			return hex.toString();
		}

		public static String HashString(String Str) {
			return BCrypt.hashpw(Str, BCrypt.gensalt()).replace("$2a$10$", "$2y$10$");
		}

		public static boolean CheckHash(String Str, String HashText) {
			String HashPass = HashText.replace("$2y$10$", "$2a$10$");
			return BCrypt.checkpw(Str, HashPass);
		}

		public static String ISNULL(String Value, String rValue) {
			if (Value == null)
				return rValue;


			else {
				if (Value.equals(""))
					return rValue;
				else
					return Value;
			}
		}

		public static void AddCurrencyTextWatcher(final EditText v) {
			v.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					if (!s.toString().equals(current)) {
						v.removeTextChangedListener(this);

						String cleanString = HandleString.ReplacePersianToDigit(s.toString());
						if (cleanString.length() > 0) {
							double parsed = Double.parseDouble(cleanString);
							NumberFormat formatter = NumberFormat.getCurrencyInstance();
							DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
							decimalFormatSymbols.setCurrencySymbol("");
							((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
							formatter.setMaximumFractionDigits(0);
							current = formatter.format(parsed);
						} else {
							current = cleanString;
						}
						v.setText(current);
						v.setSelection(current.length());
						v.addTextChangedListener(this);
					}
				}

				@Override
				public void afterTextChanged(Editable s) {

				}
			});
		}

		public static void AddCurrencyTextWatcher(final EditText v, final Runnable AfterTextChanged) {
			v.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					if (!s.toString().equals(current)) {
						v.removeTextChangedListener(this);

						String cleanString = HandleString.ReplacePersianToDigit(s.toString());
						if (cleanString.length() > 0) {
							double parsed = Double.parseDouble(cleanString);
							NumberFormat formatter = NumberFormat.getCurrencyInstance();
							DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
							decimalFormatSymbols.setCurrencySymbol("");
							((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
							formatter.setMaximumFractionDigits(0);
							current = formatter.format(parsed);
						} else {
							current = cleanString;
						}
						v.setText(current);
						v.setSelection(current.length());
						v.addTextChangedListener(this);
					}
				}

				@Override
				public void afterTextChanged(Editable s) {
					AfterTextChanged.run();
				}
			});
		}
	}

	public static class HandleDisk {
		public static void RunAService(final String ServiceName, final String[][] params) {
			new RunAsyncTask(new Runnable() {
				@Override
				public void run() {
					try {
						DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
						HttpPost httppost = new HttpPost(HttpRootAddress + "WebService/" + ServiceName + ".php");
						List<NameValuePair> nameValuePairs = new ArrayList<>(params.length);
						for (int i = 0; i < params.length; i++)
							nameValuePairs.add(new BasicNameValuePair(params[i][0], params[i][1]));
						httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
						httpclient.execute(httppost);
					} catch (Exception e) {
						Log.e("Error", e.toString());
					}
				}
			}).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}

		public static void RunAService(final String ServiceName, final String[][] params, Runnable AfterRun) {
			new RunAsyncTask(new Runnable() {
				@SuppressLint("NewApi")
				@Override
				public void run() {
					try {
						DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
						HttpPost httppost = new HttpPost(HttpRootAddress + "WebService/" + ServiceName + ".php");
						List<NameValuePair> nameValuePairs = new ArrayList<>(params.length);
						for (int i = 0; i < params.length; i++)
							nameValuePairs.add(new BasicNameValuePair(params[i][0], params[i][1]));
						httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
						httpclient.execute(httppost);

					} catch (Exception e) {
						Log.e("Error", e.toString());
					}
				}
			}, AfterRun).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}

		public static void appendLog(String text) {
			File logFile = new File(Environment.getExternalStorageDirectory(), "log.txt");
			if (!logFile.exists()) {
				try {
					logFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}


			try {
				BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
				buf.append(text);
				buf.newLine();
				buf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static void SetLocalPath() {
			PackageManager m = mContext.getPackageManager();
			String s = mContext.getPackageName();
			try {
				PackageInfo p = m.getPackageInfo(s, 0);
				LocalPath = p.applicationInfo.dataDir;
			} catch (PackageManager.NameNotFoundException e) {
			}
		}

		public static String SaveBitmapToFile(Bitmap bmp, String FileName) {
			OutputStream fOut = null;
			try {
				if (LocalPath == null)
					SetLocalPath();
				PicURL = FileName + ".jpg";
				File to = new File(LocalPath, FileName + ".jpg");
				PicUri = to.getPath();
				fOut = new FileOutputStream(to);
			} catch (Exception e) {
				Toast.makeText(mContext, "Error occured. Please try again later.",
						Toast.LENGTH_SHORT).show();
			}

			try {
				bmp.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
				fOut.flush();
				fOut.close();
			} catch (Exception e) {
			}

			return PicUri;
		}

		public static Bitmap LoadBitmapFromFile(String FileName) {
			File imgFile = new File(FileName);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
			options.inPreferredConfig = Config.RGB_565;
			options.inSampleSize = 6;
			options.inDither = true;

			return BitmapFactory.decodeFile(imgFile.getPath(), options);
		}

		public static Bitmap LoadImageFromURL(URL url) throws IOException {
			Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			return bmp;
		}
	}

	public static class HandleTime {
		public static String GetofflineTime() {
			long time;
			Calendar calendar = Calendar.getInstance();

			if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}

			String dateFromNtpServer = "";
			time = System.currentTimeMillis();
			calendar.setTimeInMillis(time);
			calendar.getTime();
			dateFromNtpServer = String.valueOf(time);

			return dateFromNtpServer;
		}

		public static String CompaireTime(String ActTime) {
			if (ActTime.equals(""))
				return "";

			long now = Long.valueOf(GetofflineTime());
			long acttime = Long.valueOf(ActTime);

			long dis = now - acttime;
			String ReturnValue = "";

			if (dis < 1000)
				ReturnValue = mContext.getResources().getString(R.string.JustNow);

			if (dis >= 1000 && dis < 60000)
				ReturnValue = String.valueOf((int) dis / 1000)
						+ mContext.getResources().getString(R.string.SecondsAgo);

			if (dis >= 60000 && dis < 3600000)
				ReturnValue = String.valueOf((int) dis / 60000)
						+ mContext.getResources().getString(R.string.MinsAgo);

			if (dis >= 3600000 && dis < 84600000)
				ReturnValue = String.valueOf((int) dis / 3600000)
						+ mContext.getResources().getString(R.string.HrsAgo);

			if (dis >= 84600000)
				ReturnValue = String.valueOf((int) dis / 84600000)
						+ mContext.getResources().getString(R.string.DaysAgo);

			return ReturnValue;
		}
	}

	public static class HandleNotification {
		public static void SendNotification(Activity a, String Meessage, String Title, Activity DistinationActivity, Runnable AfterRun) {
			try {
				HandleDisk.appendLog("Notification Sending");
				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(a)
						.setSmallIcon(R.mipmap.notificationicon)
						.setContentTitle(Title)
						.setContentText(Meessage)
						.setAutoCancel(true)
						.setDefaults(Notification.DEFAULT_ALL);

				Intent myintent = new Intent(a, DistinationActivity.getClass());
				myintent.putExtra("ShowNotification", "True");
				PendingIntent pi = PendingIntent.getActivity(a, 0, myintent, Intent.FLAG_ACTIVITY_NEW_TASK);
				mBuilder.setContentIntent(pi);

				NotificationManager mNotificationManager =
						(NotificationManager) a.getSystemService(Context.NOTIFICATION_SERVICE);

				mNotificationManager.notify(0, mBuilder.build());
				HandleDisk.appendLog("Notification Sent");
				if (AfterRun != null)
					AfterRun.run();
			} catch (Exception ex) {
				HandleDisk.appendLog(ex.toString());
			}
		}

		public static void SendNotification(Service a, int id, String Meessage, String Title, Activity DistinationActivity, Runnable AfterRun) {
			try {
				HandleDisk.appendLog("Notification Sending");
				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(a)
						.setSmallIcon(R.mipmap.notificationicon)
						.setContentTitle(Title)
						.setContentText(Meessage)
						.setAutoCancel(true)
						.setLights(mContext.getResources().getColor(R.color.primary), 100, 100)
						.setVibrate(new long[]{1000, 1000})
						.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

				Intent myintent = new Intent(a, DistinationActivity.getClass());
				myintent.putExtra("ShowNotification", "True");
				PendingIntent pi = PendingIntent.getActivity(a, 0, myintent, Intent.FLAG_ACTIVITY_NEW_TASK);
				mBuilder.setContentIntent(pi);

				NotificationManager mNotificationManager =
						(NotificationManager) a.getSystemService(Context.NOTIFICATION_SERVICE);

				mNotificationManager.notify(id, mBuilder.build());
				HandleDisk.appendLog("NotifiSecation Sent");

				if (AfterRun != null)
					AfterRun.run();

			} catch (Exception ex) {
				HandleDisk.appendLog(ex.toString());
			}
		}
	}

	public static class HandleWebServices {
		public static void SendVerificationCode(final String PhoneNumber, final String RCode, final Runnable mAfterRun) {
			new RunAsyncTask(new Runnable() {
				@Override
				public void run() {
					if (!HandleDataBase.CheckConnection("www.google.com")) {
						Toast.makeText(mContext, mContext.getString(R.string.messNoInternetConnection), Toast.LENGTH_SHORT).show();
					} else {
						try {
							DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
							HttpPost httppost = new HttpPost(HttpRootAddress + "WebService/verify_number.php");
							List<NameValuePair> nameValuePairs = new ArrayList<>(2);
							nameValuePairs.add(new BasicNameValuePair("PHONE_NUABMER", PhoneNumber));
							nameValuePairs.add(new BasicNameValuePair("RCODE", RCode));
							httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

							httpclient.execute(httppost);
						} catch (Exception ignored) {
						}
					}
				}
			}, new Runnable() {
				@Override
				public void run() {
					if (mAfterRun != null)
						mAfterRun.run();
				}
			}).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
	}

	public static class HandleApplication {
		public static String CreateRandomNumber(){
			Random r = new Random();
			int randnum = r.nextInt(99999 - 10000) + 10000;
			return String.valueOf(randnum);
		}

		public static void verifyPermissions(final Activity activity) {
			// Check if we have write permission
			int permission = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
			int permission3 = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION);

			if (permission + permission3 != PackageManager.PERMISSION_GRANTED) {

				Snackbar.make(activity.findViewById(android.R.id.content),
						"لطفااجازه دسترسی به حافظه و موقعیت را بدهید",
						Snackbar.LENGTH_INDEFINITE).setAction("تایید دسترسی",
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								ActivityCompat.requestPermissions(activity,
										new String[]{android.Manifest.permission
												.WRITE_EXTERNAL_STORAGE,
												android.Manifest.permission.ACCESS_FINE_LOCATION},
										MY_PERMISSIONS);
							}
						}).show();
			}
		}

		public static boolean checkIfAlreadyhavePermission(final Activity activity) {
			int permission = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
			int permission3 = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION);

			if (permission == PackageManager.PERMISSION_GRANTED
					&& permission3 == PackageManager.PERMISSION_GRANTED) {
				return true;
			} else {
				return false;
			}
		}

		public static Snackbar makeText(Context context, String message, int duration) {
			Activity activity = (Activity) context;
			View layout;
			Snackbar snackbar = Snackbar
					.make(activity.findViewById(android.R.id.content), message, duration);
			layout = snackbar.getView();
			layout.setBackgroundColor(context.getResources().getColor(R.color.primary));
			android.widget.TextView text = layout.findViewById(android.support.design.R.id.snackbar_text);
			text.setGravity(Gravity.RIGHT);
			text.setTextColor(context.getResources().getColor(R.color.icons));
			HandleViewAndFontSize.overrideFonts(mContext, text);

			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) text.getLayoutParams();
			params.setLayoutDirection(ViewGroup.LayoutParams.MATCH_PARENT);
			text.setLayoutParams(params);

			return snackbar;

		}

		public static void ToastMessage(String message) {
			View layout = mInflater.inflate(R.layout.toast,
					(ViewGroup) mActivity.findViewById(R.id.toast_layout_root));

			TextView text = layout.findViewById(R.id.text);
			text.setText(message);

			Toast toast = new Toast(mContext);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setView(layout);
			HandleViewAndFontSize.overrideFonts(mContext, layout);
			toast.show();
		}

		public static void handleUncaughtException(Activity a, Thread thread, Throwable e) {
			e.printStackTrace();

			Intent intent = new Intent();
			intent.setAction("com.dpa_me.restook.SEND_LOG");
			intent.putExtra("Error", e.toString());
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			a.startActivity(intent);

			System.exit(1);
		}

		public static void RunPusheNotification(String ReciverID, String ReciverField) {
			if (HandleDataBase.CheckConnection("www.google.com")) {
				try {
					DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
					HttpPost httppost = new HttpPost(HttpRootAddress + "WebService/Pushe.php");
					List<NameValuePair> nameValuePairs = new ArrayList<>(1);
					nameValuePairs.add(new BasicNameValuePair(ReciverField, ReciverID));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
					httpclient.execute(httppost);
				} catch (Exception ignored) {
				}
			}
		}

		public static void DiscareRunOnMainThread() {
			if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}
		}

		public static void CloseQuestionDialog() {
			if (mQuestionDialog != null)
				if (mQuestionDialog.isShowing())
					mQuestionDialog.dismiss();
		}

		public static void CloseMessageDialog() {
			if (mShowMessageDialog != null)
				if (mShowMessageDialog.isShowing())
					mShowMessageDialog.dismiss();
		}

		public static void CloseProgressDialog() {
			try {
				if (mProgressDialog != null)
					if (mProgressDialog.isShowing())
						mProgressDialog.dismiss();
			} catch (Exception ex) {
				mProgressDialog.dismiss();
			}
		}

		public static boolean CheckInternetConnection(Context context) {
			if (!HandleDataBase.CheckConnection("www.google.com")) {
				HandleApplication.ToastMessage(context.getString(R.string.messNoInternetConnection));
				return false;
			} else {
				return true;
			}
		}

		public static void CloseSoftKeypad(View view) {
			InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}

		public static void OpenSoftKeypad(View view) {
			InputMethodManager inputMethodManager =
					(InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.toggleSoftInputFromWindow(
					view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
		}

		public static void ScrollListViewToBottom(final ListView mListView) {
			mListView.post(new Runnable() {
				@Override
				public void run() {
					mListView.setSelection(mListView.getAdapter().getCount() - 1);
				}
			});
		}

		public static void EmptyFragmentManager(Fragment a) {
			Field childFragmentManager = null;
			try {
				childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
				childFragmentManager.setAccessible(true);
				childFragmentManager.set(a, null);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		public static void SetViewPagerParams(SlidingTabLayout mSlidingTabLayout, ViewPager vpPager,
											  int[] PageTitle, int[] PageIcons, Fragment[] PageFragments) {
			SlidingTabPageAdapter adapterViewPager = new SlidingTabPageAdapter(mFragmentManager, mContext, PageTitle, PageIcons, PageFragments);

			vpPager.setAdapter(adapterViewPager);
			vpPager.setCurrentItem(PageTitle.length - 1);
			vpPager.setBackgroundColor(mContext.getResources().getColor(R.color.divider_light));

			mSlidingTabLayout.setDistributeEvenly(true);
			mSlidingTabLayout.setViewPager(vpPager);
			mSlidingTabLayout.setBackgroundColor(mContext.getResources().getColor(R.color.divider));

		}


		public static Context SetActivityParams(final AppCompatActivity activity, int ActivityLayout,
												boolean HasDrawerLayout, String Title) {
            /*Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler()
            {
                @Override
                public void uncaughtException (Thread thread, Throwable e)
                {
                    PubProc.HandleApplication.handleUncaughtException (activity, thread, e);
                }
            });*/

			activity.setContentView(ActivityLayout);
			activity.setTitle("");
			DisplayMetrics metrics = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

			mContext = activity.getApplicationContext();
			mInflater = (LayoutInflater) PubProc.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			xdpi = metrics.widthPixels;
			ydpi = metrics.heightPixels;
			DisplayDensityDpi = metrics.density;
			LastTitle = new ArrayList<String>();
			mFragmentManager = activity.getSupportFragmentManager();
			mMainFragmentManager = activity.getSupportFragmentManager();
			mActivity = activity;
			MainTypeFace = Typeface.createFromAsset(activity.getAssets(), "BTrafcBd.ttf");
			if (activity.getSupportActionBar() != null)
				activity.getSupportActionBar().hide();

			if (HasDrawerLayout) {
				mDrawerLayout = activity.findViewById(R.id.drawer_layout);
				mDrawerList = activity.findViewById(R.id.left_drawer);


				HandleMainMenu.CreateDrawerMenu(mDrawerLayout, mDrawerList, null, activity.getSupportActionBar());
			}

			ImageView BackBtn = activity.findViewById(R.id.BackBtn);
			if (BackBtn != null) {
				BackBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						activity.finish();
					}
				});
			}

			TextView mTitle = activity.findViewById(R.id.Title);
			if (mTitle != null) {
				mTitle.setText(Title);
			}
			return mContext;
		}



		public static Context SetActivityParams(final AppCompatActivity activity, int ActivityLayout,
												boolean HasDrawerLayout, String Title, final int EnterAnim, final int ExitAnim) {
			activity.setContentView(ActivityLayout);
			activity.setTitle("");
			DisplayMetrics metrics = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

			mContext = activity.getApplicationContext();
			mInflater = (LayoutInflater) PubProc.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			xdpi = metrics.widthPixels;
			ydpi = metrics.heightPixels;
			DisplayDensityDpi = metrics.density;
			LastTitle = new ArrayList<String>();
			mFragmentManager = activity.getSupportFragmentManager();
			mMainFragmentManager = activity.getSupportFragmentManager();
			mActivity = activity;
			MainTypeFace = Typeface.createFromAsset(mContext.getAssets(), "BTrafcBd.ttf");
			if (activity.getSupportActionBar() != null)
				activity.getSupportActionBar().hide();

			if (HasDrawerLayout) {
				mDrawerLayout = activity.findViewById(R.id.drawer_layout);
				mDrawerList = activity.findViewById(R.id.left_drawer);

				HandleMainMenu.CreateDrawerMenu(mDrawerLayout, mDrawerList, null, activity.getSupportActionBar());
			}

			ImageView BackBtn = activity.findViewById(R.id.BackBtn);
			if (BackBtn != null) {
				BackBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						activity.finish();
						activity.overridePendingTransition(EnterAnim, ExitAnim);
					}
				});
			}

			TextView mTitle = activity.findViewById(R.id.Title);
			if (mTitle != null) {
				mTitle.setText(Title);
				HandleViewAndFontSize.overrideFonts(mContext, mTitle);
			}

			return mContext;
		}

		public static View FindViewByName(String ViewName, View Parent) {
			int id = mContext.getResources().getIdentifier(ViewName, "id", mContext.getPackageName());
			return Parent.findViewById(id);
		}

		public static View FindViewByName(Context context, String ViewName, View Parent) {
			int id = context.getResources().getIdentifier(ViewName, "id", context.getPackageName());
			return Parent.findViewById(id);
		}

		public static void ShowProgressDialog(Activity activity) {
			if (mProgressDialog == null)
				mProgressDialog = new MyProgressDialog(activity, mContext.getResources().getString(R.string.messPleaseWait));

			if (!mProgressDialog.isShowing()) {
				mProgressDialog = new MyProgressDialog(activity, mContext.getResources().getString(R.string.messPleaseWait));
				mProgressDialog.setCancelable(false);
				mProgressDialog.show();
			}
		}

		public static void ShowProgressDialog(Activity activity, Runnable Execute) {
			mProgressDialog = new MyProgressDialog(activity, mContext.getResources().getString(R.string.messPleaseWait));
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();

			Execute.run();
		}

		public static void ShowProgressDialog(Activity activity, String Message, Runnable Execute) {
			mProgressDialog = new MyProgressDialog(activity, Message);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();


			Execute.run();
		}

		public static void ShowProgressDialog(Activity activity, String Message) {
			mProgressDialog = new MyProgressDialog(activity, Message);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		public static void ShowMessageDialog(Activity activity, String Message, int MessageType, View.OnClickListener BtnOkClick)
		{
			mShowMessageDialog = new ShowMessageDialogClass(activity, Message, MessageType, BtnOkClick);
			mShowMessageDialog.setCancelable(false);
			mShowMessageDialog.show();
		}

		public static void ShowMessageDialog(Activity activity, String Message, int MessageType, String BtnCaption, String OptionCaption,

											 View.OnClickListener BtnOkClick, View.OnClickListener OptionOnClick)
		{
			mShowMessageDialog = new ShowMessageDialogClass(activity, Message, MessageType, BtnCaption, OptionCaption, BtnOkClick, OptionOnClick);
			mShowMessageDialog.setCancelable(false);
			mShowMessageDialog.show();
		}

		public static void ShowMessageDialog(Activity activity, String Message, int MessageType)
		{
			View.OnClickListener ocl = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mShowMessageDialog.dismiss();
				}
			};

			mShowMessageDialog = new ShowMessageDialogClass(activity, Message, MessageType, ocl);
			mShowMessageDialog.setCancelable(false);
			mShowMessageDialog.show();
		}

		public static void ShowMessageDialog(Activity activity, int MessageID, int MessageType)
		{
			String Message = mContext.getString(MessageID);

			View.OnClickListener ocl = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mShowMessageDialog.dismiss();
				}
			};

			mShowMessageDialog = new ShowMessageDialogClass(activity, Message, MessageType, ocl);
			mShowMessageDialog.setCancelable(false);
			mShowMessageDialog.show();
		}

		public static void ShowMessageDialog(Activity activity, int MessageID, int MessageType, View.OnClickListener BtnOkClick)
		{
			String Message = mContext.getString(MessageID);

			mShowMessageDialog = new ShowMessageDialogClass(activity, Message, MessageType, BtnOkClick);
			mShowMessageDialog.setCancelable(false);
			mShowMessageDialog.show();
		}

		public static void ShowMessageDialog(Activity activity, String Message, int MessageType, String BtnCaption, View.OnClickListener BtnOkClick)
		{
			mShowMessageDialog = new ShowMessageDialogClass(activity, Message, MessageType, BtnCaption, BtnOkClick);
			mShowMessageDialog.setCancelable(false);
			mShowMessageDialog.show();
		}

		public static void ShowMessageDialog(Activity activity, int MessageID, int MessageType, int BtnCaptionID, View.OnClickListener BtnOkClick)
		{
			String Message = mContext.getString(MessageID);
			String BtnCaption = mContext.getString(BtnCaptionID);

			mShowMessageDialog = new ShowMessageDialogClass(activity, Message, MessageType, BtnCaption, BtnOkClick);
			mShowMessageDialog.setCancelable(false);
			mShowMessageDialog.show();
		}

		public static void ShowQuestionDialog(Activity activity, String Message, View.OnClickListener BtnOkClick) {
			mQuestionDialog = new QuestionDialogClass(activity, Message, BtnOkClick);
			mQuestionDialog.setCancelable(false);
			mQuestionDialog.show();
		}

		public static void ShowNoConnectionToServer(String FormName) {
			if (mProgressDialog != null)
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
			Toast.makeText(mContext, mContext.getString(R.string.messNoInternetConnection), Toast.LENGTH_SHORT).show();
		}

		public static void setMargins(View v, int l, int t, int r, int b) {
			if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
				ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
				p.setMargins(l, t, r, b);
				v.requestLayout();
			}
		}

		public static int GetResourceID(String ResourceName, String ResType) {
			Resources resources = mContext.getResources();
			final int resourceId = resources.getIdentifier(ResourceName, ResType,
					mContext.getPackageName());
			return resourceId;
		}

		public static String GetResourceName(int ResourceID) {
			return mContext.getResources().getResourceEntryName(ResourceID);
		}

		public static void SetTitle(String Title) {
			txtTitle.setText(Title);
		}
	}

	public static class HandleImagesAndAnimations {

		public static String toBase64(Bitmap bitmap, Bitmap.CompressFormat compressFormat) {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			bitmap.compress(compressFormat, 100, byteArrayOutputStream);
			byte[] byteArray = byteArrayOutputStream .toByteArray();
			return Base64.encodeToString(byteArray, Base64.DEFAULT);
		}

		public static String toBase64(ImageView imageView, Bitmap.CompressFormat compressFormat) {
			Bitmap bmp = PubProc.HandleImagesAndAnimations.drawableToBitmap(imageView.getDrawable());
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			bmp.compress(compressFormat, 100, byteArrayOutputStream);
			byte[] byteArray = byteArrayOutputStream .toByteArray();
			return Base64.encodeToString(byteArray, Base64.DEFAULT);
		}

		public static String toBase64(ImageView imageView) {
			Bitmap bmp = PubProc.HandleImagesAndAnimations.drawableToBitmap(imageView.getDrawable());
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
			byte[] byteArray = byteArrayOutputStream .toByteArray();
			return Base64.encodeToString(byteArray, Base64.DEFAULT);
		}

		public static String toBase64(Bitmap bitmap) {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
			byte[] byteArray = byteArrayOutputStream .toByteArray();
			return Base64.encodeToString(byteArray, Base64.DEFAULT);
		}

		public static void ShakeAnimation(View v, int duration){
			Animation shake = AnimationUtils.loadAnimation(PubProc.mContext, R.anim.shake_anim);
			shake.setDuration(duration);
			v.startAnimation(shake);
		}

		public static void FadeInAnimation(View v, int duration, int startOffset) {
			Animation fadeIn = new AlphaAnimation(0, 1);
			fadeIn.setInterpolator(new DecelerateInterpolator());
			fadeIn.setDuration(duration);
			fadeIn.setStartOffset(startOffset);

			AnimationSet animation = new AnimationSet(false);
			animation.addAnimation(fadeIn);
			v.setAnimation(animation);
			v.startAnimation(animation);
		}

		public static void FadeInAnimation(View v, int duration, int startOffset, Runnable mRun) {
			Animation fadeIn = new AlphaAnimation(0, 1);
			fadeIn.setInterpolator(new DecelerateInterpolator());
			fadeIn.setDuration(duration);
			fadeIn.setStartOffset(startOffset);

			AnimationSet animation = new AnimationSet(false);
			animation.addAnimation(fadeIn);
			v.setAnimation(animation);
			v.startAnimation(animation);

			new Handler().postDelayed(mRun, startOffset + 1000);
		}

		public static void FadeOutAnimation(View v, int duration, int startOffset) {
			Animation fadeOut = new AlphaAnimation(1, 0);
			fadeOut.setInterpolator(new DecelerateInterpolator());
			fadeOut.setDuration(duration);
			fadeOut.setStartOffset(startOffset);

			AnimationSet animation = new AnimationSet(false);
			animation.addAnimation(fadeOut);
			v.setAnimation(animation);
			v.startAnimation(animation);
		}

		public static void FadeOutAnimation(View v, int duration, int startOffset, Runnable mRun) {
			Animation fadeOut = new AlphaAnimation(1, 0);
			fadeOut.setInterpolator(new DecelerateInterpolator());
			fadeOut.setDuration(duration);
			fadeOut.setStartOffset(startOffset);

			AnimationSet animation = new AnimationSet(false);
			animation.addAnimation(fadeOut);
			v.setAnimation(animation);
			v.startAnimation(animation);

			mRun.run();
		}

		public static void JumpInAnimation(View v, int duration, int startOffset){
			Animation slideIn = AnimationUtils.loadAnimation(mContext, R.anim.fab_scale_up);
			slideIn.setInterpolator(new DecelerateInterpolator());
			slideIn.setDuration(duration);
			slideIn.setStartOffset(startOffset);
			v.setAnimation(slideIn);
			v.startAnimation(slideIn);
		}

		public static void Expand(final View v) {
			v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			final int targetHeight = v.getMeasuredHeight();

			// Older versions of android (pre API 21) cancel animations for views with a height of 0.
			v.getLayoutParams().height = 1;
			v.setVisibility(View.VISIBLE);


			Animation a = new Animation() {
				@Override
				protected void applyTransformation(float interpolatedTime, Transformation t) {
					v.getLayoutParams().height = interpolatedTime == 1
							? RelativeLayout.LayoutParams.WRAP_CONTENT
							: (int) (targetHeight * interpolatedTime);
					v.requestLayout();
				}

				@Override
				public boolean willChangeBounds() {
					return true;
				}
			};

			// 1dp/ms
			a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
			v.startAnimation(a);
		}

		public static void Collapse(final View v) {
			final int initialHeight = v.getMeasuredHeight();

			Animation a = new Animation() {
				@Override
				protected void applyTransformation(float interpolatedTime, Transformation t) {
					if (interpolatedTime == 1) {
						v.setVisibility(View.GONE);
					} else {
						v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
						v.requestLayout();
					}
				}

				@Override
				public boolean willChangeBounds() {
					return true;
				}
			};

			// 1dp/ms
			a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
			v.startAnimation(a);
		}

		public static class DropDownAnim extends Animation {
			private final int targetHeight;
			private final View view;
			private final boolean down;

			public DropDownAnim(View view, int targetHeight, boolean down) {
				this.view = view;
				this.targetHeight = targetHeight;
				this.down = down;
			}

			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				int newHeight;
				if (down) {
					newHeight = (int) (targetHeight * interpolatedTime);
				} else {
					newHeight = (int) (targetHeight * (1 - interpolatedTime));
				}
				view.getLayoutParams().height = newHeight;
				view.requestLayout();
			}

			@Override
			public void initialize(int width, int height, int parentWidth,
								   int parentHeight) {
				super.initialize(width, height, parentWidth, parentHeight);
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		}

		public static void setProgressWithAnimation(ProgressBar pb, int progress, int duration) {
			ObjectAnimator objectAnimator = ObjectAnimator.ofInt(pb, "progress", progress);
			objectAnimator.setDuration(duration);
			objectAnimator.setInterpolator(new DecelerateInterpolator());
			objectAnimator.start();
		}

		static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(res, resId, options);
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeResource(res, resId, options);
		}

		static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
			int inSampleSize = 1;
			if (options.outHeight > reqHeight || options.outWidth > reqWidth) {
				final int halfHeight = options.outHeight / 2;
				final int halfWidth = options.outWidth / 2;
				while ((halfHeight / inSampleSize) > reqHeight
						&& (halfWidth / inSampleSize) > reqWidth) {
					inSampleSize *= 2;
				}
			}
			return inSampleSize;
		}

		public static void unbindDrawables(View view) {
			if (view.getBackground() != null) {
				view.getBackground().setCallback(null);
			}
			if (view instanceof ViewGroup) {
				for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
					unbindDrawables(((ViewGroup) view).getChildAt(i));
				}
				((ViewGroup) view).removeAllViews();
			}
		}

		public static void FetchDrawableOnThread(final int DrawableID, final ImageView imageView) {
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message message) {
					imageView.setImageDrawable((Drawable) message.obj);
					imageView.setTag(DrawableID);
				}
			};

			Thread thread = new Thread() {
				@Override
				public void run() {
					@SuppressWarnings("deprecation")
					Drawable drawable = mContext.getResources().getDrawable(DrawableID);
					Message message = handler.obtainMessage(1, drawable);
					handler.sendMessage(message);
				}
			};
			thread.start();
		}

		public static void FetchDrawableOnThread(final int DrawableID, final ImageView imageView, final int Width, final int Height) {
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message message) {
					imageView.setImageDrawable((Drawable) message.obj);
					imageView.setTag(DrawableID);
				}
			};

			Thread thread = new Thread() {
				@Override
				public void run() {
					@SuppressWarnings("deprecation")
					Drawable drawable = mContext.getResources().getDrawable(DrawableID);
					Message message = handler.obtainMessage(1, ResizeDrawable(drawable, Width, Height));
					handler.sendMessage(message);
				}
			};
			thread.start();
		}

		public static Drawable ResizeDrawable(Drawable image, int Width, int Height) {
			Bitmap b = ((BitmapDrawable) image).getBitmap();
			Bitmap bitmapResized = Bitmap.createScaledBitmap(b, Width, Height, false);
			return new BitmapDrawable(mContext.getResources(), bitmapResized);
		}

		public static Bitmap drawableToBitmap(Drawable drawable) {
			if (drawable instanceof BitmapDrawable) {
				return ((BitmapDrawable) drawable).getBitmap();
			}

			final int width = !drawable.getBounds().isEmpty() ? drawable
					.getBounds().width() : drawable.getIntrinsicWidth();

			final int height = !drawable.getBounds().isEmpty() ? drawable
					.getBounds().height() : drawable.getIntrinsicHeight();

			final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width,
					height <= 0 ? 1 : height, Config.ARGB_8888);

			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);

			return bitmap;
		}

		public static byte[] BitmapToByte(Bitmap bitmap) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			return byteArray;
		}

		public static byte[] DrawableToByte(Drawable drawable) {
			Bitmap bitmap = drawableToBitmap(drawable);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			return byteArray;
		}

		public static void SetImageViewPicToOtherImageView(ImageView SourceView, ImageView DestinationView) {
			DestinationView.setImageBitmap(((BitmapDrawable) SourceView.getDrawable()).getBitmap());
		}

		public static void LoadImageInImageView(ImageView imageView, String imagePath, boolean HideOnNoImage, boolean ShowLogoNoImage){
			imagePath = HandleString.ISNULL(imagePath, "");
			if (imagePath.contains(".jpg") || imagePath.contains(".png")) {
				Picasso.with(mContext).load(imagePath).into(imageView);
				imageView.setScaleType(CENTER_CROP);
				if (imageView.getTag() != null) {
					if (imageView.getTag().toString().equals("0")) imageView.setTag("1");
				} else imageView.setTag("1");
			}
			else if (HideOnNoImage)
				imageView.setVisibility(View.GONE);
			else if (ShowLogoNoImage)
				imageView.setImageResource(R.drawable.logo);
		}

		public static void LoadImageInImageView(ImageView imageView, String imagePath, boolean HideOnNoImage, boolean ShowLogoNoImage, ImageView.ScaleType scaleType){
			imagePath = HandleString.ISNULL(imagePath, "");
			if (imagePath.contains(".jpg") || imagePath.contains(".png")) {
				Picasso.with(mContext).load(imagePath).into(imageView);
				imageView.setScaleType(scaleType);
				if (imageView.getTag() != null) {
					if (imageView.getTag().toString().equals("0")) imageView.setTag("1");
				} else imageView.setTag("1");
			}
			else if (HideOnNoImage)
				imageView.setVisibility(View.GONE);
			else if (ShowLogoNoImage)
				imageView.setImageResource(R.drawable.logo);
		}

		public static void LoadBase64InImageView(ImageView imageView, String base64, boolean HideOnNoImage, boolean ShowLogoNoImage){
			if (!base64.equals("''") && !base64.equals("")) {
				byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
				Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
				imageView.setImageBitmap(decodedByte);
				imageView.setScaleType(CENTER_CROP);
				if (imageView.getTag() != null) {
					if (imageView.getTag().toString().equals("0")) imageView.setTag("1");
				}else imageView.setTag("1");
			}
			else if (HideOnNoImage)
				imageView.setVisibility(View.GONE);
			else if (ShowLogoNoImage)
				imageView.setImageResource(R.drawable.logo);
		}

		public static void LoadImageView(ImageView imageView, String base64OrImagePath, boolean HideOnNoImage, boolean ShowLogoNoImage){
			if (base64OrImagePath.contains("http://") && !(base64OrImagePath.contains(".jpg") || base64OrImagePath.contains(".png"))){
				return;
			} else  {
				if (base64OrImagePath.contains(".jpg") || base64OrImagePath.contains(".png"))
					LoadImageInImageView(imageView, base64OrImagePath, HideOnNoImage, ShowLogoNoImage);
				else
					LoadBase64InImageView(imageView, base64OrImagePath, HideOnNoImage, ShowLogoNoImage);
			}
		}

		public static void LoadBase64InImageView(ImageView imageView, String base64, boolean HideOnNoImage, boolean ShowLogoNoImage, ImageView.ScaleType scaleType){
			if (!base64.equals("''") && !base64.equals("")) {
				byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
				Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
				imageView.setImageBitmap(decodedByte);
				imageView.setScaleType(scaleType);
				if (imageView.getTag() != null) {
					if (imageView.getTag().toString().equals("0")) imageView.setTag("1");
				}else imageView.setTag("1");
			}
			else if (HideOnNoImage)
				imageView.setVisibility(View.GONE);
			else if (ShowLogoNoImage)
				imageView.setImageResource(R.drawable.logo);
		}

	}
}

