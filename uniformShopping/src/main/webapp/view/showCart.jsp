<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,bean.Order,util.MyFormat" %>
<%
ArrayList<Order> order_list = (ArrayList<Order>)session.getAttribute("order_list");
MyFormat mf = new MyFormat();
%>
<html>
<head>
<title>カート内容</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<!-- ブラウザ全体 -->
	<div id="wrap">

		<!--ヘッダー部分  -->
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/common/userInfo.jsp"%>

		<!-- メニュー部分 -->
		<div id="menu">
			<div class="container">
				<!-- ナビゲーション  -->
				<div id="nav">
					<ul>
						<li><a href="<%=request.getContextPath()%>/list">[商品一覧]</a></li>
					</ul>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>カート内容</h2>
				</div>
			</div>
		</div>

		<!-- 商品一覧のコンテンツ部分 -->
		<div id="main" class="container">
			<table class="list-table">
				<tr>
					<th>商品名</th>
					<th>単価</th>
					<th>個数</th>
					<th></th>
				</tr>
				<%
				int total = 0;
				if (order_list != null) {
					for (int i = 0; i < order_list.size(); i++) {
				%>
				<tr>
					<td style="text-align: center"><%=order_list.get(i).getItem_name()%></td>
					<td style="text-align: center"><%=mf.moneyFormat(order_list.get(i).getPrice())%></td>
					<td style="text-align: center"><%=order_list.get(i).getQuantity()%></td>
					<td style="text-align: center"><a href="<%=request.getContextPath()%>/insertIntoCart?delno=<%=i%>">削除</a></td>
				</tr>
				<%
					total += ((order_list.get(i).getPrice())*(order_list.get(i).getQuantity()));
					}
				}
				%>
			</table>
			<hr>
			<table class="total-price-table">
				<tr>
					<th>合計</th>
					<td><%= mf.moneyFormat(total) %></td>
				</tr>
			</table>
			<% if (order_list != null) { %>
			<form action="<%=request.getContextPath()%>/view/orderConfirm.jsp" class="buy-button">
				<input type="submit" value="注文内容確認">
			</form>
			<%} %>
		</div>
	</div>

	<!-- フッター部分 -->
	<%@ include file="/common/footer.jsp"%>
	</div>
</body>
</html>