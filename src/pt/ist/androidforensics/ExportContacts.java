package pt.ist.androidforensics;

import java.util.ArrayList;

import pt.ist.androidforensics.data.Contact;
import pt.ist.androidforensics.data.Model;
import pt.ist.androidforensics.utils.Utils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExportContacts extends Activity {

	public static final String TAG = ExportContacts.class.getName();

	private ListView mContactsList;
	private ArrayList<String> strContacts = new ArrayList<String>();
	private ArrayList<Model> contacts = new ArrayList<Model>();
	private ArrayAdapter<String> adapter;
	private Activity mActivity;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export_contacts);
		mActivity = this;
		mContactsList = (ListView) findViewById(R.id.contacts);
		new ReadContacts().execute();
	}

	public void _exportContacts(View v){
		Utils.writeCSV(contacts, Environment.getExternalStorageDirectory()+"/contacts.csv");

	}

	private class ReadContacts extends AsyncTask<Void, Integer, Void> {

		private ProgressDialog progress;

		@Override
		protected void onProgressUpdate(Integer... prog) {

		}

		@Override
		protected void onPostExecute(Void result) {
			adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strContacts);
			mContactsList.setAdapter(adapter);
			progress.dismiss();
		}

		@Override
		protected void onPreExecute() {
		Resources res =	mActivity.getResources();
			progress = ProgressDialog.show(mActivity, res.getString(R.string.export_contacts) , res.getString(R.string.exporting_contact) + " ...");
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			ContentResolver cr = getContentResolver();
			Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
					null, null, null, null);
			if (cur.getCount() > 0) {
				while (cur.moveToNext()) {
					Contact contact = new Contact();

					String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
					String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					contact.setId(Integer.parseInt(id));
					contact.setName(name);
					Cursor emails = cr.query(Email.CONTENT_URI,null,Email.CONTACT_ID + " = " + id, null, null);
					contact.parseEmailsFromCursor(emails);
					Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
							new String[]{id}, null);
					contact.parsePhonesFromCursor(pCur);
					contacts.add(contact);
					strContacts.add(contact.toString());
					Log.d(TAG,contact.toString());
				}
			}
			cur.close();
			return null;
		}
	}

}

