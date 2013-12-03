package pt.ist.androidforensics;

import android.app.Application;
import android.content.Context;

public class AndroidForensicsApp extends Application{
	
	private static AndroidForensicsApp instance;

	public AndroidForensicsApp() {
		instance = this;
	}

	public static Context getContext() {
		return instance;
	}

}
