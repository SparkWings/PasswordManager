package org.jbltd.password;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import org.jbltd.password.common.IRunnable;
import org.jbltd.password.common.PasswordAuth;
import org.jbltd.password.common.PasswordManager;

public class ILoginScreen extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 5942805071836946428L;
    private JFrame frame;
    private JPanel contentPane;
    private JPasswordField passwordField;
    private JButton btnNewButton;

    private PasswordManager manager;

    private int incorrect = 0;
    private boolean disabled = false;

    private static boolean showButton = false;

    private ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
    
    public ILoginScreen(PasswordManager manager) {

	this.manager = manager;

	setTitle("Password Manager - Login");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);

	frame = this;

	frame.setLocationRelativeTo(null);
	
	JLabel lblMasterPassword = new JLabel("Master Password:");
	lblMasterPassword.setBounds((getWidth() / 8) - 20, getHeight() / 3, 150, 20);
	contentPane.add(lblMasterPassword);

	KeyListener keyListener = new KeyListener() {

	    @Override
	    public void keyTyped(KeyEvent e) {

	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
	    }

	    @Override
	    public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

		    if (!showButton) {
			return;
		    } else {

			auth();

		    }

		}

		showButton = true;

	    }
	};

	passwordField = new JPasswordField();
	passwordField.setBounds(getWidth() / 2, getHeight() / 3, 175, 20);
	passwordField.addKeyListener(keyListener);
	contentPane.add(passwordField);

	btnNewButton = new JButton("Confirm");
	btnNewButton.setBounds(161, lblMasterPassword.getVerticalAlignment() + 200, 89, 23);

	btnNewButton.setVisible(false);

	btnNewButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		auth();
	    }
	});

	IRunnable i = new IRunnable(new Runnable() {

	    @Override
	    public void run() {

		if (showButton) {
		    btnNewButton.setVisible(true);

		} else {
		    return;
		}

	    }
	}, -1);

	i.runXTimes(Executors.newScheduledThreadPool(1), 0L, 1L, TimeUnit.SECONDS);

	contentPane.add(btnNewButton);

	lblMasterPassword.setVisible(true);
	passwordField.setVisible(true);

	setVisible(true);

    }

    private void auth() {
	char[] password = passwordField.getPassword();
	String i = new String(password);

	try {
	    if (incorrect < 5) {

		boolean pass = new PasswordAuth().authenticate(i);
		if (!pass) {

		    passwordField.setText("");
		    incorrect++;

		} else {
		    frame.dispose();
		    new Main(manager);
		}
	    } else {

		if (!disabled) {

		    passwordField.setText("");

		    JOptionPane.showMessageDialog(null,
			    "You have entered in an incorrect password too many times." +
			    " Please wait to try again.");

		    passwordField.setEditable(false);
		    passwordField.setEnabled(false);
		    btnNewButton.setEnabled(false);

		    disabled = true;

		    s.scheduleWithFixedDelay(new Runnable() {
			    
			    @Override
			    public void run() {

				disabled = false;
				passwordField.setEnabled(true);
				btnNewButton.setEnabled(true);
				btnNewButton.requestFocus();
				incorrect = 0;
				passwordField.setEditable(true);
				
			    }
			}, 30L, 30L, TimeUnit.SECONDS);
			
		    

		} else
		    return;
	    }
	} catch (Exception ex) {

	}
    }

}
