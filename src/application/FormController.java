/**
 * Sample Skeleton for 'Form.fxml' Controller Class
 */

package application;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.hankcs.lda.Corpus;
import com.hankcs.lda.LdaGibbsSampler;
import com.hankcs.lda.LdaUtil;

import common.Statics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kuromoji.ReadAndWhiter;

public class FormController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="textField1"
    private TextField textField1; // Value injected by FXMLLoader

    @FXML // fx:id="button1"
    private Button button1; // Value injected by FXMLLoader

    @FXML // fx:id="resultOfStep1"
    private Label resultOfStep1; // Value injected by FXMLLoader

    @FXML // fx:id="textField2"
    private TextField textField2; // Value injected by FXMLLoader

    @FXML // fx:id="button2"
    private Button button2; // Value injected by FXMLLoader

    @FXML // fx:id="resultOfStep2"
    private Label resultOfStep2; // Value injected by FXMLLoader

	@FXML
	void onButton1Action(ActionEvent event) {
		// 指定したフォルダからテキストファイルを読み込む
		// 形態素解析し、output_kuromojiフォルダへテキスト形式で格納する
		ReadAndWhiter raw = new ReadAndWhiter();
		try {
			raw.runReadAndWhite(textField1.getText());
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			resultOfStep1.setText(Statics.END_OF_STEP_ERROR);
		}
		resultOfStep1.setText(Statics.END_OF_STEP_NORMAL);
	}

	@FXML
	void onButton2Action(ActionEvent event) {
		// 指定したフォルダからテキストファイルを読み込む
		// LDAでトピック分析し、output_ldaフォルダへテキスト形式で格納する
		// 1. Load corpus from disk
		Corpus corpus;
		try {
			corpus = Corpus.load(textField2.getText());
			// 2. Create a LDA sampler
			LdaGibbsSampler ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
			// 3. Train it
			ldaGibbsSampler.gibbs(10);
			// 4. The phi matrix is a LDA model, you can use LdaUtil to explain it.
			double[][] phi = ldaGibbsSampler.getPhi();
			Map<String, Double>[] topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), 10);
			LdaUtil.explain(topicMap);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			resultOfStep1.setText(Statics.END_OF_STEP_ERROR);
		}
		resultOfStep2.setText(Statics.END_OF_STEP_NORMAL);
	}

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert textField1 != null : "fx:id=\"textField1\" was not injected: check your FXML file 'Form.fxml'.";
        assert button1 != null : "fx:id=\"button1\" was not injected: check your FXML file 'Form.fxml'.";
        assert resultOfStep1 != null : "fx:id=\"resultOfStep1\" was not injected: check your FXML file 'Form.fxml'.";
        assert textField2 != null : "fx:id=\"textField2\" was not injected: check your FXML file 'Form.fxml'.";
        assert button2 != null : "fx:id=\"button2\" was not injected: check your FXML file 'Form.fxml'.";
        assert resultOfStep2 != null : "fx:id=\"resultOfStep2\" was not injected: check your FXML file 'Form.fxml'.";

    }
}
