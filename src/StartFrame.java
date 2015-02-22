import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Point;
import java.awt.Frame;
/**
 * This is the main frame which is connected with all of the frames included in the application.
 */
public class StartFrame extends JFrame {

	private JPanel contentPane;
	static DBCarConnector dbcc;

	public static void main(String[] args) {
		dbcc = new DBCarConnector(
				"dbinstance.c1neuj5ak0x9.us-west-2.rds.amazonaws.com", 
				"3306", 
				"dbcar", 
				"cardbadmin", 
				"GUGroup12");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartFrame frame = new StartFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StartFrame() {
		setBackground(Color.GRAY);
		setResizable(false);
		setTitle("My New Wheels");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1265, 683);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnAdmin = new JButton("Admin");            //Admin button
		btnAdmin.setBackground(Color.WHITE);
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				AdminLoginFrame adm = new AdminLoginFrame();
				adm.setVisible(true);
			}
		});
		btnAdmin.setBounds(1107, 590, 117, 33);
		contentPane.add(btnAdmin);

		JButton btnSearch = new JButton("Search");       //Search button
		btnSearch.setBackground(Color.WHITE);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ea) {
				dispose();
				new UserFrame("Search", dbcc);
			}
		});
		btnSearch.setBounds(980, 590, 117, 33);
		contentPane.add(btnSearch);

		JButton helpbtn = new JButton("Help");      //Help button 
		helpbtn.setBackground(Color.WHITE);
		helpbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				HelpFrame hfrm = new HelpFrame();
				hfrm.setVisible(true);
			}
		});
		helpbtn.setBounds(59, 595, 89, 23);
		contentPane.add(helpbtn);

		JLabel lbl = new JLabel("");
		lbl.setBounds(0, 0, 1264, 662);
		lbl.setIcon(new ImageIcon("img/Welcome.png"));
		contentPane.add(lbl);
		
		setLocationRelativeTo(null);
	}
}
