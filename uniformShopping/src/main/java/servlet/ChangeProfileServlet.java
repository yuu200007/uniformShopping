//会員情報変更機能

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

@WebServlet("/changeProfile")
public class ChangeProfileServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";
		String cmd = "";

		try {

			//DBアクセスを行うためのDAOオブジェクト生成
			UserDAO objDao = new UserDAO();

			//更新画面からの入力情報を受け取るためのエンコード
			request.setCharacterEncoding("UTF-8");

			//更新画面からの入力情報を受け取る
			String login_id = request.getParameter("id");
			String password = request.getParameter("password");
			int authority = Integer.parseInt(request.getParameter("authority"));
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String Email = request.getParameter("Email");

			//未入力エラー
			if (password.equals("")) {
				error = "パスワードが未入力の為、会員情報更新処理を行うことができませんでした。";
				cmd = "list";
				return;
			}

			if (name.equals("")) {
				error = "名前が未入力の為、会員情報更新処理を行うことができませんでした。";
				cmd = "list";
				return;
			}

			if (address.equals("")) {
				error = "住所が未入力の為、会員情報更新処理を行うことができませんでした。";
				cmd = "list";
				return;
			}

			if (Email.equals("")) {
				error = "メールアドレスが未入力の為、会員情報更新処理を行うことができませんでした。";
				cmd = "list";
				return;
			}

			//Userオブジェクト生成し、setter
			User user = new User();
			user.setLogin_id(login_id);
			user.setPassword(password);
			user.setAuthority(authority);
			user.setName(name);
			user.setAddress(address);
			user.setEmail(Email);

			objDao.update(user);
			
			//sessionに会員情報を更新
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、会員情報の更新は行えませんでした。";
			cmd = "list";
		} finally {
			if (cmd.equals("")) {
				response.sendRedirect(request.getContextPath() + "/list");
			} else {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
