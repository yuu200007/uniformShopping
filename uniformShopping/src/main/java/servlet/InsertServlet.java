package servlet;

import java.io.File;

//商品登録機能

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bean.Item;
import dao.ItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/insert")
@MultipartConfig
public class InsertServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";
		String cmd = "";

		try {

			//画面からの入力情報を受け取るためのエンコードを設定
			request.setCharacterEncoding("UTF-8");

			//④画面からの入力情報を受け取り(isbn、title、price等の入力パラメータを取得する)
			String itemName = request.getParameter("item_name");
			String strPrice = request.getParameter("price");
			String strStock = request.getParameter("stock");
			Part filePart = request.getPart("image");

			// 取得したパラメータの各エラーチェックをおこなう

			// 全データの空白チェック（データが入力されているかどうか）
			if (itemName.equals("")) {
				error = "商品名が未入力の為、商品登録処理は行えませんでした。";
				cmd = "list";
				return;
			}
			if (strPrice.equals("")) {
				error = "価格が未入力の為、商品登録処理は行えませんでした。";
				cmd = "list";
				return;
			}
			if (strStock.equals("")) {
				error = "在庫数が未入力の為、商品登録処理は行えませんでした。";
				cmd = "list";
				return;
			}

			//itemDaoオブジェクトに設定するため、trycatchの外側に宣言
			int price;
			int stock;

			try {
				// priceの値チェック（整数かどうか）
				price = Integer.parseInt(strPrice);

			} catch (NumberFormatException e) {
				error = "価格の値が不正の為、商品登録処理は行えませんでした。";
				cmd = "list";
				return;
			}

			try {
				// stockの値チェック（整数かどうか）
				stock = Integer.parseInt(strStock);

			} catch (NumberFormatException e) {
				error = "在庫数の値が不正の為、商品登録処理は行えませんでした。";
				cmd = "list";
				return;
			}

			//①selectByIsbn（）、insert()を呼び出すためのBookDAOクラスのオブジェクトを生成
			ItemDAO itemDao = new ItemDAO();

			//パラメータからファイル名抽出
			String contentDisposition = filePart.getHeader("content-disposition");
			Pattern pattern = Pattern.compile("filename=\"(.*)\"");
			Matcher matcher = pattern.matcher(contentDisposition);
			String image_name = "";

			//ファイル名取得
			String fileName;
			if (matcher.find()) {
				fileName = matcher.group(1);
				if (fileName == "") {
					error = "画像が未セットの為、商品登録処理は行えませんでした。";
					cmd = "list";
					return;
				}

				//画像の保存先指定
				File filePath = new File(getServletContext().getRealPath("/img/") + fileName);

				//画像ファイル存在確認
				int random;
				while (filePath.exists()) {
					random = (int)(Math.random() * 999);
					fileName = random + fileName;
					filePath = new File(
							getServletContext().getRealPath("/img/") + fileName);
				}

				//パラメータ取得
				try (InputStream inputStream = filePart.getInputStream()) {
					Files.copy(inputStream, filePath.toPath(), StandardCopyOption.REPLACE_EXISTING);
					image_name = fileName;
					inputStream.close();
				}

			}

			//②登録する書籍情報を格納するBookオブジェクトを生成(Bookクラス（DTO）)
			Item item = new Item();

			//④Bookオブジェクトに格納
			item.setItemName(itemName);
			item.setPrice(price);
			item.setStock(stock);
			item.setImage(image_name);

			//⑤BookDAOクラスに定義したinsert（）メソッドを利用して、Bookオブジェクトに格納された書籍データをデータベースに登録します。
			itemDao.insert(item);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、書籍登録処理は行えませんでした。";
			cmd = "logout";

		} finally {

			// エラーの有無でフォワード先を呼び分ける
			if (error.equals("")) {
				// エラーが無い場合（正常ルート）ListServletにフォワード
				response.sendRedirect(request.getContextPath() + "/list");

			} else {
				// エラーが有る場合（異常ルート）error.jspにフォワードする
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);

			}
		}
	}
}
