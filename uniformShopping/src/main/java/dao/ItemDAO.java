package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.Item;

public class ItemDAO {
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

    /**
     * 商品一覧取得メソッド
     * @return
     */
    public ArrayList<Item> selectAll() {

        Connection con = null;
        Statement smt = null;

        ArrayList<Item> itemList = new ArrayList<>(); //商品一覧情報を格納する配列
        Item item = null;
        //データベース上から商品情報を取得するSQL文
        String sql = "SELECT item_id,item_name,price,stock,image FROM iteminfo ORDER BY item_name";

        try {
            con = getConnection(); //データベース接続
            smt = con.createStatement(); //createStatementのオブジェクト化
            ResultSet rs = smt.executeQuery(sql); //SQL文の結果

            //取得した商品一覧情報を配列に格納する
            while (rs.next()) {
                item = new Item(); //Bookのオブジェクト化
                item.setItemId(rs.getInt("item_id")); //商品IDのセット
                item.setItemName(rs.getString("item_name")); //商品名のセット
                item.setPrice(rs.getInt("price")); //価格のセット
                item.setStock(rs.getInt("stock")); //在庫数のセット
                item.setImage(rs.getString("image")); //画像のセット
                itemList.add(item); //配列に格納
            }

        } catch (Exception e) {
            throw new IllegalStateException(e); //エラーメッセージの表示
        } finally {
            if (smt != null) {
                try {
                    smt.close();
                } catch (SQLException ignore) { //Statementのクローズ
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ignore) { //Connectionのクローズ
                }
            }
        }
        return itemList;
    }

    /**
     * 新商品登録メソッド
     * @param item
     */
    public void insert(Item item) {

        Connection con = null;
        Statement smt = null;
        //データベース上に商品情報を登録するSQL文
        String sql = "INSERT INTO iteminfo(item_name,price,stock,image) VALUES('" + item.getItemName() + "',"
                + item.getPrice() + ","
                + item.getStock() + ",'" + item.getImage() + "')";

        try {
            con = getConnection(); //データベース接続
            smt = con.createStatement(); //createStatementのオブジェクト化
            smt.executeUpdate(sql); //SQL文の発行、新規商品情報の登録

        } catch (Exception e) {
            throw new IllegalStateException(e); //エラーメッセージの表示
        } finally {
            if (smt != null) {
                try {
                    smt.close();
                } catch (SQLException ignore) { //Statementのクローズ
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

    /**
     * 商品削除メソッド
     * @param itemID
     */
    public void delete(int itemId) {

        Connection con = null;
        Statement smt = null;

        //商品削除のSQL文
        String sql = "DELETE FROM iteminfo WHERE item_id = '" + itemId + "'";

        try {
            con = getConnection(); //データベース接続
            smt = con.createStatement(); //createStatementのオブジェクト化
            smt.executeUpdate(sql); //SQL文の発行、商品情報の削除

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

    /**
     * 商品情報更新メソッド
     * @param item
     */
    public void update(Item item) {

        Connection con = null;
        Statement smt = null;

        //書籍情報更新のSQL文
        String sql = "UPDATE iteminfo SET item_name='" + item.getItemName() + "',price=" + item.getPrice() + ",stock="
                + item.getStock() + ",image='" + item.getImage() + "' WHERE item_id=" + item.getItemId();

        try {
            con = getConnection(); //データベース接続
            smt = con.createStatement(); //createStatementのオブジェクト化
            smt.executeUpdate(sql); //SQL文の発行、書籍情報の更新

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

    /* 書籍データの検索 */
    public Item selectByItemId(int itemId) {

        Connection con = null;
        Statement smt = null;

        Item item = null; //Itemオブジェクトの宣言
        //ItemIdによる商品情報検索のSQL文
        String sql = "SELECT item_id,item_name,price,stock,image FROM iteminfo WHERE item_id = '" + itemId + "'";

        try {
            con = getConnection(); //データベース接続
            smt = con.createStatement(); //createStatementのオブジェクト化
            ResultSet rs = smt.executeQuery(sql); //SQL文の発行、商品情報検索結果の取得

            //取得した商品情報を配列に格納する
            while (rs.next()) {
                item = new Item(); //Bookのオブジェクト化
                item.setItemId(rs.getInt("item_id")); //商品IDのセット
                item.setItemName(rs.getString("item_name")); //商品名のセット
                item.setPrice(rs.getInt("price")); //価格のセット
                item.setStock(rs.getInt("stock")); //在庫数のセット
                item.setImage(rs.getString("image")); //画像のセット
            }

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
        return item;
    }

}
