<%@page contentType="text/html; charset=UTF-8"%>

<style>
body {
	margin: 0;
	padding: 0;
	background-color: white;
}

.b {
	width: 100%;
	text-align: center;
	background-color: #993300;
	color: white;
	padding: 2px 0; /* 上下のパディングを追加 */
	box-sizing: border-box; /* パディングを含めた幅を設定 */
}

.b h1 {
	margin: 0; /* h1タグのマージンをゼロに */
	padding: 10px 0; /* 上下のパディングを調整 */
	color: white;
	background: #993300;
}

img {
	vertical-align: middle; /* 画像の位置をテキストの中央に */
	 height: 50px; /* 適切な高さを設定 */
}

.content {
	text-align: center;
	background-color: #FFFFFF;
	color: #000000;
	padding: 20px;
	margin: 20px;
}
</style>

<header>
	<div class="b">
		<h1>
			<font face="impact">KANDA UNIFORM</font> <img src="<%=request.getContextPath()%>/img/ロゴ.png"
				alt="ロゴ" width="100" height="200">
		</h1>
	</div>
</header>
