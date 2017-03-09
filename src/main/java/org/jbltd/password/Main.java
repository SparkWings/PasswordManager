package org.jbltd.password;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

import org.jbltd.password.common.IPassword;
import org.jbltd.password.common.PasswordAuth;
import org.jbltd.password.common.PasswordManager;
import org.jbltd.update.UpdateCheck;

public class Main {

    private JFrame frame;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;
    private JTextField textField;
    private static PasswordManager manager;

    private static final double VERSION = 1.1;

    static void renderSplashFrame(Graphics2D g, int frame) {
	final String[] comps = { "Encryption Algorithms", "Password Manager", "Update Manager", "User Passwords",
		"Master Password", "Useless Developer Junk" };
	g.setComposite(AlphaComposite.Clear);
	g.fillRect(120, 140, 200, 40);
	g.setPaintMode();
	g.setColor(Color.BLACK);
	g.drawString("Loading " + comps[(frame / 5) % 6] + "...", 120, 150);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {

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

		    manager = new PasswordManager();

		    UpdateCheck uc = new UpdateCheck("PasswordManager", "https://s3.amazonaws.com/jbishop98/PWM.dat",
			    "https://s3.amazonaws.com/jbishop98/PWM_LATEST.jar", VERSION);
		    Thread t = new Thread(uc);

		    t.start();

		    Main window = new Main();
		    window.frame.setVisible(true);
		    window.frame.toFront();

		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public Main() {
	try {
	    initialize();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * Initialize the contents of the frame.
     * 
     * @throws IOException
     */
    private void initialize() throws IOException {

	// BufferedImage icon =
	// ImageIO.read(ClassLoader.getSystemResource("/img.ico"));

	frame = new JFrame();
	frame.setBounds(100, 100, 450, 300);
	frame.setTitle("Password Manager by Jeremiah Bishop");
	// frame.setIconImage(icon);
	frame.setResizable(false);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	SpringLayout springLayout = new SpringLayout();
	frame.getContentPane().setLayout(springLayout);

	final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	springLayout.putConstraint(SpringLayout.EAST, tabbedPane, 444, SpringLayout.WEST, frame.getContentPane());
	tabbedPane.setPreferredSize(frame.getSize());
	springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 0, SpringLayout.NORTH, frame.getContentPane());
	springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 0, SpringLayout.WEST, frame.getContentPane());
	springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, 0, SpringLayout.SOUTH, frame.getContentPane());
	frame.getContentPane().add(tabbedPane);

	JPanel p = new JPanel();
	JLabel label = new JLabel("Enter your master password:");
	JPasswordField pass = new JPasswordField(10);
	p.add(label);
	p.add(pass);
	String[] options = new String[] { "OK", "Cancel" };
	int option = JOptionPane.showOptionDialog(null, p, "Enter Password", JOptionPane.NO_OPTION,
		JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

	if (option == 0 || option == 1) {
	    char[] password = pass.getPassword();
	    String i = new String(password);

	    try {
		new PasswordAuth().authenticate(i);
	    } catch (Exception e) {

	    }
	}

	if (option == -1) {
	    System.exit(0);
	}

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setPreferredSize(frame.getSize());
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	tabbedPane.addTab("View Passwords", null, scrollPane, "View passwords you've already made");

	JPanel t = new JPanel();
	t.setPreferredSize(scrollPane.getSize());
	t.setLayout(new FlowLayout());

	int boundx = 10;
	int boundy = 0;

	for (IPassword i : manager.getPasswords()) {

	    JButton newlbl = new JButton(i.getName());
	    newlbl.setBounds(boundx, boundy, newlbl.getText().length() * 25, 20);
	    newlbl.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    JOptionPane.showMessageDialog(null,
			    "Your password for \"" + i.getName() + "\" is: " + i.getPassword());

		}
	    });
	    t.add(newlbl);
	    boundy -= 30;
	    System.out.println("Added password.");
	    continue;
	}

	scrollPane.getViewport().add(t);

	JPanel panel_2 = new JPanel();
	panel_2.setPreferredSize(frame.getSize());
	tabbedPane.addTab("Create Password", null, panel_2, "Create and store a new password");
	panel_2.setLayout(null);

	JLabel lblNewPassword = new JLabel("New Password:");
	lblNewPassword.setBounds(10, 35, 109, 14);
	panel_2.add(lblNewPassword);

	passwordField = new JPasswordField();
	passwordField.setBounds(165, 32, 182, 20);
	panel_2.add(passwordField);

	JLabel lblConfirmNewPassword = new JLabel("Confirm New Password:");
	lblConfirmNewPassword.setBounds(10, 85, 145, 14);
	panel_2.add(lblConfirmNewPassword);

	passwordField_1 = new JPasswordField();
	passwordField_1.setBounds(165, 82, 146, 20);
	panel_2.add(passwordField_1);

	JLabel lblWhatIsThis = new JLabel("What is this password for?");
	lblWhatIsThis.setBounds(10, 135, 204, 14);
	panel_2.add(lblWhatIsThis);

	textField = new JTextField();
	textField.setBounds(165, 132, 146, 20);
	panel_2.add(textField);
	textField.setColumns(10);

	JButton btnNewButton = new JButton("Confirm");
	btnNewButton.addActionListener(new ActionListener() {
	    @SuppressWarnings({ "deprecation" })
	    public void actionPerformed(ActionEvent e) {

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

		    IPassword password = new IPassword(textField.getText(), password1);
		    manager.addPassword(password);

		    JOptionPane.showMessageDialog(null,
			    "Success! Your password will be available for viewing the next time you open the application.");
		    passwordField.setText(null);
		    passwordField_1.setText(null);
		    textField.setText(null);

		} else {
		    JOptionPane.showMessageDialog(frame, "Passwords do not match, please try again");
		    passwordField.setText(null);
		    passwordField_1.setText(null);
		}

	    }
	});
	btnNewButton.setBounds(163, 199, 89, 23);
	panel_2.add(btnNewButton);

    }
}
