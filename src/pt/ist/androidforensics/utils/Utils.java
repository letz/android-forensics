package pt.ist.androidforensics.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import pt.ist.androidforensics.data.Model;

public class Utils {

	/**
	 * Writes to file the list of objects in csv format
	 * @param models list to save
	 * @param path filepath eg: "sdcard/log.file"
	 */
	public static void writeCSV(ArrayList<Model> models, String path) {
		File logFile = new File(path);
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,	true));
			buf.append(models.get(0).toCSVHeader());
			buf.newLine();
			for (Model m : models) {
				buf.append(m.toCSV());
				buf.newLine();
			}
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
