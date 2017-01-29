package org.jbltd.password.common;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONUtil {

    public static void loadPasswords(PasswordManager manager) {

	String homePath = System.getProperty("user.home");

	boolean WIN_32 = System.getProperty("os.name").contains("Windows");

	String WIN_32_DELIMITER = "\\";
	String OSX_DELIMITER = "/";

	File file = new File(homePath + (WIN_32 == true ? WIN_32_DELIMITER : OSX_DELIMITER)
		+ System.getProperty("user.name") + ".ps");

	if(!file.exists()) {
	    return;
	}
	
	JSONParser parser = new JSONParser();

	try {
	    Object o = parser.parse(new FileReader(file));

	    JSONObject obj = (JSONObject) o;

	    JSONObject pw = (JSONObject) obj.get("passwords");

	    for (Object s : pw.keySet()) {

		IPassword p = new IPassword((String) s, (String) new Decrypt((String) pw.get(s)).doDecrypt());
		manager.addPassword(p);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    @SuppressWarnings("unchecked")
    public static void addPasswordToFile(IPassword password) {

	String homePath = System.getProperty("user.home");

	boolean WIN_32 = System.getProperty("os.name").contains("Windows");

	String WIN_32_DELIMITER = "\\";
	String OSX_DELIMITER = "/";

	File file = new File(homePath + (WIN_32 == true ? WIN_32_DELIMITER : OSX_DELIMITER)
		+ System.getProperty("user.name") + ".ps");

	try {

	    JSONParser parser = new JSONParser();

	    Object o = parser.parse(new FileReader(file));

	    JSONObject obj = (JSONObject) o;

	    JSONObject pw = (JSONObject) obj.get("passwords");
	    pw.put(password.getName(), new Encrypt(password.getPassword()).doEncrypt());
	    
	    try (FileWriter fw = new FileWriter(file)) {

		fw.write(obj.toJSONString());
		fw.flush();

	    } catch (IOException e) {
		e.printStackTrace();
	    }

	} catch (Exception e) {

	}
    }

}
