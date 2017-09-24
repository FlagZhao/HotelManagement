package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import db.DBHelper;
import javax.swing.JButton;
import javax.swing.*;
public class accountant extends JFrame {
	private TableColumn aColumn;
	private JTable table;
	private JTable table2;
	private JScrollPane scrollPane;
	private JPanel contentPane;
	private JScrollPane scrollPane2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					accountant frame = new accountant();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void initial(){
		try {
			String result="";
			String str="select * from  bill";
			Connection conn=DBHelper.getConnection();
			Statement statement=conn.createStatement();
			ResultSet rs=statement.executeQuery(str);
			
			
			rs.last();
			int k=rs.getRow();
			rs.first();
			Object cellData[][] = new Object[k][3];
			for (int i=0;i<k;i++) {
				String[] columnNames = { "条目", "金额", "时间"};
				cellData[i][0] = rs.getString(1);
				result+=rs.getString(1)+"\t";
				cellData[i][1] = rs.getString(2);
				result+=rs.getString(2)+"\t";
				cellData[i][2] = rs.getString(3);
				result+=rs.getString(3)+"\t";
				rs.next();
				System.out.println(result);
				table = new JTable(cellData, columnNames);
				scrollPane.setViewportView(table);
				
			}

			String result2="";
			String str2="select * from  company";
			rs=statement.executeQuery(str2);
			rs.last();
			k=rs.getRow();
			rs.first();
			Object cellData2[][] = new Object[k][2];
			for (int i=0;i<k;i++) {
				String[] columnNames2 = { "公司id", "金额"};
				cellData2[i][0] = rs.getString(1);
				result2+=rs.getString(1)+"\t";
				cellData2[i][1] = rs.getString(3);
				result2+=rs.getString(2)+"\t";
				rs.next();
				System.out.println(result2);
				table2 = new JTable(cellData2, columnNames2);
				scrollPane2.setViewportView(table2);
				
			}
			
			
			
			rs.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	/**
	 * Create the frame.
	 */
	public accountant() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 556, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 13, 510, 206);
		contentPane.add(scrollPane);
		
		JButton btnexcel = new JButton("\u5BFC\u51FA\u4E3Aexcel");
		btnexcel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File file=new File("财务报表原型.xls");
				File file2=new File("挂账公司账目报表原型.xls");
				try {
					exportTable(table, file);
					exportTable(table2, file2);
					
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "导出错误");
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		btnexcel.setBounds(159, 437, 199, 27);
		contentPane.add(btnexcel);
		
		scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(14, 218, 510, 206);
		contentPane.add(scrollPane2);
		initial();
	}
	public void exportTable(JTable table, File file) throws IOException
	{
	        TableModel model = table.getModel();
	        FileWriter out = new FileWriter(file);       
	        for(int i=0; i < model.getColumnCount(); i++) 
	        {
	            out.write(model.getColumnName(i) + "\t");
	        }
	        out.write("\n");
	        for(int i=0; i< model.getRowCount(); i++) 
	        {
	            for(int j=0; j < model.getColumnCount(); j++) 
	             {
	                out.write(model.getValueAt(i,j).toString()+"\t");
	             }
	            out.write("\n");
	        }
	        out.close();
	        JOptionPane.showMessageDialog(null,"文件导出成功");
	        Runtime.getRuntime().exec( "cmd   /c   start   "+file.getPath());       //打开生成的excel文件     
	    }
}
