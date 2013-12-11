package pt.ist.androidforensics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void _exportContacts(View v) {
		Intent i = new Intent(getApplicationContext(), ExportContacts.class);
		startActivity(i);

	}

	public void _exportCallLog(View v) {
		Intent i = new Intent(getApplicationContext(), ExportContacts.class);
		startActivity(i);
	}
	
	public void _exportMessages(View v) {
		Intent i = new Intent(getApplicationContext(), ExportMessages.class);
		startActivity(i);

	}
}
