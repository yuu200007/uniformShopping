package servlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.Order;
import bean.OrderItem;
import dao.OrderDAO;
import dao.OrderItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/changeStatus")
public class ChangeStatusServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String error = null; //エラーメッセージ	格納変数
        String cmd = "list"; //画面遷移の場所情報

        try {
            //フォーム値を取得
            request.setCharacterEncoding("UTF-8");
            String payStatus = request.getParameter("payStatus");
            String deliStatus = request.getParameter("deliStatus");
            String buyer = request.getParameter("buyer");
            String purDate = request.getParameter("purDate");

            //オブジェクトの生成
            OrderDAO orderDao = new OrderDAO(); //OrderDAOオブジェクトの生成

            // 商品情報一覧を取得
            OrderItemDAO orderItemDao = new OrderItemDAO();
            ArrayList<OrderItem> orderedList = orderItemDao.selectByBuyerAndDate(buyer, purDate);

            //ステータス更新
            for (OrderItem orderItem : orderedList) {
                Order order = new Order();
                order.setTrans_id(orderItem.getTrans_id());
                order.setPay_status(Integer.parseInt(payStatus));
                order.setDeli_status(Integer.parseInt(deliStatus));
                orderDao.updateStatus(order);
            }

        } catch (Exception e) { //DB未接続エラー
            error = "DB接続エラーの為、商品更新処理は行えませんでした。";
            cmd = "logout";

        } finally { //遷移先の指定
            if (error == null) {
                response.sendRedirect(request.getContextPath() + "/orderedList"); //受注一覧に遷移

            } else {
                request.setAttribute("error", error); //エラーメッセージのセット
                request.setAttribute("cmd", cmd); //エラー画面からの遷移先のセット
                request.getRequestDispatcher("/view/error.jsp").forward(request, response); // エラー画面に遷移
            }
        }
    }

}