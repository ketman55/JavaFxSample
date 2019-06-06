package common;

public class Statics {
	// 処理終了を画面に返す
	public static final String END_OF_STEP_NORMAL = "処理が完了しました！";
	public static final String END_OF_STEP_ERROR = "処理が異常終了しました";

	// output_kuromojiのパス
	public static final String OUTPUT_KUROMOJI = "output_kuromoji";
	public static final String OUTPUT_LDA = "output_lda/output_lda.txt";

	// トピック分析に利用する品詞
	// 名詞、動詞（自立）、形容詞（自立）、副詞
	public static final String TOPIC_HINSHI =  "(名詞.+|動詞,自立.+|形容詞,自立.+|副詞.+)";
}
