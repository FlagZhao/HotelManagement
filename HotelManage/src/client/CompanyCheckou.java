package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.panels.checkout;
import db.DBHelper;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CompanyCheckou extends JFrame {

	private JPanel contentPane;
	private JTextField companyid;
	private JTextField password;
	private int amount;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompanyCheckou frame = new CompanyCheckou(10);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CompanyCheckou(int amount) {
		this.amount=amount;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblid = new JLabel("\u516C\u53F8id\uFF1A");
		lblid.setBounds(10, 72, 61, 22);
		contentPane.add(lblid);
		
		JLabel label = new JLabel("\u5BC6\u7801\uFF1A");
		label.setBounds(10, 118, 61, 22);
		contentPane.add(label);
		
		companyid = new JTextField();
		companyid.setBounds(105, 73, 224, 21);
		contentPane.add(companyid);
		companyid.setColumns(10);
		
		password = new JTextField();
		password.setColumns(10);
		password.setBounds(105, 119, 224, 21);
		contentPane.add(password);
		
		JButton button = new JButton("\u7ED3\u8D26");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LogAndCheckout();
			}
		});
		button.setBounds(155, 179, 93, 23);
		contentPane.add(button);
	}
	public void LogAndCheckout(){
		String id=companyid.getText();
		String password_s=password.getText();
		String result="";
		String str="";
		ResultSet rs;
		Statement statement;
		try {
			Connection conn=DBHelper.getConnection();
			statement=conn.createStatement();
			str="select * from company where company_id='"+id+"'";
			rs=statement.executeQuery(str);
			if(rs.next()){
				if(password_s.equals(rs.getString(2))){
					str="update company set bill='"+(amount+Integer.parseInt(rs.getString(3)))+"' where company_id='"+id+"'";
					int i=statement.executeUpdate(str);
					JOptionPane.showMessageDialog(null, "退房成功");
				}else{
					JOptionPane.showMessageDialog(null, "密码错误");	
				}
			}else{
				JOptionPane.showMessageDialog(null, "无此公司账目");
			}
			
			rs.close();
			statement.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
