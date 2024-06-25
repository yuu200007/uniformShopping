package servlet;

//商品を削除するサーブレット

import java.io.IOException;

import bean.Item;
import dao.ItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";
		String cmd = "";

		try {

			//画面から送信されるISBNとcmd情報を受け取るためのエンコードを設定
			request.setCharacterEncoding("UTF-8");

			//商品IDの入力パラメータを取得する。
			String strItemId = request.getParameter("item_id");

			//②ItemDAOをインスタンス化し、selectByItemId（）を呼び出す。
			ItemDAO itemDao = new ItemDAO();

			//String型で受け取ったパラメータをint型に変換
			int itemId = Integer.parseInt(strItemId);

			// 入力された商品IDを引数にして、存在チェック
			Item deleteItem = itemDao.selectByItemId(itemId);

			if (deleteItem == null) {
				error = "削除対象の商品が存在しない為、商品削除処理は行えませんでした。";
				cmd = "list";
				return;
			}

			//データベースのiteminfoテーブルの引数情報に該当する商品IDデータを削除するdelete（）
			itemDao.delete(itemId);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、商品削除処理は行えませんでした。";
			cmd = "list";

		} finally {

			//エラーの有無でforward先を呼び分ける
			if (error.equals("")) {
				// エラーが無い場合（正常ルート）ListServletにフォワードする。
				response.sendRedirect(request.getContextPath() + "/list"); //商品一覧に遷移

			} else {
				// エラーが有る場合（異常ルート）error.jspにフォワードする
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
