package org.jbltd.password.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jbltd.password.Walkthrough;

public class PasswordManager {

    public List<IPassword> passwords;
    
    public PasswordManager() {
	passwords = new ArrayList<IPassword>();
	
	String homePath = System.getProperty("user.home");

	boolean WIN_32 = System.getProperty("os.name").contains("Windows");

	String WIN_32_DELIMITER = "\\";
	String OSX_DELIMITER = "/";

	File file = new File(homePath + (WIN_32 == true ? WIN_32_DELIMITER : OSX_DELIMITER) + "common.ps");
	
	if(!file.exists()) {
	    Walkthrough.create();
	}
	
	JSONUtil.loadPasswords(this);
    }
 
    public List<IPassword> getPasswords() {
	return passwords;
    }
    
    public IPassword getPasswordForName(String input) {
	
	List<IPassword> l = passwords.stream().filter(v -> v.getName().equals(input)).collect(Collectors.toCollection(ArrayList::new));
	return l.get(0);
    }
 
    public void addPassword(IPassword password) {
	passwords.add(password);
	JSONUtil.addPasswordToFile(password);
    }
    
}
