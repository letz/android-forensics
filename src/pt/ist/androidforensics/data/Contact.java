package pt.ist.androidforensics.data;

import java.util.ArrayList;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;

public class Contact extends Model {

	private String mName;
	private ArrayList<String> mPhones = new ArrayList<String>();
	private ArrayList<String> mEmails = new ArrayList<String>();

	private static int mEmailsMax;
	private static int mPhonesMax;

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public ArrayList<String> getPhones() {
		return mPhones;
	}

	public void setPhones(ArrayList<String> mPhones) {
		this.mPhones = mPhones;
	}

	public ArrayList<String> getEmails() {
		return mEmails;
	}

	public void setEmails(ArrayList<String> mEmails) {
		this.mEmails = mEmails;
	}

	public void parseEmailsFromCursor(Cursor emails){
		while (emails.moveToNext()) {
			mEmails.add(emails.getString(emails.getColumnIndex(Email.DATA)));
		}
		emails.close();
		if(mEmailsMax < mEmails.size())
			mEmailsMax = mEmails.size();
	}

	public void parsePhonesFromCursor(Cursor cursor) {
		while (cursor.moveToNext())
			mPhones.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		cursor.close();
		if(mPhonesMax < mPhones.size())
			mPhonesMax = mPhones.size();
	}

	@Override
	public String toString() {
		return "Contact [mName=" + mName + ", mPhones=" + mPhones
				+ ", mEmails=" + mEmails + "]";
	}

	@Override
	public String toCSV() {
		String csv = mName;
		for(int i = 0 ; i < mPhonesMax ; ++i )
			csv += i < mPhones.size() ? "#" + mPhones.get(i) : "#";
		for(int i = 0 ; i < mEmailsMax ; ++i )
			csv += i < mEmails.size() ? "#" + mEmails.get(i) : "#";
		return csv;
	}

	@Override
	public String toCSVHeader() {
		String header = "Name";
		for(int i = 0 ; i < mPhonesMax ; ++i )
			header += "#Phone"+(i+1);
		for(int i = 0 ; i < mEmailsMax ; ++i )
			header += "#Email"+(i+1);
		return header;
	}

}
