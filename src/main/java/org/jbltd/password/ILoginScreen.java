


















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
import org.jbltd.password.common.PasswordUtil;
import org.jbltd.password.common.PasswordManager;

public class ILoginScreen extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 5942805071836946428L;
  
    private JFrame _frame;
    private JPanel _contentPane;
    private JPasswordField _passwordField;
    private JButton _btnNewButton;
    private PasswordManager _manager;
    private int _incorrect = 0;
    private boolean _disabled = false;
    
    private static boolean show_button = false;

    private ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
    
    public ILoginScreen(PasswordManager manager) {

	this._manager = manager;

	setTitle("Password Manager - Login");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	_contentPane = new JPanel();
	_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(_contentPane);
	_contentPane.setLayout(null);

	_frame = this;

	_frame.setLocationRelativeTo(null);
	
	JLabel lblMasterPassword = new JLabel("Master Password:");
	lblMasterPassword.setBounds((getWidth() / 8) - 20, getHeight() / 3, 150, 20);
	_contentPane.add(lblMasterPassword);

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

		    if (!show_button) {
			return;
		    } else {

			auth();

		    }

		}

		show_button = true;

	    }
	};

	_passwordField = new JPasswordField();
	_passwordField.setBounds(getWidth() / 2, getHeight() / 3, 175, 20);
	_passwordField.addKeyListener(keyListener);
	_contentPane.add(_passwordField);

	_btnNewButton = new JButton("Confirm");
	_btnNewButton.setBounds(161, lblMasterPassword.getVerticalAlignment() + 200, 89, 23);

	_btnNewButton.setVisible(false);

	_btnNewButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		auth();
	    }
	});

	IRunnable i = new IRunnable(new Runnable() {

	    @Override
	    public void run() {

		if (show_button) {
		    _btnNewButton.setVisible(true);

		} else {
		    return;
		}

	    }
	}, -1);

	i.runXTimes(Executors.newScheduledThreadPool(1), 0L, 1L, TimeUnit.SECONDS);

	_contentPane.add(_btnNewButton);

	lblMasterPassword.setVisible(true);
	_passwordField.setVisible(true);

	setVisible(true);

    }

    private void auth() {
	char[] password = _passwordField.getPassword();
	String i = new String(password);


	try {
	    if (_incorrect < 5) {

		
		boolean pass = new PasswordUtil().authenticate(i);
		if (!pass) {

		    _passwordField.setText("");
		    _incorrect++;

		} else {
		    _frame.dispose();
		    new Main(_manager);
		}
	    } else {

		if (!_disabled) {

		    _passwordField.setText("");

		    JOptionPane.showMessageDialog(null,
			    "You have entered in an _incorrect password too many times." +
			    " Please wait to try again.");

		    _passwordField.setEditable(false);
		    _passwordField.setEnabled(false);
		    _btnNewButton.setEnabled(false);

		    _disabled = true;

		    s.scheduleWithFixedDelay(new Runnable() {
			    
			    @Override
			    public void run() {

				_disabled = false;
				_passwordField.setEnabled(true);
				_btnNewButton.setEnabled(true);
				_btnNewButton.requestFocus();
				_incorrect = 0;
				_passwordField.setEditable(true);
				
			    }
			}, 30L, 30L, TimeUnit.SECONDS);
			
		    

		} else
		    return;
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

}