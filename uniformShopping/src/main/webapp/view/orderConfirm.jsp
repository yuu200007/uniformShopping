<%-- 注文情報入力＆購入内容確認画面 --%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,bean.*,dao.*,util.*" %>
<%@page import="java.util.ArrayList"%>
<%
User user = (User)session.getAttribute("user");
MyFormat format = new MyFormat();
%>
<html>
	<head>
		<title>購入内容確認画面</title>
		<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
	</head>
	<body>
		<!-- ブラウザ全体 -->
		<div id="wrap">

			<!--ヘッダー部分  -->
			<%@ include file="/common/header.jsp" %>

			<!-- メニュー部分 -->
			<div id="menu">
				<div class="container">
					<!-- ナビゲーション  -->
					<div id="nav">
						<ul>
							<li><a href ="<%=request.getContextPath()%>/view/list.jsp">[商品一覧]</a></li>
						</ul>
					</div>

					<!-- ページタイトル -->
					<div id="page_title">
						<h2>購入内容確認</h2>
					</div>
				</div>
			</div>

			<!-- 一覧のコンテンツ部分 -->
			<div id="main" class="container">
			
			<%
			//会員だった場合
			if(user.getAuthority()==2){
			%>
				<!--  入力フォーム -->
				<form action="<%=request.getContextPath()%>/orderConfirm">
					<table class="input-table" align="center">
					<tr>
						<th>氏名</th>
						<td><%=user.getName() %></td>
					</tr>
					<tr>
						<th>メールアドレス</th>
						<td><%=user.getEmail() %></td>
					</tr>
					<tr>
						<th>住所</th>
						<td><%=user.getAddress() %></td>
					</tr>
					<tr>
						<th>備考欄</th>
						<td><textarea name="remarks" rows="3" cols="45"></textarea></td>
					</tr>
					</table>
				</form>
			<%
			//非会員だった場合
			}else{
			%>
			<!--  入力フォーム -->
				<form action="<%=request.getContextPath()%>/orderConfirm">
					<table class="input-table" align="center">
					<tr>
						<th>氏名</th>
						<td><input type="text" name="name"></td>
					</tr>
					<tr>
						<th>メールアドレス</th>
						<td><input type="text" name="Email"></td>
					</tr>
					<tr>
						<th>住所</th>
						<td><input type="text" name="address"></td>
					</tr>
					<tr>
						<th>備考欄</th>
						<td><textarea name="remarks" rows="3" cols="45"></textarea></td>
					</tr>
					</table>
				</form>
			<%
			}
			%>
				<!-- 注文一覧表示 -->
				<table class="list-table">
					<tr>
						<th>商品名</th>
						<th>単価</th>
						<th>個数</th>
					</tr>
					
					<%
					//合計金額
					int total = 0;
					
					//スコープデータを表示する
					ArrayList<Order> order_list = (ArrayList<Order>)session.getAttribute("order_list");
					
					for(int i=0; i<order_list.size(); i++){
						Order order = order_list.get(i);
						total += order.getPrice()*order.getQuantity();
					%>
					
					<tr>
						<td><%=order.getItem_name() %></td>
						<td><%=format.moneyFormat(order.getPrice()) %></td>
						<td><%=order.getQuantity() %></td>
					</tr>
					<%
					}
					%>
				</table>

				<hr/>
				<table class="total-price-table" >
					<tr>
						<th>合計</th>
						<td><%=format.moneyFormat(total) %></td>
					</tr>
				</table>

				<form action="<%=request.getContextPath()%>/orderConfirm" class="buy-button">
					<input type="submit" value=" 購入 ">
				</form>

			</div>

			<!-- フッター部分 -->
			<%@ include file="/common/footer.jsp" %>
		</div>
	</body>
</html>
