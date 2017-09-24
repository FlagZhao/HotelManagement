package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBHelper {
	private static final String driver = "com.mysql.jdbc.Driver";
	// localhostָ������Ҳ�����ñ���ip��ַ���棬3306ΪMySQL���ݿ��Ĭ�϶˿ںţ���user��ΪҪ���ӵ����ݿ���
	private static final String url = "jdbc:mysql://localhost:3306/hotel";
	
	// �������ݿ���û���������
	private static final String username = "root";
	private static final String password = "root";
	
	public static Connection getConnection() throws SQLException{
		Connection con = null;
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}// �����������򣬴˴�������ʽע����������ķ���
		try{
			con = DriverManager.getConnection(url, username, password);// �������Ӷ���
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
}
