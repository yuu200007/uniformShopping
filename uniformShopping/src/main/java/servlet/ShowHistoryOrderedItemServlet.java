package servlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.OrderItem;
import bean.User;
import dao.OrderItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/showHistoryOrderedItem")
public class ShowHistoryOrderedItemServlet extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";
		String cmd = "";
		
		try {
			//セッションからユーザー情報を取得
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			
			//セッション切れの場合はエラー文の表示
			if(user == null) {
				error = "セッション切れの為、注文履歴は確認できません。";
				cmd = "list";
			}
			
			//入力データの文字コードの指定
			request.setCharacterEncoding("UTF-8");
			
			String login_id = user.getLogin_id();
			
			//OrderedItemDAOをインスタンス化
			OrderItemDAO objDao = new OrderItemDAO();
			
			//selectByUserメソッドを呼び出し、ArrayListに格納する
			ArrayList<OrderItem> orderItemList = objDao.selectByUser(login_id);
			
			//取得したArrayListをリクエストスコープに格納する
			request.setAttribute("orderItemList", orderItemList);
			
		}catch(IllegalStateException e) {
			error = "DB接続エラーの為、注文履歴表示は行えませんでした。";
			cmd = "list";
			
		}finally {
			//④ エラーの有無でフォワード先を呼び分ける
			if (error.equals("")) {
				//エラーがない場合はshowHistoryOrderedItem.jspにフォワード
				request.getRequestDispatcher("/view/showHistoryOrderedItem.jsp").forward(request, response);
			} else {
				// エラーが有る場合はerror.jspにフォワード
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
