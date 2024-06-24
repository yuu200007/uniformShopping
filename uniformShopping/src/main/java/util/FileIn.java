package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * ファイルを読み込み形式で処理する際に使用するクラス
 *
 * @author KandaITSchool
 *
 */
public class FileIn {

	/**
	 * オープンしたファイルの情報を管理するための変数
	 */
	private Scanner sin = null;

	/**
	 * ファイルを読み込み形式で開くメソッド
	 *
	 * @param fname
	 *            開くファイルのパス
	 *
	 * @return ファイルを開くことできたらtrue、できなければfalse
	 */
	public boolean open(String fname) {
		boolean sts = true;
		try {
			this.sin = new Scanner(new File(fname));
		} catch (FileNotFoundException e) {
			sts = false;
		}
		return sts;
	}

	/**
	 * オープンしたファイルからデータを1行読み込むメソッド
	 *
	 * @return 読み込んだ1行データ、1行データがなければ{@code null}
	 */
	public String readLine() {
		String buff;

		if (this.sin.hasNextLine()) {
			buff = sin.nextLine();
		} else {
			buff = null;
		}
		return buff;
	}

	/**
	 * ファイルを閉じるメソッド
	 *
	 * @return ファイルを閉じることができたらtrue、できなければfalse
	 */
	public boolean close() {
		boolean sts = true;
		try {
			this.sin.close();
		} catch (Exception e) {
			sts = false;
		}
		return sts;
	}

}
