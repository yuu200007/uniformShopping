package util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bean.Order;
import bean.OrderItem;
import bean.User;

public class SendMail {

	/* メール内容の記述 */
	public void MailText(User user, ArrayList<Order> order_list, int total) {
		String text; //本文
		String booktext = ""; //注文内容
		String login_id = user.getLogin_id();
		String email = user.getEmail();
		String item_name;
		int price;
		int quantity;
		
		//注文内容・合計金額の取得
		for (int i = 0; i < order_list.size(); i++) {
			item_name = order_list.get(i).getItem_name();
			price = order_list.get(i).getPrice();
			quantity = order_list.get(i).getQuantity();
			booktext += item_name + "  " + price + "円" + quantity + "個\n";
		}
		
		//本文
		text = login_id + "様\n\n" + "ユニフォームのご購入ありがとうございます。\n"
				+ "以下内容でご注文を受け付けましたので、ご連絡致します。\n\n"
				+ booktext + "合計  " + total + "円\n\nまたのご利用よろしくお願いします。\n\n"
				+"------------------------------- \n"
				+"＜振込口座＞ \n"
				+"神田銀行 神田支店 \n"
				+"普通口座 2340909 \n"
				+"口座名 カンダ \n"
				+"-------------------------------";
		Send(email, text);
	}

	/* メール送信 */
	private void Send(String email, String text) {
		try {
			Properties props = System.getProperties();

			// SMTPサーバのアドレスを指定（今回はxserverのSMTPサーバを利用）
			props.put("mail.smtp.host", "sv5215.xserver.jp");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.debug", "true");

			Session session = Session.getInstance(
					props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							//メールサーバにログインするメールアドレスとパスワードを設定
							return new PasswordAuthentication("test.sender@kanda-it-school-system.com",
									"kandaSender202208");
						}
					});

			MimeMessage mimeMessage = new MimeMessage(session);

			// 送信元メールアドレスと送信者名を指定
			mimeMessage.setFrom(
					new InternetAddress("test.sender@kanda-it-school-system.com", "神田IT School", "iso-2022-jp"));

			// 送信先メールアドレスを指定
			mimeMessage.setRecipients(Message.RecipientType.TO,email);
			
			// メールのタイトルを指定
			mimeMessage.setSubject("【神田ユニフォームショップ】ご注文いただきありがとうございます", "iso-2022-jp");

			// メールの内容を指定
			mimeMessage.setText(text, "iso-2022-jp");

			// メールの形式を指定
			mimeMessage.setHeader("Content-Type", "text/plain; charset=iso-2022-jp");

			// 送信日付を指定
			mimeMessage.setSentDate(new Date());

			// 送信します
			Transport.send(mimeMessage);

