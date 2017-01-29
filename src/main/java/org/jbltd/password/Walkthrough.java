package org.jbltd.password;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.jbltd.password.common.PasswordAuth;

public class Walkthrough {

    public static void create() {

	JPanel p = new JPanel();
	JLabel label = new JLabel(
		"Welcome to Jeremy Bishop's Personal Password Store. The next few dialogs will walk you through setup.");
	p.add(label);
	String[] options = new String[] { "OK", "Cancel" };
	int option = JOptionPane.showOptionDialog(null, p, "Welcome!", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		null, options, options[1]);

	System.out.println(option);
	
	if (option == 0) {

	    JPanel p2 = new JPanel();
	    JLabel label2 = new JLabel(
		    "Please select and ENTER a master password. Your master password will grant you access into the program. Don't lose it!");
	    JPasswordField pass = new JPasswordField(10);
	    p2.add(label2);
	    p2.add(pass);
	    String[] options2 = new String[] { "OK", "Cancel" };
	    int option2 = JOptionPane.showOptionDialog(null, p2, "Enter Password", JOptionPane.NO_OPTION,
		    JOptionPane.PLAIN_MESSAGE, null, options2, options2[1]);

	    if (option2 == 0 || option2 == 1) {

		JPanel p3 = new JPanel();
		JLabel label3 = new JLabel("Please CONFIRM your master password.");
		JPasswordField pass2 = new JPasswordField(10);
		p3.add(label3);
		p3.add(pass2);
		String[] options3 = new String[] { "OK", "Cancel" };
		int option3 = JOptionPane.showOptionDialog(null, p3, "Confirm Password", JOptionPane.NO_OPTION,
			JOptionPane.PLAIN_MESSAGE, null, options3, options3[1]);

		if (option3 == 0 || option3 == 1) {
		    if (pass.getText().equals(pass2.getText())) {

			try {
			    new PasswordAuth().createPasswordStoreFile(pass.getText());
			} catch (Exception e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			} finally {
			    try {
				new PasswordAuth().createJSONFile();
			    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }
			    JOptionPane.showMessageDialog(null,
				    "You're all set up! Feel free to email me at me@sparkwings.io with any issues!");
			}
		    } else {
			System.exit(0);
		    }

		} else {
		    System.exit(0);
		}

	    }
	}
	else {
	    System.exit(0);
	}

	if (option == -1) {
	    System.exit(0);
	}

    }

}
