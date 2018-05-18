package org.jbltd.password.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jbltd.password.ILoginScreen;
import org.jbltd.password.Walkthrough;

public class PasswordManager {

    public List<IPassword> passwords;

    public PasswordManager() {
	passwords = new ArrayList<IPassword>();
	
	PasswordUtil p = new PasswordUtil();
	
	if (!p.doesConfigExist()) {
	    Walkthrough w = new Walkthrough(this);
	    w.toFront();
	} else {
	    new ILoginScreen(this);
	}

	JSONUtil.loadPasswords(this);
    }

    public List<IPassword> getPasswords() {
	return passwords;
    }

    public IPassword getPasswordForName(String input) {

	List<IPassword> l = passwords.stream().filter(v -> v.getName().equals(input))
		.collect(Collectors.toCollection(ArrayList::new));
	return l.get(0);
    }

    public void addPassword(IPassword password) {

	if (!passwords.contains(password)) {
	    passwords.add(password);
	    JSONUtil.addPasswordToFile(password);
	}
    }

    public void removePassword(IPassword password) {

	if (passwords.contains(password)) {
	    passwords.remove(password);
	    JSONUtil.removePasswordFromFile(password);
	}

    }

}
