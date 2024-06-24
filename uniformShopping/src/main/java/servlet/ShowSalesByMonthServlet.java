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

        try {
            //検索情報を取得
            request.setCharacterEncoding("UTF-8");
            String year = request.getParameter("year");
            String month = request.getParameter("month");

            //売上げ検索結果をリクエストスコープに格納する
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            ArrayList<OrderItem> orderedList = orderItemDAO.selectBySales(year, month);
            request.setAttribute("orderedList", orderedList);

            //検索月の売上をリクエストスコープに格納
            MyFormat mf = new MyFormat();
            String Sales = year + "年 " + month + "月売上 "
                    + mf.moneyFormat(orderItemDAO.selectByTotalPrice(year, month));

            //検索月の売上判定
            if (orderItemDAO.selectByTotalPrice(year, month) == 0 || Sales == null) {
                Sales = "売上がありません。";
            }

            request.setAttribute("Sales", Sales);
        } catch (IllegalStateException e) {
            error = "DB接続エラーの為、購入情報一覧は表示出来ません。";
            cmd = "login";
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