package servlet;

//注文詳細を表示するためのサーブレット

import java.io.IOException;

import dao.OrderItemDAO;
import dao.UserDAO;
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
            OrderItemDAO orderItemDao = new OrderItemDAO();
            UserDAO userDao = new UserDAO();

            // 購入者と購入日時を取得
            String buyer = request.getParameter("buyer");
            String purDate = request.getParameter("purDate");

            // 購入者と購入日時に基づいた商品情報一覧をリクエストスコープ登録
            request.setAttribute("orderedList", orderItemDao.selectByBuyerAndDate(buyer, purDate));

            // 購入者の情報をリクエストスコープ登録
            request.setAttribute("buyer", userDao.searchUser(buyer));
            
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
