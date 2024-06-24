package util;

import java.text.DecimalFormat;

public class MyFormat {
	public String moneyFormat(int price) {
		DecimalFormat format = new DecimalFormat("###,###円");
		String strPrice = format.format(price);
		return strPrice;
	}
}
