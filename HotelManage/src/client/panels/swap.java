package client.panels;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import db.DBHelper;

import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class swap extends JPanel {
	private JTextField userid;
	private JLabel roomnum;
	private JLabel username;
	private JTable table;
	private JScrollPane scrollPane;
	private TableColumn aColumn;
	private JLabel label_2;
	private JLabel swaproom;
	private JButton button;
	private JButton button_1;
	/**
	 * Create the panel.
	 */
	public swap() {
		setLayout(null);
		
		userid = new JTextField();
		userid.setColumns(10);
		userid.setBounds(95, 10, 177, 21);
		add(userid);
		
		JLabel label = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7\u7801\uFF1A");
		label.setBounds(10, 10, 102, 21);
		add(label);
		
		JLabel label_1 = new JLabel("\u59D3\u540D\uFF1A");
		label_1.setBounds(10, 41, 102, 21);
		add(label_1);
		
		username = new JLabel("");
		username.setBounds(95, 47, 177, 15);
		add(username);
		
		JLabel label_3 = new JLabel("\u623F\u95F4\u53F7\uFF1A");
		label_3.setBounds(10, 72, 87, 28);
		add(label_3);
		
		roomnum = new JLabel("");
		roomnum.setBounds(95, 72, 124, 23);
		add(roomnum);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(310, 10, 267, 246);
		add(scrollPane);

		scrollPane.setViewportView(table);
		
		label_2 = new JLabel("\u6362\u5230\u7684\u623F\u95F4\u53F7\uFF1A");
		label_2.setBounds(10, 110, 87, 28);
		add(label_2);
		
		swaproom = new JLabel("");
		swaproom.setBounds(95, 110, 124, 23);
		add(swaproom);
		
		button = new JButton("\u67E5\u8BE2");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				swap.this.select();
			}
		});
		button.setBounds(10, 181, 93, 23);
		add(button);
		
		button_1 = new JButton("\u6362\u623F");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				swap.this.swaproom();
			}
		});
		button_1.setBounds(179, 181, 93, 23);
		add(button_1);
		initial();
	}
	
	
	public void select(){
		String result="";
		String str="";
		ResultSet rs;
		Statement statement;
		try {
			Connection conn = DBHelper.getConnection();
			statement = conn.createStatement();
			str="select * from customer where customer_id='"+userid.getText()+"'";//查找用户id
			rs=statement.executeQuery(str);
			if(rs.next()){
				username.setText(rs.getString(2));;
				str="select * from checkin where customer_id='"+userid.getText()+"'";//入住情况
				rs=statement.executeQuery(str);
				if(rs.next()){
					roomnum.setText(rs.getString(1));
					rs=statement.executeQuery(str);
					rs.next();
				}
			}else{
				JOptionPane.showMessageDialog(null, "未入住");
			}
			rs.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		initial();
	}
	
	
	public void swaproom(){
		String result="";
		String str="";
		Statement statement;
		if(!roomnum.getText().equals(null)){
			try {
				Connection conn = DBHelper.getConnection();
				statement = conn.createStatement();
				str="update room set room_state='空闲',custumer=NULL where room_num="+roomnum.getText();
				int i=statement.executeUpdate(str);
				str="update room set room_state='入住',custumer='"+username.getText()+"' where room_num='"+swaproom.getText()+"'";
				int j=statement.executeUpdate(str);	
				str="update checkin set room_num='"+swaproom.getText()+"'where room_num='"+roomnum.getText()+"'";
				int f=statement.executeUpdate(str);	
				str="insert swap values('"+roomnum.getText()+"','"+swaproom.getText()+"')";
				int k=statement.executeUpdate(str);	
				JOptionPane.showMessageDialog(null, "换房成功");
				statement.close();
				fresh();
			} catch (HeadlessException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	public void fresh(){
		username.setText("");
		roomnum.setText("");
		swaproom.setText("");
		initial();
	}
	
	public void initial(){
		try {
			String result="";
			String str="select * from  room where room_state='空闲'";
			Connection conn=DBHelper.getConnection();
			Statement statement=conn.createStatement();
			ResultSet rs=statement.executeQuery(str);
			
			
			rs.last();
			int k=rs.getRow();
			rs.first();
			Object cellData[][] = new Object[k][4];
			for (int i=0;i<k;i++) {
				String[] columnNames = { "房间号码", "房间类型", "单价", "换房" };
				cellData[i][0] = rs.getString(1);
				result+=rs.getString(1)+"\t";
				cellData[i][1] = rs.getString(4);
				result+=rs.getString(4)+"\t";
				cellData[i][2] = rs.getInt("price");
				result+=rs.getString("price")+"\n";
				rs.next();
				System.out.println(result);
				table = new JTable(cellData, columnNames);
				scrollPane.setViewportView(table);
				aColumn = table.getColumnModel().getColumn(3);
				aColumn.setCellEditor(table.getDefaultEditor(Boolean.class));
				aColumn.setCellRenderer(table.getDefaultRenderer(Boolean.class));
			}			
			rs.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				int row=table.rowAtPoint(e.getPoint());
				System.out.println(table.getValueAt(row, 0));
				swaproom.setText((String)table.getValueAt(row, 0));
			}
		});
	}
}
