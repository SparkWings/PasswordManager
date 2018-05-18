package org.jbltd.password.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

import javax.swing.JOptionPane;

public class PasswordUtil {

    private static final String ALPHABET_CAPITAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String HOME_PATH = System.getProperty("user.home");
    private static final boolean WIN_32 = System.getProperty("os.name").contains("Windows");
    private static final String WIN_32_DELIMITER = "\\";
    private static final String WIN_32_BASE_DIR = HOME_PATH + WIN_32_DELIMITER + "AppData" + WIN_32_DELIMITER + "Local"
	    + WIN_32_DELIMITER + "File" + WIN_32_DELIMITER;
    private static final String OSX_BASE_DIR = "~/Library/Application Support/Common/";

    private static final File CONFIG_FILE = new File(
	    (WIN_32 == true ? (WIN_32_BASE_DIR + "config.yml") : (OSX_BASE_DIR + "config.yml")));

    
    public boolean createConfig() throws Exception {

	if (!CONFIG_FILE.exists()) {
	    CONFIG_FILE.getParentFile().mkdirs();
	    CONFIG_FILE.createNewFile();
	    return true;
	}
	return false;

    }

    public boolean authenticate(String input) throws Exception {
	
	String plainPassword = getMasterPassword();

	if (plainPassword.equals(input)) {
	    return true;
	} else {
	    JOptionPane.showMessageDialog(null, "Incorrect password.");
	    return false;
	}

    }

    public boolean generateKey() {

	Crypto c = new Crypto();

	try {
	    FileWriter configWriter = new FileWriter(CONFIG_FILE.getAbsolutePath(), true);
	    configWriter.write("crypto=" + c.generateKey());
	    configWriter.write(System.lineSeparator());
	    configWriter.close();
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return false;

    }

    public boolean createPasswordStoreFile(String masterPassword) throws Exception {

	String fileName = generateRandomName();

	File file = new File((WIN_32 == true ? WIN_32_BASE_DIR : OSX_BASE_DIR) + fileName + ".ps");

	if (!file.exists()) {
	    file.createNewFile();
	}

	FileWriter configWriter = new FileWriter(CONFIG_FILE.getAbsolutePath(), true);
	configWriter.write("store=" + file.getAbsolutePath());
	configWriter.write(System.lineSeparator());
	configWriter.close();

	BufferedWriter bw = null;
	FileWriter fw = null;

	try {
	    fw = new FileWriter(file);
	    bw = new BufferedWriter(fw);

	    String encryptedPassword = new Encrypt(masterPassword).doEncrypt();
	    bw.write(encryptedPassword);

	} finally {
	    bw.flush();
	    bw.close();
	}

	return true;
    }

    public boolean createJSONFile() throws Exception {

	File file = new File((WIN_32 == true ? WIN_32_BASE_DIR : OSX_BASE_DIR) + generateRandomName() + ".ps");

	if (!file.exists()) {
	    file.getParentFile().mkdirs();
	    file.createNewFile();
	}

	FileWriter configWriter = new FileWriter(CONFIG_FILE.getAbsolutePath(), true);
	configWriter.write("location=" + file.getAbsolutePath());
	configWriter.close();

	BufferedWriter bw = null;
	FileWriter fw = null;

	try {
	    fw = new FileWriter(file);
	    bw = new BufferedWriter(fw);

	    bw.write("{ \"passwords\": { } }");

	} finally {
	    bw.flush();
	    bw.close();
	}

	return true;
    }

    public static String getMasterPassword() throws Exception {

	File file = null;

	if (CONFIG_FILE.exists()) {

	    file = getMPFile();

	    BufferedReader br = new BufferedReader(new FileReader(file));
	    String decrypted = "";
	    try {
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
		    sb.append(line);
		    sb.append(System.lineSeparator());
		    line = br.readLine();
		}
		String everything = sb.toString();
		decrypted = everything;
	    } finally {
		br.close();
	    }

	    String plainPassword = new Decrypt(decrypted).doDecrypt();
	    return plainPassword;
	}
	return null;
    }

    public static boolean updatePasswordStoreFile(String newPassword) {

	File file = getMPFile();

	if (file.exists()) {

	    FileWriter fw = null;

	    try {
		fw = new FileWriter(file);

		String encryptedPassword = new Encrypt(newPassword).doEncrypt();

		fw.write(encryptedPassword);
		fw.flush();
		fw.close();

	    } catch (Exception e) {

	    }

	    return true;
	}
	return false;

    }

    private String generateRandomName() {

	StringBuilder build = new StringBuilder();

	for (int i = 0; i < 8; i++) {

	    if (i >= 6) {
		build.append(new Random().nextInt(9 - 1) + 1);
	    } else {
		build.append(ALPHABET_CAPITAL.charAt(new Random().nextInt(26 - 1) + 1));
	    }

	}

	return build.toString();

    }

    public static File getMPFile() {

	
	File file = null;
	BufferedReader reader = null;
	FileReader fr = null;

	try {
	    fr = new FileReader(CONFIG_FILE);
	    reader = new BufferedReader(fr);

	    String mpData = null;
	    String[] mpLoc = null;

	    while ((mpData = reader.readLine()) != null) {

		if (mpData.contains("store")) {
		    mpLoc = mpData.split("=");
		}

	    }

	    file = new File(mpLoc[1]);
	    reader.close();
	    fr.close();
	    return file;

	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public static File getPWDFile() {
	File file = null;
	BufferedReader reader = null;
	FileReader fr = null;

	try {
	    fr = new FileReader(CONFIG_FILE);
	    reader = new BufferedReader(fr);

	    String mpData = null;
	    String[] mpLoc = null;

	    while ((mpData = reader.readLine()) != null) {

		if (mpData.contains("location")) {
		    mpLoc = mpData.split("=");
		}

	    }

	    file = new File(mpLoc[1]);
	    reader.close();
	    fr.close();
	    return file;

	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public static byte[] getKey() {
	
	BufferedReader reader = null;
	FileReader fr = null;

	try {
	    fr = new FileReader(CONFIG_FILE);
	    reader = new BufferedReader(fr);

	    String mpData = null;
	    String[] mpLoc = null;

	    while ((mpData = reader.readLine()) != null) {

		if (mpData.contains("crypto")) {
		    mpLoc = mpData.split("=");
		}

	    }
	    reader.close();
	    fr.close();

	    String crypto = mpLoc[1];
	    byte[] key = crypto.getBytes();

	    return key;

	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public boolean doesConfigExist() {
	if(CONFIG_FILE.exists()) {
	    return true;
	}
	else return false;
    }
    
}
