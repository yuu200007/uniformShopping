<%-- changeStatus.jsp --%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,bean.*,util.MyFormat"%>

<%
MyFormat mf = new MyFormat();
ArrayList<OrderItem> orderedList = (ArrayList<OrderItem>) request.getAttribute("orderedList");
%>

<html>
<head>
<title>受注詳細</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<!-- ブラウザ全体 -->
	<div id="wrap">

		<!-- ヘッダー部分 -->
		<%@ include file="/common/header.jsp"%>

		<!-- メニュー部分 -->
		<div id="menu">
			<div class="container">
				<!-- ナビゲーション  -->
				<div id="nav">
					<ul>
						<li><a href="<%=request.getContextPath()%>/view/list.jsp">[商品一覧]</a></li>
					</ul>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>受注詳細</h2>
				</div>
			</div>
		</div>

		<!-- 発送状況・入金状況更新コンテンツ部分 -->
		<div id="main" class="container">

			<!--  入力フォーム -->
			<form action="<%=request.getContextPath()%>/changeStatus">

				<table class="input-table" align="center">
					<tr>
						<th>ユーザー名</th>
						<td><%=orderedList.get(0).getBuyer()%></td>
					</tr>
					<tr>
						<th>発注日</th>
						<td><%=orderedList.get(0).getPur_date()%></td>
					</tr>
					<tr>
						<th>合計</th>
						<input type="hidden" name="name">
						<td>
							<%
							int totalPrice = 0;
							for (OrderItem orderItem : orderedList) {
							    totalPrice += orderItem.getPrice() * orderItem.getQuantity();
							}
							%> <%=mf.moneyFormat(totalPrice)%>
						</td>
					</tr>
					<tr>
						<th>入金状況</th>
						<td><select name="payStatus">
								<%
								if (orderedList.get(0).getPay_status() == 2) {
								%>
								<option value="1">入金待ち</option>
								<option value="2" selected>入金済</option>
								<%
								} else {
								%>
								<option value="1">入金待ち</option>
								<option value="2">入金済</option>
								<%} %>
						</select></td>
					</tr>
					<tr>
						<th>発送状況</th>
						<td><select name="deliStatus">
								<%
								if (orderedList.get(0).getDeli_status() == 2) {
								%>
								<option value="1">未発送</option>
								<option value="2" selected>発送準備中</option>
								<option value="3">発送済</option>
								<%
								} else if (orderedList.get(0).getDeli_status() == 3) {
								%>
								<option value="1">未発送</option>
								<option value="2">発送準備中</option>
								<option value="3" selected>発送済</option>
								<%
								} else {
								%>
								<option value="1">未発送</option>
								<option value="2">発送準備中</option>
								<option value="3">発送済</option>
								<%} %>
						</select></td>
					</tr>
					<tr>
						<td colspan=2><input type="submit" value="状況更新"> <input
							type="hidden" name="buyer"
							value="<%=orderedList.get(0).getBuyer()%>"> <input
							type="hidden" name="purDate"
							value="<%=orderedList.get(0).getPur_date()%>"></td>
					</tr>
				</table>
			</form>

			<!--  購入商品一覧 -->
			<%
			for (OrderItem orderItem : orderedList) {
			%>
			<table>
				<tr>
					<th>商品名</th>
					<td><%=orderItem.getItem_name()%></td>
				</tr>
				<tr>
					<th>商品画像</th>
					<input type="hidden" name="name">
					<td><img
						src="<%=request.getContextPath()%>/img/<%=orderItem.getImage()%>"
							alt="
						<%=orderItem.getItem_name()%>" width="176" height="159"></td>
				</tr>
				<tr>
					<th>個数</th>
					<td><%=orderItem.getQuantity()%></td>
				</tr>
				<tr>
					<th>金額</th>
					<td><%=mf.moneyFormat(orderItem.getPrice() * orderItem.getQuantity())%></td>
				</tr>
			</table>
			<%
			}
			%>
		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>

	</div>
</body>
</html>