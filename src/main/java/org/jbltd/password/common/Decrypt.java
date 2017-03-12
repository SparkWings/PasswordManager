package org.jbltd.password.common;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;

public class Decrypt {

    private String input;

    public Decrypt(String input) {
	this.input = input;

    }

    public String getInput() {
	return input;
    }

    @SuppressWarnings("restriction")
    public String doDecrypt() {

	String ALGO = "AES";

	byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };

	try {

	    Key key = new SecretKeySpec(keyValue, ALGO);
	    Cipher c = Cipher.getInstance(ALGO);
	    c.init(Cipher.DECRYPT_MODE, key);
	    byte[] decodedValue = new BASE64Decoder().decodeBuffer(input);
	    byte[] decValue = c.doFinal(decodedValue);
	    String decryptedValue = new String(decValue);

	    return decryptedValue;

	} catch (Exception e) {
	    e.printStackTrace();
	    return "";
	}

    }

}
