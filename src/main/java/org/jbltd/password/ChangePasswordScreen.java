package org.jbltd.password;

import java.awt.Color;
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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.jbltd.password.common.IPassword;
import org.jbltd.password.common.PasswordManager;

public class ChangePasswordScreen extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -8694712229867044908L;

    private PasswordManager manager;

    private JPasswordField passwordField;
    private JPasswordField passwordField_1;
    private JTextField textField;
    private JFrame frame;

    private IPassword old;

    private JPanel contentPane;

    public ChangePasswordScreen(PasswordManager manager, String what, IPassword oldPassword) {

	this.manager = manager;

	this.old = oldPassword;

	setTitle("Password Manager - Password Editor");
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
	lblNewPassword.setBounds(10, 35, 109, 20);
	panel_2.add(lblNewPassword);

	passwordField = new JPasswordField();
	passwordField.setBounds(190, 32, 182, 20);
	panel_2.add(passwordField);

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

	passwordField_1 = new JPasswordField();
	passwordField_1.setBounds(190, 82, 182, 20);
	passwordField_1.addKeyListener(keyListener);
	panel_2.add(passwordField_1);

	JLabel lblWhatIsThis = new JLabel("Password For:");
	lblWhatIsThis.setBounds(10, 135, 204, 20);
	panel_2.add(lblWhatIsThis);

	textField = new JTextField();
	textField.setBounds(190, 132, 182, 20);
	textField.addKeyListener(keyListener);
	panel_2.add(textField);
	textField.setText(what);
	textField.setColumns(20);

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

		    manager.removePassword(oldPassword);

		    new Main(manager);
		    frame.dispose();

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

	if (textField.getText().equals("") || textField.getText() == null) {
	    JOptionPane.showMessageDialog(null, "You must provide a name for your password!");
	    return;
	}

	if (password1.equals(password2)) {

	    // Remove old
	    manager.removePassword(old);

	    IPassword password = new IPassword(textField.getText(), password1);
	    manager.addPassword(password);

	    JOptionPane.showMessageDialog(null, "Success!");
	    passwordField.setText(null);
	    passwordField_1.setText(null);
	    textField.setText(null);

	    frame.dispose();
	    new Main(manager);

	} else {
	    JOptionPane.showMessageDialog(frame, "Passwords do not match, please try again");
	    passwordField.setText(null);
	    passwordField_1.setText(null);
	}

    }

    public static void main(String[] args) {

	new ChangePasswordScreen(null, "lol", null);

    }

}
