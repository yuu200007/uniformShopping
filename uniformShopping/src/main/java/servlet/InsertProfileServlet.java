package servlet;

import java.io.IOException;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/insertProfile")
public class InsertProfileServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";
		String cmd = "";

		try {
			//入力データの文字コードの指定
			request.setCharacterEncoding("UTF-8");

			//ユーザー会員登録する入力パラメーターを取得する
			String login_id = request.getParameter("id");
			String password = request.getParameter("password");
			int authority = Integer.parseInt(request.getParameter("authority"));
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String email = request.getParameter("Email");
			
			//UserDAOオブジェクトの生成
			UserDAO userDao = new UserDAO();

			//未入力エラー
			if (login_id.equals("")) {
				error = "ユーザーIDが未入力の為、会員情報登録処理を行うことができませんでした。";
				cmd = "insertProfile";
				return;
			}
			
			if (password.equals("")) {
				error = "パスワードが未入力の為、会員情報登録処理を行うことができませんでした。";
				cmd = "insertProfile";
				return;
			}

			if (name.equals("")) {
				error = "名前が未入力の為、会員情報登録処理を行うことができませんでした。";
				cmd = "insertProfile";
				return;
			}

			if (address.equals("")) {
				error = "住所が未入力の為、会員情報登録処理を行うことができませんでした。";
				cmd = "insertProfile";
				return;
			}

			if (email.equals("")) {
				error = "メールアドレスが未入力の為、会員情報登録処理を行うことができませんでした。";
				cmd = "insertProfile";
				return;
			}
			
			if (userDao.searchUser(login_id) != null) { 
				error = "ログインIDが既に存在する為、会員情報登録処理を行うことができませんでした。";
				cmd = "insertProfile";
				return;
			}

			//Userオブジェクトに格納する
			User user = new User();
			user.setLogin_id(login_id);
			user.setPassword(password);
			user.setAuthority(authority);
			user.setName(name);
			user.setAddress(address);
			user.setEmail(email);

			//UserDAOをインスタンス化、insertメソッドを呼び出す
			UserDAO objDao = new UserDAO();
			objDao.insert(user);

			//sessionに会員情報を登録
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、会員登録は行えませんでした。";
			cmd = "list";

		} finally {
			//④ エラーの有無でフォワード先を呼び分ける
			if (error.equals("")) {
				//エラーがない場合はinsertProfile.jspにフォワード
				response.sendRedirect(request.getContextPath() + "/list");
			} else {
				// エラーが有る場合はerror.jspにフォワード
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}