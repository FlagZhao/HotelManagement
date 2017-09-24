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

import db.DBHelper;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Rigist extends JFrame {

	private JPanel contentPane;
	private JTextField id;
	private JTextField password;
	private JRadioButton bu1;
	private JRadioButton bu2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Rigist frame = new Rigist();
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
	public Rigist() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u8D26\u53F7\uFF1A");
		label.setBounds(44, 50, 72, 18);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801\uFF1A");
		label_1.setBounds(44, 119, 72, 18);
		contentPane.add(label_1);
		
		id = new JTextField();
		id.setBounds(130, 47, 228, 24);
		contentPane.add(id);
		id.setColumns(10);
		
		password = new JTextField();
		password.setBounds(130, 116, 228, 24);
		contentPane.add(password);
		password.setColumns(10);
		
		JButton button = new JButton("\u6CE8\u518C");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Rigist.this.RigistID();
			}
		});
		button.setBounds(130, 213, 113, 27);
		contentPane.add(button);
		
		bu1 = new JRadioButton("\u524D\u53F0");
		bu1.setBounds(44, 169, 157, 27);
		contentPane.add(bu1);
		bu1.setSelected(true);
		
		bu2 = new JRadioButton("\u4F1A\u8BA1");
		bu2.setBounds(201, 169, 157, 27);
		contentPane.add(bu2);
		ButtonGroup bg=new ButtonGroup();
		bg.add(bu1);
		bg.add(bu2);
	}
	public void RigistID(){
		String result="";
		String str="";
		ResultSet rs;
		Statement statement;
		try {
			Connection conn=DBHelper.getConnection();
			statement=conn.createStatement();
			if(bu1.isSelected())
				str="insert into user_acount values('"+id.getText()+"','"+password.getText()+"','1')";
			else
				str="insert into user_acount values('"+id.getText()+"','"+password.getText()+"','2')";
			int i=statement.executeUpdate(str);
			JOptionPane.showMessageDialog(null, "注册成功");
	
			statement.close();
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "注册失败");
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
}
