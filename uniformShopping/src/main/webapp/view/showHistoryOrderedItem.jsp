<%-- 購入履歴確認画面 --%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,bean.*,dao.*,util.*"%>
<%
ArrayList<OrderItem> orderItemList = (ArrayList<OrderItem>) request.getAttribute("orderItemList");
MyFormat mf = new MyFormat();
%>
<html>
<head>
<title>購入履歴</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<!-- ブラウザ全体 -->
	<div id="wrap">
		<!-- ヘッダー部分 -->
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/common/userInfo.jsp"%>

		<!-- メニュー部分 -->
		<div id="menu">
			<div class="container">
				<!-- ナビゲーション  -->
				<div id="navList">
					<p class="bold">
						<a href="<%=request.getContextPath()%>/list">商品一覧</a>
					</p>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>購入履歴</h2>
				</div>
			</div>
		</div>

		<!--コンテンツ見出し部分  -->
		<div id="main" class="container">
			<table class="list-table">
				<tr>
					<th>商品名</th>
					<th>単価
					<th>個数</th>
					<th>合計</th>
					<th>注文日</th>
				</tr>

				<%
				if (orderItemList != null) {
					for (int i = 0; i < orderItemList.size(); i++) {
						int total = 0;
						total = ((orderItemList.get(i).getPrice()) * (orderItemList.get(i).getQuantity()));
				%>
				<tr>
					<td><%=orderItemList.get(i).getItem_name()%></td>
					<td><%=mf.moneyFormat(orderItemList.get(i).getPrice())%></td>
					<td><%=orderItemList.get(i).getQuantity()%></td>
					<td><%=mf.moneyFormat(total)%></td>
					<td><%=orderItemList.get(i).getPur_date().replace("-", "/")%></td>
				</tr>
				<%
				}
				}
				%>
			</table>
		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>

	</div>
</body>
</html>