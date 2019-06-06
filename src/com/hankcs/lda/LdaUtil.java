/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2015/1/29 19:07</create-date>
 *
 * <copyright file="LdaUtil.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package com.hankcs.lda;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import common.Statics;

/**
 * @author hankcs
 */
public class LdaUtil
{
	/**
	 * To translate a LDA matrix to readable result
	 * @param phi the LDA model
	 * @param vocabulary
	 * @param limit limit of max words in a topic
	 * @return a map array
	 */
	public static Map<String, Double>[] translate(double[][] phi, Vocabulary vocabulary, int limit)
	{
		limit = Math.min(limit, phi[0].length);
		Map<String, Double>[] result = new Map[phi.length];
		for (int k = 0; k < phi.length; k++)
		{
			Map<Double, String> rankMap = new TreeMap<Double, String>(Collections.reverseOrder());
			for (int i = 0; i < phi[k].length; i++)
			{
				rankMap.put(phi[k][i], vocabulary.getWord(i));
			}
			Iterator<Map.Entry<Double, String>> iterator = rankMap.entrySet().iterator();
			result[k] = new LinkedHashMap<String, Double>();
			for (int i = 0; i < limit; ++i)
			{
				Map.Entry<Double, String> entry = iterator.next();
				result[k].put(entry.getValue(), entry.getKey());
			}
		}
		return result;
	}

	public static Map<String, Double> translate(double[] tp, double[][] phi, Vocabulary vocabulary, int limit)
	{
		Map<String, Double>[] topicMapArray = translate(phi, vocabulary, limit);
		double p = -1.0;
		int t = -1;
		for (int k = 0; k < tp.length; k++)
		{
			if (tp[k] > p)
			{
				p = tp[k];
				t = k;
			}
		}
		return topicMapArray[t];
	}

	/**
	 * To print the result in a well formatted form
	 * System.out.println ⇒ リストを返却する仕様に修正
	 * @param result
	 * @throws IOException
	 */
	public static void explain(Map<String, Double>[] result) throws IOException
	{
		/*
        int i = 0;
        for (Map<String, Double> topicMap : result)
        {
            System.out.printf("topic %d :\n", i++);
            explain(topicMap);
            System.out.println();
        }
		 */

		// 2019/06/02 追記
		String writtenFile = Statics.OUTPUT_LDA;
		File outputFile = new File(writtenFile);
		PrintWriter p_writer = new PrintWriter(new BufferedWriter
				(new OutputStreamWriter(new FileOutputStream(outputFile),"UTF-8")));

		int i = 0;
		for (Map<String, Double> topicMap : result)
		{
			System.out.printf("topic %d :\n", i++);
			p_writer.println("topic:" + i);

			for (Map.Entry<String, Double> entry : topicMap.entrySet())
			{
				System.out.println(entry.toString());
				p_writer.println(entry.toString());
			}
			System.out.println();
			p_writer.println();
		}
		p_writer.close();
	}

	public static void explain(Map<String, Double> topicMap)
	{
		for (Map.Entry<String, Double> entry : topicMap.entrySet())
		{
			System.out.println(entry);
		}
	}
}
