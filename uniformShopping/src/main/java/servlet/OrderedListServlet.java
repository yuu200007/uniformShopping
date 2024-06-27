package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import bean.OrderItem;
import dao.OrderItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.MyFormat;

@WebServlet("/orderedList")
public class OrderedListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String error = "";
		String cmd = "";

		try {
			//全購入情報をリクエストスコープに格納
			OrderItemDAO orderItemDAO = new OrderItemDAO();
			ArrayList<OrderItem> orderedList = orderItemDAO.selectAll();

			//購入情報をページごとに分割
			ArrayList<ArrayList<OrderItem>> orderPageList = new ArrayList<ArrayList<OrderItem>>();
			ArrayList<OrderItem> orderSepList = new ArrayList<>();
			String preBuyer = "";
			String preDate = "";
			int pageCount = 0;
			int page = 0;
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

			request.setAttribute("pageCount", pageCount);
			request.setAttribute("orderedList", orderedList);
			request.setAttribute("cmd", "list");
			request.setAttribute("showPage", page + 1);

			//サーブレットアクセス時の年月を取得
			Calendar calendar = Calendar.getInstance();
			String thisYear = String.valueOf(calendar.get(Calendar.YEAR));
			String thisMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);

			//今月の売上をリクエストスコープに格納
			MyFormat mf = new MyFormat();
			String Sales = "今月の売上 "
					+ mf.moneyFormat(orderItemDAO.selectByTotalPrice(thisYear, thisMonth)) + "   先月の売上 ";

			//先月の売上をリクエストスコープに格納
			if (thisMonth.equals("1")) { //今月が一月の場合十二月の売上を取得
				Sales += mf.moneyFormat(
						orderItemDAO.selectByTotalPrice(String.valueOf(calendar.get(Calendar.YEAR) - 1), "12"));
			} else { //その他の月の場合-1月の売上を取得
				Sales += mf.moneyFormat(
						orderItemDAO.selectByTotalPrice(thisYear, String.valueOf(calendar.get(Calendar.MONTH))));
			}
			request.setAttribute("Sales", Sales);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、受注状況一覧は表示出来ません。";
			cmd = "orderedList";
		} finally {
			// � エラーの有無でフォワード先を呼び分ける
			if (error.equals("")) {
				// エラーが無い場合はshowOrderedItem.jspにフォワードする
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