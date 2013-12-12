package pt.ist.androidforensics;

import java.util.ArrayList;

import pt.ist.androidforensics.data.Contact;
import pt.ist.androidforensics.data.Model;
import pt.ist.androidforensics.utils.Utils;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExportContacts extends Activity {

	private ListView mContactsList;
	private ArrayList<String> strContacts = new ArrayList<String>();
	private ArrayList<Model> contacts = new ArrayList<Model>();
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export_contacts);

		mContactsList = (ListView) findViewById(R.id.contacts);

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
				Log.d("Letz",contact.toString());
			}
		}
		adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strContacts);
		mContactsList.setAdapter(adapter);
	}

	public void _exportContacts(View v){
		Utils.writeCSV(contacts, Environment.getExternalStorageDirectory()+"/contacts.csv");

	}

}

