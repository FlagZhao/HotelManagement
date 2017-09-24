package client.panels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableColumn;

import db.DBHelper;
import javax.swing.JScrollPane;

public class Rooms extends JPanel {
	private TableColumn aColumn;
	private JTable table;
	private JScrollPane scrollPane;
	/**
	 * Create the panel.
	 */
	public Rooms() {
		setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 13, 611, 523);
		add(scrollPane);
		scrollPane.setViewportView(table);
		initialrooms();
	}
	public void initialrooms(){
		try {
			String result="";
			String str="select * from  room";
			Connection conn=DBHelper.getConnection();
			Statement statement=conn.createStatement();
			ResultSet rs=statement.executeQuery(str);
			
			
			rs.last();
			int k=rs.getRow();
			rs.first();
			Object cellData[][] = new Object[k][7];
			for (int i=0;i<k;i++) {
				String[] columnNames = { "房间号", "类型", "状态","窗户数目","楼层","押金","单价/晚上"};
				cellData[i][0] = rs.getString(1);
				result+=rs.getString(1)+"\t";
				cellData[i][1] = rs.getString(4);
				result+=rs.getString(4)+"\t";
				cellData[i][2] = rs.getString(5);
				result+=rs.getString(5)+"\t";
				cellData[i][3] = rs.getString(3);
				result+=rs.getString(3)+"\t";
				cellData[i][4] = rs.getString(2);
				result+=rs.getString(2)+"\t";
				cellData[i][5] = rs.getString(7);
				result+=rs.getString(7)+"\t";
				cellData[i][6] = rs.getString(8);
				result+=rs.getString(8)+"\n";
				rs.next();
				System.out.println(result);
				table = new JTable(cellData, columnNames);
				scrollPane.setViewportView(table);
			}
			rs.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

}
