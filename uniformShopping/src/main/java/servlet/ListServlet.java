/*
 * ItemDAOのインスタンス化
 */
package servlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.Item;
import dao.ItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/list")
public class ListServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String error = null; //エラーメッセージ	格納変数
		String cmd = "logout"; //画面遷移の場所情報

		try {

			ItemDAO ItemDao = new ItemDAO(); //ItemDAOのオブジェクト化
			ArrayList<Item> list = ItemDao.selectAll(); //一覧格納変数

			//リクエストスコープに登録
			request.setAttribute("item_list", list);

		} catch (Exception e) { //DB未接続エラー
			error = "DB接続エラーの為、一覧表示は行えませんでした。";

		} finally { //遷移先の指定
			if (error == null) {
				request.getRequestDispatcher("/view/list.jsp").forward(request, response); //書籍一覧画面に遷移
			} else {
				request.setAttribute("error", error); //エラーメッセージのセット
				request.setAttribute("cmd", cmd); //エラー画面からの遷移先のセット
				request.getRequestDispatcher("/view/error.jsp").forward(request, response); // エラー画面に遷移
			}
		}
	}
}
