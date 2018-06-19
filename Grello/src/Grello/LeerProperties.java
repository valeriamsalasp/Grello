package Grello;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LeerProperties {
	public Properties getFile(String url) {
		FileInputStream profile = null;
		Properties prop = null;
		try {
			profile = new FileInputStream(url);
			prop = new Properties();
			prop.load(profile);
			profile.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
