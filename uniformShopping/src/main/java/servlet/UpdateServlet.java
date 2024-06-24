package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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

@WebServlet("/update")
@MultipartConfig
public class UpdateServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String error = null; //エラーメッセージ	格納変数
		String cmd = "list"; //画面遷移の場所情報

		try {

			//オブジェクトの生成
			ItemDAO itemDao = new ItemDAO(); //ItemDAOオブジェクトの生成
			Item item = new Item(); //Itemオブジェクトの生成

			//パラメータ取得
			cmd = request.getParameter("cmd");
			int itemId = Integer.parseInt(request.getParameter("item_id"));

			//商品詳細情報取得
			Item oldItem = itemDao.selectByItemId(itemId);

			//商品詳細・更新画面に遷移する場合finallyに移る
			if (cmd.equals("update_view")) {
				request.setAttribute("item_info", oldItem);
				return;
			}

			String itemName = request.getParameter("item_name");
			String price = request.getParameter("price");
			String stock = request.getParameter("stock");
			Part filePart = request.getPart("image");

			//商品名未入力エラー
			if (itemName.equals("")) {
				error = "商品名が未入力の為、商品登録処理は行えませんでした。";
				return;
			}
			//価格未入力エラー
			if (price.equals("")) {
				error = "価格が未入力の為、商品登録処理は行えませんでした。";
				return;
			}
			//在庫数未入力エラー
			if (price.equals("")) {
				error = "在庫数が未入力の為、商品登録処理は行えませんでした。";
				return;
			}

			//画像データの削除
			String oldImage = itemDao.selectByItemId(itemId).getImage();
			if (!(oldImage.equals("No_image.jpg"))) {
				Path delFile = new File(getServletContext().getRealPath("/img/") + oldImage).toPath();
				Files.delete(delFile);
			}

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

			//Item型に変換
			item.setItemId(itemId);
			item.setItemName(itemName);
			item.setPrice(Integer.parseInt(price));
			item.setStock(Integer.parseInt(stock));
			item.setImage(image_name);

			itemDao.update(item); //DBへの商品情報更新

		} catch (NumberFormatException e) { //値不正エラー
			error = "値が不正の為、商品更新処理は行えませんでした。";
			cmd = "list";

		} catch (Exception e) { //DB未接続エラー
			error = "DB接続エラーの為、商品更新処理は行えませんでした。";
			cmd = "logout";

		} finally { //遷移先の指定
			if ((error == null) && (cmd.equals("update_view"))) {
				request.getRequestDispatcher("/view/update.jsp").forward(request, response); //商品詳細・更新画面に遷移
			} else if ((error == null) && (cmd.equals("update"))) {
				response.sendRedirect(request.getContextPath() + "/list"); //商品一覧に遷移
				
			} else {
				request.setAttribute("error", error); //エラーメッセージのセット
				request.setAttribute("cmd", cmd); //エラー画面からの遷移先のセット
				request.getRequestDispatcher("/view/error.jsp").forward(request, response); // エラー画面に遷移
			}
		}
	}
}
