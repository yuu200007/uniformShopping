package bean;

/**
 * ユーザー情報（ユーザーID、ログインID、パスワード、権限、名前、住所、Eメールアドレス）を一つのオブジェクトとしてまとめるためのDTOクラス
 * 								
 */
public class User {

	/**
	 * ユーザーID
	 */
	private int user_id;
	/**
	 * ログインID
	 */
	private String login_id;
	/**
	 * パスワード
	 */
	private String password;
	/**
	 * ユーザー情報の権限<br>
	 * 1の場合は「一般ユーザー」、2の場合は「管理者」に該当する
	 */
	private int authority;
	/**
	 * 名前
	 */
	private String name;
	/**
	 * 住所
	 */
	private String address;
	/**
	 * Eメールアドレス
	 */
	private String email;

	/**
	 * コンストラクタ
	 * ユーザー情報（ユーザーID、ログインID、パスワード、権限、名前、住所、Eメールアドレス）の初期設定をおこなう
	 */
	public User() {
		user_id = 0;
		login_id = null;
		password = null;
		authority = 0;
		name = null;
		address = null;
		email = null;
	}

	/**
	 * ユーザー情報のユーザーIDを取得する
	 *
	 * @return ユーザー情報のユーザーID
	 */
	public int getUser_id() {
		return user_id;
	}

	/**
	 * ユーザー情報のユーザーIDを設定する
	 *
	 * @param userid
	 *            設定するユーザーID
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	/**
	 * ユーザー情報のログインIDを取得する
	 *
	 * @return ユーザー情報のログインID
	 */
	public String getLogin_id() {
		return login_id;
	}

	/**
	 * ユーザー情報のログインIDを設定する
	 *
	 * @param userid
	 *            設定するログインID
	 */
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	/**
	 * ユーザー情報のパスワードを取得する
	 *
	 * @return ユーザー情報のパスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * ユーザー情報のパスワードを設定する
	 *
	 * @param password
	 *            設定するパスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * ユーザー情報の権限を取得する
	 *
	 * @return ユーザー情報の権限<br>
	 *         1の場合は「一般ユーザー」、2の場合は「管理者」に該当する
	 */
	public int getAuthority() {
		return authority;
	}

	/**
	 * ユーザー情報の権限を取得する
	 *
	 * @param authority
	 *            ユーザー情報の権限<br>
	 *            1の場合は「一般ユーザー」、2の場合は「管理者」に該当する
	 */
	public void setAuthority(int authority) {
		this.authority = authority;
	}

	/**
	 * ユーザー情報の名前を取得する
	 *
	 * @return ユーザー情報の名前
	 */
	public String getName() {
		return name;
	}

	/**
	 * ユーザー情報の名前を設定する
	 *
	 * @param userid
	 *            設定する名前
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ユーザー情報の住所を取得する
	 *
	 * @return ユーザー情報の住所
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * ユーザー情報の住所を設定する
	 *
	 * @param userid
	 *            設定する住所
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * ユーザー情報のEメールアドレスを取得する
	 *
	 * @return ユーザー情報のEメールアドレス
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * ユーザー情報のEメールアドレスを設定する
	 *
	 * @param userid
	 *            設定するEメールアドレス
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
