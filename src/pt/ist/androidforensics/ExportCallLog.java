package pt.ist.androidforensics;

import java.util.ArrayList;
import java.util.Date;

import pt.ist.androidforensics.data.*;
import pt.ist.androidforensics.utils.Utils;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.util.Log;

public class ExportCallLog extends Activity {

	String typeOfCall = null;
    ArrayList<Model> callsList = new ArrayList<Model> ();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export_call_logs);
		
		ContentResolver cenas = getContentResolver();
		Cursor cursor = cenas.query(CallLog.Calls.CONTENT_URI, null, null, null, null);
		int numberColumn = cursor.getColumnIndex(CallLog.Calls.NUMBER);
		int typeColumn = cursor.getColumnIndex(CallLog.Calls.TYPE);
		int dateColumn = cursor.getColumnIndex(CallLog.Calls.DATE);
		int durationColumn = cursor.getColumnIndex(CallLog.Calls.DURATION);
		
		if(cursor.getCount() > 0) {
			while(cursor.moveToNext()) {
				String phoneNumber = cursor.getString(numberColumn);
				String callType = cursor.getString(typeColumn);
				String callDate = cursor.getString(dateColumn);
				String callDuration = cursor.getString(durationColumn);
				
				Date date = new Date(Long.valueOf(callDate));
				
				int tmp = Integer.parseInt(callType);
				
				if(tmp == CallLog.Calls.OUTGOING_TYPE)
					typeOfCall = "Chamada Efectuada";
				if(tmp == CallLog.Calls.INCOMING_TYPE)
					typeOfCall = "Chamada Recebida";
				if(tmp == CallLog.Calls.MISSED_TYPE)
					typeOfCall = "Chamada Perdida";
				
				Call call = new Call(phoneNumber, typeOfCall, date, callDuration);
				callsList.add(call);
				Log.d("Tits", call.toCSVHeader());
				Log.d("Tits", call.toCSV());
			}
		}
		cursor.close();
	    Utils.writeCSV(callsList, Environment.getExternalStorageDirectory()+"/call_logs.csv");

	}

}