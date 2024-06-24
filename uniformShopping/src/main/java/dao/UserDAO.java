package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.User;

/**
 *
 */
public class UserDAO {
	/**
	 * JDBCドライバ内部のDriverクラスパス
	 */
	private static final String RDB_DRIVE = "org.mariadb.jdbc.Driver";
	/**
	 * 接続するMySQLデータベースパス
	 */
	private static final String URL = "jdbc:mariadb://localhost/uniformdb";
	/**
	 * データベースのユーザー名
	 */
	private static final String USER = "root";
	/**
	 * データベースのパスワード
	 */
	private static final String PASSWD = "root123";

	/**
	 * フィールド変数のデータベース情報を基に、DB接続をおこなう
	 *
	 * @return データベース接続情報
	 * @throws IllegalStateException
	 *             メソッド内部で例外が発生した場合
	 */

	private static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(RDB_DRIVE);
			con = DriverManager.getConnection(URL, USER, PASSWD);
			return con;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * DBのユーザー情報を格納するuserinfoテーブから、引数として与えられたユーザーIDとパスワードと一致するユーザー情報を取得する
	 *
	 * @param user_id
	 *            ユーザーID
	 * @param password
	 *            パスワード
	 * @return ユーザー情報のオブジェクト<br>
	 *         ユーザー情報が見つからなかった場合は、オブジェクト内部の値が初期値の状態で返される
	 * @throws IllegalStateException
	 *             メソッド内部で例外が発生した場合
	 */
	public User selectByUser(String login_id, String password) {

		Connection con = null;
		Statement smt = null;

		//Userクラスのオブジェクト生成
		User user = new User();

		try {

			con = getConnection();
			smt = con.createStatement();

			//userinfoテーブルから特定のユーザーIDとパスワードを取得するためのSELECT文
			String sql = "SELECT * FROM userinfo WHERE login_id = '" + login_id + "' AND password = '" + password + "'";

			//DBからユーザー情報を取得
			ResultSet rs = smt.executeQuery(sql);

			if (rs.next()) {
				user.setUser_id(rs.getInt("user_id"));
				user.setLogin_id(rs.getString("login_id"));
				user.setPassword(rs.getString("password"));
				user.setAuthority(rs.getInt("authority"));
				user.setName(rs.getString("name"));
				user.setAddress(rs.getString("address"));
				user.setEmail(rs.getString("email"));
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

		//ユーザー情報を呼び出し元に返す
		return user;
	}

	/**
	 * DBの書籍情報を格納するuserinfoテーブルへ書籍情報を登録する
	 *
	 * @param user
	 *            登録する書籍情報のオブジェクト
	 * @throws IllegalStateException
	 *             メソッド内部で例外が発生した場合
	 */
	public void insert(User user) {

		Connection con = null;
		Statement smt = null;

		try {

			con = getConnection();
			smt = con.createStatement();

			String sql = "INSERT INTO userinfo VALUES(NULL,'"
					+ user.getLogin_id() + "','"
					+ user.getPassword() + "',"
					+ user.getAuthority() + ",'"
					+ user.getName() + "','"
					+ user.getAddress() + "','"
					+ user.getEmail() + "')";

			smt.executeUpdate(sql);

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
	}

	/**
	 * 引数のユーザー情報データを基にDBのユーザー情報を格納するuserinfoテーブルから概要ユーザー情報データの更新処理をおこなう
	 *
	 * @param user
	 *            更新する書籍情報のオブジェクト
	 * @throws IllegalStateException
	 *             メソッド内部で例外が発生した場合
	 */
	public void update(User user) {

		Connection con = null;
		Statement smt = null;

		try {

			con = getConnection();
			smt = con.createStatement();

			String sql = "UPDATE userinfo SET login_id = '"
						+user.getLogin_id()+"', password = '"
						+user.getPassword()+"', authority = "
						+user.getAuthority()+", name = '"
						+user.getName()+"', address = '"
						+user.getAddress()+"', email = '"
						+user.getEmail()+"' WHERE login_id = '"+user.getLogin_id()+"'";
			
			smt.executeUpdate(sql);

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
	}

	public User selectByEmail(String email) {

		Connection con = null;
		Statement smt = null;

		//Userクラスのオブジェクト生成
		User user = new User();

		try {

			con = getConnection();
			smt = con.createStatement();

			//userinfoテーブルからすべての情報を取得するためのSELECT文
			String sql = "SELECT * FROM userinfo WHERE email='" + email + "'";
			;

			//DBからユーザー情報を取得
			ResultSet rs = smt.executeQuery(sql);

			if (rs.next()) {
				user.setUser_id(rs.getInt("user_id"));
				user.setLogin_id(rs.getString("login_id"));
				user.setPassword(rs.getString("password"));
				user.setAuthority(rs.getInt("authority"));
				user.setName(rs.getString("name"));
				user.setAddress(rs.getString("address"));
				user.setEmail(rs.getString("email"));
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

		//ユーザー情報を呼び出し元に返す
		return user;

	}
}
