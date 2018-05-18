package org.jbltd.password.util;

import java.util.Enumeration;

import javax.swing.UIManager;

public class AestheticUtil {

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
	Enumeration<Object> keys = UIManager.getDefaults().keys();
	while (keys.hasMoreElements()) {
	    Object key = keys.nextElement();
	    Object value = UIManager.get(key);
	    if (value != null && value instanceof javax.swing.plaf.FontUIResource)
		UIManager.put(key, f);
	}
    }

    public static void setLookAndFeel() {

	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
