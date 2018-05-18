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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jbltd.password.common.IPassword;
import org.jbltd.password.common.PasswordManager;

public class NewPasswordScreen extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -1753339384418979731L;

    private JPanel _contentPane;
    public JPasswordField _passwordField;
    public JPasswordField _passwordField_1;
    public JTextField _textField;
    private JFrame _frame;

    private PasswordManager _manager;

    public NewPasswordScreen(PasswordManager manager) {

	this._manager = manager;

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

	JLabel lblNewPassword = new JLabel("Password:");
	lblNewPassword.setBounds(10, 35, 109, 20);
	panel_2.add(lblNewPassword);

	_passwordField = new JPasswordField();
	_passwordField.setBounds(190, 32, 182, 20);
	panel_2.add(_passwordField);

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
	
	JLabel lblConfirmNewPassword = new JLabel("Confirm Password:");
	lblConfirmNewPassword.setBounds(10, 85, 145, 20);
	panel_2.add(lblConfirmNewPassword);

	_passwordField_1 = new JPasswordField();
	_passwordField_1.setBounds(190, 82, 182, 20);
	_passwordField_1.addKeyListener(keyListener);
	panel_2.add(_passwordField_1);

	JLabel lblWhatIsThis = new JLabel("What is this password for?");
	lblWhatIsThis.setBounds(10, 135, 204, 20);
	panel_2.add(lblWhatIsThis);

	_textField = new JTextField();
	_textField.setBounds(190, 132, 182, 20);
	_textField.addKeyListener(keyListener);
	
	panel_2.add(_textField);
	_textField.setColumns(10);

	JButton btnNewButton = new JButton("Confirm");
	btnNewButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		finishSetup();
		
	    }
	});
	btnNewButton.setBounds(163, 199, 89, 23);
	panel_2.add(btnNewButton);

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
