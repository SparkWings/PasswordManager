

















package org.jbltd.password;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import org.jbltd.password.common.PasswordUtil;
import org.jbltd.password.common.PasswordManager;

public class ChangeMasterPasswordScreen extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -8694712229867044908L;

    private PasswordManager _manager;
    private JFrame _frame;
    private JPasswordField _passwordField;
    private JPasswordField _passwordField_1;
    private JPanel _contentPane;

    public ChangeMasterPasswordScreen(PasswordManager manager) {

	this._manager = manager;

	setTitle("Password Manager - Master Password Editor");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);

	_frame = this;

	_frame.setLocationRelativeTo(null);

	JPanel panel_2 = new JPanel();
	panel_2.setPreferredSize(_frame.getSize());
	panel_2.setLayout(null);

	JMenuBar cancelBtn = new JMenuBar();

	JMenu cancel = new JMenu("Go Back");
	cancel.setMnemonic(KeyEvent.VK_G);

	cancel.addMouseListener(new MouseListener() {

	    @Override
	    public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void mousePressed(MouseEvent e) {

		new Main(_manager);
		_frame.dispose();
		return;

	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void mouseClicked(MouseEvent e) {

		new Main(_manager);
		_frame.dispose();
		return;

	    }
	});

	cancelBtn.add(cancel);

	setJMenuBar(cancelBtn);

	JLabel lblNewPassword = new JLabel("New Password:");
	lblNewPassword.setBounds(10, 45, 109, 20);
	panel_2.add(lblNewPassword);

	_passwordField = new JPasswordField();
	_passwordField.setBounds(190, 45, 182, 20);
	panel_2.add(_passwordField);

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

	_passwordField_1 = new JPasswordField();
	_passwordField_1.setBounds(190, 92, 182, 20);
	_passwordField_1.addKeyListener(keyListener);
	panel_2.add(_passwordField_1);

	JButton btnNewButton = new JButton("Confirm");
	btnNewButton.addActionListener(new ActionListener() {
	    @SuppressWarnings({})
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

	_contentPane = panel_2;
	_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(_contentPane);
	_contentPane.setLayout(null);

	_frame.setVisible(true);

    }

    @SuppressWarnings("deprecation")
    private void finishSetup() {

	String password1 = _passwordField.getText();
	String password2 = _passwordField_1.getText();

	if (password1.equals("") || password1 == null) {
	    JOptionPane.showMessageDialog(null, "You must enter a password.");
	    return;
	}

	if (password1.equals(password2)) {

	    PasswordUtil.updatePasswordStoreFile(password1);

	    JOptionPane.showMessageDialog(null, "Success!");

	    _frame.dispose();
	    new Main(_manager);

	} else {
	    JOptionPane.showMessageDialog(_frame, "Passwords do not match, please try again");
	    _passwordField.setText(null);
	    _passwordField_1.setText(null);
	}

    }
}


