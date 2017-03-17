package org.jbltd.password;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.SplashScreen;

import javax.swing.UIManager;

import org.jbltd.password.common.PasswordManager;
import org.jbltd.update.UpdateCheck;

public class Launcher {

    private static PasswordManager manager;
    private static final double VERSION = 1.5;

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
		try {

		    UpdateCheck uc = new UpdateCheck("PasswordManager", "https://s3.amazonaws.com/jbishop98/PWM.dat",
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
			    Thread.sleep(90);
			} catch (InterruptedException e) {
			}

		    }
		    splash.close();

		    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

		    manager = new PasswordManager();

		} catch (Exception e) {

		}

	    }

	});

    }

}
