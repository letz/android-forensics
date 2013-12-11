package pt.ist.androidforensics;

import java.util.ArrayList;

import pt.ist.androidforensics.data.Contact;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExportContacts extends Activity {

	private ListView mContactsList;
	private ArrayList<String> contacts = new ArrayList<String>();
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
				Contact tits = new Contact();

				String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				tits.setId(Integer.parseInt(id));
				tits.setName(name);
				Cursor emails = cr.query(Email.CONTENT_URI,null,Email.CONTACT_ID + " = " + id, null, null);
				tits.parseEmailsFromCursor(emails);

				Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
						new String[]{id}, null);
				tits.parsePhonesFromCursor(pCur);
				//Cursor address = cr.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
				//		null,
				//		ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = " + id, null, null);
				//tits.parseAddressesFromCursor(address);
				Log.d("Letz",tits.toString());
			}
		}
		adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, contacts);
		mContactsList.setAdapter(adapter);
	}



}

