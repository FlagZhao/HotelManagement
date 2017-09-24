package client.panels;

import javax.swing.JPanel;
import javax.swing.JTextField;

import client.CompanyCheckou;
import db.DBHelper;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JRadioButton;

public class checkout extends JPanel {
	private JTextField userid;
	private JLabel roomnum;
	private JLabel correnttime;
	private JLabel lblVip;
	private JLabel checkintime;
	private JLabel username;
	private JLabel vip;
	private JLabel label_4;
	private JLabel divicity;
	private JLabel amount;
	private JLabel money;
	private JRadioButton solo;
	private JRadioButton company;

	/**
	 * Create the panel.
	 */
	public checkout() {
		setLayout(null);
		
		userid = new JTextField();
		userid.setColumns(10);
		userid.setBounds(134, 43, 177, 21);
		add(userid);
		
		JLabel label = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7\u7801\uFF1A");
		label.setBounds(49, 43, 102, 21);
		add(label);
		
		JLabel label_1 = new JLabel("\u623F\u95F4\u53F7\uFF1A");
		label_1.setBounds(49, 139, 87, 28);
		add(label_1);
		
		roomnum = new JLabel("");
		roomnum.setBounds(134, 144, 124, 23);
		add(roomnum);
		
		JLabel time2 = new JLabel("\u5F53\u524D\u65F6\u95F4\uFF1A");
		time2.setBounds(49, 202, 102, 15);
		add(time2);
		
		correnttime = new JLabel("");
		correnttime.setBounds(134, 202, 177, 15);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date().getTime());
		correnttime.setText(date);
		add(correnttime);
		
		JButton button = new JButton("\u67E5\u8BE2");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				checkout.this.select();
			}
		});
		button.setBounds(49, 315, 93, 23);
		add(button);
		
		JButton button_1 = new JButton("\u9000\u623F");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(checkout.this.solo.isSelected())
					checkOutSolo();
				else
					new CompanyCheckou(Integer.parseInt(money.getText()));
			}
		});
		button_1.setBounds(248, 315, 93, 23);
		add(button_1);
		
		lblVip = new JLabel("VIP\uFF1A");
		lblVip.setBounds(49, 105, 87, 28);
		add(lblVip);
		
		vip = new JLabel("\u975E\u4F1A\u5458");
		vip.setBounds(134, 108, 124, 23);
		add(vip);
		
		JLabel label_5 = new JLabel("\u5165\u4F4F\u65F6\u95F4\uFF1A");
		label_5.setBounds(49, 177, 102, 15);
		add(label_5);
		
		checkintime = new JLabel("");
		checkintime.setBounds(134, 177, 177, 15);
		add(checkintime);
		
		JLabel label_3 = new JLabel("\u59D3\u540D\uFF1A");
		label_3.setBounds(49, 74, 102, 21);
		add(label_3);
		
		username = new JLabel("");
		username.setBounds(134, 80, 177, 15);
		add(username);
		
		divicity = new JLabel("");
		divicity.setBounds(134, 227, 177, 15);
		add(divicity);
		
		label_4 = new JLabel("\u62BC\u91D1\uFF1A");
		label_4.setBounds(49, 227, 102, 15);
		add(label_4);
		
		amount = new JLabel("\u5E94\u6536\uFF1A");
		amount.setBounds(49, 252, 102, 15);
		add(amount);
		
		money = new JLabel("");
		money.setBounds(134, 252, 177, 15);
		add(money);
		
		solo = new JRadioButton("\u666E\u901A\u7ED3\u8D26");
		solo.setBounds(49, 273, 121, 23);
		solo.setSelected(true);
		add(solo);
		
		company = new JRadioButton("\u6302\u5355\u7ED3\u8D26");
		company.setBounds(220, 273, 121, 23);
		add(company);
		
		ButtonGroup bg=new ButtonGroup();
		bg.add(solo);
		bg.add(company);
		
	}
	public void checkOutSolo(){
		String result="";
		String str="";
		ResultSet rs;
		Statement statement;
		try {
			Connection conn=DBHelper.getConnection();
			statement=conn.createStatement();
			str="delete from checkin where customer_id='"+userid.getText()+"'";
			int i=statement.executeUpdate(str);
			str="update room set room_state='空闲',custumer=NULL where room_num="+roomnum.getText();
			i=statement.executeUpdate(str);
			
			int count=Integer.parseInt(money.getText())+Integer.parseInt(divicity.getText());
			str="insert into bill values('"+roomnum.getText()+"出租','"+count+"','"+correnttime.getText()+"')";;
//			System.out.println(str);
			i=statement.executeUpdate(str);
			JOptionPane.showMessageDialog(null, "退房成功");
			refresh();
			statement.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	} 
	
	public void refresh(){
		userid.setText("");
		roomnum.setText("");
		correnttime.setText("");
		checkintime.setText("");
		username.setText("");
		vip.setText("非会员");
		divicity.setText("");
		money.setText("");
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
				str="select * from vip_list where customer_id='"+userid.getText()+"'";//查找vip情况
				rs=statement.executeQuery(str);
				if(rs.next()){
					vip.setText("会员");
				}
				str="select * from checkin where customer_id='"+userid.getText()+"'";//入住情况
				rs=statement.executeQuery(str);
				if(rs.next()){
					roomnum.setText(rs.getString(1));
					divicity.setText(rs.getString(3));
					checkintime.setText(rs.getString(4));
					str="select * from room where room_num='"+roomnum.getText()+"'";//查找房间价格
					rs=statement.executeQuery(str);
					rs.next();
					int price=Integer.parseInt(rs.getString(8));
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			        format.setLenient(false);
			        Date date1 = format.parse(checkintime.getText());
			        Date date2 = new Date();
			      //计算差值，天数
			        int days=(int) ((date2.getTime()-date1.getTime())/(1000*60*60*24))+1;
			        if(vip.getText().equals("会员"))
				        money.setText(""+(int)(days*price*0.8-Integer.parseInt(divicity.getText())));
			        else
				        money.setText(""+(days*price-Integer.parseInt(divicity.getText())));
				}
			}
			
			
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
