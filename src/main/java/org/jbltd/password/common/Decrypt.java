








package org.jbltd.password.common;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
public class Decrypt {

    private String _input;

    public Decrypt(String input) {
	this._input = input;

    }

    public String getInput() {
	return _input;
    }

    public String doDecrypt() {

	String ALGO = "AES";
	byte[] keyValue = PasswordUtil.getKey();

	try {

	    Key key = new SecretKeySpec(keyValue, ALGO);
	    Cipher c = Cipher.getInstance(ALGO);
	    c.init(Cipher.DECRYPT_MODE, key);
	    byte[] decodedValue = new BASE64Decoder().decodeBuffer(_input);
	    byte[] decValue = c.doFinal(decodedValue);
	    String decryptedValue = new String(decValue);

	    return decryptedValue;

	} catch (Exception e) {
	    e.printStackTrace();
	    return "";
	}

    }

}
