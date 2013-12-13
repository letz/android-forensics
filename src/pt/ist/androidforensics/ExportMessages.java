package pt.ist.androidforensics;

import java.util.ArrayList;

import pt.ist.androidforensics.data.Model;
import pt.ist.androidforensics.data.Sms;
import pt.ist.androidforensics.utils.Utils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ExportMessages extends Activity {

	public static final String TAG = ExportMessages.class.getName();


	private ListView mMessageList;
	private ArrayList<String> strMessages = new ArrayList<String>();
	private ArrayList<Model> messages = new ArrayList<Model>();
	private ArrayAdapter<String> adapter;
	private Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export_messages);
		mActivity = this;
		mMessageList = (ListView) findViewById(R.id.messages);
		new ReadMessages().execute();

	}

	public void _exportMessages(View v){
		Utils.writeCSV(messages, Environment.getExternalStorageDirectory()+"/messages.csv");
	}

	private class ReadMessages extends AsyncTask<Void, Integer, Void>{
		private ProgressDialog progress;

		@Override
		protected void onProgressUpdate(Integer... prog) {

		}

		@Override
		protected void onPostExecute(Void result) {
			adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strMessages);
			mMessageList.setAdapter(adapter);
			progress.dismiss();
		}

		@Override
		protected void onPreExecute() {
		Resources res =	mActivity.getResources();
			progress = ProgressDialog.show(mActivity, res.getString(R.string.export_messages) , res.getString(R.string.exporting_messages) + " ...");
		}
		@Override
		protected Void doInBackground(Void... params) {
		       Uri uri = Uri.parse("content://sms/inbox");
		       Cursor c = getContentResolver().query(uri, null, null ,null,null);
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
		               Sms s = new Sms(address, body, date, read, type);
		               messages.add(s);
		               strMessages.add(s.toString());
		           }
		       }
		       c.close();
		       return null;
		}


	}
}
