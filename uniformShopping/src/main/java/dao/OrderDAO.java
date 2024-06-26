package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.Order;

public class OrderDAO {

	//データベース接続情報
	private static String RDB_DRIVE = "org.mariadb.jdbc.Driver";
	private static String URL = "jdbc:mariadb://localhost/uniformdb";
	private static String USER = "root";
	private static String PASS = "root123";

	private static Connection getConnection() {
		try {
			Class.forName(RDB_DRIVE);
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			return con;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	
	public void insert(Order order) {
		
		Connection con = null;
		Statement smt = null;
		
		//SQL文
		String sql = "INSERT INTO orderinfo VALUES(NULL,'"
				+ order.getBuyer()  + "',"
				+ order.getItem_id() + ","
				+ order.getQuantity() + ","
				+ order.getPay_status() + ","
				+ order.getDeli_status()
				+ ", NOW())";
		try {
			con = getConnection();
			smt = con.createStatement();

			//SQLをDBへ発行
			smt.executeUpdate(sql);

		} catch (Exception e) {
			throw new IllegalStateException(e);
			
		} finally {
			//リソースの開放
			if (smt != null) {
				try {smt.close();} catch (SQLException ignore) {}
			}
			if (con != null) {
				try {con.close();} catch (SQLException ignore) {}
			}
		}
	}
	
	
	public ArrayList<Order> selectAll(){
		
		Connection con = null;
		Statement smt = null;

		ArrayList<Order> orderList = new ArrayList<Order>();

		try {
			//データベースへ接続する
			con = getConnection();
			//データ操作の準備
			smt = con.createStatement();

			//検索結果を並び替えるSQL文
			String sql = "SELECT * FROM orderinfo ORDER BY trans_id";
			
			//検索系のクエリ
			ResultSet rs = smt.executeQuery(sql);
			
			//ArrayListにOrderオブジェクトを付け足していく
			while (rs.next()) {
				Order order = new Order();
				order.setTrans_id(rs.getInt("trans_id"));
				order.setBuyer(rs.getString("buyer"));
				order.setItem_id(rs.getInt("item_id"));
				order.setQuantity(rs.getInt("quantity"));
				order.setPay_status(rs.getInt("pay_status"));
				order.setDeli_status(rs.getInt("deli_status"));
				order.setPur_date(rs.getString("pur_date"));
				orderList.add(order);
			}
			
		}catch (Exception e) {
				throw new IllegalStateException(e);
		} finally {
			if (smt != null) {
				//Statementオブジェクトをクローズ
				try {smt.close();} catch (SQLException ignore) {}
			}
			if (con != null) {
				//Connectionオブジェクトをクローズ
				try {con.close();} catch (SQLException ignore) {}
			}
		}
		return orderList;
	}
	

	public void updateStatus(Order order) {

		Connection con = null;
		Statement smt = null;

		//注文情報更新のSQL文
		String sql = "UPDATE orderinfo SET pay_status=" + order.getPay_status() + ",deli_status=" + order.getDeli_status() + " WHERE trans_id=" + order.getTrans_id();

		try {
			con = getConnection(); //データベース接続
			smt = con.createStatement(); //createStatementのオブジェクト化
			smt.executeUpdate(sql); //SQL文の発行、入金・発送情報の更新

		} catch (Exception e) {
			throw new IllegalStateException(e); //エラーメッセージの表示
		} finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {//Statementのクローズ
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) { //Connectionのクローズ
				}
			}
		}
	}
	
	public void updateStock(Order order) {

		Connection con = null;
		Statement smt = null;

		//注文情報更新のSQL文
		String sql = "UPDATE iteminfo SET stock = stock - "+order.getQuantity()+" WHERE item_id = "+order.getItem_id();

		try {
			con = getConnection(); //データベース接続
			smt = con.createStatement(); //createStatementのオブジェクト化
			smt.executeUpdate(sql); //SQL文の発行、入金・発送情報の更新

		} catch (Exception e) {
			throw new IllegalStateException(e); //エラーメッセージの表示
		} finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {//Statementのクローズ
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) { //Connectionのクローズ
				}
			}
		}
	}

}