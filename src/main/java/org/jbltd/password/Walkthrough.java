














package org.jbltd.password;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import org.jbltd.password.common.PasswordManager;
import org.jbltd.password.common.PasswordUtil;

public class Walkthrough extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -3713503730599317840L;
    
    private JFrame _frame;
    private JPasswordField _passwordField, _passwordField_1;
    private PasswordManager _manager;

    public Walkthrough(PasswordManager manager) {

	this._manager = manager;

	_frame = this;
	_frame.setBounds(100, 100, 450, 300);
	_frame.setTitle("Password Manager  - Setup");
	_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	_frame.setLocationRelativeTo(null);
	create();

    }

    public void create() {

	JPanel p = new JPanel();
	JLabel label = new JLabel(
		"Welcome to Jeremiah's Personal Password Storage. The next screen will walk you through setup.");
	p.add(label);
	String[] options = new String[] { "OK", "Cancel" };
	int option = JOptionPane.showOptionDialog(null, p, "Welcome!", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		null, options, options[1]);

	if (option == 0) {

	    JPanel panel_2 = new JPanel();
	    panel_2.setPreferredSize(_frame.getSize());
	    panel_2.setLayout(null);

	    JLabel lblWelcome = new JLabel("Here you will enter your master password. Don't forget it!");
	    lblWelcome.setBounds(72, 30, 450, 20);
	    panel_2.add(lblWelcome);

	    JLabel lblWelcome_2 = new JLabel("It grants you access into the program!");
	    lblWelcome_2.setBounds(117, 50, 450, 20);
	    panel_2.add(lblWelcome_2);

	    JLabel lblNewPassword = new JLabel("Please Enter a Master Password:");
	    lblNewPassword.setBounds(10, 90, 200, 20);
	    panel_2.add(lblNewPassword);

	    _passwordField = new JPasswordField();
	    _passwordField.setBounds(210, 90, 180, 20);
	    panel_2.add(_passwordField);

	    JLabel lblConfirmNewPassword = new JLabel("Confirm Password:");
	    lblConfirmNewPassword.setBounds(10, 115, 130, 20);
	    panel_2.add(lblConfirmNewPassword);

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

			finishSetup();

		    }

		}
	    };

	    _passwordField_1 = new JPasswordField();
	    _passwordField_1.setBounds(210, 115, 180, 20);
	    _passwordField_1.addKeyListener(keyListener);
	    panel_2.add(_passwordField_1);

	    JButton btnNewButton = new JButton("Confirm");
	    btnNewButton.addActionListener(new ActionListener() {
		@SuppressWarnings({ })
		public void actionPerformed(ActionEvent e) {

		    finishSetup();

		}
	    });
	    btnNewButton.setBounds(160, 199, 89, 23);
	    panel_2.add(btnNewButton);

	    btnNewButton.setVisible(true);
	    lblConfirmNewPassword.setVisible(true);
	    lblNewPassword.setVisible(true);
	    panel_2.setVisible(true);

	    panel_2.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setContentPane(panel_2);
	    panel_2.setLayout(null);

	    _frame.setVisible(true);

	} else {
	    System.exit(0);
	}

    }

    @SuppressWarnings("deprecation")
    private void finishSetup() {
	
	new PasswordUtil().generateKey();
	String password1 = _passwordField.getText();
	String password2 = _passwordField_1.getText();

	if (password1.equals("") || password1 == null) {
	    JOptionPane.showMessageDialog(null, "You must enter a password.");
	    return;
	}

	if (password1.equals(password2)) {
	    try {
		new PasswordUtil().createPasswordStoreFile(password1);
		
	    } catch (Exception ex) {
		// TODO Auto-generated catch block
		ex.printStackTrace();
	    } finally {
		try {
		    new PasswordUtil().createJSONFile();
		} catch (Exception ex) {
		    // TODO Auto-generated catch block
		    ex.printStackTrace();
		}

		JPanel p = new JPanel();
		JLabel label = new JLabel("Here are some tips: (Click OK)");
		p.add(label);
		String[] options = new String[] { "OK", "Cancel" };
		int option = JOptionPane.showOptionDialog(null, p, "Tips", JOptionPane.NO_OPTION,
			JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

		if (option == 0) {
		    JPanel p2 = new JPanel();
		    JLabel label2 = new JLabel(
			    "1.) Click File > Create Password to create a new password!");
		    p2.add(label2);
		    String[] options2 = new String[] { "OK", "Cancel" };
		    int option2 = JOptionPane.showOptionDialog(null, p2, "Tips", JOptionPane.NO_OPTION,
			    JOptionPane.PLAIN_MESSAGE, null, options2, options2[1]);

		    
		    if(option2 == 0) {
			JPanel p3 = new JPanel();
			    JLabel label3 = new JLabel(
				    "2.) RIGHT CLICK an existing password to edit / delete it");
			    p3.add(label3);
			    String[] options3 = new String[] { "OK", "Cancel" };
			    int option3 = JOptionPane.showOptionDialog(null, p3, "Tips", JOptionPane.NO_OPTION,
				    JOptionPane.PLAIN_MESSAGE, null, options3, options3[1]);

			    if(option3 == 0) {
				JOptionPane.showMessageDialog(null, "You're all setup! Feel free to email me at me@sparkwings.io with ANY issues.");
			    }
			    
		    }
		    
		}

		new Main(_manager);
		_frame.dispose();

	    }

	} else {
	    JOptionPane.showMessageDialog(_frame, "Passwords do not match, please try again");
	    _passwordField.setText(null);
	    _passwordField_1.setText(null);
	}
    }

}