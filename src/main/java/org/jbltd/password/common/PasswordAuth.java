package org.jbltd.password.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class PasswordAuth {

    public boolean authenticate(String input) throws Exception {

	String homePath = System.getProperty("user.home");

	boolean WIN_32 = System.getProperty("os.name").contains("Windows");

	String WIN_32_DELIMITER = "\\";
	String OSX_DELIMITER = "/";

	File file = new File(homePath + (WIN_32 == true ? WIN_32_DELIMITER : OSX_DELIMITER) + "common.ps");

	if (file.exists()) {

	    // PASS 1
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

	    // PASS 2
	    if (plainPassword.equals(input)) {

		return true;
	    } else {
		JOptionPane.showMessageDialog(null, "Incorrect password.");
		return false;
	    }

	} else {
	    throw new IOException("File does not exist.");
	}

    }

    public static boolean createPasswordStoreFile(String masterPassword) throws Exception {

	String homePath = System.getProperty("user.home");

	boolean WIN_32 = System.getProperty("os.name").contains("Windows");

	String WIN_32_DELIMITER = "\\";
	String OSX_DELIMITER = "/";

	File file = new File(homePath + (WIN_32 == true ? WIN_32_DELIMITER : OSX_DELIMITER) + "common.ps");

	if (!file.exists()) {
	    file.createNewFile();
	}

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

	String homePath = System.getProperty("user.home");

	boolean WIN_32 = System.getProperty("os.name").contains("Windows");

	String WIN_32_DELIMITER = "\\";
	String OSX_DELIMITER = "/";

	File file = new File(homePath + (WIN_32 == true ? WIN_32_DELIMITER : OSX_DELIMITER)
		+ System.getProperty("user.name") + ".ps");

	if (!file.exists()) {
	    file.createNewFile();
	}

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
	
	String homePath = System.getProperty("user.home");

	boolean WIN_32 = System.getProperty("os.name").contains("Windows");

	String WIN_32_DELIMITER = "\\";
	String OSX_DELIMITER = "/";

	File file = new File(homePath + (WIN_32 == true ? WIN_32_DELIMITER : OSX_DELIMITER) + "common.ps");

	if (file.exists()) {

	    // PASS 1
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
	
	String homePath = System.getProperty("user.home");

	boolean WIN_32 = System.getProperty("os.name").contains("Windows");

	String WIN_32_DELIMITER = "\\";
	String OSX_DELIMITER = "/";

	File file = new File(homePath + (WIN_32 == true ? WIN_32_DELIMITER : OSX_DELIMITER) + "common.ps");

	if (file.exists()) {

	    file.delete();
	    try {
		createPasswordStoreFile(newPassword);
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    return true;
	}
	return false;
	
    }
    
}
