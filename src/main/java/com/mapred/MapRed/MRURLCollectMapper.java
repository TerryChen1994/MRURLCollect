package com.mapred.MapRed;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import entity.RecordContent;
import entity.RecordHeader;
import entity.TextPair;

public class MRURLCollectMapper extends Mapper<RecordHeader, RecordContent, TextPair, Text> {

	public void map(RecordHeader key, RecordContent value, Context context) throws IOException, InterruptedException {
		long lastProgressTS = 0; // 上一次发心跳的时间点
		long heartBeatInterval = 100000L; // 主动发心跳的间隔，100s，默认600s超时

		try {
			context.write(new TextPair(key.getWarcTrecId(), "0"), new Text(key.getWarcTargetUri()));
			
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
