package servlet;

//注文詳細を表示するためのサーブレット

import java.io.IOException;
import java.util.ArrayList;

import bean.OrderItem;
import dao.OrderItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/orderDetail")
public class OrderDetailServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String error = "";
        String cmd = "";

        try {

            //OrderItemクラスをインスタンス化
            OrderItemDAO orderItemDao = new OrderItemDAO();

            // 購入者と購入日時を取得
            String buyer = request.getParameter("buyer");
            String purDate = request.getParameter("purDate");

            // 商品情報一覧を取得
            ArrayList<OrderItem> orderedList = orderItemDao.selectByBuyerAndDate(buyer, purDate);

            // 詳細情報のエラーチェック
            if (orderedList == null) {
                error = "表示対象の商品が存在しない為、受注詳細は表示できませんでした。";
                cmd = "orderedList";
            }

            // 取得したorderedListをリクエストスコープに"orderedList"という名前で格納する
            request.setAttribute("orderedList", orderedList);

        } catch (IllegalStateException e) {
            error = "DB接続エラーの為、受注詳細は表示できませんでした。";
            cmd = "orderedList";

        } finally {
            if (cmd.equals("")) {
                request.getRequestDispatcher("/view/orderDetail.jsp").forward(request, response);

            } else {
                request.setAttribute("error", error);
                request.setAttribute("cmd", cmd);
                request.getRequestDispatcher("/view/error.jsp").forward(request, response);
            }
        }
    }
}
