package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import db.DBHelper;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Adminstor extends JFrame {

	private JPanel contentPane;
	private TableColumn aColumn;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel id;
	private JButton fresh;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Adminstor frame = new Adminstor();
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
	public Adminstor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 489, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 10, 350, 241);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		
		JButton addUser = new JButton("\u65B0\u589E\u7528\u6237");
		addUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Rigist().setVisible(true);;
			}
		});
		addUser.setBounds(364, 13, 93, 23);
		contentPane.add(addUser);
		
		JButton DeleteUser = new JButton("\u5220\u9664\u7528\u6237");
		DeleteUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Adminstor.this.delete();
			}
		});
		DeleteUser.setBounds(364, 43, 93, 23);
		contentPane.add(DeleteUser);
		
		id = new JLabel("");
		id.setBounds(364, 79, 72, 18);
		contentPane.add(id);
		
		fresh = new JButton("\u5237\u65B0");
		fresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Adminstor.this.initial();
				Adminstor.this.id.setText("");
			}
		});
		fresh.setBounds(364, 213, 93, 27);
		contentPane.add(fresh);
		initial();
	}
	public void initial(){
		try {
			String result="";
			String str="select * from  user_acount where user_state<>3";
			Connection conn=DBHelper.getConnection();
			Statement statement=conn.createStatement();
			ResultSet rs=statement.executeQuery(str);
			
			
			rs.last();
			int k=rs.getRow();
			rs.first();
			Object cellData[][] = new Object[k][4];
			for (int i=0;i<k;i++) {
				String[] columnNames = { "用户账号", "用户密码", "用户类别", "选择" };
				cellData[i][0] = rs.getString(1);
				result+=rs.getString(1)+"\t";
				cellData[i][1] = rs.getString(2);
				result+=rs.getString(2)+"\t";
				switch(rs.getInt("user_state")){
				case 1:
					cellData[i][2]="前台";
					break;
				case 2:
					cellData[i][2]="会计";
					break;
				}
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
		if(table!=null){
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int row=table.rowAtPoint(e.getPoint());
					System.out.println(table.getValueAt(row, 0));
					id.setText((String)table.getValueAt(row, 0));
				}
			});
		}else{
			JOptionPane.showMessageDialog(null, "无账户");
		}
	}
	public void delete(){
		String result="";
		String str="";
		ResultSet rs;
		Statement statement;
		try {
			Connection conn=DBHelper.getConnection();
			statement=conn.createStatement();
			str="delete from user_acount where user_name='"+id.getText()+"'";
			int i=statement.executeUpdate(str);
//			System.out.println(str);
			i=statement.executeUpdate(str);
			JOptionPane.showMessageDialog(null, "删除成功");
			statement.close();
			initial();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			JOptionPane.showMessageDialog(null, "删除失败");
			e.printStackTrace();
		}
	}
}
