package servlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.Item;
import bean.Order;
import bean.User;
import dao.ItemDAO;
import dao.OrderDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.SendMail;

@WebServlet("/orderConfirm")

public class OrderConfirmServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";
		String cmd = "";

		String name = "";
		String Email = "";
		String address = "";
		String remarks = "";

		try {
			name = request.getParameter("name");
			Email = request.getParameter("Email");
			address = request.getParameter("address");
			remarks = request.getParameter("remarks");

			if (name == "") {
				error = "氏名が未入力の為、注文は行えません。";
				cmd = "orderConfirm";
				return;
			}

			if (Email == "") {
				error = "メールアドレスが未入力の為、注文は行えません。";
				cmd = "orderConfirm";
				return;
			}

			if (address == "") {
				error = "住所が未入力の為、注文は行えません。";
				cmd = "orderConfirm";
				return;
			}

			//セッションから"user"のUserオブジェクトを取得する
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			if (user == null) {
				error = "セッション切れの為、注文は行えません。";
				cmd = "list";
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}

			String ForCheckEmail = user.getEmail();
			UserDAO objUserdao = new UserDAO();
			User CheckUser = new User();
			CheckUser = objUserdao.selectByEmail(ForCheckEmail);
			String ForCheckEmail2 = CheckUser.getEmail();

			//セッションから"order_list"のList配列を取得する。
			ArrayList<Order> order_list = (ArrayList<Order>) session.getAttribute("order_list");
			ArrayList<Order> _order_list = new ArrayList<Order>();
			int total = 0;

			//メール本文の合計値用変数
			if (order_list == null) {
				error = "カートの中に何も無かったので注文は行えません。";
				cmd = "list";
				return;

			}

			for (Order order : order_list) {
				ItemDAO itemDao = new ItemDAO();
				Item itemStock = itemDao.selectByItemId(order.getItem_id());
				if (order.getQuantity() > itemStock.getStock()) {
					error = "在庫が不足しているため、注文は行えません。";
					cmd = "list";
					request.setAttribute("error", error);
					request.setAttribute("cmd", cmd);
					request.getRequestDispatcher("/view/error.jsp").forward(request, response);
					return;
				}

			}

			if ((ForCheckEmail != null) && (ForCheckEmail.equals(ForCheckEmail2))) {

				for (int i = 0; i < order_list.size(); i++) {
					Order order = new Order();
					OrderDAO objOrderdao = new OrderDAO();
					order = order_list.get(i);
					order.setBuyer(CheckUser.getLogin_id());
					order.setPay_status(1);
					order.setDeli_status(1);
					_order_list.add(order);
					objOrderdao.insert(order);
					objOrderdao.updateStock(order);
					total += ((order.getPrice()) * (order_list.get(i).getQuantity()));
				}

			}

			if (user.getLogin_id().equals("user0")) {

				for (int i = 0; i < order_list.size(); i++) {
					Order order = new Order();
					OrderDAO objOrderdao = new OrderDAO();
					order = order_list.get(i);
					order.setBuyer(user.getLogin_id());
					order.setPay_status(1);
					order.setDeli_status(1);
					_order_list.add(order);
					objOrderdao.insert(order);
					objOrderdao.updateStock(order);
					total += ((order.getPrice()) * (order_list.get(i).getQuantity()));
				}

			}

			//ここにSendMailメソッドを追加
			SendMail mail = new SendMail();
			mail.MailText(user, order_list, total);

			request.setAttribute("order_list", null);
			request.setAttribute("_order_list", _order_list);

			//セッション情報の削除
			session.removeAttribute("order_list");

			//セッション情報の削除
			session.removeAttribute("order_list");

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、注文は行えません。";
			cmd = "list";

		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>" + e;
			cmd = "list";

		} finally {

			if (error != "") {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			} else {

				request.getRequestDispatcher("/view/buyConfirm.jsp").forward(request, response);
			}

		}
	}

}