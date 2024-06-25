package servlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.Item;
import bean.Order;
import bean.User;
import dao.ItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/insertIntoCart")

public class InsertIntoCartServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";
		String cmd = "";

		try {
			//①セッションから"user"のUserオブジェクトを取得する
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			if (user == null) {
				error = "セッション切れの為、カートに追加出来ません。";
				cmd = "list";
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}

			//ItemとItemDAOのオブジェクト生成
			ItemDAO objItemdao = new ItemDAO();
			Item item = new Item();

			//item_idとquantityのパラメータを取得する。
			String delno = request.getParameter("delno");

			//セッションから"order_list"のList配列を取得する。(取得出来なかった場合はArrayList<Order>配列を新規で作成する)
			ArrayList<Order> order_list = (ArrayList<Order>) session.getAttribute("order_list");
			if (order_list == null) {
				order_list = new ArrayList<Order>();
			}

			if (delno != null) {
				order_list.remove(Integer.parseInt(delno));
				return;
			}

			String item_id = request.getParameter("item_id");
			String quantity = request.getParameter("quantity");

			item = objItemdao.selectByItemId(Integer.parseInt(item_id));

			//item_idを引数に商品名と価格を取得
			String item_name = item.getItemName();
			int price = item.getPrice();

			if ((quantity.equals("")) || (Integer.parseInt(quantity) <= 0)) {
				error = "購入数が不正のため、カートに追加できません。";
				cmd = "list";
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			Order order = new Order();

			order.setItem_id(Integer.parseInt(item_id));
			order.setItem_name(item_name);
			order.setQuantity(Integer.parseInt(quantity));
			order.setPrice(price);

			for (int i = 0; i < order_list.size(); i++) {
				if (Integer.parseInt(item_id) == order_list.get(i).getItem_id()) {
					order.setQuantity((Integer.parseInt(quantity) + order_list.get(i).getQuantity()));
					order_list.remove(i);
				}
			}
			
			ItemDAO itemDao = new ItemDAO();
			Item itemStock = itemDao.selectByItemId(Integer.parseInt(item_id));
			if (order.getQuantity() > itemStock.getStock()) {
				error = "購入数が不正のため、カートに追加できません。";
				cmd = "list";
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			order_list.add(order);

			session.setAttribute("order_list", order_list);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、カートに追加は出来ません。";
			cmd = "list";

		} catch (NumberFormatException e) {
			error = "値が不正です。";
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
				//⑤「list.jsp」へフォワード
				request.getRequestDispatcher("/view/showCart.jsp").forward(request, response);
			}

		}
	}

}