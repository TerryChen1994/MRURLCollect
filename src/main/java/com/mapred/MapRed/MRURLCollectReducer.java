package com.mapred.MapRed;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
public class MRURLCollectReducer extends Reducer<Text, Text, Text, Text> {
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		long lastProgressTS = 0; // 上一次发心跳的时间点
		long heartBeatInterval = 100000L; // 主动发心跳的间隔，100s，默认600s超时
		for(Text value : values){
			context.write(key,value);

			if (System.currentTimeMillis() - lastProgressTS > heartBeatInterval) {
				context.progress();
				lastProgressTS = System.currentTimeMillis();
			}
		}
		

	}
}
