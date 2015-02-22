import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
/**
 * This is a help frame. It includes the helpful information for those who use the program for the first time.
 */

public class HelpFrame extends JFrame {

	private JPanel contentPane;
	
	public HelpFrame() {
		setBackground(Color.WHITE);
		setResizable(false);
		setTitle("Help");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 618, 401);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton backbtn = new JButton("Back");     // Back button
		backbtn.setBackground(SystemColor.inactiveCaption);
		backbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				StartFrame frmf = new StartFrame();
				frmf.setVisible(true);
			}
		});
		backbtn.setBounds(493, 327, 89, 23);
		contentPane.add(backbtn);
		
		JLabel label = new JLabel("");
		label.setBackground(Color.WHITE);
		label.setBounds(0, 0, 582, 350);
		label.setIcon(new ImageIcon("img/HelpImg.png"));
		contentPane.add(label);
		
		setLocationRelativeTo(null);
	}
}
