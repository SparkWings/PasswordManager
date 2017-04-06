package org.jbltd.password;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.swing.plaf.FontUIResource;

import org.apache.commons.io.FilenameUtils;
import org.jbltd.password.common.PasswordManager;
import org.jbltd.password.util.AestheticUtil;
import org.jbltd.update.UpdateCheck;

public class Launcher {

    private static PasswordManager manager;
    private static final double VERSION = 0.8;

    static void renderSplashFrame(Graphics2D g, int frame) {
	final String[] comps = { "Encryption Algorithms", "Password Manager", "Update Manager", "User Passwords",
		"Master Password", "Useless Developer Junk" };
	g.setComposite(AlphaComposite.Clear);
	g.fillRect(120, 140, 200, 40);
	g.setPaintMode();
	g.setColor(Color.BLACK);
	g.drawString("Loading " + comps[(frame / 5) % 6] + "...", 120, 150);
    }

    public static void main(String[] args) {

	EventQueue.invokeLater(new Runnable() {

	    public void run() {

		String path = Launcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String decodedPath = null;
		try {
		    decodedPath = URLDecoder.decode(path, "UTF-8");
		    
		   System.out.println(FilenameUtils.separatorsToSystem(decodedPath).substring(1)); 
		} catch (UnsupportedEncodingException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}

		try {

		    UpdateCheck uc = new UpdateCheck(FilenameUtils.separatorsToSystem(decodedPath).substring(1), "https://s3.amazonaws.com/jbishop98/PWM.dat",
			    "https://s3.amazonaws.com/jbishop98/PWM_LATEST.jar", VERSION);
		    Thread t = new Thread(uc);

		    t.start();

		    final SplashScreen splash = SplashScreen.getSplashScreen();
		    if (splash == null) {
			return;
		    }
		    Graphics2D g = splash.createGraphics();
		    if (g == null) {
			return;
		    }
		    for (int i = 0; i < 30; i++) {

			renderSplashFrame(g, i);
			splash.update();
			try {
			    Thread.sleep(80);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
 
		    }

		    splash.close();

		    AestheticUtil.setUIFont(new FontUIResource(new Font("Arial", 0, 14)));
		    AestheticUtil.setLookAndFeel();

		    manager = new PasswordManager();

		} catch (Exception e) {
		    e.printStackTrace();
		}

	    }

	});

    }

}
