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

        try {
            name = request.getParameter("name");
            Email = request.getParameter("Email");
            address = request.getParameter("address");

            OrderDAO objOrderDao = new OrderDAO();
            ItemDAO itemDao = new ItemDAO();
            UserDAO userDao = new UserDAO();

            /*nullじゃない(非会員の場合)*/
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

            /*セッション切れ判定*/
            if (user == null) {
                error = "セッション切れの為、注文は行えません。";
                cmd = "list";
                request.setAttribute("error", error);
                request.setAttribute("cmd", cmd);
                request.getRequestDispatcher("/view/error.jsp").forward(request, response);
            }

            /*user0の場合、ゲストユーザー登録*/
            if (user.getLogin_id().equals("user0")) {
                /*ゲストidの割り当て*/
                String guestId;
                int i = 0;
                while (true) {
                    guestId = "Guest" + i;
                    if (userDao.searchUser(guestId) == null) {
                        break;
                    }
                    i++;
                }

                /*ゲストユーザーをＤＢに登録*/
                user.setLogin_id(guestId);
                user.setName(name);
                user.setEmail(Email);
                user.setAddress(address);
                userDao.insert(user);

                //セッションのユーザー情報を上書き
                session.setAttribute("user", userDao.searchUser("user0"));
            }

            //カート内容を取得
            ArrayList<Order> order_list = (ArrayList<Order>) session.getAttribute("order_list");

            //カート内容の空確認
            if (order_list == null) {
                error = "カートの中に何も無かったので注文は行えません。";
                cmd = "list";
                return;
            }

            /*カート内商品の在庫確認*/
            for (Order order : order_list) {
                Item itemStock = itemDao.selectByItemId(order.getItem_id());
                if (order.getQuantity() > itemStock.getStock()) {
                    error = "在庫が不足しているため、注文は行えません。";
                    cmd = "list";
                    return;
                }
            }

            /*注文合計金額*/
            int total = 0;

            /*カート内商品を注文済みリストに格納*/
            for (Order order : order_list) {
                /*カート内商品にユーザーidと状況を付与*/
                order.setBuyer(user.getLogin_id());
                order.setPay_status(1);
                order.setDeli_status(1);

                /*注文済みDBに格納*/
                objOrderDao.insert(order);

                /*在庫数変化*/
                objOrderDao.updateStock(order);

                /*合計値を格納(メール用)*/
                total += ((order.getPrice()) * (order.getQuantity()));
            }

            //メール送信
            SendMail mail = new SendMail();
            mail.MailText(user, order_list, total);

            /*注文済みリストをリクエスト登録*/
            request.setAttribute("ordered_list", order_list);

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