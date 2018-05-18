package org.jbltd.password.common;

import java.util.Random;

public class Crypto {

    private static final String ALPHABET_CAPITAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String generateKey() {

	StringBuilder build = new StringBuilder();

	for (int i = 0; i < 16; i++) {

	    if (i >= 6) {
		build.append(new Random().nextInt(9 - 1) + 1);
	    } else {
		build.append(ALPHABET_CAPITAL.charAt(new Random().nextInt(26 - 1) + 1));
	    }

	}

	return build.toString();

    }

}
