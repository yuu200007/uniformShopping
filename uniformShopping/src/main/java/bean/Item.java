package bean;

public class Item {

	private int itemId; //商品ID
	private String itemName; //商品名
	private int price; //価格
	private int stock; //在庫数
	private String image; //画像

	//コンストラクタ
	public Item() {
		this.itemId = 0; //商品ID
		this.itemName = null; //商品名
		this.price = 0; //価格
		this.stock = 0; //在庫数
		this.image = null; //画像
	}

	//商品ID
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	//商品名
	public String getItemName() {
		return itemName;
	}

	//商品名
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	//価格
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	//在庫数
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	//画像
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
