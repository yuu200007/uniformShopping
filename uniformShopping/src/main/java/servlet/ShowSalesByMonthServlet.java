package servlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.OrderItem;
import dao.OrderItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.MyFormat;

@WebServlet("/showSalesByMonth")
public class ShowSalesByMonthServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String error = "";
		String cmd = "";

		//検索情報を取得
		request.setCharacterEncoding("UTF-8");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		

		//格納用変数
		String Sales = "売上がありません。";
		ArrayList<OrderItem> orderedList = new ArrayList<OrderItem>();
		ArrayList<ArrayList<OrderItem>> orderPageList = null;
		int page = 0;
		int pageCount = 0;

		try {
			OrderItemDAO orderItemDAO = new OrderItemDAO();

			//未入力確認
			if (year.equals("") || month.equals("")) {
				return;
			}

			//売上げ検索結果をリクエストスコープに格納する
			orderedList = orderItemDAO.selectBySales(year, month);

			//検索月の売上有無判定
			if (orderItemDAO.selectByTotalPrice(year, month) == 0) {
				return;
			}

			//購入情報をページごとに分割
			ArrayList<OrderItem> orderSepList = new ArrayList<>();
			orderPageList = new ArrayList<ArrayList<OrderItem>>();
			String preBuyer = "";
			String preDate = "";
			if (!(orderedList.isEmpty())) {
				for (int i = 0; i < orderedList.size(); i++) {
					if ((orderSepList.size() <= 9) || ((orderedList.get(i).getBuyer().equals(preBuyer))
							&& (orderedList.get(i).getPur_date().equals(preDate)))) {
						orderSepList.add(orderedList.get(i));
					} else {
						orderPageList.add(orderSepList);
						orderSepList = new ArrayList<>();
						orderSepList.add(orderedList.get(i));
					}
					preBuyer = orderedList.get(i).getBuyer();
					preDate = orderedList.get(i).getPur_date();
				}
				orderPageList.add(orderSepList);
				pageCount = orderPageList.size();

				//ページ数取得
				if (request.getParameter("page") != null) {
					page = (Integer.valueOf(request.getParameter("page")) - 1);
				}
				orderedList = orderPageList.get(page);
			}

			//検索月の売上をリクエストスコープに格納
			MyFormat mf = new MyFormat();
			Sales = year + "年 " + month + "月売上 " + mf.moneyFormat(orderItemDAO.selectByTotalPrice(year, month));

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、検索結果は表示出来ません。";
			cmd = "orderedList";
		} finally {
			// � エラーの有無でフォワード先を呼び分ける
			if (error.equals("")) {
				request.setAttribute("Sales", Sales);
				request.setAttribute("pageCount", pageCount);
				request.setAttribute("orderedList", orderedList);
				request.setAttribute("cmd", "salesByMonth");
				request.setAttribute("showYear", year);
				request.setAttribute("showMonth", month);
				request.setAttribute("showPage", page + 1);

				// showOrderedItem.jspにフォワードする
				request.getRequestDispatcher("/view/orderedList.jsp")
						.forward(request, response);
			} else {
				// エラーが有る場合はerror.jspにフォワードする
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(
						request, response);
			}
		}
	}
}