<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,bean.*,util.MyFormat"%>

<%
MyFormat mf = new MyFormat();
ArrayList<OrderItem> orderedList = (ArrayList<OrderItem>) request.getAttribute("orderedList");
int pageCount = (int) request.getAttribute("pageCount");
String cmd = (String) request.getAttribute("cmd");
String Sales = (String) request.getAttribute("Sales");
int showPage = (int) request.getAttribute("showPage");
String showYear = "";
String showMonth = "";
if (cmd.equals("salesByMonth")) {
    showYear = (String) request.getAttribute("showYear");
    showMonth = (String) request.getAttribute("showMonth");
}
%>

<html>
<head>
<title>受注状況一覧</title>
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
				<div id="nav">
					<p class="bold">
						<a href="<%=request.getContextPath()%>/logout">ログアウト</a>&nbsp;
						&nbsp; <a href="<%=request.getContextPath()%>/list">商品一覧</a>
						&nbsp; &nbsp; <a
							href="<%=request.getContextPath()%>/view/insert.jsp">商品登録</a>
					</p>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>受注状況一覧</h2>
				</div>
			</div>
		</div>


		<div id="main" class="container">
			<!-- 売上表示 -->
			<%=Sales%>
			<!-- 受注状況一覧 -->
			<table class="list-table">
				<tr>
					<td colspan="9">
						<!-- 検索フォーム -->
						<form class="inline-block"
							action="<%=request.getContextPath()%>/showSalesByMonth">
							<input type="text" name="year">年 <input type="text"
								name="month">月 <input type="submit" value="検索">
						</form>
						<form class="inline-block"
							action="<%=request.getContextPath()%>/orderedList">
							<input type="submit" value="全件表示">
						</form>
					</td>
				</tr>
				<tr>
					<td colspan="9">
						<%
						for (int i = 1; i <= pageCount; i++) {
						    if ((showPage != i) && (cmd.equals("list"))) {
						%> <a
						href="<%=request.getContextPath()%>/orderedList?page=<%=i%>"
						class="custom-link"><%=i%></a>&nbsp;&nbsp;&nbsp; <%
 } else if ((showPage != i) && (cmd.equals("salesByMonth"))) {
 %>
						<a
						href="<%=request.getContextPath()%>/showSalesByMonth?page=<%=i%>&year=<%=showYear%>&month=<%=showMonth%>"
						class="custom-link"><%=i%></a> <%
 }
 }
 %>
					</td>
				</tr>
				<tr>
					<th>ユーザー名</th>
					<th>商品名</th>
					<th>個数</th>
					<th>合計金額</th>
					<th>発注日</th>
					<th>入金状況</th>
					<th>発送状況</th>
					<th>&nbsp;</th>
				</tr>
				<%
				if (orderedList != null) {
				    String nextBuyer = "";
				    String nextDate = "";
				    for (OrderItem orderItem : orderedList) {

				        String payStatus = "";
				        String deliStatus = "";
				        switch (orderItem.getPay_status()) {
				        case 1:
				    payStatus = "未";
				    break;

				        case 2:
				    payStatus = "済";
				    break;
				        }

				        switch (orderItem.getDeli_status()) {
				        case 1:
				    deliStatus = "未";
				    break;

				        case 2:
				    deliStatus = "準備中";
				    break;

				        case 3:
				    deliStatus = "済";
				    break;
				        }

				        if ((nextBuyer.equals(orderItem.getBuyer())) && (nextDate.equals(orderItem.getPur_date()))) {
				%>
				<tr>
					<td></td>
					<td><%=orderItem.getItem_name()%></td>
					<td><%=orderItem.getQuantity()%></td>
					<td><%=mf.moneyFormat(orderItem.getPrice() * orderItem.getQuantity())%></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<%} else {%>
				<tr>
					<td><%=orderItem.getBuyer()%></td>
					<td><%=orderItem.getItem_name()%></td>
					<td><%=orderItem.getQuantity()%></td>
					<td><%=mf.moneyFormat(orderItem.getPrice() * orderItem.getQuantity())%></td>
					<td><%=orderItem.getPur_date()%></td>
					<td><%=payStatus%></td>
					<td><%=deliStatus%></td>
					<td><a
						href="<%=request.getContextPath()%>/orderDetail?buyer=<%=orderItem.getBuyer()%>&purDate=<%=orderItem.getPur_date()%>"
						class="custom-link">詳細</a></td>
				</tr>
				<%
				}
				nextBuyer = orderItem.getBuyer();
				nextDate = orderItem.getPur_date();
				}
				}
				%>
			</table>
		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>
</body>
</html>
