package servlet;

import java.io.IOException;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * ユニフォーム管理システムにおけるログイン機能に関する処理をおこなうサーブレットクラス
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";
		String cmd = "";
		User user = null;

		try {

			//画面からの入力情報を受け取るためのエンコードを設定
			request.setCharacterEncoding("UTF-8");

			// � login_id,password入力パラメータを取得する
			String userid = (String) request.getParameter("id");
			String password = (String) request.getParameter("password");

			// � UserDAOをインスタンス化し、ユーザー情報の検索をおこなう
			UserDAO userDaoObj = new UserDAO();
			user = userDaoObj.selectByUser(userid, password);

			// ユーザー情報のチェック
			if (user.getLogin_id() == null) {
				error = "入力データが間違っています。";
				cmd = "login";
				return;
			}

			//ユーザー情報がある場合
			if ((user.getLogin_id() != null) || (!(user.getLogin_id().equals("user0")))) {

				//取得したユーザーオブジェクトセッションにスコープにuserという名前で登録する
				HttpSession session = request.getSession();
				session.setAttribute("user", user);

				// クッキーに入力情報のuseridとpasswordを登録する（期限は5日間）
				Cookie login_idCookie = new Cookie("login_id", user.getLogin_id());
				login_idCookie.setMaxAge(60 * 60 * 24 * 5);
				response.addCookie(login_idCookie);

				Cookie passCookie = new Cookie("password", user.getPassword());
				passCookie.setMaxAge(60 * 60 * 24 * 5);
				response.addCookie(passCookie);

				//ユーザー情報がない場合login.jspにフォワードする
			} else if (cmd.equals("login")) {
				response.sendRedirect(request.getContextPath() + "/list"); //商品一覧に遷移
			}

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、ログイン出来ません。";
			cmd = "login";

		} finally {

			if (error.equals("")) {

				if (user.getAuthority() == 1) {
					response.sendRedirect(request.getContextPath() + "/orderedList"); //受注一覧に遷移
				
				} else {//エラーがない場合（正常ルート）menu.jspにフォワードする
				response.sendRedirect(request.getContextPath() + "/list");
				}
				
			} else {
				//エラーが有る場合（異常ルート）error.jspにフォワードする
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}