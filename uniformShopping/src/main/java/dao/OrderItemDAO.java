package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.OrderItem;

public class OrderItemDAO {
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
			throw new IllegalStateException(e); // エラー文の表示
		}
	}

	//佐々木修正分
	public ArrayList<OrderItem> selectAll() {

		Connection con = null;
		Statement smt = null;

		//return用オブジェクトの生成
		ArrayList<OrderItem> OrderItemList = new ArrayList<OrderItem>();

		//SQL文
		String sql = "SELECT o.trans_id, o.buyer, o.item_id, i.item_name, i.image, i.price, o.quantity, o.pay_status, o.deli_status, o.pur_date FROM orderinfo o, iteminfo i WHERE o.item_id = i.item_id ORDER BY o.trans_id DESC";

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQLをDBへ発行
			ResultSet rs = smt.executeQuery(sql);

			//検索結果を配列に格納
			while (rs.next()) {
				OrderItem ordereditem = new OrderItem();
				ordereditem.setTrans_id(rs.getInt("trans_id"));
				ordereditem.setBuyer(rs.getString("buyer"));
				ordereditem.setItem_id(rs.getInt("item_id"));
				ordereditem.setItem_name(rs.getString("item_name"));
				ordereditem.setImage(rs.getString("image"));
				ordereditem.setPrice(rs.getInt("price"));
				ordereditem.setQuantity(rs.getInt("quantity"));
				ordereditem.setPay_status(rs.getInt("pay_status"));
				ordereditem.setDeli_status(rs.getInt("deli_status"));
				ordereditem.setPur_date(rs.getString("pur_date"));
				OrderItemList.add(ordereditem);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return OrderItemList;
	}

	public ArrayList<OrderItem> selectByUser(String login_id) {

		Connection con = null;
		Statement smt = null;

		//return用オブジェクトの生成
		ArrayList<OrderItem> OrderItemList = new ArrayList<OrderItem>();

		//SQL文
		String sql = "SELECT o.buyer , i.item_name , i.price , o.quantity, o.pur_date FROM iteminfo i, orderinfo o"
				+ " WHERE i.item_id = o.item_id and o.buyer = '"+login_id+"'";

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQLをDBへ発行
			ResultSet rs = smt.executeQuery(sql);

			//検索結果を配列に格納
			while (rs.next()) {
				OrderItem ordereditem = new OrderItem();
				ordereditem.setBuyer(rs.getString("buyer"));
				ordereditem.setItem_name(rs.getString("item_name"));
				ordereditem.setPrice(rs.getInt("price"));
				ordereditem.setQuantity(rs.getInt("quantity"));
				ordereditem.setPur_date(rs.getString("pur_date"));
				OrderItemList.add(ordereditem);

			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return OrderItemList;
	}

	//佐々木修正分
	public ArrayList<OrderItem> selectBySales(String year, String month) {

		Connection con = null;
		Statement smt = null;

		//return用オブジェクトの生成
		ArrayList<OrderItem> OrderItemList = new ArrayList<OrderItem>();

		//一桁月に0を追記
		if (Integer.parseInt(month) < 10) {
			month = "0" + month;
		}

		//SQL文
		String sql = "SELECT o.trans_id, o.buyer, o.item_id, i.item_name, i.image, i.price, o.quantity, o.pay_status, o.deli_status, o.pur_date FROM orderinfo o, iteminfo i WHERE o.item_id = i.item_id AND o.pur_date LIKE '"
				+ year + "-" + month + "%' ORDER BY pur_date DESC";

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQLをDBへ発行
			ResultSet rs = smt.executeQuery(sql);

			//検索結果を配列に格納
			while (rs.next()) {
				OrderItem ordereditem = new OrderItem();
				ordereditem.setTrans_id(rs.getInt("trans_id"));
				ordereditem.setBuyer(rs.getString("buyer"));
				ordereditem.setItem_id(rs.getInt("item_id"));
				ordereditem.setItem_name(rs.getString("item_name"));
				ordereditem.setImage(rs.getString("image"));
				ordereditem.setPrice(rs.getInt("price"));
				ordereditem.setQuantity(rs.getInt("quantity"));
				ordereditem.setPay_status(rs.getInt("pay_status"));
				ordereditem.setDeli_status(rs.getInt("deli_status"));
				ordereditem.setPur_date(rs.getString("pur_date"));
				OrderItemList.add(ordereditem);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return OrderItemList;
	}

	public ArrayList<OrderItem> thisAndLastMonthSales(int year, int month) {

		Connection con = null;
		Statement smt = null;
		String sql = "";
		//return用オブジェクトの生成
		ArrayList<OrderItem> OrderItemList = new ArrayList<OrderItem>();

		//SQL文
		if (month == 1) {
			sql = "SELECT o.trans_id, o.buyer, o.item_id, o.quantity, i.price, o.pay_status, "
					+ "o.deli_status, o.pur_date FROM orderinfo o, iteminfo i WHERE date BETWEEN '" + year + "-" + month
					+ "' AND '" + (year - 1) + "-" + 12 + "' ORDER BY pur_date DESC";
		} else {
			sql = "SELECT trans_id, buyer, item_id, quantity, pay_status, "
					+ "deli_status, pur_date FROM orderinfo WHERE date BETWEEN '" + year + "-" + month + "' AND '"
					+ year + "-" + (month - 1) + "' ORDER BY pur_date DESC";
		}

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQLをDBへ発行
			ResultSet rs = smt.executeQuery(sql);

			//検索結果を配列に格納
			while (rs.next()) {
				OrderItem ordereditem = new OrderItem();
				ordereditem.setTrans_id(rs.getInt("trans_id"));
				ordereditem.setBuyer(rs.getString("buyer"));
				ordereditem.setQuantity(rs.getInt("quantity"));
				ordereditem.setPay_status(rs.getInt("pay_status"));
				ordereditem.setDeli_status(rs.getInt("deli_status"));
				ordereditem.setPur_date(rs.getString("pur_date"));
				OrderItemList.add(ordereditem);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return OrderItemList;
	}

	public OrderItem selectByTrans_id(int trans_id) {

		Connection con = null;
		Statement smt = null;

		//return用オブジェクトの生成
		OrderItem ordereditem = null;

		//SQL文
		String sql = "SELECT o.trans_id,i.item_name,i.image,i.price,o.buyer,o.item_id,o.quantity,o.pur_date,o.pay_status,o.deli_status FROM iteminfo i,orderinfo o"
				+ " WHERE i.item_id=o.item_id AND o.trans_id='" + trans_id + "'";

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQLをDBへ発行
			ResultSet rs = smt.executeQuery(sql);

			//検索結果を配列に格納
			while (rs.next()) {
				ordereditem = new OrderItem();
				ordereditem.setTrans_id(rs.getInt("trans_id"));
				ordereditem.setItem_name(rs.getString("item_name"));
				ordereditem.setImage(rs.getString("image"));
				ordereditem.setQuantity(rs.getInt("quantity"));
				ordereditem.setPur_date(rs.getString("pur_date"));
				ordereditem.setPay_status(rs.getInt("pay_status"));
				ordereditem.setDeli_status(rs.getInt("deli_status"));
				ordereditem.setBuyer(rs.getString("buyer"));
				ordereditem.setPrice(rs.getInt("price"));
			}

		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return ordereditem;
	}

	//佐々木追加分
	public int selectByTotalPrice(String year, String month) {

		Connection con = null;
		Statement smt = null;

		//return用オブジェクトの生成
		int totalPrice = 0;

		//一桁月に0を追記
		if (Integer.parseInt(month) < 10) {
			month = "0" + month;
		}

		//SQL文
		String sql = "SELECT SUM(o.quantity * i.price) AS 'totalPrice' FROM orderinfo o, iteminfo i WHERE o.item_id = i.item_id AND o.deli_status = 3  AND o.pur_date LIKE '"
				+ year + "-" + month + "%' ORDER BY pur_date DESC";

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQLをDBへ発行
			ResultSet rs = smt.executeQuery(sql);

			//検索結果を配列に格納
			if (rs.next()) {
				totalPrice = rs.getInt("totalPrice");
			}

		} catch (Exception e) {
			System.out.println(e);
			throw new IllegalStateException(e);

		} finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return totalPrice;
	}

	public ArrayList<OrderItem> selectByBuyerAndDate(String buyer, String purDate) {

		Connection con = null;
		Statement smt = null;

		//return用オブジェクトの生成
		ArrayList<OrderItem> OrderItemList = new ArrayList<OrderItem>();

		String sql = "SELECT o.trans_id, o.buyer, o.item_id, i.item_name, i.image, i.price, o.quantity, o.pay_status, o.deli_status, o.pur_date FROM orderinfo o, iteminfo i WHERE o.item_id = i.item_id AND o.buyer = '"
				+ buyer + "' AND o.pur_date LIKE '" + purDate + "' ORDER BY pur_date DESC";

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQLをDBへ発行
			ResultSet rs = smt.executeQuery(sql);

			//検索結果を配列に格納
			while (rs.next()) {
				OrderItem ordereditem = new OrderItem();
				ordereditem.setTrans_id(rs.getInt("trans_id"));
				ordereditem.setBuyer(rs.getString("buyer"));
				ordereditem.setItem_id(rs.getInt("item_id"));
				ordereditem.setItem_name(rs.getString("item_name"));
				ordereditem.setImage(rs.getString("image"));
				ordereditem.setPrice(rs.getInt("price"));
				ordereditem.setQuantity(rs.getInt("quantity"));
				ordereditem.setPay_status(rs.getInt("pay_status"));
				ordereditem.setDeli_status(rs.getInt("deli_status"));
				ordereditem.setPur_date(rs.getString("pur_date"));
				OrderItemList.add(ordereditem);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return OrderItemList;
	}

}