package client.panels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import db.DBHelper;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class vip extends JPanel {
	private JTextField name;
	private JTextField id;
	private JTextField tel;

	/**
	 * Create the panel.
	 */
	public vip() {
		setLayout(null);
		
		JLabel label = new JLabel("\u59D3\u540D\uFF1A");
		label.setBounds(10, 10, 54, 21);
		add(label);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(95, 10, 66, 21);
		add(name);
		
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
		
		tel = new JTextField();
		tel.setColumns(10);
		tel.setBounds(95, 97, 177, 21);
		add(tel);
		
		JButton rigistVIP = new JButton("\u6CE8\u518C");
		rigistVIP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(vip.this.cleckIdNumber(vip.this.id.getText()))
					vip.this.rigist();
			}
		});
		rigistVIP.setBounds(95, 181, 93, 23);
		add(rigistVIP);

	}
	public void rigist(){
		try {
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
				str = "insert into customer values('" + id.getText() + "','" + name.getText() + "','" + tel.getText()
						+ "');";
				int statues = statement.executeUpdate(str);
				if (statues == 0)
					JOptionPane.showMessageDialog(null, "错误");
			}
			str = "insert into vip_list values('" + id.getText() + "')";
			int statue = statement.executeUpdate(str);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(new Date().getTime());
			str="insert into bill values('"+name.getText()+"注册会员','200','"+date+"')";;
			System.out.println(str);
			int i=statement.executeUpdate(str);
			JOptionPane.showMessageDialog(null, "注册成功");
		} catch (HeadlessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
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
}
