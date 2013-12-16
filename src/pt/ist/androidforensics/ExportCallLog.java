package pt.ist.androidforensics;

import java.util.ArrayList;
import java.util.Date;

import pt.ist.androidforensics.data.*;
import pt.ist.androidforensics.utils.Utils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExportCallLog extends Activity {

	String typeOfCall = null;
	private ListView mCallList;
	private ArrayList<String> strCalls = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private Activity mActivity;
    ArrayList<Model> callsList = new ArrayList<Model> ();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export_call_logs);
		mActivity = this;
		mCallList = (ListView) findViewById(R.id.calls);
		new ReadCalls().execute();
		
	}
	
	public void _exportMessages(View v){
		Utils.writeCSV(callsList, Environment.getExternalStorageDirectory()+"/call_log.csv");
	}
	
		private class ReadCalls extends AsyncTask<Void, Integer, Void>{
			private ProgressDialog progress;

			@Override
			protected void onProgressUpdate(Integer... prog) {

			}

			@Override
			protected void onPostExecute(Void result) {
				adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strCalls);
				mCallList.setAdapter(adapter);
				progress.dismiss();
			}

			@Override
			protected void onPreExecute() {
			Resources res =	mActivity.getResources();
				progress = ProgressDialog.show(mActivity, res.getString(R.string.export_call_logs) , res.getString(R.string.exporting_call_log) + " ...");
			}
			
			@Override
			protected Void doInBackground(Void... params) {
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
						strCalls.add(call.toString());
						Log.d("Tits", call.toCSVHeader());
						Log.d("Tits", call.toCSV());
					}
				}
				cursor.close();
				return null;
			}
			
		}
		

	}

