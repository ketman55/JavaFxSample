package kuromoji;

import java.util.ArrayList;
import java.util.List;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

import common.Statics;

public class DoKuromoji {
	public List<String> analyse(String text){
		ArrayList<String> sentenceList = new ArrayList<String>();
		// 改行を除去
		text = text.replace("\n", "");
		// 句読点で句切る
		String[] split = text.split("。");
		// 1文ずつ解析して文字を返す
		for(int i=0; i<split.length; i++) {
			Tokenizer tokenizer = Tokenizer.builder().build();
			for (Token token : tokenizer.tokenize(split[i])) {
				wordCheck(sentenceList, token);
			}
		}
		return sentenceList;
	}

	// トピック分析に使用する文字に絞る
	private ArrayList<String> wordCheck(ArrayList<String> sentenceList, Token token) {
		if(token.getPartOfSpeech().matches(Statics.TOPIC_HINSHI)) {
			if (token.getBaseForm() == null) {
				sentenceList.add(token.getSurfaceForm());
			} else {
				sentenceList.add(token.getBaseForm());
			}
			System.out.println(token.getSurfaceForm() + "｜" + token.getBaseForm() + "｜" + token.getPartOfSpeech());
		}
		return sentenceList;
	}
}