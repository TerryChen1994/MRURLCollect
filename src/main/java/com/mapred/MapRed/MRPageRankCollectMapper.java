package com.mapred.MapRed;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import entity.TextPair;

public class MRPageRankCollectMapper extends Mapper<LongWritable, Text, TextPair, Text> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		long lastProgressTS = 0; // 上一次发心跳的时间点
		long heartBeatInterval = 100000L; // 主动发心跳的间隔，100s，默认600s超时
		String sValue = value.toString();
		String outKey = sValue.split(" ")[0];
		String outValue = sValue.split(" ")[1];
		try {
			context.write(new TextPair(outKey, "1"), new Text(outValue));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 主动发心跳
			if (System.currentTimeMillis() - lastProgressTS > heartBeatInterval) {
				context.progress();
				lastProgressTS = System.currentTimeMillis();
			}

		}

	}

}
