/*
 * ItemDAOのインスタンス化
 */
package servlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.Item;
import bean.User;
import dao.ItemDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/list")
public class ListServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String error = null; //エラーメッセージ	格納変数
		String cmd = "list"; //画面遷移の場所情報

		try {

			ItemDAO ItemDao = new ItemDAO(); //ItemDAOのオブジェクト化
			ArrayList<Item> list = ItemDao.selectAll(); //一覧格納変数

			//リクエストスコープに登録
			request.setAttribute("item_list", list);

			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			UserDAO userDao = new UserDAO();
			if (user == null) {
				user = userDao.selectByUser("user0", "user0");
				session.setAttribute("user", user);
			}

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
