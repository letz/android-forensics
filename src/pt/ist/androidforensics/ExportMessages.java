package pt.ist.androidforensics;

import java.util.ArrayList;

import pt.ist.androidforensics.data.Model;
import pt.ist.androidforensics.data.Sms;
import pt.ist.androidforensics.utils.Utils;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class ExportMessages extends Activity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export_messages);
	       Uri uri = Uri.parse("content://sms/inbox");
	       Cursor c= getContentResolver().query(uri, null, null ,null,null);
	       startManagingCursor(c);
	       ArrayList<Model> Messages = new ArrayList<Model> ();
	       // Read the sms data and store it in the list
	       if(c.moveToFirst()) {
	           for(int i=0; i < c.getCount(); i++) {
	        	   String body = c.getString(c.getColumnIndexOrThrow("body")).toString();
	        	   Log.d("body",body);
	               String address = c.getString(c.getColumnIndexOrThrow("address")).toString();
	               Log.d("address",address);
	               String date = c.getString(c.getColumnIndexOrThrow("date"));
	               Log.d("date",date);
	               String read = c.getString(c.getColumnIndexOrThrow("read"));
	               Log.d("read",read);
	               String type = c.getString(c.getColumnIndexOrThrow("type"));
	               Log.d("type",type);
	               c.moveToNext();
	               Messages.add(new Sms(address, body, date, read, type));
	           }
	       }
	       c.close();
	       Utils.writeCSV(Messages, Environment.getExternalStorageDirectory()+"/messages.csv");
	}

}
