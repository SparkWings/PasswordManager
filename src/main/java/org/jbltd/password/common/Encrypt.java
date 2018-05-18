








package org.jbltd.password.common;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class Encrypt {

    private String input;

    public Encrypt(String input) {
	this.input = input;

    }

    public String getInput() {
	return this.input;
    }

    public String doEncrypt() {

	try {
	    String ALGO = "AES";

	    byte[] keyValue = PasswordUtil.getKey();

	    Key key = new SecretKeySpec(keyValue, ALGO);
	    Cipher c = Cipher.getInstance(ALGO);
	    c.init(Cipher.ENCRYPT_MODE, key);
	    byte[] encVal = c.doFinal(input.getBytes());
	    String encryptedString = new BASE64Encoder().encode(encVal);

	    return encryptedString;

	} catch (Exception e) {
	    return null;
	}
    }

    
}