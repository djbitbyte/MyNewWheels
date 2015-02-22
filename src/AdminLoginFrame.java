import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.net.URL;
/**
 * The login frame which checks if the user name and password are correct which leads to the AdminFrame.
 */
public class AdminLoginFrame extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;
	
	public AdminLoginFrame() {
		setBackground(Color.LIGHT_GRAY);
		setTitle("Admin LogIn");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 351);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		passwordField = new JPasswordField();         //The password field 
		passwordField.setBounds(39, 176, 180, 26);
		contentPane.add(passwordField);

		textField = new JTextField();             // The user name Field
		textField.setBounds(39, 99, 180, 26);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnEnter = new JButton("LogIn");      //Login button
		btnEnter.setBackground(Color.WHITE);
		JFrame local = this;
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pass = StartFrame.dbcc.getUserPass(textField.getText());
				if(pass != null) {
					char[] rawPass = passwordField.getPassword();
				
					boolean isCorrectPass = pass.length() == rawPass.length;
					
					if(isCorrectPass) {
						for(int i = 0; i < rawPass.length; i++) {
							if(pass.charAt(i) != rawPass[i]) {
								isCorrectPass = false;
								break;
							}
						}
					}
					
					if(isCorrectPass) {
						dispose();
						new AdminFrame("Admin", StartFrame.dbcc);
						return;
					}
				}
				
				JOptionPane.showMessageDialog(local, "Wrong user name or password!");
			}
		});
		btnEnter.setBounds(318, 276, 106, 26);
		contentPane.add(btnEnter);

		JButton btnBack = new JButton("Back");       //Back button
		btnBack.setBackground(Color.WHITE);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				StartFrame frm = new StartFrame();
				frm.setVisible(true);
			}
		});
		btnBack.setBounds(202, 276, 106, 26);
		contentPane.add(btnBack);

		JLabel lblUserName = new JLabel("User Name"); 
		lblUserName.setBounds(39, 74, 68, 14);
		contentPane.add(lblUserName);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(39, 151, 68, 14);
		contentPane.add(lblPassword);
		
		JLabel admnlbl = new JLabel("");
		admnlbl.setBounds(202, 11, 222, 266);
		admnlbl.setIcon(new ImageIcon("img/administrator-icon.png"));
		contentPane.add(admnlbl);
		
		setLocationRelativeTo(null);
	}
}