			// 送信成功
			System.out.println("送信に成功しました。");

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("送信に失敗しました。\n" + e);
		}
	}
	
	/* メール内容の記述 */
	public void PayMailText(ArrayList<OrderItem> mail_list, User user) {
		String text; //本文
		String booktext = ""; //注文内容
		String login_id = user.getLogin_id();
		String email = user.getEmail();
		String item_name;
		int price;
		int quantity;
		
		//注文内容・合計金額の取得
		for (int i = 0; i < mail_list.size(); i++) {
			item_name = mail_list.get(i).getItem_name();
			price = mail_list.get(i).getPrice();
			quantity = mail_list.get(i).getQuantity();
			booktext += item_name + "  " + price + "円" + quantity + "個\n";
		}
		
		//本文
		text = login_id + "様\n\n" + "ご注文の代金をお振り込みいただき、誠にありがとうございます。\n"
				+ "ご入金を確認いたしました。\n\n"
				+ "ご注文いただいた内容は以下のとおりです。\n\n"
				+"------------------------------- \n"
				+ booktext +"\n"
				+"------------------------------- \n\n"
				+"この度はご購入くださり誠にありがとうございました。 \n"
				+"今後とも、何卒よろしくお願い申し上げます。";
		paySend(email, text);
	}

	/* メール送信 */
	private void paySend(String email, String text) {
		try {
			Properties props = System.getProperties();

			// SMTPサーバのアドレスを指定（今回はxserverのSMTPサーバを利用）
			props.put("mail.smtp.host", "sv5215.xserver.jp");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.debug", "true");

			Session session = Session.getInstance(
					props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							//メールサーバにログインするメールアドレスとパスワードを設定
							return new PasswordAuthentication("test.sender@kanda-it-school-system.com",
									"kandaSender202208");
						}
					});

			MimeMessage mimeMessage = new MimeMessage(session);

			// 送信元メールアドレスと送信者名を指定
			mimeMessage.setFrom(
					new InternetAddress("test.sender@kanda-it-school-system.com", "神田IT School", "iso-2022-jp"));

			// 送信先メールアドレスを指定
			mimeMessage.setRecipients(Message.RecipientType.TO,email);
			
			// メールのタイトルを指定
			mimeMessage.setSubject("【神田ユニフォームショップ】入金確認いたしました", "iso-2022-jp");

			// メールの内容を指定
			mimeMessage.setText(text, "iso-2022-jp");

			// メールの形式を指定
			mimeMessage.setHeader("Content-Type", "text/plain; charset=iso-2022-jp");

			// 送信日付を指定
			mimeMessage.setSentDate(new Date());

			// 送信します
			Transport.send(mimeMessage);

			// 送信成功
			System.out.println("送信に成功しました。");

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("送信に失敗しました。\n" + e);
		}
	}
	
	/* メール内容の記述 */
	public void DeliMailText(ArrayList<OrderItem> mail_list, User user) {
		String text; //本文
		String booktext = ""; //注文内容
		String login_id = user.getLogin_id();
		String email = user.getEmail();
		String item_name;
		int price;
		int quantity;
		
		//注文内容・合計金額の取得
		for (int i = 0; i < mail_list.size(); i++) {
			item_name = mail_list.get(i).getItem_name();
			price = mail_list.get(i).getPrice();
			quantity = mail_list.get(i).getQuantity();
			booktext += item_name + "  " + price + "円" + quantity + "個\n";
		}
		
		//本文
		text = login_id + "様\n\n" + "このたびは商品をご購入いただき、誠にありがとうございます。。\n"
				+ "ご注文の商品につきまして、本日発送いたしましたのでお知らせ申し上げます。\n\n"
				+ "ご注文いただいた内容は以下のとおりです。\n\n"
				+"------------------------------- \n"
				+ booktext +"\n"
				+"------------------------------- \n\n"
				+"この度はご購入くださり誠にありがとうございました。 \n"
				+"またのご利用を心よりお待ちしております。";
		deliSend(email, text);
	}

	/* メール送信 */
	private void deliSend(String email, String text) {
		try {
			Properties props = System.getProperties();

			// SMTPサーバのアドレスを指定（今回はxserverのSMTPサーバを利用）
			props.put("mail.smtp.host", "sv5215.xserver.jp");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.debug", "true");

			Session session = Session.getInstance(
					props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							//メールサーバにログインするメールアドレスとパスワードを設定
							return new PasswordAuthentication("test.sender@kanda-it-school-system.com",
									"kandaSender202208");
						}
					});

			MimeMessage mimeMessage = new MimeMessage(session);

			// 送信元メールアドレスと送信者名を指定
			mimeMessage.setFrom(
					new InternetAddress("test.sender@kanda-it-school-system.com", "神田IT School", "iso-2022-jp"));

			// 送信先メールアドレスを指定
			mimeMessage.setRecipients(Message.RecipientType.TO,email);
			
			// メールのタイトルを指定
			mimeMessage.setSubject("【神田ユニフォームショップ】商品発送のお知らせ", "iso-2022-jp");

			// メールの内容を指定
			mimeMessage.setText(text, "iso-2022-jp");

			// メールの形式を指定
			mimeMessage.setHeader("Content-Type", "text/plain; charset=iso-2022-jp");

			// 送信日付を指定
			mimeMessage.setSentDate(new Date());

			// 送信します
			Transport.send(mimeMessage);

			// 送信成功
			System.out.println("送信に成功しました。");

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("送信に失敗しました。\n" + e);
		}
	}
	
}
