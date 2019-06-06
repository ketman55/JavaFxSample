package kuromoji;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import common.Statics;

public class ReadAndWhiter {
	private DoKuromoji dk = new DoKuromoji();

	public ReadAndWhiter() {

	}

	public void runReadAndWhite(String pass) throws Exception{
			//Fileクラスのオブジェクトを生成
			File dir = new File(pass);

			//listFilesメソッドを使用して一覧を取得
			File[] list = dir.listFiles();

			for(int i=0; i<list.length; i++) {
				// テキストデータを取得
				String text = "";
				BufferedReader b_reader = new BufferedReader(new InputStreamReader(new FileInputStream(list[i]),"UTF-8"));
				String data = "";
	            while ((data = b_reader.readLine()) != null) {
	            	text += data;
	            }
	            b_reader.close();

				// 分割してkuromojiにかける
				String writtenWords = "";
				List<String> resultList = dk.analyse(text);
				for (int j=0; j<resultList.size(); j++) {
					writtenWords += " " + resultList.get(j);
				}
				// テキストデータとして保存
				String writtenFile = Statics.OUTPUT_KUROMOJI + "\\" + list[i].getName();
				File outputFile = new File(writtenFile);
				PrintWriter p_writer = new PrintWriter(new BufferedWriter
						(new OutputStreamWriter(new FileOutputStream(outputFile),"UTF-8")));
				p_writer.println(writtenWords);
				p_writer.close();
			}
	}
}
