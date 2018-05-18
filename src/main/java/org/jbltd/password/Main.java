















































package org.jbltd.password;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jbltd.password.common.IPassword;
import org.jbltd.password.common.PasswordManager;
import org.jbltd.password.util.ButtonColumn;

/**
 * Password Manager Version 1.0-BETA. <br />
 * Copyright Jeremiah Bishop and Rob Carmick (The really cool ButtonColumn class
 * guy)
 * 
 * @author Jeremiah Bishop
 * @param PasswordManager.class
 *
 */
public class Main extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -7091900718104919506L;
    
    private JFrame _frame;
    private PasswordManager _manager;
    
    
    public Main(PasswordManager manager) {

	this._manager = manager;

	try {
	    initialize();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @SuppressWarnings("serial")
    private void initialize() throws IOException {

	_frame = this;
	_frame.setBounds(100, 100, 450, 575);
	_frame.setTitle("Password Manager by Jeremiah Bishop");
	_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	_frame.setLocationRelativeTo(null);

	// Toolbar
	JMenuBar menu = new JMenuBar();

	JMenu file = new JMenu("File");
	file.setMnemonic(KeyEvent.VK_F);

	JMenuItem newPwd = new JMenuItem("Create New Password");
	newPwd.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		_frame.dispose();
		new NewPasswordScreen(_manager);

	    }
	});
	newPwd.setMnemonic(KeyEvent.VK_N);

	file.add(newPwd);

	JMenuItem newMPwd = new JMenuItem("Change Your Master Password");
	newMPwd.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		_frame.dispose();
		new ChangeMasterPasswordScreen(_manager);

	    }
	});
	newMPwd.setMnemonic(KeyEvent.VK_C);
	file.add(newMPwd);

	menu.add(file);

	setJMenuBar(menu);

	// --------------------------
	// Data Table

	Object[][] data = new Object[_manager.getPasswords().size()][];

	for (int i = 0; i < data.length; i++) {

	    int current = i;

	    Object[] o = new Object[] { _manager.getPasswords().get(current).getName(), "View Password" };
	    data[current] = o;

	}

	String[] columns = { "Password Name", "" };

	final DefaultTableModel dm = new DefaultTableModel(data, columns);
	final JTable table = new JTable(dm);

	UIDefaults defaults = UIManager.getDefaults();

	if (defaults.get("Table.alternateRowColor") == null) {
	    defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
	}

	table.setRowHeight(30);

	table.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mouseClicked(MouseEvent e) {

		if (table != null) {

		    if (SwingUtilities.isRightMouseButton(e)) {
			// -- select a row
			int idx = table.rowAtPoint(e.getPoint());
			int idc = table.columnAtPoint(e.getPoint());

			table.getSelectionModel().setSelectionInterval(idx, idx);

			int column = (idc != 0 ? idc - 1 : idc);

			String passwordName = (String) dm.getValueAt(idx, column);

			IPassword i = _manager.getPasswordForName(passwordName);

			new ChangePasswordScreen(_manager, passwordName, i);
			_frame.dispose();

		    }

		}
	    }

	});

	JTextField tf = new JTextField();
	tf.setEditable(false);

	DefaultCellEditor editor = new DefaultCellEditor(tf);
	table.setDefaultEditor(Object.class, editor);

	Action showPassword = new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		JTable table = (JTable) e.getSource();
		int modelRow = Integer.valueOf(e.getActionCommand());

		int column = table.getSelectedColumn() - 1;

		String passwordName = (String) dm.getValueAt(modelRow, column);

		IPassword i = _manager.getPasswordForName(passwordName);

		JPanel pv = new JPanel();

		JLabel l = new JLabel("Your password for " + i.getName() + " is:");

		JTextArea ta = new JTextArea(1, i.getPassword().length() + 1);
		ta.setText(i.getPassword());
		ta.setWrapStyleWord(true);
		ta.setLineWrap(true);
		ta.setCaretPosition(0);
		ta.setEditable(false);

		JScrollPane p = new JScrollPane(ta);

		pv.add(l);
		pv.add(p);

		JOptionPane.showMessageDialog(null, pv, "View Password", JOptionPane.INFORMATION_MESSAGE);

	    }
	};

	new ButtonColumn(table, showPassword, 1);

	JScrollPane scroll;

	if (table.getRowCount() < 1) {

	    JPanel jp = new JPanel();
	    jp.setPreferredSize(_frame.getSize());
	    jp.setLayout(null);

	    JLabel jl = new JLabel("No passwords... :(");
	    jl.setBounds(160, 10, 150, 20);

	    jp.add(jl);

	    scroll = new JScrollPane(jp);

	} else {
	    scroll = new JScrollPane(table);
	}

	_frame.getContentPane().add(scroll, BorderLayout.CENTER);
	_frame.setVisible(true);

    }

    public static void main(String[] args) {
	new Main(new PasswordManager());
    }

}
