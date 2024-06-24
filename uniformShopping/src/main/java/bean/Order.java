package bean;

public class Order {
	
	//フィールド変数の適宜
	private int trans_id;
	private String buyer;
	private int item_id;
	private String item_name;
	private String image;
	private int price;
	private int quantity;
	private int pay_status;
	private int deli_status;
	private String pur_date;

	//コンストラクター引数なし
	public Order() {
		this.trans_id = 0;
		this.buyer = null;
		this.item_id = 0;
		this.item_name = null;
		this.image = null;
		this.price = 0;
		this.quantity = 0;
		this.pay_status = 0;
		this.deli_status = 0;
		this.pur_date = null;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(int trans_id) {
		this.trans_id = trans_id;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPay_status() {
		return pay_status;
	}

	public void setPay_status(int pay_status) {
		this.pay_status = pay_status;
	}

	public int getDeli_status() {
		return deli_status;
	}

	public void setDeli_status(int deli_status) {
		this.deli_status = deli_status;
	}

	public String getPur_date() {
		return pur_date;
	}

	public void setPur_date(String pur_date) {
		this.pur_date = pur_date;
	}
	
}
