package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import db.DBHelper;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
					String username="boss_1";
					System.out.println();

					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(142, 77, 139, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("”√ªß√˚");
		lblNewLabel.setBounds(69, 80, 54, 15);
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("√‹¬Î");
		label.setBounds(69, 154, 54, 15);
		contentPane.add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(142, 151, 139, 21);
		contentPane.add(textField_1);
		
		
		JButton button = new JButton("\u767B\u5F55");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login();
			}
		});
		button.setBounds(142, 213, 93, 23);
		contentPane.add(button);
	}
	public void Login(){
		try {
			String username;
			String password;
			username=textField.getText();
			password=textField_1.getText();
			
			if(password.equals(""))
				JOptionPane.showMessageDialog(null, "√‹¬Î≤ªƒ‹Œ™ø’");
			
			String str="select * from user_acount where user_name='"+username+"'";
			Connection conn=DBHelper.getConnection();
			Statement statement=conn.createStatement();
			ResultSet rs=statement.executeQuery(str);
			while(rs.next()){
				System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3));
				if(password.equals(rs.getString(2))){
					switch (Integer.parseInt(rs.getString(3))) {
					case 1:
						new MainFrame().setVisible(true);
						break;
					case 2:
						new accountant().setVisible(true);
						break;
					case 3:
						new Adminstor().setVisible(true);
						break;
					default:
						break;
					}
				}else{
					JOptionPane.showMessageDialog(null, "√‹¬Î¥ÌŒÛ");
				}
			
			}
			rs.close();
			statement.close();
			this.setVisible(false);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "’Àªß¥ÌŒÛ");			
			e.printStackTrace();
		}
	}
}
