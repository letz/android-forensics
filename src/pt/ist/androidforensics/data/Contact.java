package pt.ist.androidforensics.data;

import java.util.ArrayList;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;

public class Contact {

	private String mName;
	private int mId;
	private ArrayList<String> mPhones = new ArrayList<String>();
	private ArrayList<String> mEmails = new ArrayList<String>();
	private ArrayList<String> mAddresses = new ArrayList<String>();

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

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

	public ArrayList<String> getAddresses() {
		return mAddresses;
	}

	public void setAddresses(ArrayList<String> mAddresses) {
		this.mAddresses = mAddresses;
	}

	public void parseEmailsFromCursor(Cursor emails){
		while (emails.moveToNext()) {
			mEmails.add(emails.getString(emails.getColumnIndex(Email.DATA)));
		}
		emails.close();
	}

	public void parsePhonesFromCursor(Cursor cursor) {
		while (cursor.moveToNext())
			mPhones.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		cursor.close();
	}

	public void parseAddressesFromCursor(Cursor cursor){

	}
	@Override
	public String toString() {
		return "Contact [mName=" + mName + ", mPhones=" + mPhones
				+ ", mEmails=" + mEmails + ", mAddresses=" + mAddresses + "]";
	}

}
