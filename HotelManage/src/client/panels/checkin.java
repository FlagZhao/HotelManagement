package client.panels;

import javax.swing.JPanel;

import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import db.DBHelper;

import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;

public class checkin extends JPanel {
	private JTextField name;
	private JTextField id;
	private JTextField telphone;
	private TableColumn aColumn;
	private JTable table;
	private JScrollPane scrollPane;
	private JTable table_1;
	private JLabel roomnum;
	/**
	 * Create the panel.
	 */
	public checkin() {
		setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(281, 10, 300, 229);
		scrollPane.setVisible(true);
		add(scrollPane);
		
		
		scrollPane.setViewportView(table);
		
		JLabel label = new JLabel("\u59D3\u540D\uFF1A");
		label.setBounds(10, 10, 54, 21);
		add(label);
		
		name = new JTextField();
		name.setBounds(95, 10, 66, 21);
		add(name);
		name.setColumns(10);
		
		JLabel label_1 = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7\u7801\uFF1A");
		label_1.setBounds(10, 54, 102, 21);
		add(label_1);
		
		id = new JTextField();
		id.setColumns(10);
		id.setBounds(95, 54, 177, 21);
		add(id);
		
		JLabel label_2 = new JLabel("\u8054\u7CFB\u7535\u8BDD\uFF1A");
		label_2.setBounds(10, 97, 87, 21);
		add(label_2);
		
		telphone = new JTextField();
		telphone.setColumns(10);
		telphone.setBounds(95, 97, 177, 21);
		add(telphone);
		
		JLabel label_3 = new JLabel("\u623F\u95F4\u53F7\uFF1A");
		label_3.setBounds(10, 131, 87, 28);
		add(label_3);
		
		JButton checkin = new JButton("\u5165\u4F4F");
		checkin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(cleckIdNumber(id.getText())){
					try {
						checkin.this.checkIn();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "数据库错误");
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "身份证错误");
				}
			}
		});
		checkin.setBounds(126, 216, 93, 23);
		add(checkin);
		
		JLabel label_4 = new JLabel("\u5F53\u524D\u65F6\u95F4\uFF1A");
		label_4.setBounds(10, 179, 102, 15);
		add(label_4);
		
		JLabel Time = new JLabel("");
		Time.setBounds(126, 179, 177, 15);
		add(Time);
		Format format = new SimpleDateFormat("yyyyMMdd");
		Time.setText(format.format(new Date()));
		
		roomnum = new JLabel("");
		roomnum.setBounds(95, 136, 124, 23);
		add(roomnum);
		initial();
		
	}
	
	public void checkIn()throws SQLException{
		String result="";
		String str="";
		ResultSet rs;
		Statement statement;
		Connection conn = DBHelper.getConnection();
		statement = conn.createStatement();
		
		boolean flag=false;
		str="select * from customer where customer_id='"+id.getText()+"'";
		rs=statement.executeQuery(str);
		rs.last();
		flag=rs.getRow()==0?true:false;
		
		
		if (flag) {
			str = "insert into customer values('" + id.getText() + "','" + name.getText() + "','" + telphone.getText()
					+ "');";
			int statues = statement.executeUpdate(str);
			if (statues == 0)
				JOptionPane.showMessageDialog(null, "id错误");
		}
		str="select * from room where room_num='"+roomnum.getText()+"'";
		rs=statement.executeQuery(str);
		rs.next();
		String devicity=rs.getString(7);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date().getTime());
		str="insert into checkin values('"+roomnum.getText()+"','"+id.getText()+"','"+devicity+"','"+date+"');";
		int statues=statement.executeUpdate(str);
		str="update room set room_state='已住',custumer='"+id.getText()+"'where room_num='"+roomnum.getText()+"'";
		statues=statement.executeUpdate(str);
		statement.close();
		rs.close();
		JOptionPane.showMessageDialog(null, "入住成功");
		refresh();
	}
	public void refresh(){
		name.setText("");
		id.setText("");
		telphone.setText("");
		roomnum.setText("");
		initial();
		repaint();
	}
    public boolean cleckIdNumber(String ID){
    	String[]  validtable =  {"1","0","X","9","8","7","6","5","4","3","2"};
        boolean flag = false;
        //验证码
        String validatecode = ID.substring(17,18);
        //前17位称为本体码
        String selfcode = ID.substring(0,17);
        String code[]=new String[17];
        for(int i=0;i<17;i++){
            code[i] = selfcode.substring(i,i+1);
        }
        //加权因子公式：2的n-1次幂除以11取余数，n就是那个i，从右向左排列。
        int sum = 0;   //用于加权数求和
        for(int i=0;i<code.length;i++){
            //计算该位加权因子
            int yi = adjustmentfactor(i+1)%11;
            //得到对应数位上的数字
            int count = Integer.parseInt(code[code.length-i-1]);
            //加权求和 
            sum +=(count*yi);
        }
        //验证校验码是否正确
        String valdate = validtable[sum%11];
        if(valdate.equalsIgnoreCase(validatecode)) {
            flag = true;
        }
        return flag;
    }
    public int adjustmentfactor(int digit){
        int sum = 1;
        for(int i=0;i<digit;i++){
            //sum=sum*2;
            sum=sum<<1;
        }
        return sum;
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
				String[] columnNames = { "房间号码", "房间类型", "单价", "预定" };
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
				roomnum.setText((String)table.getValueAt(row, 0));
			}
		});
	}
}
