package tech.sisyphus.util;

import tech.sisyphus.config.Config;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    private static FileWriter fw;

    public static void append(String s) throws IOException {
        fw = new FileWriter(Config.LOG_FILE, true);
		fw.write(s + "\r\n");
		if (fw != null) {
			fw.close();
		}
	}
	
}
