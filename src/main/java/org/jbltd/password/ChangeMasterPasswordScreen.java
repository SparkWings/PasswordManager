package org.jbltd.password;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.jbltd.password.common.PasswordAuth;
import org.jbltd.password.common.PasswordManager;

public class ChangeMasterPasswordScreen extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -8694712229867044908L;

    private PasswordManager manager;

    private JFrame frame;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;
    
    private JPanel contentPane;
    
    public ChangeMasterPasswordScreen(PasswordManager manager) {
	

	this.manager = manager;
	
	
	setTitle("Password Manager - Master Password Editor");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);

	
	frame = this;

	frame.setLocationRelativeTo(null);
	
	JPanel panel_2 = new JPanel();
	panel_2.setPreferredSize(frame.getSize());
	panel_2.setLayout(null);

	JMenuBar cancelBtn = new JMenuBar();

	JMenu cancel = new JMenu("Go Back");
	cancel.setMnemonic(KeyEvent.VK_G);

	cancel.addMenuListener(new MenuListener() {

	    @Override
	    public void menuSelected(MenuEvent e) {

		frame.dispose();
		new Main(manager);

	    }

	    @Override
	    public void menuDeselected(MenuEvent e) {

	    }

	    @Override
	    public void menuCanceled(MenuEvent e) {

	    }
	});

	cancelBtn.add(cancel);

	setJMenuBar(cancelBtn);

	JLabel lblNewPassword = new JLabel("New Password:");
	lblNewPassword.setBounds(10, 45, 109, 20);
	panel_2.add(lblNewPassword);

	passwordField = new JPasswordField();
	passwordField.setBounds(190, 45, 182, 20);
	panel_2.add(passwordField);

	JLabel lblConfirmNewPassword = new JLabel("Confirm New Password:");
	lblConfirmNewPassword.setBounds(10, 92, 145, 20);
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
	
	passwordField_1 = new JPasswordField();
	passwordField_1.setBounds(190, 92, 182, 20);
	passwordField_1.addKeyListener(keyListener);
	panel_2.add(passwordField_1);

	JButton btnNewButton = new JButton("Confirm");
	btnNewButton.addActionListener(new ActionListener() {
	    @SuppressWarnings({ })
	    public void actionPerformed(ActionEvent e) {

		finishSetup();
		
	    }
	});
	btnNewButton.setBounds(135, 199, 150, 23);
	panel_2.add(btnNewButton);

	
	btnNewButton.setVisible(true);
	lblConfirmNewPassword.setVisible(true);
	lblNewPassword.setVisible(true);
	cancelBtn.setVisible(true);
	panel_2.setVisible(true);

	contentPane = panel_2;
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);

	frame.setVisible(true);

	
    }

    private void finishSetup() {
	
	String password1 = passwordField.getText();
	String password2 = passwordField_1.getText();

	if (password1.equals("") || password1 == null) {
	    JOptionPane.showMessageDialog(null, "You must enter a password.");
	    return;
	}

	if (password1.equals(password2)) {


	    PasswordAuth.updatePasswordStoreFile(password1);
	    
	    JOptionPane.showMessageDialog(null, "Success!");

	    frame.dispose();
	    new Main(manager);

	} else {
	    JOptionPane.showMessageDialog(frame, "Passwords do not match, please try again");
	    passwordField.setText(null);
	    passwordField_1.setText(null);
	}

	
    }
}
