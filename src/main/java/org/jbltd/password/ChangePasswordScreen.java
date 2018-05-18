



















package org.jbltd.password;

import java.awt.Color;
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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jbltd.password.common.IPassword;
import org.jbltd.password.common.PasswordManager;

public class ChangePasswordScreen extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -8694712229867044908L;

    private PasswordManager _manager;

    private JPasswordField _passwordField;
    private JPasswordField _passwordField_1;
    private JTextField _textField;
    private JFrame _frame;

    private IPassword _old;

    private JPanel _contentPane;

    public ChangePasswordScreen(PasswordManager manager, String what, IPassword oldPassword) {

	this._manager = manager;
	this._old = oldPassword;

	setTitle("Password Manager - Password Editor");
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
		System.out.println("Called");

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
		System.out.println("Called");

		new Main(_manager);
		_frame.dispose();
		return;

	    }
	});

	cancelBtn.add(cancel);

	setJMenuBar(cancelBtn);

	JLabel lblNewPassword = new JLabel("New Password:");
	lblNewPassword.setBounds(10, 35, 109, 20);
	panel_2.add(lblNewPassword);

	_passwordField = new JPasswordField();
	_passwordField.setBounds(190, 32, 182, 20);
	panel_2.add(_passwordField);

	JLabel lblConfirmNewPassword = new JLabel("Confirm New Password:");
	lblConfirmNewPassword.setBounds(10, 85, 145, 20);
	panel_2.add(lblConfirmNewPassword);

	KeyListener keyListener = new KeyListener() {

	    @Override
	    public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

		    finishSetup();

		}

	    }
	};

	_passwordField_1 = new JPasswordField();
	_passwordField_1.setBounds(190, 82, 182, 20);
	_passwordField_1.addKeyListener(keyListener);
	panel_2.add(_passwordField_1);

	JLabel lblWhatIsThis = new JLabel("Password For:");
	lblWhatIsThis.setBounds(10, 135, 204, 20);
	panel_2.add(lblWhatIsThis);

	_textField = new JTextField();
	_textField.setBounds(190, 132, 182, 20);
	_textField.addKeyListener(keyListener);
	panel_2.add(_textField);
	_textField.setText(what);
	_textField.setColumns(20);

	JButton btnNewButton = new JButton("Confirm");
	btnNewButton.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {

		finishSetup();

	    }
	});
	btnNewButton.setBounds(50, 199, 150, 23);
	panel_2.add(btnNewButton);

	JButton btnDelete = new JButton("Delete Password");

	btnDelete.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		int i = JOptionPane.showConfirmDialog(btnDelete, "Are you SURE?");

		if (i == 0) {

		    _manager.removePassword(oldPassword);

		    new Main(_manager);
		    _frame.dispose();

		} else {
		    return;
		}

	    }
	});

	btnDelete.setForeground(Color.RED);
	btnDelete.setBounds(250, 199, 150, 23);
	panel_2.add(btnDelete);

	btnDelete.setVisible(true);
	btnNewButton.setVisible(true);
	lblConfirmNewPassword.setVisible(true);
	lblNewPassword.setVisible(true);
	lblWhatIsThis.setVisible(true);
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

	if (_textField.getText().equals("") || _textField.getText() == null) {
	    JOptionPane.showMessageDialog(null, "You must provide a name for your password!");
	    return;
	}

	if (password1.equals(password2)) {

	    // Remove _old
	    _manager.removePassword(_old);

	    IPassword password = new IPassword(_textField.getText(), password1);
	    _manager.addPassword(password);

	    JOptionPane.showMessageDialog(null, "Success!");
	    _passwordField.setText(null);
	    _passwordField_1.setText(null);
	    _textField.setText(null);

	    _frame.dispose();
	    new Main(_manager);

	} else {
	    JOptionPane.showMessageDialog(_frame, "Passwords do not match, please try again");
	    _passwordField.setText(null);
	    _passwordField_1.setText(null);
	}

    }


}

